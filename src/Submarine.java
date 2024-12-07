// 4. Submarine
public class Submarine extends Ship {

    // Constructor
    public Submarine() {
        super(1);           // Battleship length is 4
    }

    @Override
    public String getShipType() {
        return "Submarine";
    }
}
