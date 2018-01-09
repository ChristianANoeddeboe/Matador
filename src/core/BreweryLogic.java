package core;

/**
 * @author Mathias Thejsen s175192 && Simon Hansen s175191
 *
 */
public class BreweryLogic {
	private int id, totalFaceValue;
	private Brewery brewery;
	private Player currentPlayer;
	public BreweryLogic(Player currentPlayer, int totalFaceValue, Field[] fields) {
		this.currentPlayer = currentPlayer;
		this.brewery = (Brewery) fields[currentPlayer.getEndPosition()];
		this.totalFaceValue = totalFaceValue;
	}
	
	/**
	 * Logic method for brewery
	 * @param id The current field -1
	 * @param totalFaceValue The total face value of the dices, as landing on the field is dependent on it
	 * @param currentPlayer Current player
	 * @return Dependent on outcome but a string
	 */
	protected String logic() {
		if(brewery.getOwner() == null) { // Field is not owned
			if(currentPlayer.getAccount().canAfford(brewery.getBuyValue())) {
				return "NotOwned";
			}
			else {
				return "CannotAfford"; //Player can't afford field
			}
		}else{
			if(brewery.getOwner() == currentPlayer) { // Field is owned by the same player
				return "OwnedByPlayer";
			}else {
				int rentPrice = 0;
				//We calculate how many brewery the player owns and then calculate an according rentprice
				if(getOwnedBrewery() == 2) {
					rentPrice = 200*totalFaceValue;
				}else {
					rentPrice = 100*totalFaceValue;
				}
				//We check if the landing player can afford the rent
				if(currentPlayer.getAccount().canAfford(rentPrice)) {
					currentPlayer.getAccount().withdraw(rentPrice);
					brewery.getOwner().getAccount().deposit(rentPrice);
					return "CanAfford";
				}else { //The player can't afford and has to sell something
					return "saleLogic";
				}

			}

		}
	}
	/**
	 * A method to check how many brewery a player owns
	 * @param id The current brewery
	 * @return 1 or 2 depending on how many brewery are owned by the same player
	 */
	private int getOwnedBrewery() {
		if(id == 12) {
			if(brewery.getOwner() == brewery.getOwner()) {
				return 2;
			}else {
				return 1;
			}
		}else if(id == 28) {
			if(brewery.getOwner() == brewery.getOwner()) {
				return 2;
			}else {
				return 1;
			}
		}
		return 1;
	}
}


