package core;

import java.awt.Color;
import java.lang.reflect.Array;

/**
 * 
 * @author Mathias Thejsen s175192 && Simon Hansen s175191
 *
 */
public class BuyLogic {
	private int id;
	Street[] normal;

	private Player player;
	/**
	 * Constructor for the buy logic
	 * @param id
	 */
	public BuyLogic() {
	}

	/**
	 * Logic for buying a property
	 * @param currentPlayer
	 * @return
	 */
	protected void buyLogic(Player currentPlayer, Field field) {
		if(field instanceof Street) {
			this.propertyBuyLogic(currentPlayer, field);
		}else if(field instanceof Brewery) {
			this.breweryBuyLogic(currentPlayer, field);
		}else if(field instanceof Shipping) {
			this.shippingBuyLogic(currentPlayer, field);
		}
	}
	
	
	protected void propertyBuyLogic(Player currentPlayer, Field field) {
		Street street = (Street) field;
		currentPlayer.getAccount().withdraw(street.getBuyValue()); // Withdraw money form player based on the property base value
		street.setOwner(currentPlayer); // Set the owner
		GUIController.getInstance().setOwner(currentPlayer.getGuiId(), currentPlayer.getEndPosition());
		GUIController.getInstance().updatePlayerBalance(currentPlayer.getGuiId(), currentPlayer.getAccount().getBalance());
	}
	
