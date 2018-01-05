package core;
/**
 * 
 * @author Mathias Thejsen - Thejsen@live.dk && simon fritz
 *
 */
public class Shipping extends Property {
	/**
	 * Constructor for shipping field
	 * @param id The id of the field
	 * @param name The name of the field
	 * @param owner The owner of the field
	 * @param baseValue Base value(board value)
	 * @param pawnValue Pawn value
	 * @param description Description of the field
	 */
	Shipping(int id, String name, Player owner, int baseValue, int pawnValue, String description) {
		super(id, name, owner, baseValue, pawnValue, description);
	}
}
