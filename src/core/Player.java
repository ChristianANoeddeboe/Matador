package core;

/**
 * @author Nicolai Kammersgård <s143780@student.dtu.dk>
 */
public class Player{
	private String name;
	private Account account;
	private boolean prison;
	private int prisonCard;
	private int id_GUI;
	private int startPosition;
	private int endPosition;
	/**
	 * Constructor
	 */
	public Player() {
		
	}
	/**
	 * Constructor with params
	 * @param name The players name
	 */
	public Player(String name, int idGui) {
		this.name = name;
		this.account = new Account();
		this.prison = false;
		this.startPosition = 0;
		this.prisonCard = 0;
		this.id_GUI = idGui;
	}

	public int getPrisonCard() {
		return prisonCard;
	}
	public void setPrisonCard(int prisonCard) {
		this.prisonCard = prisonCard;
	}
	public void addPrisonCard() {
		this.prisonCard += 1;
	}
	public void removePrisonCard() {
		this.prisonCard -= 1;
	}
	public int getId_GUI() {
		return id_GUI;
	}
	public void setId_GUI(int id_GUI) {
		this.id_GUI = id_GUI;
	}
	public int getEndPosition() {
		return endPosition;
	}
	public void setEndPosition(int endPosition) {
		this.endPosition = endPosition;
	}
	/**
	 * @return the balance of the players account
	 */
	public Account getAccount() {
		return this.account;
	}

	/**
	 * @param balance the balance to set of the given players account
	 */
	public void setAccount(Account account) {
		this.account = account;
	}

	/**
	 * @return the name of the player
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @param sets the name of the player
	 */
	public void setName(String name) {
		this.name = name;
	}
	public int getStartPosition() {
		return this.startPosition;
	}

	/**
	 * @param sets the name of the player
	 */
	public void setStartPosition(int fieldNumber) {
		this.startPosition = fieldNumber;
	}
	
	public void setPrison(boolean prison) {
		this.prison = prison;
	}
	public boolean isPrison() {
		return this.prison;
	}
}
