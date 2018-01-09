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
	/**
	 * Constructor for gamelogic
	 */
	public GameLogic() {
		guiController = guiController.getInstance();
		diceCup = new DiceCup(2);
	}

	protected void callLogic(PlayerController playerController, Player currentPlayer) {
		fieldController = guiController.getFieldController();
		fields =fieldController.getFieldArr();
		String choices[] = {"Roll dice"};
		for (int i = 0; i < fields.length; i++) {
			if(fields[i] instanceof Street && currentPlayer.getName().equals("player1")) {
				((Street) fields[i]).setOwner(currentPlayer);
			}
		}
		System.out.println("Blabla: "+fieldController.allFieldsToBuildOn(currentPlayer).length);
		Street[] buildablestreets= fieldController.allFieldsToBuildOn(currentPlayer);
		if(buildablestreets.length > 0) {
			String choices2[] = {"Roll dice","Buy house/hotel"};
			choices = choices2;
		}
		do {
			switch(guiController.requestPlayerChoice("It is " + currentPlayer.getName() + "'s turn, choose option:", choices)) {
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
				findLogic(currentPlayer, diceCup);

				break;
			}
			case "Buy house/hotel" : {
				BuyLogic buyLogic = new BuyLogic();
				String response = guiController.requestPlayerChoice("Vælg grund at bygge huse på", buyLogic.listOfFieldsYouCanBuildOn(buildablestreets));
				for (int j = 0; j < fields.length; j++) {
					if(fields[j].getName() == response) {
						buyLogic.buyHouse(fields[j], currentPlayer);
					}
				}
				guiController.writeMessage("Du har købt et hus på..."+response);				
				break;
			}
			
			}
		} while (diceCup.isPair());

	}





	/**
	 * Called by the gamecontroller, this switch checks what kind of field we land on, and then calls a respective logic switch
	 * @param currentPlayer
	 * @return A message to the gamecontroller
	 */
	protected void findLogic(Player currentPlayer, DiceCup diceCup) {
		int id = currentPlayer.getEndPosition();
		if (fields[id] instanceof Street) { 
			StreetLogic streetLogic = new StreetLogic(currentPlayer, fields[id]);
			streetLogic.logic();
		} else if (fields[id] instanceof Brewery) {
			BreweryLogic breweryLogic = new BreweryLogic(currentPlayer, diceCup.getTotalFaceValue(), fields);
		} else if (fields[id] instanceof Chance) {
		} else if (fields[id] instanceof Shipping) {
			ShippingLogic shippingLogic = new ShippingLogic(currentPlayer, diceCup.getTotalFaceValue(), fields);
		} else if (fields[id] instanceof Prison) {
			prisonController = new PrisonController(currentPlayer, diceCup, chanceCardController);
		} else if (fields[id] instanceof Parking) {
			//TODO
		} else if (fields[id] instanceof Tax) {
			TaxLogic taxLogic = new TaxLogic(currentPlayer, fields);
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

