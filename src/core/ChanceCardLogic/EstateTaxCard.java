package core.ChanceCardLogic;

/**
 * Created by magnus
 */
public class EstateTaxCard extends ChanceCard {
    private int taxHouse;
    private int taxHotel;
    public EstateTaxCard(int id, String description, int taxHouse, int taxHotel) {
        super(id, description);
        this.taxHouse = taxHouse;
        this.taxHotel = taxHotel;
    }

    public int getTaxHouse() {
        return taxHouse;
    }

    public void setTaxHouse(int taxHouse) {
        this.taxHouse = taxHouse;
    }

    public int getTaxHotel() {
        return taxHotel;
    }

    public void setTaxHotel(int taxHotel) {
        this.taxHotel = taxHotel;
    }
}
