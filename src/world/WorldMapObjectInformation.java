package world;

import images.PhysicalObject;
import images.Position;

import java.io.Serializable;

/**
 * Constains all information on basic objects in World. It will return physicalObjects
 * upon Get instance.
 *
 * @author Jérémie Beaudoin-Dion
 */
class WorldMapObjectInformation implements Serializable {

    PhysicalObject getBasicDoor() {
        return new PhysicalObject(new Position(0,0 ), ImageLoaderInWorld.TILE_WIDTH / 2, ImageLoaderInWorld.TILE_WIDTH);
    }

    PhysicalObject getBasicEnemy() {
        return new PhysicalObject(new Position(0, 0),
                ImageLoaderInWorld.ENEMY_WIDTH, ImageLoaderInWorld.ENEMY_HEIGHT);
    }

    PhysicalObject getBasicEnemyShadow() {
        return new PhysicalObject(new Position(getBasicEnemy().getWidth()/6, getBasicEnemy().getHeight()*2/3),
                getBasicEnemy().getWidth()*5/6, getBasicEnemy().getHeight()/3);
    }

    PhysicalObject getBasicVillager() {
        return new PhysicalObject(new Position(0, 0),
                ImageLoaderInWorld.VILLAGER_WIDTH, ImageLoaderInWorld.VILLAGER_HEIGHT);
    }

}
