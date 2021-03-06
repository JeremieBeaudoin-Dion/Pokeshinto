package world.worldObjects;

import images.PhysicalObject;

import java.io.Serializable;
import java.util.List;

/**
 * A Villager is a MovingObject with which the player can interract.
 * They can be priests, shop keepers, or simple villagers who has
 * something to say.
 *
 * @author Jérémie Beaudoin-Dion
 */
public abstract class Villager extends MovingObject {

    protected List<String> conversation;

    /**
     * Constructor
     */
    Villager(PhysicalObject object, int speed, PhysicalObject shadow, List<String> conversation) {
        super(object, speed, shadow);

        this.conversation = conversation;
    }

    public List<String> getConversation() {
        return conversation;
    }

    protected void setConversation(List<String> conversation) {
        this.conversation = conversation;
    }

    public abstract int getCharacterId();

}
