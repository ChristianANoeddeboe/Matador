package core;

import java.awt.Color;

import gui_fields.GUI_Field;
import gui_fields.GUI_Street;

public class Entities {
	protected Player players[];
	protected Dice dice[];
	protected PropertiesIO config;
	protected PropertiesIO translate;
	protected Field logicFields[];
	protected String chanceCardsArray[]; 
	
	public Entities() {
		config = new PropertiesIO("config.properties");
	}

	private void initFields() {
		logicFields = new Field[40];
		for (int i = 0; i <= logicFields.length; i++) {
			// START FIELD
			if(i == 0)
				logicFields[i] = new Start(i,config.getTranslation("field"+i),config.getTranslation("field"+i+"type"));
			
			// NORMAL FIELDS
			if (i == 1 && i == 3) {
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
						config.getTranslation("field"+i+"color"));
			}
			
			// SHIPPING FIELDS
			if(i == 5) {
				logicFields[i] = new Shipping(i,
						config.getTranslation("field"+i),
						"shipping",
						null,
						Integer.parseInt(config.getTranslation("field"+i+"value")),
						Integer.parseInt(config.getTranslation("field"+i+"pant")));
			}
			
			// BREWERY FIELDS
			
			
			// CHANCE CARD FIELDS
			if(i == 2) {
				logicFields[i] = new Chance(i,
						config.getTranslation("field"+i),
						"chance");
			}
			
			//TAX FIELDS
			if(i == 4) {
				logicFields[i] = new Tax(i,
						config.getTranslation("field"+i),
						"tax",
						Integer.parseInt(config.getTranslation("field"+i+"value")));
			}
			
		}
	}
	
}


