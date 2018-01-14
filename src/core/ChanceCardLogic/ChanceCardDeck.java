package core.ChanceCardLogic;

/**
 * @author Magnus Stjernborg Koch - s175189
 *
 */
public class ChanceCardDeck {
	//When all cards are added
	private int maxSize;
	//The array of the chance cards
	private ChanceCard[] stackArray;
	private int top;

	public ChanceCardDeck(int s) {
		maxSize = s;
		stackArray = new ChanceCard[maxSize];
		top = -1;
	}

	//Pushing a chancecard into the deck
	public void push(ChanceCard j) {
		stackArray[++top] = j;
	}

	//Takes the card from the bottom of the deck and then reorder the deck so all cards moves one down in the order.
	public ChanceCard bottom() {
		ChanceCard[] tempStackArray = new ChanceCard[maxSize];
		ChanceCard returnCard = stackArray[0];
		
		--top;
		for(int i = 0 ; i <= top ; i++) {
			tempStackArray[i] = stackArray[i+1];
		}
		stackArray = tempStackArray;
		
		return returnCard;
	}
}
