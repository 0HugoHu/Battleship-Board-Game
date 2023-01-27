package edu.duke.yh342.battleship;

import java.util.HashSet;

public class RectangleShip<T> extends BasicShip<T>{
    static HashSet<Coordinate> makeCoords(Coordinate upperLeft, int width, int height) {
        if (width <= 0) {
            throw new IllegalArgumentException("Ship's occupy space must be at least 1 x 1, but its width is " + width);
        }
        if (height <= 0) {
            throw new IllegalArgumentException("Ship's occupy space must be at least 1 x 1, but its height is " + height);
        }

        HashSet<Coordinate> res = new HashSet<>();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                res.add(new Coordinate(upperLeft.getRow() + i, upperLeft.getColumn() + j));
            }
        }

        return res;
    }

    public RectangleShip(Coordinate upperLeft, int width, int height, ShipDisplayInfo<T> shipInfo) {
        super(makeCoords(upperLeft, width, height), shipInfo);
    }

    public RectangleShip(Coordinate upperLeft, int width, int height, T data, T onHit) {
        this(upperLeft, width, height, new SimpleShipDisplayInfo<T>(data, onHit));
    }

    public RectangleShip(Coordinate upperLeft, T data, T onHit) {
        this(upperLeft, 1, 1, data, onHit);
    }


}
