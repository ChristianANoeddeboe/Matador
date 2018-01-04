package core;

import java.awt.Color;

public class Entities {
	private static Entities entities = null;
	private Player players[];
	private Dice dice[];
	private PropertiesIO config;
	private PropertiesIO translate;
	private Field fieldArr[];
	private String chanceCardsArray[]; 
	
	private Entities() {
		config = new PropertiesIO("config.properties");
		initFields();
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
			
			// START FIELD
			if(i == 0)
				fieldArr[i] = new Start(i,config.getTranslation("field"+(i+1)),config.getTranslation("field"+(i+1)+"type"));
			
			// NORMAL FIELDS
			if (i == 1 || i == 3 || i == 6 || i == 8 || i == 9 || i == 11 || i == 13 || i == 14 || i == 16 || i == 18 || i == 19 || i == 21 || i == 23 || i == 24 || i == 26 || i == 27 || i == 29 || i == 31 || i == 32 || i == 34 || i == 37 || i == 39) {
				Color color;
				switch(config.getTranslation("field"+(i+1)+"color")) {
					default : color = Color.black; break;
					case "yellow" : color = Color.yellow; break;
					case "blue" : color = Color.blue; break;
					case "pink" : color = Color.pink; break;
					case "brun"	: color = Color.orange; break;
					case "hvid" : color = Color.white; break;
					case "grøn" : color = Color.green; break;
					case "lilla" : color = Color.magenta; break;
					case "rød" : color = Color.red; break;
				}
				
				fieldArr[i] = new Normal(i,
						config.getTranslation("field"+(i+1)),
						"normal", 
						null, 
						Integer.parseInt(config.getTranslation("field"+(i+1)+"value")), 
						Integer.parseInt(config.getTranslation("field"+(i+1)+"pant")), 
						Integer.parseInt(config.getTranslation("field"+(i+1)+"hus1")), 
						Integer.parseInt(config.getTranslation("field"+(i+1)+"hus2")), 
						Integer.parseInt(config.getTranslation("field"+(i+1)+"hus3")), 
						Integer.parseInt(config.getTranslation("field"+(i+1)+"hus4")), 
						Integer.parseInt(config.getTranslation("field"+(i+1)+"build")), 
						Integer.parseInt(config.getTranslation("field"+(i+1)+"hotel")),
						color);
			}
			
			// SHIPPING FIELDS
			if(i == 5 || i == 15 || i == 25 || i == 35) {
				fieldArr[i] = new Shipping(i,
						config.getTranslation("field"+(i+1)),
						"shipping",
						null,
						Integer.parseInt(config.getTranslation("field"+(i+1)+"value")),
						Integer.parseInt(config.getTranslation("field"+(i+1)+"pant")));
			}
			
			// BREWERY FIELDS
			if(i == 12 || i == 28) {
				fieldArr[i] = new Brewery(i,
						config.getTranslation("field"+(i+1)),
						"brewery",
						null,
						Integer.parseInt(config.getTranslation("field"+(i+1)+"value")),
						Integer.parseInt(config.getTranslation("field"+(i+1)+"pant")));
			}
			
			// CHANCE CARD FIELDS
			if(i == 2 || i == 7 || i == 17 || i == 22 || i == 33 || i == 36) {
				fieldArr[i] = new Chance(i,
						config.getTranslation("field"+(i+1)),
						"chance");
			}
			
			//TAX FIELDS
			if(i == 4 || i == 38) {
				fieldArr[i] = new Tax(i,
						config.getTranslation("field"+(i+1)),
						"tax",
						Integer.parseInt(config.getTranslation("field"+(i+1)+"value")));
			}
			
			//PRISON FIELD
			if(i == 10 || i == 30) {
				fieldArr[i] = new Prison(i,
						config.getTranslation("field"+(i+1)),
						"prison");
			}
			
			//Parking FIELD
			if(i == 20) {
				fieldArr[i] = new Parking(i,
						config.getTranslation("field"+(i+1)),
						"parking");
			}
			
			
		}
	}

	public Player[] getPlayers() {
		return players;
	}

	public void setPlayers(Player[] players) {
		this.players = players;
	}

	public Dice[] getDice() {
		return dice;
	}

	public void setDice(Dice[] dice) {
		this.dice = dice;
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

	public String[] getChanceCardsArray() {
		return chanceCardsArray;
	}

	public void setChanceCardsArray(String[] chanceCardsArray) {
		this.chanceCardsArray = chanceCardsArray;
	}
	
}


