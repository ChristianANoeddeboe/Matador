package core.ChanceCardLogic;

import core.*;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Magnus Stjernborg Koch - s175189
 *
 */
public class ChanceCardController {
	private GUIController guiController;
	//Deck to hold the different kind of cards
	private ChanceCardDeck chanceCardDeck;
	//Saving the prisonCard for discard purposes
	private PrisonCard prisonCard;

	public ChanceCardController () {
		guiController = GUIController.getInstance();
		//initialising chancecarddeck with the amount of 32 ChanceCards
		chanceCardDeck = new ChanceCardDeck(32);
		//Initialising chancards with the specifications from config/PropertiesIO
		initializeChanceCards();
	}

	private void initializeChanceCards () {
		ChanceCard[] chanceCardArray = new ChanceCard[32];
		//Randomarray for making a unsorted deck
		int[] randomArray = new int[32];
		//Initialising the random array, creating 32 different ints from 0 to 31, so each card has its own id based on field id
		initializeRandomArray(randomArray);

		//Loop goes through all chancecards and adding them to the array of chanceCards
		for (int i = 0; i < chanceCardArray.length; i++) {
			switch (i) {
			case 0:
			case 1:
				chanceCardArray[i] = new PrisonCard(i, PropertiesIO.getTranslation("chance"+(i+1)));
				break;
			case 2:
			case 3:
				chanceCardArray[i] = new MoveCard(i, PropertiesIO.getTranslation("chance"+(i+1)), 10);
				break;
			case 4:
			case 5:
				chanceCardArray[i] = new MoveShippingCard(i, PropertiesIO.getTranslation("chance"+(i+1)));
				break;
			case 6:
			case 7:
			case 8:
			case 9:
			case 10:
			case 11:
				chanceCardArray[i] = new DepositCard(i, PropertiesIO.getTranslation("chance"+(i+1)),1000);
				break;
			case 12:
				chanceCardArray[i] = new MoveCard(i, PropertiesIO.getTranslation("chance"+(i+1)),0);
				break;
			case 13:
				chanceCardArray[i] = new DepositCard(i, PropertiesIO.getTranslation("chance"+(i+1)),500);
				break;
			case 14:
				chanceCardArray[i] = new DepositCard(i, PropertiesIO.getTranslation("chance"+(i+1)),3000);
				break;
			case 15:
				chanceCardArray[i] = new MoveCard(i, PropertiesIO.getTranslation("chance"+(i+1)), 39);
				break;
			case 16:
				chanceCardArray[i] = new StepsBackCard(i, PropertiesIO.getTranslation("chance"+(i+1)),3);
				break;
			case 17:
				chanceCardArray[i] = new GrantCard(i, PropertiesIO.getTranslation("chance"+(i+1)));
				break;
			case 18:
				chanceCardArray[i] = new DepositCard(i, PropertiesIO.getTranslation("chance"+(i+1)),200);
				break;
			case 19:
				chanceCardArray[i] = new WithdrawCard(i, PropertiesIO.getTranslation("chance"+(i+1)),2000);
				break;
			case 20:
				chanceCardArray[i] = new EstateTaxCard(i, PropertiesIO.getTranslation("chance"+(i+1)),500,2000);
				break;
			case 21:
				chanceCardArray[i] = new EstateTaxCard(i, PropertiesIO.getTranslation("chance"+(i+1)),800,2300);
				break;
			case 22:
				chanceCardArray[i] = new WithdrawCard(i, PropertiesIO.getTranslation("chance"+(i+1)),1000);
				break;
			case 23:
			case 24:
				chanceCardArray[i] = new WithdrawCard(i, PropertiesIO.getTranslation("chance"+(i+1)),200);
				break;
			case 25:
				chanceCardArray[i] = new MoveCard(i, PropertiesIO.getTranslation("chance"+(i+1)),11);
				break;
			case 26:
			case 27:
				chanceCardArray[i] = new WithdrawCard(i, PropertiesIO.getTranslation("chance"+(i+1)),3000);
				break;
			case 28:
				chanceCardArray[i] = new WithdrawCard(i, PropertiesIO.getTranslation("chance"+(i+1)),1000);
				break;
			case 29:
				chanceCardArray[i] = new MoveCard(i, PropertiesIO.getTranslation("chance"+(i+1)),5);
				break;
			case 30:
				chanceCardArray[i] = new PresentDepositCard(i, PropertiesIO.getTranslation("chance"+(i+1)));
				break;
			case 31:
				chanceCardArray[i] = new MoveCard(i, PropertiesIO.getTranslation("chance"+(i+1)),24);
				break;
			}
		}
		//Saving the prisonCard so it easier can be removed later.
		prisonCard = (PrisonCard) chanceCardArray[0];
		//Using the randomarray to push each card in the deck unsorted and random
		for (int i = 0 ; i < chanceCardArray.length ; i++) {
			//Pushing card to deck (queue structure)
			chanceCardDeck.push(chanceCardArray[randomArray[i]]);
		}

	}

