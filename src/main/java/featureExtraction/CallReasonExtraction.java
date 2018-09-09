package featureExtraction;

import java.util.Arrays;
import java.util.List;

import profile.CallAction;
import profile.CallProfile;
import profile.CallReason;
import profile.CallSource;
import rules.ReasonRule;
import scallCallDetection.ConversationPhrase;
import scallCallDetection.DFEntity;

public class CallReasonExtraction implements Extraction {
	private ConversationPhrase phrase;

	public CallReasonExtraction(ConversationPhrase phrase) {
		this.phrase = phrase;
	}

	@Override
	public void updateProfile(CallProfile profile) {
		CallReason reason = profile.getCallReason();
		CallAction action = profile.getCallAction();
		CallSource source = profile.getCallSource();
		List<DFEntity> entityList = phrase.getEntities();

		ReasonRule rule = new ReasonRule(reason, action, source);
		
		for (DFEntity ent: entityList) {
			rule.applyRule(ent);
		}
		
		int numberOfBools = rule.getNumberOfBools();
		numberOfBools = (numberOfBools > 3) ? 3: numberOfBools;
		boolean[] params = new boolean[numberOfBools];
		Arrays.fill(params, true);
		reason.updateConfidence(params);
	}

}
