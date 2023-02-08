package edu.duke.yh342.battleship;

/**
 * This interface represents any type of Ship in our Battleship game. It is
 * generic in typename T, which is the type of information the view needs to
 * display this ship.
 */
public interface Ship<T> {

    /**
     * Check if this ship occupies the given coordinate.
     *
     * @param where is the Coordinate to check if this Ship occupies
     * @return true if where is inside this ship, false if not.
     */
    public boolean occupiesCoordinates(Coordinate where);

    /**
     * Check if this ship has been hit in all of its locations meaning it has been
     * sunk.
     *
     * @return true if this ship has been sunk, false otherwise.
     */
    public boolean isSunk();

    /**
     * Make this ship record that it has been hit at the given coordinate. The
     * specified coordinate must be part of the ship.
     *
     * @param where specifies the coordinates that were hit.
     * @throws IllegalArgumentException if where is not part of the Ship
     */
    public void recordHitAt(Coordinate where);

    /**
     * Check if this ship was hit at the specified coordinates. The coordinates must
     * be part of this Ship.
     *
     * @param where is the coordinates to check.
     * @return true if this ship as hit at the indicated coordinates, and false
     * otherwise.
     * @throws IllegalArgumentException if the coordinates are not part of this
     *                                  ship.
     */
    public boolean wasHitAt(Coordinate where);

    /**
     * Return the view-specific information at the given coordinate. This coordinate
     * must be part of the ship.
     *
     * @param where is the coordinate to return information for
     * @param myShip is to display own perspective
     * @return The view-specific information at that coordinate.
     * @throws IllegalArgumentException if where is not part of the Ship
     */
    public T getDisplayInfoAt(Coordinate where, boolean myShip);

    /**
     * Get the name of this Ship, such as "submarine".
     *
     * @return the name of this ship
     */
    public String getName();

    /**
     * Get all of the Coordinates that this Ship occupies.
     *
     * @return An Iterable with the coordinates that this Ship occupies
     */
    public Iterable<Coordinate> getCoordinates();

    /*
     * Add new coordinate to the ship
     * 
     * @param c is the coordinate to add
     * @param hit is whether the coordinate is hit
     */
    public void addCoordinate(Coordinate c, boolean hit);

    /*
     * Remove all coordinates from the ship
     * 
     */
    public void removeCoordinate();

    /*
     * Get the orientation of the ship
     * 
     * @return the orientation of the ship
     */
    public char getOrientation();
}
