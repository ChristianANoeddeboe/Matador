package core;
import java.util.concurrent.ThreadLocalRandom;
/**
 * Class created in CDIO 1
 * @author Simon Fritz (s175191) and Mathias Thejsen (s175192)
 */

public class Dice {
	private int value;
	
	/**
	 * Constructor for dice
	 */
	public Dice() {
	}
	
	/**
	 * Handles the roll
	 * @return, returns a random integer value between 1 and 6.
	 */
	public int roll() {
		value = ThreadLocalRandom.current().nextInt(1, 6 + 1);
		return value;
	}
	
	/**
	 * @return returns the value of the dice
	 */
	public int getValue() {
		return value;
	}
	
	/**
	 * @param The dice value to set
	 */
	public void setValue(int value) {
		this.value = value;
	}
	
	
}
