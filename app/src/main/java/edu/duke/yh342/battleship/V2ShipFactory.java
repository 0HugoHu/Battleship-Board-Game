package edu.duke.yh342.battleship;

/**
 * Create a ship in version 2, implements AbstractShipFactory
 */
public class V2ShipFactory implements AbstractShipFactory<Character> {

    /**
     * Create a ship with placement, size, character and name
     *
     * @param where  to place the ship
     * @param w      not used in version 2, but keep it for adaptability
     * @param h      not used in version 2, but keep it for adaptability
     * @param letter the representation of the ship
     * @param name   the name of the ship
     * @return Ship<Character> a new RectangleShip instance
     */
    protected Ship<Character> createShip(Placement where, int w, int h, char letter, String name) {
        // Battleship and Carrier are special cases
        if (name.equals("Battleship") || name.equals("Carrier")) {
            // Use the width parameter to pass the orientation of the ship without breaking the adapatbility
            return new ShapedShip<Character>(name, where.getCoordinate(), where.getOrientation() - 'A', 0, letter, '*');
        } else {
            if (where.getOrientation() == 'V') {
                return new RectangleShip<Character>(name, where, w, h, letter, '*');
            } else {
                return new RectangleShip<Character>(name, where, h, w, letter, '*');
            }
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
