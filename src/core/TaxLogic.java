package core;

import gui_main.GUI;

/**
 * 
 * @author Mathias Thejsen s175192 && Simon Hansen s175191
 *
 */
public class TaxLogic {
	private Player currentPlayer;
	private Field[] fields;
	private GUIController guiController = GUIController.getInstance();

	/**
	 * Constructor for tax logic
	 * 
	 * @param id
	 * @param currentPlayer
	 */
	public TaxLogic(Player currentPlayer, Field[] fields) {
		this.currentPlayer = currentPlayer;
		this.fields = fields;
	}

	/**
	 * If we land on field 39, we have to pay 2000
	 * @param currentPlayer
	 * @return depends on outcome
	 */
	protected void taxLogic38() {
		if (currentPlayer.getAccount().canAfford(2000)) {
			currentPlayer.getAccount().withdraw(2000);
			guiController.updatePlayerBalance(currentPlayer.getGuiId(), currentPlayer.getAccount().getBalance());
			guiController.writeMessage("Du betalte 2000 i statsskat");
		} else {
			//Sales logic
		}
	}

	/**
	 * If we land on field 5, you can either pay 10% of income tax or 4000
	 * @param currentPlayer
	 * @param choice
	 * @return Depends on outcome
	 */
	protected void taxLogic4() {
		int buildingvalue = 0;
		int playervalue = currentPlayer.getAccount().getBalance(); // The players current balance
		int propertyvalue = 0;
		// Player can either choose 10% of income tax or 4000
		String[] choices = {"4000", "10%"};
		String choice = guiController.requestPlayerChoiceButtons("Vil du betale 10% inkomst skat eller 4000", choices);
		if (choice.equals("10%")) { // we calulate 10% of income tax
			for (int i = 0; i < fields.length; i++) { // looping over all fields
				if (fields[i] instanceof Property) {
					Property property = (Property) fields[i];
					if (property.getOwner() == currentPlayer) {// find those that the player owns
						propertyvalue = propertyvalue + property.getBuyValue(); // The property value is a sum of all the basevalues
					}
				}else if(fields[i] instanceof Street) {
					Street normal = (Street) fields[i];
					for (int j = 0; j < normal.getHouseCounter(); j++) {
						buildingvalue = buildingvalue + normal.getHousePrices()[i]; // Looping over all the houses and adding up all the house prices
					}
				}
			}
			int value = (int) ((buildingvalue + playervalue + propertyvalue) * 0.10);
			if(currentPlayer.getAccount().canAfford(value)) {
				currentPlayer.getAccount().withdraw(value);
				guiController.updatePlayerBalance(currentPlayer.getGuiId(), currentPlayer.getAccount().getBalance());
				guiController.writeMessage("10% income tax amounted to a total of.."+ value);
			}else {
				//Sales logic
			}
			
		}else{
			if (currentPlayer.getAccount().canAfford(4000)) {
				currentPlayer.getAccount().withdraw(4000);
				guiController.updatePlayerBalance(currentPlayer.getGuiId(), currentPlayer.getAccount().getBalance());
			}
			// Sales logic
		}
	}

	protected void taxLogic() {
		if(currentPlayer.getEndPosition() == 38) {
			taxLogic38();
		}else {
			taxLogic4();
		}
	}
}
