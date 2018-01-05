package core;
/**
 * 
 * @author Mathias Thejsen - Thejsen@live.dk && Simon Fritz
 *
 */
public class BuyLogic {
	static Entities entities = Entities.getInstance();
	static Normal[] fields = (Normal[]) entities.getFieldArr();
	/**
	 * Constructor for the buy logic
	 * @param id
	 * @param currentPlayer
	 */
	public BuyLogic(int id, Player currentPlayer) {
		propertyBuyLogic(id, currentPlayer);
		houseBuyLogic(id, currentPlayer);
		shippingBuyLogic(id, currentPlayer);
		breweryBuyLogic(id, currentPlayer);
		unPawnProperty(id, currentPlayer);
	}

	/**
	 * Logic for buying a property
	 * @param id
	 * @param currentPlayer
	 * @return
	 */
	public String propertyBuyLogic(int id, Player currentPlayer) {
		currentPlayer.getAccount().withdraw(fields[id].getBaseValue()); // Withdraw money form player based on the property base value
		fields[id].setOwner(currentPlayer); // Set the owner
		return "Bought";
	}
	/**
	 * Buying a house logic
	 * @param id
	 * @param currentPlayer
	 * @return
	 */
	public String houseBuyLogic(int id, Player currentPlayer) {
		// Player can buy new house and there is no more than 4 buildings on the field
		if(currentPlayer.getAccount().canAfford(fields[id].getBuildPrice()) && fields[id].getHouseCounter() <= 5) { // Check if player can afford house and making sure there is not already 5
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
	/**
	 * 
	 * @param id
	 * @param houses
	 * @return the landing price
	 */
	private int calcHousePrice(int id, int houses) {
		switch (houses) {
		case 1:
			return fields[id].getHousePrices()[1];
		case 2:
			return fields[id].getHousePrices()[2];
		case 3:
			return fields[id].getHousePrices()[3];
		case 4:
			return fields[id].getHousePrices()[4];
		case 5:
			return fields[id].getHousePrices()[5];
		default:
			return id;
		}
	}

	/**
	 * Logic for buying a shipping field
	 * @param id
	 * @param currentPlayer
	 * @return
	 */
	public String shippingBuyLogic(int id, Player currentPlayer) {
		int counter = 0; // How many the player owns
		currentPlayer.getAccount().withdraw(fields[id].getBaseValue()); // Withdraw the basevalue from the player
		fields[id].setOwner(currentPlayer); // Set the owner
		Property[] fields = (Property[]) entities.getFieldArr(); 
		for (int i = 0; i <= fields.length; i++) { // First loop and find out how many we own
			if(fields[i].getOwner() == currentPlayer && fields[i].getClass().getSimpleName().equals("shipping")) {
				counter ++; // Update how many we own
				for (int j = 0; j < fields.length; j++) {
					fields[i].setCurrentValue(getShippingValue(counter)); // Set the value on them all
				}
			}

		}
		return "Bought";
	}
	/**
	 * Calculates the price of the shipping fields
	 * @param i How many we own
	 * @return The value 
	 */
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
	/**
	 * The logic for buying a brewery field 
	 * @param id
	 * @param currentPlayer
	 * @return
	 */
	public String breweryBuyLogic(int id, Player currentPlayer) {
		currentPlayer.getAccount().withdraw(fields[id].getBaseValue());
		fields[id].setOwner(currentPlayer);
		return "Bought";
	}
	/**
	 * Logic for unpawning a property
	 * @param id
	 * @param currentPlayer
	 * @return
	 */
	public String unPawnProperty(int id, Player currentPlayer) {
		if(currentPlayer.getAccount().canAfford(fields[id].getPawnValue() + ((int)(fields[id].getPawnValue()*0.10)))) { // We check if the player can afford the pawn value and 10% extra
			currentPlayer.getAccount().withdraw(fields[id].getPawnValue() + ((int)(fields[id].getPawnValue()*0.10)));
			fields[id].setIsPawned(false);
			return "UnPawned, " + (fields[id].getPawnValue() + ((int)(fields[id].getPawnValue()*0.10)));
		}
		else {
			return "CannotAfford";
		}
	}


}
