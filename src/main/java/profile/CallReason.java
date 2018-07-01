package profile;

import java.util.List;

public interface CallReason {
	
	void setCallReasonTax(boolean taxRelated, String taxAction);
	void setSentiment(int sentiment);
	boolean getTaxRelated();
	List<String> getTaxAction();
	void addTaxAction(String action);
	int getSentiment();

}
