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
		Extraction result;
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
			case "Call_Authority":
				break;
			case "Call_Operation":
				break;
			case "Call_PrivacyThreat":
				break;
			case "Call_Threat":
				break;
			case "Call_Urgency":
				break;
			default:
				// none
				break;
		}
		result.updateProfile(profile);
		System.out.println("Done!");
	}
	
	public CallProfile getCallProfile() {
		return profile;
	}

}
