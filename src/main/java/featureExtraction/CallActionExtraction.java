package featureExtraction;

import java.util.List;

import profile.CallAction;
import profile.CallProfile;
import rules.ActionRule;
import rules.Rule;
import scallCallDetection.ConversationPhrase;
import scallCallDetection.DFEntity;

public class CallActionExtraction implements Extraction {
	private ConversationPhrase phrase;

	public CallActionExtraction(ConversationPhrase phrase) {
		this.phrase = phrase;
	}

	@Override
	public void updateProfile(CallProfile profile) {
		CallAction action = profile.getCallAction();
		List<DFEntity> entityList = phrase.getEntities();
		Rule actionRule = new ActionRule(action);
		
		for (DFEntity ent: entityList) {
			actionRule.applyRule(ent);
		}
	}
}
