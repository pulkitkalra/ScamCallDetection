package featureExtraction;

import java.util.List;

import entities.Organisation;
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
			/*if (ent.getEntityName().equals("IRS")) {
				source.addOrganisation(Organisation.IRS);
			} else if (ent.getEntityName().equals("GovernmentEntity")) {
				source.addOrganisation(Organisation.GOVERNMENT_ENTITY);
			} else if (ent.getEntityName().equals("given-name")) {
				source.addName(ent.getEntityValue().getStringValue());
			}*/
		}

	}

}
