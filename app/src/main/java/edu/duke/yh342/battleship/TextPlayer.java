package edu.duke.yh342.battleship;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.function.Function;
import java.util.Random;

public class TextPlayer {
    final Board<Character> theBoard;
    final BoardTextView view;
    final BufferedReader inputReader;
    final PrintStream out;
    final AbstractShipFactory<Character> shipFactory;
    String name;
    final ArrayList<String> shipsToPlace;
    final HashMap<String, Function<Placement, Ship<Character>>> shipCreationFns;
    final boolean isAI;
    final Random rand = new Random();
    final int[] randomNumberList;
    int randomNumberPointer = 0;

    /**
     * Initialize a player object on version 1 configuration
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
        if (name.equals("AI")){
            isAI = true;
            randomNumberList = new int[10000];
            setupRandomNumberList();
        }
        else{
            isAI = false;
            randomNumberList = null;
        }
        setupShipCreationMap();
        setupShipCreationList();
    }

    /**
     * Initialize a player object on version 2 configuration
     *
     * @param name        the name of the player
     * @param theBoard    the game board
     * @param inputSource the input source
     * @param out         the output pointer
     */
    public TextPlayer(String name, Board<Character> theBoard, BufferedReader inputSource, PrintStream out, V2ShipFactory factory) {
        this.theBoard = theBoard;
        this.view = new BoardTextView(theBoard);
        this.inputReader = inputSource;
        this.out = out;
        this.shipFactory = factory;
        this.name = name;
        this.shipsToPlace = new ArrayList<>();
        this.shipCreationFns = new HashMap<>();
        if (name.equals("AI")){
            isAI = true;
            randomNumberList = new int[10000];
            setupRandomNumberList();
        }
        else{
            isAI = false;
            randomNumberList = null;
        }
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
        String result = "not null";
        while (result != null) {
            Placement p;
            if (this.isAI) {
                if (shipName.equals("Submarine") || shipName.equals("Destroyer")) {
                    p = aiGeneratePlacement(0);
                } else {
                    p = aiGeneratePlacement(1);
                }
            } else {
                p = readPlacement("Player " + name + " where would you like to put your ship?");
            }   
            // TODO: Don't know how to test this structure
            while (p == null) {
                out.print("\nInvalid placement. Try again.\n");
                p = readPlacement("Player " + name + " where would you like to put your ship?");
            }
            Ship<Character> s = createFn.apply(p);
            result = this.theBoard.tryAddShip(s);
        }
        out.print(this.view.displayMyOwnBoard());
        return true;
    }

    /**
     * Generate a random coordinate for AI
     *
     *  @return the coordinate of that ship
     */
    public Coordinate aiGenerateCoordinate() {
        int x = Integer.MAX_VALUE;
        int y = Integer.MAX_VALUE;
        while (x >= this.theBoard.getHeight()) {
            x = randomNumberList[randomNumberPointer++];
        }
        while (y >= this.theBoard.getWidth()) {
            y = randomNumberList[randomNumberPointer++];
        }
        Coordinate c = new Coordinate(x, y);
        return c;
    }

    /**
     * Generate a placement for AI
     *
     * @param type the type of placement generation
     *            0: generate a placement for H and V placement
     *            1: generate a placement for U, R, D, L placement
     * @return the placement of that ship
     */
    public Placement aiGeneratePlacement(int type) {
        Coordinate c = aiGenerateCoordinate();
        int orientation = randomNumberList[randomNumberPointer++];
        if (type == 0) {
            if (orientation % 2 == 0) {
                return new Placement(c, 'H');
            } else {
                return new Placement(c, 'V');
            }
        } else {
            if (orientation % 4 == 0) {
                return new Placement(c, 'U');
            } else if (orientation % 4 == 1) {
                return new Placement(c, 'R');
            } else if (orientation % 4 == 2) {
                return new Placement(c, 'D');
            } else {
                return new Placement(c, 'L');
            }
        }
    }
    
    /**
     * Generate a choice for AI
     *
     * @param skill the skill of choices left
     *            skill[0]: number of movement skill left
     *            skill[1]: number of scan skill left
     * @return the choice of that ship
     */
    public char aiGenerateChoice(int[] skill) {
        int choice = randomNumberList[randomNumberPointer++];
        if (skill[0] > 0 && skill[1] > 0) {
            if (choice % 3 == 0) {
                return 'F';
            } else if (choice % 3 == 1) {
                return 'M';
            } else {
                return 'S';
            }
        } else if (skill[0] > 0) {
            if (choice % 2 == 0) {
                return 'F';
            } else {
                return 'M';
            }
        } else if (skill[1] > 0) {
            if (choice % 2 == 0) {
                return 'F';
            } else {
                return 'S';
            }
        } else {
            return 'F';
        }
    }

