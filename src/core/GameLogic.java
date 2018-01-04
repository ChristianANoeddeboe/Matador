package core;

public class GameLogic {

	public static int basicOutcome(int id, Player currentPlayer) {
		String type = "tax2";//FieldArr[id].getType();
		switch (type) {
		case "tax1":
			return id;
		case "tax2":
			currentPlayer.getAccount().withdraw(2000);
			return id;
		default:
			System.out.println("Error on " + id);
			break;
		}
		return id;
	}


	public static int advanceOutcome(int id, Player currentPlayer) {
		
		return id;
	}
	
	
	public static int findLogic(int id, Player currentPlayer) {
		switch (type) {
		case "normal":
			NormalLogic
			break;

		default:
			break;
		}
	}
	
	
	public static void main(String[] args) {
		Player test = new Player("Test", 2);
		System.out.println(test.getAccount().getBalance());
		basicOutcome(1, test);
		System.out.println(test.getAccount().getBalance());
	}	

}
