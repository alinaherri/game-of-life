import java.util.ArrayList;
import java.util.Scanner;

public class Player {
	private String name;
	private boolean attendedCollege;
	private Career playerCareer;
	private ArrayList<LifeToken> playerTokens;
	private House playerHouse;
	private int moneyInBank;
	private int tokenAmount;
	private int loansTaken;
	private int interest;
	private boolean isRetired;
	private boolean isMarried;
	private boolean millionaireEstate;
	private int numKids;
	private int location;
	private int worth;
	
	public Player(String givenName) {
		name = givenName;
		attendedCollege = false;
		location = 0;
		playerCareer = null;
		playerTokens = new ArrayList<LifeToken>();
		playerHouse = null;
		moneyInBank = 100000;
		loansTaken = 0;
		interest = 0;
		isRetired = false;
		isMarried = false;
		numKids = 0;
		worth = 0;
	}
	
	//ACCESSOR METHODS
	public String getName() {
		return name;
	}
	
	public boolean getCollege() {
		return attendedCollege;
	}
	
	public Career getCareer() {
		return playerCareer;
	}
	
	public int getMoneyInBank() {
		String response = "You now have ";
		if (moneyInBank < 0) response += "-$" + Math.abs(moneyInBank) + " in the bank.";
		else response += "$" + moneyInBank + " in the bank.";
		System.out.println(response);
		return moneyInBank;
	}
	
	public int getNumTokens() {
		return playerTokens.size();
	}
	
	public boolean getIsRetired() {
		return isRetired;
	}
	
	public int getWorth() {
		int worth = moneyInBank;
		if(playerHouse != null) sellHouse();
		worth += tokenAmount;
		worth -= loansTaken;
		worth -= interest;
		worth += (numKids * 40000);
		return worth;
	}
	
	public int getLocation() {
		return location;
	}
	
	public House getHouse() {
		return playerHouse;
	}
	
	public ArrayList<LifeToken> getTokens(){
		return playerTokens;
	}
	
	public boolean getRetireLoc() {
		return millionaireEstate;
	}
	
	public int getNumKids() {
		return numKids;
	}
	
	public int getMoney() {
		return moneyInBank;
	}
	//SIMPLE MUTATOR METHODS
	public void goToCollege() {
		attendedCollege = true;
	}
	
	public void changeCareer(Career temp) {
		playerCareer = temp;
	}
	
	public void getMarried() {
		isMarried = true;
	}
	
	public void retire(boolean millionaireEstate) {
		isRetired = true;
		this.millionaireEstate = millionaireEstate;
	}
	
	public void getKid(int num) {
		numKids += num;
	}
	
	public void addSalary() {
		int salary = playerCareer.getSalary();
		System.out.println("PAYDAY: You have been paid $" + salary + ".");
		changeMoney(salary);
	}
	
	public void addLifeToken(LifeToken temp) {
		playerTokens.add(temp);
		int amount = temp.getAmount();
		tokenAmount += amount;
	}
	
	public LifeToken tradeToken() {
		int num = (int)(Math.random() * playerTokens.size());
		LifeToken tempToken = playerTokens.remove(num);
		return tempToken;
	}
	
	//GAME METHODS
	public void move(Scanner console, Board gameBoard) {
		System.out.print("\n" + name + ": ENTER to spin the wheel! (Enter 'loan' to take a loan) " );
		String response = console.next();
		if (response.equals("loan")) {
			System.out.println("You have taken a loan of 20000. A 5000 interest will be paid for each loan received.");
			takeLoan(20000);
		} else {
			int num = (int)(Math.random() * 9) + 1;
			System.out.println("You have spun a: " + num);
			int spacesMove = gameBoard.checkStopSpaces(location, num);
			int finalLoc = gameBoard.checkEnd(location, spacesMove); 
			location = finalLoc;
			System.out.println("You are now at location: " + location);
		}
	}
	
	public void changeLocation(int loc) {
		location = loc;
	}
	
	//REAL ESTATE METHODS
	public void buyHouse(House tempHouse) {
		playerHouse = tempHouse;
		int price = tempHouse.getPrice();
		moneyInBank -= price;
		System.out.println("CONGRATULATIONS, you have bought " + playerHouse.getName() + " for $" + price + "!");
		getMoneyInBank();
	}
	
	public void sellHouse() {
		int randNum = (int)(Math.random()* 9) + 1;
		int sellPrice = playerHouse.getSellingPrice(randNum);
		moneyInBank += sellPrice;
	}
	
	//MONEY METHODS
	public void takeLoan(int amount) {
		moneyInBank += amount;
		loansTaken += amount;
		interest += 5000;
		getMoneyInBank();
	}
	
	public void changeMoney(int amount) {
		moneyInBank += amount;
		getMoneyInBank();
	}
	
	public String toString() {
		String p1 = name;
		String p12 = "\n\t" + playerCareer.toString();
		String p2 = "\n\tLocation: " + location;
		String p3 = "\n\tTokens: " + getNumTokens();
		String p4 = "\n\tCollege: " + attendedCollege + ", Married: " + isMarried + ", Retired: " + isRetired + ", Kids: " + numKids;
		String p5 = "\n\tMoney in Bank: " + moneyInBank;
		return p1 + p12 + p2 + p3 + p4 + p5;
	}
	
}
