package core;

public class PlayerController {
	private Player playerArr[];
	
	public PlayerController() {
		
	}

	public void initPlayers(int amountOfPlayers) {
		playerArr = new Player[amountOfPlayers];
	}

	public Player[] getPlayers() {
		return playerArr;
	}

	public void setPlayers(Player[] playerArr) {
		this.playerArr = playerArr;
	}
}
