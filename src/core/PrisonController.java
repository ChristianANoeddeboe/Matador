package core;

import core.ChanceCardLogic.ChanceCardController;
/**
 *
 * @author Christian Stahl Andersen s164150
 *
 */
public class PrisonController {

	private Player currentPlayer;
	private DiceCup diceCup;
	private GUIController guiController = GUIController.getInstance();
	private ChanceCardController chanceCardController;

	/**
	 * Constructor for the prisonController.
	 * @param currentPlayer the player who is to be handled at the moment.
	 * @param diceCup the diceCup for the project.
	 * @param chanceCardController the controller for the chance cards, to add support for prison cards.
	 */
	PrisonController(Player currentPlayer, DiceCup diceCup, ChanceCardController chanceCardController) {
		this.currentPlayer = currentPlayer;
		this.diceCup = diceCup;
		this.chanceCardController = chanceCardController;
	}

	/**
	 * Determines if the player is visiting prison, in prison or about to be sent to prison.
	 */
	public void logic() {
		int pos = currentPlayer.getEndPosition();
		if (pos == 10 && !currentPlayer.isPrison()) {
			guiController.writeMessage(PropertiesIO.getTranslation("prisonvisit"));
		} else {
			prison(currentPlayer);
		}
	}

	/**
	 * basic logic for the player, gets an input, and selects the method to use.
	 * @param currentPlayer the player who will choose an action.
	 */
	public void prison(Player currentPlayer) {
		String choice = getPlayerChoice();
		if (choice.equals(PropertiesIO.getTranslation("throwdice"))) {
			rollJailDice(currentPlayer);
		} else if (choice.equals(PropertiesIO.getTranslation("prisonchoice1"))) {
			payFine(currentPlayer);
		} else if (choice.equals(PropertiesIO.getTranslation("prisonchoice2"))) {
			usePrisonCard(currentPlayer);
		} else if (choice.equals(PropertiesIO.getTranslation("prisongoto"))) {
			jailPlayer(currentPlayer);
		}
	}

	/**
	 * Jails the player.
	 * @param currentPlayer the player to be moved and jailed.
	 */
	public void jailPlayer(Player currentPlayer) {
		guiController.jailPlayer(currentPlayer.getGuiId(), currentPlayer.getEndPosition(), 10);
		currentPlayer.setEndPosition(10);
		currentPlayer.setPrison(true);
	}

	/**
	 * Pays 1000 to release the player from prison.
	 * @param currentPlayer the playere to be released
	 */
	public void payFine(Player currentPlayer) {
		currentPlayer.getAccount().withdraw(1000);
		guiController.updatePlayerBalance(currentPlayer.getGuiId(), currentPlayer.getAccount().getBalance());
		diceCup.roll();
		if (currentPlayer.isPrison()) {
			releasePrison(currentPlayer);
		}
	}

	/**
	 * Uses a prison card for the player.
	 * @param currentPlayer the player to be released from prison.
	 */
	public void usePrisonCard(Player currentPlayer) {
		chanceCardController.putPrisonCardInDeck();
		currentPlayer.setPrisonCard(currentPlayer.getPrisonCard()-1);
		guiController.writeMessage(PropertiesIO.getTranslation("prisonusecard")+currentPlayer.getPrisonCard());
		if (currentPlayer.isPrison()) {
			releasePrison(currentPlayer);
		}
	}

	/**
	 * If the player is in prison, he will be released properly.
	 * @param currentPlayer the active player.
	 */
	private void releasePrison(Player currentPlayer) {
		currentPlayer.setPrison(false);
		guiController.writeMessage(PropertiesIO.getTranslation("prisonrollmessage"));
		diceCup.roll();
		guiController.showDice(diceCup);
	}

	/**
	 * Rolls the dice in the jail and releases him, if he gets a pair.
	 * @param currentPlayer the player to roll for.
	 */
	public void rollJailDice(Player currentPlayer) {
		diceCup.roll();
		guiController.showDice(diceCup);
		currentPlayer.setPrisontries(currentPlayer.getPrisontries()+1);
		System.out.println(currentPlayer.getPrisontries());
		if (diceCup.isPair()) {
		    guiController.writeMessage(PropertiesIO.getTranslation("prisonpairroll"));
			currentPlayer.setPrison(false);
			currentPlayer.setPrisontries(0);
		} else {
			guiController.writeMessage(PropertiesIO.getTranslation("prisonroll")+(3-currentPlayer.getPrisontries()));
		}
	}

	/**
	 * Creates a menu with valid choices for the player.
	 * @return String with the text the player choose.
	 */
	public String getPlayerChoice() {
		SalesController salesController = new SalesController(currentPlayer);
		String choices = "";
		String[] choiceArr;
		if (currentPlayer.isPrison()) {
			if (currentPlayer.getPrisontries() < 3) {
				choices = choices + "," + PropertiesIO.getTranslation("throwdice");
			}
			if (currentPlayer.getAccount().canAfford(1000) && currentPlayer.getPrisontries() == 0 || currentPlayer.getPrisontries() == 3) {
				choices = choices + "," + PropertiesIO.getTranslation("prisonchoice1");
			}
			if (currentPlayer.getPrisonCard() > 0) {
				choices = choices + "," + PropertiesIO.getTranslation("prisonchoice2");
			}
			while (!currentPlayer.getAccount().canAfford(1000) && currentPlayer.getPrisontries() == 3) {
				salesController.cannotAfford(1000);
			}
			if (choices.startsWith(",")) choices = choices.substring(1);
			choiceArr = choices.split(",");
			return guiController.requestPlayerChoiceButtons(PropertiesIO.getTranslation("prisongenericmessage"), choiceArr);
		} else {
			if (currentPlayer.getAccount().canAfford(1000) || currentPlayer.getPrisontries() == 3) {
				choices = choices + "," + PropertiesIO.getTranslation("prisonchoice1");
			}
			if (currentPlayer.getPrisonCard() > 0) {
				choices = choices + "," + PropertiesIO.getTranslation("prisonchoice2");
			}
			choices = choices + "," + PropertiesIO.getTranslation("prisongoto");
			if (choices.startsWith(",")) choices = choices.substring(1);
			choiceArr = choices.split(",");
			return guiController.requestPlayerChoiceButtons(PropertiesIO.getTranslation("prisonlandgotomessage"), choiceArr);
		}
	}
}