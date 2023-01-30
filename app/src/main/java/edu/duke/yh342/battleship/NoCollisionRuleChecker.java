package edu.duke.yh342.battleship;

public class NoCollisionRuleChecker<T> extends PlacementRuleChecker<T> {

  @Override
  protected boolean checkMyRule(Ship<T> theShip, Board<T> theBoard) {
    for (Coordinate c : theShip.getCoordinates()) {
        if (theBoard.whatIsAt(c) != null) {
            return false;
        }
    }
    return true;
  }

  public NoCollisionRuleChecker(PlacementRuleChecker<T> next) {
    super(next);
  }
}