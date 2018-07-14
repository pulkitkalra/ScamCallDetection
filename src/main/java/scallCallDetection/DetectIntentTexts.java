package scallCallDetection;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.google.cloud.dialogflow.v2.DetectIntentResponse;
import com.google.cloud.dialogflow.v2.QueryInput;
import com.google.cloud.dialogflow.v2.SessionName;
import com.google.cloud.dialogflow.v2.SessionsClient;
import com.google.cloud.dialogflow.v2.TextInput;
import com.google.cloud.dialogflow.v2.TextInput.Builder;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import classification.DetectionEngine;
import classification.WekaClassifier;
import edu.stanford.nlp.util.Index;
import edu.stanford.nlp.util.StringParsingTask;
import export.ExportProfile;
import export.ExportProfileAdapter;
import featureExtraction.CallFeatureExtraction;
import profile.CallProfile;
import profile.ProfileDTO;
import profile.ProfileDTOAdapter;



/**
 * DialogFlow API Detect Intent sample with text inputs.
 */
public class DetectIntentTexts {
	private ProfileDTO callProfileDTO;
	
	public DetectIntentTexts(ProfileDTO dto) {
		this.callProfileDTO = dto;
	}
	
	/**
	 * Returns the result of detect intent with texts as inputs.
	 *
	 * Using the same `session_id` between requests allows continuation of the conversation.
	 * @param projectId Project/Agent Id.
	 * @param texts The text intents to be detected based on what a user says.
	 * @param sessionId Identifier of the DetectIntent session.
	 * @param languageCode Language code of the query.
	 */
	public void detectIntentTexts(String projectId, String[] texts, String sessionId,
			String languageCode) throws Exception {
		// Instantiates a client
		try (SessionsClient sessionsClient = SessionsClient.create()) {
			Long startTime = System.currentTimeMillis();
			// Set the session name using the sessionId (UUID) and projectID (my-project-id)
			SessionName session = SessionName.of(projectId, sessionId);
			System.out.println("Session Path: " + session.toString());
			CallFeatureExtraction extraction  = new CallFeatureExtraction();
			List<String[]> csvOutputList = new ArrayList<>();
			int index = 0;
			
			DetectionEngine engine = new WekaClassifier();
			engine.setup();
			
			// Detect intents for each text input
			for (String text : texts) {
				System.out.println("Processing line " + (++index) + "/" + texts.length);
				if (text.isEmpty()) continue;
				// Set the text (hello) and language code (en-US) for the query
				Builder textInput = TextInput.newBuilder().setText(text).setLanguageCode(languageCode);

				// Build the query with the TextInput
				QueryInput queryInput = QueryInput.newBuilder().setText(textInput).build();

				// Performs the detect intent request
				DetectIntentResponse response = sessionsClient.detectIntent(session, queryInput);

				ConversationPhrase phrase = new ConversationPhrase(response);
				extraction.processConversationPhrase(phrase);
				CallProfile cp = extraction.getCallProfile();
				// Create Profile DTO:
				ProfileDTOAdapter profileAdapter = new ProfileDTOAdapter(cp, callProfileDTO);
				profileAdapter.updateProfileDTO();
				// Create Export Profile.
				ExportProfileAdapter adapter = new ExportProfileAdapter(cp);
				String[] profList = adapter.getProfileList();
				if (!csvOutputList.contains(profList)) {
					csvOutputList.add(adapter.getProfileList());
				}
				//phrase.printNLPResult();
				
				double probOfScam = engine.getProbabilityOfScam(profList);
				DecimalFormat df = new DecimalFormat("####0.00");
				System.out.println("The probability of this call at this point in time being a scam is " + df.format(probOfScam*100) + "%");
				
			}
			
			ExportProfile.exportToCSV(csvOutputList);
			Long totalTime = System.currentTimeMillis() - startTime;
			totalTime.toString();
			
		}
	}
	
	public void start() throws Exception {
		@SuppressWarnings("unused")
		Storage storage = StorageOptions.newBuilder().build().getService();

		String projectId = "key-conquest-151823";
		String sessionId = UUID.randomUUID().toString();
		String languageCode = "en-US";
		
		BufferedReader abc = new BufferedReader(new FileReader("Data1.txt"));
		List<String> lines = new ArrayList<String>();
		String line;
		while((line = abc.readLine()) != null) {
		    lines.add(line);
		}
		abc.close();

		String[] data = lines.toArray(new String[]{});
		detectIntentTexts(projectId, data, sessionId, languageCode);
	}
}
