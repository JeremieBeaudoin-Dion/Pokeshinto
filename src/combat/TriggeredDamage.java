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
