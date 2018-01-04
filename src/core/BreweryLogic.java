package core;

/**
 * @author Simon og Mathias
 *
 */
public class BreweryLogic {
	
	public static String logic(int id,int totalFaceValue, Player currentPlayer) {
		if(FieldArr[id].getOwner == null) {
			if(currentPlayer.getAccount().canAfford(FieldArr[id].currentValue)) {
				return "NotOwned";
			}
			else {
				return "CannotAfford";
			}
		}else{
			if(FieldArr[id].getOwner == currentPlayer) {
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
				//Spilleren har råd til at betale leje
				if(currentPlayer.getAccount().canAfford(rentPrice)) {
					currentPlayer.getAccount().withdraw(rentPrice);
					fieldArr[id].getOwner().getAccount().deposit(rentPrice);
					return "Rentprice,"+rentPrice;
				}else {
					return "saleLogic"
				}

			}

		}
	}
	
	public static int getOwnedBrewery(int id) {
		if(id == 12) {
			if(fieldArr[28].getOwner == fieldArr[12].getOwner) {
				return 2;
			}else {
				return 1;
			}
		}else if(id == 28) {
			if(fieldArr[12].getOwner == fieldArr[28].getOwner) {
				return 2;
			}else {
				return 1;
			}
		}
	}
}


