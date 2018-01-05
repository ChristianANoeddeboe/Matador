/**
 * 
 */
package core;

/**
 * @author Simon og Mathias
 *
 */
public class NormalLogic {
	int id;
	Player currentPlayer;
	static Entities entities = Entities.getInstance();
	static Normal[] fields = (Normal[]) entities.getFieldArr();
	/**
	 * Constructor for normal logic
	 * @param id
	 * @param currentPlayer
	 */
	public NormalLogic(int id, Player currentPlayer) {
		this.id = id;
		this.currentPlayer = currentPlayer;
	}
	/**
	 * The logic when landing on a normal/property field( a field where you can put houses on)
	 * @param id
	 * @param currentPlayer
	 * @return
	 */
	public String logic(int id, Player currentPlayer) {
		if(fields[id].getOwner() == null) { // Check if field is owned
			if(currentPlayer.getAccount().canAfford(fields[id].getCurrentValue())) { // If it is not owned and we can afford it
				return "NotOwned";
			}
			else { // We can't afford it
				return "CannotAfford";
			}
		}else{
			if(fields[id].getOwner() == currentPlayer) { // Check if the player landing there is the owner itself
				return "OwnedByPlayer";
			}else {
				if(currentPlayer.getAccount().canAfford(fields[id].getCurrentValue())) { // Field is owned by someone else, we check if they can afford landing there
					currentPlayer.getAccount().withdraw(fields[id].getCurrentValue()); // They can, so we withdraw money and put it into the owners
					fields[id].getOwner().getAccount().deposit(fields[id].getCurrentValue());
					return "CanAfford";
				}else {// They can't afford landing there
					return "SaleLogic";
				}

			}
		}

	}
}


