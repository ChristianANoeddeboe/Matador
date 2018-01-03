package core;

public class Normal extends Property {
	protected int housePrice = 0;
	protected int hotelPrice = 0;
	protected String colour = "";
	
	private int getHousePrice() { return housePrice; }
	private void setHousePrice(int value) { housePrice = value; }
	private int getHotelPrice() { return hotelPrice; }
	private void setHotelPrice(int value) { hotelPrice = value; }
	private String getColour() { return colour; }
	private void setColour(String type) { colour = type; }
}
