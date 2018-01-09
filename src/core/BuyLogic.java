package core;

import java.awt.Color;
import java.lang.reflect.Array;

/**
 * 
 * @author Mathias Thejsen s175192 && Simon Hansen s175191
 *
 */
public class BuyLogic {
	private Field[] fields;
	private int id;
	Street[] normal;

	private Player player;
	/**
	 * Constructor for the buy logic
	 * @param id
	 */
	public BuyLogic() {
	}

//	protected void updatePlayer(Player currentPlayer) {
//		this.player = currentPlayer;
//		this.id = player.getEndPosition();
//		this.property = (Property) fields[id];
//	}



	/**
	 * Logic for buying a property
	 * @param currentPlayer
	 * @return
	 */
	protected void propertyBuyLogic(Player currentPlayer, Field field) {
		if(field instanceof Street) {
			Street street = (Street) field;
			currentPlayer.getAccount().withdraw(street.getBuyValue()); // Withdraw money form player based on the property base value
			street.setOwner(currentPlayer); // Set the owner
			GUIController.getInstance().setOwner(currentPlayer.getGuiId(), currentPlayer.getEndPosition());
		}else if(field instanceof Brewery) {
			this.breweryBuyLogic(currentPlayer);
		}else if(field instanceof Shipping) {
			this.shippingBuyLogic(currentPlayer);
		}
	}

	protected int getPropertyValue(Player currentPlayer) {
		return property.getBaseValue();
	}

	/**
	 * Checks if the player can buy a house by checking if the player owns a row of colours
	 * @param currentPlayer
	 * @return a boolean
	 
	protected Boolean canBuyHouse(Player currentPlayer) {
		boolean bool = true;
		this.normal = new Street[40];
		int val = 0;
		boolean exists = false;
		Color colour;
		for (int i = 0; i < fields.length; i++) { // We loop over all our fields
			if(fields[i] instanceof Street) { // We find the fields which are an instance of Normal
				Street normal = (Street) entities.getFieldArr()[i]; // Casting
				if(normal.getOwner() == currentPlayer) { // We check if the current field is owned by the player
					colour = normal.getColour(); // Grab the colour
					for (int j = 0; j < fields.length; j++) { // Start an inner loop
						if(fields[j] instanceof Street) { // Once again only want to look at the fields which are of the type normal
							Street normal2 = (Street) entities.getFieldArr()[j]; // casting
							if(normal2.getColour() == colour && normal2.getOwner() != currentPlayer && j != i) { // Making sure that the fields of the same colour and the same owner, if not the same owner we return false
								bool = false;
								break;
							}else {
								bool = true;
								break;
							}
						}
					}
					if(bool == true) {
						for (int j = 0; j < fields.length; j++) {
							if (fields[j] instanceof Street) {
								Street normal2 = (Street) entities.getFieldArr()[j]; // casting
								if(normal2.getColour() == colour) {
									exists = false;
									for (int k = 0; k < this.normal.length; k++) {
										if(this.normal[k] == normal2)
										{
											exists = true;
										}
									}
									if(!exists) {												
										this.normal[val++] = normal2;
									}

								}
							}

						}
						//break;
					}
				}
			}
		}
		return bool;
	}*/

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
						if(street[1].getHouseCounter() == i) {
							properties[index++] = street[1].getName();
							added = true;
						}
						
