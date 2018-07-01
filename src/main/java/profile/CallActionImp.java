package profile;

import java.math.BigDecimal;

import entities.Organisation;
import entities.PaymentMethod;

public class CallActionImp implements CallAction {
	// TODO: should be lists
	private BigDecimal amountRequested;
	private Organisation paymentReceiver;
	private PaymentMethod paymentMethod;
	
	public CallActionImp() {
		
	}
	
	@Override
	public void setPaymentRequest(BigDecimal amountRequested) {
		this.amountRequested = amountRequested;
	}
	
	@Override
	public void setPaymentReceiver(Organisation o) {
		this.paymentReceiver = o;
	}
	
	@Override
	public void setPaymentMethod(PaymentMethod pm) {
		this.paymentMethod = pm;
	}
	
	@Override
	public BigDecimal getAmountRequested() {
		return amountRequested;
	}
	
	@Override
	public Organisation getPaymentReceiver() {
		return paymentReceiver;
	}
	
	@Override
	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}
}
