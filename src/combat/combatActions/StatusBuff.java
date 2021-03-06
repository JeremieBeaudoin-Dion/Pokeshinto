package combat.combatActions;

/**
 * @author Jérémie Beaudoin-Dion
 */
public class StatusBuff extends Status {

    private AttributeBuff buff;

    /**
     * Constructor
     */
    public StatusBuff(AttributeBuff buff, boolean onMe, int buildUp, Condition condition, int length) {
        super(onMe, buildUp, condition, length);

        this.buff = buff;
    }

    public AttributeBuff getBuff() {
        return buff;
    }

    public Status copy() {
        return new StatusBuff(buff, onMe, initBuildup, condition, length);
    }

}
