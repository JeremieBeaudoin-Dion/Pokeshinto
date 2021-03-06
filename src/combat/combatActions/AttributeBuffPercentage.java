package combat.combatActions;

import combat.Combat;

/**
 * A positive effect on an attribute, based on a stat that will be
 * multiplied by a specific number. That number depends on Strenght
 * or Focus.
 *
 * @author Jérémie Beaudoin-Dion
 */
public class AttributeBuffPercentage extends StatOriented implements AttributeBuff {

    private String attributeToBuff;
    private boolean buffMe;

    private String attributeToConsider;
    private String attributeTofind;
    private double attributeMultiplier;
    private boolean attributeIsMe;

    /**
     * Constructor
     */
    public AttributeBuffPercentage(String attributeToBuff, boolean buffMe, String attributeToConsider, double attributeMultiplier, String attributeTofind, boolean attributeIsMe) {
        this.attributeToBuff = attributeToBuff;
        this.buffMe = buffMe;
        this.attributeToConsider = attributeToConsider;
        this.attributeMultiplier = attributeMultiplier;
        this.attributeIsMe = attributeIsMe;
        this.attributeTofind = attributeTofind;
    }

    /**
     * Getters
     */
    @Override
    public boolean isOnMe() {
        return buffMe;
    }

    @Override
    public String getAttribute() {
        return attributeToBuff;
    }

    @Override
    public double getAmount(Combat combat, String owner) {
        return findStat(combat, owner, attributeTofind, attributeIsMe) * attributeMultiplier * (1 + 0.025 * findStat(combat, owner, attributeToConsider, attributeIsMe));
    }
}
