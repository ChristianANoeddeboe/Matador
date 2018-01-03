package core;

import gui_fields.GUI_Field;

public class GUIController {
	private GUI_Field[] fields_GUI = new GUI_Field[40];
	private PropertiesIO config = new PropertiesIO("config.properties");
	private PropertiesIO translations = new PropertiesIO("translations.propertie");
	
	
	private void Create(Field field) {}
	private void addPlayer(String name, int balance) {}
	private void displayChanceCard(String cardText) {}
	private void updatePlayerPosition(int id, int position) {}
	private void updatePlayerBalance(int id, int balance) {}
}
