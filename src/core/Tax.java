package core;

public class Tax extends Field {

	private int taxvalue;

	Tax(int id, String name, int taxvalue, String description) {
		super(id, name, description);
		this.taxvalue = taxvalue;
	}

	
	
}
