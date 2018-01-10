package core;
/**
 * Logic for landing on a shipping field
 * @author Mathias Thejsen s175192 && Simon Hansen s175191
 *
 */
public class ShippingController {
	private GUIController guiController = GUIController.getInstance();
	private Shipping shipping;
	private Player currentPlayer;
	/**
	 * Constructor for shipping logic
	 * @param id The field number
	 * @param totalFaceValue The total dice value of both dices
	 * @param currentPlayer The player that landed on the field
	 */
	public ShippingController(Player currentPlayer, int totalFaceValue, Field[] fields) {
		this.currentPlayer = currentPlayer;
		this.shipping = (Shipping) fields[currentPlayer.getEndPosition()];
	}
	/**
	 * Returns the outcome of landin gon the field
	 * @param currentPlayer The current player
	 * @return
	 */
	public void logic() {
		
		 // We check if the field is owned
		if(shipping.getOwner() == null) {
			
			// Check if the Player can afford it
			if(currentPlayer.getAccount().canAfford(shipping.getBuyValue())) {
				
				// Prompt user for choice
				String[] choices = {"Yes", "No"};
				String result = guiController.requestPlayerChoiceButtons("Vil du købe..."+shipping.getName(), choices);
				
				// Check if the choice is Yes
				if(result.equals("Yes")) {
					
					// Initialize BuyController
					BuyController buyController = new BuyController(currentPlayer, shipping);
					
					// Run buyLogic method
					buyController.buyLogic();
				}
			}
			else {
				// Player cant afford the field
			}
		}
		else{
			
			// Field owned by the player landing on it
			if(shipping.getOwner() == currentPlayer) { 
			}
			
			//If it is owned by another player
			else { 
				
				// We get the field rent/price
				int rentPrice = shipping.getCurrentValue();
				
				// Check if the player can afford the rent
				if(currentPlayer.getAccount().canAfford(rentPrice)) {
					
					// Withdraw rentPrice from player
					currentPlayer.getAccount().withdraw(rentPrice);
					
					// Deposit rentPrice to owner
					shipping.getOwner().getAccount().deposit(rentPrice);
					
					// Send updates to GUIController
					guiController.updatePlayerBalance(shipping.getOwner().getGuiId(), shipping.getOwner().getAccount().getBalance());
					guiController.writeMessage("You landed on.."+shipping.getOwner().getName() + "..'s field and had to pay.."+shipping.getRentValue()); 
					
				}
				else {
					SalesController salesController = new SalesController(currentPlayer);
					salesController.cannotAfford(rentPrice);
				}

			}

		}
	}
}
