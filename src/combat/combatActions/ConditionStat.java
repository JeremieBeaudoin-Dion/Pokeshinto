package combat.combatActions;

import combat.Combat;

/**
 * A ConditionStat is a Condition which relies on combat stats on a CombatAI
 * to get its outcome.
 *
 * @author Jérémie Beaudoin-Dion
 */
public class ConditionStat extends StatOriented implements Condition {

    private String equationSymbol;
    private String ifStat;
    private double statMultiplier;
    private String conditionStat;

    private double ifFlatAmount;
    private double conditionFlatAmount;

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
    public ConditionStat(String equationSymbol, String ifStat, boolean ifStatFocusMe, String conditionStat,
                     double statMultiplier, boolean conditionStatFocusMe) {
        this.equationSymbol = equationSymbol;
        this.ifStat = ifStat;
        this.statMultiplier = statMultiplier;
        this.conditionStat = conditionStat;
        this.ifStatFocusMe = ifStatFocusMe;
        this.conditionStatFocusMe = conditionStatFocusMe;

        ifFlatAmount = 0;
        conditionFlatAmount = 0;
    }

    public ConditionStat(String equationSymbol, String ifStat, double ifFlatAmount, boolean ifStatFocusMe, String conditionStat,
                         double statMultiplier, double conditionFlatAmount, boolean conditionStatFocusMe) {
        this.equationSymbol = equationSymbol;
        this.ifStat = ifStat;
        this.statMultiplier = statMultiplier;
        this.conditionStat = conditionStat;
        this.ifStatFocusMe = ifStatFocusMe;
        this.conditionStatFocusMe = conditionStatFocusMe;

        this.ifFlatAmount = ifFlatAmount;
        this.conditionFlatAmount = conditionFlatAmount;
    }

    /**
     * Returns the outcome of the condition => if the condition is met
     *
     * @param combat: the combat istance from where to find the stat
     * @param owner: the Player or the opponent
     * @return if the condition is met
     */
    public boolean getOutcome(Combat combat, String owner) {

        switch (equationSymbol) {
            case "==":
                return (ifFlatAmount + findStat(combat, owner, ifStat, ifStatFocusMe) ==
                        conditionFlatAmount + findStat(combat, owner, conditionStat, conditionStatFocusMe) * statMultiplier);
            case ">":
                return (ifFlatAmount + findStat(combat, owner, ifStat, ifStatFocusMe) >
                        conditionFlatAmount + findStat(combat, owner, conditionStat, conditionStatFocusMe) * statMultiplier);
            case "<":
                return (ifFlatAmount + findStat(combat, owner, ifStat, ifStatFocusMe) <
                        conditionFlatAmount + findStat(combat, owner, conditionStat, conditionStatFocusMe) * statMultiplier);
            case ">=":
                return (ifFlatAmount + findStat(combat, owner, ifStat, ifStatFocusMe) >=
                        conditionFlatAmount + findStat(combat, owner, conditionStat, conditionStatFocusMe) * statMultiplier);
            case "<=":
                return (ifFlatAmount + findStat(combat, owner, ifStat, ifStatFocusMe) <=
                        conditionFlatAmount + findStat(combat, owner, conditionStat, conditionStatFocusMe) * statMultiplier);
        }

        return false;
    }

}