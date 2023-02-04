package edu.duke.yh342.battleship;

import java.util.function.Function;

/**
 * This class handles textual display of
 * a Board (i.e., converting it to a string to show
 * to the user).
 * It supports two ways to display the Board:
 * one for the player's own board, and one for the
 * enemy's board.
 */
public class BoardTextView {
    /**
     * The Board to display
     */
    private final Board<Character> toDisplay;

    /**
     * Constructs a BoardView, given the board it will display.
     *
     * @param toDisplay is the Board to display
     * @throws IllegalArgumentException if the board is larger than 10x26.
     */
    public BoardTextView(Board<Character> toDisplay) {
        this.toDisplay = toDisplay;
        if (toDisplay.getWidth() > 10 || toDisplay.getHeight() > 26) {
            throw new IllegalArgumentException(
                    "Board must be no larger than 10x26, but is " + toDisplay.getWidth() + "x" + toDisplay.getHeight());
        }
    }

    /**
     * Display current board.
     *
     * @param getSquareFn is the function to get the character to display
     * @return String of board display
     */
    public String displayAnyBoard(Function<Coordinate, Character> getSquareFn) {
        StringBuilder ans = new StringBuilder();
        String header = new String(this.makeHeader());
        char base = 'A';
        ans.append(header);
        // Append each row and column
        for (int row = 0; row < toDisplay.getHeight(); row++) {
            ans.append((char) (base + row) + " ");
            for (int column = 0; column < toDisplay.getWidth() - 1; column++) {
                if (getSquareFn.apply(new Coordinate(row, column)) != null) {
                    ans.append((Character) getSquareFn.apply(new Coordinate(row, column)) + "|");
                } else {
                    ans.append(" |");
                }

            }
            // Append last character in each row
            if (getSquareFn.apply(new Coordinate(row, toDisplay.getWidth() - 1)) != null) {
                ans.append((Character) getSquareFn.apply(new Coordinate(row, toDisplay.getWidth() - 1)) + " "
                        + (char) (base + row) + "\n");
            } else {
                ans.append("  " + (char) (base + row) + "\n");
            }
        }
        ans.append(header);
        return ans.toString();
    }

    /**
     * Lambda function for displayAnyBoard
     *
     * @return String of board display function
     */
    public String displayMyOwnBoard() {
        return displayAnyBoard((c) -> toDisplay.whatIsAtForSelf(c));
    }

    /**
     * Lambda function for displayAnyBoard
     *
     * @return String of board display function
     */
    public String displayEnemyBoard() { return displayAnyBoard((c) -> toDisplay.whatIsAtForEnemy(c)); }

    /**
     * This makes the header line, e.g. 0|1|2|3|4\n
     *
     * @return the String that is the header line for the given board
     */
    String makeHeader() {
        StringBuilder ans = new StringBuilder("  "); // README shows two spaces at
        String sep = ""; // start with nothing to separate, then switch to | to separate
        for (int column = 0; column < toDisplay.getWidth(); column++) {
            ans.append(sep);
            ans.append(column);
            sep = "|";
        }
        ans.append("\n");
        return ans.toString();
    }

}
