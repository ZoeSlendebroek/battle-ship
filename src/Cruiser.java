// 2. Cruiser
public class Cruiser extends Ship {

    // Constructor
    public Cruiser() {
        super(3);           // Battleship length is 4
    }

    @Override
    public String getShipType() {
        return "Cruiser";
    }
}
