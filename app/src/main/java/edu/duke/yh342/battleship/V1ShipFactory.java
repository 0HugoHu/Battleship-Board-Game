package edu.duke.yh342.battleship;

/**
 * Create a ship in version 1, implements AbstractShipFactory
 */
public class V1ShipFactory implements AbstractShipFactory<Character> {

    /**
     * Create a ship with placement, size, character and name
     *
     * @param where  to place the ship
     * @param w      width of the ship
     * @param h      height of the ship
     * @param letter the representation of the ship
     * @param name   the name of the ship
     * @return Ship<Character> a new RectangleShip instance
     */
    protected Ship<Character> createShip(Placement where, int w, int h, char letter, String name) {
        if (where.getOrientation() == 'V') {
            return new RectangleShip<Character>(name, where.getCoordinate(), w, h, letter, '*');
        } else {
            return new RectangleShip<Character>(name, where.getCoordinate(), h, w, letter, '*');
        }
    }

    /**
     * Create a submarine based on the placement
     *
     * @param where to place the ship
     * @return Ship<Character> a new RectangleShip instance
     */
    @Override
    public Ship<Character> makeSubmarine(Placement where) {
        return createShip(where, 1, 2, 's', "Submarine");
    }

    /**
     * Create a battleship based on the placement
     *
     * @param where to place the ship
     * @return Ship<Character> a new RectangleShip instance
     */
    @Override
    public Ship<Character> makeBattleship(Placement where) {
        return createShip(where, 1, 4, 'b', "Battleship");
    }

    /**
     * Create a carrier ship based on the placement
     *
     * @param where to place the ship
     * @return Ship<Character> a new RectangleShip instance
     */
    @Override
    public Ship<Character> makeCarrier(Placement where) {
        return createShip(where, 1, 6, 'c', "Carrier");
    }

    /**
     * Create a destroyer ship based on the placement
     *
     * @param where to place the ship
     * @return Ship<Character> a new RectangleShip instance
     */
    @Override
    public Ship<Character> makeDestroyer(Placement where) {
        return createShip(where, 1, 3, 'd', "Destroyer");
    }

}
