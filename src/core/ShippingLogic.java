package core;

public class ShippingLogic {
	public static void logic(int id,int totalFaceValue, Player currentPlayer) {
		if(FieldArr[id].getOwner == null) {
			//Felt er ikke ejet
		}else{
			if(FieldArr[id].getOwner == currentPlayer) {
				//Felt er ejet af spilleren selve
			}else {
				// Vi beregner feltlejen
				int rentPrice = 0;
				switch (getOwnedShipping(id)) {
				case 1:
					rentPrice = 500;
					break;
				case 2:
					rentPrice = 1000;
					break;
				case 3:
					rentPrice = 2000;
					break;
				case 4:
					rentPrice = 4000;
				default:
					break;
				}
				//Spilleren har råd til at betale leje
				if(currentPlayer.getAccount().canAfford(rentPrice)) {
					currentPlayer.getAccount().withdraw(rentPrice);
					fieldArr[id].getOwner().getAccount().deposit(rentPrice);
				}else {
					//Pantsætning
				}

			}

		}
	}
	
	public static int getOwnedShipping(int id) {
		Player idOwner = fieldArr[id].getOwner();
		int amount = 1;
		for (int i = 0; i < fieldArr[].length; i++) {
			if(fieldArr[i].getType() == "Shipping" && fieldArr[i].getOwner() == idOwner ) {
				amount++;
			}
		}
		return amount;
	}
}
