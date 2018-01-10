package core;

import java.awt.Color;

import gui_fields.GUI_Jail;

/**
 * 
 * @author Mathias Thejsen s175192 && Simon Hansen s175191
 *
 */
public class SalesController {
	private Player currentPlayer;
	private GUIController guiController = GUIController.getInstance();
	private FieldController fieldcontroller = guiController.getFieldController();
	public SalesController(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}
	
	/**
	 * Logic when player cannot afford something
	 * @param value
	 * @return
	 */
	protected boolean cannotAfford(int value) {
		boolean housesToSell = true;
		boolean propertyToPawn = true;
		
		// While loop until the player can afford the rent/pay
		while(currentPlayer.getAccount().getBalance() < value) {
			
			// Prompts the user for a choiec
			String[] options = {"Sell House", "Pawn Property"};
			String result = guiController.requestPlayerChoice("Sell either houses or pawn property", options);
			
			// Runs the sellHouse method if chosen
			if(result.equals("Sell House")) {
				housesToSell = sellHouse();
			}
			
			// Runs the pawnProperty method if chosen
			if(result.equals("Pawn Property")) {
				propertyToPawn = pawnProperty();
			}
			
			// If the player has no houses to sell, properties to pawn and still cannot afford, the player is declared bankrupt and false is returned
			if(!housesToSell && !propertyToPawn && currentPlayer.getAccount().getBalance() < value) {
				currentPlayer.setBanktrupt(true);
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Logic for selling a house
	 * @return
	 */
	protected boolean sellHouse() {

		// Method returns a String array with all fields that the current player owns that haves houses built on them
		String[] streets = fieldcontroller.streetsWithHouses(currentPlayer);
		
		// Returns false if there is no fields with houses
		if(streets.length >= 0) {
			return false;
		}
		
		// Prompts the user for a choice
		String response = guiController.requestPlayerChoice("Please choose a street to sell your houses from: ", streets);
		for(int i = 0; i < fieldcontroller.getFieldArr().length; i++) {
			
			// Finds the field that the player have chosen
			if(response.equals(fieldcontroller.getFieldArr()[i].getName())) {
				
				// hold the field as a street class
				Street street = (Street) fieldcontroller.getFieldArr()[i];
				
				// We get the current player and deposit the house price back into the players account
				currentPlayer.getAccount().deposit(street.getBuildPrice());
				
				// Removes a house from the Street field
				street.setHouseCounter(street.getHouseCounter()-1);
				
				// Send all the updates to GUIController
				guiController.updatePlayerBalance(currentPlayer.getGuiId(), currentPlayer.getAccount().getBalance());
				guiController.setHouse(street.getId(), -1);
				guiController.writeMessage("You have sold a house for: "+street.getBuildPrice());
			}
		}
		return true;

	}

	/**
	 * Logic for pawning a property
	 * @return
	 */
	protected boolean pawnProperty() {
		
		// Holds a String array of all the properties owned by the current Player
		String[] properties = fieldcontroller.propertiesToPawn(currentPlayer);
		
		// Returns false if the player owns no properties to pawn
		if(properties.length >= 0) {
			return false;
		}
		
		// Prompts the user for a choice
		String response = guiController.requestPlayerChoice("Please choose a property to pawn: ", properties);
		for(int i = 0; i < fieldcontroller.getFieldArr().length; i++) {
			
			// Finds the field that the player have chosen
			if(response.equals(fieldcontroller.getFieldArr()[i].getName())) {
				
				// Hold the field as a property class
				Property property = (Property) fieldcontroller.getFieldArr()[i];
				
				// We set the pawn bool to true
				property.setPawned(true);
				
				// We get the pawn value and deposit it into the owners account
				currentPlayer.getAccount().deposit(property.getPawnValue()); 
				
				// Send all updates to GUIController
				guiController.updatePlayerBalance(currentPlayer.getGuiId(), currentPlayer.getAccount().getBalance());
				guiController.writeMessage("You have pawned "+property.getName()+" for "+property.getPawnValue());
			}
		}
		return true;
	}
}

