package combat.combatActions;

import combat.Combat;
import combat.Element;

/**
 * A damage that affects Hp. It is calculated with a specific attribute and
 * the spread of the damage.
 *
 * @author Jérémie Beaudoin-Dion
 */
public class DamageHp extends StatOriented implements Damage {

    private boolean amountDealerIsMe;  // owner or other
    private String damageType;  // Physical or magical?
    private double amount;
    private String attribute;
    private int spread;
    private boolean targetIsMe;

    private boolean instant;

    private Element element;

    /**
     * Basic constructor
     *
     * @param amountDealerIsMe: if the holder is the one to deal the damage
     * @param amount: how much damage
     * @param attribute: which attribute is affected
     * @param spread: if the damage has SPREAD (from 0 to 20)
     * @param targetIsMe: if I am affected by the damage
     * @param element: the element of the damage (War, Art, Magic, etc.)
     */
    public DamageHp(boolean amountDealerIsMe, double amount, String attribute, int spread, boolean targetIsMe, Element element) {
        this.amountDealerIsMe = amountDealerIsMe;
        this.amount = amount;
        this.attribute = attribute;
        this.spread = spread;
        this.targetIsMe = targetIsMe;
        this.element = element;

        instant = false;

        if (attribute.equals("Strength")){
            damageType = "Physical";
        } else {
            damageType = "Magical";
        }
    }

    /**
     * Constructor for instant damage
     */
    public DamageHp(boolean amountDealerIsMe, double amount, String attribute, int spread, boolean targetIsMe,
                    Element element, boolean instant) {
        this.amountDealerIsMe = amountDealerIsMe;
        this.amount = amount;
        this.attribute = attribute;
        this.spread = spread;
        this.targetIsMe = targetIsMe;
        this.element = element;

        this.instant = instant;

        if (attribute.equals("Strength")){
            damageType = "Physical";
        } else {
            damageType = "Magical";
        }
    }

    // Getters
    public String getDamageType() {
        return damageType;
    }
    public Element getElement() {
        return element;
    }
    public boolean dealerIsMe() {
        return amountDealerIsMe;
    }
    public boolean affectsMe() {
        return targetIsMe;
    }
    public boolean isInstant() {
        return instant;
    }
    public void setInstant(boolean value) {
        instant = value;
    }

    /**
     * Return the total value of the damage, considering defense and element resistance
     *
     * @param combat: the current combat state of the game
     * @param damageOwner: "Player" if player is owner of the poke who does the combat
     * @return the value to reduce from target's health
     */
    public double getTotalDamage(Combat combat, String damageOwner){

        double elementResistance = findResistance(combat, damageOwner, element.getId(), targetIsMe);

        double damageValue = 0.1 * amount * ((10 + findStat(combat, damageOwner, attribute, amountDealerIsMe)) *
                (spread + 10) / (10 + findStat(combat, damageOwner, "Armor", targetIsMe))
                - spread);

        return elementResistance * damageValue;
    }
}
