package core.ChanceCardLogic;

import core.*;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by magnus
 */
public class ChanceCardController {
    private PropertiesIO propertiesIO;
    private GUIController guiController;

    //Deck to hold cards in randomized order
    private ChanceCardDeck chanceCardDeck;
    private PrisonCard prisonCard;

    public ChanceCardController () {
        propertiesIO = new PropertiesIO("config.properties");
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
                    chanceCardArray[i] = new PrisonCard(i, propertiesIO.getTranslation("chance"+(i+1)));
                    break;
                case 2:
                case 3:
                    chanceCardArray[i] = new MoveCard(i, propertiesIO.getTranslation("chance"+(i+1)), 10);
                    break;
                case 4:
                case 5:
                    chanceCardArray[i] = new MoveShippingCard(i, propertiesIO.getTranslation("chance"+(i+1)));
                    break;
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                    chanceCardArray[i] = new DepositCard(i, propertiesIO.getTranslation("chance"+(i+1)),1000);
                    break;
                case 12:
                    chanceCardArray[i] = new MoveCard(i, propertiesIO.getTranslation("chance"+(i+1)),0);
                    break;
                case 13:
                    chanceCardArray[i] = new DepositCard(i, propertiesIO.getTranslation("chance"+(i+1)),500);
                    break;
                case 14:
                    chanceCardArray[i] = new DepositCard(i, propertiesIO.getTranslation("chance"+(i+1)),3000);
                    break;
                case 15:
                    chanceCardArray[i] = new MoveCard(i, propertiesIO.getTranslation("chance"+(i+1)), 39);
                    break;
                case 16:
                    chanceCardArray[i] = new StepsBackCard(i, propertiesIO.getTranslation("chance"+(i+1)),3);
                    break;
                case 17:
                    chanceCardArray[i] = new GrantCard(i, propertiesIO.getTranslation("chance"+(i+1)));
                    break;
                case 18:
                    chanceCardArray[i] = new DepositCard(i, propertiesIO.getTranslation("chance"+(i+1)),200);
                    break;
                case 19:
                    chanceCardArray[i] = new WithdrawCard(i, propertiesIO.getTranslation("chance"+(i+1)),2000);
                    break;
                case 20:
                    chanceCardArray[i] = new EstateTaxCard(i, propertiesIO.getTranslation("chance"+(i+1)),500,2000);
                    break;
                case 21:
                    chanceCardArray[i] = new EstateTaxCard(i, propertiesIO.getTranslation("chance"+(i+1)),800,2300);
                    break;
                case 22:
                    chanceCardArray[i] = new WithdrawCard(i, propertiesIO.getTranslation("chance"+(i+1)),1000);
                    break;
                case 23:
                case 24:
                    chanceCardArray[i] = new WithdrawCard(i, propertiesIO.getTranslation("chance"+(i+1)),200);
                    break;
                case 25:
                    chanceCardArray[i] = new MoveCard(i, propertiesIO.getTranslation("chance"+(i+1)),11);
                    break;
                case 26:
                case 27:
                    chanceCardArray[i] = new WithdrawCard(i, propertiesIO.getTranslation("chance"+(i+1)),3000);
                    break;
                case 28:
                    chanceCardArray[i] = new WithdrawCard(i, propertiesIO.getTranslation("chance"+(i+1)),1000);
                    break;
                case 29:
                    chanceCardArray[i] = new MoveCard(i, propertiesIO.getTranslation("chance"+(i+1)),5);
                    break;
                case 30:
                    chanceCardArray[i] = new PresentDepositCard(i, propertiesIO.getTranslation("chance"+(i+1)));
                    break;
                case 31:
                    chanceCardArray[i] = new MoveCard(i, propertiesIO.getTranslation("chance"+(i+1)),24);
                    break;
            }
        }
        //SAVE VERSION OF PRISONCARD
        prisonCard = (PrisonCard) chanceCardArray[0];
        for (int i = 0 ; i < chanceCardArray.length ; i++) {
        	chanceCardDeck.push(chanceCardArray[i]);
        }
        
    }

    private void initializeRandomArray (int[] randomArray) {
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

    public void getCard (Player currentPlayer, Field[] fields, Player[] players) {
    	ChanceCard drawnChanceCard = chanceCardDeck.bottom();

    	guiController.writeMessage(drawnChanceCard.getDescription());

        switch (drawnChanceCard.getClass().getSimpleName()) {
            case "PrisonCard":
                prisonCard(currentPlayer);
                System.out.println("Spiller: "+currentPlayer.getName()+", chancecard:"+drawnChanceCard.getClass().getSimpleName()+" og id:"+drawnChanceCard.getId());
                break;
            case "MoveCard":
                moveCard(currentPlayer, ((MoveCard) drawnChanceCard).getField(), fields, drawnChanceCard);
                System.out.println("Spiller: "+currentPlayer.getName()+", chancecard:"+drawnChanceCard.getClass().getSimpleName()+" og id:"+drawnChanceCard.getId());
                break;
            case "MoveShipping":
                moveShippingCard(currentPlayer,fields);
                System.out.println("Spiller: "+currentPlayer.getName()+", chancecard:"+drawnChanceCard.getClass().getSimpleName()+" og id:"+drawnChanceCard.getId());
                break;
            case "GrantCard":
                grantCard(currentPlayer, fields);
                System.out.println("Spiller: "+currentPlayer.getName()+", chancecard:"+drawnChanceCard.getClass().getSimpleName()+" og id:"+drawnChanceCard.getId());
                break;
            case "PresentDepositCard":
                presentDepositCard(currentPlayer, players);
                System.out.println("Spiller: "+currentPlayer.getName()+", chancecard:"+drawnChanceCard.getClass().getSimpleName()+" og id:"+drawnChanceCard.getId());
                break;
            case "StepsBackCard":
                stepsBackCard(currentPlayer, ((StepsBackCard) drawnChanceCard).getAmount(), fields);
                System.out.println("Spiller: "+currentPlayer.getName()+", chancecard:"+drawnChanceCard.getClass().getSimpleName()+" og id:"+drawnChanceCard.getId());
                break;
            case "WithdrawCard":
                withdrawCard(currentPlayer, ((WithdrawCard) drawnChanceCard).getAmount());
                System.out.println("Spiller: "+currentPlayer.getName()+", chancecard:"+drawnChanceCard.getClass().getSimpleName()+" og id:"+drawnChanceCard.getId());
                break;
            case "DepositCard":
                depositCard(currentPlayer, ((DepositCard) drawnChanceCard).getAmount());
                System.out.println("Spiller: "+currentPlayer.getName()+", chancecard:"+drawnChanceCard.getClass().getSimpleName()+" og id:"+drawnChanceCard.getId());
                break;
            case "EstateTaxCard":
                estateTaxCard(currentPlayer, fields, ((EstateTaxCard) drawnChanceCard).getTaxHouse(), ((EstateTaxCard) drawnChanceCard).getTaxHotel());
                System.out.println("Spiller: "+currentPlayer.getName()+", chancecard:"+drawnChanceCard.getClass().getSimpleName()+" og id:"+drawnChanceCard.getId());
                break;
        }
        
        //put card in deck again unless prisoncard.
        if(!(drawnChanceCard instanceof PrisonCard))
        	chanceCardDeck.push(drawnChanceCard);
    }

    private void prisonCard (Player currentPlayer) {
        currentPlayer.addPrisonCard();
    }

    private void moveCard (Player currentPlayer, int field, Field[] fields, ChanceCard drawnChanceCard) {
        currentPlayer.setStartPosition(currentPlayer.getEndPosition());
        currentPlayer.setEndPosition(field);

        //if move to prisonfield
        if (drawnChanceCard.getId() == 2 && drawnChanceCard.getId() == 3) {
            guiController.teleport(currentPlayer.getGuiId(), field, currentPlayer.getEndPosition());
        } else {
            if (fields[field] instanceof Property)
                landOnProperty(fields, currentPlayer);
            guiController.updatePlayerPosition(currentPlayer.getGuiId(),currentPlayer.getEndPosition(),currentPlayer.getStartPosition());
        }
    }

    private void moveShippingCard (Player currentPlayer, Field[] fields) {
        if (currentPlayer.getEndPosition() == 2) {
            currentPlayer.setStartPosition(currentPlayer.getEndPosition());
            currentPlayer.setEndPosition(5);
            guiController.updatePlayerPosition(currentPlayer.getGuiId(),currentPlayer.getEndPosition(),currentPlayer.getStartPosition());
        }

        if (currentPlayer.getEndPosition() == 7) {
            currentPlayer.setEndPosition(15);
            guiController.updatePlayerPosition(currentPlayer.getGuiId(),currentPlayer.getEndPosition(),currentPlayer.getStartPosition());
        }

        if (currentPlayer.getEndPosition() == 17 || currentPlayer.getEndPosition() == 21) {
            currentPlayer.setEndPosition(25);
            guiController.updatePlayerPosition(currentPlayer.getGuiId(),currentPlayer.getEndPosition(),currentPlayer.getStartPosition());
        }

        if (currentPlayer.getEndPosition() == 33) {
            currentPlayer.setEndPosition(35);
            guiController.updatePlayerPosition(currentPlayer.getGuiId(),currentPlayer.getEndPosition(),currentPlayer.getStartPosition());
        }

        if (currentPlayer.getEndPosition() == 36) {
            currentPlayer.setEndPosition(5);
            guiController.updatePlayerPosition(currentPlayer.getGuiId(),currentPlayer.getEndPosition(),currentPlayer.getStartPosition());
            currentPlayer.getAccount().deposit(4000);
            guiController.updatePlayerBalance(currentPlayer.getGuiId(),currentPlayer.getAccount().getBalance());
        }

        landOnProperty(fields, currentPlayer);

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

    private void stepsBackCard (Player currentPlayer, int amountOfSteps, Field[] fields) {
        currentPlayer.setStartPosition(currentPlayer.getEndPosition());
        currentPlayer.setEndPosition(currentPlayer.getEndPosition() - amountOfSteps);
        guiController.teleport(currentPlayer.getGuiId(),currentPlayer.getEndPosition(),currentPlayer.getStartPosition());
        if (fields[currentPlayer.getEndPosition()] instanceof Property)
            landOnProperty(fields, currentPlayer);
    }

    private void withdrawCard(Player currentPlayer, int amount) {
        currentPlayer.getAccount().withdraw(amount);
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

    private void landOnProperty (Field[] fields, Player currentPlayer) {
        Property property = (Property) fields[currentPlayer.getEndPosition()];

        if (!(property.getOwner() == currentPlayer)) {
            if (!(property.getOwner() == null)) {
                guiController.updatePlayerBalance(currentPlayer.getGuiId(), currentPlayer.getAccount().getBalance());
                guiController.updatePlayerBalance(property.getOwner().getGuiId(), property.getOwner().getAccount().getBalance());
            } else {
                if (currentPlayer.getAccount().canAfford(property.getRentValue())) { // If it is not owned and we can afford it
                    String[] choices = {"Yes", "No"};
                    String result = guiController.requestPlayerChoiceButtons("Vil du købe..." + property.getName(), choices);
                    if (result.equals("Yes")) {
                        currentPlayer.getAccount().withdraw(property.getBuyValue());
                        property.setOwner(currentPlayer);
                        guiController.setOwner(currentPlayer.getGuiId(), property.getId());
                        guiController.updatePlayerBalance(currentPlayer.getGuiId(), currentPlayer.getAccount().getBalance());
                    }
                }
            }
        }
    }

    private void checkIfPlayerCanAfford (Player currentPlayer, int value) {
        SalesController salesController = new SalesController(currentPlayer);
        salesController.cannotAfford(value);
    }
}
