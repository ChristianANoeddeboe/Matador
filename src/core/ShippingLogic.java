package core;
/**
 * Logic for landing on a shipping field
 * @author Mathias Thejsen s175192 && Simon Hansen s175191
 *
 */
public class ShippingLogic {
	private int totalFaceValue;
	private Shipping shipping;
	private Player currentPlayer;
	/**
	 * Constructor for shipping logic
	 * @param id The field number
	 * @param totalFaceValue The total dice value of both dices
	 * @param currentPlayer The player that landed on the field
	 */
	public ShippingLogic(Player currentPlayer, int totalFaceValue, Field[] fields) {
		this.currentPlayer = currentPlayer;
		this.shipping = (Shipping) fields[currentPlayer.getEndPosition()];
		this.totalFaceValue = totalFaceValue;
	}
	/**
	 * Returns the outcome of landin gon the field
	 * @param currentPlayer The current player
	 * @return
	 */
	public void logic() {
		if(shipping.getOwner() == null) { // We check if the field is owned
			if(currentPlayer.getAccount().canAfford(shipping.getBuyValue())) {// If it is not owned and the player can afford it
				String[] choices = {"Yes", "No"};
				String result = GUIController.getInstance().requestPlayerChoiceButtons("Vil du købe..."+shipping.getName(), choices);
				if(result.equals("Yes")) {
					BuyLogic buyLogic = new BuyLogic(currentPlayer, shipping);
					buyLogic.buyLogic();
				}
			}
			else {
				// Player cant afford the field
			}
		}else{
			if(shipping.getOwner() == currentPlayer) { // Field owned by the player landing on it
			}else { //If it is owned by another player
				int rentPrice = shipping.getCurrentValue(); // We get the field rent/price
				if(currentPlayer.getAccount().canAfford(rentPrice)) { // We check if the player can afford the rent
					currentPlayer.getAccount().withdraw(rentPrice); // We withdraw
					shipping.getOwner().getAccount().deposit(rentPrice); // and deposit back to the field owner
					GUIController.getInstance().updatePlayerBalance(shipping.getOwner().getGuiId(), shipping.getOwner().getAccount().getBalance());
					GUIController.getInstance().writeMessage("You landed on.."+shipping.getOwner().getName() + "..'s field and had to pay.."+shipping.getRentValue()); 
				}else {
					//Player can't afford to land on the field
				}

			}

		}
	}
}
