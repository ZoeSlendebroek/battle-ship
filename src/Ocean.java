import java.util.ArrayList;
import java.util.Random;

/**
 * This class manages the game state by keeping track of what entity is
 * contained in each position on the game board.

 *
 */


public class Ocean implements OceanInterface {

	/**
	 * A 10x10 2D array of Ships, which can be used to quickly determine which ship
	 * is in any given location.
	 */
	protected Ship[][] ships;

	/**
	 * The total number of shots fired by the user
	 */
	protected int shotsFired;

	/**
	 * The number of times a shot hit a ship. If the user shoots the same part of a
	 * ship more than once, every hit is counted, even though the additional "hits"
	 * don't do the user any good.
	 */
	protected int hitCount;

	/**
	 * The number of ships totally sunk.
	 * 
	 */
	protected int shipsSunk;

	/**
	 * Creates an "empty" ocean, filling every space in the <code>ships</code> array
	 * with EmptySea objects. Should also initialize the other instance variables
	 * appropriately.
	 */
	public Ocean() {
		this.ships = new Ship[10][10];
		this.shotsFired = 0;
		this.hitCount = 0;
		this.shipsSunk = 0;

		// Initialises Ship array with EmptySea objects
		for (int row = 0; row < 10; row++) {
			for (int column = 0; column <10; column++) {
				this.ships[row][column] = new EmptySea();
			}
		}

	}

	/**
	 * Place all ten ships randomly on the (initially empty) ocean. Larger ships
	 * must be placed before smaller ones to avoid cases where it may be impossible
	 * to place the larger ships.
	 * 
	 * @see java.util.Random
	 */
	public void placeAllShipsRandomly() {
		Random rand = new Random();

		Ship[] allShips = {
				new Battleship(),    // Length = 4
				new Cruiser(),       // Length = 3
				new Cruiser(),       // Length = 3
				new Destroyer(),     // Length = 2
				new Destroyer(),     // Length = 2
				new Destroyer(),     // Length = 2
				new Submarine(),     // Length = 1
				new Submarine(),     // Length = 1
				new Submarine(),     // Length = 1
				new Submarine()      // Length = 1
		};

		// Loops through all ships
		for (Ship ship : allShips) {
			boolean canPlace = false; // Helps determine whether ships can be placed or are out of bounds

			while (!canPlace) { // Retry until a valid position is found
				int randomColumn = rand.nextInt(10); // Generating Random Coordinates for the battleship to be placed
				int randomRow = rand.nextInt(10);
				boolean isHorizontal = rand.nextBoolean(); // Generates random boolean to determine layout (horizontal or vertical) of ship

				// Checking bounds
				if (isHorizontal && randomColumn + ship.getLength() > 10) { // if bounds are exceeded
					continue;                // retry and generate new column coordinate
				} else if (!isHorizontal && randomRow + ship.getLength() > 10) { // if bounds are exceeded
					continue;                // retry and generate new Row coordinate
				}

				// Check for overlaps and immediate tiles next to the boat so that they remain free
				boolean overlaps = false;
				if (isHorizontal) {
					for (int i = -1; i <= ship.getLength(); i++) { // Check extra spaces before and after the ship
						for (int j = -1; j <= 1; j++) {           // Check above, below, and current row
							int checkRow = randomRow + j;
							int checkColumn = randomColumn + i;

							if (checkRow >= 0 && checkRow < 10 && checkColumn >= 0 && checkColumn < 10) { // Ensure bounds
								if (!(ships[checkRow][checkColumn] instanceof EmptySea)) {
									overlaps = true;
									break; // retry and generate new coordinates
								}
							}
						}
						if (overlaps) break;
					}
				} else { // Vertical placement
					for (int i = -1; i <= ship.getLength(); i++) { // Check extra spaces above and below the ship
						for (int j = -1; j <= 1; j++) {           // Check left, right, and current column
							int checkRow = randomRow + i;
							int checkColumn = randomColumn + j;

							if (checkRow >= 0 && checkRow < 10 && checkColumn >= 0 && checkColumn < 10) { // Ensure bounds
								if (!(ships[checkRow][checkColumn] instanceof EmptySea)) {
									overlaps = true;
									break; // retry and generate new coordinates
								}
							}
						}
						if (overlaps) break;
					}
				}

				// Place the ship if no problems encountered
				if (!overlaps) {
					canPlace = true;        // if no problems encountered, exit while loop and ship can be placed!
					if (isHorizontal) {
						for (int i = 0; i < ship.getLength(); i++) {
							ships[randomRow][randomColumn + i] = ship; // Place horizontally
						}
					} else {
						for (int i = 0; i < ship.getLength(); i++) {
							ships[randomRow + i][randomColumn] = ship; // Place vertically
						}
					}
				}
			}
		}
	}

