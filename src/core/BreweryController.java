package core;

/**
 * @author Mathias Thejsen s175192 && Simon Hansen s175191
 *
 */
public class BreweryController {
	private int totalFaceValue;
	private Brewery brewery;
	private Player currentPlayer;
	private GUIController guiController = GUIController.getInstance();
	private Field[] fields;
	public BreweryController(Player currentPlayer, int totalFaceValue, Field[] fields) {
		this.currentPlayer = currentPlayer;
		this.brewery = (Brewery) fields[currentPlayer.getEndPosition()];
		this.totalFaceValue = totalFaceValue;
		this.fields = fields;
	}
	
	/**
	 * Logic method for brewery
	 */
	protected void logic() {
		// Field is not owned
		if(brewery.getOwner() == null) { 
			
			// Check if the player can afford
			if(currentPlayer.getAccount().canAfford(brewery.getBuyValue())) {
				
				// Give the player choices
				String[] choices = {"Yes", "No"};
				String result = guiController.requestPlayerChoiceButtons("Vil du købe..."+brewery.getName(), choices);
				
				// If they choose Yes run buyLogic method
				if(result.equals("Yes")) {
					BuyController buyController = new BuyController(currentPlayer, brewery);
					buyController.buyLogic();
				}
			}
			else {
				//Player cannot afford field
			}
		}
		else{
			// Field is owned by the player
			if(brewery.getOwner() == currentPlayer) { 
				
			}
			else {
				// Set the rentPrice
				int rentPrice = brewery.getRentValue()*totalFaceValue;
				
				//We check if the landing player can afford the rent
				if(currentPlayer.getAccount().canAfford(rentPrice)) {
					
					// Notify the user
					guiController.writeMessage("You landed on.."+brewery.getName() + "..'s field and have to pay.."+brewery.getRentValue()+" to "+brewery.getOwner().getName());
					
					// Withdraw the rentPrice from the player
					currentPlayer.getAccount().withdraw(rentPrice);
					
					// Deposit the rentPrice to the owner
					brewery.getOwner().getAccount().deposit(rentPrice);
					
					// Send updates to the GUI
					guiController.updatePlayerBalance(brewery.getOwner().getGuiId(), brewery.getOwner().getAccount().getBalance());
					guiController.updatePlayerBalance(currentPlayer.getGuiId(), currentPlayer.getAccount().getBalance());
				}
				else { 
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
						brewery.getOwner().getAccount().deposit(rentPrice);
						
						// Sends updates to GUIController
						guiController.updatePlayerBalance(brewery.getOwner().getGuiId(), brewery.getOwner().getAccount().getBalance());
						guiController.updatePlayerBalance(currentPlayer.getGuiId(), currentPlayer.getAccount().getBalance());
					}
					else {
					}
				}
			}
		}
	}
}


