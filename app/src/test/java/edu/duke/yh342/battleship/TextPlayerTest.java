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
        boolean result = player2.doOnePlacement("Carrier", (p) -> player.shipFactory.makeCarrier(p));
        assertEquals(false, result);
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
        String inputData1 = "a1h\n" +
                "b1h\n" +
                "c1h\n" +
                "d1h\n" +
                "e1h\n" +
                "f1h\n" +
                "g1h\n" +
                "h1h\n" +
                "i1h\n" +
                "j1h\n";

        String inputData2 = "k1h\n" +
                "l1h\n" +
                "m1h\n" +
                "n1h\n" +
                "o1h\n" +
                "p1h\n" +
                "q1h\n" +
                "r1h\n" +
                "s1h\n" +
                "t1h\n";

        String expectedOutput = "  0|1|2|3|4|5|6|7|8|9\n" +
                "A  | | | | | | | | |  A\n" +
                "B  | | | | | | | | |  B\n" +
                "C  | | | | | | | | |  C\n" +
                "D  | | | | | | | | |  D\n" +
                "E  | | | | | | | | |  E\n" +
                "F  | | | | | | | | |  F\n" +
                "G  | | | | | | | | |  G\n" +
                "H  | | | | | | | | |  H\n" +
                "I  | | | | | | | | |  I\n" +
                "J  | | | | | | | | |  J\n" +
                "K  | | | | | | | | |  K\n" +
                "L  | | | | | | | | |  L\n" +
                "M  | | | | | | | | |  M\n" +
                "N  | | | | | | | | |  N\n" +
                "O  | | | | | | | | |  O\n" +
                "P  | | | | | | | | |  P\n" +
                "Q  | | | | | | | | |  Q\n" +
                "R  | | | | | | | | |  R\n" +
                "S  | | | | | | | | |  S\n" +
                "T  | | | | | | | | |  T\n" +
                "  0|1|2|3|4|5|6|7|8|9\n" +
                "Player A: you are going to place the following ships (which are allrectangular). For each ship, type the coordinate of the upper leftside of the ship, followed by either H (for horizontal) or V (forvertical). For example M4H would place a ship horizontally startingat M4 and going to the right. You have\n" +
                "\n" +
                "2 \"Submarines\" ships that are 1x2\n" +
                "3 \"Destroyers\" that are 1x3\n" +
                "3 \"Battleships\" that are 1x4\n" +
                "2 \"Carriers\" that are 1x6\n" +
                "\n" +
                "Player A where would you like to put your ship?\n" +
                "  0|1|2|3|4|5|6|7|8|9\n" +
                "A  |s|s| | | | | | |  A\n" +
                "B  | | | | | | | | |  B\n" +
                "C  | | | | | | | | |  C\n" +
                "D  | | | | | | | | |  D\n" +
                "E  | | | | | | | | |  E\n" +
                "F  | | | | | | | | |  F\n" +
                "G  | | | | | | | | |  G\n" +
                "H  | | | | | | | | |  H\n" +
                "I  | | | | | | | | |  I\n" +
                "J  | | | | | | | | |  J\n" +
                "K  | | | | | | | | |  K\n" +
                "L  | | | | | | | | |  L\n" +
                "M  | | | | | | | | |  M\n" +
                "N  | | | | | | | | |  N\n" +
                "O  | | | | | | | | |  O\n" +
                "P  | | | | | | | | |  P\n" +
                "Q  | | | | | | | | |  Q\n" +
                "R  | | | | | | | | |  R\n" +
                "S  | | | | | | | | |  S\n" +
                "T  | | | | | | | | |  T\n" +
                "  0|1|2|3|4|5|6|7|8|9\n" +
                "Player A where would you like to put your ship?\n" +
                "  0|1|2|3|4|5|6|7|8|9\n" +
                "A  |s|s| | | | | | |  A\n" +
                "B  |s|s| | | | | | |  B\n" +
                "C  | | | | | | | | |  C\n" +
                "D  | | | | | | | | |  D\n" +
                "E  | | | | | | | | |  E\n" +
                "F  | | | | | | | | |  F\n" +
                "G  | | | | | | | | |  G\n" +
                "H  | | | | | | | | |  H\n" +
                "I  | | | | | | | | |  I\n" +
                "J  | | | | | | | | |  J\n" +
                "K  | | | | | | | | |  K\n" +
                "L  | | | | | | | | |  L\n" +
                "M  | | | | | | | | |  M\n" +
                "N  | | | | | | | | |  N\n" +
                "O  | | | | | | | | |  O\n" +
                "P  | | | | | | | | |  P\n" +
                "Q  | | | | | | | | |  Q\n" +
                "R  | | | | | | | | |  R\n" +
                "S  | | | | | | | | |  S\n" +
                "T  | | | | | | | | |  T\n" +
                "  0|1|2|3|4|5|6|7|8|9\n" +
                "Player A where would you like to put your ship?\n" +
                "  0|1|2|3|4|5|6|7|8|9\n" +
                "A  |s|s| | | | | | |  A\n" +
                "B  |s|s| | | | | | |  B\n" +
                "C  |d|d|d| | | | | |  C\n" +
                "D  | | | | | | | | |  D\n" +
                "E  | | | | | | | | |  E\n" +
                "F  | | | | | | | | |  F\n" +
                "G  | | | | | | | | |  G\n" +
                "H  | | | | | | | | |  H\n" +
                "I  | | | | | | | | |  I\n" +
                "J  | | | | | | | | |  J\n" +
                "K  | | | | | | | | |  K\n" +
                "L  | | | | | | | | |  L\n" +
                "M  | | | | | | | | |  M\n" +
                "N  | | | | | | | | |  N\n" +
                "O  | | | | | | | | |  O\n" +
                "P  | | | | | | | | |  P\n" +
                "Q  | | | | | | | | |  Q\n" +
                "R  | | | | | | | | |  R\n" +
                "S  | | | | | | | | |  S\n" +
                "T  | | | | | | | | |  T\n" +
                "  0|1|2|3|4|5|6|7|8|9\n" +
                "Player A where would you like to put your ship?\n" +
                "  0|1|2|3|4|5|6|7|8|9\n" +
                "A  |s|s| | | | | | |  A\n" +
                "B  |s|s| | | | | | |  B\n" +
                "C  |d|d|d| | | | | |  C\n" +
                "D  |d|d|d| | | | | |  D\n" +
                "E  | | | | | | | | |  E\n" +
                "F  | | | | | | | | |  F\n" +
                "G  | | | | | | | | |  G\n" +
                "H  | | | | | | | | |  H\n" +
                "I  | | | | | | | | |  I\n" +
                "J  | | | | | | | | |  J\n" +
                "K  | | | | | | | | |  K\n" +
                "L  | | | | | | | | |  L\n" +
                "M  | | | | | | | | |  M\n" +
                "N  | | | | | | | | |  N\n" +
                "O  | | | | | | | | |  O\n" +
                "P  | | | | | | | | |  P\n" +
                "Q  | | | | | | | | |  Q\n" +
                "R  | | | | | | | | |  R\n" +
                "S  | | | | | | | | |  S\n" +
                "T  | | | | | | | | |  T\n" +
                "  0|1|2|3|4|5|6|7|8|9\n" +
                "Player A where would you like to put your ship?\n" +
                "  0|1|2|3|4|5|6|7|8|9\n" +
                "A  |s|s| | | | | | |  A\n" +
                "B  |s|s| | | | | | |  B\n" +
                "C  |d|d|d| | | | | |  C\n" +
                "D  |d|d|d| | | | | |  D\n" +
                "E  |d|d|d| | | | | |  E\n" +
                "F  | | | | | | | | |  F\n" +
                "G  | | | | | | | | |  G\n" +
                "H  | | | | | | | | |  H\n" +
                "I  | | | | | | | | |  I\n" +
                "J  | | | | | | | | |  J\n" +
                "K  | | | | | | | | |  K\n" +
                "L  | | | | | | | | |  L\n" +
                "M  | | | | | | | | |  M\n" +
                "N  | | | | | | | | |  N\n" +
                "O  | | | | | | | | |  O\n" +
                "P  | | | | | | | | |  P\n" +
                "Q  | | | | | | | | |  Q\n" +
                "R  | | | | | | | | |  R\n" +
                "S  | | | | | | | | |  S\n" +
                "T  | | | | | | | | |  T\n" +
                "  0|1|2|3|4|5|6|7|8|9\n" +
                "Player A where would you like to put your ship?\n" +
                "  0|1|2|3|4|5|6|7|8|9\n" +
                "A  |s|s| | | | | | |  A\n" +
                "B  |s|s| | | | | | |  B\n" +
                "C  |d|d|d| | | | | |  C\n" +
                "D  |d|d|d| | | | | |  D\n" +
                "E  |d|d|d| | | | | |  E\n" +
                "F  |b|b|b|b| | | | |  F\n" +
                "G  | | | | | | | | |  G\n" +
                "H  | | | | | | | | |  H\n" +
                "I  | | | | | | | | |  I\n" +
                "J  | | | | | | | | |  J\n" +
                "K  | | | | | | | | |  K\n" +
                "L  | | | | | | | | |  L\n" +
                "M  | | | | | | | | |  M\n" +
                "N  | | | | | | | | |  N\n" +
                "O  | | | | | | | | |  O\n" +
                "P  | | | | | | | | |  P\n" +
                "Q  | | | | | | | | |  Q\n" +
                "R  | | | | | | | | |  R\n" +
                "S  | | | | | | | | |  S\n" +
                "T  | | | | | | | | |  T\n" +
                "  0|1|2|3|4|5|6|7|8|9\n" +
                "Player A where would you like to put your ship?\n" +
                "  0|1|2|3|4|5|6|7|8|9\n" +
                "A  |s|s| | | | | | |  A\n" +
                "B  |s|s| | | | | | |  B\n" +
                "C  |d|d|d| | | | | |  C\n" +
                "D  |d|d|d| | | | | |  D\n" +
                "E  |d|d|d| | | | | |  E\n" +
                "F  |b|b|b|b| | | | |  F\n" +
                "G  |b|b|b|b| | | | |  G\n" +
                "H  | | | | | | | | |  H\n" +
                "I  | | | | | | | | |  I\n" +
                "J  | | | | | | | | |  J\n" +
                "K  | | | | | | | | |  K\n" +
                "L  | | | | | | | | |  L\n" +
                "M  | | | | | | | | |  M\n" +
                "N  | | | | | | | | |  N\n" +
                "O  | | | | | | | | |  O\n" +
                "P  | | | | | | | | |  P\n" +
                "Q  | | | | | | | | |  Q\n" +
                "R  | | | | | | | | |  R\n" +
                "S  | | | | | | | | |  S\n" +
                "T  | | | | | | | | |  T\n" +
                "  0|1|2|3|4|5|6|7|8|9\n" +
                "Player A where would you like to put your ship?\n" +
                "  0|1|2|3|4|5|6|7|8|9\n" +
                "A  |s|s| | | | | | |  A\n" +
                "B  |s|s| | | | | | |  B\n" +
                "C  |d|d|d| | | | | |  C\n" +
                "D  |d|d|d| | | | | |  D\n" +
                "E  |d|d|d| | | | | |  E\n" +
                "F  |b|b|b|b| | | | |  F\n" +
                "G  |b|b|b|b| | | | |  G\n" +
                "H  |b|b|b|b| | | | |  H\n" +
                "I  | | | | | | | | |  I\n" +
                "J  | | | | | | | | |  J\n" +
                "K  | | | | | | | | |  K\n" +
                "L  | | | | | | | | |  L\n" +
                "M  | | | | | | | | |  M\n" +
                "N  | | | | | | | | |  N\n" +
                "O  | | | | | | | | |  O\n" +
                "P  | | | | | | | | |  P\n" +
                "Q  | | | | | | | | |  Q\n" +
                "R  | | | | | | | | |  R\n" +
                "S  | | | | | | | | |  S\n" +
                "T  | | | | | | | | |  T\n" +
                "  0|1|2|3|4|5|6|7|8|9\n" +
                "Player A where would you like to put your ship?\n" +
                "  0|1|2|3|4|5|6|7|8|9\n" +
                "A  |s|s| | | | | | |  A\n" +
                "B  |s|s| | | | | | |  B\n" +
                "C  |d|d|d| | | | | |  C\n" +
                "D  |d|d|d| | | | | |  D\n" +
                "E  |d|d|d| | | | | |  E\n" +
                "F  |b|b|b|b| | | | |  F\n" +
                "G  |b|b|b|b| | | | |  G\n" +
                "H  |b|b|b|b| | | | |  H\n" +
                "I  |c|c|c|c|c|c| | |  I\n" +
                "J  | | | | | | | | |  J\n" +
                "K  | | | | | | | | |  K\n" +
                "L  | | | | | | | | |  L\n" +
                "M  | | | | | | | | |  M\n" +
                "N  | | | | | | | | |  N\n" +
                "O  | | | | | | | | |  O\n" +
                "P  | | | | | | | | |  P\n" +
                "Q  | | | | | | | | |  Q\n" +
                "R  | | | | | | | | |  R\n" +
                "S  | | | | | | | | |  S\n" +
                "T  | | | | | | | | |  T\n" +
                "  0|1|2|3|4|5|6|7|8|9\n" +
                "Player A where would you like to put your ship?\n" +
                "  0|1|2|3|4|5|6|7|8|9\n" +
                "A  |s|s| | | | | | |  A\n" +
                "B  |s|s| | | | | | |  B\n" +
                "C  |d|d|d| | | | | |  C\n" +
                "D  |d|d|d| | | | | |  D\n" +
                "E  |d|d|d| | | | | |  E\n" +
                "F  |b|b|b|b| | | | |  F\n" +
                "G  |b|b|b|b| | | | |  G\n" +
                "H  |b|b|b|b| | | | |  H\n" +
                "I  |c|c|c|c|c|c| | |  I\n" +
                "J  |c|c|c|c|c|c| | |  J\n" +
                "K  | | | | | | | | |  K\n" +
                "L  | | | | | | | | |  L\n" +
                "M  | | | | | | | | |  M\n" +
                "N  | | | | | | | | |  N\n" +
                "O  | | | | | | | | |  O\n" +
                "P  | | | | | | | | |  P\n" +
                "Q  | | | | | | | | |  Q\n" +
                "R  | | | | | | | | |  R\n" +
                "S  | | | | | | | | |  S\n" +
                "T  | | | | | | | | |  T\n" +
                "  0|1|2|3|4|5|6|7|8|9\n" +
                "  0|1|2|3|4|5|6|7|8|9\n" +
                "A  | | | | | | | | |  A\n" +
                "B  | | | | | | | | |  B\n" +
                "C  | | | | | | | | |  C\n" +
                "D  | | | | | | | | |  D\n" +
                "E  | | | | | | | | |  E\n" +
                "F  | | | | | | | | |  F\n" +
                "G  | | | | | | | | |  G\n" +
                "H  | | | | | | | | |  H\n" +
                "I  | | | | | | | | |  I\n" +
                "J  | | | | | | | | |  J\n" +
                "K  | | | | | | | | |  K\n" +
                "L  | | | | | | | | |  L\n" +
                "M  | | | | | | | | |  M\n" +
                "N  | | | | | | | | |  N\n" +
                "O  | | | | | | | | |  O\n" +
                "P  | | | | | | | | |  P\n" +
                "Q  | | | | | | | | |  Q\n" +
                "R  | | | | | | | | |  R\n" +
                "S  | | | | | | | | |  S\n" +
                "T  | | | | | | | | |  T\n" +
                "  0|1|2|3|4|5|6|7|8|9\n" +
                "Player B: you are going to place the following ships (which are allrectangular). For each ship, type the coordinate of the upper leftside of the ship, followed by either H (for horizontal) or V (forvertical). For example M4H would place a ship horizontally startingat M4 and going to the right. You have\n" +
                "\n" +
                "2 \"Submarines\" ships that are 1x2\n" +
                "3 \"Destroyers\" that are 1x3\n" +
                "3 \"Battleships\" that are 1x4\n" +
                "2 \"Carriers\" that are 1x6\n" +
                "\n" +
                "Player B where would you like to put your ship?\n" +
                "  0|1|2|3|4|5|6|7|8|9\n" +
                "A  | | | | | | | | |  A\n" +
                "B  | | | | | | | | |  B\n" +
                "C  | | | | | | | | |  C\n" +
                "D  | | | | | | | | |  D\n" +
                "E  | | | | | | | | |  E\n" +
                "F  | | | | | | | | |  F\n" +
                "G  | | | | | | | | |  G\n" +
                "H  | | | | | | | | |  H\n" +
                "I  | | | | | | | | |  I\n" +
                "J  | | | | | | | | |  J\n" +
                "K  |s|s| | | | | | |  K\n" +
                "L  | | | | | | | | |  L\n" +
                "M  | | | | | | | | |  M\n" +
                "N  | | | | | | | | |  N\n" +
                "O  | | | | | | | | |  O\n" +
                "P  | | | | | | | | |  P\n" +
                "Q  | | | | | | | | |  Q\n" +
                "R  | | | | | | | | |  R\n" +
                "S  | | | | | | | | |  S\n" +
                "T  | | | | | | | | |  T\n" +
                "  0|1|2|3|4|5|6|7|8|9\n" +
                "Player B where would you like to put your ship?\n" +
                "  0|1|2|3|4|5|6|7|8|9\n" +
                "A  | | | | | | | | |  A\n" +
                "B  | | | | | | | | |  B\n" +
                "C  | | | | | | | | |  C\n" +
                "D  | | | | | | | | |  D\n" +
                "E  | | | | | | | | |  E\n" +
                "F  | | | | | | | | |  F\n" +
                "G  | | | | | | | | |  G\n" +
                "H  | | | | | | | | |  H\n" +
                "I  | | | | | | | | |  I\n" +
                "J  | | | | | | | | |  J\n" +
                "K  |s|s| | | | | | |  K\n" +
                "L  |s|s| | | | | | |  L\n" +
                "M  | | | | | | | | |  M\n" +
                "N  | | | | | | | | |  N\n" +
                "O  | | | | | | | | |  O\n" +
                "P  | | | | | | | | |  P\n" +
                "Q  | | | | | | | | |  Q\n" +
                "R  | | | | | | | | |  R\n" +
                "S  | | | | | | | | |  S\n" +
                "T  | | | | | | | | |  T\n" +
                "  0|1|2|3|4|5|6|7|8|9\n" +
                "Player B where would you like to put your ship?\n" +
                "  0|1|2|3|4|5|6|7|8|9\n" +
                "A  | | | | | | | | |  A\n" +
                "B  | | | | | | | | |  B\n" +
                "C  | | | | | | | | |  C\n" +
                "D  | | | | | | | | |  D\n" +
                "E  | | | | | | | | |  E\n" +
                "F  | | | | | | | | |  F\n" +
                "G  | | | | | | | | |  G\n" +
                "H  | | | | | | | | |  H\n" +
                "I  | | | | | | | | |  I\n" +
                "J  | | | | | | | | |  J\n" +
                "K  |s|s| | | | | | |  K\n" +
                "L  |s|s| | | | | | |  L\n" +
                "M  |d|d|d| | | | | |  M\n" +
                "N  | | | | | | | | |  N\n" +
                "O  | | | | | | | | |  O\n" +
                "P  | | | | | | | | |  P\n" +
                "Q  | | | | | | | | |  Q\n" +
                "R  | | | | | | | | |  R\n" +
                "S  | | | | | | | | |  S\n" +
                "T  | | | | | | | | |  T\n" +
                "  0|1|2|3|4|5|6|7|8|9\n" +
                "Player B where would you like to put your ship?\n" +
                "  0|1|2|3|4|5|6|7|8|9\n" +
                "A  | | | | | | | | |  A\n" +
                "B  | | | | | | | | |  B\n" +
                "C  | | | | | | | | |  C\n" +
                "D  | | | | | | | | |  D\n" +
                "E  | | | | | | | | |  E\n" +
                "F  | | | | | | | | |  F\n" +
                "G  | | | | | | | | |  G\n" +
                "H  | | | | | | | | |  H\n" +
                "I  | | | | | | | | |  I\n" +
                "J  | | | | | | | | |  J\n" +
                "K  |s|s| | | | | | |  K\n" +
                "L  |s|s| | | | | | |  L\n" +
                "M  |d|d|d| | | | | |  M\n" +
                "N  |d|d|d| | | | | |  N\n" +
                "O  | | | | | | | | |  O\n" +
                "P  | | | | | | | | |  P\n" +
                "Q  | | | | | | | | |  Q\n" +
                "R  | | | | | | | | |  R\n" +
                "S  | | | | | | | | |  S\n" +
                "T  | | | | | | | | |  T\n" +
                "  0|1|2|3|4|5|6|7|8|9\n" +
                "Player B where would you like to put your ship?\n" +
                "  0|1|2|3|4|5|6|7|8|9\n" +
                "A  | | | | | | | | |  A\n" +
                "B  | | | | | | | | |  B\n" +
                "C  | | | | | | | | |  C\n" +
                "D  | | | | | | | | |  D\n" +
                "E  | | | | | | | | |  E\n" +
                "F  | | | | | | | | |  F\n" +
                "G  | | | | | | | | |  G\n" +
                "H  | | | | | | | | |  H\n" +
                "I  | | | | | | | | |  I\n" +
                "J  | | | | | | | | |  J\n" +
                "K  |s|s| | | | | | |  K\n" +
                "L  |s|s| | | | | | |  L\n" +
                "M  |d|d|d| | | | | |  M\n" +
                "N  |d|d|d| | | | | |  N\n" +
                "O  |d|d|d| | | | | |  O\n" +
                "P  | | | | | | | | |  P\n" +
                "Q  | | | | | | | | |  Q\n" +
                "R  | | | | | | | | |  R\n" +
                "S  | | | | | | | | |  S\n" +
                "T  | | | | | | | | |  T\n" +
                "  0|1|2|3|4|5|6|7|8|9\n" +
                "Player B where would you like to put your ship?\n" +
                "  0|1|2|3|4|5|6|7|8|9\n" +
                "A  | | | | | | | | |  A\n" +
                "B  | | | | | | | | |  B\n" +
                "C  | | | | | | | | |  C\n" +
                "D  | | | | | | | | |  D\n" +
                "E  | | | | | | | | |  E\n" +
                "F  | | | | | | | | |  F\n" +
                "G  | | | | | | | | |  G\n" +
                "H  | | | | | | | | |  H\n" +
                "I  | | | | | | | | |  I\n" +
                "J  | | | | | | | | |  J\n" +
                "K  |s|s| | | | | | |  K\n" +
                "L  |s|s| | | | | | |  L\n" +
                "M  |d|d|d| | | | | |  M\n" +
                "N  |d|d|d| | | | | |  N\n" +
                "O  |d|d|d| | | | | |  O\n" +
                "P  |b|b|b|b| | | | |  P\n" +
                "Q  | | | | | | | | |  Q\n" +
                "R  | | | | | | | | |  R\n" +
                "S  | | | | | | | | |  S\n" +
                "T  | | | | | | | | |  T\n" +
                "  0|1|2|3|4|5|6|7|8|9\n" +
                "Player B where would you like to put your ship?\n" +
                "  0|1|2|3|4|5|6|7|8|9\n" +
                "A  | | | | | | | | |  A\n" +
                "B  | | | | | | | | |  B\n" +
                "C  | | | | | | | | |  C\n" +
                "D  | | | | | | | | |  D\n" +
                "E  | | | | | | | | |  E\n" +
                "F  | | | | | | | | |  F\n" +
                "G  | | | | | | | | |  G\n" +
                "H  | | | | | | | | |  H\n" +
                "I  | | | | | | | | |  I\n" +
                "J  | | | | | | | | |  J\n" +
                "K  |s|s| | | | | | |  K\n" +
                "L  |s|s| | | | | | |  L\n" +
                "M  |d|d|d| | | | | |  M\n" +
                "N  |d|d|d| | | | | |  N\n" +
                "O  |d|d|d| | | | | |  O\n" +
                "P  |b|b|b|b| | | | |  P\n" +
                "Q  |b|b|b|b| | | | |  Q\n" +
                "R  | | | | | | | | |  R\n" +
                "S  | | | | | | | | |  S\n" +
                "T  | | | | | | | | |  T\n" +
                "  0|1|2|3|4|5|6|7|8|9\n" +
                "Player B where would you like to put your ship?\n" +
                "  0|1|2|3|4|5|6|7|8|9\n" +
                "A  | | | | | | | | |  A\n" +
                "B  | | | | | | | | |  B\n" +
                "C  | | | | | | | | |  C\n" +
                "D  | | | | | | | | |  D\n" +
                "E  | | | | | | | | |  E\n" +
                "F  | | | | | | | | |  F\n" +
                "G  | | | | | | | | |  G\n" +
                "H  | | | | | | | | |  H\n" +
                "I  | | | | | | | | |  I\n" +
                "J  | | | | | | | | |  J\n" +
                "K  |s|s| | | | | | |  K\n" +
                "L  |s|s| | | | | | |  L\n" +
                "M  |d|d|d| | | | | |  M\n" +
                "N  |d|d|d| | | | | |  N\n" +
                "O  |d|d|d| | | | | |  O\n" +
                "P  |b|b|b|b| | | | |  P\n" +
                "Q  |b|b|b|b| | | | |  Q\n" +
                "R  |b|b|b|b| | | | |  R\n" +
                "S  | | | | | | | | |  S\n" +
                "T  | | | | | | | | |  T\n" +
                "  0|1|2|3|4|5|6|7|8|9\n" +
                "Player B where would you like to put your ship?\n" +
                "  0|1|2|3|4|5|6|7|8|9\n" +
                "A  | | | | | | | | |  A\n" +
                "B  | | | | | | | | |  B\n" +
                "C  | | | | | | | | |  C\n" +
                "D  | | | | | | | | |  D\n" +
                "E  | | | | | | | | |  E\n" +
                "F  | | | | | | | | |  F\n" +
                "G  | | | | | | | | |  G\n" +
                "H  | | | | | | | | |  H\n" +
                "I  | | | | | | | | |  I\n" +
                "J  | | | | | | | | |  J\n" +
                "K  |s|s| | | | | | |  K\n" +
                "L  |s|s| | | | | | |  L\n" +
                "M  |d|d|d| | | | | |  M\n" +
                "N  |d|d|d| | | | | |  N\n" +
                "O  |d|d|d| | | | | |  O\n" +
                "P  |b|b|b|b| | | | |  P\n" +
                "Q  |b|b|b|b| | | | |  Q\n" +
                "R  |b|b|b|b| | | | |  R\n" +
                "S  |c|c|c|c|c|c| | |  S\n" +
                "T  | | | | | | | | |  T\n" +
                "  0|1|2|3|4|5|6|7|8|9\n" +
                "Player B where would you like to put your ship?\n" +
                "  0|1|2|3|4|5|6|7|8|9\n" +
                "A  | | | | | | | | |  A\n" +
                "B  | | | | | | | | |  B\n" +
                "C  | | | | | | | | |  C\n" +
                "D  | | | | | | | | |  D\n" +
                "E  | | | | | | | | |  E\n" +
                "F  | | | | | | | | |  F\n" +
                "G  | | | | | | | | |  G\n" +
                "H  | | | | | | | | |  H\n" +
                "I  | | | | | | | | |  I\n" +
                "J  | | | | | | | | |  J\n" +
                "K  |s|s| | | | | | |  K\n" +
                "L  |s|s| | | | | | |  L\n" +
                "M  |d|d|d| | | | | |  M\n" +
                "N  |d|d|d| | | | | |  N\n" +
                "O  |d|d|d| | | | | |  O\n" +
                "P  |b|b|b|b| | | | |  P\n" +
                "Q  |b|b|b|b| | | | |  Q\n" +
                "R  |b|b|b|b| | | | |  R\n" +
                "S  |c|c|c|c|c|c| | |  S\n" +
                "T  |c|c|c|c|c|c| | |  T\n" +
                "  0|1|2|3|4|5|6|7|8|9\n";

        TextPlayer player1 = createTextPlayer("A", 10, 20, inputData1, bytes);
        TextPlayer player2 = createTextPlayer("B", 10, 20, inputData2, bytes);
        player1.doPlacementPhase();
        player2.doPlacementPhase();
        // Should have equal printed board
        assertEquals(expectedOutput, bytes.toString());
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
    public void test_check_game_ends() throws IOException{
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(bytes, true);
        BufferedReader input = new BufferedReader(new StringReader("a0h"));
        V1ShipFactory shipFactory = new V1ShipFactory();
        Board<Character> board = new BattleShipBoard<Character>(4, 2, 'X');

        TextPlayer player1 = new TextPlayer("A", board, input, output, shipFactory);

        player1.doOnePlacement("Submarine", (p) -> player1.shipFactory.makeSubmarine(p));
        assertEquals(false, player1.isGameEnds());

        board.fireAt(new Coordinate(0, 0));
        board.fireAt(new Coordinate(0, 1));

        assertEquals(true, player1.isGameEnds());
    }


}
