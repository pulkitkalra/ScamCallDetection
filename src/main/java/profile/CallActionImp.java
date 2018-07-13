package profile;

import java.util.ArrayList;
import java.util.List;

import entities.Organisation;
import entities.PaymentMethod;

public class CallActionImp implements CallAction {
	private List<Double> amountRequested;
	private List<Organisation> paymentReceiver;
	private List<PaymentMethod> paymentMethod;
	
	public CallActionImp() {
		amountRequested = new ArrayList<>();
		paymentReceiver = new ArrayList<>();
		paymentMethod = new ArrayList<>();
	}
	
	@Override
	public void addPaymentRequest(Double amountRequested) {
		this.amountRequested.add(amountRequested);
	}
	
	@Override
	public void addPaymentReceiver(Organisation o) {
		this.paymentReceiver.add(o);
	}
	
	@Override
	public void addPaymentMethod(PaymentMethod pm) {
		this.paymentMethod.add(pm);
	}
	
	@Override
	public List<Double> getAmountRequested() {
		return amountRequested;
	}
	
	@Override
	public List<Organisation> getPaymentReceiver() {
		return paymentReceiver;
	}
	
	@Override
	public List<PaymentMethod> getPaymentMethod() {
		return paymentMethod;
	}
}
