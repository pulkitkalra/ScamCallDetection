package profile;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ProfileDTO {
	//private final StringProperty 
	private final StringProperty currentLineOfText = new SimpleStringProperty("");
	private final StringProperty listOfSourceOrgs = new SimpleStringProperty("Unknown");
	private final StringProperty callerNames = new SimpleStringProperty("Unknown");
	private final BooleanProperty taxRelated =  new SimpleBooleanProperty(false);
	private final StringProperty taxConfidence = new SimpleStringProperty("Unknown");;
	private final DoubleProperty amountRequested = new SimpleDoubleProperty(0);
	private final StringProperty paymentMethods = new SimpleStringProperty("Unknown");
	private final BooleanProperty arrestThreat = new SimpleBooleanProperty(false);
	private final BooleanProperty prisonThreat = new SimpleBooleanProperty(false);
	private final BooleanProperty privacyThreat = new SimpleBooleanProperty(false);
	private final StringProperty opererationPhrases = new SimpleStringProperty("Unknown");
	private final StringProperty urgencyIndex = new SimpleStringProperty("Unknown");
	private final StringProperty probabilityValue = new SimpleStringProperty("0%");
	private final DoubleProperty progressProbValue = new SimpleDoubleProperty(0);
	
	public ProfileDTO() {
		
	}

	public StringProperty getListOfSourceOrgs() {
		return listOfSourceOrgs;
	}
	
	public void setListOfSourceOrgs(String organisations) {
		this.listOfSourceOrgs.set(organisations);
	}

	public StringProperty getCallerNames() {
		return callerNames;
	}
	
	public void setListOfCallerName(String names) {
		this.callerNames.set(names);
	}

	public BooleanProperty getTaxRelated() {
		return taxRelated;
	}
	
	public void setTaxRelated(boolean status) {
		this.taxRelated.set(status);
	}

	public StringProperty getTaxConfidence() {
		return taxConfidence;
	}
	
	public void setTaxConfidence(String conf) {
		this.taxConfidence.set(conf);
	}

	public DoubleProperty getAmountRequested() {
		return amountRequested;
	}
	
	public void setAmountRequired(Double amount) {
		this.amountRequested.set(amount);
	}

	public StringProperty getPaymentMethods() {
		return paymentMethods;
	}
	
	public void setPaymentMethods(String methods) {
		this.paymentMethods.set(methods);
	}

	public BooleanProperty getArrestThreat() {
		return arrestThreat;
	}
	
	public void setArrestThreat(boolean threat) {
		this.arrestThreat.set(threat);
	}

	public BooleanProperty getPrisonThreat() {
		return prisonThreat;
	}
	
	public void setPrisonThreat(boolean threat) {
		this.prisonThreat.set(threat);
	}

	public BooleanProperty getPrivacyThreat() {
		return privacyThreat;
	}
	
	public void setPrivacyThreat(boolean threat) {
		this.privacyThreat.set(threat);
	}

	public StringProperty getOpererationPhrases() {
		return opererationPhrases;
	}
	
	public void setOperationPhrases(String phrases) {
		this.opererationPhrases.set(phrases);
	}

	public StringProperty getUrgencyIndex() {
		return urgencyIndex;
	}
	
	public void setUrgencyIndex(String value) {
		this.urgencyIndex.set(value);
	}

	public StringProperty getProbabilityValue() {
		return probabilityValue;
	}
	
	public void setProbabilityValue(String value) {
		this.probabilityValue.set(value);
	}

	public DoubleProperty getProgressProbValue() {
		return progressProbValue;
	}
	
	public void setProgressProbValue(Double value) {
		this.progressProbValue.set(value);
	}

	public StringProperty getCurrentLineOfText() {
		return currentLineOfText;
	}
	
	public void setCurrentLineOfText(String phrase) {
		this.currentLineOfText.set(phrase);
	}
	
}
