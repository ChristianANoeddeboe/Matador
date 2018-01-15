package core;

/**
 * 
 * @author Mathias Thejsen s175192 && Simon Hansen s175191
 *
 */
public class SalesController {
	private Player currentPlayer;
	private GUIController guiController = GUIController.getInstance();
	private FieldController fieldcontroller = guiController.getFieldController();
	/**
	 * Constructor for salescontroller
	 * @param currentPlayer
	 */
	public SalesController(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

    /**
	 * Logic when player cannot afford something
	 * @param value The value we can't afford
	 */
	public boolean cannotAfford(int value) {
		boolean housesToSell = true;
		boolean propertyToPawn = true;
		Field[] fields = fieldcontroller.getFieldArr();
		
		// While loop until the player can afford the rent/pay
		while(currentPlayer.getAccount().getBalance() < value && !currentPlayer.isBanktrupt()) {
			String[] streets = fieldcontroller.streetsWithHouses(currentPlayer); // Get an array of streets with houses
			String[] properties = fieldcontroller.propertiesToPawn(currentPlayer); // Get an array of properties we can pawn
			
			 // Check if the Player has anything to sell, if not they go bankrupt.
			if(streets.length == 0 && properties.length == 0 && currentPlayer.getAccount().getBalance() < value) {
				guiController.writeMessage(PropertiesIO.getTranslation("banktrupt"));
				currentPlayer.setBanktrupt(true);
				return false;
			}
			
			// Make array to hold options
			String[] temp = new String[2];
			int counter = 0;
			// Check if the streets array is empty (There will always be one due to the Return Option)
			if(streets.length > 0) { 
				temp[counter] = PropertiesIO.getTranslation("sellhouses");
				counter++;
			}
			// Check if the properties array is empty (There will always be one due to the Return Option)
			if(properties.length > 0) {
				temp[counter] = PropertiesIO.getTranslation("pawnproperty");
				counter++;
			}
			String[] options = new String[counter];
			for(int i = 0; i < options.length; i++) {
				options[i] = temp[i];
			}
			// Ask user for choice
			String result = guiController.requestPlayerChoiceDropdown(PropertiesIO.getTranslation("salescontrollerrequest"), options);

			
			// Runs the sellHouse method if chosen
			if(result.equals(PropertiesIO.getTranslation("sellhouses"))) {
				housesToSell = sellHouse();
			}
			
			// Runs the pawnProperty method if chosen
			if(result.equals(PropertiesIO.getTranslation("pawnproperty"))) {
				propertyToPawn = pawnProperty();
			}
			
			// If the player has no houses to sell, properties to pawn and still cannot afford, the player is declared bankrupt and false is returned
			if(!housesToSell && !propertyToPawn && currentPlayer.getAccount().getBalance() < value) {
				currentPlayer.setBanktrupt(true);
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Logic for selling a house
	 * @return a boolean that indicates if we have any houses to sell or not
	 */
	public boolean sellHouse() {
		// Method returns a String array with all fields that the current player owns that haves houses built on them
		String[] temp = fieldcontroller.streetsWithHouses(currentPlayer); // A temporary array 
		String[] streets = new String[temp.length+1]; // A street array that is one size bigger than the previous, because we need the return option
		for(int i = 0; i<temp.length;i++) {
			streets[i] = temp[i]; // Fill the streets array
		}
		streets[streets.length-1] = PropertiesIO.getTranslation("returnbutton");
		// Returns false if there is no fields with houses
		
		
		// Prompts the user for a choice
		String response = guiController.requestPlayerChoiceDropdown(PropertiesIO.getTranslation("sellhouse"), streets);
		if(response.equals(PropertiesIO.getTranslation("returnbutton"))) { // We can go a menu back

			return true;
		}
		for(int i = 0; i < fieldcontroller.getFieldArr().length; i++) {
			// Finds the field that the player have chosen
			if(response.equals(fieldcontroller.getFieldArr()[i].getName())) {
				
				// hold the field as a street class
				Street street = (Street) fieldcontroller.getFieldArr()[i];
				
				// We get the current player and deposit the house price back into the players account
				currentPlayer.getAccount().deposit(street.getBuildPrice());
				
				// Removes a house from the Street field
				street.setHouseCounter(street.getHouseCounter()-1);
				
				// Send all the updates to GUIController
				int value = street.getHouseCounter() -1;
				guiController.updatePlayerBalance(currentPlayer.getGuiId(), currentPlayer.getAccount().getBalance());
				guiController.setHouse(street.getId(), value);
				guiController.writeMessage(PropertiesIO.getTranslation("soldhouse")+street.getBuildPrice());
			}
		}
		return true;

	}

	/**
	 * Logic for pawning a property
	 * @return Returns true if we have anything at all to pawn, and false if not
	 */
	public boolean pawnProperty() {
		
		// Holds a String array of all the properties owned by the current Player
		String[] temp = fieldcontroller.propertiesToPawn(currentPlayer);
		String[] properties = new String[temp.length+1];
		for(int i = 0; i<temp.length;i++) {
			properties[i] = temp[i];
		}
		properties[properties.length-1] = PropertiesIO.getTranslation("returnbutton");
		// Returns false if the player owns no properties to pawn
		
		// Prompts the user for a choice
		String response = guiController.requestPlayerChoiceDropdown(PropertiesIO.getTranslation("pawnpick"), properties);
		if(response.equals(PropertiesIO.getTranslation("returnbutton"))) {
			return true;
		}
		for(int i = 0; i < fieldcontroller.getFieldArr().length; i++) {
			
			// Finds the field that the player have chosen
			if(response.equals(fieldcontroller.getFieldArr()[i].getName())) {
				
				// Hold the field as a property class
				Property property = (Property) fieldcontroller.getFieldArr()[i];
				
				// We set the pawn bool to true
				property.setPawned(true);
				
				// We get the pawn value and deposit it into the owners account
				currentPlayer.getAccount().deposit(property.getPawnValue()); 
				
				// Send all updates to GUIController
				guiController.updatePlayerBalance(currentPlayer.getGuiId(), currentPlayer.getAccount().getBalance());
				guiController.writeMessage(PropertiesIO.getTranslation("pawnstr") + " "+property.getName()+" for kr."+property.getPawnValue());
			}
		}
		return true;
	}
	public Player getCurrentPlayer() {
		return currentPlayer;
	}
}

