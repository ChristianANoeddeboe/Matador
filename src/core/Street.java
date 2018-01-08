package core;

import java.awt.*;
/**
 * 
 * @author Mathias Thejsen s175192 && Simon Hansen s175191
 *
 */
public class Street extends Property {
	private int houseCounter;
	private int[] housePrices = new int[6];
	private int BuildPrice;
	private Color colour;
	/**
	 * Constructor for normal field logic
	 * @param id
	 * @param name
	 * @param owner
	 * @param baseValue
	 * @param housePrices
	 * @param pawnValue
	 * @param buildPrice
	 * @param colour
	 * @param description
	 */
	public Street(int id, String name, String description, Player owner, int buyValue, int pawnValue, int rentValue, int[] housePrices, int buildPrice, Color colour) {
		super(id, name, description, owner, buyValue, pawnValue, rentValue);
		this.houseCounter = 0;
		for (int i = 0 ; i < housePrices.length ; i++) {
			this.housePrices[i] = housePrices[i];
		}
		this.BuildPrice = buildPrice;
		this.colour = colour;
	}
	
	public int getHouseCounter() {
		return houseCounter;
	}
	
	public void setHouseCounter(int houseCounter) {
		this.houseCounter = houseCounter;
	}

	public int getBuildPrice() {
		return BuildPrice;
	}

	public void setBuildPrice(int buildPrice) {
		BuildPrice = buildPrice;
	}

	public int[] getHousePrices() {
		return housePrices;
	}

	public void setHousePrices(int[] housePrices) {
		this.housePrices = housePrices;
	}

	public Color getColour() {
		return colour;
	}

	public void setColour(Color type) {
		colour = type;
	}
}
