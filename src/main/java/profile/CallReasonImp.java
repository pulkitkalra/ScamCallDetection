package profile;

import java.util.ArrayList;
import java.util.List;

/**
 * The CallReasonImp defines state related to call context/ reason.
 * It allows the 'context' to call to be captured in the call profile.
 * This is currently restricted tax related contexts.
 * @author Pulkit
 *
 */
public class CallReasonImp implements CallReason {
	
	private boolean taxRelated;
	private List<String> taxAction; 
	private int confidenceOfTax;
	
	public CallReasonImp() {
		this.taxAction = new ArrayList<>();
		this.confidenceOfTax = 0;
	}
	/**
	 * TODO:Enforce tax related being false, then no action should be defined.
	 */
	@Override
	public void setCallReasonTax(boolean taxRelated, String taxAction) {
		this.taxRelated = taxRelated;
		this.taxAction.add(taxAction);
	}
	/**
	 * Method is used to pass in a max of 3 TRUE boolean parameters to reflect
	 * if (1) tax was mentioned, (2) a government entity was mentioned and (3) irs mentioned.
	 * @param taxMentions
	 */
	@Override
	public void updateConfidence(boolean... taxMentions) {
		confidenceOfTax+= taxMentions.length;
	}
	
	@Override
	public boolean getTaxRelated() {
		return taxRelated;
	}
	
	@Override
	public List<String> getTaxAction() {
		return taxAction;
	}
	
	@Override
	public int getTaxConfidence() {
		return confidenceOfTax;
	}

	@Override
	public void addTaxAction(String action) {
		taxAction.add(action);
	}

}
