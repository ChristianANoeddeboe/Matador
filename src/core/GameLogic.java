package core;
/**
 * 
 * @author Mathias Thejsen - Thejsen@live.dk && Simon Fritz
 *
 */
public class GameLogic {
	private Entities entities = Entities.getInstance();
	private Field[] fields = entities.getFieldArr();
	private int dice1value = entities.getDiceArr()[0].getValue();
	private int dice2value = entities.getDiceArr()[1].getValue();
	private int totalFaceValue = dice1value+dice2value;

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
			PrisonLogic prisonLogic = new PrisonLogic(id, currentPlayer, dice1value, dice1value);
			prisonLogic.logic(currentPlayer);
			return "Prison";
		} else if (fields[id] instanceof Parking) {
			//TODO
			return "Parking";
		} else if (fields[id] instanceof Tax) {
			TaxLogic taxLogic = new TaxLogic(id, currentPlayer);
			//TODO
			return "Tax";
		}
		return "Type not found";
	}
}

