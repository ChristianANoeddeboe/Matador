package core;

public class TaxLogic {
	private Entities entities = Entities.getInstance();
	private Field[] fields = entities.getFieldArr();
	private int id;

	/**
	 * Constructor for tax logic
	 * 
	 * @param id
	 * @param currentPlayer
	 */
	public TaxLogic(int id, Player currentPlayer) {
		if (id == 38) {
			// taxLogic38(currentPlayer);
		} else {
			// taxLogic4(currentPlayer, choice);
		}
	}

	/**
	 * If we land on field 39, we have to pay 2000
	 * @param currentPlayer
	 * @return depends on outcome
	 */
	protected String taxLogic38(Player currentPlayer) {
		if (currentPlayer.getAccount().canAfford(2000)) {
			currentPlayer.getAccount().withdraw(2000);
			return "TaxPrice," + 2000;
		} else {
			// Pantsaetning
			return "saleLogic";
		}
	}

	/**
	 * If we land on field 5, you can either pay 10% of income tax or 4000
	 * @param currentPlayer
	 * @param choice
	 * @return Depends on outcome
	 */
	protected String taxLogic4(Player currentPlayer, int choice) {
		int buildingvalue = 0;
		int playervalue = currentPlayer.getAccount().getBalance(); // The players current balance
		int propertyvalue = 0;
		// Player can either choose 10% of income tax or 4000
		if (choice == 0) { // we calulate 10% of income tax
			for (int i = 0; i <= fields.length; i++) { // looping over all fields
				if (fields[i] instanceof Property) {
					Property property = (Property) fields[i];
					if (property.getOwner() == currentPlayer) {// find those that the player owns
						propertyvalue = propertyvalue + property.getBaseValue(); // The property value is a sum of all the basevalues
					}
				}else if(fields[i] instanceof Normal) {
					Normal normal = (Normal) fields[i];
					for (int j = 0; j < normal.getHouseCounter(); j++) {
						buildingvalue = buildingvalue + normal.getHousePrices()[i]; // Looping over all the houses and adding up all the house prices
					}
				}
			}
			return "TaxPrice," + ((int) ((buildingvalue + playervalue + propertyvalue) * 0.10)); // Add the building value,player value and property value and take 10%, casting to a integer to avoid decimals
		} else {
			if (currentPlayer.getAccount().canAfford(4000)) {
				currentPlayer.getAccount().withdraw(4000);
				return "TaxPrice," + 4000;
			}
			return "SalesLogic";
		}
	}
}
