package combat;

import pokeshinto.Dictionary;

public class CombatAttributes {
	
	// Basic stats
	double strength;
	double focus;
	double armor;
	double agility;
	
	// All elements reduction
	Dictionary<Integer> elementResistance;  // change?

	/**
	 * Constructor
	 * 
	 * @param strength
	 * @param focus
	 * @param armor
	 * @param agility
	 * @param elementResistance
	 */
	public CombatAttributes(double strength, double focus, double armor, double agility, Dictionary<Integer> elementResistance) {
		this.strength = strength;
		this.focus = focus;
		this.armor = armor;
		this.agility = agility;
		this.elementResistance = elementResistance;
	}

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

	public Dictionary<Integer> getElementResistance() {
		return elementResistance;
	}

}
