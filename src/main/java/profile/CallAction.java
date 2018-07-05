package profile;

import java.util.List;

import entities.Organisation;
import entities.PaymentMethod;

public interface CallAction {
	void addPaymentRequest(Double amountRequested);
	void addPaymentReceiver(Organisation o);
	void addPaymentMethod(PaymentMethod pm);
	List<Double> getAmountRequested();
	List<Organisation> getPaymentReceiver();
	List<PaymentMethod> getPaymentMethod();
}
