package world.worldObjects;

import duringMenus.Menu;
import images.PhysicalObject;
import images.Position;

/**
 * A Villager is a MovingObject with which the player can interract.
 * They can be priests, shop keepers, or simple villagers who has
 * something to say.
 *
 * @author Jérémie Beaudoin-Dion
 */
public abstract class Villager extends MovingObject {

    protected Menu menu;

    /**
     * Simple Constructor
     */
    public Villager(Position position, int width, int height, int speed, Position shadowInset, Position shadowDimension) {
        super(position, width, height, speed, shadowInset, shadowDimension);
    }

    /**
     * Constructor facilitated with complete objects
     */
    public Villager(PhysicalObject object, int speed, PhysicalObject shadow) {
        super(object, speed, shadow);
    }

    public Menu getMenu() {
        return menu;
    }

}
