/**
 * 
 */
package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import core.Field;
import core.FieldController;
import core.Player;
import core.PlayerController;
import core.Property;
import core.Street;

/**
 * @author Mathias Thejsen - s175192
 *
 */
public class testFieldController {

	private FieldController fieldController;
	private PlayerController playerController;
	private Player player;
	private Field[] fieldArr;
    @Before
    public void setUp() throws Exception {
    	fieldController = new FieldController();
		player = new Player("test", 1);
		playerController = new PlayerController();
		playerController.initPlayers(4);
		fieldArr = fieldController.getFieldArr();
    }

    
    
    /**
	 * Test method for {@link core.FieldController#initFields()}.
	 */
	@Test
	public void testinitFields() {
		assertTrue(fieldArr[0].getName().equals("start")); // Checking if fields are initialized properly
	}
	

	/**
	 * Test method for {@link core.FieldController#allFieldsToBuildOn(core.Player)}.
	 * The method should return a list of streets that houses can be built on, we set ourselves to owner of all fields and see if the list is generated right
	 */
	@Test
	public void testAllFieldsToBuildOn() {
		String[] streetnames = new String[22];
		int counter = 0;
		for (int i = 0; i < fieldArr.length; i++) { // Loop over all fields
			if(fieldArr[i] instanceof Street) {
				Street street = (Street) fieldArr[i]; // Cast
				street.setOwner(player); // Set player to owner of ALL street fields
				streetnames[counter] = street.getName(); // fill out names array
				counter++;
			}
		}
		
		Street[] streetarray = fieldController.allFieldsToBuildOn(player); // Generate the array
		assertTrue(streetarray.length ==  22 && streetnames.length == 22); // Check if the lenght is 22 which is equal to the amount of streets
		for (int i = 0; i < streetarray.length; i++) {
			assertTrue(streetnames[i].equals(streetarray[i].getName())); // Check if list is generated properly by checking if names are right 
		}
	}

	/**
	 * Test method for {@link core.FieldController#FieldsOwned(core.Player)}.
	 * the method should return a list of fields we own, we set ourselves to own the first 10 streets
	 */
	@Test
	public void testFieldsOwned() {
		int counter = 1;
		for (int i = 0; i < fieldArr.length; i++) { // Loop over all fields
			if(fieldArr[i] instanceof Property && counter < 10) { // We test if the field lenght corresponds to 10
				Property property = (Property) fieldArr[i]; // Cast
				property.setOwner(player); // Set player to owner of the field
				counter++;
			}
		}
		assertTrue(fieldController.FieldsOwned(player).length < 10);
	}
}