						if(street[3].getHouseCounter() == i) {
							properties[index++] = street[3].getName();
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
						if(street[6].getHouseCounter() == i) {
							properties[index++] = street[6].getName();
							added = true;
						}
						
						if(street[8].getHouseCounter() == i) {
							properties[index++] = street[8].getName();
							added = true;
						}
						
						if(street[9].getHouseCounter() == i) {
							properties[index++] = street[9].getName();
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
						if(street[11].getHouseCounter() == i) {
							properties[index++] = street[11].getName();
							added = true;
						}
						
						if(street[13].getHouseCounter() == i) {
							properties[index++] = street[13].getName();
							added = true;
						}
						
						if(street[14].getHouseCounter() == i) {
							properties[index++] = street[14].getName();
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
						if(street[16].getHouseCounter() == i) {
							properties[index++] = street[16].getName();
							added = true;
						}
						
						if(street[18].getHouseCounter() == i) {
							properties[index++] = street[18].getName();
							added = true;
						}
						
						if(street[19].getHouseCounter() == i) {
							properties[index++] = street[19].getName();
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
						if(street[21].getHouseCounter() == i) {
							properties[index++] = street[21].getName();
							added = true;
						}
						
						if(street[23].getHouseCounter() == i) {
							properties[index++] = street[23].getName();
							added = true;
						}
						
						if(street[24].getHouseCounter() == i) {
							properties[index++] = street[24].getName();
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
						if(street[26].getHouseCounter() == i) {
							properties[index++] = street[26].getName();
							added = true;
						}
						
						if(street[27].getHouseCounter() == i) {
							properties[index++] = street[27].getName();
							added = true;
						}
						
						if(street[29].getHouseCounter() == i) {
							properties[index++] = street[29].getName();
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
						if(street[31].getHouseCounter() == i) {
							properties[index++] = street[31].getName();
							added = true;
						}
						
						if(street[32].getHouseCounter() == i) {
							properties[index++] = street[32].getName();
							added = true;
						}
						
						if(street[34].getHouseCounter() == i) {
							properties[index++] = street[34].getName();
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
						if(street[37].getHouseCounter() == i) {
							properties[index++] = street[37].getName();
							added = true;
						}
						
						if(street[39].getHouseCounter() == i) {
							properties[index++] = street[39].getName();
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
		return properties;
	}
	
	public void buyHouse(String fieldName) {
		for (Field field : fields) {
			if (field.getName() == fieldName) {
				((Street) field).setHouseCounter(((Street) field).getHouseCounter() + 1);
			}
		}
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
	protected String houseBuyLogic(Player currentPlayer) {
		// Player can buy new house and there is no more than 4 buildings on the field
		if(fields[id] instanceof Street) { // We are only dealing with fields of the type normal, so only check for those
			Street normal = (Street) fields[id]; // Instantiate a new Normal object casting fieldsid normal
			if (currentPlayer.getAccount().canAfford(normal.getBuildPrice()) && normal.getHouseCounter() <= 5) { // Check if player can afford house and making sure there is not already 5
				currentPlayer.getAccount().withdraw(normal.getBuildPrice());
				normal.setHouseCounter(normal.getHouseCounter() + 1);
				normal.setCurrentValue(calcHousePrice(normal.getHouseCounter()));
				return "HouseBought";
			}
			// There is already a hotel (5 houses) on the field
			else if (normal.getHouseCounter() == 5) {
				return "TooManyHouses";
			}
			// The Player cannot afford another house
			else {
				return "CannotAfford, " + normal.getBuildPrice();
			}
		}
		return "ErrorHousebuyLogic";
	}
	/**
	 * Calculates the new price of rent when a new house has been build
	 * @param houses
	 * @return the landing price
	 */
	private int calcHousePrice(int houses) {
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
	 * Logic for buying a shipping field
	 * @param currentPlayer
	 * @return
	 */
	protected void shippingBuyLogic(Player currentPlayer) {
		int counter = 0; // How many the player owns
		currentPlayer.getAccount().withdraw(property.getBaseValue()); // Withdraw the basevalue from the player
		property.setOwner(currentPlayer); // Set the owner
		for (int i = 0; i <fields.length; i++) { // First loop and find out how many we own
			if(fields[i] instanceof Shipping) {
				Shipping shipping = (Shipping) fields[i];
				if (shipping.getOwner() == currentPlayer && fields[i].getClass().getSimpleName().equals("shipping")) {
					counter++; // Update how many we own
					for (int j = 0; j < fields.length; j++) {
						shipping.setCurrentValue(getShippingValue(counter)); // Set the value on them all
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
	protected void breweryBuyLogic(Player currentPlayer) {
		currentPlayer.getAccount().withdraw(property.getBaseValue());
		property.setOwner(currentPlayer);
	}

	/**
	 * Logic for unpawning a property
	 * @param currentPlayer
	 * @return
	 */
	protected String unPawnProperty(Player currentPlayer) {
		if (currentPlayer.getAccount().canAfford(property.getPawnValue() + ((int) (property.getPawnValue() * 0.10)))) { // We check if the player can afford the pawn value and 10% extra
			currentPlayer.getAccount().withdraw(property.getPawnValue() + ((int) (property.getPawnValue() * 0.10)));
			property.setIsPawned(false);
			return "UnPawned, " + (property.getPawnValue() + ((int) (property.getPawnValue() * 0.10)));
		} else {
			return "CannotAfford";
		}
	}


	public Street[] getNormal() {
		return normal;
	}
}
