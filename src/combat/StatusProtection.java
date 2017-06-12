package combat;

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
	 * @param cooldown: see Conditional
	 * @param buildUp: see Conditional
	 * @param condition: see Conditional
	 */
	public StatusProtection(double percentage, String type, String stat, boolean onMe, int cooldown, int buildUp, Condition condition){
		super(onMe, cooldown, buildUp, condition);
		
		this.percentage = percentage;
		this.stat = stat;
		this.type = type;
	}
	
	/**
	 * Getter
	 * 
	 * @return from 0 to 1 the amount to protect
	 */
	double getAmount() {
		return percentage;
	}
	
	/**
	 * Getter
	 * 
	 * @return the Stat to protect from
	 */
	String getStat() {
		return stat;
	}

    /**
     * Getter
     *
     * @return the Type of the protection
     */
    String getType() {
        return type;
    }

}
