package edu.duke.yh342.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class V1ShipFactoryTest {
    @Test
    public void test_create_ship() {
        V1ShipFactory v = new V1ShipFactory();
        Ship<Character> ship1 = v.makeSubmarine(new Placement(new Coordinate(20, 10), 'v'));
        Ship<Character> ship2 = v.makeBattleship(new Placement(new Coordinate(20, 10), 'h'));
        Ship<Character> ship3 = v.makeCarrier(new Placement(new Coordinate(20, 10), 'v'));
        Ship<Character> ship4 = v.makeDestroyer(new Placement(new Coordinate(20, 10), 'h'));
        checkShip(ship1, "Submarine", 's', new Coordinate(20, 10), new Coordinate(21, 10));
        checkShip(ship2, "Battleship", 'b', new Coordinate(20, 10), new Coordinate(20, 11), new Coordinate(20, 12), new Coordinate(20, 13));
        checkShip(ship3, "Carrier", 'c', new Coordinate(20, 10), new Coordinate(21, 10), new Coordinate(22, 10), new Coordinate(23, 10), new Coordinate(24, 10), new Coordinate(25, 10));
        checkShip(ship4, "Destroyer", 'd', new Coordinate(20, 10), new Coordinate(20, 11), new Coordinate(20, 12));

    }

    private void checkShip(Ship<Character> testShip, String expectedName, char expectedLetter,
                           Coordinate... expectedLocs) {
        assertEquals(testShip.getName(), expectedName);
        for (int i = 0; i < expectedLocs.length; i++) {
            assertEquals(testShip.getDisplayInfoAt(expectedLocs[i], true), expectedLetter);
        }
        assertEquals(testShip.getName(), expectedName);
    }

}
