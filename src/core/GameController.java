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
		
		while(gameIsLive) {
			for (Player player : playerController.getPlayers()) {
				boolean switchPlayer = true;
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
				player.setRolled(false);
				player.setPairs(0);
			}
		}
		for (int i = 0; i < playerController.getPlayers().length; i++) {
			if(!playerController.getPlayers()[i].isBanktrupt()) {
				guiController.writeMessage(playerController.getPlayers()[i].getName()+" has won the game!");
			}
		}
		System.exit(0);
	}
}



