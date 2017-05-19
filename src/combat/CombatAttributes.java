package combat;

/**
 * Holds the important attributes for an AI during combat
 *
 * @author Jérémie Beaudoin-Dion
 */
public class CombatAttributes {
	
	// Basic stats
	private double strength;
	private double focus;
	private double armor;
	private double agility;
	
	// All elements reduction
	private Dictionary<Integer> elementResistance;  // change?

	/**
	 * Constructor
	 * 
	 * @param strength: how much damage can be dealt
	 * @param focus: how much magical damage can be dealt
	 * @param armor: the defense
	 * @param agility: the speed
	 * @param elementResistance: if it has any element resistance
	 */
	public CombatAttributes(double strength, double focus, double armor, double agility, Dictionary<Integer> elementResistance) {
		this.strength = strength;
		this.focus = focus;
		this.armor = armor;
		this.agility = agility;
		this.elementResistance = elementResistance;
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

	public Dictionary<Integer> getElementResistance() {
		return elementResistance;
	}

}
