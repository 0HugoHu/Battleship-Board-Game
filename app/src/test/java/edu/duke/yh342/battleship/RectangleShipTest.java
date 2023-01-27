package edu.duke.yh342.battleship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

public class RectangleShipTest {
  @Test
  public void test_make_coords() {
    Coordinate c = new Coordinate(10, 20);
    assertThrows(IllegalArgumentException.class, () -> RectangleShip.makeCoords(c, 0, 1));
    assertThrows(IllegalArgumentException.class, () -> RectangleShip.makeCoords(c, 5, 0));

    HashSet<Coordinate> coord = RectangleShip.makeCoords(c, 1, 3);
    assertEquals(true, coord.contains(new Coordinate(10, 20)));
    assertEquals(true, coord.contains(new Coordinate(11, 20)));
    assertEquals(true, coord.contains(new Coordinate(12, 20)));
    assertEquals(false, coord.contains(new Coordinate(9, 20)));
    assertEquals(false, coord.contains(new Coordinate(13, 20)));
    assertEquals(false, coord.contains(new Coordinate(10, 19)));
    assertEquals(false, coord.contains(new Coordinate(10, 21)));
  }

  @Test
  public void test_rectangle_ship() {
    Coordinate c = new Coordinate(10, 20);
    RectangleShip r = new RectangleShip(c, 1, 1);
    assertEquals(true, r.occupiesCoordinates(c));
    assertEquals(false, r.occupiesCoordinates(new Coordinate(11, 20)));
    assertEquals(false, r.occupiesCoordinates(new Coordinate(10, 21)));
    assertEquals(false, r.occupiesCoordinates(new Coordinate(9, 20)));
    assertEquals(false, r.occupiesCoordinates(new Coordinate(10, 19)));
  }

}
