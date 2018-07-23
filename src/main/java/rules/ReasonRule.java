package rules;

import entities.Organisation;
import profile.CallAction;
import profile.CallReason;
import profile.CallSource;
import scallCallDetection.DFEntity;

public class ReasonRule extends Rule {
	private CallReason reason;
	private CallAction action;
	private CallSource source;
	private int numberOfBools;
	private boolean taxVisited;
	
	public ReasonRule(CallReason reason, CallAction action, CallSource source) {
		super();
		this.reason = reason;
		this.action = action;
		this.source = source;
		this.numberOfBools = 0;
		this.taxVisited = false;
	}
	
	@Override
	public void applyRule(DFEntity ent) {
		String entityName = ent.getEntityName();
		MapType type = getMapType(entityName);
		switch (type) {
			case ORGANISATION_MAP:
				Organisation org = getOrganisationMap().get(entityName);
				if (!source.getOrganisations().contains(org)) {
					source.addOrganisation(org);
				}
				numberOfBools++;
				break;
			case TAX:
				if (!taxVisited) {
					reason.setCallReasonTax(true, "");
					numberOfBools++;
					taxVisited = true;
				}
				break;
			case CURRENCY:
				action.addPaymentRequest(getPaymentAmount(ent));
				break;
			default:
				break;
		}
	}
	
	public int getNumberOfBools() {
		return this.numberOfBools;
	}

}
