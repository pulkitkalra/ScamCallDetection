package featureExtraction;

import java.util.List;

import profile.CallProfile;
import profile.CallSource;
import rules.IntroRule;
import rules.Rule;
import scallCallDetection.ConversationPhrase;
import scallCallDetection.DFEntity;

public class CallIntroExtraction implements Extraction{
	private ConversationPhrase phrase;

	public CallIntroExtraction(ConversationPhrase phrase) {
		this.phrase = phrase;
	}
	
	@Override
	public void updateProfile(CallProfile profile) {
		CallSource source = profile.getCallSource();
		List<DFEntity> entityList = phrase.getEntities();
		Rule rule = new IntroRule(source);
		for (DFEntity ent: entityList) {
			rule.applyRule(ent);
		}
	}
}
