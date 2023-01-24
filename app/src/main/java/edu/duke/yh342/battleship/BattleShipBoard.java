package edu.duke.yh342.battleship;

/**
 * Create a battleship board implements
 */
public class BattleShipBoard implements Board{
  private final int width;
  /**
   * Return the width of the board
   * @return the width of the board
   */
  public int getWidth() {
    return width;
  }
  private final int height;
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
  }
}
