package edu.duke.yh342.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class BoardTextViewTest {

    @Test
    public void test_display_empty_2by2() {
        Board<Character> b1 = new BattleShipBoard<Character>(2, 2, 'X');
        BoardTextView view = new BoardTextView(b1);
        String expectedHeader = "  0|1\n";
        assertEquals(expectedHeader, view.makeHeader());
        String expected =
                expectedHeader +
                        "A  |  A\n" +
                        "B  |  B\n" +
                        expectedHeader;
        assertEquals(expected, view.displayMyOwnBoard());
    }

    @Test
    public void test_invalid_board_size() {
        Board<Character> wideBoard = new BattleShipBoard<Character>(11, 20, 'X');
        Board<Character> tallBoard = new BattleShipBoard<Character>(10, 27, 'X');
        assertThrows(IllegalArgumentException.class, () -> new BoardTextView(wideBoard));
        assertThrows(IllegalArgumentException.class, () -> new BoardTextView(tallBoard));
    }

    private void emptyBoardHelper(int w, int h, String expectedHeader, String body) {
        Board<Character> b1 = new BattleShipBoard<Character>(w, h, 'X');
        BoardTextView view = new BoardTextView(b1);
        assertEquals(expectedHeader, view.makeHeader());
        String expected = expectedHeader + body + expectedHeader;
        assertEquals(expected, view.displayEnemyBoard());
    }

    private void nonEmptyBoardHelper(int w, int h, String expectedHeader, String body) {
        Board<Character> b1 = new BattleShipBoard<Character>(w, h, 'X');
        b1.tryAddShip(new RectangleShip(new Coordinate(0, 0), 's', '*'));
        b1.tryAddShip(new RectangleShip(new Coordinate(2, 1), 's', '*'));
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
    
    @Test
    public void test_display_enemy_5by3() {
        Board<Character> b = new BattleShipBoard(5, 1,'X');
        V1ShipFactory v = new V1ShipFactory();
        Ship<Character> s1 = v.makeSubmarine(new Placement(new Coordinate(0, 0), 'h'));
        b.tryAddShip(s1);

        BoardTextView view = new BoardTextView(b);

        String ownView = 
                "  0|1|2|3|4\n" +        
                "A s|s| | |  A\n" +
                "  0|1|2|3|4\n";
        assertEquals(ownView, view.displayMyOwnBoard());

        String enemyView =
                "  0|1|2|3|4\n" +        
                "A  | | | |  A\n" +
                "  0|1|2|3|4\n";
        assertEquals(enemyView, view.displayEnemyBoard());

        b.fireAt(new Coordinate(0,0));
        b.fireAt(new Coordinate(0,4));

        String enemyViewUpdated = 
                "  0|1|2|3|4\n" +        
                "A s| | | |X A\n" +
                "  0|1|2|3|4\n";
        assertEquals(view.displayEnemyBoard(), enemyViewUpdated);
    }

    @Test
    public void test_my_board_with_enemy_next() {
        Board<Character> b1 = new BattleShipBoard(4, 2,'X');
        Board<Character> b2 = new BattleShipBoard(4, 2,'X');
        V1ShipFactory v = new V1ShipFactory();
        Ship<Character> s1 = v.makeSubmarine(new Placement(new Coordinate(0, 0), 'h'));
        Ship<Character> s2 = v.makeDestroyer(new Placement(new Coordinate(1, 1), 'h'));
        Ship<Character> s3 = v.makeSubmarine(new Placement(new Coordinate(0, 0), 'v'));
        Ship<Character> s4 = v.makeDestroyer(new Placement(new Coordinate(0, 1), 'h'));
        b1.tryAddShip(s1);
        b1.tryAddShip(s2);
        b2.tryAddShip(s3);
        b2.tryAddShip(s4);

        BoardTextView ownView = new BoardTextView(b1);
        BoardTextView enemyView = new BoardTextView(b2);

        String result1 = ownView.displayMyBoardWithEnemyNextToIt(enemyView, "Your ocean", "Player B's ocean");
        String result2 = enemyView.displayMyBoardWithEnemyNextToIt(ownView, "Your ocean", "Player A's ocean");

        String expectedView1 = 
        "     Your ocean               Player B's ocean\n" +
        "  0|1|2|3                    0|1|2|3\n" + 
        "A s|s| |  A                A  | | |  A\n"+
        "B  |d|d|d B                B  | | |  B\n"+
        "  0|1|2|3                    0|1|2|3\n";

        String expectedView2 = 
        "     Your ocean               Player A's ocean\n" +
        "  0|1|2|3                    0|1|2|3\n" + 
        "A s|d|d|d A                A  | | |  A\n"+
        "B s| | |  B                B  | | |  B\n"+
        "  0|1|2|3                    0|1|2|3\n";

        assertEquals("\n" + expectedView1, "\n" + result1);
        assertEquals("\n" + expectedView2, "\n" + result2);
    }
}
