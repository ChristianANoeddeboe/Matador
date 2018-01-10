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
	public SalesController(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}
	
	protected boolean cannotAfford(int value) {
		boolean housesToSell = true;
		boolean propertyToPawn = true;
		while(currentPlayer.getAccount().getBalance() < value) {
			String[] options = {"Sell House", "Pawn Property"};
			String result = GUIController.getInstance().requestPlayerChoice("Sell either houses or pawn property", options);
			if(result.equals("Sell House")) {
				housesToSell = sellHouse();
			}
			if(result.equals("Pawn Property")) {
				propertyToPawn = pawnProperty();
			}
			if(housesToSell == false && propertyToPawn == false && currentPlayer.getAccount().getBalance() < value) {
				currentPlayer.setBanktrupt(true);
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Logic for selling a house
	 * @param currentPlayer
	 * @return
	 */
	protected boolean sellHouse() {
		FieldController fieldcontroller = GUIController.getInstance().getFieldController();
		
		// Method returns a String array with all fields that the current player owns that haves houses built on them
		String[] streets = fieldcontroller.streetsWithHouses(currentPlayer);
		
		// Returns false if there is no fields with houses
		if(streets.length >= 0) {
			return false;
		}
		
		// Prompts the user for a choice
		String response = GUIController.getInstance().requestPlayerChoice("Please choose a street to sell your houses from: ", streets);
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
	 * @param currentPlayer
	 * @return
	 */
	protected boolean pawnProperty() {
		FieldController fieldcontroller = guiController.getFieldController();
		
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
				
				// Send all updates to GUIController
				currentPlayer.getAccount().deposit(property.getPawnValue()); // We get the pawn value and deposit it into the owners account
				guiController.updatePlayerBalance(currentPlayer.getGuiId(), currentPlayer.getAccount().getBalance());
				guiController.writeMessage("You have pawned "+property.getName()+" for "+property.getPawnValue());
			}
		}
		return true;
	}
}
