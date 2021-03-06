package combat.combatActions;

/**
 * A trigger that will remove all negative effects of certain kind
 *
 * @author Jérémie Beaudoin-Dion
 */
public class TriggeredDispel extends Trigger {

    private String key;
    private String value;
    private boolean hasCondition;

    /**
     * Constructor with conditions
     * @param key      the name of the status to Dispel
     * @param value    the specification of the status to Dispel
     */
    public TriggeredDispel(int cooldown, int buildup, boolean once, Condition condition, String when, String key, String value) {
        super(cooldown, buildup, once, condition, when, true);
        this.key = key;
        this.value = value;
        hasCondition = true;
    }

    /**
     * Constructor without conditions
     * @param key      the name of the status to Dispel
     * @param value    the specification of the status to Dispel
     */
    public TriggeredDispel(String when, String key, String value) {
        super(when, true);
        this.key = key;
        this.value = value;
        hasCondition = false;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    @Override
    public Trigger copy() {
        if (hasCondition) {
            return new TriggeredDispel(cooldown, buildup, once, condition, when, key, value);
        }

        return new TriggeredDispel(when, key, value);
    }
}
