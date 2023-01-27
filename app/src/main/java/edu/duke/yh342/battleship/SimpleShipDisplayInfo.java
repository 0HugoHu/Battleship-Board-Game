package edu.duke.yh342.battleship;

/**
 * Simple ship display info implements ShipDisplayInfo<T>
 * 
 */
public class SimpleShipDisplayInfo<T> implements ShipDisplayInfo<T> {

  private T myData;
  private T onHit;

  /**
   * Initialize class with the display representation and hit rep.
   * 
   * @param myData display representation of thip
   * @param onHit  hit representation of thip
   */
  public SimpleShipDisplayInfo(T myData, T onHit) {
    this.myData = myData;
    this.onHit = onHit;
  }

  /**
   * Override getInfo to check which char to display
   * 
   * @param where the coordinate to check
   * @param hit   if the block is hit
   * @return T type of the block representation
   */
  @Override
  public T getInfo(Coordinate where, boolean hit) {
    // TODO Auto-generated method stub
    if (hit) {
      return onHit;
    } else {
      return myData;
    }
  }

}
