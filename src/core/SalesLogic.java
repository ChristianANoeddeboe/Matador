package core;
/**
 * 
 * @author Simon Fritz
 *
 */
public class SalesLogic {
	int id;
	Player currentPlayer;
	
	public SalesLogic(int id, Player currentPlayer) {
		this.id = id;
		this.currentPlayer = currentPlayer;
	}
	
	public String sellHouse(int id, Player currentPlayer) {
		Entities entities = Entities.getInstance();
		Normal[] fields = (Normal[]) entities.getFieldArr();
		
		currentPlayer.getAccount().deposit(fields[id].getHousePrices()[1]);
		return "SoldHouse, "+ fields[id].getHousePrices()[1];
	}
	
	public String pawnProperty(int id, Player currentPlayer) {
		Entities entities = Entities.getInstance();
		Property[] fields = (Property[]) entities.getFieldArr();
		
		fields[id].setIsPawned(true);
		currentPlayer.getAccount().deposit(fields[id].getPawnValue());
		return "Pawned, " + fields[id].getPawnValue();
	}
}
