package core;

import java.awt.Color;

/**
 * 
 * @author Mathias Thejsen s175192 && Simon Hansen s175191
 *
 */
public class BuyController {
	private Field field;
	private Player currentPlayer;
	private GUIController guiController = GUIController.getInstance();


	public BuyController(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}
	/**
	 * Constructor for the buy logic
	 * @param id
	 */
	public BuyController(Player currentPlayer, Field field) {
		this.currentPlayer = currentPlayer;
		this.field = field;
	}

	/**
	 * Logic for buying a property
	 * @param currentPlayer
	 * @return
	 */
	protected void buyLogic() {
		if(field instanceof Street) {
			this.streetBuyLogic();
		}else if(field instanceof Brewery) {
			this.breweryBuyLogic();
		}else if(field instanceof Shipping) {
			this.shippingBuyLogic();
		}
	}


	/**
	 * Logic for buying a street
	 * @param currentPlayer
	 * @param field
	 */

	private void streetBuyLogic() {
		Street street = (Street) field;
		currentPlayer.getAccount().withdraw(street.getBuyValue()); // Withdraw money form player based on the property base value
		street.setOwner(currentPlayer); // Set the owner
		guiController.setOwner(currentPlayer.getGuiId(), currentPlayer.getEndPosition());
		guiController.updatePlayerBalance(currentPlayer.getGuiId(), currentPlayer.getAccount().getBalance());
	}

	/**
	 * Logic for buying a shipping field
	 * @param currentPlayer
	 * @return
	 */
	private void shippingBuyLogic() {
		Shipping shipping = (Shipping) field;
		Field[] fields = guiController.getFieldController().getFieldArr();
		int counter = 0; // How many the player owns
		int rentvalue = 0;
		currentPlayer.getAccount().withdraw(shipping.getBuyValue()); // Withdraw the basevalue from the player
		guiController.updatePlayerBalance(currentPlayer.getGuiId(), currentPlayer.getAccount().getBalance());
		guiController.setOwner(currentPlayer.getGuiId(), field.getId());
		shipping.setOwner(currentPlayer); // Set the owner
		
		for (int i = 0; i < fields.length; i++) { // First loop and find out how many we own
			if(fields[i] instanceof Shipping) {
				Shipping shipping2 = (Shipping) fields[i];
				if (shipping2.getOwner() == currentPlayer) {
					counter++; // Update how many we own
				}
			}
		}
		rentvalue = getShippingValue(counter);
		// Set rent value
		for (int i = 0; i < fields.length; i++) {
			if(fields[i] instanceof Shipping) {
				Shipping shipping2 = (Shipping) fields[i];
				if (shipping2.getOwner() == currentPlayer) {
					shipping2.setRentValue(rentvalue);
				}
			}
		}
		
		
	}


