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
     * @return true if pass the test
     */
    @Override
    protected boolean checkMyRule(Ship<T> theShip, Board<T> theBoard) {
        for (Coordinate c : theShip.getCoordinates()) {
            if (c.getRow() < 0 || c.getRow() >= theBoard.getHeight() || c.getColumn() < 0 || c.getColumn() >= theBoard.getWidth()) {
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
    public InBoundsRuleChecker(PlacementRuleChecker<T> next) {
        super(next);
    }

}
