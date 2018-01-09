package core;
/**
 * 
 * @author Mathias Thejsen s175192 && Simon Hansen s175191
 *
 */
public class GameLogic {
	private GUIController guiController;
	private Field[] fields;
	private int dice1value;
	private int dice2value;
	private int totalFaceValue = dice1value+dice2value;
	private PrisonLogic prisonLogic;

	/**
	 * Constructor for gamelogic
	 */
	public GameLogic() {
		guiController = guiController.getInstance();
	}

	protected void callLogic(FieldController fieldController, PlayerController playerController, DiceCup diceCup, Player currentPlayer) {
		String choices[] = {"Roll dice"};

		//System.out.println("CAN I BUY HOUSES???: " + buyLogic.canBuyHouse(currentPlayer).toString());

		/*if(buyLogic.canBuyHouse(currentPlayer)) {
			String choices2[] = {"Roll dice","Buy house/hotel"};
			choices = choices2;
		}*/

		switch(guiController.requestPlayerChoice("It is " + currentPlayer.getName() + "'s turn, choose option:", choices)) {
		case "Roll dice" : {
			diceCup.roll();
			//save start position and set new end position
			
			currentPlayer.setStartPosition(currentPlayer.getEndPosition());

			if(( currentPlayer.getEndPosition() + diceCup.getTotalFaceValue() ) > 39) {
				currentPlayer.setEndPosition(currentPlayer.getEndPosition() + diceCup.getTotalFaceValue() - 40);
			}else {
				currentPlayer.setEndPosition(currentPlayer.getEndPosition() + diceCup.getTotalFaceValue());

			}
			guiController.updatePlayerPosition(currentPlayer.getGuiId(), currentPlayer.getStartPosition(), currentPlayer.getEndPosition());
			break;
		}
		case "Buy houses" : {
			//String reponse = buyLogic.houseBuyLogic(currentPlayer);

			break;
		}
		}
	}






	/**
	 * Called by the gamecontroller, this switch checks what kind of field we land on, and then calls a respective logic switch
	 * @param currentPlayer
	 * @return A message to the gamecontroller
	 */
	protected String findLogic(Player currentPlayer) {
		int id = currentPlayer.getEndPosition();
		dice1value = entities.getDiceArr()[0].getValue();
		dice2value = entities.getDiceArr()[1].getValue();
		if (fields[id] instanceof Street) { 
			NormalLogic normalLogic = new NormalLogic(id, currentPlayer);
			return normalLogic.logic(currentPlayer);
		} else if (fields[id] instanceof Brewery) {
			BreweryLogic breweryLogic = new BreweryLogic(id, totalFaceValue, currentPlayer);
			return breweryLogic.logic(currentPlayer);
		} else if (fields[id] instanceof Chance) {
			// Todo
			return "Chance";
		} else if (fields[id] instanceof Shipping) {
			ShippingLogic shippingLogic = new ShippingLogic(id, totalFaceValue, currentPlayer);
			return shippingLogic.logic(currentPlayer);
		} else if (fields[id] instanceof Prison) {
			prisonLogic = new PrisonLogic(id, currentPlayer, dice1value, dice2value);
			return "Prison";
		} else if (fields[id] instanceof Parking) {
			//TODO
			return "Parking";
		} else if (fields[id] instanceof Tax) {
			TaxLogic taxLogic = new TaxLogic(currentPlayer);
			return taxLogic.taxLogic(currentPlayer);
		}
		return "Type not found";
	}

	/**
	 * Updates balance if we passed start, called everytime we roll dices
	 * @param currentPlayer
	 * @return
	 */
	protected boolean passedStart(Player currentPlayer) {
		if(!currentPlayer.isStartRound()) {
			System.out.println(currentPlayer.getName() + currentPlayer.getStartPosition() + currentPlayer.getEndPosition());
			if(((entities.getDiceArr()[0].getValue() + entities.getDiceArr()[1].getValue() + currentPlayer.getStartPosition()) > 40) || currentPlayer.getStartPosition() == 0) {
				currentPlayer.getAccount().deposit(4000);
				return true;
			}
		}else {
			currentPlayer.setStartRound(false);
			return false;
		}
		return false;
	}

	public PrisonLogic getPrisonLogic() {
		return prisonLogic;
	}
}

