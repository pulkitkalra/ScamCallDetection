package profile;

import java.util.ArrayList;
import java.util.List;

import entities.Organisation;
import entities.PersonalInfo;
import entities.ThreatEnum;

/**
 * The ThreatImp defines state related to threats made in a conversation phrase.
 * It captures any threatening phrases with regards to prison and arrests.
 * These threats can be related to which organisation are making and who the
 * threat is directed towards.
 * @author Pulkit
 *
 */
public class ThreatImp implements Threat {
	
	private List<ThreatEnum> arrestThreat;
	private List<PersonalInfo> privacyThreat;
	private List<Organisation> organisationsInvolved;
	//private List<OperationPhrase> courtThreat;
	
	public ThreatImp() {
		this.arrestThreat = new ArrayList<>();
		this.privacyThreat = new ArrayList<>();
		this.organisationsInvolved = new ArrayList<>(); 
	}
	
	@Override
	public void addArrestThreat(ThreatEnum threat) {
		arrestThreat.add(threat);
	}
	
	@Override
	public void addPrivacyThreat(PersonalInfo info) {
		privacyThreat.add(info);
	}
	
	@Override
	public List<ThreatEnum> getArrestThreats(){
		return arrestThreat;
	}
	
	@Override
	public List<PersonalInfo> getPrivacyThreats(){
		return privacyThreat;
	}

	@Override
	public void addOrganisations(Organisation o) {
		this.organisationsInvolved.add(o);
	}

	@Override
	public List<Organisation> getOrganisations() {
		return organisationsInvolved;
	}
}
