package core;

public class Shipping extends Property {
	int currentValue;
	public Shipping(int id, String name, String type, Player owner, int baseValue, int pawnValue) {
		super(id, name, type, owner, baseValue, pawnValue);
		this.currentValue = baseValue;
	}

}
