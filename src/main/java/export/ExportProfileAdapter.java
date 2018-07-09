package export;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import entities.OperationPhrase;
import entities.Organisation;
import entities.PaymentMethod;
import entities.PersonalInfo;
import entities.ThreatEnum;
import profile.CallProfile;

public class ExportProfileAdapter {
	private static final int ATTRIBUTE_NUMBER = 12;
	private CallProfile profile;
	
	public ExportProfileAdapter(CallProfile profile) {
		this.profile = profile;
	}
	
	/**
	 * Profile List FORMAT: 
	 * @return
	 */
	public String[] getProfileList(){
		String[] result = new String[ATTRIBUTE_NUMBER];
		
		result[0] = getIRSStatus();
		result[1] = getTaxRelatedValue();
		result[2] = getTaxConfidenceValue();
		result[3] = getArrestValue();
		result[4] = getPrisonValue();
		result[5] = getPrivacyIdentityValue();
		result[6] = getPrivacyBankValue();
		result[7] = getAmountRequestedValue();
		result[8] = getPaymentMethodValue();
		result[9] = getScamSignalsValue();
		result[10] = getTimesCourtMentionedValue();
		result[11] = getUrgencyIndexValue();
		
		return result;
	}
	
	private String getIRSStatus() {
		List<Organisation> orgList = profile.getCallSource().getOrganisations();
		if (orgList.contains(Organisation.IRS)) {
			return IRSState.IRS.toString();
		} else if (orgList.contains(Organisation.GOVERNMENT_ENTITY)) {
			return IRSState.MAYBEIRS.toString();
		} else {
			return IRSState.NOTIRS.toString();
		}
	}
	
	private String getTaxRelatedValue() {
		return String.valueOf(profile.getCallReason().getTaxRelated());
	}
	
	private String getTaxConfidenceValue() {
		int conf = profile.getCallReason().getTaxConfidence();
		return getNomimalValue(conf);
	}
	
	private String getArrestValue() {
		List<ThreatEnum> threatList = profile.getCallThreat().getArrestThreats();
		if (threatList.contains(ThreatEnum.ARREST)) {
			return String.valueOf(true);
		} else {
			return String.valueOf(false);
		}
	}
	
	private String getPrisonValue() {
		List<ThreatEnum> threatList = profile.getCallThreat().getArrestThreats();
		if (threatList.contains(ThreatEnum.PRISON)) {
			return String.valueOf(true);
		} else {
			return String.valueOf(false);
		}
	}
	
	private String getPrivacyIdentityValue() {
		List<PersonalInfo> threatList = profile.getCallThreat().getPrivacyThreats();
		if (threatList.contains(PersonalInfo.IDENTITY) || threatList.contains(PersonalInfo.CREDENTIALS) || threatList.contains(PersonalInfo.SOCIAL_SECURITY)) {
			return String.valueOf(true);
		} else {
			return String.valueOf(false);
		}
	}
	
	private String getPrivacyBankValue() {
		List<PersonalInfo> threatList = profile.getCallThreat().getPrivacyThreats();
		if (threatList.contains(PersonalInfo.BANK) || threatList.contains(PersonalInfo.BELONGINGS)) {
			return String.valueOf(true);
		} else {
			return String.valueOf(false);
		}
	}
	
	private String getAmountRequestedValue() {
		List<Double> amountList = profile.getCallAction().getAmountRequested();
		if (amountList.isEmpty()) return NominalValue.NONE.toString();
		Double value = Collections.max(amountList);
		if (value < 100.0) {
			return NominalValue.NONE.toString();
		} else if (value >= 100.0 && value < 1000.0) {
			return NominalValue.LOW.toString();
		}  else if (value >= 1000.0 && value < 10000.0) {
			return NominalValue.MEDIUM.toString();
		} else {
			return NominalValue.HIGH.toString();
		}
	}
	
	private String getPaymentMethodValue() {
		List<PaymentMethod> pmList = profile.getCallAction().getPaymentMethod();
		if (pmList.isEmpty()) {
			return NominalValue.NONE.toString();
		}
		// Calculate mode from list of payment methods:
		Map<PaymentMethod, Integer> pmMap = new HashMap<>();
		for (PaymentMethod pm : PaymentMethod.values()) {
			pmMap.put(pm, Collections.frequency(pmList, pm));
		}
		
		Entry<PaymentMethod, Integer> max = null;
	    for (Entry<PaymentMethod, Integer> e : pmMap.entrySet()) {
	        if (max == null || e.getValue() > max.getValue()) {
	        	max = e;
	        } 
	    }
	    
	    PaymentMethod mode = max.getKey();
		return mode.toString();
	}
	
	private String getScamSignalsValue() {
		List<OperationPhrase> scamList = profile.getCallScamSpecifics().getScamSpecifcPhrases();
		if (scamList.isEmpty()) {
			return NominalValue.NONE.toString();
		} else if (scamList.size() == 1) {
			return scamList.get(0).toString();
		} else {
			return "MULTIPLE";
		}
	}
	
	private String getTimesCourtMentionedValue() {
		return getNomimalValue(profile.getCallScamSpecifics().getTimesCourtMentioned());
	}
	
	private String getUrgencyIndexValue() {
		return getNomimalValue(profile.getCallScamSpecifics().getUrgencyIndex());
	}
	
	private String getNomimalValue(int switchVar) {
		if (switchVar < 1) {
			return NominalValue.NONE.toString();
		} else if (switchVar >= 1 && switchVar < 3) {
			return NominalValue.LOW.toString();
		}  else if (switchVar >= 3 && switchVar < 5) {
			return NominalValue.MEDIUM.toString();
		} else {
			return NominalValue.HIGH.toString();
		}
	}

}
