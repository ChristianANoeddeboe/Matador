/**
 * 
 */
package core;

/**
 * @author Simon og Mathias
 *
 */
public class NormalLogic {
	public static String logic(int id, Player currentPlayer) {
		if(FieldArr[id].getOwner == null) {
			//Felt er ikke ejet
			return "NotOwned";
		}else{
			if(FieldArr[id].getOwner == currentPlayer) {
				//Felt er ejet af spilleren selv
				return "OwnedByPlayer";
			}else {
				//Felt er ejet af en anden
				if(currentPlayer.getAccount().canAfford(FieldArr[id].currentValue)) {
					//Spilleren har råd til at betale leje
					currentPlayer.getAccount().withdraw(FieldArr[id].currentValue);
					FieldArr[id].getOwner().getAccount().deposit(FieldArr[id].currentValue);
					return "CanAfford";
				}else {
					//Pantsætning af grundene/huse
					return "SaleLogic";
				}

			}
		}

	}
}


