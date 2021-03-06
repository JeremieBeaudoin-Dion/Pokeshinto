package world.worldObjects;

import images.PhysicalObject;

import java.util.List;

/**
 * A villager which doesn't move
 *
 * @author Jérémie Beaudoin-Dion
 */
public abstract class VillagerIdle extends Villager {

    /**
     * Constructor
     */
    VillagerIdle(PhysicalObject object, PhysicalObject shadow, List<String> conversation) {
        super(object, 0, shadow, conversation);
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
