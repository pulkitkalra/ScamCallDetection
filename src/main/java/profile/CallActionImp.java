package profile;

import java.math.BigDecimal;
import java.util.List;

import entities.Organisation;
import entities.PaymentMethod;

public class CallActionImp implements CallAction {
	// TODO: should be lists
	private List<BigDecimal> amountRequested;
	private List<Organisation> paymentReceiver;
	private PaymentMethod paymentMethod;
	
	public CallActionImp() {
		
	}
	
	@Override
	public void addPaymentRequest(BigDecimal amountRequested) {
		this.amountRequested.add(amountRequested);
	}
	
	@Override
	public void addPaymentReceiver(Organisation o) {
		this.paymentReceiver.add(o);
	}
	
	@Override
	public void setPaymentMethod(PaymentMethod pm) {
		this.paymentMethod = pm;
	}
	
	@Override
	public List<BigDecimal> getAmountRequested() {
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
