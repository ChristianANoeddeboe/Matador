package test;

import core.Player;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
/**
 * Test of the prison controller
 * @author Mathias Thejsen - Thejsen@live.dk
 *
 */
public class testPrisonController {

    private Player currentPlayer;

    @Before
    public void setUp() throws Exception {
        currentPlayer = new Player("test1", 0);
    }
    /**
     * Testing if the jailing part works
     */
    @Test
    public void jailPlayerPositive() {
        currentPlayer.setEndPosition(10);
        currentPlayer.setPrison(true);
        assertTrue("Player was not prisoned or was placed at wrong position.", currentPlayer.isPrison() && currentPlayer.getEndPosition() == 10);
    }

    /**
     * Testing the ability to pay the fine
     */
    @Test
    public void payFine() {
        int playerAmount = currentPlayer.getAccount().getBalance();
        currentPlayer.setPrison(true);
        currentPlayer.getAccount().withdraw(1000);
        if (currentPlayer.isPrison()) {
            currentPlayer.setPrison(false);
        }
        assertTrue("Player did not pay fine correctly.", !currentPlayer.isPrison() && currentPlayer.getAccount().getBalance() == playerAmount - 1000);
    }
    /**
     * Testing the usability of the prison card
     */
    @Test
    public void usePrisonCard() {
        currentPlayer.setPrisonCard(1);
        currentPlayer.setPrisonCard(currentPlayer.getPrisonCard() - 1);
        if (currentPlayer.isPrison()) {
            currentPlayer.setPrison(false);
        }
        assertTrue("Player still had a prison card or was not released from prison.", currentPlayer.getPrisonCard() == 0 && !currentPlayer.isPrison());
    }
}