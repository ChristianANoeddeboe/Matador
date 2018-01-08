package core;
/**
 * 
 * @author Mathias Thejsen s175192 && Simon Hansen s175191
 *
 */
public abstract class Property extends Field {
	private Player owner;
	private int baseValue;
	private int currentValue;
	private int pawnValue;
	private boolean isPawned;
	/**
	 * Constructor for the property field
	 * @param id
	 * @param name
	 * @param owner
	 * @param baseValue
	 * @param pawnValue
	 * @param description
	 */
	public Property(int id, String name, Player owner, int baseValue, int pawnValue, String description) {
		super(id, name, description);
		this.baseValue = baseValue;
		this.pawnValue = pawnValue;
		this.owner = owner;
		this.currentValue = 0;
		this.isPawned = false;
		this.currentValue = baseValue;
	}

	public int getBaseValue() {
		return baseValue;
	}
	public void setBaseValue(int value) {
		baseValue = value;
	}
	public int getCurrentValue() { 
		return currentValue;
	}
	public void setCurrentValue(int value) {
		currentValue = value;
	}
	public int getPawnValue() {
		return pawnValue;
	}
	public void setPawnValue(int value) {
		pawnValue = value;
	}
	public boolean isOwned () {
		return !(this.owner == null);
	}
	public Player getOwner() {
		return owner;
	}
	public void setOwner(Player newOwner) {
		owner = newOwner;
	}
	public boolean getIsPawned() {
		return isPawned;
	}
	public void setIsPawned(boolean b) {
		isPawned = b;
	}


}
