package core.ChanceCardLogic;

/**
 * @author Magnus Stjernborg Koch - s175189
 *
 */
public class StepsBackCard extends ChanceCard{
    //The amount of steps going back
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
