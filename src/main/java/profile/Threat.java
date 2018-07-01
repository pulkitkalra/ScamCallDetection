package profile;

import java.util.List;

import entities.PersonalInfo;
import entities.ThreatEnum;

public interface Threat {
	
	void addArrestThreat(ThreatEnum threat);
	void addPrivacyThreat(PersonalInfo info);
	List<ThreatEnum> getArrestThreats();
	List<PersonalInfo> getPrivacyThreats();
}
