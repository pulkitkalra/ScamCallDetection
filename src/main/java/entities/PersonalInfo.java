package entities;

/**
 * The Personal Information Enum represents entities that correspond to
 * sensitive and confidential user information. E.g. name, number, address
 * and anything else identity related. 
 * @author Pulkit
 *
 */
public enum PersonalInfo {
	IDENTITY ("identity"),
	SOCIAL_SECURITY ("social security"),
	BANK ("bank"),
	BELONGINGS ("belongings"),
	CREDENTIALS ("credentials"),
	DEFAULT ("default");
	
	private String text;

	PersonalInfo(String text) {
		this.text = text;
	}

	public String getText() {
		return this.text;
	}

	public static PersonalInfo fromString(String text) {
		for (PersonalInfo o : PersonalInfo.values()) {
			if (o.text.equalsIgnoreCase(text)) {
				return o;
			}
		}
		throw new IllegalArgumentException("No constant with text " + text + " found");
	}
}
