import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class LifeClient {

	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		
		Scanner console = new Scanner (System.in);
	
		System.out.print("How many players? ");
		int numPlayers = console.nextInt();
		ArrayList<Player> players = createPlayers(numPlayers, console);

		Board gameBoard = new Board(players);
		
		boolean done = false;
		
		for (int i = 0; i < players.size(); i++) {
			Player tempPlayer = players.get(i);
			firstMove(gameBoard, tempPlayer, console);
		}
		
		while (!done) {
			for (int i = 0; i < players.size(); i++) {
				Player tempPlayer = players.get(i);
				if (!tempPlayer.getIsRetired()) {
					if (tempPlayer.getMoney() > 0) {
						tempPlayer.move(console, gameBoard);
						gameBoard.playerTurn(tempPlayer, console);
					}
					else {
						System.out.print("Since your balance is currently a negative balance, ENTER to take a loan. ");
						tempPlayer.takeLoan(20000);
					}
				}
			}
			done = allPlayersRetired(players);
		}
		//getWinner(players);
		System.out.print("Thank you for playing GAME OF LIFE. Come back soon!");
	}
	
	//print all the stacks of a given board to check for validity
	public static void printChecks(Board gameBoard, ArrayList<Player> players) {
		System.out.println("\nSPACES");
		gameBoard.printSpaces();
		
		System.out.println("\nHOUSE DEEDS");
		gameBoard.printHouseDeeds();
		
		System.out.println("\nLIFE TOKENS");
		gameBoard.printLifeTokens();
		
		System.out.println("\nCAREERS");
		gameBoard.printCareers();
		
		System.out.println("\nCOLLEGE CAREERS");
		gameBoard.printCC();
		
		System.out.println("\nPLAYERS");
		for (Player temp : players) {
			System.out.println(temp);
		}
	}
	
	//return the winning player of a given game, based on the highest net worth
	public static void getWinner(ArrayList<Player> players) {
		int maxNetWorth = Integer.MIN_VALUE;
		Player winner = null;
		for (int i = 0; i < players.size(); i++) {
			Player temp = players.get(i);
			int netWorth = temp.getWorth();
			if (netWorth > maxNetWorth) {
				maxNetWorth = netWorth;
				winner = temp;
			}
		}
		System.out.println(winner.getName() + " is the winner with a net worth of " + maxNetWorth + "!");
	}
	
	//allow all players to choose whether to go the COLLEGE route or CAREER route
	public static void firstMove(Board gameBoard, Player tempPlayer, Scanner console) {
		System.out.println("\n" + tempPlayer.getName() + ": do you want to START CAREER (1) or START COLLEGE? (2)");
		System.out.print("Response: ");
		int choice = console.nextInt();
		
		//Choose a career
		if (choice == 1) {
			gameBoard.assignCareer(console, tempPlayer);
			tempPlayer.changeLocation(10);
			
		//Choose college
		} else {
			System.out.println("CONGRATULATIONS, " + tempPlayer.getName() + ", for choosing COLLEGE!");
			System.out.println("You have borrowed $100,000 from the bank for tuition.");
			tempPlayer.changeMoney(-100000);
			tempPlayer.takeLoan(50000);
			tempPlayer.goToCollege();
			Career unlisted = new Career ("UNLISTED");
			tempPlayer.changeCareer(unlisted);
			System.out.println(tempPlayer);
		}
	}
	
	//initialize all players with name provided by user input
	public static ArrayList<Player> createPlayers(int numPlayers, Scanner console) {
		ArrayList<Player> players = new ArrayList<Player>();
		for (int i = 1; i <= numPlayers; i++) {
			System.out.print("What is Player " + i + "'s name? ");
			String playerName = console.next();
			Player temp = new Player (playerName);
			players.add(temp);
		}
		return players;
	}
	
	//check to ensure that at least one player is still in the game/hasn't reached the end
	public static boolean allPlayersRetired(ArrayList<Player> players) {
		int countRetired = 0;
		for (int i = 0; i < players.size(); i++) {
			Player temp = players.get(i);
			if (temp.getIsRetired()) countRetired++;
		}
		boolean allRetired = false;
		if (countRetired == players.size()) allRetired = true;
		return allRetired;
	}

}
