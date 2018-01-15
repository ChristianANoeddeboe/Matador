package core;

import java.awt.Color;
/**
 * 
 * @author Mathias && Simon && Nicolai
 *
 */
public class FieldController {
	private Field fieldArr[];

	public FieldController() {
		initFields();
	}

	/**
	 * Fills out the fields array
	 */
	private void initFields() {
		fieldArr = new Field[40]; // We create an array of the lenght 40 of the type field
		for (int i = 0; i < fieldArr.length ; i++) { // Loop through all our fields
			String description = "";
			String[] descriptionSplit;

			if(i == 0) { 
				// START FIELD "id, name, description"
				description = PropertiesIO.getTranslation("startdescription"); // As we have to put something inbetween the sentence we split it
				descriptionSplit = description.split(",");
				description = (descriptionSplit[0]+PropertiesIO.getTranslation("startpassedvalue")+descriptionSplit[1]);
				fieldArr[i] = new Start(i,
						PropertiesIO.getTranslation("field"+(i+1)),
						description);
			} else if(i == 5 || i == 15 || i == 25 || i == 35) { 
				// SHIPPING FIELDS "id, name, owner, basevalue, pawnvalue, description)
				description = PropertiesIO.getTranslation("shippingdesc")+PropertiesIO.getTranslation("field"+(i+1)+"pant");
				description = description.replace("[1]", ""+PropertiesIO.getTranslation("field"+(i+1)+"leje"));
				description = description.replace("[2]", ""+PropertiesIO.getTranslation("field"+(i+1)+"rederi2"));
				description = description.replace("[3]", ""+PropertiesIO.getTranslation("field"+(i+1)+"rederi3"));
				description = description.replace("[4]", ""+PropertiesIO.getTranslation("field"+(i+1)+"rederi4"));
				fieldArr[i] = new Shipping(i, 
						PropertiesIO.getTranslation("field"+(i+1)), 
						description, 
						null, 
						Integer.parseInt(PropertiesIO.getTranslation("field"+(i+1)+"value")), 
						Integer.parseInt(PropertiesIO.getTranslation("field"+(i+1)+"pant")), 
						Integer.parseInt(PropertiesIO.getTranslation("field"+(i+1)+"leje")));
			} else if(i == 12 || i == 28) { 
				// BREWERY FIELDS "id, name, owner, basevalue, pawnvalue, description"
				description = PropertiesIO.getTranslation("brewerydesc")+PropertiesIO.getTranslation("field"+(i+1)+"value");
				fieldArr[i] = new Brewery(i, 
						PropertiesIO.getTranslation("field"+(i+1)), 
						description, 
						null, 
						Integer.parseInt(PropertiesIO.getTranslation("field"+(i+1)+"value")), 
						Integer.parseInt(PropertiesIO.getTranslation("field"+(i+1)+"pant")), 
						2000);
			} else if(i == 2 || i == 7 || i == 17 || i == 22 || i == 33 || i == 36) { 
				// CHANCE CARD FIELDS "id, name, description"
				description = PropertiesIO.getTranslation("chancedesc");
				fieldArr[i] = new Chance(i,
						PropertiesIO.getTranslation("field"+(i+1)),
						description);
			} else if(i == 4 || i == 38) { 
				//TAX FIELDS "id, name, taxvalue, description"
				fieldArr[i] = new Tax(i,
						PropertiesIO.getTranslation("field"+(i+1)),
						"");
			} else if(i == 10 || i == 30) { 
				//PRISON FIELD "id, name, description"
				if (i == 10) description = PropertiesIO.getTranslation("prisondesc1");
				else description = PropertiesIO.getTranslation("prisondesc2");
				fieldArr[i] = new Prison(i,
						PropertiesIO.getTranslation("field"+(i+1)),
						description);
			} else if(i == 20) { 
				//Parking FIELD "id, name, description"
				description = PropertiesIO.getTranslation("parkingdesc");
				fieldArr[i] = new Parking(i,
						PropertiesIO.getTranslation("field"+(i+1)),
						description);
			} else {				
				// Switch tot ranslate colour from PropertiesIO to gui colour.
				Color color;
				switch(PropertiesIO.getTranslation("field"+(i+1)+"color")) { 
				default : color = Color.black; break;
				case "yellow" : color = Color.yellow; break;
				case "blue" : color = Color.blue; break;
				case "pink" : color = Color.orange; break;
				case "white" : color = Color.white; break;
				case "green" : color = Color.green; break;
				case "purple" : color = Color.magenta; break;
				case "red" : color = Color.red; break;
				case "grey" : color = Color.gray; break;
				}

				// Get house prices from PropertiesIO and save to array
				int housePrices[] = { // Get all the house prices 
						Integer.parseInt(PropertiesIO.getTranslation("field"+(i+1)+"leje")),
						Integer.parseInt(PropertiesIO.getTranslation("field"+(i+1)+"hus1")), 
						Integer.parseInt(PropertiesIO.getTranslation("field"+(i+1)+"hus2")), 
						Integer.parseInt(PropertiesIO.getTranslation("field"+(i+1)+"hus3")), 
						Integer.parseInt(PropertiesIO.getTranslation("field"+(i+1)+"hus4")), 
						Integer.parseInt(PropertiesIO.getTranslation("field"+(i+1)+"hotel"))
				};

				// Split description of field, made up of 10 parts
				descriptionSplit = new String[10]; 
				for (int j = 0 ; j < descriptionSplit.length ; j++) {
					descriptionSplit[j] = PropertiesIO.getTranslation("propertydesc"+(j+1)); // Loop through the PropertiesIO and fill out array
				}

				description = ( // put everything in the right order
						descriptionSplit[0]+PropertiesIO.getTranslation("field"+(i+1)+"leje")+"\n"+
						descriptionSplit[1]+PropertiesIO.getTranslation("field"+(i+1)+"hus1")+"\n"+
						descriptionSplit[2]+PropertiesIO.getTranslation("field"+(i+1)+"hus2")+"\n"+
						descriptionSplit[3]+PropertiesIO.getTranslation("field"+(i+1)+"hus3")+"\n"+
						descriptionSplit[4]+PropertiesIO.getTranslation("field"+(i+1)+"hus4")+"\n"+
						descriptionSplit[5]+PropertiesIO.getTranslation("field"+(i+1)+"hotel")+"\n"+
						descriptionSplit[6]+"\n"+
						descriptionSplit[7]+PropertiesIO.getTranslation("field"+(i+1)+"build")+"\n"+
						descriptionSplit[8]+PropertiesIO.getTranslation("field"+(i+1)+"build")+"\n"+
						descriptionSplit[9]+PropertiesIO.getTranslation("field"+(i+1)+"pant")+"\n"
						);

				// STREET (int id, String name, String description, Player owner, int buyValue, int pawnValue, int rentValue, int[] housePrices, int buildPrice, Color colour)
				fieldArr[i] = new Street(i, 
						PropertiesIO.getTranslation("field"+(i+1)), 
						description, 
						null, 
						Integer.parseInt(PropertiesIO.getTranslation("field"+(i+1)+"value")), 
						Integer.parseInt(PropertiesIO.getTranslation("field"+(i+1)+"pant")), 
						housePrices[0], 
						housePrices, 
						Integer.parseInt(PropertiesIO.getTranslation("field"+(i+1)+"build")), 
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
	/**
	 * Generates a street array with all the fields that houses can be built on
	 * @param currentPlayer
	 * @return
	 */
	public Street[] allFieldsToBuildOn(Player currentPlayer) {
		Street streetArr[] = new Street[40];
		int val = 0;
		boolean exists = false;
		Color colour;

		// We loop over all our fields
		for (int i = 0; i < fieldArr.length; i++) {

			//Bool to stay 
			boolean canBeBuildOn = true;

			// We find the fields which are an instance of Normal
			if(fieldArr[i] instanceof Street) { 

				// Casting
				Street street = (Street) fieldArr[i];

				// We check if the current field is owned by the player and if there are too many houses already
				if(street.getOwner() == currentPlayer && street.getHouseCounter() <5) {
					//If the player can afford the buildprice
					if( currentPlayer.getAccount().canAfford(street.getBuildPrice())) {
						// Grab the colour
						colour = street.getColour(); 

						// Start an inner loop
						for (int j = 0; j < fieldArr.length; j++) {

							// Once again only want to look at the fields which are of the type normal
							if(fieldArr[j] instanceof Street) {

								// casting
								Street street2 = (Street) fieldArr[j];

								// Making sure that the fields of the same colour and the same owner, if not the same owner we return false
								if(street2.getColour() == colour && street2.getOwner() != currentPlayer) { 
									canBeBuildOn = false;
									break;
								}
							}
						}		

						//If property can be build on	
						if(canBeBuildOn == true) {
							streetArr[val++] = street;
						}
					}
				}
			}
		}
		/**
		 * We create a new array which only contains the streets we are allowed to build on with the right size
		 */
		Street[] sortedStreetArr = new Street[val];
		for (int i = 0; i < sortedStreetArr.length; i++) {
			sortedStreetArr[i] = streetArr[i];
		}
		return sortedStreetArr;
	}
	/**
	 * Generate an array of strings which contains that field title of the fields that the player calling owns
	 * @param currentPlayer
	 * @return
	 */
	public String[] FieldsOwned(Player currentPlayer) {

		// Makes a temporary String array with the length of all properties
		String[] temp = new String[28];

		// Counter
		int counter = 0;

		// Loops over all the fields
		for(int i = 0; i < fieldArr.length; i++) {

			// Checks if the field is an instance of the Property class
			if(fieldArr[i] instanceof Property) {

				// Casts the field to Property
				Property property = (Property) fieldArr[i];

				// Checks if the field is owned by the Player
				if(property.getOwner() == currentPlayer) {

					// Adds the field to the temp array
					temp[counter] = fieldArr[i].getName();

					// Adds one to the counter
					counter++;
				}
			}
		}

		// Makes new array with the length of counter
		String[] propertyOwned = new String[counter];

		// Loops over the temp array and adds the filled spots to the propertyOwned array
		for(int j = 0; j < counter; j++) {
			propertyOwned[j] = temp[j];
		}
		return propertyOwned;
	}


	public String[] propertiesToPawn(Player currentPlayer) {

		// Make a temporary String array with the length of all properties
		String[] temp = new String[28];

		// Counter
		int counter = 0;

		// Loops over all fields
		for(int i = 0; i < fieldArr.length; i++) {

			// Checks if the field is an instance of the Street class
			if(fieldArr[i] instanceof Street) {

				// Casts the field to street
				Street street = (Street) fieldArr[i];

				// Checks if the Street is owned by the player, checks if no houses are built on it and if it is already pawned
				if(street.getOwner() == currentPlayer && street.getHouseCounter() == 0 && !street.isPawned()) {

					// Adds the field to the temp array
					temp[counter] = fieldArr[i].getName();

					// Adds one to the counter
					counter++;
				}

				// Checks if the field is an instance of the Shipping Class
				if(fieldArr[i] instanceof Shipping) {

					// Adds the field to the temp array
					temp[counter] = fieldArr[i].getName();

					// Adds one to the counter
					counter++;
				}

				// Checks if the field is an instance of the Brewery Class
				if(fieldArr[i] instanceof Brewery) {

					// Adds the field to the temp array
					temp[counter] = fieldArr[i].getName();

					// Adds one to the counter
					counter++;
				}
			}
		}

		// Makes a new array with the length of counter
		String[] propertyOwned = new String[counter];

		// Loops over the temp array and adds the filled spots to the propertyOwned array
		for(int j = 0; j < counter; j++) {
			propertyOwned[j] = temp[j];
		}
		return propertyOwned;
	}

	public String[] streetsWithHouses(Player currentPlayer) {

		// Makes a temporary array with the length of all Street fields
		String[] temp = new String[22];

		// Counter
		int counter = 0;

		// Loops over all fields
		for(int i = 0; i < fieldArr.length; i++) {

			// Checks if the field is an instance of the Street class
			if(fieldArr[i] instanceof Street) {

				// Casts the field to street
				Street street = (Street) fieldArr[i];

				// Checks if the field is owned by the Player and there are not too many houses
				if(street.getOwner() == currentPlayer && street.getHouseCounter() <= 5 && street.getHouseCounter() > 0) {

					// Add the field to the temp array
					temp[counter] = fieldArr[i].getName();

					// Adds one to the counter
					counter++;
				}
			}
		}

		// Males a new array with the length of counter
		String[] propertyOwned = new String[counter];

		// Loops over the temp array and adds the filled spots to the propertyOwned array
		for(int j = 0; j < counter; j++) {
			propertyOwned[j] = temp[j];
		}
		return propertyOwned;
	}
	/**
	 * returns a list of pawned fields
	 * @param currentPlayer
	 * @return
	 */
	protected String[] pawnedFields(Player currentPlayer) {
		String[] temp = new String[28];
		int counter = 0;
		// Looping over all fields
		for(int i = 0; i < fieldArr.length; i++) {
			if(fieldArr[i] instanceof Property) { // Check if it's a property
				Property property = (Property) fieldArr[i]; //casting
				if(property.getOwner() == currentPlayer) { // Find the fields the player owns
					if(property.isPawned()) { // check if it's pawned
						if(currentPlayer.getAccount().canAfford((int) (property.getPawnValue()+(property.getPawnValue()*0.10)))) { // check if he can afford to unpawn
							temp[counter] = fieldArr[i].getName();// add it
							counter++; // add it
						}
					}
				}
			}
		}
		String[] propertyOwned = new String[counter];

		for(int j = 0; j < counter; j++) {
			propertyOwned[j] = temp[j];
		}
		return propertyOwned;
	}

	/**
	 * Used to check if the player should have the possibility to trade with someone
	 * @param currentPlayer
	 * @return true if possible else false
	 */
	protected boolean tradePossible(Player currentPlayer) {
		for (int i = 0; i < fieldArr.length; i++) { // loop over all fields
			if(fieldArr[i] instanceof Property) { // Find those who are properties
				Property property = (Property) fieldArr[i]; // Initialize and cast
				if(property.getOwner() != currentPlayer && property.getOwner() != null) { // Make sure it's owned by someone that is not currentPlayer
					return true; // Return true, no need to look any futher
				}
			}
		}
		return false; // Not possible to trade so no need to show option

	}
}


