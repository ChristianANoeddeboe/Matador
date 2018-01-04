package core;

public class BuyLogic {
	
	public BuyLogic(int id, Player currentPlayer) {
		propertyBuyLogic(id, currentPlayer);
		houseBuyLogic(id, currentPlayer);
		shippingBuyLogic(id, currentPlayer);
		breweryBuyLogic(id, currentPlayer);
	}
	

	static Entities entities = Entities.getInstance();
	static Normal[] fields = (Normal[]) entities.getFieldArr();
	public String propertyBuyLogic(int id, Player currentPlayer) {
		currentPlayer.getAccount().withdraw(fields[id].getBaseValue());
		fields[id].setOwner(currentPlayer);
		return "Bought";
	}

	public String houseBuyLogic(int id, Player currentPlayer) {
		// Player can buy new house and there is no more than 4 buildings on the field
		if(currentPlayer.getAccount().canAfford(fields[id].getBuildPrice()) && fields[id].getHouseCounter() < 5) {
			currentPlayer.getAccount().withdraw(fields[id].getBuildPrice());
			fields[id].setHouseCounter(fields[id].getHouseCounter() + 1);
			fields[id].setCurrentValue(calcHousePrice(id, fields[id].getHouseCounter()));
			return "HouseBought";
		}
		// There is already a hotel (5 houses) on the field
		else if(fields[id].getHouseCounter() == 5) {
			return "TooManyHouses";
		}
		// The Player cannot afford another house
		else {
			return "CannotAfford, "+fields[id].getBuildPrice();
		}
	}

	// Calculates the new price of rent when a new house has been build
	private int calcHousePrice(int id, int houses) {
		switch (houses) {
		case 1:
			return fields[id].getHouse1Price();
		case 2:
			return fields[id].getHouse2Price();
		case 3:
			return fields[id].getHouse3Price();
		case 4:
			return fields[id].getHouse4Price();
		case 5:
			return fields[id].getHotelPrice();
		default:
			return id;
		}
	}
	//Buy shipping
	public String shippingBuyLogic(int id, Player currentPlayer) {
		int counter = 0;
		currentPlayer.getAccount().withdraw(fields[id].getBaseValue());
		fields[id].setOwner(currentPlayer);
		Property[] fields = (Property[]) entities.getFieldArr(); 
		for (int i = 0; i <= fields.length; i++) {
			if(fields[i].getOwner() == currentPlayer && fields[i].getType().equals("shipping")) {
				counter ++;
			}
		}
		int rent = getShippingValue(counter);
		for (int i = 0; i <= fields.length; i++) {
			if(fields[i].getOwner() == currentPlayer && fields[i].getType().equals("shipping")) {
				fields[i].setCurrentValue(getShippingValue(counter));
			}
		}
		return "Bought";
	}
	//Calculate shipping value dependent on how many a player owns
	public int getShippingValue(int i) {
		switch (i) {
		case 1:
			return 500;
		case 2:
			return 1000;
		case 3:
			return 2000;
		case 4:
			return 4000;
		default:
			return 0;
		}
	}
	//Buy brewery
	public String breweryBuyLogic(int id, Player currentPlayer) {
		currentPlayer.getAccount().withdraw(fields[id].getBaseValue());
		fields[id].setOwner(currentPlayer);
		return "Bought";
	}
	
	public static String unPawnProperty(int id, Player currentPlayer) {
		
	}

}
