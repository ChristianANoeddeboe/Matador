package test;

import core.ChanceCardLogic;
import core.Entities;
import core.Player;
import core.PropertiesIO;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Magnus Stjernborg Koch - s175189 og Zeth Benjamin ...
 * This class contains test functions for the object/class ChanceCardLogic
 */
public class testChanceCardLogic {
    private ChanceCardLogic chanceCardLogic;
    private Player testPlayer;
    private Player testPlayer2;
    private Player testPlayer3;
    private Player[] testPlayerArray;
    private Entities entities;
    private PropertiesIO config;

    @Before
    public void initializeTest () {
        testPlayer = new Player("Magnus", 1);
        testPlayer2 = new Player("Benjamin", 2);
        testPlayer3 = new Player("Nicolai", 3);
        testPlayerArray = new Player[]{testPlayer, testPlayer2, testPlayer3};
        entities = Entities.getInstance();
        entities.setPlayers(testPlayerArray);
        chanceCardLogic = new ChanceCardLogic();
        config = entities.getConfig();
    }

    public int[] chanceCardDeck () {
        return entities.getChanceCardArr();
    }

    public int findChanceInDeck (int chance) {
        int cardNumber = 0;
        for (int i = 0; i < chanceCardDeck().length; i++) {
            if (chanceCardDeck()[i] == chance)
                chanceCardDeck()[i] = cardNumber;
        }
        return cardNumber;
    }

    public void testChance (int chance) {
        chanceCardLogic.setIndex(findChanceInDeck(chance));
        chanceCardLogic.getCard(testPlayer);
    }

    @Test
    public void testSingletonFunctionality() {
    }

    @Test
    public void testIndex () {

    }

    //Running through all chancecards and running each chance logic
    @Test
    public void testAllChances () {
        testPlayer.setEndPosition(2);
        for (int i = 0; i <= 31; i++) {
            testPlayer.setEndPosition(2);
            chanceCardLogic.getCard(testPlayer);
            testPlayer.setEndPosition(2);
        }
    }

    @Test
    public void testChance1 () {
        testChance(1);
    }

    @Test
    public void testChance2 () {

    }

    @Test
    public void testChance3 () {

    }

    @Test
    public void testChance4 () {

    }

    @Test
    public void testChance5 () {

    }

    @Test
    public void testChance6 () {

    }

    @Test
    public void testChance7 () {

    }

    @Test
    public void testChance8 () {

    }

    @Test
    public void testChance9 () {

    }

    @Test
    public void testChance10 () {

    }

    @Test
    public void testChance11 () {

    }

    @Test
    public void testChance12 () {

    }

    @Test
    public void testChance13 () {

    }

    @Test
    public void testChance14 () {

    }

    @Test
    public void testChance15 () {

    }

    @Test
    public void testChance16 () {

    }

    @Test
    public void testChance17 () {

    }

    @Test
    public void testChance18 () {

    }

    @Test
    public void testChance19 () {

    }

    @Test
    public void testChance20 () {

    }

    @Test
    public void testChance21 () {

    }

    @Test
    public void testChance22 () {

    }

    @Test
    public void testChance23 () {

    }

    @Test
    public void testChance24 () {

    }

    @Test
    public void testChance25 () {

    }

    @Test
    public void testChance26 () {

    }

    @Test
    public void testChance27 () {

    }

    @Test
    public void testChance28 () {

    }

    @Test
    public void testChance29 () {

    }

    @Test
    public void testChance30 () {

    }

    @Test
    public void testChance31 () {

    }

    @Test
    public void testChance32 () {

    }

}
