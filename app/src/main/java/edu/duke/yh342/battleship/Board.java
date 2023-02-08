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

    /**
     * Check if all ships are sunk
     *
     * @return true if all ships are sunk
     */
    public boolean allShipsSunk();

    /**
     * Return the ship at the coordinate
     *
     * @param where the coordinate to be checked
     * @return the ship at the coordinate
     */
    public Ship<T> getShipAt(Coordinate where);

    /**
     * Move a ship to a new coordinate
     *
     * @param toMove the ship to be moved
     * @param to     the new coordinate
     * @param ownBoard the board the ship is on
     * @return true if the ship is moved
     */
    public boolean moveShipTo(Ship<Character> toMove, Placement to, Board<Character> ownBoard);


    /**
     * Transfer hit points from one ship to another
     *
     * @param from the ship to transfer hit points from
     * @param to   the ship to transfer hit points to
     */
    public void transferHitPoints(Ship<Character> from, Ship<Character> to);

    /**
     * Remove a ship from the board
     *
     * @param toRemove the ship to be removed
     */
    public void removeShip(Ship<T> toRemove);

    /**
     * Get the number of tiles the enemy ship occupies on the board
     *
     * @param c the coordinate to scan
     * @param enemyBoard the enemy board
     * @return the number of ships on the board
     */
    public int[] sonarScan(Coordinate c, Board<Character> enemyBoard);

}
