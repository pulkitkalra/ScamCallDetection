package scallCallDetection;

import java.io.BufferedReader;
import java.io.FileReader;
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
import export.ExportProfile;
import export.ExportProfileAdapter;
import featureExtraction.CallFeatureExtraction;
import javafx.concurrent.Task;
import profile.CallProfile;
import profile.ProfileDTO;
import profile.ProfileDTOAdapter;

/**
 * DialogFlow API Detect Intent sample with text inputs.
 */
public class DetectIntentTexts {
	private ProfileDTO callProfileDTO;
	private String[] fileText;
	private SessionsClient sessionsClient;
	private SessionName session;
	private CallFeatureExtraction extraction;
	private List<String[]> csvOutputList;
	private DetectionEngine engine;
	private String languageCode;
	private boolean recording;
	
	public DetectIntentTexts(ProfileDTO dto, boolean record) {
		this.recording = record;
		this.callProfileDTO = dto;
		csvOutputList = new ArrayList<>();
		engine = new WekaClassifier();
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
	public synchronized void detectIntentTexts(String projectId, String[] texts, String sessionId,
			String languageCode) throws Exception {
		
		this.fileText = texts;
		this.languageCode = languageCode;
		// Instantiates a client
		try (SessionsClient sessionsClient = SessionsClient.create()) {
			this.sessionsClient = sessionsClient;
			Long startTime = System.currentTimeMillis();
			// Set the session name using the sessionId (UUID) and projectID (my-project-id)
			session = SessionName.of(projectId, sessionId);
			//System.out.println("Session Path: " + session.toString());
			extraction  = new CallFeatureExtraction();
			int index = 1;
			engine.setup();
			
			if (recording) {
				/*SpeechAudioSocket so = new SpeechAudioSocket(callProfileDTO);
            	so.convertSpeechToText();*/
				final Task<Void> task = new Task<Void>() {
		        	@Override 
		        	protected Void call() throws InterruptedException {
		        		try {
		                	SpeechAudioSocket so = new SpeechAudioSocket(callProfileDTO);
		                	so.convertSpeechToText();
		                } catch (Exception e) {
		        			e.printStackTrace();
		        		}
						return null;
		        	}            
		        };
		        new Thread(task).start();
		        
            	Thread.sleep(100);
            	String msg;
            	while((msg = SpeechAudioSocket.textQueue.take()) !="exit"){
            		processText(msg, 0, recording);
            	}
            	
				/*do {
				    this.wait(100); // to avoid a notification arriving early.
				    processText(SpeechAudioSocket.textQueue.remove(), 0, recording);
				} 
				while (!SpeechAudioSocket.textQueue.isEmpty()); */

				
			} else {
				// Detect intents for each text input: Process from File
				for (String text : texts) {
					// Process from File
					processText(text,index,recording);
					index++;
				}
			}
			
			
			ExportProfile.exportToCSV(csvOutputList);
			Long totalTime = System.currentTimeMillis() - startTime;
			totalTime.toString();
		
		}
	}
	
	private void processText(String text, int index, boolean recording) {
		if (recording) {
			System.out.println("Processed line: " + text);
		} else {
			System.out.println("Processing line " + index + "/" + fileText.length);
		}
		
		// set current line of text so we can print it on GUI
		callProfileDTO.setCurrentLineOfText(text);
		if (text.isEmpty()) return;
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
		callProfileDTO.setProgressProbValue(probOfScam);
		callProfileDTO.setProbabilityValue(df.format(probOfScam*100) + "%");
		
		System.out.println("The probability of this call at this point in time being a scam is " + df.format(probOfScam*100) + "%");
	}
	
	public void start() throws Exception {
		@SuppressWarnings("unused")
		Storage storage = StorageOptions.newBuilder().build().getService();

		String projectId = "key-conquest-151823";
		String sessionId = UUID.randomUUID().toString();
		String languageCode = "en-US";
		
		if (recording) {
			detectIntentTexts(projectId, null, sessionId, languageCode);
		} else {
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
}
