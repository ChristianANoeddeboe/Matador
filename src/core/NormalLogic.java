/**
 * 
 */
package core;

/**
 * @author Mathias Thejsen s175192 && Simon Hansen s175191
 *
 */
public class NormalLogic {
	private Street normal;
	private Player currentPlayer;
	private Entities entities = Entities.getInstance();
	private Field[] fields = entities.getFieldArr();
	/**
	 * Constructor for normal logic
	 * @param id
	 * @param currentPlayer
	 */
	public NormalLogic(int id, Player currentPlayer) {
		this.normal = (Street) fields[id];
		this.currentPlayer = currentPlayer;
	}
	/**
	 * The logic when landing on a normal/property field( a field where you can put houses on)
	 * @param currentPlayer
	 * @return
	 */
	protected String logic(Player currentPlayer) {
		if(normal.getOwner() == null) { // Check if field is owned
			if(currentPlayer.getAccount().canAfford(normal.getCurrentValue())) { // If it is not owned and we can afford it
				return "NotOwned";
			}
			else { // We can't afford it
				return "CannotAfford";
			}
		}else{
			if(normal.getOwner() == currentPlayer) { // Check if the player landing there is the owner itself
				return "OwnedByPlayer";
			}else {
				if(currentPlayer.getAccount().canAfford(normal.getCurrentValue())) { // Field is owned by someone else, we check if they can afford landing there
					currentPlayer.getAccount().withdraw(normal.getCurrentValue()); // They can, so we withdraw money and put it into the owners
					normal.getOwner().getAccount().deposit(normal.getCurrentValue());
					return "CanAfford";
				}else {// They can't afford landing there
					return "SaleLogic";
				}

			}
		}

	}
}


