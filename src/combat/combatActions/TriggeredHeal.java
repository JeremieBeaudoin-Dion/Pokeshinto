package combat.combatActions;

/**
 * @author Jérémie Beaudoin-Dion
 */
public class TriggeredHeal extends Trigger{

    private Heal heal;

    /**
     * Constructor
     */
    public TriggeredHeal(int cooldown, int buildup, boolean once, Condition condition, String when, boolean affectsMe,
                         Heal heal) {
        super(cooldown, buildup, once, condition, when, affectsMe);

        this.heal = heal;
    }

    public Trigger copy() {
        return new TriggeredHeal(initCooldown, initBuildup, once, condition, when, affectsMe, heal);
    }

    /**
     * Getter
     */
    public Heal getHeal() {
        return heal;
    }

}
