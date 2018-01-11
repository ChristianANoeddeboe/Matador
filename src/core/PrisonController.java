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
	private SalesController salesController;

	/**
	 * Constructor for the prisonController.
	 * @param currentPlayer the player who is to be handled at the moment.
	 * @param diceCup the diceCup for the project.
	 * @param chanceCardController
	 */
	PrisonController(Player currentPlayer, DiceCup diceCup, ChanceCardController chanceCardController) {
		this.currentPlayer = currentPlayer;
		this.diceCup = diceCup;
		this.chanceCardController = chanceCardController;
	}

	public void logic() {
		int pos = currentPlayer.getEndPosition();
		if (pos == 10 && !currentPlayer.isPrison()) {
			guiController.writeMessage(PropertiesIO.getTranslation("prisonvisit"));
		} else {
			prison(currentPlayer);
		}
	}

	public void prison(Player currentPlayer) {
		int state = 0;
		if (currentPlayer.isPrison()) {
			state = 1;
		}
		String choice = getPlayerChoice(state);
		if (choice.equals(PropertiesIO.getTranslation("prisonchoiceinprison"))) {
			rollJailDice(currentPlayer);
		} else if (choice.equals(PropertiesIO.getTranslation("prisonchoice1"))) {
			payFine(currentPlayer);
		} else if (choice.equals(PropertiesIO.getTranslation("prisonchoice2"))) {
			payFine(currentPlayer);
		} else if (choice.equals(PropertiesIO.getTranslation("prisongoto"))) {
			jailPlayer(currentPlayer);
		}
	}

	public void jailPlayer(Player currentPlayer) {
		guiController.jailPlayer(currentPlayer.getGuiId(), currentPlayer.getEndPosition(), 10);
		currentPlayer.setEndPosition(10);
		currentPlayer.setPrison(true);
	}

	public void payFine(Player currentPlayer) {
		currentPlayer.getAccount().withdraw(1000);
		guiController.updatePlayerBalance(currentPlayer.getGuiId(), currentPlayer.getAccount().getBalance());
		diceCup.roll();
		if (currentPlayer.isPrison()) {
			releasePrison(currentPlayer);
		}
	}

	public void usePrisonCard(Player currentPlayer) {
		chanceCardController.putPrisonCardInDeck();
		currentPlayer.setPrisonCard(currentPlayer.getPrisonCard()-1);
		guiController.writeMessage(PropertiesIO.getTranslation("prisoncarduse")+currentPlayer.getPrisonCard());
		if (currentPlayer.isPrison()) {
			releasePrison(currentPlayer);
		}
	}

	/**
	 * If the player is in prison, he will be released properly.
	 * @param currentPlayer
	 */
	private void releasePrison(Player currentPlayer) {
		currentPlayer.setPrison(false);
		guiController.writeMessage(PropertiesIO.getTranslation("prisonrollmessage"));
		diceCup.roll();
		guiController.showDice(diceCup);
	}

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
			guiController.writeMessage(PropertiesIO.getTranslation("prisonpairrollnegative"+(3-currentPlayer.getPrisontries())));
		}
	}

	public String getPlayerChoice(int state) {
		salesController = new SalesController(currentPlayer);

		String choices = "";
		String[] choiceArr;
		switch (state) {
			case 0:
				if (currentPlayer.getAccount().canAfford(1000) || currentPlayer.getPrisontries() == 3) {
					choices = choices + "," + PropertiesIO.getTranslation("prisonchoice1");
				}
				if (currentPlayer.getPrisonCard() > 0) {
					choices = choices + ",Benyt fængselskort";
				}
				choices = choices + ",Gå i fængsel";
				if (choices.startsWith(",")) choices = choices.substring(1);
				choiceArr = choices.split(",");
				choices = guiController.requestPlayerChoiceButtons(PropertiesIO.getTranslation("prisonlandgotomessage"), choiceArr);
			case 1:
				if (currentPlayer.getPrisontries() < 3) {
					System.out.println(currentPlayer.getPrisontries());
					choices = choices + ",Rul terningerne";
				}
				if (currentPlayer.getAccount().canAfford(1000) && currentPlayer.getPrisontries() > 0) {
					choices = choices + ",Betal kr. 1000";
				}
				if (currentPlayer.getPrisonCard() > 0) {
					choices = choices + ",Benyt fængselskort";
				}
				while (!currentPlayer.getAccount().canAfford(1000) && currentPlayer.getPrisontries() == 3) {
					salesController.cannotAfford(1000);
				}
				if (choices.startsWith(",")) choices = choices.substring(1);
				choiceArr = choices.split(",");
				return guiController.requestPlayerChoiceButtons(PropertiesIO.getTranslation("prisongenericmessage"), choiceArr);
			default:
				return null;
		}
	}
}