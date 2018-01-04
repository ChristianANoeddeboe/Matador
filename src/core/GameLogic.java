package core;

public class GameLogic {
	
	public GameLogic(int id, int totalFaceValue, Player currentPlayer) {
		findLogic(id, totalFaceValue, currentPlayer);
	}
	static Entities entities = Entities.getInstance();
	static Field[] fields = entities.getFieldArr();
	public String findLogic(int id, int totalFaceValue,Player currentPlayer) {
		
		String type = fields[id].getType();
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
				TaxLogic.taxLogic38(currentPlayer);
				
			}else {
				//Sp�rg gui om hvad der skal ske
				return "TaxChoice";
			}
		default:
			break;
		}
		return "Type not found";
	}
}
