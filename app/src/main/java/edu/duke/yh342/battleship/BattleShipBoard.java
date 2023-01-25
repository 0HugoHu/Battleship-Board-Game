package edu.duke.yh342.battleship;

import java.util.ArrayList;

/**
 * Create a battleship board implements
 */
public class BattleShipBoard<T> implements Board<T>{
  private final int width;
  private final int height;
  private final ArrayList<Ship<T>> myShips;

  /**
   * Return the width of the board
   * @return the width of the board
   */
  public int getWidth() {
    return width;
  }
  /**
   * Return the height of the board
   * @return the height of the board
   */
  public int getHeight() {
    return height;
  }
  /**
   * Constructs a BattleShipBoard with the specified width
   * and height
   * @param w is the width of the newly constructed board.
   * @param h is the height of the newly constructed board.
   * @throws IllegalArgumentException if the width or height are less than or equal to zero.
   */
  public BattleShipBoard(int w, int h) {
    if (w <= 0) {
      throw new IllegalArgumentException("BattleShipBoard's width must be positive but is " + w);
    }
    if (h <= 0) {
      throw new IllegalArgumentException("BattleShipBoard's height must be positive but is " + h);
    }
    this.width = w;
    this.height = h;
    this.myShips = new ArrayList<Ship<T>>();
  }

  public boolean tryAddShip(Ship<T> toAdd) {
    myShips.add(toAdd);
    return true;
  }

  public T whatIsAt(Coordinate where) {
    if (where.getRow() >= this.height) {
      throw new IllegalArgumentException("Input coordinate height must in the range but height is " + where.getRow());
    }
    if (where.getColumn() >= this.width) {
      throw new IllegalArgumentException("Input coordinate width must in the range but width is " + where.getColumn());
    }
    for (Ship<T> s: myShips) {
      if (s.occupiesCoordinates(where)){
        return s.getDisplayInfoAt(where);
      }
    }
    return null;
  }
}
