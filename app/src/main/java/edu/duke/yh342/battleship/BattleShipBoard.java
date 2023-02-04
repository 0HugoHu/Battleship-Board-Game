package edu.duke.yh342.battleship;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Create a battleship board implements
 */
public class BattleShipBoard<T> implements Board<T> {
    private final int width;
    private final int height;
    private final ArrayList<Ship<T>> myShips;
    private final PlacementRuleChecker<T> placementChecker;
    HashSet<Coordinate> enemyMisses;
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
            if(enemyMisses.contains(where)){
                return missInfo;
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
        for (Ship<T> s : myShips) {
            if (s.occupiesCoordinates(c) && !s.wasHitAt(c)) {
                s.recordHitAt(c);
                return s;
            }
        }
        enemyMisses.add(c);
        return null;
    }
}
