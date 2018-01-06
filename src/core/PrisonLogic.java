package core;
/**
 * 
 * @author Mathias Thejsen s175192 && Simon Hansen s175191
 *
 */
public class PrisonLogic {
	private int id,totalFaceValue,dice1value,dice2value;
	private Player currentPlayer;
	/**
	 * Constructor for prison logic
	 * @param id
	 * @param currentPlayer
	 * @param dice1value
	 * @param dice2value
	 */
	public PrisonLogic(int id, Player currentPlayer, int dice1value, int dice2value) {
		this.id = id;
		this.currentPlayer = currentPlayer;
		this.dice1value = dice1value;
		this.dice2value = dice2value;
		this.totalFaceValue = dice1value + dice2value;
	}
	/**
	 * Decides what should happen when landing on the field
	 * @param currentPlayer
	 * @return
	 */
	protected String logic(Player currentPlayer) {
		String returnstr = ""; // Used to build a series of strings for the gui to display all options
		if(id == 30 && !currentPlayer.isPrison()){ // Checking if the player is not prisoned and if it is the right prison field
			//If we landed on prison and we are not prisoned
			if(currentPlayer.getPrisonCard() > 0) { // check if we have a prison card
				returnstr = returnstr + ",PrisonCard";
			}
			if(currentPlayer.getAccount().canAfford(1000)) { // Check if we can afford to pay the 1000 fine to get out
				returnstr = returnstr + ",CanPayFine";
			}else { // We can't pay the fine and we don't have a card, so we are prisoned
				currentPlayer.setPrison(true);
				returnstr =  returnstr + ",CanNotPayFine";
			}
			return returnstr;
		}else if(id == 30 && currentPlayer.isPrison()) { // We check if we are in prison
			if(currentPlayer.getPrisontries() < 3 ) { // If we are, then we check how many rounds/times we tried to get out
				return "PlayerRoll";
			}else {
				if(currentPlayer.getAccount().canAfford(1000)) {// If we tried too many times we just have to pay
					return "PayFineMaxTries";
				}else { // If we can't afford the 1000 fine
					return "CantAfford";
				}
			}
		}
		return "Nothing";
	}
	/**
	 * Logic for when a player uses their prison card
	 * @param currentPlayer
	 */
	protected void prisonCardLogic(Player currentPlayer) {
		if(currentPlayer.getPrisonCard() > 0) { // We make sure we have atleast 1 prison card
			currentPlayer.setPrison(false); // We use it, and thereby get out
			currentPlayer.setPrisonCard(currentPlayer.getPrisonCard() - 1); // We substract a prison card from the player
		}
	}
	/**
	 * Logic for when the player wnats to get out of jail
	 * @param currentPlayer
	 */
	protected void payPrisonLogic(Player currentPlayer) {
		currentPlayer.getAccount().withdraw(1000); // We withdraw 1000
		currentPlayer.setPrisontries(0); // Set the prison tries to 0
		currentPlayer.setPrison(false); // Put the prison state as fals
	}

	
	/**
	 * Logic for when a player wants to get out of jail
	 * @param currentPlayer 
	 */
	protected void prisonRollLogic(Player currentPlayer) {
		if(currentPlayer.getPrisontries() < 3) { // Make sure the player has not tried too many times
			if(this.dice1value == this.dice2value) { // Make sure it is a pairs
				currentPlayer.setPrisontries(0); // If it is, then we let the player out of jail
				currentPlayer.setPrison(false);
			}
		}
	}


}
