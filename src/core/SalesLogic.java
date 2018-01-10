package core;

import java.awt.Color;

import gui_fields.GUI_Jail;

/**
 * 
 * @author Mathias Thejsen s175192 && Simon Hansen s175191
 *
 */
public class SalesLogic {
	private Player currentPlayer;
	private GUIController guiController = GUIController.getInstance();
	public SalesLogic(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}
	
	protected boolean cannotAfford(int value) {
		while(currentPlayer.getAccount().getBalance() < value) {
			String[] options = {"Sell House", "Pawn Property"};
			String result = GUIController.getInstance().requestPlayerChoice("Sell either houses or pawn property", options);
			if(result.equals("Sell House")) {
				sellHouse();
			}
			else {
				pawnProperty();
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
		String[] streets = fieldcontroller.streetsWithHouses(currentPlayer);
		if(streets.length >= 0) {
			return false;
		}
		String response = GUIController.getInstance().requestPlayerChoice("Please choose a street to sell your houses from: ", streets);
		for(int i = 0; i < fieldcontroller.getFieldArr().length; i++) {
			if(response.equals(fieldcontroller.getFieldArr()[i].getName())) {
				Street street = (Street) fieldcontroller.getFieldArr()[i];
				currentPlayer.getAccount().deposit(street.getBuildPrice()); // We get the current player and deposit the house price back into the players account
				street.setHouseCounter(street.getHouseCounter()-1);
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
		String[] properties = fieldcontroller.propertiesToPawn(currentPlayer);
		if(properties.length >= 0) {
			return false;
		}
		String response = guiController.requestPlayerChoice("Please choose a property to pawn: ", properties);
		for(int i = 0; i < fieldcontroller.getFieldArr().length; i++) {
			if(response.equals(fieldcontroller.getFieldArr()[i].getName())) {
				Property property = (Property) fieldcontroller.getFieldArr()[i];
				property.setPawned(true); // We set the pawn bool to true
				currentPlayer.getAccount().deposit(property.getPawnValue()); // Weget the pawn value and deposit it into the owners account
				guiController.updatePlayerBalance(currentPlayer.getGuiId(), currentPlayer.getAccount().getBalance());
				guiController.writeMessage("You have pawned "+property.getName()+" for "+property.getPawnValue());
			}
		}
		return true;
	}
}

