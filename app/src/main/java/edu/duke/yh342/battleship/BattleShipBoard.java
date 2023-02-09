package edu.duke.yh342.battleship;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.HashMap;

/**
 * Create a battleship board implements
 */
public class BattleShipBoard<T> implements Board<T> {
    private final int width;
    private final int height;
    private final ArrayList<Ship<T>> myShips;
    private final PlacementRuleChecker<T> placementChecker;
    HashSet<Coordinate> enemyMisses;
    // Coordiantes that are hit but after move, not shown to enemy
    HashSet<Coordinate> notShownPieces;
    // Coordinates that are hit by enemy, even moved, the original hit point is still shown to enemy
    HashMap<Coordinate, T> enemyHits;
    final T missInfo;

    /**
     * Return the width of the board
     *
     * @return the width of the board
     */
    public int getWidth() {
        return width;
    }

    /**
     * Return the height of the board
     *
     * @return the height of the board
     */
    public int getHeight() {
        return height;
    }

    /**
     * Constructs a BattleShipBoard with the specified width
     * and height
     *
     * @param w                is the width of the newly constructed board.
     * @param h                is the height of the newly constructed board.
     * @param placementChecker check if the coordinate satisfied
     * @throws IllegalArgumentException if the width or height are less than or
     *                                  equal to zero.
     */
    public BattleShipBoard(int w, int h, PlacementRuleChecker<T> placementChecker, T missInfo) {
        if (w <= 0) {
            throw new IllegalArgumentException("BattleShipBoard's width must be positive but is " + w);
        }
        if (h <= 0) {
            throw new IllegalArgumentException("BattleShipBoard's height must be positive but is " + h);
        }
        this.width = w;
        this.height = h;
        this.myShips = new ArrayList<Ship<T>>();
        this.placementChecker = placementChecker;
        this.enemyMisses = new HashSet<>();
        this.notShownPieces = new HashSet<>();
        this.enemyHits = new HashMap<>();
        this.missInfo = missInfo;
    }

    /**
     * Constructs a BattleShipBoard with the specified width
     * and height
     *
     * @param w is the width of the newly constructed board.
     * @param h is the height of the newly constructed board.
     */
    public BattleShipBoard(int w, int h, T missInfo) {
        this(w, h, new InBoundsRuleChecker<T>(new NoCollisionRuleChecker<>(null)), missInfo);
    }

    /**
     * Try add a ship to the arraylist
     *
     * @param toAdd is the ship in type T to be added
     * @return null if the placement is correct
     * or String of the error arised
     */
    public String tryAddShip(Ship<T> toAdd) {
        String result = placementChecker.checkPlacement(toAdd, this);
        if (result == null) {
            myShips.add(toAdd);
            return null;
        }
        return result;
    }

    /**
     * Return what is at the block
     *
     * @param where the Coordinate type of position, e.g., (x, y)
     * @return what is at the block in type T
     * @throws IllegalArgumentException if the given coordinate is beyond the range
     *                                  of baord
     */
    public T whatIsAtForSelf(Coordinate where) {
        return whatIsAt(where, true);
    }

    /**
     * Return what is at the block
     *
     * @param where  the Coordinate type of position, e.g., (x, y)
     * @param isSelf display by own
     * @return what is at the block in type T
     * @throws IllegalArgumentException if the given coordinate is beyond the range
     *                                  of baord
     */
    protected T whatIsAt(Coordinate where, boolean isSelf) {
        if (where.getRow() >= this.height) {
            throw new IllegalArgumentException("Input coordinate height must in the range but height is " + where.getRow());
        }
        if (where.getColumn() >= this.width) {
            throw new IllegalArgumentException("Input coordinate width must in the range but width is " + where.getColumn());
        }
        if(!isSelf){
            if (notShownPieces.contains(where)){
                return null;
            }
            if(enemyMisses.contains(where)){
                return missInfo;
            }
            if (enemyHits.containsKey(where)){
                return enemyHits.get(where);
            }
        }
        for (Ship<T> s: myShips) {
            if (s.occupiesCoordinates(where)){
                return s.getDisplayInfoAt(where, isSelf);
            }
        }
        return null;
    }

    /**
     * Return what is at the block for enemy view
     *
     * @param where the Coordinate type of position, e.g., (x, y)
     * @return what is at the block in type T
     */
    public T whatIsAtForEnemy(Coordinate where) {
        return whatIsAt(where, false);
    }

