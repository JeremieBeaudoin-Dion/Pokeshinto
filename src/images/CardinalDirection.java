package images;

/**
 * @author Jérémie Beaudoin-Dion
 */
class CardinalDirection {

    private int north;
    private int south;
    private int east;
    private int west;

    CardinalDirection(int north, int south, int east, int west) {
        this.north = north;
        this.south = south;
        this.east = east;
        this.west = west;
    }

    int getNorth() {
        return north;
    }

    int getSouth() {
        return south;
    }

    int getEast() {
        return east;
    }

    int getWest() {
        return west;
    }
}