	/**
	 * Logic for buying a shipping field
	 * @param currentPlayer
	 * @return
	 */
	protected void shippingBuyLogic(Player currentPlayer, Field field) {
		Shipping shipping = (Shipping) field;
		Field[] fields = GUIController.getInstance().getFieldController().getFieldArr();
		int counter = 0; // How many the player owns
		currentPlayer.getAccount().withdraw(shipping.getBuyValue()); // Withdraw the basevalue from the player
		GUIController.getInstance().updatePlayerBalance(currentPlayer.getGuiId(), currentPlayer.getAccount().getBalance());
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
	
	
	protected String[] listOfFieldsYouCanBuildOn(Street[] street) {
		String[] properties = new String[40];
		Color colour;		
		boolean added = false;
		int amount = 0;
		int index = 0;

		for (int counter = 0; counter < street.length; counter++) {
			switch (convertColor(street[counter].getColour())) {
			case "blue":
				for(int i = 0 ; i < 6 ; i++) {
					if(street[counter].getHouseCounter() == i) {
						properties[index++] = street[counter].getName();
						added = true;
					}

					if(street[counter+1].getHouseCounter() == i) {
						properties[index++] = street[counter+1].getName();
						added = true;
					}

					if(added) {
						added = false;
						counter += 1;
						break;
					}
				}
				break;
			case "orange":
				for(int i = 0 ; i < 6 ; i++) {
					if(street[counter].getHouseCounter() == i) {
						properties[index++] = street[counter].getName();
						added = true;
					}

					if(street[counter+1].getHouseCounter() == i) {
						properties[index++] = street[counter+1].getName();
						added = true;
					}

					if(street[counter+2].getHouseCounter() == i) {
						properties[index++] = street[counter+2].getName();
						added = true;
					}

					if(added) {
						added = false;
						counter += 2;
						break;
					}
				}
				break;
			case "green":
				for(int i = 0 ; i < 6 ; i++) {
					if(street[counter].getHouseCounter() == i) {
						properties[index++] = street[counter].getName();
						added = true;
					}

					if(street[counter+1].getHouseCounter() == i) {
						properties[index++] = street[counter+1].getName();
						added = true;
					}

					if(street[counter+2].getHouseCounter() == i) {
						properties[index++] = street[counter+2].getName();
						added = true;
					}

					if(added) {
						added = false;
						counter += 2;
						break;
					}
				}
				break;
			case "gray":
				for(int i = 0 ; i < 6 ; i++) {
					if(street[counter].getHouseCounter() == i) {
						properties[index++] = street[counter].getName();
						added = true;
					}

					if(street[counter+1].getHouseCounter() == i) {
						properties[index++] = street[counter+1].getName();
						added = true;
					}

					if(street[counter+2].getHouseCounter() == i) {
						properties[index++] = street[counter+2].getName();
						added = true;
					}

					if(added) {
						added = false;
						counter += 2;
						break;
					}
				}
				break;
			case "red":
				for(int i = 0 ; i < 6 ; i++) {
					if(street[counter].getHouseCounter() == i) {
						properties[index++] = street[counter].getName();
						added = true;
					}

					if(street[counter+1].getHouseCounter() == i) {
						properties[index++] = street[counter+1].getName();
						added = true;
					}

					if(street[counter+2].getHouseCounter() == i) {
						properties[index++] = street[counter+2].getName();
						added = true;
					}

					if(added) {
						added = false;
						counter += 2;
						break;
					}
				}
				break;
			case "white":
				for(int i = 0 ; i < 6 ; i++) {
					if(street[counter].getHouseCounter() == i) {
						properties[index++] = street[counter].getName();
						added = true;
					}

					if(street[counter+1].getHouseCounter() == i) {
						properties[index++] = street[counter+1].getName();
						added = true;
					}

					if(street[counter+2].getHouseCounter() == i) {
						properties[index++] = street[counter+2].getName();
						added = true;
					}

					if(added) {
						added = false;
						counter += 2;
						break;
					}
				}
				break;
			case "yellow":
				for(int i = 0 ; i < 6 ; i++) {
					if(street[counter].getHouseCounter() == i) {
						properties[index++] = street[counter].getName();
						added = true;
					}

					if(street[counter+1].getHouseCounter() == i) {
						properties[index++] = street[counter+1].getName();
						added = true;
					}

					if(street[counter+2].getHouseCounter() == i) {
						properties[index++] = street[counter+2].getName();
						added = true;
					}

					if(added) {
						added = false;
						counter += 2;
						break;
					}
				}
				break;
			case "purple":
				for(int i = 0 ; i < 6 ; i++) {
					if(street[counter].getHouseCounter() == i) {
						properties[index++] = street[counter].getName();
						added = true;
					}

					if(street[counter+1].getHouseCounter() == i) {
						properties[index++] = street[counter+1].getName();
						added = true;
					}

					if(added) {
						added = false;
						counter += 2;
						break;
					}
				}
				break;
			}
		}

		String[] propertiesSorted = new String[index];
		for (int i = 0; i < propertiesSorted.length; i++) {
			propertiesSorted[i] = properties[i];
		}
		return propertiesSorted;
	}

	public void buyHouse(Field field, Player currentPlayer) {
		Street street = (Street) field;
		currentPlayer.getAccount().withdraw(street.getBuildPrice());
		street.setHouseCounter(street.getHouseCounter() + 1);
		GUIController.getInstance().setHouse(field.getId(),street.getHouseCounter());
		GUIController.getInstance().updatePlayerBalance(currentPlayer.getGuiId(), currentPlayer.getAccount().getBalance());
	}

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
	protected void houseBuyLogic(Player currentPlayer) {
		// Player can buy new house and there is no more than 4 buildings on the field
		Field[] fields = GUIController.getInstance().getFieldController().getFieldArr();
		if(fields[id] instanceof Street) { // We are only dealing with fields of the type normal, so only check for those
			Street normal = (Street) fields[id]; // Instantiate a new Normal object casting fieldsid normal
			if (currentPlayer.getAccount().canAfford(normal.getBuildPrice()) && normal.getHouseCounter() <= 5) { // Check if player can afford house and making sure there is not already 5
				currentPlayer.getAccount().withdraw(normal.getBuildPrice());
				normal.setHouseCounter(normal.getHouseCounter() + 1);
				normal.setRentValue(calcHousePrice(normal.getHouseCounter()));
			}
		}
	}
	/**
	 * Calculates the new price of rent when a new house has been build
	 * @param houses
	 * @return the landing price
	 */
	private int calcHousePrice(int houses) {
		Field[] fields = GUIController.getInstance().getFieldController().getFieldArr();
		if(fields[id] instanceof Street) {
			Street normal = (Street) fields[id];
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
				return id;
			}
		}
		return id;
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
	protected void breweryBuyLogic(Player currentPlayer, Field field) {
		Brewery brewery = (Brewery) field;
		currentPlayer.getAccount().withdraw(brewery.getBuyValue());
		GUIController.getInstance().updatePlayerBalance(currentPlayer.getGuiId(), currentPlayer.getAccount().getBalance());
		brewery.setOwner(currentPlayer);
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


	public Street[] getNormal() {
		return normal;
	}
}
