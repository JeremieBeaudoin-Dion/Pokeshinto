package combat.combatActions;

/**
 * A negative effect on an attribute, based on a fixed number
 *
 * @author Jérémie Beaudoin-Dion
 */
public class AttributeDebuffFlat extends AttributeBuffFlat {

    /**
     * Basic constructor with all variables
     */
    public AttributeDebuffFlat(boolean buffMe, String attributeToBuff, double flatAmount, String attributeToConsider, double attributeMultiplier, boolean attributeIsMe) {
        super(buffMe, attributeToBuff, flatAmount, attributeToConsider, attributeMultiplier, attributeIsMe);
    }

    /**
     * Constructor for flat debuff
     */
    public AttributeDebuffFlat(boolean buffMe, String attributeToBuff, double flatAmount) {
        super(buffMe, attributeToBuff, flatAmount);
    }

    /**
     * Constructor for flat debuff to self
     */
    public AttributeDebuffFlat(String attributeToBuff, double flatAmount) {
        super(attributeToBuff, flatAmount);
    }
}

