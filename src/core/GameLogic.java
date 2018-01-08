package core;
/**
 * 
 * @author Mathias Thejsen s175192 && Simon Hansen s175191
 *
 */
public class GameLogic {
	private Entities entities = Entities.getInstance();
	private Field[] fields = entities.getFieldArr();
	private int dice1value = entities.getDiceArr()[0].getValue();
	private int dice2value = entities.getDiceArr()[1].getValue();
	private int totalFaceValue = dice1value+dice2value;
	private PrisonLogic prisonLogic;

	/**
	 * Constructor for gamelogic
	 */
	public GameLogic() {
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
		if (fields[id] instanceof Normal) { 
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

