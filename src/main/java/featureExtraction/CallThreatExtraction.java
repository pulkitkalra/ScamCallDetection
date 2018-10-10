package featureExtraction;

import java.util.List;

import profile.CallProfile;
import profile.CallReason;
import profile.Threat;
import rules.Rule;
import rules.ThreatRules;
import scallCallDetection.ConversationPhrase;
import scallCallDetection.DFEntity;

/**
 * Call Threat extraction extracts threat rules from conversation phrases.
 * @author Pulkit
 *
 */
public class CallThreatExtraction implements Extraction {
	
	private ConversationPhrase phrase;
	
	public CallThreatExtraction(ConversationPhrase phrase) {
		this.phrase = phrase;
	}
	
	@Override
	public void updateProfile(CallProfile profile) {
		Threat threat = profile.getCallThreat();
		CallReason reason = profile.getCallReason();
		List<DFEntity> entityList = phrase.getEntities();
		Rule rule = new ThreatRules(threat, reason);
		for (DFEntity ent: entityList) {
			rule.applyRule(ent);
		}
	}
}
