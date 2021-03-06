package combat.combatActions;

/**
 * @author Jérémie Beaudoin-Dion
 */
public class TriggeredBuff extends Trigger {

    private AttributeBuff buff;

    /**
     * Constructor
     */
    public TriggeredBuff(int cooldown, int buildup, boolean once, Condition condition, String when, boolean affectsMe,
                         AttributeBuff buff) {
        super(cooldown, buildup, once, condition, when, affectsMe);

        this.buff = buff;
    }

    public Trigger copy() {
        return new TriggeredBuff(initCooldown, initBuildup, once, condition, when, affectsMe, buff);
    }

    /**
     * Getter
     */
    public AttributeBuff getBuff() {
        return buff;
    }
}