    /**
     * Display the enemy board, print prompts and do one placement
     *
     * @throws IOException if I/O operation fails
     */
    public void doPlacementPhase() throws IOException {
        out.print(this.view.displayMyOwnBoard());
        out.print("\n");
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
        out.print("\n");
        for (int i = 0; i < shipsToPlace.size(); i++) {
            doOnePlacement(shipsToPlace.get(i), shipCreationFns.get(shipsToPlace.get(i)));
            out.print("\n");
        }
        out.print("----------------------------------------------------------\n\n");
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
        // shipsToPlace.addAll(Collections.nCopies(2, "Submarine"));
        // shipsToPlace.addAll(Collections.nCopies(3, "Destroyer"));
        shipsToPlace.addAll(Collections.nCopies(1, "Battleship"));
        // shipsToPlace.addAll(Collections.nCopies(1, "Carrier"));
    }

    /**
     * Set up random number list
     */
    protected void setupRandomNumberList() {
        for (int i = 0; i < this.randomNumberList.length; i++) {
            // Generate a random number between 0 and 19
            this.randomNumberList[i] = rand.nextInt(20);
        }
    }

    /**
     * Check if the player has lost the game
     *
     * @param enemyBoard the enemy board
     * @return true if all ships are sunk
     */
    public boolean isGameEnds(Board<Character> enemyBoard) {
        return enemyBoard.allShipsSunk();
    }

    /**
     * Read and fire at the enemy board
     *
     * @param enemyBoard the enemy board
     * @throws IOException if I/O operation fails
     */
    public void readAndFire(Board<Character> enemyBoard) throws IOException {
        Coordinate c = null;
        if (this.isAI) {
            c = aiGenerateCoordinate();
            out.print("AI attacks " + c.toString() + "\n");
        } else {
            String s = inputReader.readLine();
            while (s == null) {
                out.print("Invalid input, please enter a coordinate (e.g., A2).\n");
                s = inputReader.readLine();
            }
            try {
                c = new Coordinate(s);
            } catch (IllegalArgumentException e) {
                out.print("Invalid coordinate, please refer to the documentation for valid input.\n");
            }
        }
        
        Ship<Character> ship = enemyBoard.fireAt(c);
        if (ship == null) {
            if (this.isAI) {
                out.print("\nAI missed your target!\n\n");
            } else {
                out.print("\nYou missed!\n\n");
            }
        } 
        else {
            if (this.isAI) {
                out.print("\nAI hit a " + ship.getName() + "!\n\n");
            } else {
                out.print("\nYou hit a " + ship.getName() + "!\n\n");
            }
        }
    }


    /**
     * Read and move the battleship
     *
     * @param ownBoard the own board
     * @throws IOException if I/O operation fails
     */
    public boolean readAndMove(Board<Character> ownBoard) throws IOException {
        Coordinate c = null;
        Ship<Character> ship = null;
        while (ship == null) {
            if (this.isAI) {
                c = aiGenerateCoordinate();
            } else {
                String s = inputReader.readLine();
                while (s == null) {
                    out.print("Invalid input, please enter a coordinate (e.g., A2).\n");
                    s = inputReader.readLine();
                }
                c = new Coordinate(s);
            }
            
            // Try to get the ship at the coordinate
            // try {
            ship = ownBoard.getShipAt(c);
            if (ship == null && !this.isAI) {
                out.print("No ship at this coordinate. \n");
                return false;
            }
        }
        
        if (this.isAI) {
            out.print("AI chooses a " + ship.getName() + " at " + c.toString() + ".\n");
        } else {
            out.print("You chose a " + ship.getName() + ", enter the new coordinate of the ship: \n");
        }
        
        Placement newPosition = null;
        // Read the new coordinate
        if (this.isAI) {
            if (ship.getName().equals("Submarine") || ship.getName().equals("Destroyer")) {
                newPosition = aiGeneratePlacement(0);
            } else {
                newPosition = aiGeneratePlacement(1);
            }
        } else {
            String newPositionStr = inputReader.readLine();
            while (newPositionStr == null) {
                out.print("Invalid input, please enter a coordinate with orientation (e.g., A2H).\n");
                newPositionStr = inputReader.readLine();
            }
            newPosition = new Placement(newPositionStr);
        }
        
        // Try to move the ship
        if (ownBoard.moveShipTo(ship, newPosition, ownBoard)) {
            if (this.isAI) {
                out.print("AI's Ship moved to " + newPosition.toString() + ".\n");
            } else {
                out.print("Ship moved successfully.\n");
            }
            return true;
        } else {
            out.print("Ship cannot be moved to this position.\n");
            return false;
        }
        // } catch (IllegalArgumentException e) {
        //     out.print("Invalid coordinate, please refer to the documentation for valid input.\n");
        // }
        // return false;
    }


