package edu.duke.yh342.battleship;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Reader;
import java.io.StringReader;

public class TextPlayer {
    final Board<Character> theBoard;
    final BoardTextView view;
    final BufferedReader inputReader;
    final PrintStream out;
    final AbstractShipFactory<Character> shipFactory;
    String name;

    /**
     * Initialize a player object
     *
     * @param name        the name of the player
     * @param theBoard    the game board
     * @param inputSource the input source
     * @param out         the output pointer
     */
    public TextPlayer(String name, Board<Character> theBoard, BufferedReader inputSource, PrintStream out, V1ShipFactory factory) {
        this.theBoard = theBoard;
        this.view = new BoardTextView(theBoard);
        this.inputReader = inputSource;
        this.out = out;
        this.shipFactory = factory;
        this.name = name;
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
        Placement p = readPlacement("Player " + name + " where would you like to put your ship?");
        Ship<Character> s = shipFactory.makeDestroyer(p);
        this.theBoard.tryAddShip(s);
        out.print(this.view.displayMyOwnBoard());
    }

    /**
     * Display the enemy board, print prompts and do one placement
     *
     * @throws IOException if I/O operation fails
     */
    public void doPlacementPhase() throws IOException {
        out.print(this.view.displayMyOwnBoard());
        String prompt = "Player " + name + ": you are going to place the following ships (which are all" +
                "rectangular). For each ship, type the coordinate of the upper left" +
                "side of the ship, followed by either H (for horizontal) or V (for" +
                "vertical). For example M4H would place a ship horizontally starting" +
                "at M4 and going to the right. You have\n\n" +
                "2 \"Submarines\" ships that are 1x2\n" +
                "3 \"Destroyers\" that are 1x3\n" +
                "3 \"Battleships\" that are 1x4\n" +
                "2 \"Carriers\" that are 1x6\n\n";
        out.print(prompt);
        doOnePlacement();
    }

}
