package core;

import core.ChanceCardLogic.ChanceCardController;

/**
 * 
 * @author Mathias Thejsen s175192 && Simon Hansen s175191
 *
 */
public class GameLogic {
	private DiceCup	diceCup;
	private GUIController guiController = GUIController.getInstance();
	private Field[] fields;
	private PrisonController prisonController;
	private FieldController fieldController;
	private ChanceCardController cardController;
	private PlayerController playerController;
	private TradeController tradeController;
	/**
	 * Constructor for gamelogic
	 */
	public GameLogic() {
		cardController = new ChanceCardController();
		diceCup = new DiceCup(2);
		tradeController = new TradeController();
	}

	/**
	 * Generates a list of options for the player
	 * @param playerController
	 * @param currentPlayer
	 * @return true if their turn is over 
	 */
	public boolean showOptions(PlayerController playerController, Player currentPlayer) {
		fieldController = guiController.getFieldController();
		fields =fieldController.getFieldArr();
		BuyController buyController = new BuyController(currentPlayer);
		SalesController salesController = new SalesController(currentPlayer);
		this.playerController = playerController;
		int counter = 0;
		String choicesArr[] = new String[5];
		if(currentPlayer.isPrison()) { // Check if player is prisoned
			prisonController.prison(currentPlayer);
			if(!currentPlayer.isPrison()) {
				updatePos(currentPlayer);

				passedStart(currentPlayer);

				checkIfExtraRound(currentPlayer);

				resolveField(currentPlayer, diceCup);

				return false;
			}
		} else {
			//Add end turn if player has rolled
			if(currentPlayer.isRolled()) {
				choicesArr[counter++] = "Afslut tur";
			}
			//Add buy house or hotel
			Street[] buildablestreets= fieldController.allFieldsToBuildOn(currentPlayer);
			if(buildablestreets.length > 0) {
				choicesArr[counter++] = "Køb huse/hoteller";
			}
			//Add Roll dice if not already rolled
			if(!currentPlayer.isRolled()) {
				choicesArr[counter++] = "Rul terningerne";
			}
			
			if(fieldController.propertiesToPawn(currentPlayer).length > 0) { // Check if the player has anything to pawn
				choicesArr[counter++] = "Pantsæt grund";	
			}
			if(fieldController.pawnedFields(currentPlayer).length > 0) { // Check if the player has any pawned property
				choicesArr[counter++] = "Fjern pantsætning";
			}
			if(fieldController.tradePossible(currentPlayer)) { // Check if there are any propreties to trade with
				choicesArr[counter++] = "Byt grunde";
			}

			//Move to new array
			String choices[] = new String[counter];
			for (int i = 0 ; i < counter ; i++)
				choices[i] = choicesArr[i];

			do {
				switch(guiController.requestPlayerChoiceButtons(PropertiesIO.getTranslation("turn1") + currentPlayer.getName() + PropertiesIO.getTranslation("turn2"), choices)) {
				case "Rul terningerne" : {

					diceCup.roll(); // roll dices
					guiController.showDice(diceCup); // show the dices on the gui

					updatePos(currentPlayer); // update pos

					passedStart(currentPlayer); // check if we passed start if so give money

					resolveField(currentPlayer, diceCup); // call some logic on the field

					if(currentPlayer.isMoved()) { // check if the player was moved with a chance card
						resolveField(currentPlayer, diceCup);
						currentPlayer.setMoved(false);
					}
					if(currentPlayer.isBanktrupt() || currentPlayer.isPrison() || checkIfExtraRound(currentPlayer)) { // Check if player is bankrupt, prisoned or if he deserves an extra round
						return true;
					}

					return false;
				}
				case "Køb huse/hoteller" : {
					buildablestreets = fieldController.allFieldsToBuildOn(currentPlayer); // Grab list of buildable streets
					buyController = new BuyController(currentPlayer, fields[currentPlayer.getEndPosition()]); // initialize buycontroller
					String houseList = guiController.requestPlayerChoiceDropdown(PropertiesIO.getTranslation("chooseproperty"), buyController.listOfFieldsYouCanBuildOn(buildablestreets)); // The property a player chose
					for (int j = 0; j < fields.length; j++) {
						if(fields[j].getName() == houseList) { // Find the specificed house
							buyController.houseBuyLogic(fields[j]); // call housebuylogic which adds the house
							break;
						}
					}
					guiController.writeMessage(PropertiesIO.getTranslation("boughthouseon")+houseList);				
					return false;
				}

				case "Pantsæt grund":{
					salesController.pawnProperty(); // Call pawn property
					return false;
				}

				case "Afslut tur":{
					return true; // end turn
				}

				case "Fjern pantsætning":{
					buyController.unPawnProperty();
					return false;
				}

				case "Byt grunde": {
					tradeController.startTrade(currentPlayer, fieldController, playerController.getPlayers());
					return false;
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
	public void resolveField(Player currentPlayer, DiceCup diceCup) {
		int id = currentPlayer.getEndPosition();
		if (fields[id] instanceof Street) { 
			StreetController streetController = new StreetController(currentPlayer, fields[id], playerController.getPlayers());
			streetController.logic();
		} else if (fields[id] instanceof Brewery) {
			BreweryController breweryController = new BreweryController(currentPlayer, diceCup.getTotalFaceValue(), fields, playerController.getPlayers());
			breweryController.logic();
		} else if (fields[id] instanceof Chance) {
			cardController.getCard(currentPlayer, fields, playerController.getPlayers());
		} else if (fields[id] instanceof Shipping) {
			ShippingController shippingController = new ShippingController(currentPlayer, diceCup.getTotalFaceValue(), fields, playerController.getPlayers());
			shippingController.logic();
		} else if (fields[id] instanceof Prison) {
			prisonController = new PrisonController(currentPlayer, diceCup, cardController);
			prisonController.logic();
		} else if (fields[id] instanceof Parking) {
			guiController.writeMessage(PropertiesIO.getTranslation("landonparking"));
		} else if (fields[id] instanceof Tax) {
			TaxController taxController = new TaxController(currentPlayer, fields);
			taxController.taxLogic();
		}
	}
	private void updatePos(Player currentPlayer) {
		//save start position and set new end position
		currentPlayer.setStartPosition(currentPlayer.getEndPosition());
		if(( currentPlayer.getEndPosition() + diceCup.getTotalFaceValue() ) > 39) {
			currentPlayer.setEndPosition(currentPlayer.getEndPosition() + diceCup.getTotalFaceValue() - 40); // Player passed start
		}else {
			currentPlayer.setEndPosition(currentPlayer.getEndPosition() + diceCup.getTotalFaceValue()); // Player did not pass start

		}
		guiController.updatePlayerPosition(currentPlayer.getGuiId(), currentPlayer.getEndPosition(), currentPlayer.getStartPosition()); // Update position on gui 
	}

	private boolean checkIfExtraRound(Player currentPlayer) {
		//check if player will get another turn because of pairs
		if(!diceCup.isPair()) {
			currentPlayer.setRolled(true);
		}else {
			currentPlayer.setPairs(currentPlayer.getPairs()+1);
			if (currentPlayer.getPairs() >= 3) { // Check if he rolled 3 pairs in a row
				currentPlayer.setPairs(0);
				guiController.writeMessage(PropertiesIO.getTranslation("rolled3inarow"));
				PrisonController prisonController = new PrisonController(currentPlayer, diceCup, cardController);
				prisonController.jailPlayer(currentPlayer); // jail the player
				return true;
			}
		}
		return false;
	}


	/**
	 * Updates balance if we passed start, called everytime we roll dices
	 * @param currentPlayer
	 */
	public void passedStart(Player currentPlayer) {
		boolean passed = false;
		if(!currentPlayer.isStartRound()) {
			if(((diceCup.getTotalFaceValue() + currentPlayer.getStartPosition()) > 40) || currentPlayer.getStartPosition() == 0) { // Check if he passed start
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

