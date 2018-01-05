package test;

import core.Account;
import core.Player;
import gui_fields.GUI_Player;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Magnus Stjernborg Koch - s175189
 * This class contains test functions for the object/class Account
 */
public class testAccount {
    //One player is created for testing purposes
    private Player player = new Player("player1");
    //The account related to the player here gets an reference
    private Account player1_Account = player.getAccount();

    //Testing the construtor, we test if the default balance is equal to 1000.
    @Test
    public void testConstructor () {
        assertEquals(12000, player1_Account.getBalance());
    }

    //Testing the despoit function, where it is excepted to add money to the balance.
    @Test
    public void testDespoit () {
        player1_Account.deposit(18000);
        assertEquals(30000, player1_Account.getBalance());
    }

    //Tesing withdraw, which needs to remove 500 points from the balance.
    @Test
    public void testWithdraw () {
        player1_Account.withdraw(6000);
        assertEquals(6000, player1_Account.getBalance());
    }

    //Testing despoit with negative numbers, nothing should happen with the account balance
    @Test
    public void testNegativeDespoit () {
        player1_Account.deposit(-5000);
        assertEquals(12000, player1_Account.getBalance());
    }

    //Testing withdraw with negative numbers, it is expected to perform the withdraw and remove the amount from the balance.
    @Test
    public void testNegativeWithdraw () {
        player1_Account.withdraw(-24000);
        assertEquals(0, player1_Account.getBalance());
    }

    //Tesitng that the balance value can not get lower than 0
    @Test
    public void testWithdrawBiggerValueThanbalance () {
        player1_Account.withdraw(13000);
        assertEquals(0, player1_Account.getBalance());
    }

}
