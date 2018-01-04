package core;

public class TaxLogic {
	public static String taxLogic38(Player currentPlayer) {
		if(currentPlayer.getAccount().canAfford(2000)) {
			currentPlayer.getAccount().withdraw(2000);
			return "TaxPrice, "+2000;
		}else {
			//Pants�tning
			return "saleLogic";
		}
	}
	
	public static String taxLogic4(Player currentPlayer, int choice) {
		if(choice == 0) {
			
		}
		
	}
	
}
