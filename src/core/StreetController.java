/**
 * 
 */
package core;

/**
 * @author Mathias Thejsen s175192 && Simon Hansen s175191
 *
 */
public class StreetController {
	private Street street;
	private Player currentPlayer;
	private GUIController guiController = GUIController.getInstance();
	/**
	 * Constructor for normal logic
	 * @param currentPlayer
	 * @param field
	 */
	public StreetController(Player currentPlayer, Field field) {
		this.currentPlayer = currentPlayer;
		this.street = (Street) field;
	}
	/**
	 * The logic when landing on a normal/property field( a field where you can put houses on)
	 */
	protected void logic() {
		// Check if field is owned
		if(street.getOwner() == null) {  
			// Check if we can afford it
			if(currentPlayer.getAccount().canAfford(street.getRentValue())) {
				
				// Prompt the player for a choice
				String[] choices = {"Yes", "No"};
				String result = guiController.requestPlayerChoiceButtons("Vil du købe..."+street.getName(), choices);
				
				// Check if choice is Yes
				if(result.equals("Yes")) {
					
					// Initialize buyController
					BuyController buyController = new BuyController(currentPlayer, street);
					
					// Run the buyLogic method
					buyController.buyLogic();
				}
			}
			else { 
				
			}
		}
		else{ // The field is owned
			
			// Check if the player landing there is the owner itself
			if(street.getOwner() == currentPlayer) { 
			}
			else {
				
				// If field is owned by someone else, we check if they can afford landing there
				if(currentPlayer.getAccount().canAfford(street.getRentValue())) { 
					guiController.writeMessage("You landed on.."+street.getName() + "..'s field and have to pay.."+street.getRentValue()+" to "+street.getOwner().getName());
					// Withdraw rentValue from the player
					currentPlayer.getAccount().withdraw(street.getRentValue());
					
					// Deposit rentValue to the owner
					street.getOwner().getAccount().deposit(street.getRentValue());
					
					// Sends updates to GUIController
					guiController.updatePlayerBalance(street.getOwner().getGuiId(), street.getOwner().getAccount().getBalance());

					
				}
				else { // Can't afford
					// Initialize the SalesController
					SalesController salesController = new SalesController(currentPlayer);
					
					// Run cannotAfford method
					salesController.cannotAfford(street.getRentValue());
				}
			}
		}
	}
}


