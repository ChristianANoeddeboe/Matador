package core;

public class Shipping extends Property {
	int currentValue;
	public Shipping(int id, String name, String type, Player owner, int baseValue, int pawnValue) {
		super(id, name, type, owner, baseValue, pawnValue);
		this.id = id;
		this.name = name;
		this.type = type;
		this.owner = owner;
		this.baseValue = baseValue;
		this.pawnValue = pawnValue;
		this.currentValue = baseValue;
	}

}
