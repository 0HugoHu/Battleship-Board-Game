/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package edu.duke.yh342.battleship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.StringReader;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.ResourceAccessMode;
import org.junit.jupiter.api.parallel.ResourceLock;
import org.junit.jupiter.api.parallel.Resources;

class AppTest {
  @Test
  public void test_read_placement() throws IOException {
      StringReader sr = new StringReader("B2V\nC8H\na4v\n");
      ByteArrayOutputStream bytes = new ByteArrayOutputStream();
      PrintStream ps = new PrintStream(bytes, true);
      Board<Character> b = new BattleShipBoard<Character>(10, 20);

      App app = new App(b, sr, ps);
      
      String prompt = "Please enter a location for a ship:";
      Placement[] expected = new Placement[3];
      expected[0] = new Placement(new Coordinate(1, 2), 'V');
      expected[1] = new Placement(new Coordinate(2, 8), 'H');
      expected[2] = new Placement(new Coordinate(0, 4), 'V');

      for (int i = 0; i < expected.length; i++) {
          Placement p = app.readPlacement(prompt);
          assertEquals(prompt + "\n", bytes.toString()); //should have printed prompt and newline
          assertEquals(p, expected[i]); //did we get the right Placement back
          
          bytes.reset(); //clear out bytes for next time around
      }
  }

  @Test
  public void test_do_one_replacement() throws IOException {
    StringReader sr = new StringReader("B2V\n");
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream ps = new PrintStream(bytes, true);
    Board<Character> b = new BattleShipBoard<Character>(3, 2);

    String prompt = "Where would you like to put your ship?";

    App app = new App(b, sr, ps);

    app.doOnePlacement();

    assertEquals(prompt + "\n" + "  0|1|2\nA  | |  A\nB  | |s B\n  0|1|2\n", bytes.toString());
  }

  @Test
  @ResourceLock(value = Resources.SYSTEM_OUT, mode = ResourceAccessMode.READ_WRITE)
  void test_main() throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes, true);

    InputStream input = getClass().getClassLoader().getResourceAsStream("input.txt");
    assertNotNull(input);

    InputStream expectedStream = getClass().getClassLoader().getResourceAsStream("output.txt");
    assertNotNull(expectedStream);

    InputStream oldIn = System.in;
    PrintStream oldOut = System.out;

    try {
        System.setIn(input);
        System.setOut(out);
        App.main(new String[0]);
    } finally {
        System.setIn(oldIn);
        System.setOut(oldOut);
    }

    String expected = new String(expectedStream.readAllBytes());
    String actual = bytes.toString();

    assertEquals(expected, actual);
  }

}
