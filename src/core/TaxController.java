package core;
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
	public void taxLogic38() {
		// Check if the player can afford the tax
		if (currentPlayer.getAccount().canAfford(Integer.parseInt(PropertiesIO.getTranslation("statetaxvalue")))) {
			
			// Withdraw the value from the players account
			currentPlayer.getAccount().withdraw(Integer.parseInt(PropertiesIO.getTranslation("statetaxvalue")));
			
			// Send updates to the GUIController
			guiController.updatePlayerBalance(currentPlayer.getGuiId(), currentPlayer.getAccount().getBalance());
			guiController.writeMessage(PropertiesIO.getTranslation("statetatxpaymentstr"));
		} 
		else { // If the player cannot afford the tax
			
			// Notify the player
			guiController.writeMessage(PropertiesIO.getTranslation("statetaxnotpaidstr"));
			
			// Make SalesController
			SalesController salesController = new SalesController(currentPlayer);
			
			// Calls the cannotAfford method, which returns a boolean depending on whether or not the player can afford the tax after selling/pawning
			boolean response = salesController.cannotAfford(Integer.parseInt(PropertiesIO.getTranslation("statetaxvalue")));
			if(response) { //If we can afford the tax after selling/pawning
				
				// Withdraw the tax from the player
				currentPlayer.getAccount().withdraw(Integer.parseInt(PropertiesIO.getTranslation("statetaxvalue")));
				
				// Send update to the GUIController
				guiController.updatePlayerBalance(currentPlayer.getGuiId(), currentPlayer.getAccount().getBalance());
				guiController.writeMessage(PropertiesIO.getTranslation("statetatxpaymentstr"));
			}
		}
	}

	/**
	 * If we land on field 5, you can either pay 10% of income tax or 4000
	 * @return Depends on outcome
	 */
	public void taxLogic4() {
		int buildingvalue = 0; // Variable used to hold value of houses
		int playervalue = currentPlayer.getAccount().getBalance(); // The players current balance
		int propertyvalue = 0; // Variable used to hold value of properties
		SalesController salesController = new SalesController(currentPlayer); // Initialize a SalesController
		
		// Make the two choices for the player
		String[] choices = {"4000", "10%"};
		
		// Prompt user for a choice
		String choice = guiController.requestPlayerChoiceButtons(PropertiesIO.getTranslation("taxoptions"), choices);
		
		// If they chose the 10% 
		if (choice.equals("10%")) { 
			// Calculates 10% of income tax
			
			// looping over all fields
			for (int i = 0; i < fields.length; i++) {
				
				// Only dealing with fields that are property -> can be owned
				if (fields[i] instanceof Property) { 
					
					// Casting & initializing a new property as we know fields[i] represents a property
					Property property = (Property) fields[i]; 
					
					// Find those that the player owns
					if (property.getOwner() == currentPlayer) {
						
						// The property value is a sum of all the basevalues
						propertyvalue = propertyvalue + property.getBuyValue(); 
					}
				
				}
				// If we are dealing with streets->Properties that can have houses
				else if(fields[i] instanceof Street) { 
					Street street = (Street) fields[i];
					
					// Loop over all the houses
					for (int j = 0; j < street.getHouseCounter(); j++) {
						
						// adding up all the house prices
						buildingvalue = buildingvalue + street.getHousePrices()[i]; 
					}
				}
			}
			// Take 10% of it
			int value = (int) ((buildingvalue + playervalue + propertyvalue) * 0.10);
			// Check if we can afford
			if(currentPlayer.getAccount().canAfford(value)) {
				
				// If we can, withdraw the value from the player
				currentPlayer.getAccount().withdraw(value);
				
				// Send update to the GUIController
				guiController.updatePlayerBalance(currentPlayer.getGuiId(), currentPlayer.getAccount().getBalance());
				guiController.writeMessage(PropertiesIO.getTranslation("statetaxtotal")+ value);
			}
			// If we cannot afford it
			else {
				
				//Notify the player
				guiController.writeMessage(PropertiesIO.getTranslation("incometaxcantafford"));
				
				// Call the cannotAfford method, which keeps running until the player can either afford the tax or have gone bankrupt
				boolean response = salesController.cannotAfford(value);
				
				// If the player can afford the tax after selling
				if(response) {
					
					// Withdraw the value from the player
					currentPlayer.getAccount().withdraw(value);
					
					// Send update to the GUIController
					guiController.updatePlayerBalance(currentPlayer.getGuiId(), currentPlayer.getAccount().getBalance());
					guiController.writeMessage(PropertiesIO.getTranslation("statetaxtotal")+ value);
				}
			}
			
		}else{ // If the choice is to pay 4000
			
			// Check if the player can afford the tax
			if (currentPlayer.getAccount().canAfford(Integer.parseInt(PropertiesIO.getTranslation("incometaxvalue")))) {
				
				// If the player can afford it, withdraw the tax from the players account
				currentPlayer.getAccount().withdraw(Integer.parseInt(PropertiesIO.getTranslation("incometaxvalue")));
				
				// Send update to the GUIController
				guiController.updatePlayerBalance(currentPlayer.getGuiId(), currentPlayer.getAccount().getBalance());
			}
			// If the player cannot afford the tax
			else {
				
				// Notify the player
				guiController.writeMessage(PropertiesIO.getTranslation("incometaxcantafford"));
				
				// Call the cannotAfford method, which keeps running until the player can either pay the tax or have gone bankrupt
				boolean response = salesController.cannotAfford(Integer.parseInt(PropertiesIO.getTranslation("incometaxvalue")));
				
				// If the player can afford the tax after selling/pawning
				if(response) {
					
					// Notify the player
					guiController.writeMessage(PropertiesIO.getTranslation("incometaxnowavailable"));
					
					// Withdraw the tax from the player
					currentPlayer.getAccount().withdraw(Integer.parseInt(PropertiesIO.getTranslation("incometaxvalue")));
					
					// Send an update to the GUIController
					guiController.updatePlayerBalance(currentPlayer.getGuiId(), currentPlayer.getAccount().getBalance());
				}
			}
		}
	}
	/**
	 * Method that checks which tax field the player has landed on
	 */
	public void taxLogic() {
		
		// Check if the player landed on the tax field on field 38
		if(currentPlayer.getEndPosition() == 38) {
			taxLogic38();
		}
		// If the player has landed on the tax field on field 4
		else {
			taxLogic4();
		}
	}
}
