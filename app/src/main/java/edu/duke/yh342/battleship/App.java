/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package edu.duke.yh342.battleship;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.Reader;

/**
 * The main interface of the game, consists of read from input (file),
 * write to output (file), and show the prompts and gameplay.
 */
public class App {
    final Board<Character> theBoard;
    final BoardTextView view;
    final BufferedReader inputReader;
    final PrintStream out;

    /**
     * Initialize App with board, input and output stream
     * 
     * @param theBoard the game board
     * @param inputSource the input source
     * @param out the output pointer
     */
    public App(Board<Character> theBoard, Reader inputSource, PrintStream out) {
        this.theBoard = theBoard;
        this.view = new BoardTextView(theBoard);
        this.inputReader = new BufferedReader(inputSource);
        this.out = out;
    }

    /**
     * Read placement(s) from the file
     * 
     * @param prompt the string that consists coordinate and orientation 
     * @return the placement of that ship
     * @throws IOException if I/O operation fails
     */
    public Placement readPlacement(String prompt) throws IOException {
        this.out.println(prompt);
        String s = inputReader.readLine();
        return new Placement(s);
    }

    /**
     * Do one placement
     * 
     * @throws IOException if I/O operation fails
     */
    public void doOnePlacement() throws IOException {
        Placement p = readPlacement("Where would you like to put your ship?");
        RectangleShip<Character> b = new RectangleShip<Character>(p.getCoordinate(), 's', '*');
        this.theBoard.tryAddShip(b);
        out.print(this.view.displayMyOwnBoard());
    }

    /**
     * Main Entrance: build the game board, set input and output source,
     * and do one replacement
     * 
     * @throws IOException if I/O operation fails
     */
    public static void main(String[] args) throws IOException {
        Board<Character> b = new BattleShipBoard<Character>(10, 20);
        App app = new App(b, new InputStreamReader(System.in), System.out);
        app.doOnePlacement();
    }
}
