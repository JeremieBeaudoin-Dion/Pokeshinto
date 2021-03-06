package combat.combatActions;

import pokeshinto.Copyable;

/**
 * A trigger happens at a certain time and has a condition
 *
 * When:
 *      Start Turn
 *      Start Attack
 *      End Attack
 *      End Turn
 * 
 * @author Jérémie Beaudoin-Dion
 *
 */
public abstract class Trigger extends Conditional implements Copyable<Trigger> {
	
	protected boolean affectsMe;  // if the trigger affects the owner
    protected String when;
    private boolean isUsed;

    /**
     * Constructor with a condition
     *
     * @param when: the period when the trigger is considered
     * @param affectsMe: if the trigger affects the owner
     */
    public Trigger (int cooldown, int buildup, boolean once, Condition condition, String when, boolean affectsMe) {
		super(cooldown, buildup, once, condition);

		this.affectsMe = affectsMe;
		this.when = when;
        isUsed = false;
	}

    /**
     * Constructor without condition
     */
    public Trigger (String when, boolean affectsMe) {
	    super(0, 0, false, null);

        this.affectsMe = affectsMe;
        this.when = when;
        isUsed = false;
    }

    @Override
    public void resetCooldown() {
        super.resetCooldown();
        isUsed = false;
    }

    public void useTrigger() {
        isUsed = true;
    }

    /**
     * Returns true if the Trigger happens now
     */
    public boolean isTheRightTime(String time) {
	    return when.equals(time);
    }

    public boolean isEnded() {
        return isUsed;
    }

    /**
     * Getter
     */
    public boolean isOnMe() {
	    return affectsMe;
    }
	
}
