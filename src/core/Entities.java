package core;

import java.awt.Color;
import java.util.concurrent.ThreadLocalRandom;

public class Entities {
	private static Entities entities = null;
	private PropertiesIO config;
	private PropertiesIO translate;	
	private Field fieldArr[];
	private Dice diceArr[];
	private int chanceCardArr[]; 
	private Player playerArr[];
	private Field[] NormalBlue = new Field[2];
	private Field[] NormalRed = new Field[3];
	private Field[] NormalPurple = new Field[2];
	private Field[] NormalYellow = new Field[3];
	private Field[] NormalWhite = new Field[3];
	private Field[] NormalGrey = new Field[3];
	private Field[] NormalGreen = new Field[3];
	private Field[] NormalOrange = new Field[3];
	private int blue = 0, red = 0, purple = 0, yellow = 0, white = 0, grey = 0, green = 0, orange = 0;
	

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

	/**
	 * initializes the fields
	 */
	private void initFields() {
		fieldArr = new Field[40]; // We create an array of the lenght 40 of the type field
		for (int i = 0; i < fieldArr.length ; i++) { // Loop through all our fields
			String description = "";
			String[] descriptionSplit;

			if(i == 0) { // START FIELD "id, name, description"
				description = config.getTranslation("startdescription"); // As we have to put something inbetween the sentence we split it
				descriptionSplit = description.split(",");
				description = (descriptionSplit[0]+config.getTranslation("startpassedvalue")+descriptionSplit[1]);
				fieldArr[i] = new Start(i,
						config.getTranslation("field"+(i+1)),
						description);
			} else if(i == 5 || i == 15 || i == 25 || i == 35) { // SHIPPING FIELDS "id, name, owner, basevalue, pawnvalue, description)
				description = config.getTranslation("shippingdesc")+config.getTranslation("field"+(i+1)+"pant");
				description = description.replace("[1]", ""+config.getTranslation("field"+(i+1)+"leje"));
				description = description.replace("[2]", ""+config.getTranslation("field"+(i+1)+"rederi2"));
				description = description.replace("[3]", ""+config.getTranslation("field"+(i+1)+"rederi3"));
				description = description.replace("[4]", ""+config.getTranslation("field"+(i+1)+"rederi4"));
				fieldArr[i] = new Shipping(i,
						config.getTranslation("field"+(i+1)),
						null,
						Integer.parseInt(config.getTranslation("field"+(i+1)+"value")),
						Integer.parseInt(config.getTranslation("field"+(i+1)+"pant")),
						description);
			} else if(i == 12 || i == 28) { // BREWERY FIELDS "id, name, owner, basevalue, pawnvalue, description"
				description = config.getTranslation("brewerydesc")+config.getTranslation("field"+(i+1)+"value");
				fieldArr[i] = new Brewery(i,
						config.getTranslation("field"+(i+1)),
						null,
						Integer.parseInt(config.getTranslation("field"+(i+1)+"value")),
						Integer.parseInt(config.getTranslation("field"+(i+1)+"pant")),
						description);
			} else if(i == 2 || i == 7 || i == 17 || i == 22 || i == 33 || i == 36) { // CHANCE CARD FIELDS "id, name, description"
				description = config.getTranslation("chancedesc");
				fieldArr[i] = new Chance(i,
						config.getTranslation("field"+(i+1)),
						description);
			} else if(i == 4 || i == 38) { //TAX FIELDS "id, name, taxvalue, description"
				fieldArr[i] = new Tax(i,
						config.getTranslation("field"+(i+1)),
						Integer.parseInt(config.getTranslation("field"+(i+1)+"value")),
						"");
			} else if(i == 10 || i == 30) { //PRISON FIELD "id, name, description"
				if (i == 10) description = config.getTranslation("prisondesc1");
				else description = config.getTranslation("prisondesc2");
				fieldArr[i] = new Prison(i,
						config.getTranslation("field"+(i+1)),
						description);
			} else if(i == 20) { //Parking FIELD "id, name, description"
				description = config.getTranslation("parkingdesc");
				fieldArr[i] = new Parking(i,
						config.getTranslation("field"+(i+1)),
						description);
			} else {
				// NORMAL FIELDS "id, name, owner, basevalue, houseprices, pawnvalue, buildprice, colour, description"
				//if (i == 1 || i == 3 || i == 6 || i == 8 || i == 9 || i == 11 || i == 13 || i == 14 || i == 16 || i == 18 || i == 19 || i == 21 || i == 23 || i == 24 || i == 26 || i == 27 || i == 29 || i == 31 || i == 32 || i == 34 || i == 37 || i == 39) {
				Color color;
				switch(config.getTranslation("field"+(i+1)+"color")) { // Switch tot ranslate colour from config to gui colour.
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
				
			
				

				int housePrices[] = { // Get all the house prices 
						Integer.parseInt(config.getTranslation("field"+(i+1)+"value")),
						Integer.parseInt(config.getTranslation("field"+(i+1)+"hus1")), 
						Integer.parseInt(config.getTranslation("field"+(i+1)+"hus2")), 
						Integer.parseInt(config.getTranslation("field"+(i+1)+"hus3")), 
						Integer.parseInt(config.getTranslation("field"+(i+1)+"hus4")), 
						Integer.parseInt(config.getTranslation("field"+(i+1)+"hotel"))
				};

				descriptionSplit = new String[10]; // Split description of field, made up of 10 parts
				for (int j = 0 ; j < descriptionSplit.length ; j++) {
					descriptionSplit[j] = config.getTranslation("propertydesc"+(j+1)); // Loop through the config and fill out array
				}

				description = ( // put everything in the right order
						descriptionSplit[0]+config.getTranslation("field"+(i+1)+"leje")+"\n"+
						descriptionSplit[1]+config.getTranslation("field"+(i+1)+"hus1")+"\n"+
						descriptionSplit[2]+config.getTranslation("field"+(i+1)+"hus2")+"\n"+
						descriptionSplit[3]+config.getTranslation("field"+(i+1)+"hus3")+"\n"+
						descriptionSplit[4]+config.getTranslation("field"+(i+1)+"hus4")+"\n"+
						descriptionSplit[5]+config.getTranslation("field"+(i+1)+"hotel")+"\n"+
								descriptionSplit[6]+"\n"+
								descriptionSplit[7]+config.getTranslation("field"+(i+1)+"build")+"\n"+
								descriptionSplit[8]+config.getTranslation("field"+(i+1)+"build")+"\n"+
								descriptionSplit[9]+config.getTranslation("field"+(i+1)+"pant")+"\n"
						);

				fieldArr[i] = new Normal(i, // Constructor is called here, we inititialize a new normal object
						config.getTranslation("field"+(i+1)), 
						null,
						Integer.parseInt(config.getTranslation("field"+(i+1)+"value")), 
						housePrices,
						Integer.parseInt(config.getTranslation("field"+(i+1)+"pant")), 
						Integer.parseInt(config.getTranslation("field"+(i+1)+"build")), 
						color,
						description);
				
				switch (color.toString()) {
				case "Color.blue":
					NormalBlue[blue] = fieldArr[i];
					blue++;
					break;
				case "Color.yellow":
					NormalYellow[yellow] = fieldArr[i];
					yellow++;
					break;
				case "Color.magenta":
					NormalPurple[purple] = fieldArr[i];
					purple++;
					break;
				case "Color.orange":
					NormalOrange[orange] = fieldArr[i];
					orange++;
					break;
				case "Color.white":
					NormalWhite[white] = fieldArr[i];
					white++;
					break;
				case "Color.green":
					NormalGreen[green] = fieldArr[i];
					green++;
					break;
				case "Color.grey":
					NormalGrey[grey] = fieldArr[i];
					grey++;
					break;
				case "Color.red":
					NormalRed[red] = fieldArr[i];
					red++;
					break;
				}
				
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


