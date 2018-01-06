package core;

public class GameController {
	private Entities entities;
	private GUIController guiController;
	private GameLogic gameLogic;
	private Player currentPlayer;
	private boolean playerRoundHasEnded = false;
	
	public static void main(String Args[]) {
		GameController gameController = new GameController();

		gameController.prepareGame();
		
		gameController.startGame();
	
	}
	
	public GameController() {
		entities = Entities.getInstance();
		guiController = GUIController.getInstance();
		gameLogic = new GameLogic();
		
	}
	
	public void prepareGame() {
		guiController.setupBoard(entities.getFieldArr());
		
		int amountOfPlayers = guiController.requestNumberOfPlayers();
		entities.initPlayers(amountOfPlayers);
		
		for ( int i = 0 ; i < amountOfPlayers ; i++ ) {
			String name = guiController.requestStringInput("Please write name.");
			if (name.equals("")) name = "player"+(i+1);
			Player player = new Player(name, i);
			
			guiController.addPlayer(0, 30000, name);
			entities.getPlayers()[i] = player;
		}
		
		currentPlayer = entities.getPlayers()[0];
	}
	
	public void startGame() {
		boolean gameIsLive = true;
		
		while(gameIsLive) {
			choosePlayer();
			startRound();
			//gameIsLive = gameLogic.isGameOver;
			
		}
	}

	public void choosePlayer() {
		boolean choosePlayer = false;
		if(playerRoundHasEnded) {
			do {
				for (Player player : entities.getPlayers()) {
					if(choosePlayer) {
						currentPlayer = player;
						choosePlayer = false;
						break;
					}
					
					if(player == currentPlayer)
						choosePlayer = true;
				}
			} while (choosePlayer);
			playerRoundHasEnded = false;
		}
	}

	public void startRound() {
		String choices[] = {"Roll dice", "Build house/hotel","Auction"};
		switch(guiController.requestPlayerChoice("It is " + currentPlayer.getName() + "'s turn, choose option:", choices)) {
			case "Roll dice" : {
				playRound();
				break;
			}
			case "Build house/hotel" : {
				build();
				break;
			}
			case "Auction" : {
				auction();
				break;
			}
		}
	}
	
	public void playRound() {
		rollDice();
		System.out.println(gameLogic.findLogic(currentPlayer));
		switch(gameLogic.findLogic(currentPlayer)) {
			case "NotOwned" : {
				break;
			}
			case "CannotAfford" : {
				break;
			}
			case "OwnedByPlayer" : {
				break;
			}
			case "CanAfford" : {
				break;
			}
			case "SaleLogic" : {
				break;
			}
			case "Rentprice" : {
				break;
			}
			case "saleLogic" : {
				break;
			}	
		}	
	}
		
		
	public void rollDice() {
		int totalValue = 0;
		for ( int i = 0 ; i < entities.getDiceArr().length ; i++ )
			totalValue += entities.getDiceArr()[i].roll();
		
		//save start position and set new end position
		currentPlayer.setStartPosition(currentPlayer.getEndPosition());
		if( ( currentPlayer.getEndPosition() + totalValue ) > 39)
			currentPlayer.setEndPosition(currentPlayer.getEndPosition() + totalValue - 41);
		else
			currentPlayer.setEndPosition(currentPlayer.getEndPosition() + totalValue);
	}
	

	
	public void build() {
		playerRoundHasEnded = true;
	}
	
	public void auction() {
		
	}
	
	
	
	
	
}

