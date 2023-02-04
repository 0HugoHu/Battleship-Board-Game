package edu.duke.yh342.battleship;

/**
 * Check if the given coordinate has collision with other ships
 */
public class NoCollisionRuleChecker<T> extends PlacementRuleChecker<T> {

    /**
     * Check if the given coordinate has collision with other ships
     *
     * @param theShip  consists of ship information
     * @param theBoard consists of board information
     * @return null if pass the test, Stirng of error otherwise
     */
    @Override
    protected String checkMyRule(Ship<T> theShip, Board<T> theBoard) {
        for (Coordinate c : theShip.getCoordinates()) {
            if (theBoard.whatIsAtForSelf(c) != null) {
                return "That placement is invalid: the ship overlaps another ship.";
            }
        }
        return null;
    }

    /**
     * Construct a check chain
     *
     * @param next the next placement rule to be checked
     */
    public NoCollisionRuleChecker(PlacementRuleChecker<T> next) {
        super(next);
    }
}