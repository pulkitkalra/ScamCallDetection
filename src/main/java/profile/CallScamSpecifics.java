package profile;

import java.util.List;

import entities.OperationPhrase;
import entities.Organisation;
import entities.ThreatEnum;

/**
 * The CallScamSpecific abstraction enforces behavior related to specific features of an IRS scam.
 * This includes states and behaviours that do not fit into any other existing categories.
 * @author Pulkit
 *
 */
public interface CallScamSpecifics {
	
	int getTimesCourtMentioned();
	void incrementCourtMention();
	List<Organisation> getOrganisations();
	void addOrganisation(Organisation o);
	List<ThreatEnum> getThreats();
	void addThreat(ThreatEnum t);
	List<OperationPhrase> getScamSpecifcPhrases();
	void addScamSpecifcPhrase(OperationPhrase op);
	int getUrgencyIndex();
	void incrementUrgencyIndex();
	
}
