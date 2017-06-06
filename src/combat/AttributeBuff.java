package combat;

/**
 * @author Jérémie Beaudoin-Dion
 */
public class AttributeBuff extends StatOriented {

    private String attributeToBuff;
    private double flatAmount;
    boolean buffMe;

    private String attributeToConsider;
    private double attributeMultiplier;
    private boolean attributeIsMe;

    public AttributeBuff(boolean buffMe, String attributeToBuff, double flatAmount, String attributeToConsider,
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
    public AttributeBuff(String attributeToBuff, double flatAmount) {
        this.attributeToBuff = attributeToBuff;
        this.flatAmount = flatAmount;
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

        return attributeMultiplier * (10 + findStat(combat, owner, attributeToConsider, attributeIsMe))/20 + flatAmount;
    }
}
