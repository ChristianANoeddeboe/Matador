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
			ShippingLogic.logic(id, totalFaceValue, currentPlayer);
			break;
		case "Parking":
			break;
		case "Prison":
			break;
		case "Tax":
			if(id == 38) {
				if(currentPlayer.getAccount().canAfford(2000)) {
					currentPlayer.getAccount().withdraw(2000);
				}else {
					//Pantsætning
				}
			}else {
				//Spørg gui om hvad der skal ske
			}
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
