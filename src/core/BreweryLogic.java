package core;

/**
 * @author Simon og Mathias
 *
 */
public class BreweryLogic {
	static Entities entities = Entities.getInstance();
	static Brewery[] fields = (Brewery[]) entities.getFieldArr();
	public static String logic(int id,int totalFaceValue, Player currentPlayer) {
		
		if(fields[id].getOwner() == null) {
			if(currentPlayer.getAccount().canAfford(fields[id].getCurrentValue())) {
				return "NotOwned";
			}
			else {
				return "CannotAfford";
			}
		}else{
			if(fields[id].getOwner() == currentPlayer) {
				//Felt er ejet af spilleren selv
				return "OwnedByPlayer";
			}else {
				int rentPrice = 0;
				//Hvormange bryggerier ejer spilleren?
				if(getOwnedBrewery(id) == 2) {
					rentPrice = 200*totalFaceValue;

				}else {
					rentPrice = 100*totalFaceValue;
				}
				//Spilleren har r�d til at betale leje
				if(currentPlayer.getAccount().canAfford(rentPrice)) {
					currentPlayer.getAccount().withdraw(rentPrice);
					fields[id].getOwner().getAccount().deposit(rentPrice);
					return "Rentprice,"+rentPrice;
				}else {
					return "saleLogic";
				}

			}

		}
	}
	
	public static int getOwnedBrewery(int id) {
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


