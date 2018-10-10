package featureExtraction;

import profile.CallProfile;
import profile.CallProfileImp;
import scallCallDetection.ConversationPhrase;

/**
 * Call Feature extraction defines the mapping between DF intents and the relevant 
 * extraction classes.
 * 
 * When adding a new extractor, you must register it under its intent under here.
 * @author Pulkit
 *
 */
public class CallFeatureExtraction {
	private CallProfile profile;
	
	public CallFeatureExtraction() {
		this.profile = new CallProfileImp();
	}
	
	/**
	 * This method extracts the relevant intent from a conversation phrase and finds 
	 * the matching extraction class.
	 * 
	 * The call profile is updated after the phrase has been processed.
	 * @param phrase
	 */
	public void processConversationPhrase(ConversationPhrase phrase) {
		String intentName = phrase.getIntent().getIntent().getDisplayName();
		Extraction result = null;
		switch (intentName) {
			case "Call_intro":
				result = new CallIntroExtraction(phrase);
				break;
			case "Call_Context":
				result = new CallReasonExtraction(phrase);
				break;
			case "Call_RequestPayment":
				result = new CallActionExtraction(phrase);
				break;
			case "Call_PrivacyThreat": case "Call_Threat":
				result = new CallThreatExtraction(phrase);
				break;
			case "Call_Authority": case "Call_Operation": case "Call_Urgency":
				result = new CallScamSpecificsExtraction(phrase);
				break;
			default:
				// none: Default Fallback Intent
				break;
		}
		
		if (result != null) {
			result.updateProfile(profile);
		}
	}
	
	public CallProfile getCallProfile() {
		return profile;
	}

}
