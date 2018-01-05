package core;

import org.omg.CosNaming.NamingContextPackage.NotFound;

import java.awt.Color;
import java.util.NoSuchElementException;
import java.util.concurrent.ThreadLocalRandom;

public class Entities {
	private static Entities entities = null;
	private PropertiesIO config;
	private PropertiesIO translate;	
	private Field fieldArr[];
	private Dice diceArr[];
	private int chanceCardArr[]; 
	private Player playerArr[];

	private Entities() {
		//load config and translation
		config = new PropertiesIO("config.properties");
		// translate = new PropertiesIO("translate.properties");

		//initialize fields, dies and chanceCards
		initFields();
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

	private void initFields() {
		fieldArr = new Field[40];
		for (int i = 0; i <= fieldArr.length ; i++) {

			String description = "";
			String[] descriptionSplit;

			// START FIELD
			if(i == 0) {
				description = config.getTranslation("field"+(i+1)+"description");
				descriptionSplit = description.split(",");
				description = descriptionSplit[0]+config.getTranslation("field"+(i+1)+"value")+descriptionSplit[1];
				fieldArr[i] = new Start(i,
						config.getTranslation("field"+(i+1)),
						description);
			}
			// NORMAL FIELDS
			if (i == 1 || i == 3 || i == 6 || i == 8 || i == 9 || i == 11 || i == 13 || i == 14 || i == 16 || i == 18 || i == 19 || i == 21 || i == 23 || i == 24 || i == 26 || i == 27 || i == 29 || i == 31 || i == 32 || i == 34 || i == 37 || i == 39) {
				Color color;
				switch(config.getTranslation("field"+(i+1)+"color")) {
				default : color = Color.black; break;
				case "gul" : color = Color.yellow; break;
				case "blå" : color = Color.blue; break;
				case "pink" : color = Color.pink; break;
				case "brun"	: color = Color.orange; break;
				case "hvid" : color = Color.white; break;
				case "grøn" : color = Color.green; break;
				case "lilla" : color = Color.magenta; break;
				case "rød" : color = Color.red; break;
				case "grå" : color = Color.gray; break;

				}

				int housePrices[] = { 
						Integer.parseInt(config.getTranslation("field"+(i+1)+"value")),
						Integer.parseInt(config.getTranslation("field"+(i+1)+"hus1")), 
						Integer.parseInt(config.getTranslation("field"+(i+1)+"hus2")), 
						Integer.parseInt(config.getTranslation("field"+(i+1)+"hus3")), 
						Integer.parseInt(config.getTranslation("field"+(i+1)+"hus4")), 
						Integer.parseInt(config.getTranslation("field"+(i+1)+"hotel"))
				};

				fieldArr[i] = new Normal(i,
						config.getTranslation("field"+(i+1)), 
						null,
						Integer.parseInt(config.getTranslation("field"+(i+1)+"value")), 
						housePrices,
						Integer.parseInt(config.getTranslation("field"+(i+1)+"pant")), 
						Integer.parseInt(config.getTranslation("field"+(i+1)+"build")), 
						color,
						"test");
			}

			// SHIPPING FIELDS
			if(i == 5 || i == 15 || i == 25 || i == 35) {
				fieldArr[i] = new Shipping(i,
						config.getTranslation("field"+(i+1)),
						null,
						Integer.parseInt(config.getTranslation("field"+(i+1)+"value")),
						Integer.parseInt(config.getTranslation("field"+(i+1)+"pant")),
						"test");
			}

			// BREWERY FIELDS
			if(i == 12 || i == 28) {
				fieldArr[i] = new Brewery(i,
						config.getTranslation("field"+(i+1)),
						null,
						Integer.parseInt(config.getTranslation("field"+(i+1)+"value")),
						Integer.parseInt(config.getTranslation("field"+(i+1)+"pant")),
						"test");
			}

			// CHANCE CARD FIELDS
			if(i == 2 || i == 7 || i == 17 || i == 22 || i == 33 || i == 36) {
				fieldArr[i] = new Chance(i,
						config.getTranslation("field"+(i+1)),
						"test");
			}

			//TAX FIELDS
			if(i == 4 || i == 38) {
				fieldArr[i] = new Tax(i,
						config.getTranslation("field"+(i+1)),
						Integer.parseInt(config.getTranslation("field"+(i+1)+"value")),
						"test");
			}

			//PRISON FIELD
			if(i == 10 || i == 30) {
				fieldArr[i] = new Prison(i,
						config.getTranslation("field"+(i+1)),
						"test");
			}

			//Parking FIELD
			if(i == 20) {
				fieldArr[i] = new Parking(i,
						config.getTranslation("field"+(i+1)),
						"test");
			}


		}
	}

	public void initPlayers(int amountOfPlayers) {
		playerArr = new Player[amountOfPlayers];
	}

	public Player[] getPlayers() {
		return playerArr;
	}

	public void setPlayers(Player[] playerArr) {
		this.playerArr = playerArr;
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


