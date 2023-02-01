package edu.duke.yh342.battleship;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.StringReader;

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
        TextPlayer player = createTextPlayer(10, 20, "B2V\nC8H\na4v\n", bytes);

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

        TextPlayer player = createTextPlayer(3, 2, "B0H\n", bytes);

        player.doOnePlacement();

        // Should have equal printed board
        assertEquals(prompt + "\n" + "  0|1|2\nA  | |  A\nB d|d|d B\n  0|1|2\n", bytes.toString());
    }

    @Test
    public void test_do_placement_phrase() throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        String prompt1 = "Player A: you are going to place the following ships (which are all" +
                "rectangular). For each ship, type the coordinate of the upper left" +
                "side of the ship, followed by either H (for horizontal) or V (for" +
                "vertical). For example M4H would place a ship horizontally starting" +
                "at M4 and going to the right. You have\n\n" +
                "2 \"Submarines\" ships that are 1x2\n" +
                "3 \"Destroyers\" that are 1x3\n" +
                "3 \"Battleships\" that are 1x4\n" +
                "2 \"Carriers\" that are 1x6\n";
        String prompt2 = "Player A where would you like to put your ship?";


        TextPlayer player = createTextPlayer(3, 2, "B0H\n", bytes);

        player.doPlacementPhase();
        // Should have equal printed board
        assertEquals("  0|1|2\nA  | |  A\nB  | |  B\n  0|1|2\n" + prompt1 + "\n" + prompt2 + "\n" + "  0|1|2\nA  | |  A\nB d|d|d B\n  0|1|2\n", bytes.toString());
    }

    /**
     * Create a textplayer helper to generate it for testing
     *
     * @param w         the width of the board
     * @param h         the height of the board
     * @param inputData the input string
     * @param bytes     the output pointer
     */
    private TextPlayer createTextPlayer(int w, int h, String inputData, OutputStream bytes) {
        BufferedReader input = new BufferedReader(new StringReader(inputData));
        PrintStream output = new PrintStream(bytes, true);
        Board<Character> board = new BattleShipBoard<Character>(w, h);
        V1ShipFactory shipFactory = new V1ShipFactory();
        return new TextPlayer("A", board, input, output, shipFactory);
    }

}
