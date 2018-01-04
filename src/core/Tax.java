package core;

public class Tax extends Field {
	protected int taxvalue;
	public Tax(int id, String name, int taxvalue) {
		super(id, name);
		this.taxvalue = taxvalue;
	}

	
	
}
