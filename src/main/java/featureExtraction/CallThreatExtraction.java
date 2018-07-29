package featureExtraction;

import java.util.List;

import profile.CallProfile;
import profile.Threat;
import rules.Rule;
import rules.ThreatRules;
import scallCallDetection.ConversationPhrase;
import scallCallDetection.DFEntity;

public class CallThreatExtraction implements Extraction {
	
	private ConversationPhrase phrase;
	
	public CallThreatExtraction(ConversationPhrase phrase) {
		this.phrase = phrase;
	}
	
	@Override
	public void updateProfile(CallProfile profile) {
		Threat threat = profile.getCallThreat();
		List<DFEntity> entityList = phrase.getEntities();
		Rule rule = new ThreatRules(threat);
		for (DFEntity ent: entityList) {
			rule.applyRule(ent);
		}
	}
}
