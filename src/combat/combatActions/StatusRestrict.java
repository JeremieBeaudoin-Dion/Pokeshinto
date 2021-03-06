package combat.combatActions;

/**
 * A status that restricts on what the CombatAI can do
 *
 * @author Jérémie Beaudoin-Dion
 */
public class StatusRestrict extends Status{

    private String type;
    private String stat;

    /**
     * Constructor
     *
     * @param type: Ex: "Skill"
     * @param stat: Ex: "War", "Slash", etc.
     * @param onMe: see Conditional
     * @param buildUp: see Conditional
     * @param condition: see Conditional
     */
    public StatusRestrict(String type, String stat, boolean onMe, int buildUp, Condition condition, int length){
        super(onMe, buildUp, condition, length);

        this.stat = stat;
        this.type = type;
    }

    public Status copy() {
        return new StatusRestrict(type, stat, onMe, initBuildup, condition, length);
    }

    /**
     * Getter
     *
     * @return the Stat to protect from
     */
    public String getStat() {
        return stat;
    }

    /**
     * Getter
     *
     * @return the Type of the protection
     */
    public String getType() {
        return type;
    }

}
