package edu.duke.yh342.battleship;

/**
 * Board Interface with method getWidth()
 * and getHeight()
 */
public interface Board<T> {
  public int getWidth();

  public int getHeight();

  public boolean tryAddShip(Ship<T> toAdd);
  
  public T whatIsAt(Coordinate where);

}
