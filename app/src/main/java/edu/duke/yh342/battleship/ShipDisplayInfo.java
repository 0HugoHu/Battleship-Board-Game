package edu.duke.yh342.battleship;

/**
 * Ship display interface shows the info for display
 */
public interface ShipDisplayInfo<T> {

    /**
     * get current display character on the board
     *
     * @param where the coordinate to check
     * @param hit   if this point is hit
     * @return T type of the block
     */
    public T getInfo(Coordinate where, boolean hit);
}
