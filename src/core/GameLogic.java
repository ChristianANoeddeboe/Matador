package core;

import core.ChanceCardLogic.ChanceCardController;

/**
 * 
 * @author Mathias Thejsen s175192 && Simon Hansen s175191
 *
 */
public class GameLogic {
	private DiceCup	diceCup;
	private GUIController guiController;
	private Field[] fields;
	private int dice1value;
	private int dice2value;
	private int totalFaceValue = dice1value+dice2value;
	private PrisonController prisonController;
	private FieldController fieldController;
	private ChanceCardController chanceCardController;
	private PlayerController playerController;
	/**
	 * Constructor for gamelogic
	 */
	public GameLogic() {
		guiController = guiController.getInstance();
		diceCup = new DiceCup(2);

	}

	protected boolean callLogic(PlayerController playerController, Player currentPlayer) {
		fieldController = guiController.getFieldController();
		fields =fieldController.getFieldArr();
		this.playerController = playerController;

		String response = "Afslut tur";
		Street[] buildablestreets= fieldController.allFieldsToBuildOn(currentPlayer);
		if(buildablestreets.length > 0) {
			response = response + ",Buy house/hotel";
		}
		if(!currentPlayer.isRolled()) {
			response = response + ",Roll dice";
		}
		String choices[] = response.split(",");

		do {
			switch(guiController.requestPlayerChoiceButtons("It is " + currentPlayer.getName() + "'s turn, choose option:", choices)) {
			case "Roll dice" : {
				diceCup.roll();
				guiController.getInstance().showDice(diceCup);
				//save start position and set new end position
				System.out.println(diceCup.getTotalFaceValue());
				currentPlayer.setStartPosition(currentPlayer.getEndPosition());
				if(( currentPlayer.getEndPosition() + diceCup.getTotalFaceValue() ) > 39) {
					currentPlayer.setEndPosition(currentPlayer.getEndPosition() + diceCup.getTotalFaceValue() - 40);
				}else {
					currentPlayer.setEndPosition(currentPlayer.getEndPosition() + diceCup.getTotalFaceValue());

				}
				guiController.updatePlayerPosition(currentPlayer.getGuiId(), currentPlayer.getEndPosition(), currentPlayer.getStartPosition());
				if(passedStart(currentPlayer)) {
					guiController.updatePlayerBalance(currentPlayer.getGuiId(), currentPlayer.getAccount().getBalance());
				}
				if(!diceCup.isPair()) {
					currentPlayer.setRolled(true);
				}else {
					currentPlayer.setPairs(currentPlayer.getPairs()+1);
					if (currentPlayer.getPairs() >= 3) {
						currentPlayer.setPairs(0);
						guiController.writeMessage("You rolled 3 pairs in a row and were jailed");
						PrisonController prisonController = new PrisonController(currentPlayer, diceCup, chanceCardController);
						prisonController.jailPlayer(currentPlayer);
					}
				}
				findLogic(currentPlayer, diceCup);



				return false;
			}
			case "Buy house/hotel" : {
				BuyLogic buyLogic = new BuyLogic(currentPlayer, fields[currentPlayer.getEndPosition()]);
				String houseList = guiController.requestPlayerChoice("Vælg grund at bygge huse på", buyLogic.listOfFieldsYouCanBuildOn(buildablestreets));
				for (int j = 0; j < fields.length; j++) {
					if(fields[j].getName() == houseList) {
						buyLogic.houseBuyLogic(fields[j]);
						break;
					}
				}
				guiController.writeMessage("Du har købt et hus på..."+houseList);				
				return false;
			}

			case "Afslut tur":{
				return true;
			}

			}
		} while (diceCup.isPair());
		return true;

	}





	/**
	 * Called by the gamecontroller, this switch checks what kind of field we land on, and then calls a respective logic switch
	 * @param currentPlayer
	 * @return A message to the gamecontroller
	 */
	protected void findLogic(Player currentPlayer, DiceCup diceCup) {
		int id = currentPlayer.getEndPosition();
		if (fields[id] instanceof Street) { 
			StreetController streetController = new StreetController(currentPlayer, fields[id]);
			streetController.logic();
		} else if (fields[id] instanceof Brewery) {
			BreweryLogic breweryLogic = new BreweryLogic(currentPlayer, diceCup.getTotalFaceValue(), fields);
			breweryLogic.logic();
		} else if (fields[id] instanceof Chance) {
			ChanceCardController cardController = new ChanceCardController();
			cardController.getCard(currentPlayer, fields, playerController.getPlayers());
		} else if (fields[id] instanceof Shipping) {
			ShippingController shippingController = new ShippingController(currentPlayer, diceCup.getTotalFaceValue(), fields);
			shippingController.logic();
		} else if (fields[id] instanceof Prison) {
			prisonController = new PrisonController(currentPlayer, diceCup, chanceCardController);
			prisonController.logic();
		} else if (fields[id] instanceof Parking) {
			guiController.writeMessage("You landed on parking");
		} else if (fields[id] instanceof Tax) {
			TaxLogic taxLogic = new TaxLogic(currentPlayer, fields);
			taxLogic.taxLogic();
		}
	}

	/**
	 * Updates balance if we passed start, called everytime we roll dices
	 * @param currentPlayer
	 * @return
	 */
	protected boolean passedStart(Player currentPlayer) {
		if(!currentPlayer.isStartRound()) {
			System.out.println(currentPlayer.getName() + currentPlayer.getStartPosition() + currentPlayer.getEndPosition());
			if(((diceCup.getTotalFaceValue() + currentPlayer.getStartPosition()) > 40) || currentPlayer.getStartPosition() == 0) {
				currentPlayer.getAccount().deposit(4000);
				guiController.updatePlayerBalance(currentPlayer.getGuiId(), currentPlayer.getAccount().getBalance());
				return true;
			}
		}else {
			currentPlayer.setStartRound(false);
			return false;
		}
		return false;
	}

	public PrisonController getPrisonLogic() {
		return prisonController;
	}
}

