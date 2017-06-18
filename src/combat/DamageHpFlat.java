package combat;

/**
 * A type of damage that is not affected by the DamageHp formula
 *
 * @author Jérémie Beaudoin-Dion
 */
public class DamageHpFlat extends StatOriented implements Damage {

    private final String type;
    private boolean amountDealerIsMe;
    private double amount;
    private double multiplier;
    private String attributeToMultiply;
    private boolean targetIsMe;
    private Element element;

    /**
     * Constructor
     */
    public DamageHpFlat(boolean amountDealerIsMe, double amount, double multiplier, String attributeToMultiply,
                        boolean targetIsMe, Element element, String type) {
        this.amountDealerIsMe = amountDealerIsMe;
        this.amount = amount;
        this.multiplier = multiplier;
        this.attributeToMultiply = attributeToMultiply;
        this.targetIsMe = targetIsMe;
        this.element = element;
        this.type = type;
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

        if (attributeToMultiply != null) {
            added = findStat(combat, damageOwner, attributeToMultiply, amountDealerIsMe) * multiplier;
        }

        return amount + added;
    }
}
