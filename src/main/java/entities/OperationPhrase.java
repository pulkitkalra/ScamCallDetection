package entities;

/**
 * The operation Phrase Enum represents Entities that correspond to
 * operation phrases. E.g. "disconnect", "record" in sentences such as 
 * "please do not disconnect this line, as it is federally recorded"...
 * @author Pulkit
 *
 */
public enum OperationPhrase {
	DISCONNECT ("disconnect"),
	ON_HOLD ("on hold"),
	RECORD ("record"),
	MUTE ("mute"),
	DEFAULT ("default");

	private String text;

	OperationPhrase(String text) {
		this.text = text;
	}

	public String getText() {
		return this.text;
	}

	public static OperationPhrase fromString(String text) {
		for (OperationPhrase op : OperationPhrase.values()) {
			if (op.text.equalsIgnoreCase(text)) {
				return op;
			}
		}
		throw new IllegalArgumentException("No constant with text " + text + " found");
	}

}
