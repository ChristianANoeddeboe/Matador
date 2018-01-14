package core.ChanceCardLogic;

/**
 * @author Magnus Stjernborg Koch - s175189
 *
 */
public class MoveCard extends ChanceCard {
    //Specific field to place the player on
    private int field;
    public MoveCard(int id, String text, int field) {
        super(id, text);
        this.field = field;
    }

    public int getField() {
        return field;
    }

    public void setField(int field) {
        this.field = field;
    }
}
