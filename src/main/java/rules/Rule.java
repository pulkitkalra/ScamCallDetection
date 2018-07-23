package rules;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.google.protobuf.Struct;

import entities.Organisation;
import entities.PaymentMethod;
import entities.PersonalInfo;
import entities.ThreatEnum;
import scallCallDetection.DFEntity;

public abstract class Rule {
	
	public enum MapType {
		ORGANISATION_MAP, THREAT_MAP, PAYMENT_MAP, CURRENCY, NUMBER,
		NAME, PRIVACY_MAP, TAX,
		DEFAULT // DELETE THIS
	}
	
	private static final Map<String, Organisation> organisationMap;
	static {
		Map<String, Organisation> map = new HashMap<>();
		map.put("IRS", Organisation.IRS);
		map.put("GovernmentEntity", Organisation.GOVERNMENT_ENTITY);
		
		organisationMap = Collections.unmodifiableMap(map);
	}
	
	private static final Map<String, ThreatEnum> threatMap;
	static {
		Map<String, ThreatEnum> map = new HashMap<>();
		map.put("Arrest", ThreatEnum.ARREST);
		map.put("Prison", ThreatEnum.PRISON);
		
		threatMap = Collections.unmodifiableMap(map);
	}
	
	private static final Map<String, PaymentMethod> pmMap;
	static {
		Map<String, PaymentMethod> map = new HashMap<>();
		map.put("gift card", PaymentMethod.GIFT_CARD);
		map.put("tax voucher", PaymentMethod.TAX_VOUCHER);
		map.put("bank card", PaymentMethod.BANK_CARD);
		map.put("transfer", PaymentMethod.TRANSFER);
		
		pmMap = Collections.unmodifiableMap(map);
	}
	
	private static final Map<String, PersonalInfo> privateInfoMap;
	static {
		Map<String, PersonalInfo> map = new HashMap<>();
		map.put("identitity", PersonalInfo.IDENTITY);
		map.put("belongings", PersonalInfo.BELONGINGS);
		map.put("social_security", PersonalInfo.SOCIAL_SECURITY);
		map.put("bank", PersonalInfo.BANK);
		map.put("credentials", PersonalInfo.CREDENTIALS);

		privateInfoMap = Collections.unmodifiableMap(map);
	}
	
	
	public abstract void applyRule(DFEntity entity);
	
	public MapType getMapType(String entityName) {
		switch (entityName) {
			case "IRS": case "GovernmentEntity": case "Court":
				return MapType.ORGANISATION_MAP;
			case "PaymentMethods":
				return MapType.PAYMENT_MAP;
			case "unit-currency":
				return MapType.CURRENCY;
			case "number":
				return MapType.NUMBER;
			case "Arrest": case "Prison":
				return MapType.THREAT_MAP;
			case "Personal_Information":
				return MapType.PRIVACY_MAP;
			case "Tax":
				return MapType.TAX;
			default:
				return MapType.DEFAULT;
		}
	}
	
	public Double getPaymentAmount(DFEntity ent) {
		Struct s = ent.getEntityValue().getStructValue();
		Double amount = s.getFieldsMap().get("amount").getNumberValue();
		return amount;
	}
	
	public Map<String, Organisation> getOrganisationMap() {
		return organisationMap;
	}

	public Map<String, PaymentMethod> getPaymentMethodMap() {
		return pmMap;
	}

	public Map<String, ThreatEnum> getThreatMap() {
		return threatMap;
	}

	public Map<String, PersonalInfo> getPrivateInfoMap() {
		return privateInfoMap;
	}

}
