
public class LifeToken {
	private int amount;
	private String message;
	
	public LifeToken(int amount, String message) {
		this.amount = amount;
		this.message = message;
	}
	
	public int getAmount() {
		return amount;
	}
	
	public String getMessage() {
		return message;
	}
	
	public String toString() {
		return "LIFE TOKEN: " + message;
	}
}
