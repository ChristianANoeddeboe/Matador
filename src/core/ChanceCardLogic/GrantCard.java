package core.ChanceCardLogic;

/**
 * @author Magnus Stjernborg Koch - s175189
 *
 */
public class GrantCard extends ChanceCard {
    //What the grant amount is
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
