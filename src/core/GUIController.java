package core;

import gui_fields.*;
import gui_main.GUI;

import java.awt.*;
import java.util.concurrent.TimeUnit;

/**
 * @author Christian S. Andersen
 */

public class GUIController {

	private GUI_Field[] fields_GUI;
	private GUI gui;
	private GUI_Player[] players_GUI;
	private static final GUIController guiController = new GUIController();

	private GUIController() {}

	public static GUIController getInstance() {
		return guiController;
	}

	public void setupBoard(Field[] fields) {
		fields_GUI = new GUI_Field[fields.length];
		createFields(fields);
	}

	public void createFields(Field[] fields) {
		for (int i = 0 ; i < fields_GUI.length ; i++) {
			if(fields[i] instanceof Start) {
				Start start = (Start) fields[i];
				fields_GUI[i] = new GUI_Start("Start", "", start.getDescription(), Color.GREEN, Color.BLACK);
			}else if(fields[i] instanceof Normal) {
				Normal normal = (Normal)fields[i];
				fields_GUI[i] = new GUI_Street(normal.getName(), "", normal.getDescription(), ""+normal.getBaseValue(), normal.getColour(), Color.BLACK);
			}else if(fields[i] instanceof Brewery) {
				Brewery brewery = (Brewery)fields[i];
				fields_GUI[i] = new GUI_Brewery("default", brewery.getName(), "", "0101010101010101010101010\n0101010101010101010101010\n0101010101010101010101010\n0101010101010101010101010\n0101010101010101010101010\n0101010101010101010101010\n0101010101010101010101010\n0101010101010101010101010\n0101010101010101010101010\n0101010101010101010101010\n0101010101010101010101010\n", ""+brewery.getBaseValue(), Color.WHITE, Color.BLACK);
			}else if(fields[i] instanceof Shipping) {
				Shipping shipping = (Shipping)fields[i];
				fields_GUI[i] = new GUI_Shipping("default", shipping.getName(), "Subtext", "Description", ""+shipping.getBaseValue(), Color.WHITE, Color.BLACK);
			}else if(fields[i] instanceof Chance) {
				Chance chance = (Chance)fields[i];
				fields_GUI[i] = new GUI_Chance(chance.getName(), "Subtext", "Description", Color.WHITE, Color.BLACK);
			}else if(fields[i] instanceof Prison) {
				Prison prison = (Prison)fields[i];
				fields_GUI[i] = new GUI_Jail("default", prison.getName(), "Subtext", "Description", Color.WHITE, Color.BLACK);
			}else if(fields[i] instanceof Parking) {
				Parking parking = (Parking)fields[i];
				fields_GUI[i] = new GUI_Refuge("default", parking.getName(), "Subtext", "Description", Color.WHITE, Color.BLACK);
			}else if(fields[i] instanceof Tax) {
				Tax tax = (Tax)fields[i];
				fields_GUI[i] = new GUI_Tax(tax.getName(), "Subtext", "Description", Color.WHITE, Color.BLACK);
			}else {
				fields_GUI[i] = new GUI_Empty();
			}
		}
		gui = new GUI(fields_GUI);
	}

	/**
	 * Used when adding a new player to the board.
	 * @param id the id associated with the Player object.
	 * @param startValue value the player starts with.
	 * @param name name of the player.
	 */
	public void addPlayer(int id, int startValue, String name) {
		players_GUI[id] = new GUI_Player(name, startValue, createCar(id));
		fields_GUI[0].setCar(players_GUI[id], true);
		gui.addPlayer(players_GUI[id]);
	}

	/**
	 * Creates the GUI vehicle for a player.
	 * @param id the id associated with the player.
	 * @return object type GUI_Car.
	 */
	private GUI_Car createCar(int id) {
		Color color = getVehicleColor(id);
		return new GUI_Car(color, color, GUI_Car.Type.CAR, GUI_Car.Pattern.FILL);
	}

	/**
	 * Displays a message in the middle of the board, with black text on white background.
	 * @param message the message you would like displayed on the card.
	 */
	public void displayChanceCard(String message) {
		gui.displayChanceCard(message);
	}

