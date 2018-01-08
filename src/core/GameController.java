package core;

import java.util.Arrays;

public class GameController {
	private Entities entities;
	private GUIController guiController;
	private GameLogic gameLogic;
	private Player currentPlayer;
	private boolean playerRoundHasEnded = false;
	private BuyLogic buyLogic;

	public static void main(String Args[]) {
		GameController gameController = new GameController();

		gameController.prepareGame();

		gameController.startGame();
	}

	public GameController() {
		entities = Entities.getInstance();
		guiController = GUIController.getInstance();
		gameLogic = new GameLogic();
		buyLogic = new BuyLogic();

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
		
		Field fields[] = entities.getFieldArr();
		for( int i = 0 ; i < fields.length ; i++ ) {
			if(fields[i].getClass().getSimpleName().equals("Normal")) {
				((Normal) fields[i]).setOwner(currentPlayer);
			}
		}
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
		
		for (Normal normal : entities.getNormalBlue()) {
			System.out.println(normal.getName());
		}
		
		System.out.println("CAN I BUY HOUSES???: " + buyLogic.canBuyHouse(currentPlayer).toString());
		if(buyLogic.canBuyHouse(currentPlayer)) {
			String choices2[] = {"Roll dice","Buy house/hotel"};
			choices = choices2;
		}
		
		switch(guiController.requestPlayerChoice("It is " + currentPlayer.getName() + "'s turn, choose option:", choices)) {
		case "Roll dice" : 
			playRound();
			break;
		case "Buy houses" :
			String reponse = buyLogic.houseBuyLogic(currentPlayer);
			break;
		}
	}
	
	public void playRound() {
		rollDice();
		guiController.updatePlayerPosition(currentPlayer.getId_GUI(), currentPlayer.getEndPosition(), currentPlayer.getStartPosition());
		
		//FOR FUNS
		System.out.println(gameLogic.findLogic(currentPlayer));

		switch(gameLogic.findLogic(currentPlayer)) {
			case "NotOwned" : {
				buyLogic.updatePlayer(currentPlayer);
				String choices[] = {"Yes", "No"};
				switch (guiController.requestPlayerChoiceButtons(entities.getFieldArr()[currentPlayer.getEndPosition()].getName() +" is not owned, would you like to purchase it?", choices)) {
					case "Yes": {
						buyLogic.propertyBuyLogic(currentPlayer);
						guiController.setOwner(currentPlayer.getId_GUI(), currentPlayer.getEndPosition());
						break;
					}
					case "No":
						break;
				}
				break;
			}
			
			case "StateTax":{
				guiController.writeMessage("You were taxed 2000 in state tax");
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
				}
				break;
			}

			case "CannotAfford" : {
				buyLogic.updatePlayer(currentPlayer);
				// Nothing should happen if we can't afford buying a property
				break;
			}
			
			case "OwnedByPlayer" : {
				buyLogic.updatePlayer(currentPlayer);
				// Nothing should happen when we land on our own property
				break;
			}
			
			case "CanAfford" : {
				buyLogic.updatePlayer(currentPlayer);
				Property property = (Property) entities.getFieldArr()[currentPlayer.getEndPosition()];
				guiController.writeMessage("You landed on another players property and were charged with "+property.getCurrentValue());
				guiController.updatePlayerBalance(property.getOwner().getId_GUI(), property.getOwner().getAccount().getBalance());
				break;
			}
			
			case "Prison" : {
			    if (currentPlayer.getEndPosition() == 10 && !currentPlayer.isPrison()) {
			        guiController.writeMessage("You are visiting prison.");
                } else if (currentPlayer.getEndPosition() == 10 && currentPlayer.isPrison()) {
                    String stringinput = gameLogic.getPrisonLogic().logic(currentPlayer);
                    if (stringinput.startsWith(",")) stringinput = stringinput.substring(1);
                    String[] input = stringinput.split(",");
                    String[] choices = Arrays.copyOfRange(input, 1, input.length);
                    String choice = guiController.requestPlayerChoiceButtons(choices[0], choices);
                    switch (choice) {
                        case "Roll":
                            rollDice();
                            guiController.writeMessage("You have rolled "+currentPlayer.getPrisontries()+" times out of 3.");
                            gameLogic.getPrisonLogic().prisonRollLogic(currentPlayer);
                            break;
                        case "Pay fine":
                            gameLogic.getPrisonLogic().payPrisonLogic(currentPlayer);
                            guiController.writeMessage("You paid your fine and is released immediately");
                            break;
                        case "Wait a turn":
                            guiController.writeMessage("You do not have enough money and have to wait a turn.");
                            break;
                        default:
                            break;
                    }
                }
                if (currentPlayer.getEndPosition() == 30) {
                    String stringinput = gameLogic.getPrisonLogic().logic(currentPlayer);
                    if (stringinput.startsWith(",")) stringinput = stringinput.substring(1);
                    String[] input = stringinput.split(",");
                    String[] choices = Arrays.copyOfRange(input, 1, input.length);
                    String choice = guiController.requestPlayerChoiceButtons(choices[0], choices);
                    switch (choice) {
                        case "Prison Card":
                            gameLogic.getPrisonLogic().prisonCardLogic(currentPlayer);
                            guiController.writeMessage("You used a prison card and do not go to jail.");
                            break;
                        case "Pay Fine":
                            gameLogic.getPrisonLogic().payPrisonLogic(currentPlayer);
                            guiController.writeMessage("You paid your fine and is released immediately");
                            break;
                        case "Go to jail":
                            currentPlayer.setPrison(true);
                            guiController.writeMessage("You go to jail.");
                            guiController.jailPlayer(currentPlayer.getId_GUI(), currentPlayer.getEndPosition(), 10);
                            break;
                        default:
                            break;
                    }
                }
				break;
			}
			
			case "saleLogic" : {
				break;
			}
		}
		
		guiController.updatePlayerBalance(currentPlayer.getId_GUI(), currentPlayer.getAccount().getBalance());

		
		if(entities.getDiceArr()[0].getValue() == entities.getDiceArr()[1].getValue() && !currentPlayer.isPrison()) {
			guiController.writeMessage("You rolled a pair and gets another turn");
			playerRoundHasEnded = false;
		} else
			playerRoundHasEnded = true;
	}


	public void rollDice() {
		int totalValue = 0;
		for ( int i = 0 ; i < entities.getDiceArr().length ; i++ )
			totalValue += entities.getDiceArr()[i].roll();
		
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

