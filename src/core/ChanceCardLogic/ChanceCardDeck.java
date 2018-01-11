package core.ChanceCardLogic;

public class ChanceCardDeck {
	private int maxSize;
	private ChanceCard[] stackArray;
	private int top;

	public ChanceCardDeck(int s) {
		maxSize = s;
		stackArray = new ChanceCard[maxSize];
		top = -1;
	}
	
	public void push(ChanceCard j) {
		stackArray[++top] = j; array out of bounds exception, når fængselskort skal lægges tilbage
	}
	
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
