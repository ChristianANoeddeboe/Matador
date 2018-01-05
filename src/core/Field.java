package core;

public abstract class Field {

	private String name;
	private int id;
	private String description;

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

	public String getDescription() {
		return description;
	}

	public Field(int id, String name, String description) {
		this.id = id;
		this.name = name;
		this.description = description;
	}
}
