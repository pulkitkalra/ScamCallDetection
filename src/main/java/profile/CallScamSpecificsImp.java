package profile;

import java.util.ArrayList;
import java.util.List;

import entities.OperationPhrase;
import entities.Organisation;
import entities.ThreatEnum;

/**
 * The CallScamSpecifics defines state related to specifically to tax-related scams.
 * It captures features of the scam call that are specific to an IRS scam and yet do
 * not fit into any other category of scam.
 * Currently these include: - People claiming calls are recorded and monitored; or 
 * creating urgency.
 * @author Pulkit
 *
 */
public class CallScamSpecificsImp implements CallScamSpecifics {
	
	private int timesCourtMentioned;
	private List<Organisation> organisations;
	private List<ThreatEnum> threats;
	private List<OperationPhrase> scamSpecifcPhrases;
	private int urgencyIndex; 
	
	public CallScamSpecificsImp() {
		this.urgencyIndex = 0;
		this.timesCourtMentioned = 0;
		this.organisations = new ArrayList<>();
		this.threats = new ArrayList<>();
		this.scamSpecifcPhrases = new ArrayList<>();
	}

	@Override
	public int getTimesCourtMentioned() {
		return timesCourtMentioned;
	}

	@Override
	public void incrementCourtMention() {
		this.timesCourtMentioned++;
	}

	@Override
	public List<Organisation> getOrganisations() {
		return organisations;
	}

	@Override
	public void addOrganisation(Organisation o) {
		this.organisations.add(o);
	}

	@Override
	public List<ThreatEnum> getThreats() {
		return threats;
	}

	@Override
	public void addThreat(ThreatEnum t) {
		this.threats.add(t);
	}

	@Override
	public List<OperationPhrase> getScamSpecifcPhrases() {
		return scamSpecifcPhrases;
	}

	@Override
	public void addScamSpecifcPhrase(OperationPhrase op) {
		this.scamSpecifcPhrases.add(op);
	}

	@Override
	public int getUrgencyIndex() {
		return urgencyIndex;
	}

	@Override
	public void incrementUrgencyIndex() {
		this.urgencyIndex++;
	}

}
