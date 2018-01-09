package core.ChanceCardLogic;

/**
 * Created by magnus
 */
public class StepsBackCard extends ChanceCard{
    private int amount;
    public StepsBackCard(int id, String description, int amount) {
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
