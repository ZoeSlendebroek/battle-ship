// 1. Battleship
public class Battleship extends Ship {

    // Constructor
    public Battleship() {
        super(4);           // Battleship length is 4
    }

    @Override
    public String getShipType() {
        return "Battleship";
    }
}
