package core;

public class Normal extends Property {
	protected int housePrice;
	protected int hotelPrice;
	protected String colour = "";
	
	public Normal(int houseprice, int hotelprice, String colour, int basevalue, int currentvalue, int pawnvalue) {
		this.baseValue = basevalue;
		this.currentValue = currentvalue;
		this.isPawned = false;
		this.pawnValue = pawnvalue;
		this.housePrice = houseprice;
		this.hotelPrice = hotelprice;
		this.colour = colour;
	}
	
	private int getHousePrice() { return housePrice; }
	private void setHousePrice(int value) { housePrice = value; }
	private int getHotelPrice() { return hotelPrice; }
	private void setHotelPrice(int value) { hotelPrice = value; }
	private String getColour() { return colour; }
	private void setColour(String type) { colour = type; }
}
