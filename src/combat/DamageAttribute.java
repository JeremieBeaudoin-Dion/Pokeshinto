package combat;

/**
 * This combat object is an action much like damage, but it is not affected by 
 * the CombatCalculator formula. It affects directly the selected attribute.
 * 
 * @author Jérémie Beaudoin-Dion
 *
 */
public class DamageAttribute {
	
	String amountTarget;
	double amout;
	String attribute;
	int spread;
	String targetDamage;
	String targetAttribute;

	/**
	 * Basic constructor
	 * 
	 * @param amountTarget: From who to get the attribute for amount*attribute
	 * @param amout: amout*attribute = change
	 * @param attribute: amout*attribute = change
	 * @param spread: Spread is at 0 normally. Skills and effect can set it between -10 and 10. 
	 * The effect of Strenght and Armor is augmented by 1/10 for each positive spread.
	 * @param targetDamage: who is the target of the damage
	 * @param targetAttribute: what attribute will the target suffer from?
	 */
	public DamageAttribute(String amountTarget, double amout, String attribute, int spread, String targetDamage, String targetAttribute) {
		this.amountTarget = amountTarget;
		this.targetAttribute = targetAttribute;
		this.amout = amout;
		this.attribute = attribute;
		this.spread = spread;
		this.targetDamage = targetDamage;
	}
	
	// Getters
	public String getAmountTarget() {
		return amountTarget;
	}
	public String getTargetAttribute() {
		return targetAttribute;
	}
	public double getAmout() {
		return amout;
	}
	public String getAttribute() {
		return attribute;
	}
	public int getSpread() {
		return spread;
	}
	public String getTargetDamage() {
		return targetDamage;
	}

}
