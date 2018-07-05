package featureExtraction;

import profile.CallProfile;
import profile.CallProfileImp;
import scallCallDetection.ConversationPhrase;

public class CallFeatureExtraction {
	private CallProfile profile;
	
	public CallFeatureExtraction() {
		this.profile = new CallProfileImp();
	}
	
	/**
	 * Assume we are only dealing with Call_intro intent for now.
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
		
		System.out.println("Done!");
	}
	
	public CallProfile getCallProfile() {
		return profile;
	}

}
