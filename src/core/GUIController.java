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
		System.out.println(fields[0].getClass().getSimpleName());
		for (int i = 0 ; i < fields_GUI.length ; i++) {
			switch (fields[i].getClass().getSimpleName().toLowerCase()) {
				case "start":
					Start start = (Start)fields[i];
					fields_GUI[i] = new GUI_Start("Start", "", start.getDescription(), Color.GREEN, Color.BLACK);
					break;
				case "normal":
					Normal normal = (Normal)fields[i];
					fields_GUI[i] = new GUI_Street(normal.getName(), "", normal.getDescription(), ""+normal.getBaseValue(), normal.getColour(), Color.BLACK);
					break;
				case "brewery":
					Brewery brewery = (Brewery)fields[i];
					fields_GUI[i] = new GUI_Brewery("default", brewery.getName(), "", "0101010101010101010101010\n0101010101010101010101010\n0101010101010101010101010\n0101010101010101010101010\n0101010101010101010101010\n0101010101010101010101010\n0101010101010101010101010\n0101010101010101010101010\n0101010101010101010101010\n0101010101010101010101010\n0101010101010101010101010\n", ""+brewery.getBaseValue(), Color.WHITE, Color.BLACK);
					break;
				case "shipping":
					Shipping shipping = (Shipping)fields[i];
					fields_GUI[i] = new GUI_Shipping("default", shipping.getName(), "Subtext", "Description", ""+shipping.getBaseValue(), Color.WHITE, Color.BLACK);
					break;
				case "chance":
					Chance chance = (Chance)fields[i];
					fields_GUI[i] = new GUI_Chance(chance.getName(), "Subtext", "Description", Color.WHITE, Color.BLACK);
					break;
				case "prison":
					Prison prison = (Prison)fields[i];
					fields_GUI[i] = new GUI_Jail("default", prison.getName(), "Subtext", "Description", Color.WHITE, Color.BLACK);
					break;
				case "parking":
					Parking parking = (Parking)fields[i];
					fields_GUI[i] = new GUI_Refuge("default", parking.getName(), "Subtext", "Description", Color.WHITE, Color.BLACK);
					break;
				case "tax":
				    Tax tax = (Tax)fields[i];
					fields_GUI[i] = new GUI_Tax(tax.getName(), "Subtext", "Description", Color.WHITE, Color.BLACK);
					break;
				default:
					fields_GUI[i] = new GUI_Empty();
					break;
			}
		}
		gui = new GUI(fields_GUI);
	}

	public void addPlayer(int id, int startValue, String name) {
		players_GUI[id] = new GUI_Player(name, startValue);
		fields_GUI[0].setCar(players_GUI[id], true);
		gui.addPlayer(players_GUI[id]);
	}

	public void displayChanceCard(String cardText) {
		gui.displayChanceCard(cardText);
	}

	public void updatePlayerPosition(int id, int newPos, int oldPos) {
		for (int i = oldPos ; i < newPos ; i++) {
			fields_GUI[i].setCar(players_GUI[id], false);
			fields_GUI[i+1].setCar(players_GUI[id], true);
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void updatePlayerBalance(int id, int newValue, int oldValue) {
		for (int i = oldValue ; i < newValue ; i++) {
			players_GUI[id].setBalance(i);
			try {
				TimeUnit.MILLISECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
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
	public String requestPlayerChoice(String message, String... options) {
		return gui.getUserSelection(message, options);
	}

	/**
	 * Writes a message on the GUI.
	 * @param message the String which will be written.
	 */
	public void writeMessage(String message) {
		gui.showMessage(message);
	}
}