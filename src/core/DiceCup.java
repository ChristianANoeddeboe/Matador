package core;
/**
 * 
 * @author Whole group
 *
 */
public class DiceCup {
	private Dice diceArr[];
	/**
	 * The dicecup contains a dice arr, which is filled with refrences to dices
	 * @param amoutOfDice
	 */
	public DiceCup(int amoutOfDice) {
		diceArr = new Dice[amoutOfDice];
		for (int i = 0; i < diceArr.length; i++) {
			diceArr[i] = new Dice();
		}
	}
	/**
	 * Rolls all the dices contained within the dice arr
	 */
	public void roll() {
		for (int i = 0; i < diceArr.length; i++) {
			diceArr[i].roll();
		}
	}
	/**
	 * Returns true if the dices are pairs
	 * @return
	 */
	public boolean isPair() {
		int tempValue = 0;
		for (Dice dice : diceArr) {
			if(tempValue == 0)
				tempValue = dice.getFaceValue();
			if(tempValue != dice.getFaceValue())
				return false;
		}
		return true;
	}
	
	public int getTotalFaceValue() {
		int total = 0;
		for (int i = 0; i < diceArr.length; i++) {
			total = total + diceArr[i].getFaceValue();
		}
		return total;
	}
	
	public Dice[] getDiceArr() {
		return diceArr;
	}
	
	

}
