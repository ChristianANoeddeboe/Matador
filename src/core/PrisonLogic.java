package core;

public class PrisonLogic {
	public static String logic(int id, int totalFaceValue, Player currentPlayer) {
		if(id == 30 && !currentPlayer.isPrison()){
			//If we landed on prison and we are not prisoned
			if(currentPlayer.getPrisonCard() > 0) {
				//We have a prison card
				return "PrisonCard";
			}else if(currentPlayer.getAccount().canAfford(1000)) {
				//We can afford to pay the fine
				return "PayFine";
			}else {
				//the player is prisoned
				currentPlayer.setPrison(true);
				return "PlayerPrisoned";
			}
		}else if(id == 30 && currentPlayer.isPrison()) {
			if(currentPlayer.getPrisontries() < 3 ) {
				return "PlayerRoll";
			}else {
				if(currentPlayer.getAccount().canAfford(1000)) {
					return "PayFineMaxTries";
				}else {
					return "CantAfford";
				}
			}
		}
		return "Nothing";
	}

	public static void prisonCardLogic(Player currentPlayer) {
		if(currentPlayer.getPrisonCard() > 0) {
			currentPlayer.setPrison(false);
			currentPlayer.setPrisonCard(currentPlayer.getPrisonCard() - 1);
		}
	}

	public static void payPrisonLogic(Player currentPlayer) {
		currentPlayer.getAccount().withdraw(1000);
		currentPlayer.setPrisontries(0);
		currentPlayer.setPrison(false);
	}

	public static void prisonRollLogic(Player currentPlayer, int dice1value, int dice2value) {
		if(currentPlayer.getPrisontries() < 3) {
			if(dice1value == dice2value) {
				currentPlayer.setPrisontries(0);
				currentPlayer.setPrison(false);
			}
		}
	}


}
