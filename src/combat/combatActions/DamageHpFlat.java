package combat.combatActions;

import combat.Combat;
import combat.Element;

/**
 * A type of damage that is not affected by the DamageHp formula
 *
 * @author Jérémie Beaudoin-Dion
 */
public class DamageHpFlat extends StatOriented implements Damage {

    private final String type;
    private boolean amountDealerIsMe;
    private double flatAmount;
    private double multiplier;
    private String attributeToMultiply;
    private boolean targetIsMe;
    private Element element;

    private boolean instant;

    /**
     * Constructor
     */
    public DamageHpFlat(boolean amountDealerIsMe, double flatAmount, double multiplier, String attributeToMultiply,
                        boolean targetIsMe, Element element, String type) {
        this.amountDealerIsMe = amountDealerIsMe;
        this.flatAmount = flatAmount;
        this.multiplier = multiplier;
        this.attributeToMultiply = attributeToMultiply;
        this.targetIsMe = targetIsMe;
        this.element = element;
        this.type = type;

        instant = false;
    }

    /**
     * Constructor for instant damage
     */
    public DamageHpFlat(boolean amountDealerIsMe, double flatAmount, double multiplier, String attributeToMultiply,
                        boolean targetIsMe, Element element, String type, boolean instant) {
        this.amountDealerIsMe = amountDealerIsMe;
        this.flatAmount = flatAmount;
        this.multiplier = multiplier;
        this.attributeToMultiply = attributeToMultiply;
        this.targetIsMe = targetIsMe;
        this.element = element;
        this.type = type;

        this.instant = instant;
    }

    public Element getElement() {
        return element;
    }

    public String getDamageType() {
        return type;
    }

    public boolean dealerIsMe() {
        return amountDealerIsMe;
    }

    public boolean affectsMe() {
        return targetIsMe;
    }

    public double getTotalDamage(Combat combat, String damageOwner) {
        double added = 0;
        double elementResistance = findResistance(combat, damageOwner, element.getId(), targetIsMe);

        if (attributeToMultiply != null) {
            added = findStat(combat, damageOwner, attributeToMultiply, amountDealerIsMe) * multiplier;
        }

        return elementResistance* (flatAmount + added);
    }

    public boolean isInstant() {
        return instant;
    }

    public void setInstant(boolean value) {
        instant = value;
    }
}
