package combat;
/**
 * A conditional object is an object which can be tested with its conditions
 * 
 * @author Jérémie Beaudoin-Dion
 *
 */
public abstract class Conditional {

	protected int initCooldown;
	protected int cooldown;
	protected int initBuildup;
	protected int buildup;
	protected boolean once;
	
	protected Condition condition;
	
	/**
	 * Constructor
	 * 
	 * @param cooldown
	 * @param buildup
	 * @param once: if it can only be casted once
	 */
	public Conditional(int cooldown, int buildup, boolean once, Condition condition) {
		this.cooldown = 0;
		this.initCooldown = cooldown;
		this.initBuildup = buildup;
		this.buildup = buildup;
		this.once = once;
	}
	
	/**
	 * Returns wether the skill can be done or not
	 * 
	 * @return: if it can be done, depending on the condition and cooldowns
	 */
	public boolean isUsable(Combat combat, String owner) {
		
		if (cooldown == 0 && buildup == 0) {
			// Check condition
			if (condition != null){
				boolean conditionOutcome = condition.getOutcome(combat, owner);
				// The condition was not met
				if(conditionOutcome){
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
	public void resetCooldown() {
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
