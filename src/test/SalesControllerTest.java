package test;

import static org.junit.Assert.*;

import org.junit.Test;

import core.Field;
import core.FieldController;
import core.Player;
import core.Property;
import core.SalesController;
import core.Street;

public class SalesControllerTest {
	private Player testplayer;
	private int value;
	private FieldController fieldcontroller;
	private Field fields[];
	
	private void initTest() {
		testplayer = new Player("Test", 1);
		fieldcontroller = new FieldController();
		fields = fieldcontroller.getFieldArr();
		value = 4000;
		for(int i = 0; i<fields.length;) {
			if(fields[i] instanceof Property) {
				Property property = (Property) fields[i];
				property.setOwner(testplayer);
				i = i + 2;
			}
		}
		for(int i = 0; i<fields.length; i++) {
			if(fields[i] instanceof Street) {
				Street street = (Street) fields[i];
				if(street.getOwner() == testplayer) {
					street.setHouseCounter(1);
					street.setRentValue(street.getHousePrices()[street.getHouseCounter()]);
				}
			}
		}
	}

	@Test
	public void testSalesController() {
		initTest();
		SalesController salescontroller = new SalesController(testplayer);
		assertEquals("Test", salescontroller.getCurrentPlayer().getName());
	}

	@Test
	public void testCannotAfford() {
		initTest();
		SalesController salescontroller = new SalesController(testplayer);
		
		testplayer.getAccount().setBalance(3000);
		boolean testbool = salescontroller.cannotAfford(value);
		
		
		
		assertEquals(true, testbool);
		fail("Not yet implemented");
	}

	@Test
	public void testSellHouse() {
		fail("Not yet implemented");
	}

	@Test
	public void testPawnProperty() {
		fail("Not yet implemented");
	}

}
