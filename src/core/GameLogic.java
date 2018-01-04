package core;

public class GameLogic {
	static Entities entities = Entities.getInstance();
	static Field[] fields = entities.getFieldArr();
	public static String findLogic(int id, int totalFaceValue,Player currentPlayer) {
		
		switch (fields[id].getClass().toString()) {
		case "core.Normal":
			return NormalLogic.logic(id, currentPlayer);
		case "core.Brewery":
			return BreweryLogic.logic(id, totalFaceValue, currentPlayer);
		case "core.Chance":
			//Kald magnuses metode
		break;
		case "core.Shipping":
			return ShippingLogic.logic(id, totalFaceValue, currentPlayer);
		case "core.Parking":
			return "Parking";
		case "core.Prison":
			return PrisonLogic.logic(id, totalFaceValue, currentPlayer);
		case "core.Tax":
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
	
	
	public static void main(String[] args) {
		Player test = new Player("Test", 2);
		System.out.println(test.getAccount().getBalance());
		findLogic(1,12, test);
		System.out.println(test.getAccount().getBalance());
	}	

}