	public void updatePlayerPosition(int id, int newPos, int oldPos) {
		if (oldPos > newPos) {
			for (int i = oldPos ; i < fields_GUI.length-1 ; i++) {
				fields_GUI[i].setCar(players_GUI[id], false);
				fields_GUI[i+1].setCar(players_GUI[id], true);
				try {
					TimeUnit.MILLISECONDS.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			fields_GUI[fields_GUI.length-1].setCar(players_GUI[id], false);
			fields_GUI[0].setCar(players_GUI[id], true);
			oldPos = 0;
			try {
				TimeUnit.MILLISECONDS.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		for (int i = oldPos ; i < newPos ; i++) {
			fields_GUI[i].setCar(players_GUI[id], false);
			fields_GUI[i+1].setCar(players_GUI[id], true);
			try {
				TimeUnit.MILLISECONDS.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void updatePlayerBalance(int id, int newValue, int oldValue) {
		if(newValue >= oldValue) { // If we are updating gui balance in a positive way
			for (int i = oldValue ; i <= newValue ; i++) {
				players_GUI[id].setBalance(i);
				try {
					TimeUnit.NANOSECONDS.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}else {
			for (int i = oldValue ; i >= newValue ; i--) { // if we are updating balance in a negative way
				players_GUI[id].setBalance(i);
				try {
					TimeUnit.NANOSECONDS.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Moves the players vehicle from the GUI to the jail field.
	 * @param id the id associated with the player.
	 * @param playerPos position of the player.
	 * @param jailPos position of the jail.
	 */
	public void jailPlayer(int id, int playerPos, int jailPos) {
		fields_GUI[playerPos].setCar(players_GUI[id],false);
		fields_GUI[jailPos].setCar(players_GUI[id],true);
	}

	/**
	 * Changes the border of an ownable field, to the color of the player.
	 * @param player_id the location of the specific GUI_Player object in the array of GUI_Player objects.
	 * @param field_id the location of the specific GUI_Field object in the array of GUI_Field objects.
	 */
	public void setOwner(int player_id, int field_id) {
		((GUI_Ownable)fields_GUI[field_id]).setBorder(players_GUI[player_id].getPrimaryColor());
	}

	/**
	 * Lets the user input an integer the system can interprit. A pre defined message will be displayed alongside the text field.
	 * @return int
	 */
	public int requestNumberOfPlayers() {
		int input = gui.getUserInteger("Choose number of players.", 3, 6);
		players_GUI = new GUI_Player[input];
		return input;
	}

	/**
	 * Lets the user input a String message the system can interpret.
	 * @param message - Message the GUI will display along with the text field.
	 * @return String
	 */
	public String requestStringInput(String message) {
		return gui.getUserString(message);
	}

	/**
	 * Creates a bottom and dropdown menu on the board, with the message provided, and the options provided. Returns a String for the users choice.
	 * @param message the message which will be written to the players.
	 * @param options the list of options for the dropdown menu.
	 * @return a String with the players choice.
	 */
	public String requestPlayerChoice(String message, String[] options) {
		return gui.getUserSelection(message, options);
	}

	/**
	 * Writes a message on the GUI.
	 * @param message the String which will be written.
	 */
	public void writeMessage(String message) {
		gui.showMessage(message);
	}

	/**
	 * Changes the dice on the board.
	 */
	public void showDice() {
		gui.setDice(Entities.getInstance().getDiceArr()[0].getValue(), Entities.getInstance().getDiceArr()[1].getValue());
	}

	/**
	 * Returns the color depending on what number the player is.
	 * @return Color
	 */
	public Color getVehicleColor(int id) {
		switch (id) {
		case 0:
			return Color.BLUE;
		case 1:
			return Color.RED;
		case 2:
			return Color.GREEN;
		case 3:
			return Color.YELLOW;
		case 4:
			return Color.MAGENTA;
		case 5:
			return Color.CYAN;
		case 6:
			return Color.WHITE;
		default:
			return Color.BLUE;
		}
	}

	/**
	 * Used when starting new game. Will reset all players position to start and set all their money to the start money.
	 */
	public void resetUIPlayers(int startBalance) {
		for (GUI_Field field : fields_GUI) {
			field.removeAllCars();
		}
		for (GUI_Player player : players_GUI) {
			fields_GUI[0].setCar(player, true);
			player.setBalance(startBalance);
		}
	}
}