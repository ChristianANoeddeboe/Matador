package core;

import java.awt.Color;

/**
 * 
 * @author Mathias Thejsen s175192 && Simon Hansen s175191
 *
 */
public class SalesLogic {
	private int id;
	private Player currentPlayer;
	private Entities entities = Entities.getInstance();
	
	public SalesLogic(int id, Player currentPlayer) {
		this.id = id;
		this.currentPlayer = currentPlayer;
	}
	/**
	 * Logic for selling a house
	 * @param currentPlayer
	 * @return
	 */
	protected String sellHouse(Player currentPlayer) {
		Normal[] fields = (Normal[]) entities.getFieldArr();
		currentPlayer.getAccount().deposit(fields[id].getHousePrices()[1]); // We get the current player and deposit the house price back into the players account
		return "SoldHouse, "+ fields[id].getHousePrices()[1];
	}
	/**
	 * Logic for pawning a property
	 * @param currentPlayer
	 * @return
	 */
	protected String pawnProperty(Player currentPlayer) {
		Property[] fields = (Property[]) entities.getFieldArr();
		if(fields[id] instanceof Normal) {
			Normal[] nfields = (Normal[]) entities.getFieldArr();
			if(nfields[id].getHouseCounter() == 0 && checkHouses(currentPlayer) && nfields[id].getHouseCounter() == 0) {
				fields[id].setIsPawned(true); // We set the pawn bool to true
				currentPlayer.getAccount().deposit(fields[id].getPawnValue()); // Weget the pawn value and deposit it into the owners account
				return "Pawned, " + fields[id].getPawnValue();
			}
			else {
				return "RemoveHouses";
			}
		}
		fields[id].setIsPawned(true); // We set the pawn bool to true
		currentPlayer.getAccount().deposit(fields[id].getPawnValue()); // Weget the pawn value and deposit it into the owners account
		return "Pawned, " + fields[id].getPawnValue();
	}
	
	protected boolean checkHouses(Player currentPlayer) {
		Normal[] fields = (Normal[]) entities.getFieldArr();
		Color propertyColor = fields[id].getColour();
		
		switch (propertyColor.toString()) {
		case "Color.blue":
			Normal[] blueFields = (Normal[]) entities.getNormalBlue();
			for(int i = 0; i < blueFields.length; i++) {
				if(blueFields[i].getOwner() == fields[id].getOwner()) {
					if(blueFields[i].getHouseCounter() > fields[id].getHouseCounter() + 1) {
						return false;
					}
					else {
						return true;
					}
				}
				else {
					return true;
				}
			}
		case "Color.pink":
			Normal[] pinkFields = (Normal[]) entities.getNormalBlue();
			for(int i = 0; i < pinkFields.length; i++) {
				if(pinkFields[i].getOwner() == fields[id].getOwner()) {
					if(pinkFields[i].getHouseCounter() > fields[id].getHouseCounter() + 1) {
						return false;
					}
					else {
						return true;
					}
				}
				else {
					return true;
				}
			}
		default:
			break;
		}
		
		return true;
	}
}
