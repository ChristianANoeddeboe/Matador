package test;

import core.ChanceCardLogic;
import core.Player;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Magnus Stjernborg Koch - s175189 og Zeth Benjamin ...
 * This class contains test functions for the object/class ChanceCardLogic
 */
public class testChanceCardLogic{
    private ChanceCardLogic chanceCardLogic = new ChanceCardLogic();
    Player player = new Player("Magnus", 1);
    @Test
    public void testSingletonFunctionality() {
    }

    @Test
    public void testIndex () {
        assertEquals(1, chanceCardLogic.getIndex());
        chanceCardLogic.getCard(player);
        assertEquals(2, chanceCardLogic.getIndex());
    }

    @Test
    public void testChance1 () {
        Player player = new Player("Magnus", 1);

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
