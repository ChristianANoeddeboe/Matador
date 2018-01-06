package core;
/**
 * 
 * @author Simon Fritz
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
		currentPlayer.getAccount().deposit(fields[id].getHousePrices()[1]); // We get the current player and deposit the house price bnack into the players acount
		return "SoldHouse, "+ fields[id].getHousePrices()[1];
	}
	/**
	 * Logic for pawning a property
	 * @param currentPlayer
	 * @return
	 */
	protected String pawnProperty(Player currentPlayer) { 
		Property[] fields = (Property[]) entities.getFieldArr();
		fields[id].setIsPawned(true); // We set the pawn bool to true
		currentPlayer.getAccount().deposit(fields[id].getPawnValue()); // Weget the pawn value and deposit it into the owners account
		return "Pawned, " + fields[id].getPawnValue();
	}
}
