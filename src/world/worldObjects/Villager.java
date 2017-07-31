package world.worldObjects;

import duringMenus.Menu;
import images.PhysicalObject;

import java.util.List;

/**
 * A Villager is a MovingObject with which the player can interract.
 * They can be priests, shop keepers, or simple villagers who has
 * something to say.
 *
 * @author Jérémie Beaudoin-Dion
 */
public abstract class Villager extends MovingObject {

    protected Menu menu;
    protected List<String> conversation;

    /**
     * Constructor
     */
    public Villager(PhysicalObject object, int speed, PhysicalObject shadow, Menu menu, List<String> conversation) {
        super(object, speed, shadow);

        this.menu = menu;
        this.conversation = conversation;
    }

    public Menu getMenu() {
        return menu;
    }

    public List<String> getConversation() {
        return conversation;
    }

    protected void setMenu(Menu menu) {
        this.menu = menu;
    }

    protected void setConversation(List<String> conversation) {
        this.conversation = conversation;
    }

}
