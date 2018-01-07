package core;

import java.io.ObjectInputStream.GetField;

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
		rollDice();
		guiController.updatePlayerPosition(currentPlayer.getId_GUI(), currentPlayer.getEndPosition(), currentPlayer.getStartPosition());
		if(entities.getDiceArr()[0].getValue() == entities.getDiceArr()[1].getValue()) { // Incase we throw pairs
			rollDice();
			guiController.updatePlayerPosition(currentPlayer.getId_GUI(), currentPlayer.getEndPosition(), currentPlayer.getStartPosition());			
		}
		System.out.println(gameLogic.findLogic(currentPlayer));
		switch(gameLogic.findLogic(currentPlayer)) {
		case "NotOwned" : {
			String choices[] = {"Yes", "No"};
			switch (guiController.requestPlayerChoiceButtons(entities.getFieldArr()[currentPlayer.getEndPosition()].getName() +" is not owned, would you like to purchase it?", choices)) {
			case "Yes":
				BuyLogic buyLogic = new BuyLogic(currentPlayer);
				guiController.updatePlayerBalance(currentPlayer.getId_GUI(), currentPlayer.getAccount().getBalance()-buyLogic.getPropertyValue(currentPlayer), currentPlayer.getAccount().getBalance());
				buyLogic.propertyBuyLogic(currentPlayer);
				guiController.setOwner(currentPlayer.getId_GUI(), currentPlayer.getEndPosition());
				break;
			case "No":
				break;
			}
			break;
		}
		case "StateTax":{
			guiController.writeMessage("You were taxed 2000 in state tax");
			guiController.updatePlayerBalance(currentPlayer.getId_GUI(), currentPlayer.getAccount().getBalance(), currentPlayer.getAccount().getBalance()-2000);
			break;
		}

		case "TaxChoice":{
			String choices[] = {"4000", "Incometax"};
			String response;
			TaxLogic taxLogic = new TaxLogic(currentPlayer);
			if(guiController.requestPlayerChoiceButtons("Would you like to pay 4000 or 10% income tax?", choices).equals("4000")) {
				response = taxLogic.taxLogic4(currentPlayer, 1);
			}else {
				response = taxLogic.taxLogic4(currentPlayer, 0);
			}

			if (response.equals("SaleLogic")){ // Incase the player could not afford the taxes
				//Call stuff here
			}else {
				guiController.writeMessage("You have paid your taxes which amounted to a total of: " + response);
				guiController.updatePlayerBalance(currentPlayer.getId_GUI(), currentPlayer.getAccount().getBalance(), currentPlayer.getAccount().getBalance()-Integer.parseInt(response));
			}
			break;
		}

		case "CannotAfford" : {
			// Nothing should happen if we can't afford buying a property
			break;
		}
		case "OwnedByPlayer" : {
			// Nothing should happen when we land on our own property
			break;
		}
		case "CanAfford" : {
			Property property = (Property) entities.getFieldArr()[currentPlayer.getEndPosition()];
			guiController.writeMessage("You landed on another players property and were charged with "+property.getCurrentValue());
			guiController.updatePlayerBalance(currentPlayer.getId_GUI(), currentPlayer.getAccount().getBalance(), currentPlayer.getAccount().getBalance()-property.getCurrentValue());
			guiController.updatePlayerBalance(property.getOwner().getId_GUI(), property.getOwner().getAccount().getBalance(), property.getOwner().getAccount().getBalance()+property.getCurrentValue());
			break;
		}
		case "SaleLogic" : {
			break;
		}
		case "saleLogic" : {
			break;
		}
		}
		if(gameLogic.passedStart(currentPlayer)) { // Check if we passed start
			guiController.updatePlayerBalance(currentPlayer.getId_GUI(), currentPlayer.getAccount().getBalance(), currentPlayer.getAccount().getBalance()-4000);
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

