package rules;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import entities.Organisation;
import entities.PaymentMethod;
import scallCallDetection.DFEntity;

public abstract class Rule {
	
	public enum MapType {
		ORGANISATION_MAP, THREAT_MAP, PAYMENT_MAP, CURRENCY, NUMBER,
		DEFAULT
	}
	
	private static final Map<String, Organisation> organisationMap;
	static {
		Map<String, Organisation> map = new HashMap<>();
		map.put("IRS", Organisation.IRS);
		map.put("GovernmentEntity", Organisation.GOVERNMENT_ENTITY);
		
		organisationMap = Collections.unmodifiableMap(map);
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
	
	public abstract void applyRule(DFEntity entity);
	
	public MapType getMapType(String entityName) {
		switch (entityName) {
			case "IRS": case "GovernmentEntity":
				return MapType.ORGANISATION_MAP;
			case "PaymentMethods":
				return MapType.PAYMENT_MAP;
			case "unit-currency":
				return MapType.CURRENCY;
			case "number":
				return MapType.NUMBER;
			default:
				return MapType.DEFAULT;
		}
	}

	public static Map<String, Organisation> getOrganisationMap() {
		return organisationMap;
	}

	public static Map<String, PaymentMethod> getPaymentMethodMap() {
		return pmMap;
	}

}
