package core.ChanceCardLogic;

/**
 * Created by magnus
 */
public class DepositCard extends ChanceCard {
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
