/**
 * Parts of the code is reused from last CDIO.
 */
package core;
import java.awt.Color;
import gui_fields.GUI_Car;
import gui_fields.GUI_Field;
import gui_fields.GUI_Player;
import gui_main.GUI;
/**
 * @author Mathias Thejsen - s175192
 * 		   Simon Fritz - s175191
 */
public class GameController {
	/**
	 * Intializing various variables
	 */
	private static GUI_Field[] fields = new GUI_Field[40]; 
	protected static int[] prisonarray = new int[fields.length];
	protected static int[] taxiarray = new int[fields.length];
	public static Translator translator = new Translator();
	public Player[] playerArr;
	private Player[] propertiesarray = new Player[Integer.parseInt(translator.getTranslation("amountoffields"))];
	public Dice[] dicesArr;
	public GUI_Player[] playerGUIArr;
	private boolean startup = true;
	private int iterator;
	private int playerChooser = 0;
	private GUI_Player guiwhitecar;
	private GUI gui;

	/**
	 * Initializing the GameController which initializes players and dices
	 * While loop controls whose turn it is
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		GameController controller = new GameController(2); //Makes new Controller
		controller.runGame(); // Runs the Game
	}

	/**
	 * GameController constructor
	 * @param dices Amount of dices
	 */
	public GameController(int dices) {
		this.playerGUIArr = null;
		this.playerArr = null;
		this.dicesArr = new Dice[dices];
		GameBoard.initBoard(fields); // Prepares everything that has todo with the board

		this.gui = new GUI(fields);
		for (int i = 1; i <= dicesArr.length; i++) {
			this.dicesArr[i-1] = new Dice();
		}

	}


	/**
	 * Chooses a random pattern so no two cars look alike
	 * @param i the player number
	 * @return a pattern
	 */
	private static GUI_Car.Pattern nextPattern(int i) {
		switch (i) {
		case 1:
			return GUI_Car.Pattern.CHECKERED;
		case 2:
			return GUI_Car.Pattern.DOTTED;
		case 3:
			return GUI_Car.Pattern.DIAGONAL_DUAL_COLOR;
		case 4:
			return GUI_Car.Pattern.HORIZONTAL_LINE;
		case 5:
			return GUI_Car.Pattern.ZEBRA;
		case 6:
			return GUI_Car.Pattern.HORIZONTAL_GRADIANT;
		default:
			return GUI_Car.Pattern.HORIZONTAL_DUAL_COLOR;
		}
	}
	/**
	 * Chooses a random color so no two cars have the same colour
	 * @param i the player number
	 * @return a color
	 */
	private static Color randomColour(int i) {
		switch (i) {
		case 1:
			return Color.RED;
		case 2:
			return Color.BLUE;
		case 3:
			return Color.CYAN;
		case 4:
			return Color.DARK_GRAY;
		case 5:
			return Color.PINK;
		case 6:
			return Color.MAGENTA;
		default:
			return Color.YELLOW;
		}
	}
	/**
	 * Method that starts the game and then keeps it running
	 */

	/**
	 * Main loop of the game
	 * @throws InterruptedException
	 */
	private void runGame() throws InterruptedException {


		String result = this.gui.getUserSelection("\t\t"+translator.getTranslation("playerchoosertext"), "2", "3", "4", "5", "6");
		int noPlayers = Integer.parseInt(result);

		/**
		 * Generates array of Players and their respective cars
		 */
		this.playerGUIArr = new GUI_Player[noPlayers];
		for (int i = 1; i <= noPlayers; i++) {
			String name = this.gui.getUserString("\t\t"+translator.getTranslation("playernameleft")+i+" "+translator.getTranslation("playernameright"));
			while(name.length() <= 0) {
				name = this.gui.getUserString("\t\t"+translator.getTranslation("playernameleft")+i+ translator.getTranslation("playernameright"));
			}
			GUI_Car car = new GUI_Car(randomColour(i), randomColour(i+1), GUI_Car.Type.RACECAR, nextPattern(i));
			this.playerGUIArr[i-1] = new GUI_Player(name, Integer.parseInt(translator.getTranslation("startmoney")), car);
		}
		/**
		 * Adds the white car which controls the game
		 */
		GUI_Car car = new GUI_Car(Color.WHITE, Color.WHITE, GUI_Car.Type.CAR, GUI_Car.Pattern.FILL);
		guiwhitecar = new GUI_Player("GameMaster", 0,car);
		fields[0].setCar(guiwhitecar, true);

		/**
		 * generates the players as a GUI_Player and sets their positions(cars)
		 */
		for (int i = 0; i < this.playerGUIArr.length; i++) {
			this.gui.addPlayer(this.playerGUIArr[i]);
			fields[0].setCar(this.playerGUIArr[i], true);
		}
		/**
		 * Creates a array of Players
		 */
		this.playerArr = new Player[noPlayers];
		for (int i = 0; i < noPlayers; i++) {
			this.playerArr[i] = new Player(this.playerGUIArr[i].getName());
		}
		/**
		 * Loop to keep the game running
		 */
		while(true) {

			for (playerChooser = 0; playerChooser < noPlayers; playerChooser++) {
				boolean prisonstatus = this.playerArr[playerChooser].isPrison();
				if(prisonstatus) {this.playerArr[playerChooser].setPrison(false); playerChooser++; break;}
				startRound(getPlayerArr()[playerChooser], dicesArr[0], dicesArr[1]);
				gameLogic(getPlayerGUIArr()[playerChooser], dicesArr[0], dicesArr[1]);
			}
		}
			
	}

