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
	protected void sellHouse(Field field) {
		Street street = (Street) field;
		currentPlayer.getAccount().deposit(street.getHousePrices()[1]); // We get the current player and deposit the house price back into the players account
		street.setHouseCounter(street.getHouseCounter()-1);
		GUIController.getInstance().writeMessage("You have sold a house for: "+street.getBuildPrice());
	}

	/**
	 * Logic for pawning a property
	 * @param currentPlayer
	 * @return
	 */
	protected void pawnProperty(Field field) {
		Property property = (Property) field;
		property.setPawned(true); // We set the pawn bool to true
		currentPlayer.getAccount().deposit(property.getPawnValue()); // Weget the pawn value and deposit it into the owners account
		GUIController.getInstance().writeMessage("You have pawned "+property.getName()+" for "+property.getPawnValue());
	}
}

