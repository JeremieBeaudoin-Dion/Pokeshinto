package combat;
/**
 * A type of status which protects from certain damage
 * 
 * @author Jérémie Beaudoin-Dion
 *
 */
public class StatusProtection extends Status {
	
	private double percentage;
	private String stat;
	
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
	public StatusProtection(double percentage, String stat, boolean onMe, int cooldown, int buildUp, Condition condition){
		super(onMe, cooldown, buildUp, condition);
		
		this.percentage = percentage;
		this.stat = stat;
	}
	
	/**
	 * Getter
	 * 
	 * @return: from 0 to 1 the amount to protect
	 */
	public double getAmount() {
		return percentage;
	}
	
	/*
	 * Getter
	 * 
	 * @return: the Stat to protect from
	 */
	public String getStat() {
		return stat;
	}

}
