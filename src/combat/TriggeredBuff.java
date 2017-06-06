package combat;

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

    /**
     * Getter
     */
    AttributeBuff getBuff() {
        return buff;
    }
}
