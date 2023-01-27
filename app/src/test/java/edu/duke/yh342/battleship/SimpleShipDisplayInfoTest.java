package edu.duke.yh342.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class SimpleShipDisplayInfoTest {
  @Test
  public void test_get_info() {
    SimpleShipDisplayInfo<Character> s = new SimpleShipDisplayInfo<>('s', '#');
    assertEquals('#', s.getInfo(new Coordinate(0, 0), true));
    assertEquals('s', s.getInfo(new Coordinate(0, 1), false));
  }

}
