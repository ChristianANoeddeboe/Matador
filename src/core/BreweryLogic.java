package core;

/**
 * @author Simon og Mathias
 *
 */
public class BreweryLogic {
	int id;
	int totalFaceValue;
	Player currentPlayer;
	
	public BreweryLogic(int id, int totalFaceValue, Player currentPlayer) {
		this.id = id;
		this.totalFaceValue = totalFaceValue;
		this.currentPlayer = currentPlayer;
	}
	
	Entities entities = Entities.getInstance();
	Brewery[] fields = (Brewery[]) entities.getFieldArr();
	/**
	 * Logic method for brewery
	 * @param id The current field -1
	 * @param totalFaceValue The total face value of the dices, as landing on the field is dependent on it
	 * @param currentPlayer Current player
	 * @return Dependent on outcome but a string
	 */
	public String logic(int id,int totalFaceValue, Player currentPlayer) {
		if(fields[id].getOwner() == null) { // Field is not owned
			if(currentPlayer.getAccount().canAfford(fields[id].getCurrentValue())) {
				return "NotOwned";
			}
			else {
				return "CannotAfford"; //Player can't afford field
			}
		}else{
			if(fields[id].getOwner() == currentPlayer) { // Field is owned by the same player
				return "OwnedByPlayer";
			}else {
				int rentPrice = 0;
				//We calculate how many brewery the player owns and then calculate an according rentprice
				if(getOwnedBrewery(id) == 2) {
					rentPrice = 200*totalFaceValue;
				}else {
					rentPrice = 100*totalFaceValue;
				}
				//We check if the landing player can afford the rent
				if(currentPlayer.getAccount().canAfford(rentPrice)) {
					currentPlayer.getAccount().withdraw(rentPrice);
					fields[id].getOwner().getAccount().deposit(rentPrice);
					return "Rentprice,"+rentPrice;
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
	public int getOwnedBrewery(int id) {
		if(id == 12) {
			if(fields[28].getOwner() == fields[12].getOwner()) {
				return 2;
			}else {
				return 1;
			}
		}else if(id == 28) {
			if(fields[12].getOwner() == fields[28].getOwner()) {
				return 2;
			}else {
				return 1;
			}
		}
		return 1;
	}
}


