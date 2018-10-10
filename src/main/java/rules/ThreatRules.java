package rules;

import java.util.List;

import com.google.protobuf.Value;

import profile.CallReason;
import profile.Threat;
import scallCallDetection.DFEntity;

/**
 * ThreatRules inherits from rule. This class defines behaviour associated
 * with intents related to Threat and CallReason intent. 
 * @author Pulkit
 *
 */
public class ThreatRules extends Rule {
	private Threat threat; 
	private CallReason reason;
	
	public ThreatRules(Threat threat, CallReason reason) {
		super();
		this.threat = threat;
		this.reason = reason;
	}
	
	/**
	 * Apply rule based on any threats detected to arrest, prison, money
	 * or privacy related threats.
	 */
	@Override
	public void applyRule(DFEntity ent) {
		String entityName = ent.getEntityName();
		MapType type = getMapType(entityName);
		switch (type) {
			case THREAT_MAP:
				threat.addArrestThreat(getThreatMap().get(entityName));
				break;
			case ORGANISATION_MAP:
				threat.addOrganisations(getOrganisationMap().get(entityName));
				break;
			case PRIVACY_MAP:
				List<Value> valList = ent.getEntityValue().getListValue().getValuesList();
				for (Value v: valList) {
					String entityValue = v.getStringValue();
					threat.addPrivacyThreat(getPrivateInfoMap().get(entityValue));
				}
				break;
			case TAX:
				reason.setCallReasonTax(true, "");
			default:
				break;
		}
	}

}
