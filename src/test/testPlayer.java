package test;

import core.Account;
import core.Player;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * 
 * @author Nicolai Kammersgård <s143780@student.dtu.dk> && Mathias Thejsen (s175192)
 *
 */
public class testPlayer extends Player {
	Player player = new Player("Grethe",1);
	/**
	 * Tests the constructor to insure the values are as expected
	 */
	@Test
	public void testConstructor() {
		Account account = new Account();
		assertEquals(player.getName(),"Grethe");
		assertEquals(player.getAccount().getBalance(),account.getBalance());
		assertEquals(player.isPrison(), false);
		assertEquals(player.getStartPosition(), 0);
	}
	/**
	 * Tests the getter and setter for the value name
	 */
	@Test
	public void testName() {
		String name = "Bo";
		assertFalse(player.getName() == name);
		
		player.setName(name);
		assertEquals(player.getName(), name);
	}
	/**
	 * Test if User is in prison
	 */
	@Test
	public void testPrison() {
		assertTrue(player.isPrison() == false);
	}
	/**
	 * Tests the getter and setter for the value Account
	 */
	@Test
	public void testAccount() {
		Account account = new Account();
		account.deposit(100);
		assertFalse(player.getAccount() == account);
		
		player.setAccount(account);
		assertEquals(player.getAccount(),account);
	}
	
	/**
	 * Tests the can afford method
	 */
	@Test
	public void testCanAffrord() {
		assertEquals(player.getAccount().canAfford(30000),false);
	}
	
	
}
