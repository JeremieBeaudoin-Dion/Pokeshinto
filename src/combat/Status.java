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
	 * @param onMe: is it on me, or the other?
	 * @param effecType: "Protection", 
	 * @param effectModifier: EX: 20*Strenght
	 * @param effectStat: What will it affect?
	 * @param howMuch: amount desired
	 * @param howLong: Will it last?
	 * @param buildUp: Does it happen now?
	 * @param when: When is the status updated?
	 * @param condition: if not met, don't do the effect
	 */
	public Status(boolean onMe, int cooldown, int buildUp, Condition condition) {
		super(cooldown, buildUp, false, condition);
		this.onMe = onMe;
	}
	
	/**
	 * Getters
	 * 
	 * @return if the Status is on the caster or not
	 */
	public boolean isOnMe() {
		return onMe;
	}
	
	public boolean isEnded() {
		return cooldown == 0;
	}
	

}
