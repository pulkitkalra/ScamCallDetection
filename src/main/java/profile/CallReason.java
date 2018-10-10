package profile;

import java.util.List;

/**
 * The CallReason abstraction enforces behavior related to context of the call.
 * Any logic related to "Why is the caller calling" should be enforced by this interface.
 * @author Pulkit
 *
 */
public interface CallReason {
	
	void setCallReasonTax(boolean taxRelated, String taxAction);
	void updateConfidence(boolean... taxMentions);
	boolean getTaxRelated();
	List<String> getTaxAction();
	void addTaxAction(String action);
	int getTaxConfidence();

}
