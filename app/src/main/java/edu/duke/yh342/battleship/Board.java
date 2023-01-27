package edu.duke.yh342.battleship;

/**
 * Board Interface with method getWidth()
 * and getHeight()
 */
public interface Board<T> {
  /**
   * Return the width of the board
   * 
   * @return the width of the board
   */
  public int getWidth();

  /**
   * Return the height of the board
   * 
   * @return the height of the board
   */
  public int getHeight();

  /**
   * Try add a ship to the arraylist
   * 
   * @param toAdd is the ship in type T to be added
   * @return true always
   */
  public boolean tryAddShip(Ship<T> toAdd);

  /**
   * Return what is at the block
   * 
   * @param where the Coordinate type of position, e.g., (x, y)
   * @return what is at the block in type T
   */
  public T whatIsAt(Coordinate where);

}
