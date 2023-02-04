package edu.duke.yh342.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class NoCollisionRuleCheckerTest {
    @Test
    public void test_no_collision_checker() {
        V1ShipFactory v = new V1ShipFactory();
        Placement p1 = new Placement(new Coordinate(10, 5), 'v');
        Placement p2 = new Placement(new Coordinate(8, 5), 'v');
        Placement p3 = new Placement(new Coordinate(13, 1), 'h');

        Ship<Character> s1 = v.makeCarrier(p1);

        NoCollisionRuleChecker<Character> checker = new NoCollisionRuleChecker<>(null);
        BattleShipBoard<Character> b = new BattleShipBoard<>(10, 20, checker, 'X');

        assertEquals(checker.checkPlacement(s1, b), null);
        b.tryAddShip(s1);
        Ship<Character> s2 = v.makeCarrier(p2);
        assertEquals(checker.checkPlacement(s2, b), "That placement is invalid: the ship overlaps another ship.");
        Ship<Character> s3 = v.makeCarrier(p3);
        assertEquals(checker.checkPlacement(s3, b), "That placement is invalid: the ship overlaps another ship.");
    }

    @Test
    public void test_combined() {
        V1ShipFactory v = new V1ShipFactory();
        Placement p1 = new Placement(new Coordinate(10, 5), 'v');
        Placement p2 = new Placement(new Coordinate(8, 5), 'v');
        Placement p3 = new Placement(new Coordinate(13, 1), 'h');

        Ship<Character> s1 = v.makeCarrier(p1);

        PlacementRuleChecker<Character> checker = new InBoundsRuleChecker<>(new NoCollisionRuleChecker<>(null));
        BattleShipBoard<Character> b = new BattleShipBoard<>(10, 20, checker, 'X');

        assertEquals(checker.checkPlacement(s1, b), null);
        b.tryAddShip(s1);
        Ship<Character> s2 = v.makeCarrier(p2);
        assertEquals(checker.checkPlacement(s2, b), "That placement is invalid: the ship overlaps another ship.");
        Ship<Character> s3 = v.makeCarrier(p3);
        assertEquals(checker.checkPlacement(s3, b), "That placement is invalid: the ship overlaps another ship.");

    }

}
