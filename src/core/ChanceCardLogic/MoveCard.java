package core.ChanceCardLogic;

/**
 * Created by magnus
 */
public class MoveCard extends ChanceCard {
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
