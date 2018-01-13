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
	private AuctionController auctionController;
	private Player[] players;
	/**
	 * Constructor for normal logic
	 * @param currentPlayer
	 * @param field
	 */
	public StreetController(Player currentPlayer, Field field, Player[] players) {
		this.currentPlayer = currentPlayer;
		this.street = (Street) field;
		this.players = players;
		auctionController = new AuctionController();
	}
	/**
	 * The logic when landing on a normal/property field( a field where you can put houses on)
	 */
	public void logic() {
		// Check if field is owned
		if(street.getOwner() == null) {  
			// Check if we can afford it

			if(currentPlayer.getAccount().canAfford(street.getBuyValue())) {

				// Prompt the player for a choice
				String[] choices = {PropertiesIO.getTranslation("yesbutton"), PropertiesIO.getTranslation("nobutton")};
				String result = guiController.requestPlayerChoiceButtons(PropertiesIO.getTranslation("streetlandonbuy")+" "+street.getName()+ " for " + street.getBuyValue(), choices);
				
				// Check if choice is Yes
				if(result.equals(PropertiesIO.getTranslation("yesbutton"))) {
					
					// Initialize buyController
					BuyController buyController = new BuyController(currentPlayer, street);
					
					// Run the buyLogic method
					buyController.buyLogic();
				}
				else { // If the player does not wish to buy the field
					auctionController.startAuction(currentPlayer, street, players);
				}
			}
			else { // If the player cannot afford the field
				auctionController.startAuction(currentPlayer, street, players);
			}
		}
		else{ // The field is owned
			
			// Check if the player landing there is the owner itself
			if(street.getOwner() != currentPlayer || street.isPawned()){
				// If field is owned by someone else, we check if they can afford landing there
				if(currentPlayer.getAccount().canAfford(street.getRentValue())) { 
					// Show information to player
					guiController.writeMessage(PropertiesIO.getTranslation("streetlanddon")+" "+street.getName() +" " + PropertiesIO.getTranslation("streetlanddon2") + " "+street.getRentValue()+" til "+street.getOwner().getName());
					// Withdraw rentValue from the player
					currentPlayer.getAccount().withdraw(street.getRentValue());
					
					// Deposit rentValue to the owner
					street.getOwner().getAccount().deposit(street.getRentValue());
					
					// Sends updates to GUIController
					guiController.updatePlayerBalance(street.getOwner().getGuiId(), street.getOwner().getAccount().getBalance());
					guiController.updatePlayerBalance(currentPlayer.getGuiId(), currentPlayer.getAccount().getBalance());

					
				}
				else { // Can't afford
					guiController.writeMessage(PropertiesIO.getTranslation("streetcantafford"));
					// Initialize the SalesController
					SalesController salesController = new SalesController(currentPlayer);
					
					// Run cannotAfford method
					boolean response = salesController.cannotAfford(street.getRentValue());
					if(response) {
						guiController.writeMessage(PropertiesIO.getTranslation("streetcanpayrent")+ " "+street.getRentValue());
						
						// Withdraw rentValue from the player
						currentPlayer.getAccount().withdraw(street.getRentValue());
						
						// Deposit rentValue to the owner
						street.getOwner().getAccount().deposit(street.getRentValue());
						
						// Sends updates to GUIController
						guiController.updatePlayerBalance(street.getOwner().getGuiId(), street.getOwner().getAccount().getBalance());
						guiController.updatePlayerBalance(currentPlayer.getGuiId(), currentPlayer.getAccount().getBalance());
					}
				}
			}
		}
	}
}