	/**
	 * Is called each round,  promts the user to roll the dices
	 * @param player The player whose turn it is
	 * @param dice1 Dice1
	 * @param dice2 Dice2
	 */
	public void startRound(Player player, Dice dice1, Dice dice2) {
		if(startup) {
			this.gui.displayChanceCard(translator.getTranslation("informationtext"));
		}
		this.gui.showMessage(translator.getTranslation("turntextleft")+player.getName()+" "+translator.getTranslation("turntextright"));
		this.gui.getUserButtonPressed(translator.getTranslation("rollbuttoninfo"), translator.getTranslation("rollbuttontext"));
		this.gui.setDice(dice1.roll(), dice2.roll());
		startup = false;
	}

	/**
	 * Handles all the game logic, calculates according to dice values how to affect the players balance
	 * @param player The current player who is rolling
	 * @param dice1 Dice1
	 * @param dice2 Dice2
	 * @return The value of the given field, can either be 0, positive or negative.
	 */

	public int gameLogic(GUI_Player player, Dice dice1, Dice dice2) throws InterruptedException { // Exception here due to Thread.sleep()
		int totalFaceValue = dice1.getValue() + dice2.getValue();
		int value = Integer.valueOf(translator.getTranslation("fieldvalue"+totalFaceValue));
		int currentPosition = this.playerArr[player.getNumber()].startPosition; // Saves the current position
		this.gui.displayChanceCard(player.getName() +" " + translator.getTranslation("rolledtext")+ " " + totalFaceValue);
		mainloop:
			for(int i = 0; i <= totalFaceValue; i++) { // Loop makes the player move one field at a time instead of teleporting
				for (int j = 0; j < fields.length; j++) {
					fields[j].setCar(player, false);
				}
				if(currentPosition+i >= Integer.parseInt(translator.getTranslation("amountoffields"))) {
					for (int j = 0; j <= (totalFaceValue-i); j++) {
						for (int j2 = 0; j2 < fields.length; j2++) {
							fields[j2].setCar(player, false);
						}
						if(j == 0 && currentPosition != 0) {// We passed start
							player.setBalance(player.getBalance()+3000);
							this.playerArr[player.getNumber()].getAccount().deposit(3000);
							fields[iterator].setCar(guiwhitecar, false);
							iterator++; // Move car one step each time we pass start until it's at the end
							if(iterator == Integer.parseInt(translator.getTranslation("amountoffields"))) { // Check if the white car has moved to the end
								Player max = this.playerArr[0];
								for (int j2 = 0; j2 < this.playerArr.length; j2++) {
									if(this.playerArr[j2].getAccount().getBalance() >= max.getAccount().getBalance()) {
										max = this.playerArr[j2];
									}
								}
								this.gui.displayChanceCard(max.getName()+" "+translator.getTranslation("winnertext")); 
								Thread.sleep(2500);
								System.exit(0);
							}else {
								fields[iterator].setCar(guiwhitecar, true); // The game is not done, so move the car one step forward.
							}
						}
						fields[j].setCar(player, true);
						Thread.sleep(250); // Sleep to make the movement more realistic (1000 = 1 sec)
					}
					this.playerArr[player.getNumber()].setStartPosition((totalFaceValue-i));
					break mainloop; // Breaks outerloop
				}else {
					if((currentPosition + i) == 0 && currentPosition != 0) {// You get 3000 when you pass the start field
						player.setBalance(player.getBalance()+3000); 
						this.playerArr[player.getNumber()].getAccount().deposit(3000);
					}
					fields[currentPosition + i].setCar(player, true);
				}
				Thread.sleep(250); // Sleep to make the movement more realistic (1000 = 1 sec)
			}
		
			int fieldvalue = Integer.parseInt(translator.getTranslation("fieldvalue"+(this.playerArr[player.getNumber()].getStartPosition()+1)));
			if(currentPosition + totalFaceValue > Integer.parseInt(translator.getTranslation("amountoffields"))) {
			}else {
				this.playerArr[player.getNumber()].setStartPosition(totalFaceValue+currentPosition); // Saves the new position of the Player
			}
			/**
			 *  Variables for If statements
			 */
			boolean checkIfOnTaxiField = taxiarray[(this.playerArr[player.getNumber()].getStartPosition())] == 1;
			boolean checkIfNotOnTaxiField = taxiarray[(this.playerArr[player.getNumber()].getStartPosition())] != 1;
			boolean canPlayerAfford = this.playerArr[player.getNumber()].getAccount().canAfford(fieldvalue);
			boolean checkIfOnPrisonField = prisonarray[(this.playerArr[player.getNumber()].getStartPosition())] == 1;
			boolean checkIfNotOnPrisonField = prisonarray[(this.playerArr[player.getNumber()].getStartPosition())] != 1;
			boolean playerNotInPrison = this.playerArr[player.getNumber()].isPrison() == false;
			boolean checkStartPosNot25 = this.playerArr[player.getNumber()].getStartPosition() != 25;
			if(this.playerArr[player.getNumber()].getStartPosition() >= 25) {
				this.playerArr[player.getNumber()].setStartPosition(1);
			}
			boolean checkIfOwned = propertiesarray[this.playerArr[player.getNumber()].getStartPosition()] == null;
			
			
			if(checkIfOnTaxiField && canPlayerAfford) {
				int result = this.gui.getUserInteger(translator.getTranslation("taxifieldtext"), 2,25);
				if(result >=1 && result <=Integer.parseInt(translator.getTranslation("amountoffields"))) { // Taxi field being handled here
					fields[this.playerArr[player.getNumber()].getStartPosition()].setCar(player, false);
					fields[result-1].setCar(player, true);
					this.playerArr[player.getNumber()].getAccount().withdraw(fieldvalue);
					player.setBalance(player.getBalance()-fieldvalue);
					this.playerArr[player.getNumber()].setStartPosition(result-1); // Saves the new position of the Player
				}
			} 
			if(checkIfOnPrisonField && playerNotInPrison){
				this.playerArr[player.getNumber()].setPrison(true); // Player is now in prison and is skipped a turn
				this.gui.displayChanceCard(player.getName() + " "+translator.getTranslation("prisonfieldtext"));
			}
			//Below if for buying property or when you land on someone elses property.
			if(checkStartPosNot25 && checkIfOwned &&  checkIfNotOnPrisonField && checkIfNotOnTaxiField && canPlayerAfford) {
				boolean result = this.gui.getUserLeftButtonPressed(translator.getTranslation("fieldbuytext")+" "+fields[this.playerArr[player.getNumber()].getStartPosition()].getTitle() , translator.getTranslation("yesbutton"), translator.getTranslation("nobutton"));
				if(result) {
					propertiesarray[this.playerArr[player.getNumber()].getStartPosition()] = this.playerArr[player.getNumber()];
					this.playerArr[player.getNumber()].getAccount().withdraw(fieldvalue);
					player.setBalance(player.getBalance()-fieldvalue);
					fields[this.playerArr[player.getNumber()].getStartPosition()].setSubText(this.playerArr[player.getNumber()].getName()+":"+fieldvalue);
					this.gui.displayChanceCard(player.getName() +" "+translator.getTranslation("boughtfieldtext")+" "+ fields[this.playerArr[player.getNumber()].getStartPosition()].getTitle());
					fields[this.playerArr[player.getNumber()].getStartPosition()].setBackGroundColor(player.getPrimaryColor());
					Thread.sleep(2500);
				}
			}else {
				if(checkStartPosNot25 && checkIfNotOnPrisonField && checkIfNotOnTaxiField && canPlayerAfford) {
					this.gui.displayChanceCard(translator.getTranslation("landfieldtextright")+" "+propertiesarray[this.playerArr[player.getNumber()].getStartPosition()].getName()+ " " + translator.getTranslation("landfieldtextleft") + " "+ value);
					player.setBalance(player.getBalance()-fieldvalue);
					this.playerArr[player.getNumber()].getAccount().withdraw(fieldvalue);
					propertiesarray[this.playerArr[player.getNumber()].getStartPosition()].getAccount().deposit(fieldvalue);
					for (int i = 0; i < getPlayerGUIArr().length; i++) {
						if(this.playerGUIArr[i].getName() == propertiesarray[this.playerArr[player.getNumber()].getStartPosition()].getName()) {
							this.playerGUIArr[i].setBalance(this.playerGUIArr[i].getBalance()+fieldvalue);
							break;
						}
					}
					Thread.sleep(2500);
				}

			}
		
		return value;
	}


	/**
	 * @return the playerArr
	 */
	public Player[] getPlayerArr() {
		return this.playerArr;
	}

	/**
	 * @param playerArr the playerArr to set
	 */
	public void setPlayerArr(Player[] playerArr) {
		this.playerArr = playerArr;
	}

	/**
	 * 
	 * @return
	 */
	public GUI_Player[] getPlayerGUIArr() {
		return this.playerGUIArr;
	}

	/**
	 * @param playerArr the playerArr to set
	 */
	public void setPlayerGUIArr(GUI_Player[] playerGUIArr) {
		this.playerGUIArr = playerGUIArr;
	}
}