	/**
	 * Function for creating the random array to be used to making a random order in the ChanceCardDeckArray
	 *
	 */
	private void initializeRandomArray (int[] randomArray) {
		for (int i = 0 ; i < randomArray.length ; i++) {
			int rndNumber;
			boolean newCard;
			do {
				newCard = true;
				rndNumber = ThreadLocalRandom.current().nextInt(0, randomArray.length);
				for (int j = 0 ; j < i ; j++)
					if(randomArray[j] == rndNumber) {
						newCard = false;
						break;
					}
			} while (!newCard);
			randomArray[i] = rndNumber;
		}
	}

	/**
	 * The function called by other controllers when landed on the ChanceCard field, then it handles both the logic and
	 * the user interface related to chance cards
	 *
	 */
	public void getCard (Player currentPlayer, Field[] fields, Player[] players) {
		//picks a new card from the bottom of the deck.
		ChanceCard drawnChanceCard = chanceCardDeck.bottom();

		//getting the description for user interface
		guiController.displayChanceCard(drawnChanceCard.getDescription());
		guiController.writeMessage(PropertiesIO.getTranslation("takeachancecard"));

		//Checks with type of chanceCard has been drawned, and then calls a function related to that type of chance card
		if(drawnChanceCard instanceof PrisonCard) {
			prisonCard(currentPlayer);
		}else if(drawnChanceCard instanceof MoveCard) {
			moveCard(currentPlayer, ((MoveCard) drawnChanceCard).getField(), drawnChanceCard);
		}else if(drawnChanceCard instanceof MoveShippingCard) {
			moveShippingCard(currentPlayer);
		}else if(drawnChanceCard instanceof GrantCard) {
			grantCard(currentPlayer, fields);
		}else if(drawnChanceCard instanceof PresentDepositCard) {
			presentDepositCard(currentPlayer, players);
		}else if(drawnChanceCard instanceof StepsBackCard) {
			stepsBackCard(currentPlayer, ((StepsBackCard) drawnChanceCard).getAmount());
		}else if(drawnChanceCard instanceof WithdrawCard) {
			withdrawCard(currentPlayer, ((WithdrawCard) drawnChanceCard).getAmount());
		}else if(drawnChanceCard instanceof DepositCard) {
			depositCard(currentPlayer, ((DepositCard) drawnChanceCard).getAmount());
		}else if(drawnChanceCard instanceof EstateTaxCard) {
			estateTaxCard(currentPlayer, fields, ((EstateTaxCard) drawnChanceCard).getTaxHouse(), ((EstateTaxCard) drawnChanceCard).getTaxHotel());
		}

		//If the drawn card were a prison card dont push it back.
		if(!(drawnChanceCard instanceof PrisonCard)) {
			chanceCardDeck.push(drawnChanceCard);
		}
	}

	/**
	 * This function adds a prison card to a user which drawn the card
	 *
	 */
	private void prisonCard (Player currentPlayer) {
		currentPlayer.addPrisonCard();
	}

	/**
	 * This function is moving the player depending on which moveCard were drawn
	 *
	 */
	private void moveCard (Player currentPlayer, int field, ChanceCard drawnChanceCard) {
		currentPlayer.setStartPosition(currentPlayer.getEndPosition());
		currentPlayer.setEndPosition(field);

		//if move to prisonfield
		if (drawnChanceCard.getId() == 2 && drawnChanceCard.getId() == 3) {
			guiController.teleport(currentPlayer.getGuiId(), currentPlayer.getStartPosition(), currentPlayer.getEndPosition());
		} else {
			guiController.updatePlayerPosition(currentPlayer.getGuiId(),currentPlayer.getEndPosition(),currentPlayer.getStartPosition());
			currentPlayer.setMoved(true);
			if(currentPlayer.getEndPosition() < currentPlayer.getStartPosition() && currentPlayer.getEndPosition() != 0) {
				currentPlayer.getAccount().deposit(4000);
				guiController.updatePlayerBalance(currentPlayer.getGuiId(), currentPlayer.getAccount().getBalance());
			}

		}
	}

	/**
	 * This function moves to the closes shipping field depending on which chance field the player landed on
	 *
	 */
	private void moveShippingCard (Player currentPlayer) {
		currentPlayer.setStartPosition(currentPlayer.getEndPosition());
		if (currentPlayer.getEndPosition() == 2) {
			currentPlayer.setEndPosition(5);
		}

		if (currentPlayer.getEndPosition() == 7) {
			currentPlayer.setEndPosition(15);
		}

		if (currentPlayer.getEndPosition() == 17 || currentPlayer.getEndPosition() == 22) {
			currentPlayer.setEndPosition(25);
		}

		if (currentPlayer.getEndPosition() == 33) {
			currentPlayer.setEndPosition(35);
		}

		if (currentPlayer.getEndPosition() == 36) {
			currentPlayer.setEndPosition(5);
			currentPlayer.getAccount().deposit(4000);
		}
		guiController.updatePlayerPosition(currentPlayer.getGuiId(),currentPlayer.getEndPosition(),currentPlayer.getStartPosition());
		guiController.updatePlayerBalance(currentPlayer.getGuiId(),currentPlayer.getAccount().getBalance());
		currentPlayer.setMoved(true);

	}

