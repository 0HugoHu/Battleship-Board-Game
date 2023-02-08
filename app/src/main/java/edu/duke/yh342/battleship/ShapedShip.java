package edu.duke.yh342.battleship;

import java.util.HashSet;

/**
 * A rectangular ship implements basic ship interface
 */
public class ShapedShip<T> extends BasicShip<T> {

    final String name;
    final char orientation;

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
     * @param upperLeft start coordinate of the ship
     * @param width     width of the ship
     * @param height    height of the ship
     * @return HashSet of all coordinates the ship occupies
     */
    static HashSet<Coordinate> makeCoords(Coordinate upperLeft, int width, int height, String name) {
        HashSet<Coordinate> res = new HashSet<>();

        if (height == 0 && (name.equals("Battleship") || name.equals("Carrier"))) {
            int baseRow = upperLeft.getRow();
            int baseCol = upperLeft.getColumn();

            if (name.equals("Battleship")) {
                switch (width + 'A') {
                    /*
                         b      OR    b         bbb         b
                        bbb           bb   OR    b     OR  bb
                                      b                     b
                     */
                    case 'U':
                        // I know this is not the best way, but it can avoid mistakes
                        res.add(new Coordinate(baseRow, baseCol + 1));
                        res.add(new Coordinate(baseRow + 1, baseCol));
                        res.add(new Coordinate(baseRow + 1, baseCol + 1));
                        res.add(new Coordinate(baseRow + 1, baseCol + 2));
                        break;
                    case 'R':
                        res.add(new Coordinate(baseRow, baseCol));
                        res.add(new Coordinate(baseRow + 1, baseCol));
                        res.add(new Coordinate(baseRow + 1, baseCol + 1));
                        res.add(new Coordinate(baseRow + 2, baseCol));
                        break;
                    case 'D':
                        res.add(new Coordinate(baseRow, baseCol));
                        res.add(new Coordinate(baseRow, baseCol + 1));
                        res.add(new Coordinate(baseRow, baseCol + 2));
                        res.add(new Coordinate(baseRow + 1, baseCol + 1));
                        break;
                    case 'L':
                        res.add(new Coordinate(baseRow, baseCol + 1));
                        res.add(new Coordinate(baseRow + 1, baseCol));
                        res.add(new Coordinate(baseRow + 1, baseCol + 1));
                        res.add(new Coordinate(baseRow + 2, baseCol + 1));
                        break;
                    default:
                        throw new IllegalArgumentException("Unexpected error happened in ShapedShip.makeCoords()!");
                }
            } else {
                switch (width + 'A') {
                    /*
                        c                       c             
                        c           cccc        cc         ccc
                        cc   OR    ccc      OR  cc   OR  cccc     
                        cc                       c         
                         c                       c
                     */
                    case 'U':
                        res.add(new Coordinate(baseRow, baseCol));
                        res.add(new Coordinate(baseRow + 1, baseCol));
                        res.add(new Coordinate(baseRow + 2, baseCol));
                        res.add(new Coordinate(baseRow + 2, baseCol + 1));
                        res.add(new Coordinate(baseRow + 3, baseCol));
                        res.add(new Coordinate(baseRow + 3, baseCol + 1));
                        res.add(new Coordinate(baseRow + 4, baseCol + 1));
                        break;
                    case 'R':
                        res.add(new Coordinate(baseRow, baseCol + 1));
                        res.add(new Coordinate(baseRow, baseCol + 2));
                        res.add(new Coordinate(baseRow, baseCol + 3));
                        res.add(new Coordinate(baseRow, baseCol + 4));
                        res.add(new Coordinate(baseRow + 1, baseCol));
                        res.add(new Coordinate(baseRow + 1, baseCol + 1));
                        res.add(new Coordinate(baseRow + 1, baseCol + 2));
                        break;
                    case 'D':
                        res.add(new Coordinate(baseRow, baseCol));
                        res.add(new Coordinate(baseRow + 1, baseCol));
                        res.add(new Coordinate(baseRow + 1, baseCol + 1));
                        res.add(new Coordinate(baseRow + 2, baseCol));
                        res.add(new Coordinate(baseRow + 2, baseCol + 1));
                        res.add(new Coordinate(baseRow + 3, baseCol + 1));
                        res.add(new Coordinate(baseRow + 4, baseCol + 1));
                        break;
                    case 'L':
                        res.add(new Coordinate(baseRow, baseCol + 2));
                        res.add(new Coordinate(baseRow, baseCol + 3));
                        res.add(new Coordinate(baseRow, baseCol + 4));
                        res.add(new Coordinate(baseRow + 1, baseCol));
                        res.add(new Coordinate(baseRow + 1, baseCol + 1));
                        res.add(new Coordinate(baseRow + 1, baseCol + 2));
                        res.add(new Coordinate(baseRow + 1, baseCol + 3));
                        break;
                    default:
                        throw new IllegalArgumentException("Unexpected error happened in ShapedShip.makeCoords()!");
                }
            }
            return res;
        }

        // In other cases, height and width should never be smaller than 0
        if (width <= 0) {
            throw new IllegalArgumentException("Ship's occupy space must be at least 1 x 1, but its width is " + width);
        }
        if (height <= 0) {
            throw new IllegalArgumentException("Ship's occupy space must be at least 1 x 1, but its height is " + height);
        }

        
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
     * @param upperLeft        start position of the ship
     * @param width            width of the ship
     * @param height           height of the ship
     * @param myDisplayInfo    contains the data and onhit representation
     * @param enemyDisplayInfo contains the data and onhit representation
     */
    public ShapedShip(String name, Coordinate upperLeft, int width, int height, ShipDisplayInfo<T> myDisplayInfo, ShipDisplayInfo<T> enemyDisplayInfo) {
        super(makeCoords(upperLeft, width, height, name), myDisplayInfo, enemyDisplayInfo);
        this.name = name;
        this.orientation = (char) (width + 'A');
    }

    /**
     * Initialize the rectancle ship with its name, position and display info
     * This constructor is used for test case where width and height are both 1 only
     *
     * @param name      of the ship
     * @param upperLeft start position of the ship
     * @param width     width of the ship
     * @param height    height of the ship
     * @param data      representation on the block
     * @param onHit     hit representation on the block
     */
    public ShapedShip(String name, Coordinate upperLeft, int width, int height, T data, T onHit) {
        this(name, upperLeft, width, height, new SimpleShipDisplayInfo<T>(data, onHit), new SimpleShipDisplayInfo<T>(null, data));
    }

    /**
     * Initialize the rectancle ship with its name, position and display info with test case
     * where width and height are both 1 and name is "testship"
     * This constructor is used for test case where width and height are both 1 only
     *
     * @param upperLeft start position of the ship
     * @param data      representation on the block
     * @param onHit     hit representation on the block
     */
    public ShapedShip(Coordinate upperLeft, T data, T onHit) {
        this("testship", upperLeft, 1, 1, data, onHit);
    }

    /*
     * Get the orientation of the ship
     * 
     * @return the orientation of the ship
     */
    public char getOrientation() {
        return this.orientation;
    }

}
