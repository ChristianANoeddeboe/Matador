package core;

public class DiceCup {
	private Dice diceArr[];
	
	public DiceCup(int amoutOfDice) {
		diceArr = new Dice[amoutOfDice];
	}
	
	public void roll() {
		for (Dice dice : diceArr)
			dice.roll();
		
	}
	
	public boolean isPair() {
		int tempValue = 0;
		for (Dice dice : diceArr) {
			if(tempValue == 0)
				tempValue = dice.getValue();
			if(tempValue != dice.getValue())
				return false;
		}
		return true;
	}
	
	public int getTotalFaceValue() {
		int total = 0;
		for (Dice dice : diceArr)
			total += dice.getValue();
		return total;
	}
	
	

}
