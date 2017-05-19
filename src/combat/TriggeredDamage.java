package combat;
/**
 * A damage that will happen at a certain time
 * 
 * For the conditions, their value depend on when the check is made.
 * 		1) Start turn == check whole stat
 * 		2) Start encounter == check whole stat
 * 		3) End encounter == check only dmg done during encounter
 * 		4) End turn == check the whole stat
 * 
 * @author Jérémie Beaudoin-Dion
 *
 */
public class TriggeredDamage {
	
	Damage damage;  // the damage it does
	
	int buildUp;  // Does it happen now?
	
	String when;  // When is the status updated?
	
	Condition condition;  // if it has a condition
	boolean hasCondition;
	
	/**
	 * Basic constructor
	 * 
	 * @param damage
	 * @param buildUp
	 * @param when
	 * @param condition
	 * @param hasCondition
	 */
	public TriggeredDamage(Damage damage, int buildUp, String when, Condition condition, boolean hasCondition) {
		this.damage = damage;
		this.buildUp = buildUp;
		this.when = when;
		this.condition = condition;
		this.hasCondition = hasCondition;
	}

	/**
	 * Constructor without condition
	 * 
	 * @param damage
	 * @param buildUp
	 * @param when
	 */
	public TriggeredDamage(Damage damage, int buildUp, String when) {
		this.damage = damage;
		this.buildUp = buildUp;
		this.when = when;
		
		this.condition = null;
		hasCondition = false;
	}

	public Damage getDamage() {
		return damage;
	}
	public int getBuildUp() {
		return buildUp;
	}
	public String getWhen() {
		return when;
	}
	public Condition getCondition() {
		return condition;
	}
	public boolean isHasCondition() {
		return hasCondition;
	}
	
}
