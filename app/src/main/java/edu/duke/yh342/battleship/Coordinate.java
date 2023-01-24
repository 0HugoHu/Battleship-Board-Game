package edu.duke.yh342.battleship;

public class Coordinate {
  private final int row, column;
  public int getRow() {
    return row;
  }
  public int getColumn() {
    return column;
  }
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

  @Override
  public boolean equals(Object o) {
    if (o.getClass().equals(getClass())) {
      Coordinate c = (Coordinate) o;
      return row == c.row && column == c.column;
    }
    return false;
  }

  @Override
  public String toString() {
    return "("+row+", " + column+")";
  }
  
  @Override
  public int hashCode() {
    return toString().hashCode();
  }

  public Coordinate(String descr) {
    if (descr.length() != 2) {
      throw new IllegalArgumentException("Coordinate's string must be at least length 2 but is " + descr.length());
    }
    descr = descr.toUpperCase();
    if (descr.charAt(0) < 'A' || descr.charAt(0) > 'Z') {
      throw new IllegalArgumentException("Coordinate's first character must be in range A ~ Z but is " + descr.charAt(0));
    }
    this.row = descr.charAt(0) - 'A';
    int column = descr.charAt(1) - '0';
    if (column < 0 || column > 9) {
        throw new IllegalArgumentException("Coordinate's last character must be numerical value but is " + column);
    }
    this.column = column;
  }

}
