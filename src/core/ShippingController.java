package core;

import java.util.Properties;

/**
 * Logic for landing on a shipping field
 * @author Mathias Thejsen s175192 && Simon Hansen s175191
 *
 */
public class ShippingController {
	private GUIController guiController = GUIController.getInstance();
	private Shipping shipping;
	private Player currentPlayer;
	private Player[] players;
	private AuctionController auctionController;
	/**
	 * Constructor for shipping logic
	 * @param currentPlayer The player that landed on the field
	 * @param totalFaceValue The total dice value of both dices
	 * @param fields The fields array
	 */
	public ShippingController(Player currentPlayer, int totalFaceValue, Field[] fields, Player[] players) {
		this.currentPlayer = currentPlayer;
		this.shipping = (Shipping) fields[currentPlayer.getEndPosition()];
		this.players = players;
		auctionController = new AuctionController();
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
				String[] choices = {PropertiesIO.getTranslation("yesbutton"), PropertiesIO.getTranslation("nobutton")};
				String result = guiController.requestPlayerChoiceButtons(PropertiesIO.getTranslation("streetlanddon")+" "+shipping.getName() + " for " + shipping.getBuyValue(), choices);
				
				// Check if the choice is Yes
				if(result.equals(PropertiesIO.getTranslation("yesbutton"))) {
					
					// Initialize BuyController
					BuyController buyController = new BuyController(currentPlayer, shipping);
					
					// Run buyLogic method
					buyController.buyLogic();
				}
				else { // If the player does not wish to buy the field
					auctionController.startAuction(currentPlayer, shipping, players);
				}
			}
			else {
				// If the player cannot afford the field
				auctionController.startAuction(currentPlayer, shipping, players);
			}
		}
		else{
			
			// Field owned by the player landing on it
			if(shipping.getOwner() == currentPlayer || shipping.isPawned()) { 
				// Nothing should happen
			}
			
			//If it is owned by another player
			else { 
				
				// We get the field rent/price
				int rentPrice = shipping.getBuyValue();
				
				// Check if the player can afford the rent
				if(currentPlayer.getAccount().canAfford(rentPrice)) {
					// Send update to player
					guiController.writeMessage(PropertiesIO.getTranslation("streetlanddon")+" "+shipping.getName() +" " + PropertiesIO.getTranslation("streetlanddon2") + " "+shipping.getRentValue()+" til "+shipping.getOwner().getName());
					
					// Withdraw rentPrice from player
					currentPlayer.getAccount().withdraw(rentPrice);
					
					// Deposit rentPrice to owner
					shipping.getOwner().getAccount().deposit(rentPrice);
					
					// Send updates to GUIController
					guiController.updatePlayerBalance(shipping.getOwner().getGuiId(), shipping.getOwner().getAccount().getBalance());
					guiController.updatePlayerBalance(currentPlayer.getGuiId(), currentPlayer.getAccount().getBalance());
					
				}
				else { // We can't afford landing here
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
						shipping.getOwner().getAccount().deposit(rentPrice);
						
						// Sends updates to GUIController
						guiController.updatePlayerBalance(shipping.getOwner().getGuiId(), shipping.getOwner().getAccount().getBalance());
						guiController.updatePlayerBalance(currentPlayer.getGuiId(), currentPlayer.getAccount().getBalance());
					}
				}
			}
		}
	}
}
