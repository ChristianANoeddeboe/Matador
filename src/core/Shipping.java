package core;

public class Shipping extends Property {
	int currentValue;
	public Shipping(int id, String name, Player owner, int baseValue, int pawnValue) {
		super(id, name, owner, baseValue, pawnValue);
		this.currentValue = baseValue;
	}

}
