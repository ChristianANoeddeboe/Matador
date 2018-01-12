/**
 * 
 */
package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import core.Field;
import core.FieldController;
import core.GUIController;
import core.Player;
import core.PlayerController;
import core.Street;
import core.StreetController;

/**
 * @author Mathias Thejsen
 *
 */
public class testStreetController {

	/**
	 * Testing wherever it is possible to set an owner of a field
	 */
	@Test
	public void testLogic() {
		FieldController fieldController = new FieldController();
		Player player = new Player("test", 1);
		PlayerController playerController = new PlayerController();
		playerController.initPlayers(4);
		Player[] playerarr = playerController.getPlayers();
		playerarr[0] = player;
		Field[] field = fieldController.getFieldArr();
		StreetController streetController = new StreetController(player, field[1], playerarr);
		Street street = (Street) field[1];
		street.setOwner(player); // Emulating the logic() method in streetcontroller
		assertTrue(street.getOwner().equals(player));
	}

	/**
	 * Testing if a player can buy a field without having enough money
	 */
	@Test
	public void testLogic2() {
		FieldController fieldController = new FieldController();
		Player player = new Player("test", 1);
		player.getAccount().setBalance(100);
		PlayerController playerController = new PlayerController();
		playerController.initPlayers(4);
		Player[] playerarr = playerController.getPlayers();
		playerarr[0] = player;
		Field[] field = fieldController.getFieldArr();
		StreetController streetController = new StreetController(player, field[1], playerarr);
		Street street = (Street) field[1];
		
		boolean bought = false; // Boolean representing 
		if(player.getAccount().canAfford(street.getBuyValue())) {
			street.setOwner(player);
			bought = true;
		}else {
			bought = false;
		}
		assertTrue(!bought);
	}
}
