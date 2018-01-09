/**
 * 
 */
package core;

/**
 * @author Mathias Thejsen s175192 && Simon Hansen s175191
 *
 */
public class StreetLogic {
	private Street street;
	private Player currentPlayer;
	/**
	 * Constructor for normal logic
	 * @param id
	 * @param currentPlayer
	 */
	public StreetLogic(Player currentPlayer, Field[] fields) {
		this.currentPlayer = currentPlayer;
		this.street = (Street) fields[currentPlayer.getEndPosition()];
	}
	/**
	 * The logic when landing on a normal/property field( a field where you can put houses on)
	 * @param currentPlayer
	 * @return
	 */
	protected String logic() {
		if(street.getOwner() == null) { // Check if field is owned
			if(currentPlayer.getAccount().canAfford(street.getRentValue())) { // If it is not owned and we can afford it
				return "NotOwned";
			}
			else { // We can't afford it
				return "CannotAfford";
			}
		}else{
			if(street.getOwner() == currentPlayer) { // Check if the player landing there is the owner itself
				return "OwnedByPlayer";
			}else {
				if(currentPlayer.getAccount().canAfford(street.getRentValue())) { // Field is owned by someone else, we check if they can afford landing there
					currentPlayer.getAccount().withdraw(street.getRentValue()); // They can, so we withdraw money and put it into the owners
					street.getOwner().getAccount().deposit(street.getRentValue());
					return "CanAfford";
				}else {// They can't afford landing there
					return "SaleLogic";
				}

			}
		}

	}
}


