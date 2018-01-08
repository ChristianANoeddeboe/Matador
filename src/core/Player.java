package core;

/**
 * @author Nicolai Kammersgård <s143780@student.dtu.dk>
 */
public class Player{
	private String name;
	private Account account;
	private boolean prison, bankrupt,startRound;
	private int prisonCard;
	private int guiId;
	private int startPosition;
	private int endPosition;
	private int prisontries;
	
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
		this.bankrupt = false;
		this.startRound = true;
		this.startPosition = 0;
		this.prisonCard = 0;
		this.guiId = idGui;
		this.prisontries = 0;
		this.endPosition = this.startPosition;
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
	public int getGuiId() {
		return guiId;
	}
	public void setGuiId(int id_GUI) {
		this.guiId = id_GUI;
	}
	public int getEndPosition() {
		return endPosition;
	}
	public void setEndPosition(int endPosition) {
		this.endPosition = endPosition;
	}
	public Account getAccount() {
		return this.account;
	}
	
	public void setAccount(Account account) {
		this.account = account;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public int getStartPosition() {
		return this.startPosition;
	}

	public void setStartPosition(int fieldNumber) {
		this.startPosition = fieldNumber;
	}
	
	public void setPrison(boolean prison) {
		this.prison = prison;
	}
	public boolean isPrison() {
		return this.prison;
	}
	
	public void setPrisontries(int prisontries) {
		this.prisontries = prisontries;
	}
	
	public int getPrisontries() {
		return prisontries;
	}
	
	public boolean isBanktrupt() {
		return this.bankrupt;
	}
	
	public void setBanktrupt(boolean bankrupt) {
		this.bankrupt = bankrupt;
	}
	
	public boolean isStartRound() {
		return startRound;
	}
	public void setStartRound(boolean startRound) {
		this.startRound = startRound;
	}
}
