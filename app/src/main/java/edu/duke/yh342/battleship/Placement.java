package edu.duke.yh342.battleship;

/**
 * Place a ship onto the board
 */
public class Placement {
    private final Coordinate where;
    private final char orientation;

    /**
     * Return the coordinate
     *
     * @return the coordinate
     */
    public Coordinate getCoordinate() {
        return where;
    }

    /**
     * Return the orientation of the ship
     *
     * @return the orientation of the ship
     */
    public char getOrientation() {
        return orientation;
    }

    /**
     * Set a placement on the board based on coordinate and orientation
     *
     * @param where       the coordinate of the ship
     * @param orientation the orientation of the ship
     * @throws IllegalArgumentException if the orientation is invalid
     */
    public Placement(Coordinate where, char orientation) {
        this.where = where;
        this.orientation = Character.toUpperCase(orientation);

        // Valid for version 1
        // char[] valid = {'V', 'H'};
        // Now updated to version 2
        char[] valid = {'V', 'H', 'U', 'R', 'D', 'L'};
        int i = 0;
        for (; i < valid.length; i++) {
            if (this.orientation == valid[i])
                break;
        }

        if (i == valid.length) {
            throw new IllegalArgumentException("Ship's orientation must be one of 'V', 'H', 'U', 'R', 'D', 'L' but is " + orientation);
        }
    }

    /**
     * Set a placement on the board based on string description
     *
     * @param descr the string description of the ship
     * @throws IllegalArgumentException if the string is invalid. Valid: e.g., "A0H"
     */
    public Placement(String descr) {
        // Check string length
        if (descr.length() != 3) {
            throw new IllegalArgumentException("Placement's string must be at least length 3 but is " + descr.length());
        }

        descr = descr.toUpperCase();

        // Check the first char
        if (descr.charAt(0) < 'A' || descr.charAt(0) > 'Z') {
            throw new IllegalArgumentException(
                    "Placement's first character must be in range A ~ Z but is " + descr.charAt(0));
        }
        int row = descr.charAt(0) - 'A';

        int column = descr.charAt(1) - '0';
        // Check the second char
        if (column < 0 || column > 9) {
            throw new IllegalArgumentException("Coordinate's last character must be numerical value but is " + column);
        }
        char orientation = descr.charAt(2);
        // char[] valid = {'V', 'H'};
        // Now updated to version 2
        char[] valid = {'V', 'H', 'U', 'R', 'D', 'L'};
        int i = 0;
        for (; i < valid.length; i++) {
            if (orientation == valid[i])
                break;
        }
        if (i == valid.length) {
            throw new IllegalArgumentException("Ship's orientation must be one of 'V', 'H', but is " + orientation);
        }

        this.where = new Coordinate(row, column);
        this.orientation = orientation;
    }

    /**
     * Compare if two placement are equal
     *
     * @param o Object to be compared
     * @return if they are equal
     */
    @Override
    public boolean equals(Object o) {
        if (o.getClass().equals(getClass())) {
            Placement c = (Placement) o;
            return orientation == c.getOrientation() && where.equals(c.getCoordinate());
        }
        return false;
    }

    /**
     * Return string of the placement
     *
     * @return string of the placement
     */
    @Override
    public String toString() {
        return where.toString() + orientation + "";
    }

    /**
     * Return hashcode of the placement
     *
     * @return hashcode of the placement
     */
    @Override
    public int hashCode() {
        return toString().hashCode();
    }

}
