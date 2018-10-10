package profile;

import java.util.List;

import entities.Organisation;
import entities.PaymentMethod;

/**
 * The call action interface is the abstraction which defines behavior for the 
 * actions (demands) made by persons. The actions currently are restricted to 
 * payment requests/ ways of making payments.
 * @author Pulkit
 *
 */
public interface CallAction {
	void addPaymentRequest(Double amountRequested);
	void addPaymentReceiver(Organisation o);
	void addPaymentMethod(PaymentMethod pm);
	List<Double> getAmountRequested();
	List<Organisation> getPaymentReceiver();
	List<PaymentMethod> getPaymentMethod();
}
