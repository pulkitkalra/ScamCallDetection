package rules;

import java.util.List;

import com.google.protobuf.Value;

import profile.CallScamSpecifics;
import scallCallDetection.DFEntity;

public class CustomIRSRules extends Rule{
	private CallScamSpecifics callSpecifics;
	private String intentName;

	public CustomIRSRules(CallScamSpecifics callSpecifics, String intentName) {
		super();
		this.callSpecifics = callSpecifics;
		this.intentName = intentName;
	}

	@Override
	public void applyRule(DFEntity ent) {
		if (intentName.equals("Call_Urgency")) {
			callSpecifics.incrementUrgencyIndex();
		} else if (intentName.equals("Call_Authority")) {
			callAuthorityRule(ent);
		} else {
			callPhrasesRule(ent);
		}

	}

	private void callAuthorityRule(DFEntity ent) {
		String entityName = ent.getEntityName();
		MapType type = getMapType(entityName);
		switch (type) {
		case ORGANISATION_MAP:
			if (entityName.equals("Court")) {
				List<Value> valList = ent.getEntityValue().getListValue().getValuesList();
				for (Value v: valList) {
					String entityValue = v.getStringValue();
					if (entityValue.equals("court")) {
						callSpecifics.incrementCourtMention();
					} else {
						callSpecifics.addOrganisation(getOrganisationMap().get(entityName));
					}
				}
			}
			break;
		default:
			break;
		}	
	}

	private void callPhrasesRule(DFEntity ent) {
		String entityName = ent.getEntityName();
		MapType type = getMapType(entityName);
		if (entityName.equals("Court")) {
			callSpecifics.incrementCourtMention();
			return;
		} 
		switch (type) {
			case ORGANISATION_MAP:
				callSpecifics.addOrganisation(getOrganisationMap().get(entityName));
				break;
			case THREAT_MAP:
				callSpecifics.addThreat(getThreatMap().get(entityName));
				break;
			case CUSTOM_IRS:
				List<Value> valList = ent.getEntityValue().getListValue().getValuesList();
				for (Value v: valList) {
					String entityValue = v.getStringValue();
					callSpecifics.addScamSpecifcPhrase(getOperationPhraseMap().get(entityValue));
				}
				break;
			default:
				break;
		}
	}

}
