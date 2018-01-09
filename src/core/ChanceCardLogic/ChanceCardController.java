package core.ChanceCardLogic;

import core.*;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by magnus
 */
public class ChanceCardController {
    private PropertiesIO propertiesIO;

    private static ChanceCard[] chanceCardArray;
    private static int[] randomArray;
    private static int index;
    private String returnmessage;

    //All types of cards
    private ChanceCard drawnChanceCard;
    private PrisonCard prisonCard;
    private DepositCard depositCard;
    private EstateTaxCard estateTaxCard;
    private MoveCard moveCard;
    private MoveShippingCard moveShippingCard;
    private WithdrawCard withdrawCard;
    private GrantCard grantCard;
    private PresentDepositCard presentDepositCard;
    private StepsBackCard stepsBackCard;

    public ChanceCardController () {
        propertiesIO = new PropertiesIO("config.properties");
        initializeChanceCards();
        initializeRandomArray();
        index = 0;
    }

    private void initializeChanceCards () {
        chanceCardArray = new ChanceCard[32];
        for (int i = 0; i < chanceCardArray.length; i++) {
            switch (i) {
                case 0:
                case 1:
                    chanceCardArray[i] = new PrisonCard(i, propertiesIO.getTranslation("chance"+i+1));
                    break;
                case 2:
                case 3:
                    chanceCardArray[i] = new MoveCard(i, propertiesIO.getTranslation("chance"+i+1), 10);
                    break;
                case 4:
                case 5:
                    chanceCardArray[i] = new MoveShippingCard(i, propertiesIO.getTranslation("chance"+i+1));
                    break;
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                    chanceCardArray[i] = new DepositCard(i, propertiesIO.getTranslation("chance"+i+1),1000);
                    break;
                case 12:
                    chanceCardArray[i] = new MoveCard(i, propertiesIO.getTranslation("chance"+i+1),0);
                    break;
                case 13:
                    chanceCardArray[i] = new DepositCard(i, propertiesIO.getTranslation("chance"+i+1),500);
                    break;
                case 14:
                    chanceCardArray[i] = new DepositCard(i, propertiesIO.getTranslation("chance"+i+1),3000);
                    break;
                case 15:
                    chanceCardArray[i] = new MoveCard(i, propertiesIO.getTranslation("chance"+i+1), 39);
                    break;
                case 16:
                    chanceCardArray[i] = new StepsBackCard(i, propertiesIO.getTranslation("chance"+i+1),3);
                    break;
                case 17:
                    chanceCardArray[i] = new GrantCard(i, propertiesIO.getTranslation("chance"+i+1));
                    break;
                case 18:
                    chanceCardArray[i] = new DepositCard(i, propertiesIO.getTranslation("chance"+i+1),200);
                    break;
                case 19:
                    chanceCardArray[i] = new WithdrawCard(i, propertiesIO.getTranslation("chance"+i+1),2000);
                    break;
                case 20:
                    chanceCardArray[i] = new EstateTaxCard(i, propertiesIO.getTranslation("chance"+i+1),500,2000);
                    break;
                case 21:
                    chanceCardArray[i] = new EstateTaxCard(i, propertiesIO.getTranslation("chance"+i+1),800,2300);
                    break;
                case 22:
                    chanceCardArray[i] = new WithdrawCard(i, propertiesIO.getTranslation("chance"+i+1),1000);
                    break;
                case 23:
                case 24:
                    chanceCardArray[i] = new WithdrawCard(i, propertiesIO.getTranslation("chance"+i+1),200);
                    break;
                case 25:
                    chanceCardArray[i] = new MoveCard(i, propertiesIO.getTranslation("chance"+i+1),11);
                    break;
                case 26:
                case 27:
                    chanceCardArray[i] = new WithdrawCard(i, propertiesIO.getTranslation("chance"+i+1),3000);
                    break;
                case 28:
                    chanceCardArray[i] = new WithdrawCard(i, propertiesIO.getTranslation("chance"+i+1),1000);
                    break;
                case 29:
                    chanceCardArray[i] = new MoveCard(i, propertiesIO.getTranslation("chance"+i+1),5);
                    break;
                case 30:
                    chanceCardArray[i] = new PresentDepositCard(i, propertiesIO.getTranslation("chance"+i+1));
                    break;
                case 31:
                    chanceCardArray[i] = new MoveCard(i, propertiesIO.getTranslation("chance"+i+1),24);
                    break;
            }

        }
    }

