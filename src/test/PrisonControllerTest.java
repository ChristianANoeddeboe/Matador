package test;

import core.Player;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PrisonControllerTest {

    private Player currentPlayer;

    @Before
    public void setUp() throws Exception {
        currentPlayer = new Player("test1", 0);
    }

    @Test
    public void jailPlayerPositive() {
        currentPlayer.setEndPosition(10);
        currentPlayer.setPrison(true);
        assertTrue("Player was not prisoned or was placed at wrong position.", currentPlayer.isPrison() && currentPlayer.getEndPosition() == 10);
    }

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