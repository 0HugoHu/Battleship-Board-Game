package edu.duke.yh342.battleship;

/**
 * Check if the given coordinate is outside of board
 */
public class InBoundsRuleChecker<T> extends PlacementRuleChecker<T> {

    /**
     * Check if the given coordinate is outside of board
     *
     * @param theShip  consists of ship information
     * @param theBoard consists of board information
     * @return null if pass the test, Stirng of error otherwise
     */
    @Override
    protected String checkMyRule(Ship<T> theShip, Board<T> theBoard) {
        for (Coordinate c : theShip.getCoordinates()) {
            if (c.getRow() < 0) {
                return "That placement is invalid: the ship goes off the top of the board.";
            } else if (c.getRow() >= theBoard.getHeight()) {
                return "That placement is invalid: the ship goes off the bottom of the board.";
            } else if (c.getColumn() < 0) {
                return "That placement is invalid: the ship goes off the left of the board.";
            } else if (c.getColumn() >= theBoard.getWidth()) {
                return "That placement is invalid: the ship goes off the right of the board.";
            }
        }
        return null;
    }

    /**
     * Construct a check chain
     *
     * @param next the next placement rule to be checked
     */
    public InBoundsRuleChecker(PlacementRuleChecker<T> next) {
        super(next);
    }

}
