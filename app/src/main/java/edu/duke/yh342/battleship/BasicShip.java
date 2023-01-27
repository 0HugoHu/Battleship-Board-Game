package edu.duke.yh342.battleship;

import java.util.*;

/**
 * A basic ship implements ship interface
 */
public abstract class BasicShip<T> implements Ship<T> {
  protected HashMap<Coordinate, Boolean> myPieces;
  protected ShipDisplayInfo<T> myDisplayInfo;

  /**
   * Initialize basic ship with coordinate on where
   * 
   * @param where         the coordinate list of the ship
   * @param myDisplayInfo if the coordinate is hit or not
   */
  public BasicShip(Iterable<Coordinate> where, ShipDisplayInfo<T> myDisplayInfo) {
    myPieces = new HashMap<Coordinate, Boolean>();
    for (Coordinate c : where) {
      myPieces.put(c, false);
    }
    this.myDisplayInfo = myDisplayInfo;
  }

  /**
   * Check if this ship occupies the given coordinate.
   * 
   * @param where is the Coordinate to check if this Ship occupies
   * @return true if where is inside this ship, false if not.
   */
  @Override
  public boolean occupiesCoordinates(Coordinate where) {
    Iterator whereIterator = myPieces.entrySet().iterator();
    while (whereIterator.hasNext()) {
      Map.Entry mapElement = (Map.Entry) whereIterator.next();
      if (mapElement.getKey().equals(where))
        return true;
    }
    return false;
  }

  /**
   * Check if this ship has been hit in all of its locations meaning it has been
   * sunk.
   * 
   * @return true if this ship has been sunk, false otherwise.
   */
  @Override
  public boolean isSunk() {
    for (Coordinate c : myPieces.keySet()) {
      if (!myPieces.get(c)) {
        return false;
      }
    }
    return true;
  }

  /**
   * Make this ship record that it has been hit at the given coordinate. The
   * specified coordinate must be part of the ship.
   * 
   * @param where specifies the coordinates that were hit.
   * @throws IllegalArgumentException if where is not part of the Ship
   */
  @Override
  public void recordHitAt(Coordinate where) {
    checkCoordinateInThisShip(where);
    myPieces.put(where, true);
  }

  /**
   * Check if this ship was hit at the specified coordinates. The coordinates must
   * be part of this Ship.
   * 
   * @param where is the coordinates to check.
   * @return true if this ship as hit at the indicated coordinates, and false
   *         otherwise.
   * @throws IllegalArgumentException if the coordinates are not part of this
   *                                  ship.
   */
  @Override
  public boolean wasHitAt(Coordinate where) {
    checkCoordinateInThisShip(where);
    return myPieces.get(where);
  }

  /**
   * Return the view-specific information at the given coordinate. This coordinate
   * must be part of the ship.
   * 
   * @param where is the coordinate to return information for
   * @throws IllegalArgumentException if where is not part of the Ship
   * @return The view-specific information at that coordinate.
   */
  @Override
  public T getDisplayInfoAt(Coordinate where) {
    // look up the hit status of this coordinate
    checkCoordinateInThisShip(where);
    return myDisplayInfo.getInfo(where, wasHitAt(where));
  }

  /**
   * Check if the coordinate is inside a ship
   * 
   * @param c is the coordinate to check
   * @throws IllegalArgumentException if where is not part of the Ship
   */
  protected void checkCoordinateInThisShip(Coordinate c) {
    if (!myPieces.containsKey(c)) {
      throw new IllegalArgumentException("The coordinate is not in the range of ship occupies.");
    }
  }

}
