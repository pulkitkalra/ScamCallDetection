package profile;

import java.util.ArrayList;
import java.util.List;

public class CallReasonImp implements CallReason {
	
	private boolean taxRelated;
	private List<String> taxAction; 
	private int sentiment;
	
	public CallReasonImp() {
		this.taxAction = new ArrayList<>();
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
	 * Set sentiment of call reason. Sentiment must be between -1 and 1.
	 * @param sentiment
	 */
	@Override
	public void setSentiment(int sentiment) {
		if (sentiment > -1 && sentiment < 1) {
			this.sentiment = sentiment;
		} else {
			sentiment = 0;
		}
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
	public int getSentiment() {
		return sentiment;
	}

	@Override
	public void addTaxAction(String action) {
		taxAction.add(action);
		
	}

}