    /**
     * Search for any ship that occupies coordinate c
     *
     * @param c where should be checked
     * @return the ship that has been fired
     */
    public Ship<T> fireAt(Coordinate c) {
        if (notShownPieces.contains(c)) {
            this.notShownPieces.remove(c);
        }
        for (Ship<T> s : myShips) {
            if (s.occupiesCoordinates(c) && !s.wasHitAt(c)) {
                this.enemyHits.put(c, s.getDisplayInfoAt(c, true));
                if (enemyMisses.contains(c)) {
                    enemyMisses.remove(c);
                }
                s.recordHitAt(c);
                return s;
            }
        }
        enemyMisses.add(c);
        if (enemyHits.containsKey(c)) {
            enemyHits.remove(c);
        }
        return null;
    }

    /**
     * Check if the board is all sunk
     *
     * @return true if all ships are sunk
     */
    public boolean allShipsSunk() {
        for (Ship<T> s : myShips) {
            if (!s.isSunk()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Return the ship at the coordinate
     *
     * @param where the coordinate to be checked
     * @return the ship at the coordinate
     */
    public Ship<T> getShipAt(Coordinate where) {
        for (Ship<T> s : myShips) {
            if (s.occupiesCoordinates(where)) {
                return s;
            }
        }
        return null;
    }

    /**
     * Move a ship to a new coordinate
     *
     * @param toMove the ship to be moved
     * @param to     the new placement
     * @param ownBoard the board to be moved to
     * @return true if the ship is moved
     */
    public boolean moveShipTo(Ship<Character> toMove, Placement to, Board<Character> ownBoard) {
        // Traverse the ship coordinate to find the top left coordinate
        V2ShipFactory shipFactory = new V2ShipFactory();
        Ship<Character> s = null;
        if (toMove.getName().equals("Battleship")) {
            s = shipFactory.makeBattleship(to);
        } else if (toMove.getName().equals("Carrier")) {
            s = shipFactory.makeCarrier(to);
        } else if (toMove.getName().equals("Destroyer")) {
            s = shipFactory.makeDestroyer(to);
        } else {
            s = shipFactory.makeSubmarine(to);
        }

        // Check for valid placement
        String result = ownBoard.tryAddShip(s);
        if (result == null) {
            ownBoard.removeShip(s);
            // Now add hit points to the ship
            transferHitPoints(toMove, s);
            toMove.removeCoordinate();
            // Remove the original ship
            ownBoard.removeShip(toMove);
            for (Coordinate c : s.getCoordinates()) {
                toMove.addCoordinate(c, s.wasHitAt(c));
            }
            toMove.setOrientation(s.getOrientation());
            ownBoard.tryAddShip(toMove);
            return true;
        } else {
            return false;
        }
    }


     /**
     * Transfer hit points from one ship to another
     *
     * @param from the ship to transfer hit points from
     * @param to   the ship to transfer hit points to
     */
    public void transferHitPoints(Ship<Character> from, Ship<Character> to) {
        // Find the top left coordinate of old ship
        int baseOldCol = Integer.MAX_VALUE;
        int baseOldRow = Integer.MAX_VALUE;
        for (Coordinate c : from.getCoordinates()) {
            if (c.getColumn() < baseOldCol) {
                baseOldCol = c.getColumn();
            }
            if (c.getRow() < baseOldRow) {
                baseOldRow = c.getRow();
            }
        }
        // Find the top left coordinate of new ship
        int baseNewCol = Integer.MAX_VALUE;
        int baseNewRow = Integer.MAX_VALUE;
        for (Coordinate c : to.getCoordinates()) {
            if (c.getColumn() < baseNewCol) {
                baseNewCol = c.getColumn();
            }
            if (c.getRow() < baseNewRow) {
                baseNewRow = c.getRow();
            }
        }

        Character oldOrient = from.getOrientation();
        Character newOrient = to.getOrientation();

        for (Coordinate oldCoord : from.getCoordinates()) {
            if (from.wasHitAt(oldCoord)) {
                int col = oldCoord.getColumn() - baseOldCol;
                int row = oldCoord.getRow() - baseOldRow;

                // Transfer this coordinate
                Coordinate newHitPoint = new Coordinate(0, 0);
                
                // Exactly the same orientation
                if (oldOrient == newOrient){
                    newHitPoint = new Coordinate(baseNewRow + row, baseNewCol + col);
                }
                // Basic H and V orientations
                /*
                 *  From: *** To: *
                 *                *
                 *                *
                 */
                else if (oldOrient == 'H') {
                    // Then new ship's orientation must be V
                    newHitPoint = new Coordinate(baseNewRow + col, baseNewCol);
                }
                /*
                 *  From: *  To: ***
                 *        *          
                 *        *        
                 */
                else if (oldOrient == 'V') {
                    newHitPoint = new Coordinate(baseNewRow, baseNewCol + row);
                }
                // Orientation of Battleship
                else if (from.getName() == "Battleship") {
                    if (oldOrient == 'U') {
                        if (newOrient == 'R' ) {
                            Coordinate newPivot = new Coordinate(baseNewRow, baseNewCol + 1);
                            newHitPoint = new Coordinate(newPivot.getRow() + col, newPivot.getColumn() - row);
                        } else if (newOrient == 'D' ) {
                            Coordinate newPivot = new Coordinate(baseNewRow + 1, baseNewCol + 2);
                            newHitPoint = new Coordinate(newPivot.getRow() - row, newPivot.getColumn() - col);
                        } else {
                            Coordinate newPivot = new Coordinate(baseNewRow + 2, baseNewCol);
                            newHitPoint = new Coordinate(newPivot.getRow() - col, newPivot.getColumn() + row);
                        }
                        
                    } else if (oldOrient == 'R') {
                        if (newOrient == 'D' ) {
                            Coordinate newPivot = new Coordinate(baseNewRow, baseNewCol + 2);
                            newHitPoint = new Coordinate(newPivot.getRow() + col, newPivot.getColumn() - row);
                        } else if (newOrient == 'L' ) {
                            Coordinate newPivot = new Coordinate(baseNewRow + 2, baseNewCol + 1);
                            newHitPoint = new Coordinate(newPivot.getRow() - row, newPivot.getColumn() - col);
                        } else {
                            Coordinate newPivot = new Coordinate(baseNewRow + 1, baseNewCol);
                            newHitPoint = new Coordinate(newPivot.getRow() - col, newPivot.getColumn() + row);
                        }
                    } else if (oldOrient == 'D') {
                        if (newOrient == 'L' ) {
                            Coordinate newPivot = new Coordinate(baseNewRow, baseNewCol + 1);
                            newHitPoint = new Coordinate(newPivot.getRow() + col, newPivot.getColumn() - row);
                        } else if (newOrient == 'U' ) {
                            Coordinate newPivot = new Coordinate(baseNewRow + 1, baseNewCol + 2);
                            newHitPoint = new Coordinate(newPivot.getRow() - row, newPivot.getColumn() - col);
                        } else {
                            Coordinate newPivot = new Coordinate(baseNewRow + 2, baseNewCol);
                            newHitPoint = new Coordinate(newPivot.getRow() - col, newPivot.getColumn() + row);
                        }
                    } else {
                        if (newOrient == 'U' ) {
                            Coordinate newPivot = new Coordinate(baseNewRow, baseNewCol + 2);
                            newHitPoint = new Coordinate(newPivot.getRow() + col, newPivot.getColumn() - row);
                        } else if (newOrient == 'R' ) {
                            Coordinate newPivot = new Coordinate(baseNewRow + 2, baseNewCol + 1);
                            newHitPoint = new Coordinate(newPivot.getRow() - row, newPivot.getColumn() - col);
                        } else {
                            Coordinate newPivot = new Coordinate(baseNewRow + 1, baseNewCol);
                            newHitPoint = new Coordinate(newPivot.getRow() - col, newPivot.getColumn() + row);
                        }
                    }
                    // Orientation of Carrier
                } else {
                    if (oldOrient == 'U') {
                        if (newOrient == 'R' ) {
                            Coordinate newPivot = new Coordinate(baseNewRow, baseNewCol + 4);
                            newHitPoint = new Coordinate(newPivot.getRow() + col, newPivot.getColumn() - row);
                        } else if (newOrient == 'D' ) {
                            Coordinate newPivot = new Coordinate(baseNewRow + 4, baseNewCol + 1);
                            newHitPoint = new Coordinate(newPivot.getRow() - row, newPivot.getColumn() - col);
                        } else {
                            Coordinate newPivot = new Coordinate(baseNewRow + 1, baseNewCol);
                            newHitPoint = new Coordinate(newPivot.getRow() - col, newPivot.getColumn() + row);
                        }
                    } else if (oldOrient == 'R') {
                        if (newOrient == 'D' ) {
                            Coordinate newPivot = new Coordinate(baseNewRow, baseNewCol + 1);
                            newHitPoint = new Coordinate(newPivot.getRow() + col, newPivot.getColumn() - row);
                        } else if (newOrient == 'L' ) {
                            Coordinate newPivot = new Coordinate(baseNewRow + 1, baseNewCol + 4);
                            newHitPoint = new Coordinate(newPivot.getRow() - row, newPivot.getColumn() - col);
                        } else {
                            Coordinate newPivot = new Coordinate(baseNewRow + 4, baseNewCol);
                            newHitPoint = new Coordinate(newPivot.getRow() - col, newPivot.getColumn() + row);
                        }
                    } else if (oldOrient == 'D') {
                        if (newOrient == 'L' ) {
                            Coordinate newPivot = new Coordinate(baseNewRow, baseNewCol + 4);
                            newHitPoint = new Coordinate(newPivot.getRow() + col, newPivot.getColumn() - row);
                        } else if (newOrient == 'U' ) {
                            Coordinate newPivot = new Coordinate(baseNewRow + 4, baseNewCol + 1);
                            newHitPoint = new Coordinate(newPivot.getRow() - row, newPivot.getColumn() - col);
                        } else {
                            Coordinate newPivot = new Coordinate(baseNewRow + 1, baseNewCol);
                            newHitPoint = new Coordinate(newPivot.getRow() - col, newPivot.getColumn() + row);
                        }
                    } else {
                        if (newOrient == 'U' ) {
                            Coordinate newPivot = new Coordinate(baseNewRow, baseNewCol + 1);
                            newHitPoint = new Coordinate(newPivot.getRow() + col, newPivot.getColumn() - row);
                        } else if (newOrient == 'R' ) {
                            Coordinate newPivot = new Coordinate(baseNewRow + 1, baseNewCol + 4);
                            newHitPoint = new Coordinate(newPivot.getRow() - row, newPivot.getColumn() - col);
                        } else {
                            Coordinate newPivot = new Coordinate(baseNewRow + 4, baseNewCol);
                            newHitPoint = new Coordinate(newPivot.getRow() - col, newPivot.getColumn() + row);
                        }
                    }
                }
                to.addCoordinate(newHitPoint, true);
                to.setOrientation(newOrient);
                if (this.notShownPieces.contains(oldCoord)) {
                    this.notShownPieces.remove(oldCoord);
                }
                this.notShownPieces.add(newHitPoint);
            }
        }
    }

    /**
     * Remove a ship from the board
     *
     * @param toRemove the ship to be removed
     */
    public void removeShip(Ship<T> toRemove) {
        myShips.remove(toRemove);
    }


    /**
     * Get the number of tiles the enemy ship occupies on the board
     *
     * @param c the coordinate to scan
     * @param enemyBoard the enemy board
     * @return the number of ships on the board
     */
    public int[] sonarScan(Coordinate c, Board<Character> enemyBoard) {
        int[] result = new int[4];
        for (int i = 0; i < result.length; i++) {
            result[i] = 0;
        }

        // Scan the board from the given coordinate
        // Generated by Copilot
        int[][] offsets = {{-3, 0}, {-2, -1}, {-2, 0}, {-2, 1}, {-1, -2}, {-1, -1}, {-1, 0}, {-1, 1}, {-1, 2}, {0, -3}, {0, -2}, {0, -1}, {0, 0}, {0, 1}, {0, 2}, {0, 3}, {1, -2}, {1, -1}, {1, 0}, {1, 1}, {1, 2}, {2, -1}, {2, 0}, {2, 1}, {3, 0}};
        for (int i = 0; i < offsets.length; i++) {
            int row = offsets[i][0] + c.getRow();
            int col = offsets[i][1] + c.getColumn();

            if (row < 0 || row >= enemyBoard.getHeight()) {
                continue;
            }
            if (col < 0 || col >= enemyBoard.getWidth()) {
                continue;
            }

            // Do not consider suqares that are hit
            if (enemyBoard.whatIsAtForSelf(new Coordinate(row, col)) == null) continue;
            switch (enemyBoard.whatIsAtForSelf(new Coordinate(row, col))) {
                case 's':
                    result[0]++;
                    break;
                case 'd':
                    result[1]++;
                    break;
                case 'b':
                    result[2]++;
                    break;
                case 'c':
                    result[3]++;
                    break;
            }
        }
        return result;
    }
}