    /**
     * Read and scan enemy's board
     *
     * @param enemyBoard the enemy board
     * @throws IOException if I/O operation fails
     */
    public boolean readAndScan(Board<Character> enemyBoard) throws IOException {
        Coordinate c;
        
        if (this.isAI) {
            c = aiGenerateCoordinate();
            out.print("AI scans " + c.toString() + ".\n");
        } else {
            String s = inputReader.readLine();
            while (s == null) {
                out.print("Invalid input, please enter a coordinate (e.g., A2).\n");
                s = inputReader.readLine();
            }
            // TODO: check if the coordinate is inside the board
            try {
                c = new Coordinate(s);
            } catch (IllegalArgumentException e) {
                out.print("Invalid coordinate, please refer to the documentation for valid input.\n");
                return false;
            }
        }

        int[] result = enemyBoard.sonarScan(c, enemyBoard);
        out.print("---------------------------------------------------------------------------\n" + 
        "Submarines occupy " + result[0] + " square(s)\n" +
        "Destroyers occupy " + result[1] + " square(s)\n" +
        "Battleships occupy " + result[2] + " square(s)\n" +
        "Carriers occupy " + result[3] + " square(s)\n" +
        "---------------------------------------------------------------------------\n\n");
        return true;
    }


    /**
     * Read choice from the user
     *
     * @throws IOException if I/O operation fails
     */
    public char readChoice(int[] skill) throws IOException {
        String s;
        if (this.isAI) {
            return aiGenerateChoice(skill);
        } else {
            s = inputReader.readLine();
        }
        while (s == null || s.length() != 1 || (s.charAt(0) != 'F' && s.charAt(0) != 'f' && s.charAt(0) != 'S' && s.charAt(0) != 's' && s.charAt(0) != 'M' && s.charAt(0) != 'm')) {
            out.print("Invalid input, please enter a valid choice.\n");
            s = inputReader.readLine();
        }
        return s.charAt(0);
    }


    /**
     * Play one turn of the game
     *
     * @param enemyBoard the enemy board
     * @param enemyView  the enemy board view
     * @param enemyName  the enemy name
     * @param skill[]    the left skill array
     * @return true if the game ends
     * @throws IOException if I/O operation fails
     */
    public boolean playOneTurn(Board<Character> enemyBoard, BoardTextView enemyView, String enemyName, int[] skill) throws IOException{
        if (this.isAI) {
            out.print("New Turn Begins\n\n" + this.view.displayMyBoardWithEnemyNextToIt(enemyView, "AI's ocean", "Player " + enemyName + "'s ocean") + "\n\n");
        } else {
            out.print("New Turn Begins\n\n" + this.view.displayMyBoardWithEnemyNextToIt(enemyView, "Your ocean", "Player " + enemyName + "'s ocean") + "\n\n");
        }
        
        boolean result = false;
        while (!result) {
            if (skill[0] != 0 || skill[1] != 0) {
                out.print("---------------------------------------------------------------------------\n" + 
                        "Possible actions for Player " + name + ":\n\n" + 
                        " F Fire at a square\n");
                if (skill[0] > 0) {
                    out.print(" M Move a ship to another square (" + skill[0] + " remaining)\n");
                }
                if (skill[1] > 0) {
                    out.print(" S Sonar scan (" + skill[1] + " remaining)\n");
                }
                if (!this.isAI) {
                    out.print("\nPlayer " + name + ", what would you like to do?\n");
                }
                out.print("---------------------------------------------------------------------------\n");
                
                switch (readChoice(skill)) {
                    case 'F':
                    case 'f':
                        if (!this.isAI) {
                            out.print("\nPlayer " + name + ", now is your turn to fire:\n");
                        }
                        readAndFire(enemyBoard);
                        result = true;
                        out.print("After Attack:\n" + this.view.displayMyBoardWithEnemyNextToIt(enemyView, "Your ocean", "Player " + enemyName + "'s ocean") + "\n\n");
                        out.print("----------------------------------------------------------\n");
                        break;
                    case 'M':
                    case 'm':
                        if (!this.isAI) {
                            out.print("\nPlayer " + name + ", now is your turn to move:\n");
                        }
                        if (readAndMove(this.theBoard)) {
                            skill[0]--;
                            result = true;
                        }
                        out.print("After Move:\n" + this.view.displayMyBoardWithEnemyNextToIt(enemyView, "Your ocean", "Player " + enemyName + "'s ocean") + "\n\n");
                        out.print("----------------------------------------------------------\n");
                        break;
                    case 'S':
                    case 's':
                        if (!this.isAI) {
                            out.print("\nPlayer " + name + ", now is your turn to scan:\n");
                        }
                        if (readAndScan(enemyBoard)) {
                            skill[1]--;
                            result = true;
                        }
                        break;
                }
            } else {
                if (!this.isAI) {
                    out.print("Player " + name + ", now is your turn to fire:\n");
                }
                readAndFire(enemyBoard);
                out.print("After Attack:\n" + this.view.displayMyBoardWithEnemyNextToIt(enemyView, "Your ocean", "Player " + enemyName + "'s ocean") + "\n\n");
                out.print("----------------------------------------------------------\n");          
            }
        }
        
        
        if (isGameEnds(enemyBoard)) {
            out.print("Player " + enemyName + " has lost the game! Player" + this.name + " wins!\n");
            return true;
        }
        return false;
    }


}
