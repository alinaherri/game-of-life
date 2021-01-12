import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Board {
	//private Space[] boardSpaces;
	//int numSpaces;
	private ArrayList<Player> players;
	private ArrayList<Space> spaces = new ArrayList<Space>();
	private ArrayList<House> houseDeeds = new ArrayList<House>();
	private ArrayList<LifeToken> lifeTokens = new ArrayList<LifeToken>();
	private ArrayList<Career> careers = new ArrayList<Career>();
	private ArrayList<Career> collegeCareers = new ArrayList<Career>();
	
	public Board(ArrayList<Player> players) throws FileNotFoundException {
		this.players = players;
		spaces = boardSpaces();
		houseDeeds = houseDeedsStack();
		lifeTokens = lifeTokenStack();
		careers = careerStack();
		collegeCareers = ccStack();
	}
	
	//Create and initialize all stacks needed to play the game
	//Read information from files, create individual instances, add to arrayList
	public ArrayList<House> houseDeedsStack() throws FileNotFoundException{
		ArrayList<House> houseDeeds = new ArrayList<House>();
		File houseFile = new File ("HouseDeeds");
		Scanner scanHouses = new Scanner (houseFile);
		while (scanHouses.hasNextLine()) {
			String line = scanHouses.nextLine();
			String name = line.substring(0, line.indexOf("/"));
			line = line.substring(line.indexOf("/") + 1);
			Scanner lineScan = new Scanner (line);
			int price = lineScan.nextInt();
			int insurance = lineScan.nextInt();
			int s1 = lineScan.nextInt();
			int s2 = lineScan.nextInt();
			int s3 = lineScan.nextInt();
			String tempString = lineScan.next();
			String description = line.substring(line.indexOf(tempString));
			House temp = new House (name, description, price, insurance, s1, s2, s3);
			houseDeeds.add(temp);
		}
		return houseDeeds;
	}
	
	public ArrayList<LifeToken> lifeTokenStack() throws FileNotFoundException {
		ArrayList<LifeToken> lifeTokens = new ArrayList<LifeToken>();
		File spaceFile = new File ("LIFEMessages");
		Scanner spaceFileScan = new Scanner (spaceFile);
		while (spaceFileScan.hasNextLine()) {
			String line = spaceFileScan.nextLine();
			Scanner lineScan = new Scanner (line);
			int amount = lineScan.nextInt();
			String message = line.substring(6);
			LifeToken temp = new LifeToken (amount, message);
			lifeTokens.add(temp);
		}
		return lifeTokens;
	}
	
	public ArrayList<Career> careerStack() throws FileNotFoundException {
		ArrayList<Career> careers = new ArrayList<Career>();
		File careerFile = new File ("CareerFile");
		Scanner careerScan = new Scanner (careerFile);
		while (careerScan.hasNextLine()) {
			String line = careerScan.nextLine();
			Scanner lineScan = new Scanner (line);
			int salary = lineScan.nextInt();
			String part = lineScan.next();
			String name = line.substring(line.indexOf(part));
			Career temp = new Career (name, salary);
			careers.add(temp);
		}
		return careers;
	}
	
	public ArrayList<Career> ccStack() throws FileNotFoundException {
		ArrayList<Career> collegeCareers = new ArrayList<Career>();
		File ccFile = new File ("CollegeCareers");
		Scanner ccScan = new Scanner (ccFile);
		while (ccScan.hasNextLine()) {
			String line = ccScan.nextLine();
			Scanner lineScan = new Scanner (line);
			int salary = lineScan.nextInt();
			String part = lineScan.next();
			String name = line.substring(line.indexOf(part));
			Career temp = new Career (name, salary);
			collegeCareers.add(temp);
		}
		return collegeCareers;
	}

	public ArrayList<Space> boardSpaces() throws FileNotFoundException{
		ArrayList<Space> spaces = new ArrayList<Space>();
		File boardFile = new File ("SpaceMessages");
		Scanner fileScan = new Scanner (boardFile);
		while (fileScan.hasNextLine()) {
			String line = fileScan.nextLine();
			Scanner lineScan = new Scanner (line);
			int amount = lineScan.nextInt();
			String type = lineScan.next();
			String message = line.substring(line.indexOf(type) + type.length() + 1);
			Space temp = new Space (amount, type, message);
			spaces.add(temp);
		}
		return spaces;
	}
	
	public void playerTurn(Player tempPlayer, Scanner console) {
		int location = tempPlayer.getLocation();
		Space playerSpace = spaces.get(location);
		String type = playerSpace.getType();
		
		//regular space
		if (type.equals("yellow")) {
			System.out.println(playerSpace.getMessage());
			int amount = playerSpace.getAmount();
			tempPlayer.changeMoney(amount);
			
		//draw a life token
		} else if (type.equals("LIFE")) {
			System.out.println(playerSpace.getMessage());
			generateToken(console, tempPlayer);
		
		//payday!
		} else if (type.equals("green")) tempPlayer.addSalary();
		
		//choose a career
		else if (type.equals("careerChoice")) assignCC(console, tempPlayer);
		
		//get married
		else if (type.equals("married")) marry(console, tempPlayer);
			
		//buy a house
		else if (type.equals("house")) assignHouse(console, tempPlayer);
			
		//selling a house, buying a new one
		else if (type.equals("sellHouse")) {
			System.out.println(playerSpace.getMessage());
			sellHouse(console, tempPlayer);
			assignHouse(console, tempPlayer);
			
		//baby cases
		} else if (type.equals("babyBoy")) {
			System.out.println("CONGRATULATIONS, you now have a baby boy!");
			generateToken(console, tempPlayer);
			tempPlayer.getKid(1);
		} else if (type.equals("babyGirl")) {
			System.out.println("CONGRATULATIONS, you now have a baby girl!");
			tempPlayer.getKid(1);
			generateToken(console, tempPlayer);
		} else if (type.equals("twins")) {
			System.out.println("CONGRATULATIONS, you now have twins!");
			tempPlayer.getKid(2);
			generateToken(console, tempPlayer);
		
		//child costs + college
		} else if (type.equals("childCost")) {
			System.out.println(playerSpace.getMessage());
			int total = tempPlayer.getNumKids() * 5000;
			tempPlayer.changeMoney(-1 * total);
		} else if (type.equals("college")) {
			System.out.println(playerSpace.getMessage());
			int total = tempPlayer.getNumKids() * 50000;
			tempPlayer.changeMoney(-1 * total);
		
		//pension
		} else if (type.equals("pension")) {
			System.out.println(playerSpace.getMessage());
			pension(console, tempPlayer);
		
		//retirement
		} else if (type.equals("red")) retire(console, tempPlayer);
		
		else System.out.println("ERROR");
	}
	
	//individual case methods
	public void marry(Scanner console, Player tempPlayer) {
		System.out.print("ENTER to tie the knot! ");
		String enter = console.next();
		System.out.println("CONGRATULATIONS, you are now married!");
		tempPlayer.getMarried();
		generateToken(console, tempPlayer);
		System.out.println(tempPlayer);
	}
	
	public void sellHouse(Scanner console, Player tempPlayer) {
		System.out.print("ENTER to spin a number! ");
		int randNum = (int)(Math.random() * 9) + 1;
		String enter = console.next();
		System.out.println("You have spun a: " + randNum);
		House tempHouse = tempPlayer.getHouse();
		int sellingPrice = tempHouse.getSellingPrice(randNum);
		System.out.println("The selling price of your " + tempHouse.getName() + " is: " + sellingPrice);
		tempPlayer.changeMoney(-1 * sellingPrice);
	}
	
	public void pension(Scanner console, Player tempPlayer) {
		System.out.println("ENTER to spin! ");
		String enter = console.next();
		int randNum = (int)(Math.random() * 9) + 1;
		System.out.println("You have collected " + (randNum * 20000) + " pension.");
		tempPlayer.changeMoney(randNum * 20000);
	}
	
	public void retire(Scanner console, Player tempPlayer) {
		System.out.print("Would you like to go to (1) Millionaire Estates or (2) Countryside Acres? ");
		int num = console.nextInt();
		if (num == 1) {
			tempPlayer.retire(true);
		} else {
			tempPlayer.retire(false);
			generateToken(console, tempPlayer);
		}
	}
	
	//Checks to stop at designated STOP spaces
	public int checkStopSpaces(int location, int num) {
		int numSpaces = 0;
		boolean hasStopSpace = false;
		int count = 1;
		while (!hasStopSpace && count <= num) {
			Space tempSpace = spaces.get(location + count);
			String type = tempSpace.getType();
			if (!type.equals("green") && !type.equals("yellow") && !type.equals("purple") && !type.equals("LIFE")) hasStopSpace = true;
			else count++;
		}
		if (!hasStopSpace) numSpaces = num;
		else numSpaces = count;
		return numSpaces;
	}
	
	public int checkEnd(int location, int spacesMoved) {
		int total = location + spacesMoved;
		int finalLoc = 0;
		if (total < spaces.size()) finalLoc = total;
		else finalLoc = spaces.size() - 1;
		return finalLoc;
 	}
	//Randomly select career or house, marked as taken or own
	public void assignCareer(Scanner console, Player tempPlayer) {
		System.out.print("ENTER to draw a Career! ");
		String enter = console.next();
		
		int num = (int)(Math.random() * careers.size());
		Career tempCareer = careers.get(num);
		while (tempCareer.getIsTaken()) {
			num = (int)(Math.random() * careers.size());
			tempCareer = careers.get(num);
		}
		tempCareer.takeCareer();
		Career playerCareer = tempCareer;
		tempPlayer.changeCareer(playerCareer);
		
		System.out.println("Congratulations " + tempPlayer.getName() + ", you are now a " + playerCareer.getName() + "!");
		System.out.println(tempPlayer);
	}
	
	public void assignCC(Scanner console, Player tempPlayer) {
		System.out.print("ENTER to draw a College Career! ");
		String enter = console.next();
		
		int num = (int)(Math.random() * collegeCareers.size());
		Career tempCareer = collegeCareers.get(num);
		while (tempCareer.getIsTaken()) {
			num = (int)(Math.random() * collegeCareers.size());
			tempCareer = collegeCareers.get(num);
		}
		tempCareer.takeCareer();
		Career playerCareer = tempCareer;
		tempPlayer.changeCareer(playerCareer);
		
		System.out.println("Congratulations " + tempPlayer.getName() + ", you are now a " + playerCareer.getName() + "!");
		System.out.println(tempPlayer);
	}
	
	public void assignHouse(Scanner console, Player tempPlayer) {
		System.out.print("ENTER to draw a house! ");
		String enter = console.next();
		int num = (int)(Math.random() * houseDeeds.size());
		House tempHouse = houseDeeds.get(num);
		while (tempHouse.getIsOwned()) {
			num = (int)(Math.random() * houseDeeds.size());
			tempHouse = houseDeeds.get(num);
		}
		tempHouse.boughtHouse();
		tempPlayer.buyHouse(tempHouse);
	}
	
	//Randomly select token, remove from the arrayList
	public void generateToken(Scanner console, Player tempPlayer) {
		System.out.print("ENTER to draw a LIFE token! ");
		String enter = console.next();
		
		LifeToken tempToken = null;
		if (lifeTokens.size() > 0) {
			int num = (int)(Math.random() * lifeTokens.size());
			tempToken = lifeTokens.remove(num);
		} else {
			System.out.println("Which player do you want to steal a LifeToken from? (Need to be retired) ");
			String response = console.next();
			boolean chosen = false;
			int count = 0;
			while (!chosen && count < players.size()) {
				Player otherPlayer = players.get(count);
				String name = otherPlayer.getName();
				if (name.equals(response)) {
					chosen = true;
					boolean millionaire = otherPlayer.getRetireLoc();
					if (millionaire) {
						tempToken = otherPlayer.tradeToken();
					} else count++;
				} else count++;
			}
		}
		tempPlayer.addLifeToken(tempToken);
		System.out.println(tempToken);
	}

	//PRINT METHODS
	public void printSpaces() {
		for (Space temp : spaces) {
			System.out.println(temp);
		}
	}
	
	public void printHouseDeeds() {
		for (House temp : houseDeeds) {
			System.out.println(temp);
		}
	}
	
	public void printLifeTokens() {
		for (LifeToken temp : lifeTokens) {
			System.out.println(temp);
		}
	}
	
	public void printCareers() {
		for (Career temp : careers) {
			System.out.println(temp);
		}
	}
	
	public void printCC() {
		for (Career temp : collegeCareers) {
			System.out.println(temp);
		}
	}
}
