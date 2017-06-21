package combatActions;

/**
 * A negative effect on an attribute
 *
 * @author Jérémie Beaudoin-Dion
 */
public class AttributeDebuff extends AttributeBuff {

    /**
     * Basic constructor with all variables
     *
     * @param buffMe
     * @param attributeToBuff
     * @param flatAmount
     * @param attributeToConsider
     * @param attributeMultiplier
     * @param attributeIsMe
     */
    public AttributeDebuff(boolean buffMe, String attributeToBuff, double flatAmount, String attributeToConsider, double attributeMultiplier, boolean attributeIsMe) {
        super(buffMe, attributeToBuff, flatAmount, attributeToConsider, attributeMultiplier, attributeIsMe);
    }

    /**
     * Constructor for flat buff
     *
     * @param buffMe
     * @param attributeToBuff
     * @param flatAmount
     */
    public AttributeDebuff(boolean buffMe, String attributeToBuff, double flatAmount) {
        super(buffMe, attributeToBuff, flatAmount);
    }

    /**
     * Constructor for flat buff
     *
     * @param attributeToBuff
     * @param flatAmount
     */
    public AttributeDebuff(String attributeToBuff, double flatAmount) {
        super(attributeToBuff, flatAmount);
    }
}

