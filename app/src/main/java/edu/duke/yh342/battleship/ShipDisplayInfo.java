package edu.duke.yh342.battleship;

public interface ShipDisplayInfo<T> {
    public T getInfo(Coordinate where, boolean hit);
}
