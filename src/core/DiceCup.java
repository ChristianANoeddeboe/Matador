package core;

public class DiceCup {
	private Dice diceArr[];
	
	public DiceCup(int amoutOfDice) {
		diceArr = new Dice[amoutOfDice];
		for (int i = 0; i < diceArr.length; i++) {
			diceArr[i] = new Dice();
		}
	}
	
	public void roll() {
		for (int i = 0; i < diceArr.length; i++) {
			diceArr[i].roll();
		}
	}
	
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
		for (Dice dice : diceArr)
			total += dice.getFaceValue();
		return total;
	}
	
	public Dice[] getDiceArr() {
		return diceArr;
	}
	
	

}
