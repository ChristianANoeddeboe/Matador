package core;

/**
 * This class handles the trading of properties within the game
 * @author Magnus Stjernborg Koch s175189 & Nicolai Kammersgård s143780
 *
 */
public class TradeController {
	private GUIController guiController = GUIController.getInstance();
	/**
	 * This method initialises the trade
	 * @param currentPlayer 
	 * @param fieldController
	 * @param players
	 */
	public void startTrade (Player currentPlayer, FieldController fieldController, Player[] players) {
		String[] choices = new String[players.length-1];
		boolean playerempty = true;
		int counter = 0;
		for (int i = 0; i < players.length; i++) { // Looping over all the players
			if (!(players[i] == currentPlayer) && fieldController.FieldsOwned(players[i]).length > 0 ) { // We don't want the player to be able to trade to himself and we want to make sure the players actually owns some fields  
				choices[counter++] = players[i].getName(); // Add the players to a list
				playerempty = false; // Boolean to keep track if we actually have any players that can trade something
			}
		}
		String[] actualchoices = new String[counter+1]; // make a new array
		for (int i = 0; i < (actualchoices.length - 1 ); i++) { // reserve last space in array for return button
			actualchoices[i] = choices[i];
		}
		actualchoices[actualchoices.length - 1] = PropertiesIO.getTranslation("returnbutton"); //add return button
		
		if(!playerempty) { // if we dont have any players that can trade, then don't start the trading stuff
			String response = guiController.requestPlayerChoiceButtons(PropertiesIO.getTranslation("tradewhototradewith"),actualchoices); // Get the player that the currentplayer wants to trade with
			if (!(response ==PropertiesIO.getTranslation("returnbutton"))) { // Make sure it is not a return button
				for (int i = 0; i < players.length; i++) { // Loop over all players 
					if (players[i].getName().equals(response)) { // find the player the currentPlayer choose to trade with
						String[] fieldsOwned = fieldController.FieldsOwned(players[i]); // Grab the fields that the player owns
						if (fieldsOwned.length > 0) { // Make sure he owns some fields 
							choices = new String[fieldsOwned.length + 1]; // Add space for the return button
							for(int k = 0 ; k < choices.length-1 ; k++) { 
								choices[k] = fieldsOwned[k];
							}
							choices[choices.length-1] = PropertiesIO.getTranslation("returnbutton"); // Assign return button
							response = guiController.requestPlayerChoiceButtons(PropertiesIO.getTranslation("tradewhattotradewith"), choices); //Pick a field
						}
					}
				}
	
				Field[] fields = fieldController.getFieldArr(); // Get the global field arr NOT the players
	
				for (int i = 0; i < fields.length; i++) { // Loop over all fields
					if (fields[i].getName().equals(response)) { // find the field the player chose
						Property property = (Property) fields[i]; // Grab the property
						int amountOffered = 0;
						boolean cannotAfford = true;
						do {
							amountOffered = guiController.requestIntegerInput(PropertiesIO.getTranslation("tradehowmuchoffer"),0,currentPlayer.getAccount().getBalance()); // Get an offer  
							if(currentPlayer.getAccount().canAfford(amountOffered)) { // Check he can afford
								cannotAfford = false; // He can afford so set bool to false
							}	
						} while(cannotAfford);
						// Ask the player if he wishses to acccept the offer
						response = guiController.requestPlayerChoiceButtons(property.getOwner().getName()+" "+PropertiesIO.getTranslation("tradeacceptoffer")+" "+amountOffered+" for "+property.getName()+"?",PropertiesIO.getTranslation("yesbutton"),PropertiesIO.getTranslation("nobutton"));
						if (response.equals(PropertiesIO.getTranslation("yesbutton"))) { // If player accepts offer withdraw and transfer ownership
							currentPlayer.getAccount().withdraw(amountOffered);
							property.getOwner().getAccount().deposit(amountOffered);
							guiController.updatePlayerBalance(currentPlayer.getGuiId(), currentPlayer.getAccount().getBalance());
							guiController.updatePlayerBalance(property.getOwner().getGuiId(), property.getOwner().getAccount().getBalance());
							property.setOwner(currentPlayer);
							guiController.setOwner(currentPlayer.getGuiId(), property.getId());
						}
					}
				}
			}
		}
	}
}
