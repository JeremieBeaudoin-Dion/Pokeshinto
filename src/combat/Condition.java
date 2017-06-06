package combat;

/**
 * A condition is used in combat for a skill or a damage. If the condition is 
 * not met, then the skill or damage is not done.
 * 
 * @author Jérémie Beaudoin-Dion
 *
 */
public class Condition extends StatOriented {
	
	private String equationSymbol;
    private String ifStat;
    private double statMultiplier;
    private String conditionStat;

    private boolean ifStatFocusMe;
    private boolean conditionStatFocusMe;
	
	/**
	 * Constructor
	 * 
	 * @param equationSymbol: < , > or ==
	 * @param ifStat: the Stat to verify with the IF
	 * @param statMultiplier: Multiply by the conditionStat
	 * @param conditionStat: the threshold value
	 * @param ifStatFocusMe: if stat focus owner
	 * @param conditionStatFocusMe: if threshold focus owner
	 */
	public Condition(String equationSymbol, String ifStat, boolean ifStatFocusMe, String conditionStat,
                     double statMultiplier, boolean conditionStatFocusMe) {
		this.equationSymbol = equationSymbol;
		this.ifStat = ifStat;
		this.statMultiplier = statMultiplier;
		this.conditionStat = conditionStat;
		this.ifStatFocusMe = ifStatFocusMe;
		this.conditionStatFocusMe = conditionStatFocusMe;
	}
	
	/**
	 * Returns the outcome of the condition => if the condition is met
	 * 
	 * @param combat: the combat istance from where to find the stat
	 * @param owner: the Player or the opponent
	 * @return if the condition is met
	 */
	boolean getOutcome(Combat combat, String owner) {

        switch (equationSymbol) {
            case "==":
                return (findStat(combat, owner, ifStat, ifStatFocusMe) ==
                        findStat(combat, owner, conditionStat, conditionStatFocusMe) * statMultiplier);
            case ">":
                return (findStat(combat, owner, ifStat, ifStatFocusMe) >
                        findStat(combat, owner, conditionStat, conditionStatFocusMe) * statMultiplier);
            case "<":
                return (findStat(combat, owner, ifStat, ifStatFocusMe) <
                        findStat(combat, owner, conditionStat, conditionStatFocusMe) * statMultiplier);
            case ">=":
                return (findStat(combat, owner, ifStat, ifStatFocusMe) >=
                        findStat(combat, owner, conditionStat, conditionStatFocusMe) * statMultiplier);
            case "<=":
                return (findStat(combat, owner, ifStat, ifStatFocusMe) <=
                        findStat(combat, owner, conditionStat, conditionStatFocusMe) * statMultiplier);
        }

		return false;
	}

}
