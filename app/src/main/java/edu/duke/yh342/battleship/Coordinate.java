package edu.duke.yh342.battleship;

/**
 * Create a coordinate based on row and column
 */
public class Coordinate {
    private final int row, column;

    /**
     * Return the row of the coordinate
     *
     * @return the row of the coordinate
     */
    public int getRow() {
        return row;
    }

    /**
     * Return the column of the coordinate
     *
     * @return the column of the coordinate
     */
    public int getColumn() {
        return column;
    }

    /**
     * Return coordinate based on row and column
     *
     * @param row    the height
     * @param column the width
     * @throws IllegalArgumentException if row or column is smaller than 0
     */
    public Coordinate(int row, int column) {
        if (row < 0) {
            throw new IllegalArgumentException("Coordinate's row must be positive but is " + row);
        }
        if (column < 0) {
            throw new IllegalArgumentException("Coordinate's column must be positive but is " + column);
        }
        this.row = row;
        this.column = column;
    }

    /**
     * Return coordinate based on the string
     *
     * @param descr string that refers to a coordinate
     * @throws IllegalArgumentException if the string is invalid with format. Valid:
     *                                  e.g., "A0"
     */
    public Coordinate(String descr) {
        if (descr.length() != 2) {
            throw new IllegalArgumentException("Coordinate's string must be at least length 2 but is " + descr.length());
        }

        // Case-insensitive
        descr = descr.toUpperCase();

        // Check for first char in A - Z
        if (descr.charAt(0) < 'A' || descr.charAt(0) > 'Z') {
            throw new IllegalArgumentException(
                    "Coordinate's first character must be in range A ~ Z but is " + descr.charAt(0));
        }
        this.row = descr.charAt(0) - 'A';

        int column = descr.charAt(1) - '0';
        // Check for second char in 0 - 9
        if (column < 0 || column > 9) {
            throw new IllegalArgumentException("Coordinate's last character must be numerical value but is " + column);
        }

        this.column = column;
    }

    /**
     * Compare if two coordinate are equal
     *
     * @param o Object to be compared
     * @return if they are equal
     */
    @Override
    public boolean equals(Object o) {
        // Compare if the class name is the same
        if (o.getClass().equals(getClass())) {
            Coordinate c = (Coordinate) o;
            return row == c.row && column == c.column;
        }
        return false;
    }

    /**
     * Return string of the coordinate
     *
     * @return string of the coordinate
     */
    @Override
    public String toString() {
        return "(" + row + ", " + column + ")";
    }

    /**
     * Return hashcode of the coordinate
     *
     * @return hashcode of the coordinate
     */
    @Override
    public int hashCode() {
        return toString().hashCode();
    }

}
