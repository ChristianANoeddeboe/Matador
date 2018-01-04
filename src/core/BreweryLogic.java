package core;

/**
 * @author Simon og Mathias
 *
 */
public class BreweryLogic {
	
	public static void logic(int id,int totalFaceValue, Player currentPlayer) {
		if(FieldArr[id].getOwner == null) {
			//Felt er ikke ejet
		}else{
			if(FieldArr[id].getOwner == currentPlayer) {
				//Felt er ejet af spilleren selve
			}else {
				int rentPrice = 0;
				//Hvormange bryggerier ejer spilleren?
				if(getOwnerBrewery(id) == 2) {
					rentPrice = 200*totalFaceValue;

				}else {
					rentPrice = 100*totalFaceValue;
				}
				//Spilleren har r�d til at betale leje
				if(currentPlayer.getAccount().canAfford(rentPrice)) {
					currentPlayer.getAccount().withdraw(rentPrice);
					fieldArr[id].getOwner().getAccount().deposit(rentPrice);
				}else {
					//Pants�tning
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


