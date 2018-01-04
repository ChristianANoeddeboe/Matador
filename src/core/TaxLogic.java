package core;

public class TaxLogic {
	static Entities entities = Entities.getInstance();
	static Property[] fields = (Property[]) entities.getFieldArr();
	public TaxLogic(int id, Player currentPlayer, int choice) {
		if(id == 38) {
		taxLogic38(currentPlayer);
		}
		else {
			taxLogic4(currentPlayer, choice);
		}
	}
	
	
	public String taxLogic38(Player currentPlayer) {
		if(currentPlayer.getAccount().canAfford(2000)) {
			currentPlayer.getAccount().withdraw(2000);
			return "TaxPrice, "+2000;
		}else {
			//Pants�tning
			return "saleLogic";
		}
	}

	public String taxLogic4(Player currentPlayer, int choice) {
		int buildingvalue = 0;
		int playervalue = currentPlayer.getAccount().getBalance();
		int propertyvalue = 0;
		if(choice == 0) {
			for (int i = 0; i <= fields.length; i++) {
				if(fields[i].getOwner() == currentPlayer) {
					propertyvalue = propertyvalue + fields[i].getBaseValue();
					if(fields[i].getType() == "Normal") {
						Normal[] fields = (Normal[]) entities.getFieldArr();
						for (int j = 0; j < fields[i].getHouseCounter(); j++) {
							buildingvalue = buildingvalue + fields[i].getHousePrices()[i];
						}
					}
					
				}
			}
			return "TaxPrice,"+((int) ((buildingvalue + playervalue + propertyvalue)*0.10));
		}else {
			if(currentPlayer.getAccount().canAfford(4000)) {
				currentPlayer.getAccount().withdraw(4000);
				return "TaxPrice,"+4000;
			}
			return "SalesLogic";
		}
	}
}
