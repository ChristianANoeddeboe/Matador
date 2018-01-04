package core;

public class GameLogic {

	public static int findLogic(int id, int totalFaceValue,Player currentPlayer) {
		String type = fieldArr[id].getType();
		switch (type) {
		case "normal":
			NormalLogic.logic(id, currentPlayer);
			break;
		case "Brewery":
			BreweryLogic.logic(id, totalFaceValue, currentPlayer);
			break;
		case "Chance":
			//Kald magnuses metode
		break;
		case "Shipping":
			
			break;
		default:
			break;
		}
	}
	
	
	public static void main(String[] args) {
		Player test = new Player("Test", 2);
		System.out.println(test.getAccount().getBalance());
		findLogic(1, test);
		System.out.println(test.getAccount().getBalance());
	}	

}
