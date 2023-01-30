package edu.duke.yh342.battleship;

public class InBoundsRuleChecker<T> extends PlacementRuleChecker<T> {

  @Override
  protected boolean checkMyRule(Ship<T> theShip, Board<T> theBoard) {
    for (Coordinate c : theShip.getCoordinates()) {
        if (c.getRow() < 0 || c.getRow() >= theBoard.getHeight() || c.getColumn() < 0 || c.getColumn() >= theBoard.getWidth()) {
            return false;
        }
    }
    return true;
  }

  public InBoundsRuleChecker(PlacementRuleChecker<T> next) {
    super(next);
  }

}
