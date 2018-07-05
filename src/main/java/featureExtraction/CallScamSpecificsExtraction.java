package featureExtraction;

import java.util.List;

import com.google.protobuf.Value;

import entities.OperationPhrase;
import entities.Organisation;
import entities.ThreatEnum;
import profile.CallProfile;
import profile.CallScamSpecifics;
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
		if (intentName.equals("Call_Urgency")) {
			scam.incrementUrgencyIndex();
		} else if (intentName.equals("Call_Authority")) {
			resolveCallAuthority(entityList,scam);
		} else {
			resolveCallOperation(entityList,scam);
		}
	}
	
	private void resolveCallAuthority(List<DFEntity> entityList, CallScamSpecifics scam) {
		for (DFEntity ent: entityList) {
			String entityName = ent.getEntityName();
			// (1) Detecting Call_Authority
			if (entityName.equals("Court")) {
				List<Value> valList = ent.getEntityValue().getListValue().getValuesList();
				for (Value v: valList) {
					String entityValue = v.getStringValue();
					if (entityValue.equals("court")) {
						scam.incrementCourtMention();
					} else {
						scam.addOrganisation(Organisation.COURT_OFFICIAL);
					}
				}
			}
		}
	}
	
	private void resolveCallOperation(List<DFEntity> entityList, CallScamSpecifics scam) {
		for (DFEntity ent: entityList) {
			String entityName = ent.getEntityName();
			if (entityName.equals("Court")) {
				scam.incrementCourtMention();
			} else if (entityName.equals("Arrest")) {
				scam.addThreat(ThreatEnum.ARREST);
			} else if (entityName.equals("GovernmentEntity")) {
				scam.addOrganisation(Organisation.GOVERNMENT_ENTITY);
			} else if (entityName.equals("Operation_Phrase")) {
				OperationPhrase op = null;
				List<Value> valList = ent.getEntityValue().getListValue().getValuesList();
				for (Value v: valList) {
					String entityValue = v.getStringValue();
					switch (entityValue) {
						case "disconnect":
							op = OperationPhrase.DISCONNECT;
							break;
						case "on hold":
							op = OperationPhrase.ON_HOLD;
							break;
						case "record":
							op = OperationPhrase.RECORD;
							break;
						case "do not hang up":
							op = OperationPhrase.DO_NOT_HANG_UP;
							break;
						default:
							op = OperationPhrase.DEFAULT;
							break;
					}
					scam.addScamSpecifcPhrase(op);
				}
			}
		}
	}
	
}
