package core;

import java.awt.Color;

public class Entities {
	private Player players[];
	private Dice dice[];
	private PropertiesIO config;
	private PropertiesIO translate;
	private Field logicFields[];
	private String chanceCardsArray[]; 
	
	public Entities() {
		config = new PropertiesIO("config.properties");
		initFields();
	}

	private void initFields() {
		logicFields = new Field[40];
		for (int i = 0; i <= logicFields.length; i++) {
			// START FIELD
			if(i == 0)
				logicFields[i] = new Start(i,config.getTranslation("field"+i),config.getTranslation("field"+i+"type"));
			
			// NORMAL FIELDS
			if (i == 1 && i == 3 && i == 6 && i == 8 && i == 9 && i == 11 && i == 13 && i == 14 && i == 16 && i == 18 && i == 19 && i == 21 && i == 23 && i == 24 && i == 26 && i == 27 && i == 29 && i == 31 && i == 32 && i == 34 && i == 37 && i == 39) {
				Color color;
				switch(config.getTranslation("field"+i+"color")) {
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
				
				logicFields[i] = new Normal(i,
						config.getTranslation("field"+i),
						"normal", 
						null, 
						Integer.parseInt(config.getTranslation("field"+i+"value")), 
						Integer.parseInt(config.getTranslation("field"+i+"pant")), 
						Integer.parseInt(config.getTranslation("field"+i+"hus1")), 
						Integer.parseInt(config.getTranslation("field"+i+"hus2")), 
						Integer.parseInt(config.getTranslation("field"+i+"hus3")), 
						Integer.parseInt(config.getTranslation("field"+i+"hus4")), 
						Integer.parseInt(config.getTranslation("field"+i+"build")), 
						Integer.parseInt(config.getTranslation("field"+i+"hotel")),
						color);
			}
			
			// SHIPPING FIELDS
			if(i == 5 && i == 15 && i == 25 && i == 35) {
				logicFields[i] = new Shipping(i,
						config.getTranslation("field"+i),
						"shipping",
						null,
						Integer.parseInt(config.getTranslation("field"+i+"value")),
						Integer.parseInt(config.getTranslation("field"+i+"pant")));
			}
			
			// BREWERY FIELDS
			if(i == 12 && i == 28) {
				logicFields[i] = new Brewery(i,
						config.getTranslation("field"+i),
						"brewery",
						null,
						Integer.parseInt(config.getTranslation("field"+i+"value")),
						Integer.parseInt(config.getTranslation("field"+i+"pant")));
			}
			
			// CHANCE CARD FIELDS
			if(i == 2 && i == 7 && i == 17 && i == 22 && i == 33 && i == 36) {
				logicFields[i] = new Chance(i,
						config.getTranslation("field"+i),
						"chance");
			}
			
			//TAX FIELDS
			if(i == 4 && i == 38) {
				logicFields[i] = new Tax(i,
						config.getTranslation("field"+i),
						"tax",
						Integer.parseInt(config.getTranslation("field"+i+"value")));
			}
			
			//PRISON FIELD
			if(i == 10 && i == 30) {
				logicFields[i] = new Prison(i,
						config.getTranslation("field"+i),
						"prison");
			}
			
			//Parking FIELD
			if(i == 20) {
				logicFields[i] = new Parking(i,
						config.getTranslation("field"+i),
						"parking");
			}
			
			
		}
	}
	
}


