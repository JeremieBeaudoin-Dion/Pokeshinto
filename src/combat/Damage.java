package combat;
/**
 * An object representing damage. Dmg is affected by focus or Strenght,
 * and is calculate with the CombatInfo formula.
 * 
 * @author Jérémie Beaudoin-Dion
 *
 */
public class Damage extends StatOriented{
	
	boolean isMe;  // owner or other
	String damageType;  // Physical or magical?
	double amount;
	String attribute;
	int spread;
	String targetDamage;  // owner or other
	
	Element element;
	Condition condition;
	boolean hasCondition;

	/**
	 * Basic constructor
	 * 
	 * @param amountTarget: From who to get the attribute for amount*attribute
	 * @param amout: amout*attribute = damage
	 * @param attribute: amout*attribute = damage
	 * @param spread: Spread is at 0 normally. Skills and effect can set it between -10 and 10. 
	 * The effect of Strenght and Armor is augmented by 1/10 for each positive spread.
	 * @param targetDamage: who is the target of the damage
	 */
	public Damage(boolean amountDealerIsMe, double amount, String attribute, int spread, String targetDamage, Condition condition, Element element) {
		this.isMe = amountDealerIsMe;
		this.amount = amount;
		this.attribute = attribute;
		this.spread = spread;
		this.targetDamage = targetDamage;
		this.condition = condition;
		this.element = element;
		
		if (condition == null){
			hasCondition = false;
		} else {
			hasCondition = true;
		}
		
		if (attribute.equals("Strength")){
			damageType = "Physical";
		} else {
			damageType = "Magical";
		}
	}
	
	/**
	 * Constructor without condition
	 * 
	 * @param amountDealerIsMe
	 * @param damageType
	 * @param amount
	 * @param attribute
	 * @param spread
	 * @param targetDamage
	 */
	public Damage(boolean amountDealerIsMe, double amount, String attribute, int spread, String targetDamage, Element element) {
		this.isMe = amountDealerIsMe;
		this.amount = amount;
		this.attribute = attribute;
		this.spread = spread;
		this.targetDamage = targetDamage;
		this.element = element;
		
		if (attribute.equals("Strength")){
			damageType = "Physical";
		} else {
			damageType = "Magical";
		}
		
		this.condition = null;
		hasCondition = false;
	}
	
	// Getters
	public String getDamageType() {
		return damageType;
	}
	public boolean getHasCondition() {
		return hasCondition;
	}
	
	/**
	 * Return the total value of the damage, considering defense and everything
	 * 
	 * @param combat - the current combat state of the game
	 * @param damageOwner - "Player" if player is owner of the poke who does the combat
	 * @return double damage value to reduce from target's health
	 */
	public double getTotalDamage(Combat combat, String damageOwner){
		
		// Check condition
		if (hasCondition){
			boolean conditionOutcome = condition.getOutcome(combat, damageOwner);
			// The condition was not met
			if(!conditionOutcome){
				throw new ExceptionConditionNotMet();
			}
		}
		
		return 0.1 * amount * ((10 + findStat(combat, damageOwner, attribute, isMe)) * 
					(spread + 10) / (10 + findStat(combat, damageOwner, "Armor", !isMe)) 
					- spread);
	}	
}
