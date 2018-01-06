package core;
/**
 * 
 * @author Mathias Thejsen s175192 && Simon Hansen s175191
 *
 */
public class Tax extends Field {
	private int taxvalue;
	/**
	 * Constructor for Tax field
	 * @param id Field number
	 * @param name Field name
	 * @param taxvalue Taxvalue
	 * @param description Description
	 */
	public Tax(int id, String name, int taxvalue, String description) {
		super(id, name, description);
		this.taxvalue = taxvalue;
	}

	
	
}
