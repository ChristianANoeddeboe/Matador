package core.ChanceCardLogic;

/**
 * Created by magnus
 */
public class GrantCard extends ChanceCard {
    private int grantAmount;
    public GrantCard(int id, String description) {
        super(id, description);
        this.grantAmount = 40000;
    }

    public int getGrantAmount() {
        return grantAmount;
    }

    public void setGrantAmount(int grantAmount) {
        this.grantAmount = grantAmount;
    }
}
