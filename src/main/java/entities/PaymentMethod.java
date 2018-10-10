package entities;

/**
 * The Payment Method Enum represents entities that correspond to
 * methods of payment. E.g. gift card, tax voucher, bank card etc.
 * @author Pulkit
 *
 */
public enum PaymentMethod {
	BANK_CARD ("bank card"),
	TRANSFER ("transfer"),
	GIFT_CARD ("gift card"),
	TAX_VOUCHER ("tax voucher"),
	DEFAULT ("default");
	
	private String text;

	PaymentMethod(String text) {
		this.text = text;
	}

	public String getText() {
		return this.text;
	}

	public static PaymentMethod fromString(String text) {
		for (PaymentMethod o : PaymentMethod.values()) {
			if (o.text.equalsIgnoreCase(text)) {
				return o;
			}
		}
		throw new IllegalArgumentException("No constant with text " + text + " found");
	}
}
