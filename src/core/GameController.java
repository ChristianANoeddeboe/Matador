package core;

import java.util.Arrays;

public class GameController {
	private Entities entities;
	private GUIController guiController;
	private GameLogic gameLogic;
	
	public static void main(String Args[]) {
		GameController gameController = new GameController();

		//gameController.prepareGame();
		
		//gameController.startGame();
	
	}
	
	public GameController() {
		entities = Entities.getInstance();
		guiController = GUIController.getInstance();
		//gameLogic = new GameLogic()
		
	}
	
	public void prepareGame() {
		guiController.setupBoard(entities.getFieldArr());
		
		int amountOfPlayers = guiController.requestNumberOfPlayers();
		entities.initPlayers(amountOfPlayers);
		
		for ( int i = 0 ; i < amountOfPlayers ; i++ ) {
			String name = guiController.requestStringInput("Please write name.");
			Player player = new Player(name, i);
			
			guiController.addPlayer(0, 30000, name);
			entities.getPlayers()[i] = player;
		}
		
	}
	
	public void startGame() {
		int PlayerChooser = 0;
		boolean gameIsLive = true;
		
		while(gameIsLive) {
			
		}
	}


	
	
	
	
}

