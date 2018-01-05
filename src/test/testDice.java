/**
 * Created in CDIO 1 reused.
 * This file tests the functionality of the dice,
 * By checking if the results are the same as they would be according to probability theory.
 */
package test;

import core.Dice;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * @author Simon Fritz (s175191) and Mathias Thejsen (s175192)
 */
public class testDice extends Dice {
	
	/**
	 * Test method for {@link Dice#rollMultiple(int)}.
	 * Test if the our dice works accordingly to probality theory
	 */
	@Test
	public void testRollMultipleforones() {
		int value = collector(1,60000);
		assertTrue(9600 <=value&&value<=10400);
	}


	/**
	 * Test method for {@link Dice#rollMultiple(int)}.
	 * Test if the our dice works accordingly to probality theory
	 */
	@Test
	public void testRollMultiplefortwos() {
		int value = collector(2,60000);
		assertTrue(9600 <=value&&value<=10400);
	}


	/**
	 * Test method for {@link Dice#rollMultiple(int)}.
	 * Test if the our dice works accordingly to probality theory
	 */
	@Test
	public void testRollMultipleforthrees() {
		int value = collector(3,60000);
		assertTrue(9600 <=value&&value<=10400);
	}

	/**
	 * Test method for {@link Dice#rollMultiple(int)}.
	 * Test if the our dice works accordingly to probality theory
	 */
	@Test
	public void testRollMultipleforfours() {
		int value = collector(1,60000);
		assertTrue(9600 <=value&&value<=10400);
	}


	/**
	 * Test method for {@link Dice#rollMultiple(int)}.
	 * Test if the our dice works accordingly to probality theory
	 */
	@Test
	public void testRollMultipleforfives() {
		int value = collector(1,60000);
		assertTrue(9600 <=value&&value<=10400);
	}


	/**
	 * Test method for {@link Dice#rollMultiple(int)}.
	 * Test if the our dice works accordingly to probality theory
	 */
	@Test
	public void testRollMultipleforsixs() {
		int value = collector(1,60000);
		assertTrue(9600 <=value&&value<=10400);
	}
	
	/**
	 * We test if the dices roll pairs of ones accordingly to probaility theory (2/12)
	 */
	@Test
	public void testRollPairsOnes() {
			int value1 = collector(1,60000);
			int value2 = collector(1,60000);
			assertTrue((9600 <=value1&&value1<=10400)&&(9600 <=value2&&value2<=10400));
	}
	
	/**
	 * We test if the dices roll pairs of twos accordingly to probaility theory (2/12)
	 */
	@Test
	public void testRollPairsTwos() {
			int value1 = collector(2,60000);
			int value2 = collector(2,60000);
			assertTrue((9600 <=value1&&value1<=10400)&&(9600 <=value2&&value2<=10400));
	}
	
	/**
	 * We test if the dices roll pairs of threes accordingly to probaility theory (2/12)
	 */
	@Test
	public void testRollPairsThrees() {
			int value1 = collector(3,60000);
			int value2 = collector(3,60000);
			assertTrue((9600 <=value1&&value1<=10400)&&(9600 <=value2&&value2<=10400));
	}
	
	/**
	 * We test if the dices roll pairs of fours accordingly to probaility theory (2/12)
	 */
	@Test
	public void testRollPairsFours() {
			int value1 = collector(4,60000);
			int value2 = collector(4,60000);
			assertTrue((9600 <=value1&&value1<=10400)&&(9600 <=value2&&value2<=10400));
	}
	
	/**
	 * We test if the dices roll pairs of fives accordingly to probaility theory (2/12)
	 */
	@Test
	public void testRollPairsFives() {
			int value1 = collector(5,60000);
			int value2 = collector(5,60000);
			assertTrue((9600 <=value1&&value1<=10400)&&(9600 <=value2&&value2<=10400));
	}
	
	/**
	 * We test if the dices roll pairs of ones accordingly to probaility theory (2/12)
	 */
	@Test
	public void testRollPairsSixs() {
			int value1 = collector(6,60000);
			int value2 = collector(6,60000);
			assertTrue((9600 <=value1&&value1<=10400)&&(9600 <=value2&&value2<=10400));
	}
	
	/**
	 * 
	 * @param n the number we count
	 * @param times how many times we roll
	 * @return returns how many times 'n' was rolled
	 */
	protected int collector(int n, int times) {
		int collection = 0;
		for (int i=0; i<times; i++) {
			if(roll() == n) {
				collection++;
			}
		}
		return collection;
	}
}