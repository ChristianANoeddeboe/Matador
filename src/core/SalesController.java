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
				
				// Notify player
				guiController.writeMessage("You've gone bankrupt! Thanks for playing");
				
				// Set the player to bankrupt
				currentPlayer.setBanktrupt(true);
				
				// Need to reset fields owned and house counter
				// Loop over the field array
				for(int i = 0; i < fields.length; i++) {
					// If the field is an instance of Property
					if(fields[i] instanceof Property) {
						// Cast
						Property property = (Property) fields[i];
						// Check if the property is owned by currentPlayer
						if(property.getOwner() == currentPlayer) {
							// Set owner to null
							property.setOwner(null);
							// Check if field is an instance of Shipping
							if(property instanceof Shipping) {
								// Cast
								Shipping shipping = (Shipping) property;
								// Set rentValue to 500 -> Start rent value
								shipping.setRentValue(500);
							}
							// Check if field is an instance of Brewery
							if(property instanceof Brewery) {
								// Cast
								Brewery brewery = (Brewery) property;
								brewery.setRentValue(100);
							}
							// Check if the field is an instance of Street
							if(property instanceof Street) {
								// Cast
								Street street = (Street) property;
								// Set the house counter to 0
								street.setHouseCounter(0);
								// Set the rentValue to no houses
								street.setRentValue(street.getHousePrices()[0]);
							}
						}
					}
				}
				return false;
			}
			
			// Make array to hold options
			String[] temp = new String[2];
			int counter = 0;
			
			// Check if the streets array is empty
			if(streets.length > 0) { 
				
				// if not empty, add the Sell Houses string to temp
				temp[counter] = "Sell Houses";
				
				// Increase counter by one
				counter++;
			}
			// Check if the properties array is empty
			if(properties.length > 0) {
				
				// if not empty, add the Pawn Property to temp
				temp[counter] = "Pawn Property";
				
				// Increase counter by one
				counter++;
			}
			// Make the options array
			String[] options = new String[counter];
			
			// Parse the temp array into the options array
			for(int i = 0; i < options.length; i++) {
				options[i] = temp[i];
			}
			
			// Ask user for choice
			String result = guiController.requestPlayerChoice("What do you wish to do?", options);
			
			// Runs the sellHouse method if chosen
			if(result.equals("Sell Houses")) {
				housesToSell = sellHouse();
			}
			
			// Runs the pawnProperty method if chosen
			if(result.equals("Pawn Property")) {
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
	protected boolean sellHouse() {
		// Method returns a String array with all fields that the current player owns that haves houses built on them
		String[] temp = fieldcontroller.streetsWithHouses(currentPlayer); // A temporary array 
		String[] streets = new String[temp.length+1]; // A street array that is one size bigger than the previous, because we need the return option
		for(int i = 0; i<temp.length;i++) {
			streets[i] = temp[i]; // Fill the streets array
		}
		streets[streets.length-1] = "Return";
		// Returns false if there is no fields with houses
		
		
		// Prompts the user for a choice
		String response = guiController.requestPlayerChoice("Please choose a street to sell your houses from: ", streets);
		if(response.equals("Return")) { // We can go a menu back
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
				guiController.updatePlayerBalance(currentPlayer.getGuiId(), currentPlayer.getAccount().getBalance());
				guiController.setHouse(street.getId(), ((street.getHouseCounter())-1));
				guiController.writeMessage("You have sold a house for: "+street.getBuildPrice());
			}
		}
		return true;

	}

	/**
	 * Logic for pawning a property
	 * @return Returns true if we have anything at all to pawn, and false if not
	 */
	protected boolean pawnProperty() {
		
		// Holds a String array of all the properties owned by the current Player
		String[] temp = fieldcontroller.propertiesToPawn(currentPlayer);
		String[] properties = new String[temp.length+1];
		for(int i = 0; i<temp.length;i++) {
			properties[i] = temp[i];
		}
		properties[properties.length-1] = PropertiesIO.getTranslation("returnbutton");
		// Returns false if the player owns no properties to pawn
		
		// Prompts the user for a choice
		String response = guiController.requestPlayerChoice(PropertiesIO.getTranslation("pawnpick"), properties);
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
				guiController.writeMessage(PropertiesIO.getTranslation("pawnstr")+property.getName()+" for "+property.getPawnValue());
			}
		}
		return true;
	}
}

