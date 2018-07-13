package profile;

import java.util.List;

import entities.OperationPhrase;
import entities.Organisation;
import entities.ThreatEnum;

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
