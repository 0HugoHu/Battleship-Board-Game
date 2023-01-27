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
    RectangleShip r = new RectangleShip(c, 's', '*');
    assertEquals(true, r.occupiesCoordinates(c));
    assertEquals(false, r.occupiesCoordinates(new Coordinate(11, 20)));
    assertEquals(false, r.occupiesCoordinates(new Coordinate(10, 21)));
    assertEquals(false, r.occupiesCoordinates(new Coordinate(9, 20)));
    assertEquals(false, r.occupiesCoordinates(new Coordinate(10, 19)));
  }

  @Test
  public void test_hit() {
    Coordinate c1 = new Coordinate(10, 20);
    Coordinate c2 = new Coordinate(9, 20);
    Coordinate c3 = new Coordinate(10, 21);
    RectangleShip r = new RectangleShip(c1, 's', '*');
    assertThrows(IllegalArgumentException.class, () -> r.recordHitAt(c2));
    assertThrows(IllegalArgumentException.class, () -> r.recordHitAt(c3));
    r.recordHitAt(c1);
    assertThrows(IllegalArgumentException.class, () -> r.wasHitAt(c2));
    assertThrows(IllegalArgumentException.class, () -> r.wasHitAt(c3));
    assertEquals(true, r.wasHitAt(c1));
  }

  @Test
  public void test_sunk() {
    Coordinate c1 = new Coordinate(10, 20);
    Coordinate c2 = new Coordinate(10, 21);
    RectangleShip r = new RectangleShip("testship", c1, 2, 1, 's', '*');
    assertEquals(false, r.isSunk());
    r.recordHitAt(c1);
    assertEquals(false, r.isSunk());
    r.recordHitAt(c2);
    assertEquals(true, r.isSunk());
  }

  @Test
  public void test_display_info() {
    Coordinate c1 = new Coordinate(10, 20);
    Coordinate c2 = new Coordinate(10, 21);
    Coordinate c3 = new Coordinate(11, 21);
    RectangleShip<Character> r = new RectangleShip<>("testship", c1, 2, 1 , 's', '*');
    assertThrows(IllegalArgumentException.class, () -> r.getDisplayInfoAt(c3));
    r.recordHitAt(c1);
    assertEquals(r.getDisplayInfoAt(c1), '*');
    assertEquals(r.getDisplayInfoAt(c2), 's');
  }

  @Test
  public void test_get_name() {
    Coordinate c1 = new Coordinate(10, 20);
    RectangleShip<Character> r = new RectangleShip<>(c1, 's', '*');
    assertEquals(r.getName(), "testship");
  }

}
