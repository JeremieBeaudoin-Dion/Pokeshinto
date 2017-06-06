package combat;

/**
 * A damage that will happen at a certain time
 * 
 * For the value "When", the triggers are checked at a certain time:
 *      1) Start Turn
 *      2) Start Attack
 *      3) End Attack
 *      4) End Turn
 * 
 * @author Jérémie Beaudoin-Dion
 *
 */
public abstract class Trigger extends Conditional {
	
	private boolean affectsMe;  // if the trigger affects the owner
    private String when;
    private boolean isUsed;

    /**
     * Constructor with a condition
     *
     * @param when: the period when the trigger is considered
     * @param affectsMe: if the trigger affects the owner
     */
    Trigger (int cooldown, int buildup, boolean once, Condition condition, String when, boolean affectsMe) {
		super(cooldown, buildup, once, condition);

		this.affectsMe = affectsMe;
		this.when = when;
        isUsed = false;
	}

    /**
     * Constructor without condition
     */
    Trigger (String when, boolean affectsMe) {
	    super(0, 0, false, null);

        this.affectsMe = affectsMe;
        this.when = when;
        isUsed = false;
    }

    void useTrigger() {
        isUsed = true;
    }

    /**
     * Returns true if the Trigger happens now
     */
	boolean isTheRightTime(String time) {
	    return when.equals(time);
    }

    boolean isEnded() {
        return isUsed;
    }

    /**
     * Getter
     */
	boolean isOnMe() {
	    return affectsMe;
    }
	
}
