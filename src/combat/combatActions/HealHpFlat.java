package combat.combatActions;

import combat.Combat;

/**
 * @author Jérémie Beaudoin-Dion
 */
public class HealHpFlat extends StatOriented implements Heal {

    private boolean affectsMe;
    private String statAffected;

    private double flatAmount;

    private String statMultiplier;
    private double multiply;
    private boolean statIsMe;

    /**
     * Basic constructor
     */
    public HealHpFlat(boolean affectsMe, String statAffected, double flatAmount, double multiply, String statMultiplier, boolean statIsMe) {
        this.affectsMe = affectsMe;
        this.statAffected = statAffected;
        this.flatAmount = flatAmount;
        this.statMultiplier = statMultiplier;
        this.statIsMe = statIsMe;
        this.multiply = multiply;
    }

    /**
     * Constructor for Flat Amount
     */
    public HealHpFlat(boolean affectsMe, String statAffected, double flatAmount) {
        this.affectsMe = affectsMe;
        this.statAffected = statAffected;
        this.flatAmount = flatAmount;
        this.statMultiplier = null;
        this.multiply = 0;
    }


    @Override
    public double getTotalHeal(Combat combat, String healOwner) {
        if (statMultiplier == null) {
            return flatAmount;
        }

        return flatAmount + (findStat(combat, healOwner, statMultiplier, statIsMe) * multiply);
    }

    @Override
    public boolean getAffectsMe() {
        return affectsMe;
    }

    @Override
    public String getStatAffected() {
        return statAffected;
    }
}
