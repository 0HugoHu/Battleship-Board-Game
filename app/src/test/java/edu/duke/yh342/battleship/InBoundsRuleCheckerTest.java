package edu.duke.yh342.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class InBoundsRuleCheckerTest {
  @Test
  public void test_check_my_rule() {
    V1ShipFactory v = new V1ShipFactory();
    Placement p1 = new Placement(new Coordinate(10, 2), 'v');
    Placement p2 = new Placement(new Coordinate(19, 6), 'h');
    Placement p3 = new Placement(new Coordinate(20, 1), 'v');
    Placement p4 = new Placement(new Coordinate(15, 8), 'v');
    Placement p5 = new Placement(new Coordinate(0, 9), 'h');
    Placement p6 = new Placement(new Coordinate(16, 0), 'v');
    Placement p7 = new Placement(new Coordinate(8, 8), 'h');

    Ship<Character> s1 = v.makeCarrier(p1);
    Ship<Character> s2 = v.makeCarrier(p2);
    Ship<Character> s3 = v.makeCarrier(p3);
    Ship<Character> s4 = v.makeCarrier(p4);
    Ship<Character> s5 = v.makeCarrier(p5);
    Ship<Character> s6 = v.makeCarrier(p6);
    Ship<Character> s7 = v.makeCarrier(p7);

    PlacementRuleChecker<Character> checker = new InBoundsRuleChecker<>(null);
    BattleShipBoard<Character> b = new BattleShipBoard<>(10, 20, checker);

    assertEquals(checker.checkPlacement(s1, b), true);
    assertEquals(checker.checkPlacement(s2, b), false);
    assertEquals(checker.checkPlacement(s3, b), false);
    assertEquals(checker.checkPlacement(s4, b), false);
    assertEquals(checker.checkPlacement(s5, b), false);
    assertEquals(checker.checkPlacement(s6, b), false);
    assertEquals(checker.checkPlacement(s7, b), false);

  }

}
