package core;

public abstract class Field {

	protected String name = "";
	protected int id = 0;
	protected String type = "";
	public String getName() { return name; }
	public void setName(String n) { name = n; }
	public int getId() { return id; }
	public void setId(int i) { id = i; }
	
	public Field(int id, String name, String type) {
		this.id = id;
		this.name = name;
		this.type = type;
	}
}
