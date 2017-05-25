package world;

import images.Point;

/**
 * This class handles collision with a moving object according to a Map
 * objects.
 *
 * @author Jérémie Beaudoin-Dion
 */
class CollisionHandler {

    private static final int[] IMPASSABLE_BACKGROUND = new int[]{10, 11, 12, 13, 14,
            30, 31, 32, 33, 34, 50, 51, 52, 53, 54};

    /**
     * @param northEast the north-most and east-most point of the object
     * @param southWest the south-most and west-most point of the object
     * @param background an array of tile ids
     * @return if there is a collision with a solid object in the background array an the two points according to tile ID
     */
    boolean getBackgroundCollision(Point northEast, Point southWest, int[][] background){
        return getBackgroundCollisionWithAdjustedPoint(adjustBackgroundPoint(northEast), background) &&
                getBackgroundCollisionWithAdjustedPoint(adjustBackgroundPoint(southWest), background);
    }

    /**
     * Returns an adjusted point according to the TILE_WIDTH
     */
    private Point adjustBackgroundPoint(Point position){
        return new Point(position.getX()/WorldImageLoader.TILE_WIDTH, position.getY()/WorldImageLoader.TILE_WIDTH);
    }

    /**
     * Compares IMPASSABLE_BACKGROUND ID and the desired tiled position ID
     */
    private boolean getBackgroundCollisionWithAdjustedPoint(Point position, int[][] background){

        for (int i=0; i<IMPASSABLE_BACKGROUND.length; i++) {
            if (IMPASSABLE_BACKGROUND[i] == background[position.getY()][position.getX()]){
                return true;
            }
        }

        return false;
    }
}
