package core;

import java.awt.Color;

public class FieldController {
	private Field fieldArr[];
	private PropertiesIO config;
	
	public FieldController() {
		config = new PropertiesIO("config.properties");
		initFields();
	}
	
	private void initFields() {
		fieldArr = new Field[40]; // We create an array of the lenght 40 of the type field
		for (int i = 0; i < fieldArr.length ; i++) { // Loop through all our fields
			String description = "";
			String[] descriptionSplit;

			if(i == 0) { 
				// START FIELD "id, name, description"
				description = config.getTranslation("startdescription"); // As we have to put something inbetween the sentence we split it
				descriptionSplit = description.split(",");
				description = (descriptionSplit[0]+config.getTranslation("startpassedvalue")+descriptionSplit[1]);
				fieldArr[i] = new Start(i,
						config.getTranslation("field"+(i+1)),
						description);
			} else if(i == 5 || i == 15 || i == 25 || i == 35) { 
				// SHIPPING FIELDS "id, name, owner, basevalue, pawnvalue, description)
				description = config.getTranslation("shippingdesc")+config.getTranslation("field"+(i+1)+"pant");
				description = description.replace("[1]", ""+config.getTranslation("field"+(i+1)+"leje"));
				description = description.replace("[2]", ""+config.getTranslation("field"+(i+1)+"rederi2"));
				description = description.replace("[3]", ""+config.getTranslation("field"+(i+1)+"rederi3"));
				description = description.replace("[4]", ""+config.getTranslation("field"+(i+1)+"rederi4"));
				fieldArr[i] = new Shipping(i, 
						config.getTranslation("field"+(i+1)), 
						description, 
						null, 
						Integer.parseInt(config.getTranslation("field"+(i+1)+"value")), 
						Integer.parseInt(config.getTranslation("field"+(i+1)+"pant")), 
						Integer.parseInt(config.getTranslation("field"+(i+1)+"leje")));
			} else if(i == 12 || i == 28) { 
				// BREWERY FIELDS "id, name, owner, basevalue, pawnvalue, description"
				description = config.getTranslation("brewerydesc")+config.getTranslation("field"+(i+1)+"value");
				fieldArr[i] = new Brewery(i, 
						config.getTranslation("field"+(i+1)), 
						description, 
						null, 
						Integer.parseInt(config.getTranslation("field"+(i+1)+"value")), 
						Integer.parseInt(config.getTranslation("field"+(i+1)+"pant")), 
						2000);
			} else if(i == 2 || i == 7 || i == 17 || i == 22 || i == 33 || i == 36) { 
				// CHANCE CARD FIELDS "id, name, description"
				description = config.getTranslation("chancedesc");
				fieldArr[i] = new Chance(i,
						config.getTranslation("field"+(i+1)),
						description);
			} else if(i == 4 || i == 38) { 
				//TAX FIELDS "id, name, taxvalue, description"
				fieldArr[i] = new Tax(i,
						config.getTranslation("field"+(i+1)),
						"");
			} else if(i == 10 || i == 30) { 
				//PRISON FIELD "id, name, description"
				if (i == 10) description = config.getTranslation("prisondesc1");
				else description = config.getTranslation("prisondesc2");
				fieldArr[i] = new Prison(i,
						config.getTranslation("field"+(i+1)),
						description);
			} else if(i == 20) { 
				//Parking FIELD "id, name, description"
				description = config.getTranslation("parkingdesc");
				fieldArr[i] = new Parking(i,
						config.getTranslation("field"+(i+1)),
						description);
			} else {				
				// Switch tot ranslate colour from config to gui colour.
				Color color;
				switch(config.getTranslation("field"+(i+1)+"color")) { 
					default : color = Color.black; break;
					case "gul" : color = Color.yellow; break;
					case "blå" : color = Color.blue; break;
					case "pink" : color = Color.orange; break;
					case "hvid" : color = Color.white; break;
					case "grøn" : color = Color.green; break;
					case "lilla" : color = Color.magenta; break;
					case "rød" : color = Color.red; break;
					case "grå" : color = Color.gray; break;
				}

				// Get house prices from config and save to array
				int housePrices[] = { // Get all the house prices 
						Integer.parseInt(config.getTranslation("field"+(i+1)+"value")),
						Integer.parseInt(config.getTranslation("field"+(i+1)+"hus1")), 
						Integer.parseInt(config.getTranslation("field"+(i+1)+"hus2")), 
						Integer.parseInt(config.getTranslation("field"+(i+1)+"hus3")), 
						Integer.parseInt(config.getTranslation("field"+(i+1)+"hus4")), 
						Integer.parseInt(config.getTranslation("field"+(i+1)+"hotel"))
				};

				// Split description of field, made up of 10 parts
				descriptionSplit = new String[10]; 
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

				// STREET (int id, String name, String description, Player owner, int buyValue, int pawnValue, int rentValue, int[] housePrices, int buildPrice, Color colour)
				fieldArr[i] = new Street(i, 
						config.getTranslation("field"+(i+1)), 
						description, 
						null, 
						Integer.parseInt(config.getTranslation("field"+(i+1)+"value")), 
						Integer.parseInt(config.getTranslation("field"+(i+1)+"pant")), 
						housePrices[0], 
						housePrices, 
						Integer.parseInt(config.getTranslation("field"+(i+1)+"build")), 
						color);
			}
		}
	}

	public Field[] getFieldArr() {
		return fieldArr;
	}

	public void setFieldArr(Field[] fieldArr) {
		this.fieldArr = fieldArr;
	}

}