	/**
	 * Calculates the price of the shipping fields
	 * @param i How many we own
	 * @return The value
	 */
	private int getShippingValue(int i) {
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
	 * @param currentPlayer
	 * @return
	 */
	private void breweryBuyLogic() {
		Brewery brewery = (Brewery) field;
		Field[] fields = guiController.getFieldController().getFieldArr();
		int counter = 0;
		currentPlayer.getAccount().withdraw(brewery.getBuyValue());
		guiController.updatePlayerBalance(currentPlayer.getGuiId(), currentPlayer.getAccount().getBalance());
		guiController.setOwner(currentPlayer.getGuiId(), field.getId());
		brewery.setOwner(currentPlayer);
		for (int i = 0; i < fields.length; i++) { // First loop and find out how many we own
			if(fields[i] instanceof Brewery) {
				Brewery brewery2 = (Brewery) fields[i];
				if (brewery2.getOwner() == currentPlayer) {
					counter++; // Update how many we own
					for (int j = 0; j < fields.length; j++) {
						brewery.setRentValue(getBreweryValue(counter)); // Set the value on them all
					}
				}
			}
		}
	}


	/**
	 * Calculates the price of the brewery fields
	 * @param i How many we own
	 * @return The value
	 */
	private int getBreweryValue(int i) {
		switch (i) {
		case 1:
			return 100;
		case 2:
			return 200;
		default:
			return 0;
		}
	}
	/**
	 * Converts a color to a string
	 * @param color
	 * @return a string color
	 */
	private String convertColor(Color color) {
		if(color == Color.yellow) {
			return "yellow";
		}else if(color == Color.blue) {
			return "blue";
		}else if(color == Color.orange) {
			return "orange";
		}else if(color == Color.white) {
			return "white";
		}else if(color == Color.green) {
			return "green";
		}else if(color == Color.magenta) {
			return "purple";
		}else if(color == Color.red) {
			return "red";
		}else if(color == Color.gray) {
			return "gray";
		}else {
			return "Error";
		}
	}

	/**
	 * Buying a house logic
	 * @param currentPlayer
	 * @return
	 */
	protected void houseBuyLogic(Field field) {
		if(field instanceof Street) { // We are only dealing with fields of the type normal, so only check for those
			Street normal = (Street) field; // Instantiate a new Normal object casting fieldsid normal
			currentPlayer.getAccount().withdraw(normal.getBuildPrice());
			this.field = field;
			normal.setHouseCounter(normal.getHouseCounter() + 1);
			normal.setRentValue(calcHousePrice(normal.getHouseCounter()));
			guiController.updatePlayerBalance(currentPlayer.getGuiId(), currentPlayer.getAccount().getBalance());
			guiController.setHouse(normal.getId(),normal.getHouseCounter());
			guiController.setOwner(currentPlayer.getGuiId(), normal.getId());
		}
	}


	/**
	 * Calculates the new price of rent when a new house has been build
	 * @param houses
	 * @return the landing price
	 */
	private int calcHousePrice(int houses) {
		if(field instanceof Street) {
			Street normal = (Street) field;
			switch (houses) {
			case 1:
				return normal.getHousePrices()[1];
			case 2:
				return normal.getHousePrices()[2];
			case 3:
				return normal.getHousePrices()[3];
			case 4:
				return normal.getHousePrices()[4];
			case 5:
				return normal.getHousePrices()[5];
			default:
				return field.getId();
			}
		}
		return field.getId();
	}






	/**
	 * Logic for unpawning a property
	 */
	protected void unPawnProperty() { // This is only called when the player can afford to unpawn a field
		
		// Gets the field array from the GUIController
		Field[] fieldArr = guiController.getFieldController().getFieldArr();
		
		// Initializes the property variable
		Property property = null;
		
		// Calls and hold the String array of all the fields that the player owns and can afford to unpawn
		String[] temp = guiController.getFieldController().pawnedFields(currentPlayer);
		
		// Makes the String array to hold all the fields and a return option
		String[] pawnedProperties = new String[temp.length+1];
		
		// Parses all the fields from the temp array into the pawnedProperties array
		for(int i = 0; i<temp.length;i++) {
			pawnedProperties[i] = temp[i];
		}
		
		// Adds the return option to the array
		pawnedProperties[pawnedProperties.length-1] = PropertiesIO.getTranslation("returnbutton");
		
		// Prompts the user to choose a field to unpawn or return
		String result = guiController.requestPlayerChoiceDropdown(PropertiesIO.getTranslation("unpawnpick"), pawnedProperties);
		
		// If the choice is not the return option
		if(!result.equals(PropertiesIO.getTranslation("returnbutton"))) {
			
			// Loops over the field array to get the field information 
			for(int i = 0; i < guiController.getFieldController().getFieldArr().length; i++) {
				
				// Checks if the field name matches the choice of the player
				if(fieldArr[i].getName().equals(result)) {
					property = (Property) fieldArr[i];
				}
			}
			
			// Sets the property to not pawned
			property.setPawned(false);
			
			// Saves the price of unpawning which is the pawnValue + 10% of the pawnValue
			int value = property.getPawnValue()+(int)((property.getPawnValue()*0.10));
			
			// Withdraws from the Players account
			currentPlayer.getAccount().withdraw(value);
			
			// Sends an update to the GUIController
			guiController.updatePlayerBalance(currentPlayer.getGuiId(), currentPlayer.getAccount().getBalance());
		}
	}


	/**
	 * Returns a string array containing all the fields a player can build on
	 * @param street
	 * @return
	 */
	protected String[] listOfFieldsYouCanBuildOn(Street[] street) {           
		String[] properties = new String[40];
		String[] propertiesSorted = null;
		int index = 0;

		//Loop over all streets in input array
		for (int counter = 0; counter < street.length; counter++) {
			//Get color from street in string format
			String color = convertColor(street[counter].getColour());
			int amountOfHouses = street[counter].getHouseCounter();
			boolean mayBuild = true;
			for (Street streetTemp : street) {
				String color2 = convertColor(streetTemp.getColour());
				if(color == color2) {
					if(amountOfHouses > streetTemp.getHouseCounter()) {
						mayBuild = false;
					}
				}
			}

			if(mayBuild)
				properties[index++] = street[counter].getName();
		}
		//add to return array.
		propertiesSorted = new String[index+1];
		for (int i = 0; i < propertiesSorted.length; i++) {
			propertiesSorted[i] = properties[i];
		}
		propertiesSorted[propertiesSorted.length-1] = PropertiesIO.getTranslation("returnbutton");
		return propertiesSorted;
	}
}