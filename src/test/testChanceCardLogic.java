package test;

import core.ChanceCardLogic;
import core.Entities;
import core.Player;
import core.PropertiesIO;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

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
    private int[] chanceCardDeck;
    private PropertiesIO config;
    private int startMoney;

    @Before
    public void initializeTest () {
        testPlayer = new Player("Magnus", 1);
        testPlayer2 = new Player("Benjamin", 2);
        testPlayer3 = new Player("Nicolai", 3);
        testPlayerArray = new Player[]{testPlayer, testPlayer2, testPlayer3};
        entities = Entities.getInstance();
        entities.setPlayers(testPlayerArray);
        chanceCardLogic = new ChanceCardLogic();
        chanceCardDeck = entities.getChanceCardArr();
        config = entities.getConfig();
        startMoney = 30000;
    }

    public int findCardInDeck (int chance) {
        int cardNumber = 0;
        for (int i = 0; i < chanceCardDeck.length; i++) {
            if (chanceCardDeck[i] == chance)
                 cardNumber = i;
        }
        return cardNumber;
    }

    public void runChance (int chance) {
        chanceCardLogic.setIndex(findCardInDeck(chance));
        chanceCardLogic.getCard(testPlayer);
    }

    @Test
    public void testChance1 () {
        runChance(0);
        assertEquals(1, testPlayer.getPrisonCard());
    }

    @Test
    public void testChance2 () {
        runChance(1);
        assertEquals(10, testPlayer.getEndPosition());
    }

    @Test
    public void testChance3 () {
        runChance(2);
        assertEquals(false, true);
    }

    @Test
    public void testChance4 () {
        runChance(3);
        assertEquals(startMoney+1000, testPlayer.getAccount().getBalance());
    }

    @Test
    public void testChance5 () {
        runChance(4);
        assertEquals(startMoney-200, testPlayer.getAccount().getBalance());
    }

    @Test
    public void testChance6 () {
        runChance(5);
        assertEquals(startMoney+1000, testPlayer.getAccount().getBalance());
    }

    @Test
    public void testChance7 () {
        runChance(6);
        assertEquals(false, true);
    }

    @Test
    public void testChance8 () {
        runChance(7);
        assertEquals(0,testPlayer.getEndPosition());
    }

    @Test
    public void testChance9 () {
        runChance(8);
        assertEquals(startMoney+500, testPlayer.getAccount().getBalance());
    }

    @Test
    public void testChance10 () {
        runChance(9);
        assertEquals(startMoney+3000, testPlayer.getAccount().getBalance());
    }

    @Test
    public void testChance11 () {
        runChance(10);
        assertEquals(39, testPlayer.getEndPosition());
    }

    @Test
    public void testChance12 () {
        runChance(11);
        assertEquals(testPlayer.getEndPosition()-3, testPlayer.getEndPosition());
    }

    @Test
    public void testChance13 () {
        runChance(13);
    }

    @Test
    public void testChance14 () {
        runChance(14);
    }

    @Test
    public void testChance15 () {
        runChance(15);
    }

    @Test
    public void testChance16 () {
        runChance(16);
    }

    @Test
    public void testChance17 () {
        runChance(17);
    }

    @Test
    public void testChance18 () {
        runChance(18);
    }

    @Test
    public void testChance19 () {
        runChance(19);
    }

    @Test
    public void testChance20 () {
        runChance(20);
    }

    @Test
    public void testChance21 () {
        runChance(21);
    }

    @Test
    public void testChance22 () {
        runChance(22);
    }

    @Test
    public void testChance23 () {
        runChance(23);
    }

    @Test
    public void testChance24 () {
        runChance(24);
    }

    @Test
    public void testChance25 () {
        runChance(25);
    }

    @Test
    public void testChance26 () {
        runChance(26);
    }

    @Test
    public void testChance27 () {
        runChance(27);
    }

}
