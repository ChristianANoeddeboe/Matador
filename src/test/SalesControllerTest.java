package test;

import static org.junit.Assert.*;

import org.junit.Test;

import core.Field;
import core.FieldController;
import core.Player;
import core.PropertiesIO;
import core.Property;
import core.SalesController;
import core.Street;

public class SalesControllerTest {
	private Player testplayer;
	private int value;
	private FieldController fieldcontroller;
	private Field fields[];
	private Property property;
	private Street street;
	
	private void initTest() {
		testplayer = new Player("Test", 1);
		fieldcontroller = new FieldController();
		fields = fieldcontroller.getFieldArr();
		property = (Property) fields[1];
		street = (Street) fields[3];
		street.setHouseCounter(1);
	}

	@Test
	public void testSalesController() {
		initTest();
		SalesController salescontroller = new SalesController(testplayer);
		assertEquals(testplayer, salescontroller.getCurrentPlayer());
	}

	@Test
	public void testSellHouse() {
		initTest();
		street.setHouseCounter(street.getHouseCounter() - 1);
		assertTrue(street.getHouseCounter() == 0);
	}

	@Test
	public void testPawnProperty() {
		initTest();
		property.setPawned(true);
		assertTrue(property.isPawned() == true);
	}
}
