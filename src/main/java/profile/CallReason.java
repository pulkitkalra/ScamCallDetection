package profile;

import java.util.List;

public interface CallReason {
	
	void setCallReasonTax(boolean taxRelated, String taxAction);
	void updateConfidence(boolean... taxMentions);
	boolean getTaxRelated();
	List<String> getTaxAction();
	void addTaxAction(String action);
	int getTaxConfidence();

}
