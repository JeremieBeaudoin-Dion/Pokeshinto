package combat;

/**
 * A damage that will happen at a certain time
 * 
 * For the conditions, their value depend on when the check is made.
 * 		1) Start Turn == check whole stat
 * 		2) Start Encounter == check whole stat
 * 		3) End Encounter == check only dmg done during encounter
 * 		4) End Turn == check the whole stat
 * 
 * @author Jérémie Beaudoin-Dion
 *
 */
public class TriggeredDamage extends Trigger {
	
	private Damage damage;  // the damage it does

    public TriggeredDamage(int cooldown, int buildup, boolean once, Condition condition, String when, boolean affectsMe,
                    Damage damage) {
        super(cooldown, buildup, once, condition, when, affectsMe);

        this.damage = damage;
    }

	/**
	 * Constructor without condition
	 */
    public TriggeredDamage(Damage damage, String when, boolean affectsMe) {
		super(when, affectsMe);

		this.damage = damage;
	}

	Damage getDamage() {
		return damage;
	}
	
}
