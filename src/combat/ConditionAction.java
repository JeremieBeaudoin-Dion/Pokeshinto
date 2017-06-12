package combat;

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
            return isPlayerAction(combat);
        } else {
            return isOpponentAction(combat);
        }
    }

    private boolean isPlayerAction(Combat combat) {
        return combat.getPlayerChoice().equals(desiredAction);
    }

    private boolean isOpponentAction(Combat combat) {
        return combat.getOpponentChoice().equals(desiredAction);
    }

}
