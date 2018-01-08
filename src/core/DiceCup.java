package core;

public class DiceCup {
	private Dice diceArr[];
	
	public DiceCup(int amoutOfDice) {
		diceArr = new Dice[amoutOfDice];
		for ( int i = 0 ; i < diceArr.length ; i++)
			diceArr[i] = new Dice();	
	}

}
