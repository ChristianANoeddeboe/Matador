package core.ChanceCardLogic;

import core.Player;
import core.Prison;
import core.PropertiesIO;

/**
 * Created by magnus
 */
public class ChanceCardController {
    private ChanceCard[] chanceCardArray;
    private int index;

    //All types of cards
    private PrisonCard prisonCard;
    private DepositCard depositCard;
    private EstateTaxCard estateTaxCard;
    private MoveCard moveCard;
    private MoveShippingCard moveShippingCard;
    private WithdrawCard withdrawCard;
    private GrantCard grantCard;

    private PropertiesIO propertiesIO;

    public ChanceCardController () {
        InitializeChanceCards();
        index = 0;
    }

    public void InitializeChanceCards () {
        chanceCardArray = new ChanceCard[31];
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
                    chanceCardArray[i] = new DepositCard(i, propertiesIO.getTranslation("chance"+i+1),1000);


            }



        }
    }

    public void RandomiseCards () {
        /*for (int i = 0; i < chanceCardArray.length; i++)
        {
            int rndNumber;
            boolean newCard;

            do {
                newCard = true;
                rndNumber = ThreadLocalRandom.current().nextInt(0, chanceCardArray.length);

                for (int j = 0 ; j < i ; j++)
                    if(chanceCardArray[j] == rndNumber) {
                        newCard = false;
                        break;
                    }
            } while (!newCard);

            chanceCardArray[i] = rndNumber;
        }*/
    }

    public String getCard (Player currentPlayer) {

        return "cyka blyat";
    }

    public void prisonCard () {
    }

    public void moveCard () {

    }

    public void moveShipping () {

    }

    public void discardPrisonCard () {
        prisonCard.addPrisonCard();
    }

    public ChanceCard[] getChanceCardArray () {
        return this.chanceCardArray;
    }

    public void setChanceCardArray(ChanceCard[] chanceCardArray) {
        this.chanceCardArray = chanceCardArray;
    }

}
