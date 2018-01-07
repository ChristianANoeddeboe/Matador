package core;
/**
 * Logic for landing on a shipping field
 * @author Mathias Thejsen s175192 && Simon Hansen s175191
 *
 */
public class ShippingLogic {
	private Entities entities = Entities.getInstance();
	private Field[] fields = entities.getFieldArr();
	private int id, totalFaceValue;
	private Player currentPlayer;
	private Shipping shipping;
	/**
	 * Constructor for shipping logic
	 * @param id The field number
	 * @param totalFaceValue The total dice value of both dices
	 * @param currentPlayer The player that landed on the field
	 */
	public ShippingLogic(int id, int totalFaceValue, Player currentPlayer) {
		this.shipping = (Shipping) fields[id];
		this.id = id;
		this.totalFaceValue = totalFaceValue;
		this.currentPlayer = currentPlayer;
	}
	/**
	 * Returns the outcome of landin gon the field
	 * @param currentPlayer The current player
	 * @return
	 */
	public String logic(Player currentPlayer) {
		if(shipping.getOwner() == null) { // We check if the field is owned
			if(currentPlayer.getAccount().canAfford(shipping.getBaseValue())) {// If it is not owned and the player can afford it
				return "NotOwned"; 
			}
			else {
				return "CannotAfford"; // Player cant afford the field
			}
		}else{
			if(shipping.getOwner() == currentPlayer) { // Field owned by the player landing on it
				return "OwnedByPlayer";
			}else { //If it is owned by another player
				int rentPrice = shipping.getCurrentValue(); // We get the field rent/price
				if(currentPlayer.getAccount().canAfford(rentPrice)) { // We check if the player can afford the rent
					currentPlayer.getAccount().withdraw(rentPrice); // We withdraw
					shipping.getOwner().getAccount().deposit(rentPrice); // and deposit back to the field owner
					return "Rentprice,"+rentPrice; 
				}else {
					//Player can't afford to land on the field
					return "saleLogic";
				}

			}

		}
	}
}
