package world.worldInformation;

import images.PhysicalObject;
import images.Position;
import world.ImageLoaderInWorld;

/**
 * Constains all information on basic objects in World.
 *
 * @author Jérémie Beaudoin-Dion
 */
public final class WorldMapObjectInformation {

    public static PhysicalObject getBasicDoor() {
        return new PhysicalObject(new Position(0,0 ), ImageLoaderInWorld.TILE_WIDTH / 2, ImageLoaderInWorld.TILE_WIDTH);
    }

    public static PhysicalObject getBasicEnemy() {
        return new PhysicalObject(new Position(0, 0),
                ImageLoaderInWorld.ENEMY_WIDTH, ImageLoaderInWorld.ENEMY_HEIGHT);
    }

    public static PhysicalObject getBasicEnemyShadow() {
        return new PhysicalObject(new Position(getBasicEnemy().getWidth()/6, getBasicEnemy().getHeight()*2/3),
                getBasicEnemy().getWidth()*5/6, getBasicEnemy().getHeight()/3);
    }

    public static PhysicalObject getBasicVillager() {
        return new PhysicalObject(new Position(0, 0),
                ImageLoaderInWorld.VILLAGER_WIDTH, ImageLoaderInWorld.VILLAGER_HEIGHT);
    }

    public static PhysicalObject getBasicVillagerShadow() {
        return new PhysicalObject(new Position(ImageLoaderInWorld.VILLAGER_WIDTH/6,ImageLoaderInWorld.VILLAGER_HEIGHT*5/6),
                ImageLoaderInWorld.VILLAGER_WIDTH *2/3, ImageLoaderInWorld.VILLAGER_HEIGHT/5);
    }

}
