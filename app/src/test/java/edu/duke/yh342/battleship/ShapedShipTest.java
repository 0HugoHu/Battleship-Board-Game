package edu.duke.yh342.battleship;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

public class ShapedShipTest {
  @Test
    public void test_make_coords() {
        Coordinate c = new Coordinate(1, 2);
        assertThrows(IllegalArgumentException.class, () -> ShapedShip.makeCoords(c, 0, 0, "Carrier"));
        assertThrows(IllegalArgumentException.class, () -> ShapedShip.makeCoords(c, 0, 0, "Battleship"));
        assertEquals(ShapedShip.makeCoords(c, 11, 0, "Battleship").contains(c), false);
        assertEquals(ShapedShip.makeCoords(c, 3, 0, "Carrier").contains(c), true);
        assertEquals(ShapedShip.makeCoords(c, 17, 0, "Carrier").contains(c), false);
        assertThrows(IllegalArgumentException.class, () -> ShapedShip.makeCoords(c, 0, 0, "Destroyer"));
        assertThrows(IllegalArgumentException.class, () -> ShapedShip.makeCoords(c, 0, 0, "Destroyer"));
        assertThrows(IllegalArgumentException.class, () -> ShapedShip.makeCoords(c, 5, 0, "Destroyer"));
        assertEquals(ShapedShip.makeCoords(c, 2, 2, "Destroyer").contains(c), true);
        ShapedShip r = new ShapedShip(c, 's', '*');
        assertEquals(true, r.occupiesCoordinates(c));
    }

}
