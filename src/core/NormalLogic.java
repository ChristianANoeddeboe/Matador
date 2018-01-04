/**
 * 
 */
package core;

/**
 * @author Simon og Mathias
 *
 */
public class NormalLogic {
	public static void logic(int id, Player currentPlayer) {
		if(FieldArr[id].getOwner == null) {
			//Felt er ikke ejet
		}else{
			if(FieldArr[id].getOwner == currentPlayer) {
				//Felt er ejet af spilleren selve
			}else {
				//Felt er ejet af en anden
				if(currentPlayer.getAccount().canAfford(FieldArr[id].currentValue)) {
					//Spilleren har råd til at betale leje
				}else {
					//Pantsætning af grundene/huse
				}

			}
		}

	}
}


