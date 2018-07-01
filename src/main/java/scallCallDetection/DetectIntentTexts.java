package scallCallDetection;

import java.io.BufferedReader;
import java.io.FileReader;
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

import featureExtraction.CallFeatureExtraction;



/**
 * DialogFlow API Detect Intent sample with text inputs.
 */
public class DetectIntentTexts {

	/**
	 * Returns the result of detect intent with texts as inputs.
	 *
	 * Using the same `session_id` between requests allows continuation of the conversation.
	 * @param projectId Project/Agent Id.
	 * @param texts The text intents to be detected based on what a user says.
	 * @param sessionId Identifier of the DetectIntent session.
	 * @param languageCode Language code of the query.
	 */
	public static void detectIntentTexts(String projectId, String[] texts, String sessionId,
			String languageCode) throws Exception {
		// Instantiates a client
		try (SessionsClient sessionsClient = SessionsClient.create()) {
			// Set the session name using the sessionId (UUID) and projectID (my-project-id)
			SessionName session = SessionName.of(projectId, sessionId);
			System.out.println("Session Path: " + session.toString());
			CallFeatureExtraction extraction  = new CallFeatureExtraction();
			// Detect intents for each text input
			for (String text : texts) {
				if (text.isEmpty()) continue;
				// Set the text (hello) and language code (en-US) for the query
				Builder textInput = TextInput.newBuilder().setText(text).setLanguageCode(languageCode);

				// Build the query with the TextInput
				QueryInput queryInput = QueryInput.newBuilder().setText(textInput).build();

				// Performs the detect intent request
				DetectIntentResponse response = sessionsClient.detectIntent(session, queryInput);

				ConversationPhrase phrase = new ConversationPhrase(response);
				extraction.processConversationPhrase(phrase);
				phrase.printNLPResult();
			}
		}
	}
	
	public static void main(String[] args) throws Exception {
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
