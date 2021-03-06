package combat;

import pokeshinto.Copyable;

import java.io.Serializable;

/**
 * Holds the important attributes for an AI during combat
 *
 * @author Jérémie Beaudoin-Dion
 */
public class CombatAttributes implements Copyable<CombatAttributes>, Serializable {
	
	// Basic stats
	private double strength;
	private double focus;
	private double armor;
	private double agility;
	
	// All elements reduction
	private Dictionary elementResistance;  // change?

	/**
	 * Constructor
	 * 
	 * @param strength: how much damage can be dealt
	 * @param focus: how much magical damage can be dealt
	 * @param armor: the defense
	 * @param agility: the speed
	 * @param elementResistance: if it has any element resistance
	 */
	public CombatAttributes(double strength, double focus, double armor, double agility, Dictionary elementResistance) {
		this.strength = strength;
		this.focus = focus;
		this.armor = armor;
		this.agility = agility;
		this.elementResistance = elementResistance;
	}

    /**
     * Modifiers
     */
    public void addStrength(double amount) {
        strength += amount;
    }
    public void addFocus(double amount) {
        focus += amount;
    }
    public void addAgility(double amount) {
        agility += amount;
    }
    public void addArmor(double amount) {
        armor += amount;
    }

	/**
	 * Getters
	 */
	public double getStrength() {
		return strength;
	}

	public double getFocus() {
		return focus;
	}

	public double getArmor() {
		return armor;
	}

	public double getAgility() {
		return agility;
	}

	public Dictionary getElementResistance() {
		return elementResistance;
	}

	public CombatAttributes copy() {
	    if (getElementResistance() == null) {
            return new CombatAttributes(strength, focus, armor, agility, null);
        }

        return new CombatAttributes(strength, focus, armor, agility, getElementResistance().copy());
    }

}
