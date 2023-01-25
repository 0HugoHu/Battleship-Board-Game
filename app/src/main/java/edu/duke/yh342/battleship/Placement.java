package edu.duke.yh342.battleship;

public class Placement {
  private final Coordinate where;
  private final char orientation;
  public Coordinate getCoordinate() {
    return where;
  }
  public char getOrientation() {
    return orientation;
  }
  public Placement(Coordinate where, char orientation) {
    this.where = where;
    this.orientation = Character.toUpperCase(orientation);
    char[] valid = {'V', 'H', 'L', 'R'};
    int i = 0;
    for (; i < 4; i++) {
      if (this.orientation == valid[i]) break;
    }
    if (i == 4) {
      throw new IllegalArgumentException("Ship's orientation must be one of 'V', 'H', 'L', 'R', but is " + orientation);
    }
  }

  @Override
  public boolean equals(Object o) {
    if (o.getClass().equals(getClass())) {
      Placement c = (Placement) o;
      return orientation == c.getOrientation() && where.equals(c.getCoordinate());
    }
    return false;
  }

  @Override
  public String toString() {
    return where.toString() + orientation + "";
  }
  
  @Override
  public int hashCode() {
    return toString().hashCode();
  }

  public Placement(String descr) {
    if (descr.length() != 3) {
      throw new IllegalArgumentException("Placement's string must be at least length 3 but is " + descr.length());
    }
    descr = descr.toUpperCase();
    if (descr.charAt(0) < 'A' || descr.charAt(0) > 'Z') {
      throw new IllegalArgumentException("Placement's first character must be in range A ~ Z but is " + descr.charAt(0));
    }
    int row = descr.charAt(0) - 'A';
    int column = descr.charAt(1) - '0';
    if (column < 0 || column > 9) {
        throw new IllegalArgumentException("Coordinate's last character must be numerical value but is " + column);
    }
    char orientation = descr.charAt(2);
    char[] valid = {'V', 'H', 'L', 'R'};
    int i = 0;
    for (; i < 4; i++) {
      if (orientation == valid[i]) break;
    }
    if (i == 4) {
      throw new IllegalArgumentException("Ship's orientation must be one of 'V', 'H', 'L', 'R', but is " + orientation);
    }

    this.where = new Coordinate(row, column);
    this.orientation = orientation;
  }
  
}
