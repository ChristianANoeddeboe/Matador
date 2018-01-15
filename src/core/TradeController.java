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
		String[] actualchoices = new String[counter+1]; // make a new array and fill out
		for (int i = 0; i < actualchoices.length; i++) {
			actualchoices[i] = choices[i];
		}
		actualchoices[counter+1] = PropertiesIO.getTranslation("returnbutton");

		if(!playerempty) { // if we dont have any players that can trade, then don't start the trading stuff
			String response = guiController.requestPlayerChoiceButtons(PropertiesIO.getTranslation("tradewhototradewith"),actualchoices);
			if (!(response ==PropertiesIO.getTranslation("returnbutton"))) {
				for (int i = 0; i < players.length; i++) { // Loop over all players
					if (players[i].getName().equals(response)) { // find the player the currentPlayer choose to trade with
						if (fieldController.FieldsOwned(players[i]).length > 0) { // Make sure he owns some fields
							response = guiController.requestPlayerChoiceButtons(PropertiesIO.getTranslation("tradewhototradewith"), fieldController.FieldsOwned(players[i])); //Pick a field
						}
					}
				}

				Field[] fields = fieldController.getFieldArr();

				for (int i = 0; i < fields.length; i++) { // Loop over all fields
					if (fields[i].getName().equals(response)) { // find the field the player chose
						Property property = (Property) fields[i];
						int amountOffered = 0;
						boolean cannotAfford = true;
						do {
							amountOffered = guiController.requestIntegerInput(PropertiesIO.getTranslation("tradehowmuchoffer"));
							if (currentPlayer.getAccount().canAfford(amountOffered)) {
								cannotAfford = false;
							}
						} while (cannotAfford);
						// Ask the player if he wishses to acccept the offer
						response = guiController.requestPlayerChoiceButtons(property.getOwner().getName() + PropertiesIO.getTranslation("tradeacceptoffer") + " " + amountOffered + " for " + property.getName() + "?", PropertiesIO.getTranslation("yesbutton"), PropertiesIO.getTranslation("nobutton"));
						if (response.equals(PropertiesIO.getTranslation("yesbutton"))) {
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
