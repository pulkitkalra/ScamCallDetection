package rules;

import java.util.List;

import com.google.protobuf.Value;

import profile.CallAction;
import scallCallDetection.DFEntity;

/**
 * ActionRule inherits from Rule. This class defines behaviour associated
 * with intents related to CallAction. 
 * @author Pulkit
 *
 */
public class ActionRule extends Rule {
	private CallAction action;
	public ActionRule (CallAction action) {
		super();
		this.action = action;
	}
	
	/**
	 * Applies rules based on which type of entity is detected within an
	 * intent that is passed in as input.
	 */
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
