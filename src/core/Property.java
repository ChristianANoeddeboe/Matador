package core;

public abstract class Property extends Field {

	private Player owner = null;
	private int baseValue = 0;
	private int currentValue = 0;
	private int pawnValue = 0;
	private boolean isPawned;

	public Property(int id, String name, Player owner, int baseValue, int pawnValue) {
		super(id, name);
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

	public int getCurrentValue() { return currentValue;
	}
	public void setCurrentValue(int value) { currentValue = value; }
	public int getPawnValue() { return pawnValue; }
	public void setPawnValue(int value) { pawnValue = value; }
	public boolean isOwned () {
		return this.owner == null;
	}
	public Player getOwner() { return owner; }
	public void setOwner(Player newOwner) { owner = newOwner; }
	public boolean getIsPawned() { return isPawned; }
	public void setIsPawned(boolean b) { isPawned = b; }
	

}
