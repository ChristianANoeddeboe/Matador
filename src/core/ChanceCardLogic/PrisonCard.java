package core.ChanceCardLogic;

/**
 * Created by magnus
 */
public class PrisonCard extends ChanceCard {
    private static int counter;
    public PrisonCard(int id, String text) {
        super(id, text);
        counter = 2;
    }

    public void addPrisonCard () {
        counter += 1;
    }

    public void removePrisonCard () {
        counter -= 1;
    }

    public boolean isPrisonCard () {
        if (counter == 0) {
            return false;
        }
        else {
            return true;
        }
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }
}
