package core;

import java.awt.Color;

import gui_fields.GUI_Car;
import gui_fields.GUI_Field;
import gui_fields.GUI_Street;
/**
 * 
 * @author Mathias Thejsen - s175192
 *
 */
public class GameBoardController {
	private Field[] logicFields = new Field[40];
	
	/**
	 * Initializes the board, creating the regular fields, taxi fields and prison fields.
	 */
	public static void initBoard(GUI_Field[] fields) {
		for (int i = 0; i <= fields.length; i++) {
			if(i>=1&&i<=Integer.valueOf(GameController.properties.getTranslation("amountoffields"))) {
				fields[i-1] = new GUI_Street(GameController.properties.getTranslation("field"+i), GameController.properties.getTranslation("valuetext") + " " + (GameController.properties.getTranslation("fieldvalue"+i)), "", "", Color.GREEN, Color.BLACK);
				if(Integer.parseInt(GameController.properties.getTranslation("fieldvalue"+i))==0) {
					GameController.prisonarray[i-1] = 1;
				}
				try {
					if(GameController.properties.getTranslation("field"+i+"taxi").equals("true")) {
						GameController.taxiarray[i-1]=1;
					}
				} catch (Exception e) {
					//No  need to deal with fields that are NOT taxi fields
				}

			}else {
				if(i-1 > 0) {
					fields[i-1] = new GUI_Street("", "", "", "", Color.GREEN, Color.GREEN);
				}
			}
		}
	}

	protected static void toString(GUI_Field[] fields) {
		for (int i = 0; i < Integer.parseInt(GameController.properties.getTranslation("amountoffields")); i++) {
			System.out.println(i+"|Field title: "+ fields[i].getTitle() + " "+ fields[i].getSubText());
		}
	}
	public void generateBoard(Player[] players) {
		
	}

	/**
	 * Chooses a random pattern so no two cars look alike
	 * @param i the player number
	 * @return a pattern
	 */
	private static GUI_Car.Pattern nextPattern(int i) {
		switch (i) {
		case 1:
			return GUI_Car.Pattern.CHECKERED;
		case 2:
			return GUI_Car.Pattern.DOTTED;
		case 3:
			return GUI_Car.Pattern.DIAGONAL_DUAL_COLOR;
		case 4:
			return GUI_Car.Pattern.HORIZONTAL_LINE;
		case 5:
			return GUI_Car.Pattern.ZEBRA;
		case 6:
			return GUI_Car.Pattern.HORIZONTAL_GRADIANT;
		default:
			return GUI_Car.Pattern.HORIZONTAL_DUAL_COLOR;
		}
	}
	/**
	 * Chooses a random color so no two cars have the same colour
	 * @param i the player number
	 * @return a color
	 */
	private static Color randomColour(int i) {
		switch (i) {
		case 1:
			return Color.RED;
		case 2:
			return Color.BLUE;
		case 3:
			return Color.CYAN;
		case 4:
			return Color.DARK_GRAY;
		case 5:
			return Color.PINK;
		case 6:
			return Color.MAGENTA;
		default:
			return Color.YELLOW;
		}
	}
}
