package core;

public class GameLogic {
	NormalLogic normalLogic;
	BreweryLogic breweryLogic;
	ShippingLogic shippingLogic;
	PrisonLogic prisonLogic;
	TaxLogic taxLogic;
	BuyLogic buyLogic;
	SalesLogic salesLogic;
	
	
	public GameLogic(int id, int totalFaceValue, Player currentPlayer,int dice1value, int dice2value, int choice) {
		findLogic(id, totalFaceValue, currentPlayer, dice1value, dice2value, choice);
		normalLogic = new NormalLogic(id, currentPlayer);
		breweryLogic = new BreweryLogic(id, totalFaceValue, currentPlayer);
		shippingLogic = new ShippingLogic(id, totalFaceValue, currentPlayer);
		prisonLogic = new PrisonLogic(id, totalFaceValue, currentPlayer, dice1value, dice2value);
		taxLogic = new TaxLogic(id, currentPlayer, choice);
		buyLogic = new BuyLogic(id, currentPlayer);
		salesLogic = new SalesLogic(id, currentPlayer);
		
	}
	
	
	static Entities entities = Entities.getInstance();
	static Field[] fields = entities.getFieldArr();
	
	public String findLogic(int id, int totalFaceValue,Player currentPlayer, int dice1value, int dice2value, int choice) {
		

		
		
		String type = fields[id].getType();
		switch (type) {
		case "normal":
			return normalLogic.logic(id, currentPlayer);
		case "Brewery":
			return breweryLogic.logic(id, totalFaceValue, currentPlayer);
		case "Chance":
			//Kald magnuses metode
		break;
		case "Shipping":
			return shippingLogic.logic(id, totalFaceValue, currentPlayer);
		case "Parking":
			return "Parking";
		case "Prison":
			return prisonLogic.logic(id, totalFaceValue, currentPlayer);
		case "Tax":
			if(id == 38) {
				taxLogic.taxLogic38(currentPlayer);
				
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
