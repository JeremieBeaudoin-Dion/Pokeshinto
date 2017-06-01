package images;

/**
 * @author Jérémie Beaudoin-Dion
 */
public class CardinalDirection {

    private int north;
    private int south;
    private int east;
    private int west;

    public CardinalDirection(int north, int south, int east, int west) {
        this.north = north;
        this.south = south;
        this.east = east;
        this.west = west;
    }

    public int getNorth() {
        return north;
    }

    public int getSouth() {
        return south;
    }

    public int getEast() {
        return east;
    }

    public int getWest() {
        return west;
    }
}
