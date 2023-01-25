package edu.duke.yh342.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class BoardTextViewTest {

  @Test
  public void test_display_empty_2by2() {
    Board<Character> b1 = new BattleShipBoard<Character>(2, 2);
    BoardTextView view = new BoardTextView(b1);
    String expectedHeader= "  0|1\n";
    assertEquals(expectedHeader, view.makeHeader());
    String expected=
      expectedHeader+
      "A  |  A\n"+
      "B  |  B\n"+
      expectedHeader;
    assertEquals(expected, view.displayMyOwnBoard());
  }

  @Test
  public void test_invalid_board_size() {
    Board<Character> wideBoard = new BattleShipBoard<Character>(11,20);
    Board<Character> tallBoard = new BattleShipBoard<Character>(10,27);
    assertThrows(IllegalArgumentException.class, () -> new BoardTextView(wideBoard));
    assertThrows(IllegalArgumentException.class, () -> new BoardTextView(tallBoard));
  }

  private void emptyBoardHelper(int w, int h, String expectedHeader, String body){
    Board<Character> b1 = new BattleShipBoard<Character>(w, h);
    BoardTextView view = new BoardTextView(b1);
    assertEquals(expectedHeader, view.makeHeader());
    String expected = expectedHeader + body + expectedHeader;
    assertEquals(expected, view.displayMyOwnBoard());
  }

  private void nonEmptyBoardHelper(int w, int h, String expectedHeader, String body){
    Board<Character> b1 = new BattleShipBoard<Character>(w, h);
    b1.tryAddShip(new BasicShip(new Coordinate(0, 0)));
    b1.tryAddShip(new BasicShip(new Coordinate(2, 1)));
    BoardTextView view = new BoardTextView(b1);
    assertEquals(expectedHeader, view.makeHeader());
    String expected = expectedHeader + body + expectedHeader;
    assertEquals(expected, view.displayMyOwnBoard());
  }

  @Test
  public void test_display_empty_3by2() {
    String expectedHeader = new String("  0|1|2\n");
    String expectedBody = new String("A  | |  A\nB  | |  B\n");
    emptyBoardHelper(3, 2, expectedHeader, expectedBody);
  }

  @Test
  public void test_display_empty_3by5() {
    String expectedHeader = new String("  0|1|2\n");
    String expectedBody = new String("A  | |  A\nB  | |  B\nC  | |  C\nD  | |  D\nE  | |  E\n");
    emptyBoardHelper(3, 5, expectedHeader, expectedBody);
  }

  @Test
  public void test_display_4by3() {
    String expectedHeader = new String("  0|1|2|3\n");
    String expectedBody = new String("A s| | |  A\nB  | | |  B\nC  |s| |  C\n");
    nonEmptyBoardHelper(4, 3, expectedHeader, expectedBody);
  }

}
