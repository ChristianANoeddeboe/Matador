package core;

import java.util.Properties;

/**
 * @author Mathias Thejsen s175192 && Simon Hansen s175191
 *
 */
public class BreweryController {
	private int totalFaceValue;
	private Brewery brewery;
	private Player currentPlayer;
	private GUIController guiController = GUIController.getInstance();
	private AuctionController auctionController;

	private Player[] players;

	public BreweryController(Player currentPlayer, int totalFaceValue, Field[] fields, Player[] players) {
		this.currentPlayer = currentPlayer;
		this.brewery = (Brewery) fields[currentPlayer.getEndPosition()];
		this.totalFaceValue = totalFaceValue;
		this.players = players;
		auctionController = new AuctionController();
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
				String[] choices = {PropertiesIO.getTranslation("yesbutton"), PropertiesIO.getTranslation("nobutton")};
				String result = guiController.requestPlayerChoiceButtons(PropertiesIO.getTranslation("streetlandonbuy")+" "+brewery.getName() + " for " + brewery.getBuyValue(), choices);
				
				// If they choose Yes run buyLogic method
				if(result.equals(PropertiesIO.getTranslation("yesbutton"))) {
					BuyController buyController = new BuyController(currentPlayer, brewery);
					buyController.buyLogic();
				}

				else { // If the player does not wish to buy the field
					auctionController.startAuction(currentPlayer, brewery, players);
				}
			}
			else { // If the player cannot afford the field
				auctionController.startAuction(currentPlayer, brewery, players);
			}
		}
		else{
			// Field is owned by the player
			if(brewery.getOwner() == currentPlayer || brewery.isPawned()) { 
				
			}else {
				// Set the rentPrice
				int rentPrice = brewery.getRentValue()*totalFaceValue;
				
				//We check if the landing player can afford the rent
				if(currentPlayer.getAccount().canAfford(rentPrice)) {
					
					// Notify the user
					guiController.writeMessage(PropertiesIO.getTranslation("streetlanddon")+" "+brewery.getName() +" " + PropertiesIO.getTranslation("streetlanddon2") + " "+brewery.getRentValue()+" til "+brewery.getOwner().getName());
					
					// Withdraw the rentPrice from the player
					currentPlayer.getAccount().withdraw(rentPrice);
					
					// Deposit the rentPrice to the owner
					brewery.getOwner().getAccount().deposit(rentPrice);
					
					// Send updates to the GUI
					guiController.updatePlayerBalance(brewery.getOwner().getGuiId(), brewery.getOwner().getAccount().getBalance());
					guiController.updatePlayerBalance(currentPlayer.getGuiId(), currentPlayer.getAccount().getBalance());
				}else { 
					guiController.writeMessage(PropertiesIO.getTranslation("streetcantafford"));
					// Initialize the SalesController
					SalesController salesController = new SalesController(currentPlayer);
					
					// Run cannotAfford method
					boolean response = salesController.cannotAfford(rentPrice);
					if(response) {
						guiController.writeMessage(PropertiesIO.getTranslation("streetcanpayrent")+rentPrice);
						
						// Withdraw rentValue from the player
						currentPlayer.getAccount().withdraw(rentPrice);
						
						// Deposit rentValue to the owner
						brewery.getOwner().getAccount().deposit(rentPrice);
						
						// Sends updates to GUIController
						guiController.updatePlayerBalance(brewery.getOwner().getGuiId(), brewery.getOwner().getAccount().getBalance());
						guiController.updatePlayerBalance(currentPlayer.getGuiId(), currentPlayer.getAccount().getBalance());
					}
				}
			}
		}
	}
}


