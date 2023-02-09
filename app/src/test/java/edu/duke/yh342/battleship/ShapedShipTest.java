package edu.duke.yh342.battleship;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

public class ShapedShipTest {
    @Test
    public void test_make_coords() {
        Placement p1 = new Placement(new Coordinate(1, 2), 'v');
        Placement p2 = new Placement(new Coordinate(1, 2), 'u');
        Placement p3 = new Placement(new Coordinate(1, 2), 'l');
        Placement p4 = new Placement(new Coordinate(1, 2), 'r');
        Placement p5 = new Placement(new Coordinate(1, 2), 'd');
        assertThrows(IllegalArgumentException.class, () -> ShapedShip.makeCoords(p1, 0, 0, "Carrier"));
        assertThrows(IllegalArgumentException.class, () -> ShapedShip.makeCoords(p1, 0, 0, "Battleship"));
        assertThrows(IllegalArgumentException.class, () -> ShapedShip.makeCoords(p1, 0, 0, "Destroyer"));
        assertThrows(IllegalArgumentException.class, () -> ShapedShip.makeCoords(p1, 5, 0, "Destroyer"));
        assertEquals(ShapedShip.makeCoords(p2, 11, 0, "Battleship").contains(p2.getCoordinate()), false);
        assertEquals(ShapedShip.makeCoords(p3, 11, 0, "Battleship").contains(p3.getCoordinate()), false);
        assertEquals(ShapedShip.makeCoords(p2, 3, 0, "Carrier").contains(p2.getCoordinate()), true);
        assertEquals(ShapedShip.makeCoords(p4, 3, 0, "Carrier").contains(p4.getCoordinate()), false);
        assertEquals(ShapedShip.makeCoords(p5, 3, 0, "Carrier").contains(p5.getCoordinate()), true);
        assertEquals(ShapedShip.makeCoords(p1, 2, 2, "Destroyer").contains(p1.getCoordinate()), true);
        ShapedShip r = new ShapedShip(p2, 's', '*');
        assertEquals(true, r.occupiesCoordinates(p2.getCoordinate()));
        assertEquals("testship", r.getName());
        assertEquals('U', r.getOrientation());
    }

}
