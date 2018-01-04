package core;

public abstract class Field {

	private String name;
	private int id;

	public String getName() {
		return name;
	}

	public void setName(String n) {
		name = n;
	}

	public int getId() {
		return id;
	}
	public void setId(int i) {
		id = i;
	}

	public Field(int id, String name) {
		this.id = id;
		this.name = name;
	}
}
