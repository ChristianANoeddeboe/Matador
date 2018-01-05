package core;

import java.awt.*;
/**
 * 
 * @author Mathias Thejsen - Thejsen@live.dk && Simon Fritz
 *
 */
public class Normal extends Property {
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
	Normal(int id, String name, Player owner, int baseValue, int[] housePrices, int pawnValue, int buildPrice, Color colour, String description) {
		super(id, name, owner, baseValue, pawnValue, description);
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
