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
	 * @param id
	 * @param currentPlayer
	 */
	public StreetController(Player currentPlayer, Field field) {
		this.currentPlayer = currentPlayer;
		this.street = (Street) field;
	}
	/**
	 * The logic when landing on a normal/property field( a field where you can put houses on)
	 * @param currentPlayer
	 * @return
	 */
	protected void logic() {
		if(street.getOwner() == null) { // Check if field is owned
			if(currentPlayer.getAccount().canAfford(street.getRentValue())) { // If it is not owned and we can afford it
				String[] choices = {"Yes", "No"};
				String result = guiController.requestPlayerChoiceButtons("Vil du købe..."+street.getName(), choices);
				if(result.equals("Yes")) {
					BuyController buyController = new BuyController(currentPlayer, street);
					buyController.buyLogic();
				}
			}
			else { 
				
			}
		}else{
			if(street.getOwner() == currentPlayer) { // Check if the player landing there is the owner itself
			}else {
				if(currentPlayer.getAccount().canAfford(street.getRentValue())) { // Field is owned by someone else, we check if they can afford landing there
					currentPlayer.getAccount().withdraw(street.getRentValue()); // They can, so we withdraw money and put it into the owners
					street.getOwner().getAccount().deposit(street.getRentValue());
					guiController.updatePlayerBalance(street.getOwner().getGuiId(), street.getOwner().getAccount().getBalance());
					guiController.writeMessage("You landed on.."+street.getOwner().getName() + "..'s field and had to pay.."+street.getRentValue());
				}else {
					SalesController salesController = new SalesController(currentPlayer);
					salesController.pawnProperty();
				}

			}
		}

	}
}


