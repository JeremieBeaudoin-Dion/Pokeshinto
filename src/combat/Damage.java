package combat;

/**
 * An object representing damage. Dmg is affected by focus or Strenght,
 * and is calculate with the CombatInfo formula.
 * 
 * @author Jérémie Beaudoin-Dion
 *
 */
public class Damage extends StatOriented{
	
	private boolean isMe;  // owner or other
    private String damageType;  // Physical or magical?
    private double amount;
    private String attribute;
    private int spread;
    private String targetDamage;  // owner or other

    private Element element;
    private Condition condition;
    private boolean hasCondition;

    /**
     * Basic constructor
     *
     * @param amountDealerIsMe: if the holder is the one to deal the damage
     * @param amount: how much damage
     * @param attribute: which attribute is affected
     * @param spread: if the damage has SPREAD (from 0 to 20)
     * @param targetDamage: who is affected by the damage
     * @param condition: what condition the damage must follow
     * @param element: the element of the damage (War, Art, Magic, etc.)
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
     * Constructor without conditions
     *
     * @param amountDealerIsMe: if the holder is the one to deal the damage
     * @param amount: how much damage
     * @param attribute: which attribute is affected
     * @param spread: if the damage has SPREAD (from 0 to 20)
     * @param targetDamage: who is affected by the damage
     * @param element: the element of the damage (War, Art, Magic, etc.)
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
	String getDamageType() {
		return damageType;
	}
	boolean getHasCondition() {
		return hasCondition;
	}
	
	/**
	 * Return the total value of the damage, considering defense and everything
	 * 
	 * @param combat: the current combat state of the game
	 * @param damageOwner: "Player" if player is owner of the poke who does the combat
	 * @return double damage value to reduce from target's health
	 */
	double getTotalDamage(Combat combat, String damageOwner){
		
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
