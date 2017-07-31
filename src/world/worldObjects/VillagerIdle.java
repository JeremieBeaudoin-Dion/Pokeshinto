package world.worldObjects;

import duringMenus.Menu;
import images.PhysicalObject;

import java.util.List;

/**
 * A villager which doesn't move
 *
 * @author Jérémie Beaudoin-Dion
 */
public class VillagerIdle extends Villager {

    /**
     * Constructor
     */
    public VillagerIdle(PhysicalObject object, PhysicalObject shadow, Menu menu, List<String> conversation) {
        super(object, 0, shadow, menu, conversation);
    }

    @Override
    protected String getDecision() {
        return null;
    }

    @Override
    protected boolean decisionHasChanged() {
        return false;
    }
}
