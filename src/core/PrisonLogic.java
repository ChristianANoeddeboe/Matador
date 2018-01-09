package core;
/**
 *
 * @author Mathias Thejsen s175192 && Simon Hansen s175191
 *
 */
public class PrisonLogic {
	private int id;
	private Player currentPlayer;
	private DiceCup diceCup;
	private GUIController guiController = GUIController.getInstance();

	/**
	 *
	 * @param currentPlayer
	 * @param diceCup
	 */
	public PrisonLogic(int id, Player currentPlayer, DiceCup diceCup) {
		this.id = id;
		this.currentPlayer = currentPlayer;
		this.diceCup = diceCup;
	}

	public void logic(Player currentPlayer) {
		if (id == 10) {
			if (currentPlayer.isPrison()) {
				inPrisonLogic(currentPlayer);
			} else {
				guiController.writeMessage("TODO Du besøger fænglset. Se lige de undermålere bag tremmer. Ha Ha Ha Ha");
			}

		}

	}

	private void inPrisonLogic(Player currentPlayer) {
		switch (getPlayerChoice(1)) {

		}
	}

	public String getPlayerChoice(int state) {
		String choices = "";
		String[] choiceArr;
		switch (state) {
			case 0:
				if (currentPlayer.getAccount().canAfford(1000)) {
					choices = choices + ",Betal kr. 1000";
				}
				if (currentPlayer.getPrisonCard() > 0) {
					choices = choices + ",Benyt fængselskort";
				}
				choices = choices + ",Benyt fængselskort";
				if (choices.startsWith(",")) choices = choices.substring(1);
				choiceArr = choices.split(",");
				return guiController.requestPlayerChoiceButtons("TODO Du er i fængsel. Vælg hvad du vil.")
				break;
			case 1:
				if (currentPlayer.getPrisontries() < 3) {
					choices = choices + ",Rul terningerne";
				}
				if (currentPlayer.getAccount().canAfford(1000)) {
					choices = choices + ",Betal kr. 1000";
				}
				if (currentPlayer.getPrisonCard() > 0) {
					choices = choices + ",Benyt fængselskort";
				}
				if (choices.startsWith(",")) choices = choices.substring(1);
				choiceArr = choices.split(",");
				return guiController.requestPlayerChoiceButtons("TODO Du er i fængsel. Vælg hvad du vil.")
				break;
		}


	}

	/**
	 * Decides what should happen when landing on the field
	 * @param currentPlayer
	 * @return
	 */
	/*
	protected String logic(Player currentPlayer) {
		String returnstr; // Used to build a series of strings for the gui to display all options
		if(id == 30 && !currentPlayer.isPrison()){ // Checking if the player is not prisoned and if it is the right prison field
			//If we landed on prison and we are not prisoned
			returnstr = "You landed on prison field. What do you want to do?";
			if(currentPlayer.getPrisonCard() > 0) { // check if we have a prison card
				returnstr = returnstr + ",Prison card";
			}
			if(currentPlayer.getAccount().canAfford(1000)) { // Check if we can afford to pay the 1000 fine to get out
				returnstr = returnstr + ",Pay fine";
			}
			returnstr =  returnstr + ",Go to jail";
			return returnstr;
		}else if(id == 30 && currentPlayer.isPrison()) { // We check if we are in prison
			returnstr = "You're in prison. You have "+currentPlayer.getPrisontries()+" rolls left or you can pay the fine.";
			if(currentPlayer.getPrisontries() < 3 ) { // If we are, then we check how many rounds/times we tried to get out
				returnstr = returnstr + ",Roll";
			} else {
				if(currentPlayer.getAccount().canAfford(1000)) {// If we tried too many times we just have to pay
					returnstr = returnstr + ",Pay fine";
				} else { // If we can't afford the 1000 fine
					returnstr = returnstr + ",Wait a turn";
				}
			}
			return returnstr;
		}
		return "Nothing";
	}
	*/

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
