// My Abstract Class Ship, serving as the blueprint for all ship objects

public abstract class Ship {
    protected int length;           // Length of Ship
    protected int row;              // Starting Row
    protected int column;           // Starting column
    protected boolean isHorizontal;       // Determines whether ship is horizontal or vertical
    protected boolean[] hits;       // Tracks where a ship is hit



    // Constructors
    protected Ship(int length) {
        this.length = length;
        this.hits = new boolean[length];
    }

    // Getters for Ships
    public int getLength(){
        return length;
    }

    public int getRow(){
        return row;
    }

    public int getColumn(){
        return column;
    }

    public boolean getLayout(){
        return isHorizontal;
    }

    public boolean[] getHits(){
        return hits;
    }


    // Abstract Methods to be implemented by each subclass
    public abstract String getShipType();

    // Checking if the ship is already sunk
    public boolean isSunk() {
        for (boolean partHit : hits) {
            if (!partHit) {
                return false;   // If any part is not hit, the ship is not sunk
            }
        }
        return true;            // All parts are hit, the ship is sunk
    }


}

