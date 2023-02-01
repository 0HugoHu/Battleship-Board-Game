package edu.duke.yh342.battleship;

/**
 * Abstract class: check in chain for rule sets
 */
public abstract class PlacementRuleChecker<T> {
    protected abstract boolean checkMyRule(Ship<T> theShip, Board<T> theBoard);

    private final PlacementRuleChecker<T> next;

    /**
     * Construct a check chain
     *
     * @param next the next placement rule to be checked
     */
    public PlacementRuleChecker(PlacementRuleChecker<T> next) {
        this.next = next;
    }

    /**
     * Check if the given coordinate has broken the rule
     * and pass the check to its children's class
     *
     * @param theShip  consists of ship information
     * @param theBoard consists of board information
     * @return true if pass the test
     */
    public boolean checkPlacement(Ship<T> theShip, Board<T> theBoard) {
        //if we fail our own rule: stop the placement is not legal
        if (!checkMyRule(theShip, theBoard)) {
            return false;
        }
        //other wise, ask the rest of the chain.
        if (next != null) {
            return next.checkPlacement(theShip, theBoard);
        }
        //if there are no more rules, then the placement is legal
        return true;
    }

}
