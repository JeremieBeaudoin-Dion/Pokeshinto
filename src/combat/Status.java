package combat;

/**
 * A status has a cooldown, and conditions to be met. It can last
 * one or more turns. It does an effect on the desired shinto.
 * 
 * @author Jérémie Beaudoin-Dion
 *
 */
public class Status extends Conditional {
	
	//TODO: ABSTRACT
	private boolean onMe;  // is it on me, or the other?

    /**
     * Constructor
     *
     * @param onMe: if the status will take effect on the caster
     * @param cooldown: how many steps have to pass before it takes effect another time
     * @param buildUp: how many steps have to pass before it takes effect the first time
     * @param condition: if it must follow a certain condition
     */
	Status(boolean onMe, int cooldown, int buildUp, Condition condition) {
		super(cooldown, buildUp, false, condition);
		this.onMe = onMe;
	}
	
	/**
	 * Getters
	 * 
	 * @return if the Status is on the caster or not
	 */
	boolean isOnMe() {
		return onMe;
	}
	
	boolean isEnded() {
		return cooldown == 0;
	}
	

}
