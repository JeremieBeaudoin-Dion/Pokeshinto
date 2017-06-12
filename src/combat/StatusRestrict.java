package combat;

/**
 * A status that restricts on what the CombatAI can do
 *
 * @author Jérémie Beaudoin-Dion
 */
public class StatusRestrict extends Status{

    private double percentage;
    private String type;
    private String stat;

    /**
     * Constructor
     *
     * @param percentage: from 0 to 1, how much to reduce the stat
     * @param type: Ex: "Skill"
     * @param stat: Ex: "War", "Slash", etc.
     * @param onMe: see Conditional
     * @param cooldown: see Conditional
     * @param buildUp: see Conditional
     * @param condition: see Conditional
     */
    public StatusRestrict(double percentage, String type, String stat, boolean onMe, int cooldown, int buildUp, Condition condition){
        super(onMe, cooldown, buildUp, condition);

        this.percentage = percentage;
        this.stat = stat;
        this.type = type;
    }

    /**
     * Getter
     *
     * @return from 0 to 1 the amount to protect
     */
    double getAmount() {
        return percentage;
    }

    /**
     * Getter
     *
     * @return the Stat to protect from
     */
    String getStat() {
        return stat;
    }

    /**
     * Getter
     *
     * @return the Type of the protection
     */
    String getType() {
        return type;
    }

}
