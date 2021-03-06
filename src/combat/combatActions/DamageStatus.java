package combat.combatActions;

import combat.Combat;
import combat.Element;

/**
 * A type of Damage that will affect a Status
 *
 * @author Jérémie Beaudoin-Dion
 */
public class DamageStatus extends StatOriented implements Damage {

    private boolean amountDealerIsHolder;
    private String type;
    private double flatAmount;
    private Element element;
    private boolean targetIsMe;

    private String statToMultiply;
    private double multiplier;

    private boolean instant;

    /**
     * Basic constructor
     *
     * @param type if it affects "Stun", "Dizzy", "Pain" or "Seal"
     * @param flatAmount how much
     * @param element the element of the Damage
     */
    public DamageStatus(boolean amountDealerIsHolder, String type, double flatAmount, Element element, boolean targetIsMe) {
        this.amountDealerIsHolder = amountDealerIsHolder;
        this.type = type;
        this.flatAmount = flatAmount;
        this.element = element;
        this.targetIsMe = targetIsMe;

        statToMultiply = null;
        multiplier = 0;

        instant = false;
    }

    /**
     * Constructor for a non-flat DamageStatus
     * @param statToMultiply    The desired stat to consider when calculating damage
     * @param multiplier        The multiplier of the desired stat
     */
    public DamageStatus(boolean amountDealerIsHolder, String type, double flatAmount, Element element, boolean targetIsMe,
                        String statToMultiply, double multiplier, boolean instant) {
        this.type = type;
        this.amountDealerIsHolder = amountDealerIsHolder;
        this.flatAmount = flatAmount;
        this.element = element;
        this.targetIsMe = targetIsMe;
        this.statToMultiply = statToMultiply;
        this.multiplier = multiplier;
        this.instant = instant;
    }

    /**
     * Basic constructor for instant damage
     */
    public DamageStatus(boolean amountDealerIsHolder, String type, double flatAmount, Element element, boolean targetIsMe,
                        boolean instant) {
        this.amountDealerIsHolder = amountDealerIsHolder;
        this.type = type;
        this.flatAmount = flatAmount;
        this.element = element;
        this.targetIsMe = targetIsMe;

        statToMultiply = null;
        multiplier = 0;

        this.instant = instant;
    }

    public Element getElement() {
        return element;
    }

    public String getDamageType() {
        return type;
    }

    public boolean affectsMe() {
        return targetIsMe;
    }

    public boolean dealerIsMe() {
        return amountDealerIsHolder;
    }

    public boolean isInstant() {
        return instant;
    }

    public void setInstant(boolean value) {
        instant = value;
    }

    /**
     * The damage of a Status is always flat
     */
    public double getTotalDamage(Combat combat, String damageOwner) {

        if (statToMultiply == null) {
            return flatAmount;
        }

        return flatAmount + (findStat(combat, damageOwner, statToMultiply, targetIsMe) * multiplier);

    }

}