	/**
	 * This function is checking if the player's networth is higher or lower then 15.000 if yes then ads 40000 to balance
	 *
	 */
	private void grantCard (Player currentPlayer,Field[] fields) {
		int playerValue = 0;
		for (int i = 0; i < fields.length; i++) {
			if (fields[i] instanceof Property) {
				Property property = (Property) fields[i];
				if (property.getOwner() == currentPlayer) {
					playerValue += property.getBuyValue();
					if (property instanceof Street) {
						Street street = (Street) property;
						if (street.getHouseCounter() > 0) {
							for (int j = 0; j < street.getHouseCounter(); j++) {
								playerValue += street.getBuildPrice();
							}
						}
					}
				}
			}
		}

		playerValue += currentPlayer.getAccount().getBalance();

		if (playerValue <= 15000) {
			currentPlayer.getAccount().deposit(40000);
			guiController.updatePlayerBalance(currentPlayer.getGuiId(),currentPlayer.getAccount().getBalance());
		}
	}

	/**
	 * This function takes 200 money from each player (not the currentplayer) and give all the total money to the currentplayer)
	 *
	 */
	private void presentDepositCard (Player currentPlayer, Player[] players) {
		int	present = 0;
		for (int i = 0; i < players.length; i++) {
			if (!(players[i] == currentPlayer)) {
				players[i].getAccount().withdraw(200);
				present += 200;
				guiController.updatePlayerBalance(players[i].getGuiId(),players[i].getAccount().getBalance());
			}
		}
		currentPlayer.getAccount().deposit(present);
		guiController.updatePlayerBalance(currentPlayer.getGuiId(),currentPlayer.getAccount().getBalance());
	}

	/**
	 * This function moves the player some steps back depending on the card
	 *
	 */
	private void stepsBackCard (Player currentPlayer, int amountOfSteps) {
		currentPlayer.setStartPosition(currentPlayer.getEndPosition());
		if((currentPlayer.getEndPosition() - amountOfSteps) < 0)
			currentPlayer.setEndPosition(39);
		currentPlayer.setEndPosition(currentPlayer.getEndPosition() - amountOfSteps);
		guiController.teleport(currentPlayer.getGuiId(),currentPlayer.getStartPosition(), currentPlayer.getEndPosition());
		currentPlayer.setMoved(true);

	}

	/**
	 * This function is withdrawing a specific amount of money from the current player depending on which card
	 *
	 */
	private void withdrawCard(Player currentPlayer, int amount) {
		int currentPlayerBalance = currentPlayer.getAccount().getBalance();
		if (checkIfAfford(currentPlayer, amount))
			currentPlayer.getAccount().withdraw(amount);
		else {
			currentPlayer.getAccount().withdraw(currentPlayerBalance);
			currentPlayer.setBanktrupt(true);
		}
		guiController.updatePlayerBalance(currentPlayer.getGuiId(),currentPlayer.getAccount().getBalance());
	}

	/**
	 * This function is depositing a specific amount of money to the current player depending on which card
	 *
	 */
	private void depositCard (Player currentPlayer, int amount) {
		currentPlayer.getAccount().deposit(amount);
		guiController.updatePlayerBalance(currentPlayer.getGuiId(),currentPlayer.getAccount().getBalance());
	}

	/**
	 * This function is checking which fields is owned by the currentplayer and how many houses and hotels are placed on them
	 * then withdrawing a tax from each house and hotel.
	 *
	 */
	private void estateTaxCard (Player currentPlayer, Field[] fields, int housetax, int hoteltax) {
		int estateTax = 0;

		for (int i = 0; i < fields.length; i++) {
			if (fields[i] instanceof Street) {
				Street street = (Street) fields[i];
				if (street.getOwner() == currentPlayer) {
					if (street.getHouseCounter() == 5) {
						estateTax += hoteltax;
					} else {
						estateTax += (housetax * (street.getHouseCounter()));
					}
				}
			}
		}
		currentPlayer.getAccount().withdraw(estateTax);
		guiController.updatePlayerBalance(currentPlayer.getGuiId(),currentPlayer.getAccount().getBalance());
	}

	/**
	 * This function is called when the prisoncard is used and needs to be return to the deck, so it can be drawn by others
	 *
	 */
	public void putPrisonCardInDeck () {
		chanceCardDeck.push(prisonCard);
	}

	/**
	 * This function is using the SalesController to check if the player can afford the different types of cards.
	 *
	 */
	private boolean checkIfAfford (Player currentPlayer, int value) {
		SalesController salesController = new SalesController(currentPlayer);
		return salesController.cannotAfford(value);
	}
}
