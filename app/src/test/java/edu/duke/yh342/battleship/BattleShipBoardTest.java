package edu.duke.yh342.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class BattleShipBoardTest {

    @Test
    public void test_width_and_height() {
        Board<Character> b1 = new BattleShipBoard<Character>(10, 20, 'X');
        assertEquals(20, b1.getHeight());
        assertEquals(10, b1.getWidth());
    }

    @Test
    public void test_invalid_dimensions() {
        assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(10, 0, 'X'));
        assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(0, 20, 'X'));
        assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(10, -5, 'X'));
        assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(-8, 20, 'X'));
    }

    @Test
    public void test_add_ship_to_board() {
        Character[][] board_expected = new Character[20][10];
        BattleShipBoard<Character> b1 = new BattleShipBoard<Character>(10, 20, 'X');
        // Should be empty
        assertEquals(null, b1.whatIsAtForSelf(new Coordinate(0, 0)));

        // Add four ships
        b1.tryAddShip(new RectangleShip(new Coordinate(0, 0), 's', '*'));
        b1.tryAddShip(new RectangleShip(new Coordinate(12, 0), 's', '*'));
        b1.tryAddShip(new RectangleShip(new Coordinate(0, 5), 's', '*'));
        b1.tryAddShip(new RectangleShip(new Coordinate(19, 9), 's', '*'));

        board_expected[0][0] = 's';
        board_expected[0][5] = 's';
        board_expected[12][0] = 's';
        board_expected[19][9] = 's';

        // Should be same as expected array
        checkWhatIsAtBoard(b1, board_expected);

        // Check for collision
        V1ShipFactory v = new V1ShipFactory();
        Placement p1 = new Placement(new Coordinate(12, 0), 'v');
        Ship<Character> s1 = v.makeCarrier(p1);
        assertEquals("That placement is invalid: the ship overlaps another ship.", b1.tryAddShip(s1));
    }

    /**
     * Check what is at the block
     *
     * @param b        game board
     * @param expected expected placement of the board
     */
    private <T> void checkWhatIsAtBoard(BattleShipBoard<T> b, T[][] expected) {
        for (int i = 0; i < expected.length; i++) {
            for (int j = 0; j < expected[0].length; j++) {
                if (expected[i][j] != null && (Character) expected[i][j] == 's') {
                    assertEquals('s', b.whatIsAtForSelf(new Coordinate(i, j)));
                } else {
                    assertEquals(null, b.whatIsAtForSelf(new Coordinate(i, j)));
                }
            }
        }
    }

    @Test
    public void test_what_is_at() {
        BattleShipBoard<Character> b1 = new BattleShipBoard<Character>(10, 20, 'X');
        Coordinate c1 = new Coordinate(10, 19);
        Coordinate c2 = new Coordinate(20, 9);
        assertThrows(IllegalArgumentException.class, () -> b1.whatIsAtForSelf(c1));
        assertThrows(IllegalArgumentException.class, () -> b1.whatIsAtForSelf(c2));
    }

    @Test
    public void test_fire_at() {
        BattleShipBoard<Character> b1 = new BattleShipBoard<Character>(10, 20, 'X');
        V1ShipFactory v1 = new V1ShipFactory();
        Placement p1 = new Placement("E5H");
        Ship<Character> s1 = v1.makeSubmarine(p1);
        b1.tryAddShip(s1);

        assertNull(b1.fireAt(new Coordinate(0, 0)));
        assertSame(b1.fireAt(new Coordinate(4, 5)), s1);

        assertFalse(s1.isSunk());

        b1.fireAt(new Coordinate(4, 6));
        assertTrue(s1.isSunk());
    }

    @Test
    public void test_what_is_at_enemy() {
        BattleShipBoard<Character> b1 = new BattleShipBoard<>(10, 20, 'X');
        V1ShipFactory f = new V1ShipFactory();
        Ship<Character> s1 = f.makeDestroyer(new Placement("A0H"));
        b1.tryAddShip(s1);
        Coordinate c1 = new Coordinate(8, 0);
        b1.fireAt(c1);
        assertEquals(b1.whatIsAtForEnemy(new Coordinate(8, 0)), 'X');
        assertNull(b1.whatIsAtForEnemy(new Coordinate(9, 0)));
    }

}
