package core;
/**
 * 
 * @author Mathias Thejsen s175192 && Simon Hansen s175191
 *
 */
public class TaxLogic {
	private Entities entities = Entities.getInstance();
	private Field[] fields = entities.getFieldArr();
	private int id;
	private Tax tax;


	/**
	 * Constructor for tax logic
	 * 
	 * @param id
	 * @param currentPlayer
	 */
	public TaxLogic(Player currentPlayer) {
		this.id = currentPlayer.getEndPosition();
		this.tax = (Tax) fields[id];
	}

	/**
	 * If we land on field 39, we have to pay 2000
	 * @param currentPlayer
	 * @return depends on outcome
	 */
	protected String taxLogic38(Player currentPlayer) {
		if (currentPlayer.getAccount().canAfford(2000)) {
			currentPlayer.getAccount().withdraw(2000);
			return "StateTax";
		} else {
			return "SaleLogic";
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
			int value = (int) ((buildingvalue + playervalue + propertyvalue) * 0.10);
			if(currentPlayer.getAccount().canAfford(value)) {
				return Integer.toString(value); // Add the building value,player value and property value and take 10%, casting to a integer to avoid decimals
			}else {
				return "SaleLogic";
			}
			
		}else{
			if (currentPlayer.getAccount().canAfford(4000)) {
				currentPlayer.getAccount().withdraw(4000);
				return "4000";
			}
			return "SaleLogic";
		}
	}

	protected String taxLogic(Player currentPlayer) {
		if(currentPlayer.getEndPosition() == 38) {
			return this.taxLogic38(currentPlayer);
		}else {
			return "TaxChoice";
		}
	}
}
