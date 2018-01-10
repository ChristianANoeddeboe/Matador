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
		System.out.println("hi");
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
		currentPlayer.getAccount().withdraw(shipping.getBuyValue()); // Withdraw the basevalue from the player
		guiController.updatePlayerBalance(currentPlayer.getGuiId(), currentPlayer.getAccount().getBalance());
		guiController.setOwner(currentPlayer.getGuiId(), field.getId());
		shipping.setOwner(currentPlayer); // Set the owner
		for (int i = 0; i < fields.length; i++) { // First loop and find out how many we own
			if(fields[i] instanceof Shipping) {
				Shipping shipping2 = (Shipping) fields[i];
				if (shipping2.getOwner() == currentPlayer) {
					counter++; // Update how many we own
					for (int j = 0; j < fields.length; j++) {
						shipping2.setCurrentValue(getShippingValue(counter)); // Set the value on them all
					}
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
		currentPlayer.getAccount().withdraw(brewery.getBuyValue());
		guiController.updatePlayerBalance(currentPlayer.getGuiId(), currentPlayer.getAccount().getBalance());
		guiController.setOwner(currentPlayer.getGuiId(), field.getId());
		brewery.setOwner(currentPlayer);
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
			//if (normal.getHouseCounter() <= 5) { // Check if player can afford house and making sure there is not already 5
			currentPlayer.getAccount().withdraw(normal.getBuildPrice());
			normal.setHouseCounter(normal.getHouseCounter() + 1);
			normal.setRentValue(calcHousePrice(normal.getHouseCounter()));
			guiController.updatePlayerBalance(currentPlayer.getGuiId(), currentPlayer.getAccount().getBalance());
			guiController.setHouse(normal.getId(),normal.getHouseCounter());
			guiController.setOwner(currentPlayer.getGuiId(), normal.getId());
			//}
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
	 * @param currentPlayer
	 * @return
	 */
	protected String unPawnProperty(Player currentPlayer, Field field) {
		Property property = (Property) field;
		if (currentPlayer.getAccount().canAfford(property.getPawnValue() + ((int) (property.getPawnValue() * 0.10)))) { // We check if the player can afford the pawn value and 10% extra
			currentPlayer.getAccount().withdraw(property.getPawnValue() + ((int) (property.getPawnValue() * 0.10)));
			property.setPawned(false);
			return "UnPawned, " + (property.getPawnValue() + ((int) (property.getPawnValue() * 0.10)));
		} else {
			return "CannotAfford";
		}
	}


	/**
	 * Returns a string array containing all the fields a player can build on
	 * @param street
	 * @return
	 */
	protected String[] listOfFieldsYouCanBuildOn(Street[] street) {
		String[] properties = new String[40];
		Color colour;		
		boolean added = false;
		int amount = 0;
		int index = 0;
		String[] propertiesSorted = null;
		for (int counter = 0; counter < street.length; counter++) {
			String color = convertColor(street[counter].getColour());

			if(color.equals("blue")||color.equals("purple")) {
				for(int i = 0 ; i < 6 ; i++) {
					for (int j = 0; j <= 1; j++) {
						if(street[counter+j].getHouseCounter() == i) {
							properties[index++] = street[counter+j].getName();
							added = true;
						}
					}
					if(added) {
						added = false;
						counter += 1;
						break;
					}
				}
			}else {
				for(int i = 0 ; i < 6 ; i++) {
					for (int j = 0; j <= 2; j++) {
						if(street[counter+j].getHouseCounter() == i) {
							properties[index++] = street[counter+j].getName();
							added = true;
						}
					}
					if(added) {
						added = false;
						counter += 2;
						break;
					}
				}

			}

			 propertiesSorted = new String[index];
			for (int i = 0; i < propertiesSorted.length; i++) {
				propertiesSorted[i] = properties[i];
			}
			return propertiesSorted;
		}
		return propertiesSorted;
	}
}