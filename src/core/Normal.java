package core;

import java.awt.*;

public class Normal extends Property {
	private int house1Price;
	private int house2Price;
	private int house3Price;
	private int house4Price;
	private int BuildPrice;
	private int hotelPrice;
	private Color colour;
	
	public Normal(int id, String name, String type, Player owner, int baseValue, int pawnValue, int house1Price, int house2Price, int house3Price, int house4Price, int buildPrice, int hotelprice, Color colour) {
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

	public int getHousePrice() {
		return house1Price;
	}

	public void setHousePrice(int value) {
		house1Price = value;
	}

	public int getHotelPrice() {
		return hotelPrice;
	}

	public void setHotelPrice(int value) {
		hotelPrice = value;
	}

	public Color getColour() {
		return colour;
	}

	public void setColour(Color type) {
		colour = type;
	}
}
