package combat.combatActions;

import combat.Combat;

import java.io.Serializable;

/**
 * A conditional object is an object which can be tested with its conditions
 * 
 * @author Jérémie Beaudoin-Dion
 */
public abstract class Conditional implements Serializable {

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
	public Conditional(int cooldown, int buildup, boolean once, Condition condition) {
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
	public boolean isUsable(Combat combat, String owner) {

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

	public String getDescription() {
	    if (cooldown > 0) {
	        return "Cooldown : " + cooldown;
        }

        if (buildup > 0) {
            return "Buildup : " + buildup;
        }

        return null;
    }
	
	/**
	 * Resets the coolDown to its initial value
	 */
	public void resetCooldown() {
		cooldown = initCooldown;
	}

	/**
	 * Resets the buildup
	 */
    public void resetBuildup() {
        buildup = initBuildup;
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

    /**
     * When necessary, adds a certain number to the initial cooldown of a skill
     */
	public void addToInitialCooldown(int amount) {
        initCooldown += amount;
	}
	
}
