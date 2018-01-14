package core.ChanceCardLogic;

/**
 * @author Magnus Stjernborg Koch - s175189
 *
 */
public abstract class ChanceCard {
    //Field id, goes easier together with other controllers
    private int id;
    //For GUI purposes
    private String description;

    public ChanceCard(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