	/**
	 * Checks if this coordinate is not empty; that is, if this coordinate does not
	 * contain an EmptySea reference.
	 * 
	 * @param row    the row (0 to 9) in which to check for a floating ship
	 * @param column the column (0 to 9) in which to check for a floating ship
	 * @return {@literal true} if the given location contains a ship, and
	 *         {@literal false} otherwise.
	 */
	public boolean isOccupied(int row, int column) {

		if (!(ships[row][column] instanceof EmptySea)) {
			return true;
		} else {
			return false;
		}
	}


	/**
	 * Fires a shot at this coordinate. This will update the number of shots that
	 * have been fired (and potentially the number of hits, as well). If a location
	 * contains a real, not sunk ship, this method should return {@literal true}
	 * every time the user shoots at that location. If the ship has been sunk,
	 * additional shots at this location should return {@literal false}.
	 * 
	 * @param row    the row (0 to 9) in which to shoot
	 * @param column the column (0 to 9) in which to shoot
	 * @return {@literal true} if the given location contains an afloat ship (not an
	 *         EmptySea), {@literal false} if it does not.
	 */
	public boolean shootAt(int row, int column) {
		this.shotsFired++;
		Ship ship = ships[row][column];

		if (ship instanceof EmptySea) {
			return false; // Miss
		}

		int hitIndex = ship.isHorizontal ? column - ship.column : row - ship.row;
		ship.hits[hitIndex] = true; // Register the hit

		if (ship.isSunk()) {
			this.shipsSunk++; // Increment sunk ships count if the ship is fully sunk
		}

		this.hitCount++;
		return true; // Hit
	}

	/**
	 * @return the number of shots fired in this game.
	 */
	public int getShotsFired() {
		return this.shotsFired;
	}

	/**
	 * @return the number of hits recorded in this game.
	 */
	public int getHitCount() {
		return this.shotsFired;
	}

	/**
	 * @return the number of ships sunk in this game.
	 */
	public int getShipsSunk() {
		return this.shipsSunk;
	}

	/**
	 * @return {@literal true} if all ships have been sunk, otherwise
	 *         {@literal false}.
	 */
	public boolean isGameOver() {
		return shipsSunk == 10; // returns true only if all ships are sunk
	}

	/**
	 * Provides access to the grid of ships in this Ocean. The methods in the Ship
	 * class that take an Ocean parameter must be able to read and even modify the
	 * contents of this array. While it is generally undesirable to allow methods in
	 * one class to directly access instancce variables in another class, in this
	 * case there is no clear and elegant alternatives.
	 * 
	 * @return the 10x10 array of ships.
	 */
	public Ship[][] getShipArray() {
		return this.ships;
	}

	/**
	 * Prints the ocean. To aid the user, row numbers should be displayed along the
	 * left edge of the array, and column numbers should be displayed along the top.
	 * Numbers should be 0 to 9, not 1 to 10. The top left corner square should be
	 * 0, 0.
	 * <ul>
	 * <li>Use 'S' to indicate a location that you have fired upon and hit a (real)
	 * ship</li>
	 * <li>'-' to indicate a location that you have fired upon and found nothing
	 * there</li>
	 * <li>'x' to indicate a location containing a sunken ship</li>
	 * <li>'.' (a period) to indicate a location that you have never fired
	 * upon.</li>
	 * </ul>
	 * 
	 * This is the only method in Ocean that has any printing capability, and it
	 * should never be called from within the Ocean class except for the purposes of
	 * debugging.
	 * 
	 */


	public void print() {
		// Print column headers
		System.out.print("  "); // Top-left corner padding
		for (int column = 0; column < 10; column++) {
			System.out.print(column + " "); // Print column numbers
		}
		System.out.println(); // New line after column headers

		// Print the grid with row numbers
		for (int row = 0; row < 10; row++) {
			System.out.print(row + " "); // Print row number on the left
			for (int column = 0; column < 10; column++) {
				Ship ship = ships[row][column];

				if (ship instanceof EmptySea) {
					System.out.print(". "); // '.' for EmptySea
				} else if (ship.isSunk()) {
					System.out.print("x "); // 'x' for sunken ships
				} else if (ship.getHits()[ship.isHorizontal ? column - ship.getColumn() : row - ship.getRow()]) {
					System.out.print("S "); // 'S' for hit ship parts
				} else {
					System.out.print(". "); // '.' for untouched parts of the grid
				}
			}
			System.out.println(); // Move to the next row
		}
	}

}
