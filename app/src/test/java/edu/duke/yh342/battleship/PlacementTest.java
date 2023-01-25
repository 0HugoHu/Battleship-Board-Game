package edu.duke.yh342.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class PlacementTest {
  @Test
  public void test_get_coordinate_and_orientation() {
    Placement p1 = new Placement(new Coordinate(10, 20), 'V');
    Placement p2 = new Placement(new Coordinate(10, 20), 'v');
    assertThrows(IllegalArgumentException.class, () -> new Placement(new Coordinate(10, 20), 'x'));
    assertEquals(new Coordinate(10, 20), p1.getCoordinate());
    assertEquals('V', p1.getOrientation());
    assertEquals('V', p2.getOrientation());
    
  }

  @Test
  public void test_equals() {
    Placement p1 = new Placement(new Coordinate(10, 20), 'V');
    Placement p2 = new Placement(new Coordinate(10, 20), 'V');
    Placement p3 = new Placement(new Coordinate(10, 20), 'v');
    Placement p4 = new Placement(new Coordinate(10, 20), 'h');
    Placement p5 = new Placement(new Coordinate(9, 21), 'V');
    Placement p6 = new Placement(new Coordinate(20, 10), 'V');
    assertEquals(p1, p2);  
    assertEquals(p1, p3); 
    assertNotEquals(p1, p4);  
    assertNotEquals(p1, p5);
    assertNotEquals(p1, p6);
    assertNotEquals(p1, "(10, 20)V"); 
  }

  @Test
  public void test_to_string() {
    Placement p1 = new Placement(new Coordinate(10, 20), 'V');
    Placement p2 = new Placement(new Coordinate(20, 10), 'V');
    Placement p3 = new Placement(new Coordinate(10, 20), 'V');
    assertEquals(p1.toString(), p3.toString());
    assertNotEquals(p1.toString(), p2.toString());
    assertNotEquals(p2.toString(), p3.toString());
  }

  @Test
  public void test_hashCode() {
    Placement p1 = new Placement(new Coordinate(0, 0), 'V');
    Placement p2 = new Placement(new Coordinate(0, 0), 'v');
    Placement p3 = new Placement(new Coordinate(0, 2), 'V');
    Placement p4 = new Placement(new Coordinate(0, 0), 'H');
    assertEquals(p1.hashCode(), p2.hashCode());
    assertNotEquals(p1.hashCode(), p3.hashCode());
    assertNotEquals(p1.hashCode(), p4.hashCode());
  }

  @Test
  public void test_string_constructor_valid_cases() {
    Placement p1 = new Placement("B3V");
    assertEquals(1, p1.getCoordinate().getRow());
    assertEquals(3, p1.getCoordinate().getColumn());
    assertEquals('V', p1.getOrientation());
    Placement p2 = new Placement("D5H");
    assertEquals(3, p2.getCoordinate().getRow());
    assertEquals(5, p2.getCoordinate().getColumn());
    assertEquals('H', p2.getOrientation());
    Placement p3 = new Placement("A9v");
    assertEquals(0, p3.getCoordinate().getRow());
    assertEquals(9, p3.getCoordinate().getColumn());
    assertEquals('V', p3.getOrientation());
    Placement p4 = new Placement("Z0h");
    assertEquals(25, p4.getCoordinate().getRow());
    assertEquals(0, p4.getCoordinate().getColumn());
    assertEquals('H', p4.getOrientation());

  }
  @Test
  public void test_string_constructor_error_cases() {
    assertThrows(IllegalArgumentException.class, () -> new Placement("00V"));
    assertThrows(IllegalArgumentException.class, () -> new Placement("AA0"));
    assertThrows(IllegalArgumentException.class, () -> new Placement("@0V"));
    assertThrows(IllegalArgumentException.class, () -> new Placement("[0V"));
    assertThrows(IllegalArgumentException.class, () -> new Placement("A/v"));
    assertThrows(IllegalArgumentException.class, () -> new Placement("A:H"));
    assertThrows(IllegalArgumentException.class, () -> new Placement("A"));
    assertThrows(IllegalArgumentException.class, () -> new Placement("A1X"));
    assertThrows(IllegalArgumentException.class, () -> new Placement("A1VV"));
    assertThrows(IllegalArgumentException.class, () -> new Placement("A12H"));
  }

}
