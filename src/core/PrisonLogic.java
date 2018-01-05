package core;
/**
 * 
 * @author Mathias Thejsen - Thejsen@live.dk
 *
 */
public class PrisonLogic {
	int id;
	int totalFaceValue;
	Player currentPlayer;
	int dice1value;
	int dice2value;
	
	public PrisonLogic(int id, int totalFaceValue, Player currentPlayer, int dice1value, int dice2value) {
		this.id = id;
		this.totalFaceValue = totalFaceValue;
		this.currentPlayer = currentPlayer;
		this.dice1value = dice1value;
		this.dice2value = dice2value;
	}
	
	public String logic(int id, int totalFaceValue, Player currentPlayer) {
		String returnstr = "";
		if(id == 30 && !currentPlayer.isPrison()){
			//If we landed on prison and we are not prisoned
			if(currentPlayer.getPrisonCard() > 0) {
				//We have a prison card
				returnstr = returnstr + ",PrisonCard";
			}
			if(currentPlayer.getAccount().canAfford(1000)) {
				//We can afford to pay the fine
				returnstr = returnstr + ",CanPayFine";
			}else {
				//the player is prisoned
				currentPlayer.setPrison(true);
				returnstr =  returnstr + ",CanNotPayFine";
			}
			return returnstr;
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

	public void prisonCardLogic(Player currentPlayer) {
		if(currentPlayer.getPrisonCard() > 0) {
			currentPlayer.setPrison(false);
			currentPlayer.setPrisonCard(currentPlayer.getPrisonCard() - 1);
		}
	}

	public void payPrisonLogic(Player currentPlayer) {
		currentPlayer.getAccount().withdraw(1000);
		currentPlayer.setPrisontries(0);
		currentPlayer.setPrison(false);
	}

	public void prisonRollLogic(Player currentPlayer, int dice1value, int dice2value) {
		if(currentPlayer.getPrisontries() < 3) {
			if(dice1value == dice2value) {
				currentPlayer.setPrisontries(0);
				currentPlayer.setPrison(false);
			}
		}
	}


}
