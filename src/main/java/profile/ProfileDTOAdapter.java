package profile;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import entities.OperationPhrase;
import entities.Organisation;
import entities.PaymentMethod;
import entities.PersonalInfo;
import entities.ThreatEnum;

public class ProfileDTOAdapter {
	
	private ProfileDTO profileDTO;
	private CallProfile profile;
	
	public ProfileDTOAdapter(CallProfile profile, ProfileDTO dto) {
		this.profile = profile;
		this.profileDTO = dto;
	}
	
	public void updateProfileDTO() {
		this.profileDTO.setListOfCallerName(updateCallerName());
		this.profileDTO.setListOfSourceOrgs(updateOrgList());
		this.profileDTO.setTaxRelated(updateTaxRelated());
		this.profileDTO.setTaxConfidence(updateTaxConfidence());
		this.profileDTO.setAmountRequired(updateAmountRequested());
		this.profileDTO.setPaymentMethods(updatePaymentMethods());
		this.profileDTO.setArrestThreat(updateArrestThreat());
		this.profileDTO.setPrisonThreat(updatePrisonThreat());
		this.profileDTO.setPrivacyThreat(updatePrivacyThreat());
		this.profileDTO.setOperationPhrases(updateOperationList());
		this.profileDTO.setUrgencyIndex(updateUrgencyIndex());
	}
	
	private String updateCallerName() {
		Set<String> callerName = new HashSet<String>(profile.getCallSource().getName());
		String result = "";
		for (String s: callerName) {
			result+= s + "; ";
		}
		return result;
	}
	
	private String updateOrgList() {
		Set<Organisation> orgSet = new HashSet<>(profile.getCallSource().getOrganisations());
		String result = "";
		for (Organisation o: orgSet) {
			result+= o.name()+ "; ";
		}
		return result;
	}
	
	private boolean updateTaxRelated() {
		return profile.getCallReason().getTaxRelated();
	}
	
	private String updateTaxConfidence() {
		return String.valueOf(profile.getCallReason().getTaxConfidence());
	}
	
	private Double updateAmountRequested() {
		List<Double> amounts = profile.getCallAction().getAmountRequested();
		if (!amounts.isEmpty()) {
			return Collections.max(profile.getCallAction().getAmountRequested());
		}
		return 0.0;
	}
	
	private String updatePaymentMethods() {
		Set<PaymentMethod> pm = new HashSet<>(profile.getCallAction().getPaymentMethod());
		if (pm.isEmpty()) return "None";
		String result = "";
		for (PaymentMethod p: pm) {
			result+= p.name() + "; ";
		}
		return result;
	}
	
	private boolean updateArrestThreat() {
		List<ThreatEnum> threatList =  profile.getCallThreat().getArrestThreats();
		if (threatList.contains(ThreatEnum.ARREST)) {
			return true;
		} 
		return false;
	}
	
	private boolean updatePrisonThreat() {
		List<ThreatEnum> threatList =  profile.getCallThreat().getArrestThreats();
		if (threatList.contains(ThreatEnum.PRISON)) {
			return true;
		} 
		return false;
	}
	
	private boolean updatePrivacyThreat() {
		List<PersonalInfo> threatList = profile.getCallThreat().getPrivacyThreats();
		List<PersonalInfo> enums = Arrays.asList(PersonalInfo.values());
		
		if (!Collections.disjoint(threatList, enums)) {
			return true;
		} 
		return false;
	}
	
	private String updateOperationList() {
		Set<OperationPhrase> opSet = new HashSet<>(profile.getCallScamSpecifics().getScamSpecifcPhrases());
		if (opSet.isEmpty()) return "None";
		String result = "";
		for (OperationPhrase op: opSet) {
			result+= op.name()+ "; ";
		}
		return result;
	}
	
	private String updateUrgencyIndex() {
		return String.valueOf(profile.getCallScamSpecifics().getUrgencyIndex());
	}
}
