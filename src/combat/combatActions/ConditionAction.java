package combat.combatActions;

import combat.Combat;
import combat.CombatAI;
import pokeshinto.Action;

/**
 * A condition that depends on the Action of the CombatAI
 * ex: if the conditionnal demands that the CombatAI's action is "Meditate"
 *
 * @author Jérémie Beaudoin-Dion
 */
public class ConditionAction implements Condition {

    private Action<String> desiredAction;
    private boolean isMe;

    /**
     * Constructor
     *
     * @param isMe if it checks the owner's action
     * @param desiredAction the action to compare
     */
    public ConditionAction(boolean isMe, Action<String> desiredAction) {
        this.desiredAction = desiredAction;
        this.isMe = isMe;
    }

    public boolean getOutcome(Combat combat, String owner) {
        if (owner.equals("Player") && isMe || owner.equals("Opponent") && !isMe) {
            return isDesiredAction(combat.getPlayer());
        } else {
            return isDesiredAction(combat.getOpponent());
        }
    }

    private boolean isDesiredAction(CombatAI ai) {
        try {
            return ai.getDecision().equals(desiredAction);
        } catch (NullPointerException e) {
            return false;
        }
    }

}
