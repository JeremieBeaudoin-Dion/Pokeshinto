package world;

import images.PhysicalObject;
import images.Position;
import pokeshinto.Player;

import java.util.Iterator;
import java.util.List;

/**
 * This class handles collision with a moving object according to a Map
 * objects.
 *
 * @author Jérémie Beaudoin-Dion
 */
class CollisionHandler {

    private static final int[] IMPASSABLE_BACKGROUND = new int[]{10, 11, 12, 13, 14,
            30, 31, 32, 33, 34, 50, 51, 52, 53, 54, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 75,
            76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102,
            103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120};

    private static final int[] WATER_BACKGROUND = new int[]{10, 11, 12, 13, 14,
            30, 31, 32, 33, 34, 50, 51, 52};


    /**
     * Return true if one of the Position is out of the current map
     *
     * @param northEast the north-most and east-most point of the object
     * @param southWest the south-most and west-most point of the object
     */
    boolean getIsOutOfBounds(Position northEast, Position southWest) {
        return northEast.getX() < 0 || northEast.getY() < 0 ||
                southWest.getX() >= Player.currentMapSize.getX() || southWest.getY() >= Player.currentMapSize.getY();
    }

    /**
     * The background collisions are handled with the int[][] TILEID object.
     *
     * It is done this way to increase performance, considering that the background object won't move.
     *
     * @param northEast the north-most and east-most point of the object
     * @param southWest the south-most and west-most point of the object
     * @param background an array of tile ids
     * @return if there is a collision with a solid object in the background array an the two points according to tile ID
     */
    boolean getBackgroundCollision(Position northEast, Position southWest, int[][] background){
        return getBackgroundCollisionWithAdjustedPoint(adjustBackgroundPoint(northEast), background) ||
                getBackgroundCollisionWithAdjustedPoint(adjustBackgroundPoint(southWest), background);
    }

    /**
     * Returns an adjusted point according to the TILE_WIDTH
     */
    private Position adjustBackgroundPoint(Position position){
        return new Position(position.getX()/WorldImageLoader.TILE_WIDTH, position.getY()/WorldImageLoader.TILE_WIDTH);
    }

    /**
     * Compares IMPASSABLE_BACKGROUND ID and the desired tiled position ID
     */
    private boolean getBackgroundCollisionWithAdjustedPoint(Position position, int[][] background){

        for (int i=0; i<IMPASSABLE_BACKGROUND.length; i++) {
            if (IMPASSABLE_BACKGROUND[i] == background[position.getY()][position.getX()]){
                return true;
            }
            if (background[position.getY()][position.getX()] < IMPASSABLE_BACKGROUND[i]) {
                return false;
            }
        }

        return false;
    }

    /**
     * Returns if there is a background collision with a water tile
     */
    boolean getWaterCollision(Position northEast, Position southWest, int[][] background){
        return getWaterCollisionWithAdjustedPoint(adjustBackgroundPoint(northEast), background) ||
                getWaterCollisionWithAdjustedPoint(adjustBackgroundPoint(southWest), background);
    }

    private boolean getWaterCollisionWithAdjustedPoint(Position position, int[][] background) {
        for (int i=0; i<WATER_BACKGROUND.length; i++) {
            if (WATER_BACKGROUND[i] == background[position.getY()][position.getX()]){
                return true;
            }
            if (background[position.getY()][position.getX()] < WATER_BACKGROUND[i]) {
                return false;
            }
        }

        return false;
    }

    /**
     * Handles collision with all other solid objects of the map
     *
     * @param movingObject the moving object's shadow
     * @param obstacles all other shadows
     * @return if there is a collision
     */
    PhysicalObject getSolidObjectCollision(PhysicalObject movingObject, List<PhysicalObject> obstacles) {

        Iterator<PhysicalObject> iter = obstacles.iterator();
        PhysicalObject currentObject;

        while(iter.hasNext()) {
            currentObject = iter.next();

            if (getAllCollision(movingObject, currentObject)) {
                return currentObject;
            }

        }

        return null;
    }

    Enemy getCollisionWithEnemy(PhysicalObject movingObject, List<Enemy> allEnemies) {

        Iterator<Enemy> iter = allEnemies.iterator();
        Enemy currentObject;

        while(iter.hasNext()) {
            currentObject = iter.next();

            if (getAllCollision(movingObject, currentObject.getShadow())){
                return currentObject;
            }

        }

        return null;

    }

    Door getCollisionWithDoor(PhysicalObject movingObject, List<Door> allDoors) {

        // There are no doors
        if (allDoors == null) {
            return null;
        }

        Iterator<Door> iter = allDoors.iterator();
        Door currentObject;

        while(iter.hasNext()) {
            currentObject = iter.next();

            if (getAllCollision(movingObject, currentObject)) {
                return currentObject;
            }

        }

        return null;
    }

    private boolean getAllCollision(PhysicalObject first, PhysicalObject second) {
        return getEastCollision(first, second) && getWestCollision(first, second) &&
                getSouthCollision(first, second) && getNorthCollision(first, second);
    }

    private boolean getEastCollision(PhysicalObject first, PhysicalObject second) {
        return first.getX() + first.getWidth() >= second.getX();
    }

    private boolean getWestCollision(PhysicalObject first, PhysicalObject second) {
        return first.getX() <= second.getX() + second.getWidth();
    }

    private boolean getSouthCollision(PhysicalObject first, PhysicalObject second) {
        return first.getY() + first.getHeight() >= second.getY();
    }

    private boolean getNorthCollision(PhysicalObject first, PhysicalObject second) {
        return first.getY() <= second.getY() + second.getHeight();
    }

}
