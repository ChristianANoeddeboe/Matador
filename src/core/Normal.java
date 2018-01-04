package core;

public class Normal extends Property {
	protected int housePrice;
	protected int hotelPrice;
	protected String colour = "";
	public Normal(int id, String name, String type, Player owner, int baseValue, int pawnValue, int houseprice, int hotelprice, String colour) {
		super(id, name, type, owner, baseValue, pawnValue);
		this.id = id;
		this.name = name;
		this.type = type;
		this.owner = owner;
		this.baseValue = baseValue;
		this.currentValue = baseValue;
		this.pawnValue = pawnValue;
		this.isPawned = false;
		this.housePrice = houseprice;
		this.hotelPrice = hotelprice;
		this.colour = colour;
		// TODO Auto-generated constructor stub
	}
	

	private int getHousePrice() { return housePrice; }
	private void setHousePrice(int value) { housePrice = value; }
	private int getHotelPrice() { return hotelPrice; }
	private void setHotelPrice(int value) { hotelPrice = value; }
	private String getColour() { return colour; }
	private void setColour(String type) { colour = type; }
}
