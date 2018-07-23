package rules;

import java.util.List;

import com.google.protobuf.Struct;
import com.google.protobuf.Value;

import profile.CallAction;
import scallCallDetection.DFEntity;

public class ActionRule extends Rule {
	private CallAction action;
	public ActionRule (CallAction action) {
		super();
		this.action = action;
	}
	
	/*public void applyRule(DFEntity ent, CallAction action) {
		this.action = action;
		applyRule(ent);
	}*/
	
	@Override
	public void applyRule(DFEntity ent) {
		String entityName = ent.getEntityName();
		MapType type = getMapType(entityName);
		switch (type) {
			case ORGANISATION_MAP:
				action.addPaymentReceiver((getOrganisationMap().get(entityName)));
				break;
			case PAYMENT_MAP:
				List<Value> valList = ent.getEntityValue().getListValue().getValuesList();
				for (Value v: valList) {
					String entityValue = v.getStringValue();
					action.addPaymentMethod(getPaymentMethodMap().get(entityValue));
				}
				break;
			case CURRENCY:
				action.addPaymentRequest(getPaymentAmount(ent));
				break;
			case NUMBER:
				action.addPaymentRequest(ent.getEntityValue().getNumberValue());
				break;
			default:
				break;
		}
		
	}


}
