package core;


public class GameController {
	
	public static void main(String Args[]) {
		Entities entities = Entities.getInstance();

		Field[] fields = entities.getFieldArr();

		GUIController guiController = GUIController.getInstance();
		guiController.setupBoard(fields);
		int playeramount = guiController.requestNumberOfPlayers();
		guiController.setupPlayers(playeramount);
		guiController.addPlayer(0, 4000, guiController.requestStringInput("Please write name."));
		
	}
	
	public GameController() {
		
	}
	
}

