package core;

import java.awt.Color;

/**
 * 
 * @author Mathias Thejsen s175192 && Simon Hansen s175191
 *
 */
public class SalesLogic {
	private Player currentPlayer;
	
	public SalesLogic(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}
	/**
	 * Logic for selling a house
	 * @param currentPlayer
	 * @return
	 */
	protected boolean sellHouse(Field field) {
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
				GUIController.getInstance().updatePlayerBalance(currentPlayer.getGuiId(), currentPlayer.getAccount().getBalance());
				GUIController.getInstance().setHouse(field.getId(), -1);
				GUIController.getInstance().writeMessage("You have sold a house for: "+street.getBuildPrice());
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
		FieldController fieldcontroller = GUIController.getInstance().getFieldController();
		String[] properties = fieldcontroller.propertiesToPawn(currentPlayer);
		if(properties.length >= 0) {
			return false;
		}
		String response = GUIController.getInstance().requestPlayerChoice("Please choose a property to pawn: ", properties);
		for(int i = 0; i < fieldcontroller.getFieldArr().length; i++) {
			if(response.equals(fieldcontroller.getFieldArr()[i].getName())) {
				Property property = (Property) fieldcontroller.getFieldArr()[i];
				property.setPawned(true); // We set the pawn bool to true
				currentPlayer.getAccount().deposit(property.getPawnValue()); // Weget the pawn value and deposit it into the owners account
				GUIController.getInstance().updatePlayerBalance(currentPlayer.getGuiId(), currentPlayer.getAccount().getBalance());
				GUIController.getInstance().writeMessage("You have pawned "+property.getName()+" for "+property.getPawnValue());
			}
		}
		return true;
	}
}

