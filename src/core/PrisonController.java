package core;

import core.ChanceCardLogic.ChanceCardController;
/**
 *
 * @author Christian Stahl Andersen s164150
 *
 */
public class PrisonController {

	private int id;
	private Player currentPlayer;
	private DiceCup diceCup;
	private GUIController guiController = GUIController.getInstance();
	private ChanceCardController chanceCardController;
	private SalesLogic salesLogic;

	/**
	 *
	 * @param currentPlayer
	 * @param diceCup
	 */
	PrisonController(Player currentPlayer, DiceCup diceCup, ChanceCardController chanceCardController) {
		this.id = id;
		this.currentPlayer = currentPlayer;
		this.diceCup = diceCup;
		this.chanceCardController = chanceCardController;
	}

	public void logic(Player currentPlayer) {
		if (id == 10) {
			if (currentPlayer.isPrison()) {
				inPrisonLogic(currentPlayer);
			} else {
				guiController.writeMessage("TODO Du besøger fænglset. Se lige de undermålere bag tremmer. Ha Ha Ha Ha");
			}
		}
		if (id == 30) {
			landGoToPrison(currentPlayer);
		}
	}

	private void landGoToPrison(Player currentPlayer) {
		switch (getPlayerChoice(0)) {
			case "Betal kr. 1000":
				currentPlayer.getAccount().withdraw(1000);
				guiController.updatePlayerBalance(currentPlayer.getGuiId(), currentPlayer.getAccount().getBalance());
				break;
			case "Benyt fængselskort":
				chanceCardController.discardPrisonCard();
				currentPlayer.setPrisonCard(currentPlayer.getPrisonCard()-1);
				guiController.writeMessage("Du brugte et fængselskort og har "+currentPlayer.getPrisonCard()+" kort tilbage.");
				break;
			case "Gå i fængsel":
				guiController.jailPlayer(currentPlayer.getGuiId(), currentPlayer.getEndPosition(), 10);
				currentPlayer.setEndPosition(10);
				currentPlayer.setPrison(true);
				break;
			default:
				break;
		}
	}

	private void inPrisonLogic(Player currentPlayer) {
		switch (getPlayerChoice(1)) {
			case "Rul terningerne":
				diceCup.roll();
				if (diceCup.isPair()) {
					currentPlayer.setPrison(false);
				}
				break;
			case "Betal kr. 1000":
				currentPlayer.getAccount().withdraw(1000);
				guiController.updatePlayerBalance(currentPlayer.getGuiId(), currentPlayer.getAccount().getBalance());
				break;
			case "Benyt fængselskort":
				chanceCardController.discardPrisonCard();
				currentPlayer.setPrisonCard(currentPlayer.getPrisonCard()-1);
				guiController.writeMessage("Du brugte et fængselskort og har "+currentPlayer.getPrisonCard()+" kort tilbage.");
				break;
			default:
				break;
		}
	}

	private String getPlayerChoice(int state) {
		String choices = "";
		String[] choiceArr;
		switch (state) {
			case 0:
				if (currentPlayer.getAccount().canAfford(1000) || currentPlayer.getPrisontries() == 3) {
					choices = choices + ",Betal kr. 1000";
				}
				if (currentPlayer.getPrisonCard() > 0) {
					choices = choices + ",Benyt fængselskort";
				}
				choices = choices + ",Gå i fængsel";
				if (choices.startsWith(",")) choices = choices.substring(1);
				choiceArr = choices.split(",");
				return guiController.requestPlayerChoiceButtons("TODO Du landede på 'Gå i fængsel'. Vælg hvad du vil.", choiceArr);
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
				while (!currentPlayer.getAccount().canAfford(1000) && currentPlayer.getPrisontries() == 3) {
					if (!salesLogic.pawnProperty()) {
						currentPlayer.setBanktrupt(true);
					}
				}
				if (choices.startsWith(",")) choices = choices.substring(1);
				choiceArr = choices.split(",");
				return guiController.requestPlayerChoiceButtons("TODO Du er i fængsel. Vælg hvad du vil.", choiceArr);
			default:
				return null;
		}
	}
}