package world.worldObjects;

import duringMenus.Menu;
import images.PhysicalObject;

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
     * Constructor
     */
    public Villager(PhysicalObject object, int speed, PhysicalObject shadow, Menu menu) {
        super(object, speed, shadow);

        this.menu = menu;
    }

    public Menu getMenu() {
        return menu;
    }

}
