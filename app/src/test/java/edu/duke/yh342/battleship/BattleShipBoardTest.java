package edu.duke.yh342.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class BattleShipBoardTest {
  @Test
  public void test_width_and_height() {
    Board<Character> b1 = new BattleShipBoard<Character>(10, 20);
    assertEquals(20, b1.getHeight());
    assertEquals(10, b1.getWidth());
  }

  @Test
  public void test_invalid_dimensions() {
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(10, 0));
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(0, 20));
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(10, -5));
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(-8, 20));
  }


  @Test
  public void test_add_ship_to_board() {
    Character[][] board_expected = new Character[20][10];
    BattleShipBoard<Character> b1 = new BattleShipBoard<Character>(10, 20);
    assertEquals(null, b1.whatIsAt(new Coordinate(0, 0)));

    b1.tryAddShip(new BasicShip(new Coordinate(0, 0)));
    b1.tryAddShip(new BasicShip(new Coordinate(12, 0)));
    b1.tryAddShip(new BasicShip(new Coordinate(0, 5)));
    b1.tryAddShip(new BasicShip(new Coordinate(19, 9)));
    
    board_expected[0][0] = 's';
    board_expected[0][5] = 's';
    board_expected[12][0] = 's';
    board_expected[19][9] = 's';
    checkWhatIsAtBoard(b1, board_expected);

  }
  

  @Test
  public void test_what_is_at() {
    BattleShipBoard<Character> b1 = new BattleShipBoard<Character>(10, 20);
    Coordinate c1 = new Coordinate(10, 19);
    Coordinate c2 = new Coordinate(20, 9);
    assertThrows(IllegalArgumentException.class, () -> b1.whatIsAt(c1));
    assertThrows(IllegalArgumentException.class, () -> b1.whatIsAt(c2));
  }

  private <T> void checkWhatIsAtBoard(BattleShipBoard<T> b, T[][] expected) {
    for (int i = 0; i < expected.length; i++) {
      for (int j = 0; j < expected[0].length; j++) {
        if (expected[i][j] != null && (Character)expected[i][j] == 's') {
          assertEquals('s', b.whatIsAt(new Coordinate(i, j)));
        } else {
          assertEquals(null, b.whatIsAt(new Coordinate(i, j)));
        }
      }
    }
  }


}
