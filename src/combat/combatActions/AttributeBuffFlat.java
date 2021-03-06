package combat.combatActions;

import combat.Combat;

/**
 * A positive effect on an attribute, based on a fixed number
 *
 * @author Jérémie Beaudoin-Dion
 */
public class AttributeBuffFlat extends StatOriented implements AttributeBuff {

    private String attributeToBuff;
    private double flatAmount;
    private boolean buffMe;

    private String attributeToConsider;
    private double attributeMultiplier;
    private boolean attributeIsMe;

    /**
     * Basic constructor with all variables
     */
    public AttributeBuffFlat(boolean buffMe, String attributeToBuff, double flatAmount, String attributeToConsider,
                         double attributeMultiplier, boolean attributeIsMe) {
        this.buffMe = buffMe;
        this.attributeToBuff = attributeToBuff;
        this.flatAmount = flatAmount;

        this.attributeToConsider = attributeToConsider;
        this.attributeMultiplier = attributeMultiplier;
        this.attributeIsMe = attributeIsMe;
    }

    /**
     * Constructor for flat buff
     */
    public AttributeBuffFlat(boolean buffMe, String attributeToBuff, double flatAmount) {
        this.buffMe = buffMe;
        this.attributeToBuff = attributeToBuff;
        this.flatAmount = flatAmount;

        this.attributeToConsider = null;
        this.attributeMultiplier = 0.0;
        this.attributeIsMe = false;
    }

    /**
     * Constructor for flat buff to self
     */
    public AttributeBuffFlat(String attributeToBuff, double flatAmount) {
        this.attributeToBuff = attributeToBuff;
        this.flatAmount = flatAmount;
        buffMe = true;
    }

    /**
     * Getters
     */
    public boolean isOnMe() {
        return buffMe;
    }

    public String getAttribute() {
        return attributeToBuff;
    }

    public double getAmount(Combat combat, String owner) {
        if (attributeToConsider == null) {
            return flatAmount;
        }

        return attributeMultiplier * (1 + 0.025 * findStat(combat, owner, attributeToConsider, attributeIsMe)) + flatAmount;
    }
}
