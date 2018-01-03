package core;

import java.awt.Color;
import java.util.Arrays;

import gui_fields.GUI_Field;
import gui_fields.GUI_Street;
/**
 * 
 * @author Mathias Thejsen - s175192
 *
 */
public class GameBoard {
	/**
	 * Initializes the board, creating the regular fields, taxi fields and prison fields.
	 */
	public static void initBoard(GUI_Field[] fields) {
		for (int i = 0; i <= fields.length; i++) {
			if(i>=1&&i<=Integer.valueOf(GameController.translator.getTranslation("amountoffields"))) {
				fields[i-1] = new GUI_Street(GameController.translator.getTranslation("field"+i), GameController.translator.getTranslation("valuetext") + " " + (GameController.translator.getTranslation("fieldvalue"+i)), "", "", Color.GREEN, Color.BLACK);
				if(Integer.parseInt(GameController.translator.getTranslation("fieldvalue"+i))==0) {
					GameController.prisonarray[i-1] = 1;
				}
				try {
					if(GameController.translator.getTranslation("field"+i+"taxi").equals("true")) {
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
		for (int i = 0; i < Integer.parseInt(GameController.translator.getTranslation("amountoffields")); i++) {
			System.out.println(i+"|Field title: "+ fields[i].getTitle() + " "+ fields[i].getSubText());
		}
	}
}

