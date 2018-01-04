package core;

public class GameLogic {

	public static String findLogic(int id, int totalFaceValue,Player currentPlayer) {
		String type = FieldArr[id].getType();
		switch (type) {
		case "normal":
			return NormalLogic.logic(id, currentPlayer);
		case "Brewery":
			return BreweryLogic.logic(id, totalFaceValue, currentPlayer);
		case "Chance":
			//Kald magnuses metode
		break;
		case "Shipping":
			return ShippingLogic.logic(id, totalFaceValue, currentPlayer);
		case "Parking":
			return "Parking";
		case "Prison":
			return PrisonLogic.logic(id, totalFaceValue, currentPlayer);
		case "Tax":
			if(id == 38) {
				if(currentPlayer.getAccount().canAfford(2000)) {
					currentPlayer.getAccount().withdraw(2000);
					return "TaxPrice, "+2000;
				}else {
					//Pantsætning
					return "saleLogic";
				}
			}else {
				//Spørg gui om hvad der skal ske
				return "TaxChoice";
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
