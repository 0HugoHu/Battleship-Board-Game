package edu.duke.yh342.battleship;

import java.util.HashSet;

/**
 * A rectangular ship implements basic ship interface
 */
public class RectangleShip<T> extends BasicShip<T> {

    final String name;
    char orientation;

    /**
     * Return the name of the ship
     *
     * @return the name of the ship
     */
    public String getName() {
        return name;
    }

    /**
     * Make coordinates based on the ship shape and store them in HashSet
     *
     * @param upperLeft start coordinate of the ship with orientation
     * @param width     width of the ship
     * @param height    height of the ship
     * @return HashSet of all coordinates the ship occupies
     */
    static HashSet<Coordinate> makeCoords(Placement placement, int width, int height) {
        Coordinate upperLeft = placement.getCoordinate();
        if (width <= 0) {
            throw new IllegalArgumentException("Ship's occupy space must be at least 1 x 1, but its width is " + width);
        }
        if (height <= 0) {
            throw new IllegalArgumentException("Ship's occupy space must be at least 1 x 1, but its height is " + height);
        }

        HashSet<Coordinate> res = new HashSet<>();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                res.add(new Coordinate(upperLeft.getRow() + i, upperLeft.getColumn() + j));
            }
        }

        return res;
    }

    /**
     * Initialize the rectancle ship with its name, position and display info
     *
     * @param name             of the ship
     * @param upperLeft        start position of the ship with orientation
     * @param width            width of the ship
     * @param height           height of the ship
     * @param myDisplayInfo    contains the data and onhit representation
     * @param enemyDisplayInfo contains the data and onhit representation
     */
    public RectangleShip(String name, Placement upperLeft, int width, int height, ShipDisplayInfo<T> myDisplayInfo, ShipDisplayInfo<T> enemyDisplayInfo) {
        super(makeCoords(upperLeft, width, height), myDisplayInfo, enemyDisplayInfo);
        this.name = name;
        this.orientation = upperLeft.getOrientation();
    }

    /**
     * Initialize the rectancle ship with its name, position and display info
     *
     * @param name      of the ship
     * @param upperLeft start position of the ship with orientation
     * @param width     width of the ship
     * @param height    height of the ship
     * @param data      representation on the block
     * @param onHit     hit representation on the block
     */
    public RectangleShip(String name, Placement upperLeft, int width, int height, T data, T onHit) {
        this(name, upperLeft, width, height, new SimpleShipDisplayInfo<T>(data, onHit), new SimpleShipDisplayInfo<T>(null, data));
    }

    /**
     * Initialize the rectancle ship with its name, position and display info with test case
     * where width and height are both 1 and name is "testship"
     *
     * @param upperLeft start position of the ship
     * @param data      representation on the block
     * @param onHit     hit representation on the block
     */
    public RectangleShip(Coordinate upperLeft, T data, T onHit) {
        this("testship", new Placement(upperLeft, 'H'), 1, 1, data, onHit);
    }

    /*
     * Get the orientation of the ship
     *
     * @return the orientation of the ship
     */
    public char getOrientation() {
        return this.orientation;
    }

    /*
     * Set the orientation of the ship after orientation
     *
     * @param c is the orientation to set
     */
    public void setOrientation(char c) {
        this.orientation = c;
    }

}
