package profile;

import java.util.ArrayList;
import java.util.List;

import entities.PersonalInfo;
import entities.ThreatEnum;

public class ThreatImp implements Threat {
	
	//public enum ThreatType { ARREST_THREAT, COURT_THREAT, PRIVACY_THREAT};
	
	private List<ThreatEnum> arrestThreat;
	private List<PersonalInfo> privacyThreat;
	//private List<OperationPhrase> courtThreat;
	
	public ThreatImp() {
		this.arrestThreat = new ArrayList<>();
		this.privacyThreat = new ArrayList<>();
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
}
