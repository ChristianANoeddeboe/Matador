package core;

import java.awt.Color;
import java.util.concurrent.ThreadLocalRandom;

public class Entities {
	private int chanceCardArr[]; 

	private Entities() {
		initChanceCards(32);

	}

	private void initChanceCards(int amountOfChanceCards) {
		chanceCardArr = new int[amountOfChanceCards];
		for (int i = 0 ; i < chanceCardArr.length ; i++)
		{
			int rndNumber;
			boolean newCard;

			do {
				newCard = true;
				rndNumber = ThreadLocalRandom.current().nextInt(0, chanceCardArr.length);

				for (int j = 0 ; j < i ; j++)
					if(chanceCardArr[j] == rndNumber) {
						newCard = false;
						break;
					}
			} while (!newCard);

			chanceCardArr[i] = rndNumber;
		}
	}
	
	/**
	 * 
	 * @param amountOfDies
	 */

	public int[] getChanceCardArr() {
		return chanceCardArr;
	}

	public void setChanceCardArr(int[] chanceCardArr) {
		this.chanceCardArr = chanceCardArr;
	}
}


