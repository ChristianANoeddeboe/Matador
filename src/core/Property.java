package core;
/**
 * 
 * @author Mathias Thejsen s175192 && Simon Hansen s175191
 *
 */
public abstract class Property extends Field {
	private Player owner;
	private int buyValue;
	private int rentValue;
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
	public Property(int id, String name, String description, Player owner, int buyValue, int pawnValue, int rentValue) {
		super(id, name, description);
		this.buyValue = buyValue;
		this.pawnValue = pawnValue;
		this.owner = owner;
		this.isPawned = false;
		this.rentValue = rentValue;
	}
	public Player getOwner() {
		return owner;
	}
	public void setOwner(Player owner) {
		this.owner = owner;
	}
	public int getBuyValue() {
		return buyValue;
	}
	public void setBuyValue(int buyValue) {
		this.buyValue = buyValue;
	}
	public int getRentValue() {
		return rentValue;
	}
	public void setRentValue(int rentValue) {
		this.rentValue = rentValue;
	}
	public int getPawnValue() {
		return pawnValue;
	}
	public void setPawnValue(int pawnValue) {
		this.pawnValue = pawnValue;
	}
	public boolean isPawned() {
		return isPawned;
	}
	public void setPawned(boolean isPawned) {
		this.isPawned = isPawned;
	}


}
