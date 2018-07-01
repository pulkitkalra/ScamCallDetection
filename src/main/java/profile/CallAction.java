package profile;

import java.math.BigDecimal;

import entities.Organisation;
import entities.PaymentMethod;

public interface CallAction {
	void setPaymentRequest(BigDecimal amountRequested);
	void setPaymentReceiver(Organisation o);
	void setPaymentMethod(PaymentMethod pm);
	BigDecimal getAmountRequested();
	Organisation getPaymentReceiver();
	PaymentMethod getPaymentMethod();
}
