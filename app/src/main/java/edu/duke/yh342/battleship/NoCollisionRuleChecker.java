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
     * @return true if pass the test
     */
    @Override
    protected boolean checkMyRule(Ship<T> theShip, Board<T> theBoard) {
        for (Coordinate c : theShip.getCoordinates()) {
            if (theBoard.whatIsAt(c) != null) {
                return false;
            }
        }
        return true;
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