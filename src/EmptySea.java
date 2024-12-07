// 5. EmptySea
public class EmptySea extends Ship {

    // Constructor
    public EmptySea () {
        super(1);
    }

    @Override
    public String getShipType() {
        return "EmptySea ";
    }
    
    @Override
    public boolean getLayout() {
        return false; // no need
    }

}
