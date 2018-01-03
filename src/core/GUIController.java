package core;

import gui_fields.*;
import gui_main.GUI;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class GUIController {

	private GUI_Field[] fields_GUI;
	private PropertiesIO config = new PropertiesIO("config.properties");
	private PropertiesIO translations = new PropertiesIO("translations.propertie");
	private GUI gui;
	private GUI_Player[] players_GUI;
	
	
	private void create(Field[] fields) {
		fields_GUI = new GUI_Field[fields.length];
		for (int i = 0 ; i < fields_GUI.length ; i++) {
			initializeField(i, fields[i]);
		}
	}

	private void initializeField(int i, Field field) {
		switch (field.type) {
			case 0:
				fields_GUI[i] = new GUI_Start();
				break;
			case 1:
				fields_GUI[i] = new GUI_Brewery(String picture, String title, String subText, String description, String rent, Color bgColor, Color fgColor);

		}
	}

	private void addPlayer(String name, int balance) {

	}

	private void displayChanceCard(String cardText) {
		gui.displayChanceCard(cardText);
	}

	private void updatePlayerPosition(int id, int newPos, int oldPos) {
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

	private void updatePlayerBalance(int id, int balance) {

	}
}
