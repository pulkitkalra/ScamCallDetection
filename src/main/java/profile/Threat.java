package profile;

import java.util.List;

import entities.Organisation;
import entities.PersonalInfo;
import entities.ThreatEnum;


public interface Threat {
	
	void addArrestThreat(ThreatEnum threat);
	void addPrivacyThreat(PersonalInfo info);
	void addOrganisations(Organisation o); 
	List<ThreatEnum> getArrestThreats();
	List<PersonalInfo> getPrivacyThreats();
	List<Organisation> getOrganisations();
}
