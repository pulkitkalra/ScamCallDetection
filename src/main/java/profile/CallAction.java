package profile;

import java.math.BigDecimal;
import java.util.List;

import entities.Organisation;
import entities.PaymentMethod;

public interface CallAction {
	void addPaymentRequest(BigDecimal amountRequested);
	void addPaymentReceiver(Organisation o);
	void setPaymentMethod(PaymentMethod pm);
	List<BigDecimal> getAmountRequested();
	Organisation getPaymentReceiver();
	PaymentMethod getPaymentMethod();
}
