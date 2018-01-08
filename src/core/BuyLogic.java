package core;

import java.awt.Color;

/**
 * 
 * @author Mathias Thejsen s175192 && Simon Hansen s175191
 *
 */
public class BuyLogic {
	private Property property;
	private Entities entities = Entities.getInstance();
	private Field[] fields = entities.getFieldArr();
	private int id;

	private Player player;
	/**
	 * Constructor for the buy logic
	 * @param id
	 */
	public BuyLogic() {
	}
	
	protected void updatePlayer(Player currentPlayer) {
		this.player = currentPlayer;
		this.id = player.getEndPosition();
		this.property = (Property) fields[id];
	}
	
	
	
	/**
	 * Logic for buying a property
	 * @param currentPlayer
	 * @return
	 */
	protected void propertyBuyLogic(Player currentPlayer) {
		Field field = Entities.getInstance().getFieldArr()[currentPlayer.getEndPosition()];
		if(field instanceof Normal) {
			currentPlayer.getAccount().withdraw((property.getBaseValue())); // Withdraw money form player based on the property base value
			property.setOwner(currentPlayer); // Set the owner
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
	 */
	protected Boolean canBuyHouse(Player currentPlayer) {
		boolean bool[] = {false;false;false;false;false;false;false};
		
		
		
		for(Field field : entities.getFieldArr()) {
			if(field instanceof Normal) {
				//WE NOW HAVE ALL THE NORMALS YOU OWN
				
			}
		}
		
		
		
		Color colour;
		outerloop:
			for (int i = 0; i < fields.length; i++) { // We loop over all our fields
				if(fields[i] instanceof Normal) { // We find the fields which are an instance of Normal
					Normal normal = (Normal) entities.getFieldArr()[i]; // Casting
					if(normal.getOwner() == currentPlayer) { // We check if the current field is owned by the player
						colour = normal.getColour(); // Grab the colour
						for (int j = 0; j < fields.length; j++) { // Start an inner loop
							if(fields[j] instanceof Normal) { // Once again only want to look at the fields which are of the type normal
								Normal normal2 = (Normal) entities.getFieldArr()[j]; // casting
								if(normal2.getColour() == colour && normal2.getOwner() != currentPlayer && j != i) { // Making sure that the fields of the same colour and the same owner, if not the same owner we return false
									bool = false;
									break;
								}else {
									bool = true;
									break outerloop;
								}
							}
						}
					}
				}
			}
		return bool;
	}

	protected String[] buyHouseList(Player currentPlayer) {
		String[] properties = new String[40];
		int counter = 0;
		Color colour;
		for (int i = 0; i < fields.length; i++) {
			if(fields[i] instanceof Normal) {
				Normal normal = (Normal) entities.getFieldArr()[i];
				if(normal.getOwner() == currentPlayer) {
					for (int j = 0; j < fields.length; j++) {
						if(fields[j] instanceof Normal) {
							Normal normal2 = (Normal) entities.getFieldArr()[j];
							if(normal2.getColour() == normal.getColour() && normal2.getOwner() != currentPlayer && j!= i) {
								break;
							}else {
								for (int k = 0; k < properties.length; k++) {
									if(properties[k] == normal.getName()) {
										for (int k2 = 0; k2 < properties.length; k2++) {
											if(properties[k2] == normal.getName() && k!=k2) {

											}else {
												properties[counter] = normal.getName();
												counter++;
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return properties;
	}

	/**
	 * Buying a house logic
	 * @param currentPlayer
	 * @return
	 */
	protected String houseBuyLogic(Player currentPlayer) {
		// Player can buy new house and there is no more than 4 buildings on the field
		if(fields[id] instanceof Normal) { // We are only dealing with fields of the type normal, so only check for those
			Normal normal = (Normal) fields[id]; // Instantiate a new Normal object casting fieldsid normal
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
		if(fields[id] instanceof Normal) {
			Normal normal = (Normal) fields[id];
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

}
