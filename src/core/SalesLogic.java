package core;

public class SalesLogic {
	
	public static String sellHouse(int id, Player currentPlayer) {
		Entities entities = Entities.getInstance();
		Normal[] fields = (Normal[]) entities.getFieldArr();
		
		currentPlayer.getAccount().deposit(fields[id].getHousePrice());
		return "SoldHouse, "+ fields[id].getHousePrice();
	}
	
	public static String pawnProperty(int id, Player currentPlayer) {
		Entities entities = Entities.getInstance();
		Property[] fields = (Property[]) entities.getFieldArr();
		
		fields[id].setIsPawned(true);
		currentPlayer.getAccount().deposit(fields[id].getPawnValue());
		return "Pawned, " + fields[id].getPawnValue();
	}
}
