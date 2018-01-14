package core.ChanceCardLogic;

/**
 * @author Magnus Stjernborg Koch - s175189
 *
 */
public class WithdrawCard extends ChanceCard {
    //The amount to withdraw
    private int amount;
    public WithdrawCard(int id, String description, int amount) {
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
