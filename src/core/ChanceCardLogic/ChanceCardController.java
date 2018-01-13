package core.ChanceCardLogic;

import core.*;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by magnus
 */
public class ChanceCardController {
	private GUIController guiController;

	//Deck to hold cards in randomized order
	private ChanceCardDeck chanceCardDeck;
	private PrisonCard prisonCard;

	public ChanceCardController () {
		guiController = GUIController.getInstance();
		chanceCardDeck = new ChanceCardDeck(32);
		initializeChanceCards();
	}

	private void initializeChanceCards () {
		ChanceCard[] chanceCardArray = new ChanceCard[32];
		int[] randomArray = new int[32];
		initializeRandomArray(randomArray);

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
		prisonCard = (PrisonCard) chanceCardArray[0];
		for (int i = 0 ; i < chanceCardArray.length ; i++) {
			chanceCardDeck.push(chanceCardArray[randomArray[i]]);
		}

	}

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

	public void getCard (Player currentPlayer, Field[] fields, Player[] players) {
		ChanceCard drawnChanceCard = chanceCardDeck.bottom();

		guiController.displayChanceCard(drawnChanceCard.getDescription());
		guiController.writeMessage(PropertiesIO.getTranslation("takeachancecard"));
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
		if(!(drawnChanceCard instanceof PrisonCard)) {
			chanceCardDeck.push(drawnChanceCard);
		}
	}

	private void prisonCard (Player currentPlayer) {
		currentPlayer.addPrisonCard();
	}

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

	private void stepsBackCard (Player currentPlayer, int amountOfSteps) {
		currentPlayer.setStartPosition(currentPlayer.getEndPosition());
		if((currentPlayer.getEndPosition() - amountOfSteps) < 0)
			currentPlayer.setEndPosition(39);
		currentPlayer.setEndPosition(currentPlayer.getEndPosition() - amountOfSteps);
		guiController.teleport(currentPlayer.getGuiId(),currentPlayer.getStartPosition(), currentPlayer.getEndPosition());
		currentPlayer.setMoved(true);

	}

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

	private void depositCard (Player currentPlayer, int amount) {
		currentPlayer.getAccount().deposit(amount);
		guiController.updatePlayerBalance(currentPlayer.getGuiId(),currentPlayer.getAccount().getBalance());
	}

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

	public void putPrisonCardInDeck () {
		chanceCardDeck.push(prisonCard);
	}

	private boolean checkIfAfford (Player currentPlayer, int value) {
		SalesController salesController = new SalesController(currentPlayer);
		return salesController.cannotAfford(value);
	}
}
