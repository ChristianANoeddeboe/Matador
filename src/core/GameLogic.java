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
		
		int counter = 0;
		String choicesArr[] = new String[5];
		if(currentPlayer.isPrison()) {
			prisonController.inPrisonLogic(currentPlayer);
			if(!currentPlayer.isPrison()) {
				updatePos(currentPlayer);
				
				passedStart(currentPlayer);
				
				checkIfExtraRound(currentPlayer);
				
				findLogic(currentPlayer, diceCup);

				return false;
			}
		} else {
			//Add end turn if player has rolled
			if(currentPlayer.isRolled())
				choicesArr[counter++] = "Afslut tur";
			
			//Add buy house or hotel
			Street[] buildablestreets= fieldController.allFieldsToBuildOn(currentPlayer);
			if(buildablestreets.length > 0)
				choicesArr[counter++] = "Buy house/hotel";
			
			//Add Roll dice if not already rolled
			if(!currentPlayer.isRolled())
				choicesArr[counter++] = "Roll dice";
			
			//Move to new array
			String choices[] = new String[counter];
			for (int i = 0 ; i < counter ; i++)
				choices[i] = choicesArr[i];
	
			do {
				switch(guiController.requestPlayerChoiceButtons("It is " + currentPlayer.getName() + "'s turn, choose option:", choices)) {
				case "Roll dice" : {
					
					diceCup.roll();
					guiController.showDice(diceCup);
					
					updatePos(currentPlayer);
					
					passedStart(currentPlayer);
					
					checkIfExtraRound(currentPlayer);
					
					findLogic(currentPlayer, diceCup);

					return false;
				}
				case "Buy house/hotel" : {
					BuyController buyController = new BuyController(currentPlayer, fields[currentPlayer.getEndPosition()]);
					String houseList = guiController.requestPlayerChoice("Vælg grund at bygge huse på", buyController.listOfFieldsYouCanBuildOn(buildablestreets));
					for (int j = 0; j < fields.length; j++) {
						if(fields[j].getName() == houseList) {
							buyController.houseBuyLogic(fields[j]);
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
		}
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
			BreweryController breweryController = new BreweryController(currentPlayer, diceCup.getTotalFaceValue(), fields);
			breweryController.logic();
		} else if (fields[id] instanceof Chance) {
			System.out.println("Chance");
			ChanceCardController cardController = new ChanceCardController();
			cardController.getCard(currentPlayer, fields, playerController.getPlayers());
		} else if (fields[id] instanceof Shipping) {
			ShippingController shippingController = new ShippingController(currentPlayer, diceCup.getTotalFaceValue(), fields);
			shippingController.logic();
		} else if (fields[id] instanceof Prison) {
			ChanceCardController chanceCardController = new ChanceCardController();
			prisonController = new PrisonController(currentPlayer, diceCup, chanceCardController);
			prisonController.logic();
		} else if (fields[id] instanceof Parking) {
			guiController.writeMessage("You landed on parking");
		} else if (fields[id] instanceof Tax) {
			TaxLogic taxLogic = new TaxLogic(currentPlayer, fields);
			taxLogic.taxLogic();
		}
	}
	private void updatePos(Player currentPlayer) {
		//save start position and set new end position
		System.out.println(diceCup.getTotalFaceValue());
		currentPlayer.setStartPosition(currentPlayer.getEndPosition());
		if(( currentPlayer.getEndPosition() + diceCup.getTotalFaceValue() ) > 39) {
			currentPlayer.setEndPosition(currentPlayer.getEndPosition() + diceCup.getTotalFaceValue() - 40);
		}else {
			currentPlayer.setEndPosition(currentPlayer.getEndPosition() + diceCup.getTotalFaceValue());

		}
		guiController.updatePlayerPosition(currentPlayer.getGuiId(), currentPlayer.getEndPosition(), currentPlayer.getStartPosition());
	}
	
	private void checkIfExtraRound(Player currentPlayer) {
		//check if player will get another turn because of pairs
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
	}
	

	/**
	 * Updates balance if we passed start, called everytime we roll dices
	 * @param currentPlayer
	 */
	protected void passedStart(Player currentPlayer) {
		boolean passed = false;
		if(!currentPlayer.isStartRound()) {
			System.out.println(currentPlayer.getName() + currentPlayer.getStartPosition() + currentPlayer.getEndPosition());
			if(((diceCup.getTotalFaceValue() + currentPlayer.getStartPosition()) > 40) || currentPlayer.getStartPosition() == 0) {
				currentPlayer.getAccount().deposit(4000);
				guiController.updatePlayerBalance(currentPlayer.getGuiId(), currentPlayer.getAccount().getBalance());
				passed = true;
			}
		} else {
			currentPlayer.setStartRound(false);
			passed = false;
		}
		
		//update balance if start is passed
		if(passed)
			guiController.updatePlayerBalance(currentPlayer.getGuiId(), currentPlayer.getAccount().getBalance());	
	}
	
	

	public PrisonController getPrisonLogic() {
		return prisonController;
	}
}

