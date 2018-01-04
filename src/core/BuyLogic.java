package core;

public class BuyLogic {

	public static String propertyBuyLogic(int id, Player currentPlayer) {
		currentPlayer.getAccount().getBalance().withdraw(FieldArr[id].baseValue);
		FieldArr[id].setOwner(currentPlayer);
		return "Bought";
	}
	
	public static String houseBuyLogic(int id, Player currentPlayer) {
		if(currentPlayer.getAccount().canAfford(FieldArr[id].getBuildPrice)) {
			currentPlayer.getAccount().getBalance().withdraw(FieldArr[id].getBuildPrice);
			FieldArr[id].houseNumber++;
			FieldArr[id].setCurrentPrice(calcHousePrice(id, FieldArr[id].houseNumber));
			return "HouseBought";
		}
		else {
			return "CannotAfford, "+FieldArr[id].getBuildPrice();
		}
	}
	
	private static int calcHousePrice(int id, int houses) {
		switch (houses) {
		case 1:
			return FieldArr[id].getHouse1Price();
		case 2:
			return FieldArr[id].getHouse2Price();
		case 3:
			return FieldArr[id].getHouse3Price();
		case 4:
			return FieldArr[id].getHouse4Price();
		case 5:
			return FieldArr[id].getHotelPrice();
		default:
			break;
		}
	}
	
}
