package combatActions;

import combat.Combat;
import combat.DamageHolder;
import pokeshinto.Action;

/**
 * Used to describe the action of a CombatAI during combat.
 *
 * @author Jérémie Beaudoin-Dion
 */
public class ActionDescriptor {

    private Action<String> playerAction;
    private Action<String> opponentAction;

    private double playerHealthDifference;
    private double opponentHealthDifference;

    /**
     * Depending on the outcome of the combat, returns a string with a description
     * of what happenned.
     */
    public String getCombatDescription(Combat combat) {
        String healthToShow;
        String combatDescription = "Opponent ";

        if (opponentAction.getKey().equals("Meditate")){
            combatDescription += "meditated";

        } else if (opponentAction.getKey().equals("Skill")) {
            combatDescription += "used " + opponentAction.getValue();
            if (playerHealthDifference > 0) {
                healthToShow = String.format("%.1f", playerHealthDifference);
                combatDescription += " for " + healthToShow + " damage.";
            }

        } else if (opponentAction.getKey().equals("Switch")) {
            combatDescription += "switched to " + combat.getOpponentPokeshinto().getId();

        } else if (opponentAction.getKey().equals("Stun")) {
            combatDescription += " is stunned and can't move.";

        } else if (opponentAction.getKey().equals("Choose")) {
            combatDescription += " chose " + combat.getOpponentPokeshinto().getId();
        }

        combatDescription += "\n";
        combatDescription += "You ";

        if (playerAction.getKey().equals("Meditate")){
            combatDescription += "meditated";

        } else if (playerAction.getKey().equals("Skill")) {
            combatDescription += "used " + playerAction.getValue();
            if (opponentHealthDifference > 0) {
                healthToShow = String.format("%.1f", opponentHealthDifference);
                combatDescription += " for " + healthToShow + " damage.";
            }

        } else if (playerAction.getKey().equals("Switch")) {
            combatDescription += "switched to " + combat.getPlayerPokeshinto().getId();

        } else if (playerAction.getKey().equals("Stun")) {
            combatDescription += " are stunned and can't move.";

        } else if (playerAction.getKey().equals("Choose")) {
            combatDescription += " chose " + combat.getPlayerPokeshinto().getId();
        }

        return combatDescription;
    }

    /**
     * Returns a description of a DamageHolder object
     */
    private String getDamageDescription(DamageHolder damage) {
        int numberOfDamages = 0;
        String description = "";

        if (damage.get("Physical") > 0) {
            description = " for ";
            description += (int) damage.get("Physical") + " physical damage,";
            numberOfDamages++;
        }

        if (damage.get("Magical") > 0) {
            if (numberOfDamages == 0) {
                description = " for ";
            }
            description += (int) damage.get("Magical") + " magical damage,";
            numberOfDamages++;
        }

        if (damage.get("Stun") > 0) {
            if (numberOfDamages == 0) {
                description = " for ";
            }
            description += (int) damage.get("Stun") + " stun damage,";
            numberOfDamages++;
        }

        if (damage.get("Pain") > 0) {
            if (numberOfDamages == 0) {
                description = " for ";
            }
            description += (int) damage.get("Pain") + " pain damage,";
            numberOfDamages++;
        }

        if (damage.get("Dizzy") > 0) {
            if (numberOfDamages == 0) {
                description = " for ";
            }
            description += (int) damage.get("Dizzy") + " dizzy damage,";
            numberOfDamages++;
        }

        if (damage.get("Seal") > 0) {
            if (numberOfDamages == 0) {
                description = " for ";
            }
            description += (int) damage.get("Seal") + " seal damage,";
        }

        if (description.length() > 0) {
            description = description.substring(0, description.length() - 1);
            description += ".";
        }

        return description;
    }

    /**
     * Setter when the CombatAI does an action
     */
    public void setAction(String owner, Action<String> action) {
        if (owner.equals("Player")) {
            playerAction = action;
        } else {
            opponentAction = action;
        }
    }

    public void setHealth(String owner, double health) {
        if (owner.equals("Player")) {
            playerHealthDifference = health;
        } else {
            opponentHealthDifference = health;
        }
    }

    public void updateHealth(String owner, double health) {
        if (owner.equals("Player")) {
            playerHealthDifference -= health;
        } else {
            opponentHealthDifference -= health;
        }
    }

}
