package combat.combatActions;

/**
 * A owner of status which protects from a type (ex: damage)
 * and its stat (physical, magical, etc.)
 * 
 * @author Jérémie Beaudoin-Dion
 */
public class StatusProtection extends Status {
	
	private double percentage;
	private String stat;
	private String type;
	
	/**
	 * Constructor
	 * 
	 * @param percentage: from 0 to 1, how much to reduce the stat
	 * @param stat: The stat it has protection from. EX: "Physical Damage", "Magical Damage", etc.
	 * @param onMe: see Conditional
	 * @param buildUp: see Conditional
	 * @param condition: see Conditional
	 */
	public StatusProtection(double percentage, String type, String stat, boolean onMe, int buildUp, Condition condition,
                            int length){
		super(onMe, buildUp, condition, length);
		
		this.percentage = percentage;
		this.stat = stat;
		this.type = type;
	}

	public Status copy() {
	    return new StatusProtection(percentage, type, stat, onMe, initBuildup, condition, length);
    }
	
	/**
	 * Getter
	 * 
	 * @return from 0 to 1 the amount to protect
	 */
	public double getAmount() {
		return percentage;
	}
	
	/**
	 * Getter
	 * 
	 * @return the Stat to protect from
	 */
	public String getStat() {
		return stat;
	}

    /**
     * Getter
     *
     * @return the Type of the protection
     */
	public String getType() {
        return type;
    }

}
