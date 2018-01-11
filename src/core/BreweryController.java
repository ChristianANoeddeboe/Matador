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
					//The player can't afford and has to sell something
					SalesController salesController = new SalesController(currentPlayer);
					salesController.cannotAfford(rentPrice);
				}

			}

		}
	}
}


