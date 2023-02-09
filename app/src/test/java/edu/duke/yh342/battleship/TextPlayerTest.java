package edu.duke.yh342.battleship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.StringReader;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class TextPlayerTest {
    /**
     * Test readPlacement()
     *
     * @throws IOException if I/O operation fails
     */
    @Test
    public void test_read_placement() throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        TextPlayer player = createTextPlayer("A", 10, 20, "B2V\nC8H\na4v\n", bytes);

        String prompt = "Please enter a location for a ship:";
        Placement[] expected = new Placement[3];
        expected[0] = new Placement(new Coordinate(1, 2), 'V');
        expected[1] = new Placement(new Coordinate(2, 8), 'H');
        expected[2] = new Placement(new Coordinate(0, 4), 'V');

        for (int i = 0; i < expected.length; i++) {
            Placement p = player.readPlacement(prompt);
            // Should have printed prompt and newline
            assertEquals(prompt + "\n", bytes.toString());
            // Did we get the right Placement back
            assertEquals(p, expected[i]);
            // Clear out bytes for next time around
            bytes.reset();
        }

        TextPlayer player2 = createTextPlayer("A", 10, 20, "", bytes);
        TextPlayer player3 = createTextPlayer("A", 10, 20, "BVV", bytes);
        assertThrows(IOException.class, () -> player2.readPlacement(prompt));
        Placement p3 = player3.readPlacement(prompt);
        assertNull(p3);
    }

    /**
     * Test doOnePlacement()
     *
     * @throws IOException if I/O operation fails
     */
    @Test
    public void test_do_one_replacement() throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        String prompt = "Player A where would you like to put your ship?";

        TextPlayer player = createTextPlayer("A", 3, 2, "B0H\n", bytes);
        player.doOnePlacement("Destroyer", (p) -> player.shipFactory.makeDestroyer(p));

        // Should have equal printed board
        assertEquals(prompt + "\n" + "  0|1|2\nA  | |  A\nB d|d|d B\n  0|1|2\n", bytes.toString());

        TextPlayer player2 = createTextPlayer("A", 3, 2, "B0H\n", bytes);
        // boolean result = player2.doOnePlacement("Destroyer", (p) -> player.shipFactory.makeCarrier(p));
        // assertEquals(false, result);
    }


    @Test
    public void test_difficult_to_test_part() {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(bytes, true);
        BufferedReader input = new BufferedReader(new StringReader("a0h\na0\na1\n"));
        V2ShipFactory shipFactory = new V2ShipFactory();
        Board<Character> board = new BattleShipBoard<Character>(4, 2, 'X');
        BoardTextView view = new BoardTextView(board);

        TextPlayer player1 = new TextPlayer("A", board, input, output, shipFactory);
        TextPlayer player2 = createTextPlayer("AI", 10, 20, "", bytes);
        TextPlayer player3 = new TextPlayer("AI", board, input, output, shipFactory);
        player2.aiGenerateCoordinate();
        player3.aiGeneratePlacement(0);
        player3.aiGeneratePlacement(1);
        // These are to test the random number, so a large amount of test is needed
        // The possibility of passing the test by once is low.
        player3.aiGenerateChoice(new int[]{0, 0});
        player3.aiGenerateChoice(new int[]{1, 0});
        player3.aiGenerateChoice(new int[]{1, 0});
        player3.aiGenerateChoice(new int[]{1, 0});
        player3.aiGenerateChoice(new int[]{1, 0});
        player3.aiGenerateChoice(new int[]{1, 0});
        player3.aiGenerateChoice(new int[]{1, 0});
        player3.aiGenerateChoice(new int[]{1, 0});
        player3.aiGenerateChoice(new int[]{1, 0});
        player3.aiGenerateChoice(new int[]{1, 0});
        player3.aiGenerateChoice(new int[]{1, 0});
        player3.aiGenerateChoice(new int[]{1, 0});
        player3.aiGenerateChoice(new int[]{1, 0});
        player3.aiGenerateChoice(new int[]{0, 1});
        player3.aiGenerateChoice(new int[]{0, 1});
        player3.aiGenerateChoice(new int[]{0, 1});
        player3.aiGenerateChoice(new int[]{0, 1});
        player3.aiGenerateChoice(new int[]{0, 1});
        player3.aiGenerateChoice(new int[]{0, 1});
        player3.aiGenerateChoice(new int[]{0, 1});
        player3.aiGenerateChoice(new int[]{0, 1});
        player3.aiGenerateChoice(new int[]{0, 1});
        player3.aiGenerateChoice(new int[]{0, 1});
        player3.aiGenerateChoice(new int[]{0, 1});
        player3.aiGenerateChoice(new int[]{1, 1});
        player3.aiGenerateChoice(new int[]{1, 1});
        player3.aiGenerateChoice(new int[]{1, 1});
        player3.aiGenerateChoice(new int[]{1, 1});
        player3.aiGenerateChoice(new int[]{1, 1});
        player3.aiGenerateChoice(new int[]{1, 1});
        player3.aiGenerateChoice(new int[]{1, 1});
        player3.aiGenerateChoice(new int[]{0, 1});
        player3.aiGenerateChoice(new int[]{1, 1});
        player3.aiGenerateChoice(new int[]{1, 1});
        player3.aiGenerateChoice(new int[]{1, 1});
        player3.aiGenerateChoice(new int[]{1, 1});
        player3.aiGenerateChoice(new int[]{1, 1});
    }

    /**
     * Create a textplayer helper to generate it for testing
     *
     * @param w         the width of the board
     * @param h         the height of the board
     * @param inputData the input string
     * @param bytes     the output pointer
     */
    private TextPlayer createTextPlayer(String name, int w, int h, String inputData, OutputStream bytes) {
        BufferedReader input = new BufferedReader(new StringReader(inputData));
        PrintStream output = new PrintStream(bytes, true);
        Board<Character> board = new BattleShipBoard<Character>(w, h, 'X');
        V1ShipFactory shipFactory = new V1ShipFactory();
        return new TextPlayer(name, board, input, output, shipFactory);
    }

    @Test
    public void test_check_game_ends() throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(bytes, true);
        BufferedReader input = new BufferedReader(new StringReader("a0h"));
        V1ShipFactory shipFactory = new V1ShipFactory();
        Board<Character> board = new BattleShipBoard<Character>(4, 2, 'X');

        TextPlayer player1 = new TextPlayer("A", board, input, output, shipFactory);

        player1.doOnePlacement("Submarine", (p) -> player1.shipFactory.makeSubmarine(p));
        assertEquals(false, player1.isGameEnds(board));

        board.fireAt(new Coordinate(0, 0));
        board.fireAt(new Coordinate(0, 1));

        assertEquals(true, player1.isGameEnds(board));
    }

    @Test
    public void test_read_and_fire() throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(bytes, true);
        BufferedReader input = new BufferedReader(new StringReader("a0h\na0\na1\n"));
        V1ShipFactory shipFactory = new V1ShipFactory();
        Board<Character> board = new BattleShipBoard<Character>(4, 2, 'X');

        TextPlayer player2 = new TextPlayer("A", board, input, output, shipFactory);
        player2.doOnePlacement("Submarine", (p) -> player2.shipFactory.makeSubmarine(p));
        assertEquals(false, player2.isGameEnds(board));

        player2.readAndFire(board);
        player2.readAndFire(board);

        assertEquals(true, player2.isGameEnds(board));

        input = new BufferedReader(new StringReader(""));
        TextPlayer player3 = new TextPlayer("A", board, input, output, shipFactory);
        assertThrows(IOException.class, () -> player3.readAndFire(board));
    }

    @Test
    public void test_read_and_move() throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(bytes, true);
        BufferedReader input = new BufferedReader(new StringReader("kk"));
        V1ShipFactory shipFactory = new V1ShipFactory();
        Board<Character> board = new BattleShipBoard<Character>(4, 2, 'X');

        TextPlayer player2 = new TextPlayer("A", board, input, output, shipFactory);

        assertThrows(IOException.class, () -> player2.readAndMove(board));

        input = new BufferedReader(new StringReader("A0H\nA0"));
        TextPlayer player3 = new TextPlayer("A", board, input, output, shipFactory);
        player3.doOnePlacement("Submarine", (p) -> player3.shipFactory.makeSubmarine(p));

        assertThrows(IOException.class, () -> player3.readAndMove(board));

        Board<Character> board2 = new BattleShipBoard<Character>(4, 2, 'X');
        input = new BufferedReader(new StringReader("A0H\nA0\nB0U"));
        TextPlayer player4 = new TextPlayer("A", board2, input, output, shipFactory);
        player4.doOnePlacement("Submarine", (p) -> player2.shipFactory.makeSubmarine(p));

        assertThrows(IOException.class, () -> player4.readAndMove(board2));

        Board<Character> board3 = new BattleShipBoard<Character>(4, 2, 'X');
        input = new BufferedReader(new StringReader("A0H\nA0\nB12U"));
        TextPlayer player5 = new TextPlayer("A", board3, input, output, shipFactory);
        player5.doOnePlacement("Submarine", (p) -> player2.shipFactory.makeSubmarine(p));

        assertThrows(IOException.class, () -> player5.readAndMove(board3));

        Board<Character> board4 = new BattleShipBoard<Character>(4, 2, 'X');
        input = new BufferedReader(new StringReader("A0H\nA0\nB9H"));
        TextPlayer player6 = new TextPlayer("A", board4, input, output, shipFactory);
        player6.doOnePlacement("Submarine", (p) -> player2.shipFactory.makeSubmarine(p));

        assertEquals(false, player6.readAndMove(board4));
    }

    @Test
    public void test_read_and_scan1() throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(bytes, true);
        BufferedReader input = new BufferedReader(new StringReader(""));
        V1ShipFactory shipFactory = new V1ShipFactory();
        Board<Character> board = new BattleShipBoard<Character>(4, 2, 'X');

        TextPlayer player2 = new TextPlayer("A", board, input, output, shipFactory);

        assertThrows(IOException.class, () -> player2.readAndScan(board));
    }

    @Test
    public void test_read_and_scan2() throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(bytes, true);
        BufferedReader input = new BufferedReader(new StringReader(""));
        V1ShipFactory shipFactory = new V1ShipFactory();
        Board<Character> board = new BattleShipBoard<Character>(4, 2, 'X');

        TextPlayer player2 = new TextPlayer("A", board, input, output, shipFactory);

        assertThrows(IOException.class, () -> player2.readAndScan(board));
    }

    @Test
    public void test_play_one_turn1() throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(bytes, true);
        BufferedReader input = new BufferedReader(new StringReader("a0h\na0\na1\nf\na0\n"));
        V1ShipFactory shipFactory = new V1ShipFactory();
        Board<Character> board = new BattleShipBoard<Character>(4, 2, 'X');
        BoardTextView view = new BoardTextView(board);

        TextPlayer player1 = new TextPlayer("A", board, input, output, shipFactory);
        player1.doOnePlacement("Submarine", (p) -> player1.shipFactory.makeSubmarine(p));
        assertEquals(false, player1.playOneTurn(board, view, "A", new int[]{0, 0}));
        assertEquals(true, player1.playOneTurn(board, view, "A", new int[]{0, 0}));
        assertEquals(true, player1.playOneTurn(board, view, "A", new int[]{1, 0}));
    }

    @Test
    public void test_play_one_turn2() throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(bytes, true);
        BufferedReader input = new BufferedReader(new StringReader("a0h\ns\na2\na12\n"));
        V1ShipFactory shipFactory = new V1ShipFactory();
        Board<Character> board = new BattleShipBoard<Character>(4, 2, 'X');
        BoardTextView view = new BoardTextView(board);

        TextPlayer player1 = new TextPlayer("A", board, input, output, shipFactory);
        player1.doOnePlacement("Submarine", (p) -> player1.shipFactory.makeSubmarine(p));
        assertEquals(false, player1.playOneTurn(board, view, "A", new int[]{1, 1}));
    }


    @Test
    public void test_play_one_turn3() throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(bytes, true);
        BufferedReader input = new BufferedReader(new StringReader("a0h\ns\na0\na1\n"));
        V1ShipFactory shipFactory = new V1ShipFactory();
        Board<Character> board = new BattleShipBoard<Character>(4, 2, 'X');
        BoardTextView view = new BoardTextView(board);

        TextPlayer player1 = new TextPlayer("A", board, input, output, shipFactory);
        player1.doOnePlacement("Submarine", (p) -> player1.shipFactory.makeSubmarine(p));
        assertEquals(false, player1.playOneTurn(board, view, "A", new int[]{0, 1}));
    }
}
