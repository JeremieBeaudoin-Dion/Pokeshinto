package world.worldObjects;

import images.PhysicalObject;

import java.util.List;

/**
 * A villager which walks following a certain pattern
 *
 * @author Jérémie Beaudoin-Dion
 */
public abstract class VillagerRoaming extends Villager {

    private List<String> pattern;
    private int currentDecisionID;

    private int currentTime;
    private int MAX_WAIT_TIME;

    /**
     * Constructor
     */
    public VillagerRoaming(PhysicalObject object, int speed, PhysicalObject shadow, List<String> pattern, List<String> conversation) {
        super(object, speed, shadow, conversation);

        this.pattern = pattern;
        currentDecisionID = -1;
        currentTime = 0;
        MAX_WAIT_TIME = 100;
    }

    @Override
    protected String getDecision() {
        currentDecisionID++;
        currentDecisionID %= pattern.size();

        return pattern.get(currentDecisionID);
    }

    @Override
    protected boolean decisionHasChanged() {
        if (currentTime == 0) {
            currentTime = MAX_WAIT_TIME;
            return true;
        }

        currentTime--;
        return false;
    }
}
