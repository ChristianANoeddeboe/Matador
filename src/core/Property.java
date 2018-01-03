package core;

public class Property extends Field {

	Player owner = null;
	protected int baseValue = 0;
	protected int currentValue = 0;
	protected int pawnValue = 0;
	
	public int getBaseValue() { return baseValue; }
	public void setBaseValue(int value) { baseValue = value; }
	public int getCurrentValue() { return currentValue; }
	public void setCurrentValue(int value) { currentValue = value; }
	public int getPawnValue() { return pawnValue; }
	public void setPawnValue(int value) { pawnValue = value; } 
	public String getOwner() { return owner.getName(); }
	public void setOwner(Player newOwner) { owner = newOwner; }
}
