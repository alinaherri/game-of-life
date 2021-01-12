
public class Space {
	private int amount;
	private String type;
	private String message;
	
	public Space(int amount, String type, String message) {
		this.amount = amount;
		this.type = type;
		this.message = message;
	}
	
	public int getAmount() {
		return amount;
	}
	
	public String getType() {
		return type;
	}
	
	public String getMessage() {
		return message;
	}
	
	public String toString() {
		String p1 = "Type: " + type + "; Amount: " + amount;
		String p2 = "\n\t" + message;
		return p1 + p2;
	}
}
