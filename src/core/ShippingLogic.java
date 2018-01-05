package core;
/**
 * Logic for landing on a shipping field
 * @author Mathias Thejsen - Thejsen@live.dk
 *
 */
public class ShippingLogic {
	int id;
	int totalFaceValue;
	Player currentPlayer;
	/**
	 * Constructor for shipping logic
	 * @param id The field number
	 * @param totalFaceValue The total dice value of both dices
	 * @param currentPlayer The player that landed on the field
	 */
	public ShippingLogic(int id, int totalFaceValue, Player currentPlayer) {
		this.id = id;
		this.totalFaceValue = totalFaceValue;
		this.currentPlayer = currentPlayer;
	}
	/**
	 * Returns the outcome of landin gon the field
	 * @param id The field number
	 * @param totalFaceValue The total dice value of both dices
	 * @param currentPlayer The current player
	 * @return
	 */
	public String logic(int id,int totalFaceValue, Player currentPlayer) {
		Entities entities = Entities.getInstance();
		Shipping[] fields = (Shipping[]) entities.getFieldArr();
		if(fields[id].getOwner() == null) { // We check if the field is owned
			if(currentPlayer.getAccount().canAfford(fields[id].getBaseValue())) {// If it is not owned and the player can afford it
				return "NotOwnedAndCanAfford"; 
			}
			else {
				return "CannotAfford"; // Player cant afford the field
			}
		}else{
			if(fields[id].getOwner() == currentPlayer) { // Field owned by the player landing on it
				return "OwnedByPlayer";
			}else { //If it is owned by another player
				int rentPrice = fields[id].getCurrentValue(); // We get the field rent/price
				if(currentPlayer.getAccount().canAfford(rentPrice)) { // We check if the player can afford the rent
					currentPlayer.getAccount().withdraw(rentPrice); // We withdraw
					fields[id].getOwner().getAccount().deposit(rentPrice); // and deposit back to the field owner
					return "Rentprice,"+rentPrice; 
				}else {
					//Player can't afford to land on the field
					return "saleLogic";
				}

			}

		}
	}
}
