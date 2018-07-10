package entities;

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
