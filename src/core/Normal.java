package core;

public class Normal extends Property {
	private int house1Price;
	private int house2Price;
	private int house3Price;
	private int house4Price;
	private int BuildPrice;
	private int hotelPrice;
	private String colour = "";
	
	public Normal(int id, String name, String type, Player owner, int baseValue, int pawnValue, int house1Price, int house2Price, int house3Price, int house4Price, int buildPrice, int hotelprice, String colour) {
		super(id, name, type, owner, baseValue, pawnValue);
		this.id = id;
		this.name = name;
		this.type = type;
		this.owner = owner;
		this.baseValue = baseValue;
		this.currentValue = baseValue;
		this.pawnValue = pawnValue;
		this.isPawned = false;
		this.house1Price = house1Price;
		this.house2Price = house2Price;
		this.house3Price = house3Price;
		this.house4Price = house4Price;
		this.hotelPrice = hotelprice;
		this.BuildPrice = buildPrice;
		this.colour = colour;
		// TODO Auto-generated constructor stub
	}
	

	public int getHouse1Price() {
		return house1Price;
	}


	public void setHouse1Price(int house1Price) {
		this.house1Price = house1Price;
	}


	public int getHouse2Price() {
		return house2Price;
	}


	public void setHouse2Price(int house2Price) {
		this.house2Price = house2Price;
	}


	public int getHouse3Price() {
		return house3Price;
	}


	public void setHouse3Price(int house3Price) {
		this.house3Price = house3Price;
	}


	public int getHouse4Price() {
		return house4Price;
	}


	public void setHouse4Price(int house4Price) {
		this.house4Price = house4Price;
	}


	public int getBuildPrice() {
		return BuildPrice;
	}


	public void setBuildPrice(int buildPrice) {
		BuildPrice = buildPrice;
	}


	private int getHousePrice() { return house1Price; }
	private void setHousePrice(int value) { house1Price = value; }
	private int getHotelPrice() { return hotelPrice; }
	private void setHotelPrice(int value) { hotelPrice = value; }
	private String getColour() { return colour; }
	private void setColour(String type) { colour = type; }
}
