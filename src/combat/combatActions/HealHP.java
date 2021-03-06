package combat.combatActions;

import combat.Combat;

/**
 * @author Jérémie Beaudoin-Dion
 */
public class HealHP extends StatOriented implements Heal {

    private boolean affectsMe;
    private double amount;
    private String statAffected;
    private String statMultiplier;
    private boolean statIsMe;

    /**
     * Constructor
     */
    public HealHP(boolean affectsMe, double amount, String statAffected, String statMultiplier, boolean statIsMe) {
        this.affectsMe = affectsMe;
        this.amount = amount;
        this.statAffected = statAffected;
        this.statMultiplier = statMultiplier;
        this.statIsMe = statIsMe;
    }

    public double getTotalHeal(Combat combat, String healOwner) {
        return amount * (10 + findStat(combat, healOwner, statMultiplier, statIsMe)/20);
    }

    public boolean getAffectsMe() {
        return affectsMe;
    }

    public String getStatAffected() {
        return statAffected;
    }
}
