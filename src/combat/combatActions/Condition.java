package combat.combatActions;

import combat.Combat;

/**
 * A condition has an outcome which can be met or not.
 * 
 * @author Jérémie Beaudoin-Dion
 */
public interface Condition {
	
	/**
	 * Returns the outcome of the condition
	 * 
	 * @param combat: the combat istance from where to find the stat
	 * @param owner: the Player or the opponent
	 * @return if the condition is met
	 */
	boolean getOutcome(Combat combat, String owner);

}
