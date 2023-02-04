package edu.duke.yh342.battleship;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.function.Function;

public class TextPlayer {
    final Board<Character> theBoard;
    final BoardTextView view;
    final BufferedReader inputReader;
    final PrintStream out;
    final AbstractShipFactory<Character> shipFactory;
    String name;
    final ArrayList<String> shipsToPlace;
    final HashMap<String, Function<Placement, Ship<Character>>> shipCreationFns;

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
        this.shipsToPlace = new ArrayList<>();
        this.shipCreationFns = new HashMap<>();
        setupShipCreationMap();
        setupShipCreationList();
    }

    /**
     * Read placement(s) from the file
     *
     * @param prompt the string that consists coordinate and orientation
     * @return the placement of that ship
     * @throws IOException if read nothing or error occurs
     */
    public Placement readPlacement(String prompt) throws IOException {
        this.out.println(prompt);
        String s = inputReader.readLine();
        if (s == null) {
            throw new IOException();
        }
        Placement p = null;
        try {
            p = new Placement(s);
        } catch (IllegalArgumentException e) {
            return null;
        }
        return p;
    }

    /**
     * Do one placement
     *
     * @param shipName the name of ship
     * @param createFn an object an apply method that takes a Placement and returns a Ship<Character>
     * @throws IOException if I/O operation fails
     */
    public boolean doOnePlacement(String shipName, Function<Placement, Ship<Character>> createFn) throws IOException {
        Placement p = readPlacement("Player " + name + " where would you like to put your ship?");
        // TODO: Should consider while prompts

        Ship<Character> s = createFn.apply(p);
        String result = this.theBoard.tryAddShip(s);
        if (result != null) {
            return false;
        }
        out.print(this.view.displayMyOwnBoard());
        return true;
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
        for (int i = 0; i < shipsToPlace.size(); i++) {
            doOnePlacement(shipsToPlace.get(i), shipCreationFns.get(shipsToPlace.get(i)));
        }
    }

    /**
     * Set up Java lambda expressions
     */
    protected void setupShipCreationMap() {
        shipCreationFns.put("Submarine", (p) -> shipFactory.makeSubmarine(p));
        shipCreationFns.put("Destroyer", (p) -> shipFactory.makeDestroyer(p));
        shipCreationFns.put("Battleship", (p) -> shipFactory.makeBattleship(p));
        shipCreationFns.put("Carrier", (p) -> shipFactory.makeCarrier(p));
    }

    /**
     * Add all ships into creation list
     */
    protected void setupShipCreationList() {
        shipsToPlace.addAll(Collections.nCopies(2, "Submarine"));
        shipsToPlace.addAll(Collections.nCopies(3, "Destroyer"));
        shipsToPlace.addAll(Collections.nCopies(3, "Battleship"));
        shipsToPlace.addAll(Collections.nCopies(2, "Carrier"));
    }

    /**
     * Check if the player has lost the game
     *
     * @return true if all ships are sunk
     */
    public boolean isGameEnds() {
        return this.theBoard.allShipsSunk();
    }

}
