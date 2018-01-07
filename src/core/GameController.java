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
			if (name.equals("")) { name = "player"+(i+1);}
			Player player = new Player(name, i);
			guiController.addPlayer(i, 30000, name);
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
		String choices[] = {"Roll dice"};
		switch(guiController.requestPlayerChoice("It is " + currentPlayer.getName() + "'s turn, choose option:", choices)) {
		case "Roll dice" : 
			playRound();
			break;
		}
	}

	public void playRound() {
		String choices[] = {"Yes", "No"};
		rollDice();
		guiController.updatePlayerPosition(currentPlayer.getId_GUI(), currentPlayer.getEndPosition(), currentPlayer.getStartPosition());
		switch(gameLogic.findLogic(currentPlayer)) {
		case "NotOwned" : {
			switch (guiController.requestPlayerChoice(entities.getFieldArr()[currentPlayer.getEndPosition()].getName() +" is not owned, would you like to purchase it?", choices)) {
			case "Yes":
				BuyLogic buyLogic = new BuyLogic(currentPlayer);
				buyLogic.propertyBuyLogic(currentPlayer);
				break;
			case "No":
				System.out.println("No");
				break;
			}
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
		if(gameLogic.passedStart(currentPlayer)) { // Check if we passed start
			guiController.updatePlayerBalance(currentPlayer.getId_GUI(), currentPlayer.getAccount().getBalance(), currentPlayer.getAccount().getBalance()-3000);
		}
		playerRoundHasEnded = true;
	}


	public void rollDice() {
		int totalValue = 0;
		for ( int i = 0 ; i < entities.getDiceArr().length ; i++ ) {
			totalValue += entities.getDiceArr()[i].roll();
		}
		guiController.showDice();
		//save start position and set new end position
		currentPlayer.setStartPosition(currentPlayer.getEndPosition());
		if( ( currentPlayer.getEndPosition() + totalValue ) > 39) {
			currentPlayer.setEndPosition(currentPlayer.getEndPosition() + totalValue - 40);
		}else {
			currentPlayer.setEndPosition(currentPlayer.getEndPosition() + totalValue);
		}
	}



	public void build() {
		playerRoundHasEnded = true;
	}

	public void auction() {

	}





}

