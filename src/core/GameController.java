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

	public void prepareGame() {
		String playerNames[] = guiController.setupBoard();
		playerController.initPlayers(playerNames.length);
		for(int i = 0 ; i < playerNames.length ; i++) {
			Player player = new Player(playerNames[i], i);
			playerController.getPlayers()[i] = player;
		}
	}
	
	public void playRound() {
		boolean gameIsLive = true;
		boolean switchPlayer = true;
		while(gameIsLive) {
			for (Player player : playerController.getPlayers()) {
				do {
					//Check if game is still live
					int amountBankrupt = 0;
					for (Player otherPlayer : playerController.getPlayers())
						if(otherPlayer.isBanktrupt())
							amountBankrupt++;
					
					if(amountBankrupt >= playerController.getPlayers().length-1) {
						gameIsLive = false;
						break;
					}
						
					if(!player.isBanktrupt())
						switchPlayer = gameLogic.callLogic(playerController, player);
				} while(!switchPlayer);
				player.setRolled(true);
			}
		}
		
		//GAME IS OVER WRITE SOMETHING NICE TO THE WINNER
	}
}



