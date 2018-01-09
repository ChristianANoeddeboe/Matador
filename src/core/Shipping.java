package core;
/**
 * 
 * @author Mathias Thejsen s175192 && Simon Hansen s175191
 *
 */
public class Shipping extends Property {
	int currentValue;
	/**
	 * Constructor for shipping field
	 * @param id The id of the field
	 * @param name The name of the field
	 * @param owner The owner of the field
	 * @param baseValue Base value(board value)
	 * @param pawnValue Pawn value
	 * @param description Description of the field
	 */
	public Shipping(int id, String name, String description, Player owner, int buyValue, int pawnValue, int rentValue) {
		super(id, name, description, owner, buyValue, pawnValue, rentValue);
		this.currentValue = 500;
	}
	
	public int getCurrentValue() {return currentValue;}
	public void setCurrentValue(int value) { this.currentValue = value; }
}

