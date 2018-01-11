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
	 * @param currentPlayer The player that landed on the field
	 * @param totalFaceValue The total dice value of both dices
	 * @param fields The fields array
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
				// Player cant afford the field, so nothing happens
			}
		}
		else{
			
			// Field owned by the player landing on it
			if(shipping.getOwner() == currentPlayer) { 
				// Nothing should happen
			}
			
			//If it is owned by another player
			else { 
				
				// We get the field rent/price
				int rentPrice = shipping.getBuyValue();
				
				// Check if the player can afford the rent
				if(currentPlayer.getAccount().canAfford(rentPrice)) {
					// Send update to player
					guiController.writeMessage("You landed on.."+shipping.getName() + "..'s field and have to pay.."+shipping.getRentValue()+" to "+shipping.getOwner().getName());
					
					// Withdraw rentPrice from player
					currentPlayer.getAccount().withdraw(rentPrice);
					
					// Deposit rentPrice to owner
					shipping.getOwner().getAccount().deposit(rentPrice);
					
					// Send updates to GUIController
					guiController.updatePlayerBalance(shipping.getOwner().getGuiId(), shipping.getOwner().getAccount().getBalance());
					guiController.updatePlayerBalance(currentPlayer.getGuiId(), currentPlayer.getAccount().getBalance());
					
				}
				else { // We can't afford landing here
					guiController.writeMessage("You cannot afford the rent, you have to pawn or sell something");
					// Initialize the SalesController
					SalesController salesController = new SalesController(currentPlayer);
					
					// Run cannotAfford method
					boolean response = salesController.cannotAfford(rentPrice);
					if(response) {
						guiController.writeMessage("You can now pay the rent of "+rentPrice);
						
						// Withdraw rentValue from the player
						currentPlayer.getAccount().withdraw(rentPrice);
						
						// Deposit rentValue to the owner
						shipping.getOwner().getAccount().deposit(rentPrice);
						
						// Sends updates to GUIController
						guiController.updatePlayerBalance(shipping.getOwner().getGuiId(), shipping.getOwner().getAccount().getBalance());
						guiController.updatePlayerBalance(currentPlayer.getGuiId(), currentPlayer.getAccount().getBalance());
					}
					else {
					}
				}

			}

		}
	}
}
