package featureExtraction;

import java.util.List;

import entities.Organisation;
import profile.CallProfile;
import profile.CallSource;
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
		for (DFEntity ent: entityList) {
			if (ent.getEntityName().equals("IRS")) {
				source.addOrganisation(Organisation.IRS);
			} else if (ent.getEntityName().equals("GovernmentEntity")) {
				source.addOrganisation(Organisation.GOVERNMENT_ENTITY);
			} else if (ent.getEntityName().equals("given-name")) {
				source.addName(ent.getEntityValue().getStringValue());
			}
		}

	}

}
