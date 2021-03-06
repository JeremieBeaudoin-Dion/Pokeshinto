package world;

import images.PhysicalObject;
import images.Position;
import pokeshinto.Player;
import world.worldObjects.Door;
import world.worldObjects.Enemy;
import world.worldObjects.MovingObject;

import java.util.Iterator;
import java.util.List;

/**
 * This class handles collision with a moving object according to a Map
 * objects.
 *
 * @author Jérémie Beaudoin-Dion
 */
class CollisionHandler {

    private static final int[] IMPASSABLE_BACKGROUND = new int[]{0, 1, 2, 3, 4, 57, 58, 59, 60, 61, 62, 63, 64, 117, 118,
            119, 120, 121, 122, 123, 124, 177, 178, 179, 180, 181, 182, 183, 184, 237, 238, 239, 240, 241, 242, 243, 244,
            300, 301, 302, 303, 304};

    private static final int[] WATER_BACKGROUND = new int[]{0, 1, 2, 3, 4, 57, 58, 59, 60, 61, 62, 63, 64, 117, 118,
            119, 120, 121, 122, 123, 124, 177, 178, 179, 180, 181, 182, 183, 184, 237, 238, 239, 240, 241, 242, 243, 244,
            300, 301, 302, 303, 304};

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
        return new Position(position.getX()/ ImageLoaderInWorld.TILE_WIDTH, position.getY()/ ImageLoaderInWorld.TILE_WIDTH);
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

    MovingObject getCollisionWithEnemy(PhysicalObject collidingObject, List<MovingObject> allMovingObjects) {

        Iterator<MovingObject> iter = allMovingObjects.iterator();
        MovingObject currentMovingObject;

        while(iter.hasNext()) {
            currentMovingObject = iter.next();

            if (getAllCollision(collidingObject, currentMovingObject.getShadow())){
                return currentMovingObject;
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
