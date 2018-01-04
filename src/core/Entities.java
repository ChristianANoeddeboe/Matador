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
				logicFields[0] = new Start(i,config.getTranslation("field"+i),config.getTranslation("field"+i+"type"));
			
			// NORMAL FIELDS
			if (i == 1) {
				logicFields[0] = new Normal(i,config.getTranslation("field"+i),"normal", null, Integer.parseInt(config.getTranslation("field"+i+"value")), Integer.parseInt(config.getTranslation("field"+i+"pant")), Integer.parseInt(config.getTranslation("field"+i+"value")), config.getTranslation("field"+i+"color")), Integer.parseInt(config.getTranslation("field"+i+"value"));
			}
			
			// SHIPPING FIELDS
			
			
			// BREWERY FIELDS
			
			
			// CHANCE CARD FIELDS
			
			
			//
			if
			switch (i) {
			case 0 : 	
						break;
			case 1 :	
				
				
					
				
			}
			if(i>=1&&i<=Integer.valueOf(config.getTranslation("amountoffields"))) {
				
				logicFields[i-1] = new GUI_Street(config.getTranslation("field"+i), config.getTranslation("valuetext") + " " + (config.getTranslation("fieldvalue"+i)), "", "", Color.GREEN, Color.BLACK);
				if(Integer.parseInt(GameController.translator.getTranslation("fieldvalue"+i))==0) {
					GameController.prisonarray[i-1] = 1;
				}
				try {
					if(GameController.translator.getTranslation("field"+i+"taxi").equals("true")) {
						GameController.taxiarray[i-1]=1;
					}
				} catch (Exception e) {
					//No  need to deal with fields that are NOT taxi fields
				}

			}else {
				if(i-1 > 0) {
					fields[i-1] = new GUI_Street("", "", "", "", Color.GREEN, Color.GREEN);
				}
			}
		}
	}

	protected static void toString(GUI_Field[] fields) {
		for (int i = 0; i < Integer.parseInt(GameController.translator.getTranslation("amountoffields")); i++) {
			System.out.println(i+"|Field title: "+ fields[i].getTitle() + " "+ fields[i].getSubText());
		}
	}
	
}


