package featureExtraction;

import java.util.List;

import com.google.protobuf.Struct;
import com.google.protobuf.Value;

import entities.Organisation;
import entities.PaymentMethod;
import profile.CallAction;
import profile.CallProfile;
import rules.ActionRule;
import rules.Rule;
import scallCallDetection.ConversationPhrase;
import scallCallDetection.DFEntity;

public class CallActionExtraction implements Extraction {
	private ConversationPhrase phrase;

	public CallActionExtraction(ConversationPhrase phrase) {
		this.phrase = phrase;
	}

	@Override
	public void updateProfile(CallProfile profile) {
		CallAction action = profile.getCallAction();
		List<DFEntity> entityList = phrase.getEntities();
		Rule actionRule = new ActionRule(action);
		
		for (DFEntity ent: entityList) {
			//String enitityName = ent.getEntityName();
			actionRule.applyRule(ent);
			//action = actionRule.
			
			/*if (enitityName.equals("IRS")) {
				action.addPaymentReceiver(Organisation.IRS);
			} else if (enitityName.equals("GovernmentEntity")) {
				action.addPaymentReceiver(Organisation.GOVERNMENT_ENTITY);
			} else if (enitityName.equals("PaymentMethods")){
				List<Value> valList = ent.getEntityValue().getListValue().getValuesList();
				for (Value v: valList) {
					String entityValue = v.getStringValue();
					PaymentMethod method = null;
					switch (entityValue) {
						case "gift card":
							method = PaymentMethod.GIFT_CARD;
							break;
						case "tax voucher":
							method = PaymentMethod.TAX_VOUCHER;
							break;
						case "bank card":
							method = PaymentMethod.BANK_CARD;
							break;
						case "transfer":
							method = PaymentMethod.TRANSFER;
							break;
					}
					action.addPaymentMethod(method);
				}
			} else if (enitityName.equals("unit-currency")) {
				Struct s = ent.getEntityValue().getStructValue();
				Double amount = s.getFieldsMap().get("amount").getNumberValue();
				action.addPaymentRequest(amount);
			} else if (enitityName.equals("number")) {
				action.addPaymentRequest(ent.getEntityValue().getNumberValue());
			}*/
		}
	}

}
