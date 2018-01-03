package core;

/**
 * @author Nicolai Kammersgård <s143780@student.dtu.dk>
 */
public class Player{
	private String name;
	private Account account;
	private boolean prison;
	protected int startPosition;
	/**
	 * Constructor
	 */
	public Player() {
		
	}
	/**
	 * Constructor with params
	 * @param name The players name
	 */
	public Player(String name) {
		this.name = name;
		this.account = new Account();
		this.prison = false;
		this.startPosition = 0;
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
