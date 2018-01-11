package core;

import gui_main.GUI;

/**
 * 
 * @author Mathias Thejsen s175192 && Simon Hansen s175191
 *
 */
public class TaxController {
	private Player currentPlayer;
	private Field[] fields;
	private GUIController guiController = GUIController.getInstance();

	/**
	 * Constructor for tax logic
	 * @param currentPlayer
	 * @param fields a field array
	 */
	public TaxController(Player currentPlayer, Field[] fields) {
		this.currentPlayer = currentPlayer;
		this.fields = fields;
	}

	/**
	 * If we land on field 39, we have to pay 2000
	 * @return depends on outcome
	 */
	protected void taxLogic38() {
		if (currentPlayer.getAccount().canAfford(2000)) {
			currentPlayer.getAccount().withdraw(2000);
			guiController.updatePlayerBalance(currentPlayer.getGuiId(), currentPlayer.getAccount().getBalance());
			guiController.writeMessage(PropertiesIO.getTranslation("statetaxpaymentstr"));
		} 
		else {
			guiController.writeMessage("You cannot afford the tax, you have to sell or pawn something");
			SalesController salesController = new SalesController(currentPlayer);
			boolean response = salesController.cannotAfford(2000); // We can't afford it
			if(response) {
				currentPlayer.getAccount().withdraw(2000);
				guiController.updatePlayerBalance(currentPlayer.getGuiId(), currentPlayer.getAccount().getBalance());
				guiController.writeMessage("Du betalte 2000 i statsskat");
			}
			else {
				
			}
		}
	}

	/**
	 * If we land on field 5, you can either pay 10% of income tax or 4000
	 * @return Depends on outcome
	 */
	protected void taxLogic4() {
		int buildingvalue = 0;
		int playervalue = currentPlayer.getAccount().getBalance(); // The players current balance
		int propertyvalue = 0;
		SalesController salesController = new SalesController(currentPlayer);
		String[] choices = {"4000", "10%"};
		String choice = guiController.requestPlayerChoiceButtons("Vil du betale 10% inkomst skat eller 4000", choices);
		if (choice.equals("10%")) { // we calulate 10% of income tax
			for (int i = 0; i < fields.length; i++) { // looping over all fields
				if (fields[i] instanceof Property) { // Only dealing with fields that are property -> can be owned
					Property property = (Property) fields[i]; // Casting& initializing a new property as we know fields[i] represents a property
					if (property.getOwner() == currentPlayer) {// find those that the player owns
						propertyvalue = propertyvalue + property.getBuyValue(); // The property value is a sum of all the basevalues
					}
				}else if(fields[i] instanceof Street) { // If we are dealing with streets->Properties that can have houses
					Street street = (Street) fields[i];
					for (int j = 0; j < street.getHouseCounter(); j++) { // loop over all the houses
						buildingvalue = buildingvalue + street.getHousePrices()[i]; // adding up all the house prices
					}
				}
			}
			int value = (int) ((buildingvalue + playervalue + propertyvalue) * 0.10); // Take 10% of it
			if(currentPlayer.getAccount().canAfford(value)) { // Check if we can afford
				currentPlayer.getAccount().withdraw(value);
				guiController.updatePlayerBalance(currentPlayer.getGuiId(), currentPlayer.getAccount().getBalance());
				guiController.writeMessage("10% income tax amounted to a total of.."+ value);
			}else {
				guiController.writeMessage("You cannot afford the tax, you have to sell or pawn something");
				boolean response = salesController.cannotAfford(value); // We can't afford it
				if(response) {
					currentPlayer.getAccount().withdraw(value);
					guiController.updatePlayerBalance(currentPlayer.getGuiId(), currentPlayer.getAccount().getBalance());
					guiController.writeMessage("10% income tax amounted to a total of.."+ value);
				}
				else {
				}
			}
			
		}else{ // 4000
			if (currentPlayer.getAccount().canAfford(4000)) { // check if player can afford 4000
				currentPlayer.getAccount().withdraw(4000);
				guiController.updatePlayerBalance(currentPlayer.getGuiId(), currentPlayer.getAccount().getBalance());
			}
			else {
				guiController.writeMessage("You cannot afford the tax, you have to sell or pawn something");
				boolean response = salesController.cannotAfford(4000); // We can't afford it
				System.out.println("KIG HER:"+response);
				if(response) {
					guiController.writeMessage("You can now afford the tax");
					currentPlayer.getAccount().withdraw(4000);
					guiController.updatePlayerBalance(currentPlayer.getGuiId(), currentPlayer.getAccount().getBalance());
				}
				else {
				}
			}
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
