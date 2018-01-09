package core;

import java.awt.Color;
import java.util.concurrent.ThreadLocalRandom;

public class Entities {
	private static Entities entities = null;
	private PropertiesIO config;
	private PropertiesIO translate;	
	private int chanceCardArr[]; 
	private Player playerArr[];

	private Entities() {
		//load config and translation
		config = new PropertiesIO("config.properties");
		// translate = new PropertiesIO("translate.properties");

		//initialize fields, dies and chanceCards
		initDies(2);
		initChanceCards(32);

		/* 
		 * PlayerArr will only be initialized with a call to the public function: initPlayers 
		 */
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
	private void initDies(int amountOfDies) {
		diceArr = new Dice[amountOfDies];
		for ( int i = 0 ; i < diceArr.length ; i++)
			diceArr[i] = new Dice();		
	}

	public static Entities getInstance()
	{
		if (entities == null)
			entities = new Entities();

		return entities;
	}


	public Dice[] getDiceArr() {
		return diceArr;
	}

	public void setDiceArr(Dice[] diceArr) {
		this.diceArr = diceArr;
	}

	public PropertiesIO getConfig() {
		return config;
	}

	public void setConfig(PropertiesIO config) {
		this.config = config;
	}

	public PropertiesIO getTranslate() {
		return translate;
	}

	public void setTranslate(PropertiesIO translate) {
		this.translate = translate;
	}

	public Field[] getFieldArr() {
		return fieldArr;
	}

	public void setFieldArr(Field[] fieldArr) {
		this.fieldArr = fieldArr;
	}

	public int[] getChanceCardArr() {
		return chanceCardArr;
	}

	public void setChanceCardArr(int[] chanceCardArr) {
		this.chanceCardArr = chanceCardArr;
	}
}


