/**
 * 
 */
package core;

/**
 * @author Simon og Mathias
 *
 */
public class NormalLogic {
	
	public NormalLogic(int id, Player currentPlayer) {
		logic(id, currentPlayer);
	}
	
	static Entities entities = Entities.getInstance();
	static Normal[] fields = (Normal[]) entities.getFieldArr();
	public String logic(int id, Player currentPlayer) {
		if(fields[id].getOwner() == null) {
			//Felt er ikke ejet
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
				//Felt er ejet af en anden
				if(currentPlayer.getAccount().canAfford(fields[id].getCurrentValue())) {
					//Spilleren har r�d til at betale leje
					currentPlayer.getAccount().withdraw(fields[id].getCurrentValue());
					fields[id].getOwner().getAccount().deposit(fields[id].getCurrentValue());
					return "CanAfford";
				}else {
					//Pants�tning af grundene/huse
					return "SaleLogic";
				}

			}
		}

	}
}


