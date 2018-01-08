package core;

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