    private void initializeRandomArray () {
        randomArray = new int[32];
        for (int i = 0 ; i < randomArray.length ; i++)
        {
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

    public String getCard (Player currentPlayer, Field[] fields, Player[] players) {
        drawnChanceCard = chanceCardArray[randomArray[index]];
        increaseIndex();

        switch (drawnChanceCard.getClass().getSimpleName()) {
            case "PrisonCard":
                prisonCard = (PrisonCard) drawnChanceCard;

                if (prisonCard.isPrisonCard()) {
                    prisonCard(currentPlayer);
                    prisonCard.removePrisonCard();
                    returnmessage = prisonCard.getDescription();
                }
                else
                    returnmessage = getCard(currentPlayer, fields, players);
                break;
            case "MoveCard":
                moveCard = (MoveCard) drawnChanceCard;
                moveCard(currentPlayer, moveCard.getField());
                returnmessage = moveCard.getDescription();
                break;
            case "MoveShipping":
                moveShippingCard = (MoveShippingCard) drawnChanceCard;
                moveShippingCard(currentPlayer,fields);
                returnmessage = moveCard.getDescription();
                break;
            case "GrantCard":
                grantCard = (GrantCard) drawnChanceCard;
                grantCard(currentPlayer, fields);
                returnmessage = moveCard.getDescription();
                break;
            case "PresentDepositCard":
                presentDepositCard = (PresentDepositCard) drawnChanceCard;
                presentDepositCard(currentPlayer, players);
                returnmessage = presentDepositCard.getDescription();
                break;
            case "StepsBackCard":
                stepsBackCard = (StepsBackCard) drawnChanceCard;
                stepsBackCard(currentPlayer, stepsBackCard.getAmount());
                returnmessage = stepsBackCard.getDescription();
                break;
            case "WithdrawCard":
                withdrawCard = (WithdrawCard) drawnChanceCard;
                withDrawCard(currentPlayer, withdrawCard.getAmount());
                returnmessage = withdrawCard.getDescription();
                break;
            case "DepositCard":
                depositCard = (DepositCard) drawnChanceCard;
                depositCard(currentPlayer, depositCard.getAmount());
                returnmessage = depositCard.getDescription();
                break;
            case "EstateTaxCard":
                estateTaxCard = (EstateTaxCard) drawnChanceCard;
                estateTaxCard(currentPlayer, fields, estateTaxCard.getTaxHouse(), estateTaxCard.getTaxHotel());
                returnmessage = estateTaxCard.getDescription();
                break;
        }
        return returnmessage;
    }

    private void prisonCard (Player currentPlayer) {
        currentPlayer.addPrisonCard();
    }

    private void moveCard (Player currentPlayer, int field) {
        currentPlayer.setEndPosition(field);
    }

    private void moveShippingCard (Player currentPlayer, Field[] fields) {
        if (currentPlayer.getEndPosition() == 2) {
            currentPlayer.setEndPosition(5);
        }

        if (currentPlayer.getEndPosition() == 7) {
            currentPlayer.setEndPosition(15);
        }

        if (currentPlayer.getEndPosition() == 17 || currentPlayer.getEndPosition() == 21) {
            currentPlayer.setEndPosition(25);
        }

        if (currentPlayer.getEndPosition() == 33) {
            currentPlayer.setEndPosition(35);
        }

        if (currentPlayer.getEndPosition() == 36) {
            currentPlayer.setEndPosition(5);
            currentPlayer.getAccount().deposit(4000);
        }

        Property property = (Property) fields[currentPlayer.getEndPosition()];

        if (property.getOwner() == null) {
            currentPlayer.getAccount().withdraw(8000);
            property.getOwner().getAccount().deposit(8000);
        }
    }

    private void grantCard (Player currentPlayer,Field[] fields) {
        Property property = null;

        int playerValue = 0;
        for (int i = 0; i < fields.length; i++) {
            if (fields[i].getClass().getSimpleName() == "Normal") {
                if (currentPlayer == property.getOwner()) {
                    property = (Property) fields[i];
                    Street normal = (Street) fields[i];
                    playerValue += (property.getBuyValue());
                    if (normal.getHouseCounter() > 0) {
                        int buildPrice = normal.getBuildPrice();
                        for (int j = 0; j < normal.getHouseCounter(); j++) {
                            playerValue += buildPrice;
                        }
                    }
                }
            }

            else if (fields[i].getClass().getSimpleName() == "Shipping" || fields[i].getClass().getSimpleName() == "Brewery") {
                if (currentPlayer == property.getOwner()) {
                    playerValue += (property.getBuyValue());
                }
            }
        }

        if (playerValue <= 15000)
            currentPlayer.getAccount().deposit(15000);
    }

    private void presentDepositCard (Player currentPlayer, Player[] players) {
        int	present = 0;
        for (int i = 0; i < players.length; i++) {
            if (!(players[i] == currentPlayer)) {
                players[i].getAccount().withdraw(200);
                present += 200;
            }
        }
        currentPlayer.getAccount().deposit(present);
    }

    private void stepsBackCard (Player currentPlayer, int amountOfSteps) {
        int currentPlayerEndPosition = currentPlayer.getEndPosition();
        if (currentPlayerEndPosition == 0)
            currentPlayer.setEndPosition(39);
        else
            currentPlayer.setEndPosition(currentPlayerEndPosition - amountOfSteps);
    }

    private void withDrawCard (Player currentPlayer, int amount) {
        currentPlayer.getAccount().withdraw(amount);
    }

    private void depositCard (Player currentPlayer, int amount) {
        currentPlayer.getAccount().deposit(amount);
    }

    private void estateTaxCard (Player currentPlayer, Field[] fields, int housetax, int hoteltax) {
        int estateTax = 0;

        for (int i = 0; i < fields.length; i++) {
            if (fields[i].getClass().getSimpleName() == "Normal") {
                Street street= (Street) fields[i];
                if (street.getOwner() == currentPlayer) {
                    if (street.getHouseCounter() == 5) {
                        estateTax += 2300;
                    } else {
                        estateTax += (800 * (street.getHouseCounter()));
                    }
                }
            }
        }
        currentPlayer.getAccount().withdraw(estateTax);
    }

    public void discardPrisonCard () {
        prisonCard.addPrisonCard();
    }

    private ChanceCard[] getChanceCardArray () {
        return this.chanceCardArray;
    }

    private void setChanceCardArray(ChanceCard[] chanceCardArray) {
        this.chanceCardArray = chanceCardArray;
    }

    private void increaseIndex () {
        index += 1;
        if (index > 31)
            setIndex(0);
    }

    private int getIndex() {
        return index;
    }

    private void setIndex(int index) {
        this.index = index;
    }
}