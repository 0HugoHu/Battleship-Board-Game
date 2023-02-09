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
    public void test_fire_at_and_move_ship_to() {
        BattleShipBoard<Character> b1 = new BattleShipBoard<Character>(10, 20, 'X');
        V1ShipFactory v1 = new V1ShipFactory();
        Placement p1 = new Placement("E5H");
        Placement p1_1 = new Placement("E5V");
        Placement p2 = new Placement("E5U");
        Placement p3 = new Placement("E8U");
        Ship<Character> s1 = v1.makeSubmarine(p1);
        Ship<Character> s2 = v1.makeDestroyer(p1);
        Ship<Character> s3 = v1.makeCarrier(p1);
        Ship<Character> s4 = v1.makeBattleship(p1);

        b1.tryAddShip(s1);


        b1.getShipAt(new Coordinate(4, 5));
        b1.getShipAt(new Coordinate(5, 5));

        b1.moveShipTo(s1, p1, b1);
        b1.moveShipTo(s1, p1_1, b1);
        b1.moveShipTo(s2, p1, b1);
        b1.moveShipTo(s3, p2, b1);
        b1.moveShipTo(s3, p3, b1);
        b1.moveShipTo(s4, p2, b1);

        b1.enemyMisses.add(new Coordinate(4, 5));
        b1.notShownPieces.add(new Coordinate(4, 5));
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
        b1.enemyHits.put(new Coordinate(4, 7), 'b');
        assertEquals(b1.whatIsAtForEnemy(new Coordinate(4, 7)), 'b');
        b1.notShownPieces.add(new Coordinate(4, 5));
        assertNull(b1.whatIsAtForEnemy(new Coordinate(4, 5)));
        b1.enemyMisses.add(new Coordinate(4, 6));
        assertEquals(b1.whatIsAtForEnemy(new Coordinate(4, 6)), 'X');
    }

    @Test
    public void test_transfer_hit_points() {
        BattleShipBoard<Character> b1 = new BattleShipBoard<>(10, 20, 'X');
        V2ShipFactory f = new V2ShipFactory();
        Ship<Character> s1 = f.makeDestroyer(new Placement("A0H"));
        b1.tryAddShip(s1);
        b1.fireAt(new Coordinate(0, 0));
        Ship<Character> s2 = f.makeDestroyer(new Placement("D0H"));
        b1.transferHitPoints(s1, s2);

        s2 = f.makeDestroyer(new Placement("D0v"));
        b1.transferHitPoints(s1, s2);

        b1 = new BattleShipBoard<>(10, 20, 'X');
        s1 = f.makeDestroyer(new Placement("A0v"));
        b1.tryAddShip(s1);
        b1.fireAt(new Coordinate(0, 0));
        s2 = f.makeDestroyer(new Placement("D0h"));
        b1.transferHitPoints(s1, s2);

        // Battleship u

        b1 = new BattleShipBoard<>(10, 20, 'X');
        s1 = f.makeBattleship(new Placement("a0u"));
        b1.tryAddShip(s1);
        b1.fireAt(new Coordinate(1, 0));
        s2 = f.makeBattleship(new Placement("m0r"));
        b1.transferHitPoints(s1, s2);

        b1 = new BattleShipBoard<>(10, 20, 'X');
        s1 = f.makeBattleship(new Placement("a0u"));
        b1.tryAddShip(s1);
        b1.fireAt(new Coordinate(1, 0));
        s2 = f.makeBattleship(new Placement("m0d"));
        b1.transferHitPoints(s1, s2);

        b1 = new BattleShipBoard<>(10, 20, 'X');
        s1 = f.makeBattleship(new Placement("a0u"));
        b1.tryAddShip(s1);
        b1.fireAt(new Coordinate(1, 0));
        s2 = f.makeBattleship(new Placement("m0l"));
        b1.transferHitPoints(s1, s2);

        // Battleship r
        b1 = new BattleShipBoard<>(10, 20, 'X');
        s1 = f.makeBattleship(new Placement("a0r"));
        b1.tryAddShip(s1);
        b1.fireAt(new Coordinate(1, 0));
        s2 = f.makeBattleship(new Placement("m0d"));
        b1.transferHitPoints(s1, s2);

        b1 = new BattleShipBoard<>(10, 20, 'X');
        s1 = f.makeBattleship(new Placement("a0r"));
        b1.tryAddShip(s1);
        b1.fireAt(new Coordinate(1, 0));
        s2 = f.makeBattleship(new Placement("m0l"));
        b1.transferHitPoints(s1, s2);

        b1 = new BattleShipBoard<>(10, 20, 'X');
        s1 = f.makeBattleship(new Placement("a0r"));
        b1.tryAddShip(s1);
        b1.fireAt(new Coordinate(1, 0));
        s2 = f.makeBattleship(new Placement("m0u"));
        b1.transferHitPoints(s1, s2);

        // Battleship d
        b1 = new BattleShipBoard<>(10, 20, 'X');
        s1 = f.makeBattleship(new Placement("a0d"));
        b1.tryAddShip(s1);
        b1.fireAt(new Coordinate(0, 0));
        s2 = f.makeBattleship(new Placement("m0l"));
        b1.transferHitPoints(s1, s2);

        b1 = new BattleShipBoard<>(10, 20, 'X');
        s1 = f.makeBattleship(new Placement("a0d"));
        b1.tryAddShip(s1);
        b1.fireAt(new Coordinate(0, 0));
        s2 = f.makeBattleship(new Placement("m0u"));
        b1.transferHitPoints(s1, s2);

        b1 = new BattleShipBoard<>(10, 20, 'X');
        s1 = f.makeBattleship(new Placement("a0d"));
        b1.tryAddShip(s1);
        b1.fireAt(new Coordinate(0, 0));
        s2 = f.makeBattleship(new Placement("m0r"));
        b1.transferHitPoints(s1, s2);

        // Battleship l
        b1 = new BattleShipBoard<>(10, 20, 'X');
        s1 = f.makeBattleship(new Placement("a0l"));
        b1.tryAddShip(s1);
        b1.fireAt(new Coordinate(1, 0));
        s2 = f.makeBattleship(new Placement("m0u"));
        b1.transferHitPoints(s1, s2);

        b1 = new BattleShipBoard<>(10, 20, 'X');
        s1 = f.makeBattleship(new Placement("a0l"));
        b1.tryAddShip(s1);
        b1.fireAt(new Coordinate(1, 0));
        s2 = f.makeBattleship(new Placement("m0r"));
        b1.transferHitPoints(s1, s2);

        b1 = new BattleShipBoard<>(10, 20, 'X');
        s1 = f.makeBattleship(new Placement("a0l"));
        b1.tryAddShip(s1);
        b1.fireAt(new Coordinate(1, 0));
        s2 = f.makeBattleship(new Placement("m0d"));
        b1.transferHitPoints(s1, s2);


        // Carrier u
        b1 = new BattleShipBoard<>(10, 20, 'X');
        s1 = f.makeCarrier(new Placement("a0u"));
        b1.tryAddShip(s1);
        b1.fireAt(new Coordinate(1, 0));
        s2 = f.makeCarrier(new Placement("m0r"));
        b1.transferHitPoints(s1, s2);

        b1 = new BattleShipBoard<>(10, 20, 'X');
        s1 = f.makeCarrier(new Placement("a0u"));
        b1.tryAddShip(s1);
        b1.fireAt(new Coordinate(1, 0));
        s2 = f.makeCarrier(new Placement("m0d"));
        b1.transferHitPoints(s1, s2);

        b1 = new BattleShipBoard<>(10, 20, 'X');
        s1 = f.makeCarrier(new Placement("a0u"));
        b1.tryAddShip(s1);
        b1.fireAt(new Coordinate(1, 0));
        s2 = f.makeCarrier(new Placement("m0l"));
        b1.transferHitPoints(s1, s2);

        // Carrier r
        b1 = new BattleShipBoard<>(10, 20, 'X');
        s1 = f.makeCarrier(new Placement("a0r"));
        b1.tryAddShip(s1);
        b1.fireAt(new Coordinate(1, 0));
        s2 = f.makeCarrier(new Placement("m0d"));
        b1.transferHitPoints(s1, s2);

        b1 = new BattleShipBoard<>(10, 20, 'X');
        s1 = f.makeCarrier(new Placement("a0r"));
        b1.tryAddShip(s1);
        b1.fireAt(new Coordinate(1, 0));
        s2 = f.makeCarrier(new Placement("m0l"));
        b1.transferHitPoints(s1, s2);

        b1 = new BattleShipBoard<>(10, 20, 'X');
        s1 = f.makeCarrier(new Placement("a0r"));
        b1.tryAddShip(s1);
        b1.fireAt(new Coordinate(1, 0));
        s2 = f.makeCarrier(new Placement("m0u"));
        b1.transferHitPoints(s1, s2);

        // Carrier d
        b1 = new BattleShipBoard<>(10, 20, 'X');
        s1 = f.makeCarrier(new Placement("a0d"));
        b1.tryAddShip(s1);
        b1.fireAt(new Coordinate(0, 0));
        s2 = f.makeCarrier(new Placement("m0l"));
        b1.transferHitPoints(s1, s2);

        b1 = new BattleShipBoard<>(10, 20, 'X');
        s1 = f.makeCarrier(new Placement("a0d"));
        b1.tryAddShip(s1);
        b1.fireAt(new Coordinate(0, 0));
        s2 = f.makeCarrier(new Placement("m0u"));
        b1.transferHitPoints(s1, s2);

        b1 = new BattleShipBoard<>(10, 20, 'X');
        s1 = f.makeCarrier(new Placement("a0d"));
        b1.tryAddShip(s1);
        b1.fireAt(new Coordinate(0, 0));
        s2 = f.makeCarrier(new Placement("m0r"));
        b1.transferHitPoints(s1, s2);

        // Carrier l
        b1 = new BattleShipBoard<>(10, 20, 'X');
        s1 = f.makeCarrier(new Placement("a0l"));
        b1.tryAddShip(s1);
        b1.fireAt(new Coordinate(1, 0));
        s2 = f.makeCarrier(new Placement("m0u"));
        b1.transferHitPoints(s1, s2);

        b1 = new BattleShipBoard<>(10, 20, 'X');
        s1 = f.makeCarrier(new Placement("a0l"));
        b1.tryAddShip(s1);
        b1.fireAt(new Coordinate(1, 0));
        s2 = f.makeCarrier(new Placement("m0r"));
        b1.transferHitPoints(s1, s2);

        b1 = new BattleShipBoard<>(10, 20, 'X');
        s1 = f.makeCarrier(new Placement("a0l"));
        b1.tryAddShip(s1);
        b1.fireAt(new Coordinate(1, 0));
        s2 = f.makeCarrier(new Placement("m0d"));
        b1.notShownPieces.add(new Coordinate(1, 0));
        b1.transferHitPoints(s1, s2);
    }

    @Test
    public void test_sonar_scan() {
        BattleShipBoard<Character> b1 = new BattleShipBoard<>(10, 20, 'X');
        V2ShipFactory f = new V2ShipFactory();
        Ship<Character> s1 = f.makeDestroyer(new Placement("A0H"));
        Ship<Character> s2 = f.makeCarrier(new Placement("D1R"));
        Ship<Character> s3 = f.makeBattleship(new Placement("B0R"));
        Ship<Character> s4 = f.makeSubmarine(new Placement("B1H"));
        b1.tryAddShip(s1);
        b1.tryAddShip(s2);
        b1.tryAddShip(s3);
        b1.tryAddShip(s4);

        assertEquals(b1.sonarScan(new Coordinate(2, 2), b1)[0], 2);
        assertEquals(b1.sonarScan(new Coordinate(2, 2), b1)[1], 2);
        assertEquals(b1.sonarScan(new Coordinate(2, 2), b1)[2], 4);
        assertEquals(b1.sonarScan(new Coordinate(2, 2), b1)[3], 6);
    }

}
