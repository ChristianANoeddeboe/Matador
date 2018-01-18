package core;

public class GameController {
	private GUIController guiController;
	private PlayerController playerController;
	private GameLogic gameLogic;

	public static void main(String Args[]) {
		GameController gameController = new GameController();
		gameController.prepareGame();
		gameController.playRound();
	}

	public GameController() {
		guiController = GUIController.getInstance();
		playerController = new PlayerController();
		gameLogic = new GameLogic();
	}
	
	/**
	 * Prepares the game, instanciates the players
	 * 
	 * @return void
	 */
	public void prepareGame() {
		String playerNames[] = guiController.setupBoard();
		playerController.initPlayers(playerNames.length);
		for(int i = 0 ; i < playerNames.length ; i++) {
			Player player = new Player(playerNames[i], i);
			playerController.getPlayers()[i] = player;
		}
	}
	
	/**
	 * Holds the main game loop, as well as the player switcher Do While
	 * 
	 * @return void 
	 */
	public void playRound() {
		boolean gameIsLive = true;
		
		//Main game loop (game is won when gameIsLive = false
		while(gameIsLive) {
			
			//Player switching loop
			for (Player player : playerController.getPlayers()) {
				boolean switchPlayer = true;
				
				//Do while loop as long as it is the player turn
				do {
					//Check if any players is bankrupt
					int amountBankrupt = 0;
					for (Player otherPlayer : playerController.getPlayers())
						if(otherPlayer.isBanktrupt())
							amountBankrupt++;
					
					//if only one player is not bankrupt end game.
					if(amountBankrupt >= playerController.getPlayers().length-1) {
						gameIsLive = false;
						break;
					}
					
					//start round if player not bankrupt
					if(!player.isBanktrupt()) {
						switchPlayer = gameLogic.showOptions(playerController, player);
					}
				} while(!switchPlayer);
				
				//reset player attributes
				player.setRolled(false);
				player.setPairs(0);
			}
		}
		
		//Find player whom won and display winner string
		for (int i = 0; i < playerController.getPlayers().length; i++) {
			if(!playerController.getPlayers()[i].isBanktrupt()) {
				guiController.writeMessage(playerController.getPlayers()[i].getName()+PropertiesIO.getTranslation("winnerstr"));
			}
		}
		
		//End game
		System.exit(0);
	}
}



