package rules;

import java.util.List;

import com.google.protobuf.Value;

import profile.CallReason;
import profile.Threat;
import scallCallDetection.DFEntity;

public class ThreatRules extends Rule {
	private Threat threat; 
	private CallReason reason;
	
	public ThreatRules(Threat threat, CallReason reason) {
		super();
		this.threat = threat;
		this.reason = reason;
	}
	
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
