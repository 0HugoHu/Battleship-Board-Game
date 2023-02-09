package edu.duke.yh342.battleship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

public class RectangleShipTest {
    @Test
    public void test_make_coords() {
        Placement p = new Placement(new Coordinate(10, 20), 'v');
        assertThrows(IllegalArgumentException.class, () -> RectangleShip.makeCoords(p, 0, 1));
        assertThrows(IllegalArgumentException.class, () -> RectangleShip.makeCoords(p, 5, 0));

        HashSet<Coordinate> coord = RectangleShip.makeCoords(p, 1, 3);
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
        Placement p1 = new Placement(new Coordinate(10, 20), 'v');
        Placement p2 = new Placement(new Coordinate(10, 21), 'v');
        RectangleShip r = new RectangleShip("testship", p1, 2, 1, 's', '*');
        assertEquals(false, r.isSunk());
        r.recordHitAt(p1.getCoordinate());
        assertEquals(false, r.isSunk());
        r.recordHitAt(p2.getCoordinate());
        assertEquals(true, r.isSunk());
    }

    @Test
    public void test_display_info() {
        Placement p1 = new Placement(new Coordinate(10, 20), 'v');
        Placement p2 = new Placement(new Coordinate(10, 21), 'v');
        Placement p3 = new Placement(new Coordinate(11, 21), 'v');
        RectangleShip<Character> r = new RectangleShip<>("testship", p1, 2, 1, 's', '*');
        assertThrows(IllegalArgumentException.class, () -> r.getDisplayInfoAt(p3.getCoordinate(), true));
        r.recordHitAt(p1.getCoordinate());
        assertEquals(r.getDisplayInfoAt(p1.getCoordinate(), true), '*');
        assertEquals(r.getDisplayInfoAt(p2.getCoordinate(), true), 's');
        assertEquals(r.getDisplayInfoAt(p1.getCoordinate(), false), 's');
        assertNull(r.getDisplayInfoAt(p2.getCoordinate(), false));
    }

    @Test
    public void test_get_name() {
        Placement p1 = new Placement(new Coordinate(10, 20), 'v');
        RectangleShip<Character> r = new RectangleShip<>(p1.getCoordinate(), 's', '*');
        assertEquals(r.getName(), "testship");
    }

    @Test
    public void test_get_coordinates() {
        Placement p1 = new Placement(new Coordinate(10, 20), 'v');
        HashSet<Coordinate> hashset = new HashSet<>();
        hashset.add(p1.getCoordinate());
        hashset.add(new Coordinate(10, 21));
        RectangleShip<Character> r = new RectangleShip<>("testship", p1, 2, 1, 's', '*');
        for (Coordinate c : r.getCoordinates()) {
            assertEquals(true, hashset.contains(c));
        }
    }

    @Test
    public void test_get_orientation() {
        RectangleShip<Character> r = new RectangleShip<>("testship", new Placement("A0V"), 1, 1, 's', '*');
        assertEquals(r.getOrientation(), 'V');
    }

    @Test
    public void test_add_coordinate() {
        ShapedShip<Character> r = new ShapedShip<>("testship", new Placement("A0V"), 1, 1, 's', '*');
        r.addCoordinate(new Coordinate(1, 0), true);
        assertEquals(true, r.occupiesCoordinates(new Coordinate(1, 0)));
    }

    @Test
    public void test_remove_coordinate() {
        ShapedShip<Character> r = new ShapedShip<>("testship", new Placement("A0V"), 1, 1, 's', '*');
        r.removeCoordinate();
        assertEquals(false, r.occupiesCoordinates(new Coordinate(0, 0)));
    }

}
