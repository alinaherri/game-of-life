
public class House {
	
	private String name;
	private int price;
	private String description;
	private int insurance;
	private int sellingPrice1;
	private int sellingPrice2;
	private int sellingPrice3;
	private boolean isOwned;
	
	public House(String name, String description, int price, int insurance, int s1, int s2, int s3) {
		this.name = name;
		this.description = description;
		this.price = price;
		this.insurance = insurance;
		sellingPrice1 = s1;
		sellingPrice2 = s2;
		sellingPrice3 = s3;
		isOwned = false;
	}
	
	//ACCESSOR METHODS
	public String getName() {
		return name;
	}
	
	public boolean getIsOwned() {
		return isOwned;
	}

	public int getSellingPrice(int num) {
		if (num < 5) return sellingPrice1;
		else if (num < 9) return sellingPrice2;
		else return sellingPrice3;
	}
	
	public int getPrice() {
		return price;
	}
	
	//set isOwned to true
	public void boughtHouse() {
		isOwned = true;
	}
	
	public String toString() {
		String p1 = name + ": $" + price + "\n";
		String p2 = "\t" + description;
		String p3 = "\n\tInsurance: " + insurance;
		String p4 = "\n\tSelling Prices: " + sellingPrice1 + ", " + sellingPrice2 + ", " + sellingPrice3;
		return p1 + p2 + p3 + p4;
		
	}

}
