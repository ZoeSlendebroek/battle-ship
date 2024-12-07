// 3. Destroyer
public class Destroyer extends Ship {

    // Constructor
    public Destroyer() {
        super(2);           // Battleship length is 4
    }

    @Override
    public String getShipType() {
        return "Destroyer";
    }
}
