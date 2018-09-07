package featureExtraction;

import java.util.List;

import profile.CallProfile;
import profile.CallScamSpecifics;
import rules.CustomIRSRules;
import rules.Rule;
import scallCallDetection.ConversationPhrase;
import scallCallDetection.DFEntity;

public class CallScamSpecificsExtraction implements Extraction {
	private ConversationPhrase phrase;
	private String intentName;
	
	public CallScamSpecificsExtraction(ConversationPhrase phrase) {
		this.phrase = phrase;
		this.intentName = phrase.getIntent().getIntent().getDisplayName();
	}

	@Override
	public void updateProfile(CallProfile profile) {
		CallScamSpecifics scam = profile.getCallScamSpecifics();
		List<DFEntity> entityList = phrase.getEntities();
		Rule rule = new CustomIRSRules(scam, intentName);
		for (DFEntity ent: entityList) {
			rule.applyRule(ent);
		}
	}
}
