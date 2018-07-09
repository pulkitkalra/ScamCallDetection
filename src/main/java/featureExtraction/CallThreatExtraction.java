package featureExtraction;

import java.util.List;

import com.google.protobuf.Value;

import entities.Organisation;
import entities.PersonalInfo;
import entities.ThreatEnum;
import profile.CallProfile;
import profile.Threat;
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

		for (DFEntity ent: entityList) {
			String entityName = ent.getEntityName();
			if (entityName.equals("Arrest")) {
				threat.addArrestThreat(ThreatEnum.ARREST);
			} else if (entityName.equals("Prison")) {
				threat.addArrestThreat(ThreatEnum.PRISON);
			} else if (entityName.equals("GovernmentEntity")){
				threat.addOrganisations(Organisation.GOVERNMENT_ENTITY);
			} else if (entityName.equals("Court")){
				threat.addOrganisations(Organisation.COURTHOUSE);
			} else if (entityName.equals("IRS")){
				threat.addOrganisations(Organisation.IRS);
			} else if (entityName.equals("Personal_Information")) {
				List<Value> lv = ent.getEntityValue().getListValue().getValuesList();
				for (Value v: lv) {
					String threatType = v.getStringValue();
					PersonalInfo pInfo;
					switch (threatType) {
						case "identitity":
							pInfo = PersonalInfo.IDENTITY;
							break;
						case "belongings":
							pInfo = PersonalInfo.BELONGINGS;
							break;
						case "social_security":
							pInfo = PersonalInfo.SOCIAL_SECURITY;
							break;
						case "bank":
							pInfo = PersonalInfo.BANK;
							break;
						case "credentials":
							pInfo = PersonalInfo.CREDENTIALS;
							break;
						default:
							pInfo = PersonalInfo.DEFAULT;
							break;
					}
					threat.addPrivacyThreat(pInfo);
				}
				
			}
			
		}
	}
}
