package rules;

import java.util.List;

import com.google.protobuf.Value;

import profile.CallScamSpecifics;
import scallCallDetection.DFEntity;

/**
 * Custom IRS Rule inherits from rule. This class defines behaviour associated
 * with intents related to CallScamSpecifics. 
 * @author Pulkit
 *
 */
public class CustomIRSRules extends Rule{
	private CallScamSpecifics callSpecifics;
	private String intentName;

	public CustomIRSRules(CallScamSpecifics callSpecifics, String intentName) {
		super();
		this.callSpecifics = callSpecifics;
		this.intentName = intentName;
	}
	
	/**
	 * Applies rule based on the intent detected.
	 */
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
	
	/**
	 * Define rule related to call authority: i.e. related to courts/ law.
	 * @param ent
	 */
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
	
	/**
	 * Define rule related to courts mention and any threats related to those.
	 * @param ent
	 */
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
