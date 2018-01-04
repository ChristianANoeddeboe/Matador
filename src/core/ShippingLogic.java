package core;

public class ShippingLogic {
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
				// Vi beregner feltlejen
				int rentPrice = fieldArr[id].getCurrentValue();
				//Spilleren har råd til at betale leje
				if(currentPlayer.getAccount().canAfford(rentPrice)) {
					currentPlayer.getAccount().withdraw(rentPrice);
					fieldArr[id].getOwner().getAccount().deposit(rentPrice);
					return "Rentprice,"+rentPrice;
				}else {
					//Pantsætning
					return "saleLogic";
				}

			}

		}
	}
}
