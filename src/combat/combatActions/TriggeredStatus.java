package combat.combatActions;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Jérémie Beaudoin-Dion
 */
public class TriggeredStatus extends Trigger {

    private List<Status> status;
    private boolean hasCondition;

    public TriggeredStatus(int cooldown, int buildup, boolean once, Condition condition, String when, boolean affectsMe,
                           List<Status> status) {
        super(cooldown, buildup, once, condition, when, affectsMe);

        this.status = status;
        hasCondition = true;
    }

    /**
     * Constructor without condition
     */
    public TriggeredStatus(String when, boolean affectsMe, List<Status> status) {
        super(when, affectsMe);

        this.status = status;
        hasCondition = false;
    }

    public List<Status> getStatus() {
        return status;
    }

    public boolean getHasCondition() {
        return hasCondition;
    }

    @Override
    public Trigger copy() {
        List<Status> newStatus = new LinkedList<>();
        Iterator<Status> iter = status.iterator();

        while(iter.hasNext()) {
            newStatus.add(iter.next().copy());
        }

        if (hasCondition) {
            return new TriggeredStatus(initCooldown, initBuildup, once, condition, when, affectsMe, newStatus);
        }

        return new TriggeredStatus(when, affectsMe, newStatus);
    }
}
