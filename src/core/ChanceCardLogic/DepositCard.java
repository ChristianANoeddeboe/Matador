package core.ChanceCardLogic;

/**
 * @author Magnus Stjernborg Koch - s175189
 *
 */
public class DepositCard extends ChanceCard {
    //Amount of money to deposit
    private int amount;
    public DepositCard(int id, String description, int amount) {
        super(id, description);
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
