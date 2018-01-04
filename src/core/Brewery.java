package core;

public class Brewery extends Property {

	public Brewery(int id, String name, String type, Player owner, int baseValue, int pawnValue) {
		super(id, name, type, owner, baseValue, pawnValue);
		this.owner = owner;
		this.baseValue = baseValue;
		this.pawnValue = pawnValue;
		this.currentValue = baseValue;
	}

}
