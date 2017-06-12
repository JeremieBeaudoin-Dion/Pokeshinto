package combat;

/**
 * A conditional object is an object which can be tested with its conditions
 * 
 * @author Jérémie Beaudoin-Dion
 */
public abstract class Conditional {

    protected int initCooldown;
	protected int cooldown;
    protected int initBuildup;
	protected int buildup;
    protected boolean once;
    protected boolean hasBeenUsed;
	
	protected Condition condition;
	
	/**
	 * Constructor
	 * 
	 * @param cooldown the time before the condition can be used a second time
	 * @param buildup the time before the condition can be used
	 * @param once: if it can only be casted once
	 */
	Conditional(int cooldown, int buildup, boolean once, Condition condition) {
		this.cooldown = 0;
		this.initCooldown = cooldown;
		this.initBuildup = buildup;
		this.buildup = buildup;
		this.once = once;
		this.condition = condition;
        hasBeenUsed = false;
	}
	
	/**
	 * Returns wether the Conditionnal can be done or not
	 * 
	 * @return: if it can be done, depending on the condition and cooldowns
	 */
	boolean isUsable(Combat combat, String owner) {

	    if (hasBeenUsed && once) {
	        return false;
        }
		
		if (cooldown == 0 && buildup == 0) {
			// Check condition
			if (condition != null){
				boolean conditionOutcome = condition.getOutcome(combat, owner);
				// The condition was met
				if(conditionOutcome){
				    hasBeenUsed = true;
					return true;
				}
			} else {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Resets the coolDown to its initial value
	 */
	void resetCooldown() {
		cooldown = initCooldown;
	}
	
	/**
	 * Updates the buildup and cooldown
	 */
	public void update() {
		buildup -= 1;
		if (buildup < 0){
			buildup = 0;
		}
		cooldown -= 1;
		if (cooldown < 0) {
			cooldown = 0;
		}
	}
	
}
