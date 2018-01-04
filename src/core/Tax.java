package core;

public class Tax extends Field {
	protected int taxvalue;
	public Tax(int id, String name, String type, int taxvalue) {
		super(id, name, type);
		this.id = id;
		this.name = name;
		this.type = type;
		this.taxvalue = taxvalue;
	}

	
	
}
