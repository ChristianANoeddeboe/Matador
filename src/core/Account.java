/**
 * 
 */
package core;

/**
 * @author Magnus Stjernborg Koch - s175189
 *
 * This class is an Account object which contains the player's balance
 */
public class Account {

	private int balance;

	/**
	 * Constructor which sets the account value to the default value
	 */
	public Account() {
		this.balance = 30000;
	}

	/**
	 * Getter for the balance
	 * @return the balance
	 */
	public int getBalance() {
		return this.balance;
	}

	/**
	 * Setter for the balance
	 * @param balance
	 */
	public void setBalance(int balance) {
		this.balance = balance;
	}

	/**
	 * Deposit function for balance
	 * @param value
	 */
	public void deposit (int value) {
		//Makes sure that a negative value would not effect the balance
	    if (value >= 0)
	    	this.balance += value;
	}

	/**
	 * Withdraw function for balance
	 * @param value
	 */
	public void withdraw (int value) {
		//Ensure that that a negative value would function
		this.balance -= Math.abs(value);
		//Makes sure that that we do not end up with a negative balance
		if (this.balance < 0)
			this.balance = 0;
	}

	/**
	 *
	 * Checks if the player has the balance required to buy a specific field
	 * @param value
	 * @return boolean
	 */
	public boolean canAfford(int value) {
		if(this.balance - value>= 0) {
			return true;
		}else {
			return false;
		}
	}
}
