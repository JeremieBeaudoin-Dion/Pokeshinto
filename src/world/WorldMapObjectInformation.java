package world;

import images.PhysicalObject;
import images.Position;

/**
 * Constains all information on basic objects in World. It will return physicalObjects
 * upon Get instance.
 *
 * @author Jérémie Beaudoin-Dion
 */
class WorldMapObjectInformation {

    PhysicalObject getBasicDoor() {
        return new PhysicalObject(new Position(0,0 ), WorldImageLoader.TILE_WIDTH, WorldImageLoader.TILE_WIDTH);
    }

    PhysicalObject getBasicEnemy() {
        return new PhysicalObject(new Position(0, 0),
                WorldImageLoader.TILE_WIDTH, WorldImageLoader.TILE_WIDTH);
    }

    PhysicalObject getBasicEnemyShadow() {
        return new PhysicalObject(new Position(getBasicEnemy().getWidth()/6, getBasicEnemy().getHeight()*2/3),
                getBasicEnemy().getWidth()*5/6, getBasicEnemy().getHeight()/3);
    }


}
