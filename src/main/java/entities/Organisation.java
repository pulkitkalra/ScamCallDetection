package entities;

/**
 * The Organisation Enum represents Entities that correspond to
 * real-world ogranisation. E.g. IRS, IRD, Court etc. 
 * @author Pulkit
 */
public enum Organisation {
	IRS ("IRS"),
	GOVERNMENT_ENTITY ("GovernmentEntity"),
	COURTHOUSE ("court"),
	COURT_OFFICIAL ("official");
	
	private String text;

	Organisation(String text) {
		this.text = text;
	}

	public String getText() {
		return this.text;
	}

	public static Organisation fromString(String text) {
		for (Organisation o : Organisation.values()) {
			if (o.text.equalsIgnoreCase(text)) {
				return o;
			}
		}
		throw new IllegalArgumentException("No constant with text " + text + " found");
	}
}
