package combat;

/**
 * A type of Damage that will
 *
 * @author Jérémie Beaudoin-Dion
 */
public class DamageStatus implements Damage {

    private String type;
    private boolean amountDealerIsMe;
    private double value;
    private Element element;
    private boolean targetIsMe;

    /**
     * Basic constructor
     *
     * @param type if it affects "Stun", "Dizzy", "Pain" or "Seal"
     * @param value how much
     * @param element the element of the Damage
     */
    public DamageStatus(boolean amountDealerIsMe, String type, double value, Element element, boolean targetIsMe) {
        this.amountDealerIsMe = amountDealerIsMe;
        this.type = type;
        this.value = value;
        this.element = element;
        this.targetIsMe = targetIsMe;
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
        return amountDealerIsMe;
    }

    /**
     * The damage of a Status is always flat
     */
    public double getTotalDamage(Combat combat, String damageOwner) {
        return value;
    }

}
