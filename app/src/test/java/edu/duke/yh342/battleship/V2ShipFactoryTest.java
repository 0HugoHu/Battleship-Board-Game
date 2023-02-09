package edu.duke.yh342.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class V2ShipFactoryTest {
    @Test
    public void test_create_ship_v2() {
        V2ShipFactory v = new V2ShipFactory();
        Ship<Character> ship1 = v.makeSubmarine(new Placement(new Coordinate(2, 1), 'v'));
        Ship<Character> ship2 = v.makeDestroyer(new Placement(new Coordinate(2, 1), 'h'));
        Ship<Character> ship3 = v.makeCarrier(new Placement(new Coordinate(2, 1), 'u'));
        Ship<Character> ship4 = v.makeBattleship(new Placement(new Coordinate(2, 1), 'd'));
        Ship<Character> ship5 = v.makeCarrier(new Placement(new Coordinate(2, 1), 'l'));
        Ship<Character> ship6 = v.makeBattleship(new Placement(new Coordinate(2, 1), 'r'));
        assertEquals(ship1.getDisplayInfoAt(new Coordinate(2, 1), true), 's');
    }

}
