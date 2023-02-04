package edu.duke.yh342.battleship;

/**
 * Board Interface with method getWidth()
 * and getHeight()
 */
public interface Board<T> {
    /**
     * Return the width of the board
     *
     * @return the width of the board
     */
    public int getWidth();

    /**
     * Return the height of the board
     *
     * @return the height of the board
     */
    public int getHeight();

    /**
     * Try add a ship to the arraylist
     *
     * @param toAdd is the ship in type T to be added
     * @return null if no errors arise
     * or String of the error
     */
    public String tryAddShip(Ship<T> toAdd);

    /**
     * Return what is at the block for self only
     *
     * @param where the Coordinate type of position, e.g., (x, y)
     * @return what is at the block in type T
     */
    public T whatIsAtForSelf(Coordinate where);

    /**
     * Return what is at the block for enemy view
     *
     * @param where the Coordinate type of position, e.g., (x, y)
     * @return what is at the block in type T
     */
    public T whatIsAtForEnemy(Coordinate where);

    /**
     * Search for any ship that occupies coordinate c
     *
     * @param c where should be checked
     * @return the ship that has been fired
     */
    public Ship<T> fireAt(Coordinate c);

}
