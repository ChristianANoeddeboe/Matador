package core;

public class ShippingLogic {
	public static String logic(int id,int totalFaceValue, Player currentPlayer) {
		Entities entities = Entities.getInstance();
		Shipping[] fields = (Shipping[]) entities.getFieldArr();
		if(fields[id].getOwner() == null) {
			if(currentPlayer.getAccount().canAfford(fields[id].currentValue)) {
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
				// Vi beregner feltlejen
				int rentPrice = fields[id].getCurrentValue();
				//Spilleren har r�d til at betale leje
				if(currentPlayer.getAccount().canAfford(rentPrice)) {
					currentPlayer.getAccount().withdraw(rentPrice);
					fields[id].getOwner().getAccount().deposit(rentPrice);
					return "Rentprice,"+rentPrice;
				}else {
					//Pants�tning
					return "saleLogic";
				}

			}

		}
	}
}
