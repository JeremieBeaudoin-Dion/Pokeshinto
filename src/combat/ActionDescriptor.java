package combat;

import pokeshinto.Action;
import pokeshinto.Player;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import static combat.InfoHandler.getPokeshintoDescription;

/**
 * Used to describe the action of a CombatAI during combat.
 *
 * @author Jérémie Beaudoin-Dion
 */
public class ActionDescriptor  implements Serializable {

    public static final int DESCRIPTION_COMPLEXITY_NORMAL = 1;
    public static final int DESCRIPTION_COMPLEXITY_LESS = 0;
    public static final int DESCRIPTION_COMPLEXITY_MORE = 2;

    private Action<String> playerAction;
    private Action<String> opponentAction;

    // Player
    private DamageHolder playerDamageDealtToOpponent;
    private DamageHolder playerDamageDealtToHimself;

    private DamageHolder playerHealingDone;

    // Opponent
    private DamageHolder opponentDamageDealtToPlayer;
    private DamageHolder opponentDamageDealtToHimself;

    private DamageHolder opponentHealingDone;

    /**
     * Constructor with normal complexity
     */
    ActionDescriptor() {
        playerDamageDealtToOpponent = new DamageHolder();
        playerDamageDealtToHimself = new DamageHolder();
        playerHealingDone = new DamageHolder();

        opponentDamageDealtToPlayer = new DamageHolder();
        opponentDamageDealtToHimself = new DamageHolder();
        opponentHealingDone = new DamageHolder();
    }

    /**
     * The principal method to use with actionDescriptor. It returns a list of Strings
     * to show on menus in order to describe the current combat.
     */
    public List<String> get(Combat combat) {
        switch (Player.descriptionComplexity) {
            case DESCRIPTION_COMPLEXITY_NORMAL:
                return getNormalComplexDescription(combat);

            case DESCRIPTION_COMPLEXITY_LESS:
                return getLessComplexDescription(combat);

            case DESCRIPTION_COMPLEXITY_MORE:
                return getMoreComplexDescription(combat);

        }

        throw new IllegalArgumentException("The description complexity " + Player.descriptionComplexity + " is not handled.");
    }

    private List<String> getLessComplexDescription(Combat combat) {
        String combatDescription = "";

        combatDescription += get1LineDescription("Opponent");

        combatDescription += "\n";

        combatDescription += get1LineDescription("Player");

        List<String> allCombatDescription = new LinkedList<>();
        allCombatDescription.add(combatDescription);

        return allCombatDescription;
    }

    private List<String> getMoreComplexDescription(Combat combat) {
        return null;
    }

    private List<String> getNormalComplexDescription(Combat combat) {
        List<String> listOfDescriptions = new LinkedList<>();

        listOfDescriptions.add(get2LinesDescription("Opponent", combat));

        listOfDescriptions.add(get2LinesDescription("Player", combat));

        return listOfDescriptions;
    }

    /**
     * Returns Strings representing the desired description
     */
    private String get1LineDescription(String owner) {

        String combatDescription;
        Action<String> action;

        if (owner.equals("Opponent")) {
            combatDescription = "Opponent ";
            action = opponentAction;

        } else {
            combatDescription = "You ";
            action = playerAction;
        }

        switch (action.getKey()) {
            case "Meditate":
                combatDescription += "meditated";
                return combatDescription;

            case "Skill":
                combatDescription += "used " + action.getValue();
                return combatDescription;

            case "Item":
                combatDescription += "used " + action.getValue();
                return combatDescription;

            case "Switch":
                combatDescription += "switched to";
                combatDescription += action.getValue();
                return combatDescription;

            case "Stun":
                combatDescription += getStunDescription(combatDescription);
                return combatDescription;

            case "Choose":
                if (action.getValue() == null) {
                    return null;
                }

                combatDescription += "chose " + action.getValue();
                return combatDescription;

            case "Restrict":
                combatDescription += "can't do " + action.getValue();
                return combatDescription;
        }

        throw new IllegalArgumentException("The action " + action.getKey() + " is not handled in ActionDescriptor.");
    }

    private String get2LinesDescription(String owner, Combat combat) {
        String combatDescription;
        Action<String> action;

        if (owner.equals("Opponent")) {
            combatDescription = "Opponent ";
            action = opponentAction;

        } else {
            combatDescription = "You ";
            action = playerAction;
        }

        switch (action.getKey()) {
            case "Meditate":
                combatDescription += "meditated";

                if (owner.equals("Player")) {
                    combatDescription += "\n";
                    combatDescription += getCapturedStatus(combat);
                }

                return combatDescription;

            case "Skill":
                combatDescription += get2LinesSkillDescription(owner);
                return combatDescription;

            case "Item":
                combatDescription += "used " + action.getValue();
                return combatDescription;

            case "Switch":
                combatDescription += "switched to";
                combatDescription += "\n";
                combatDescription += action.getValue() + ", " + getPokeshintoDescription(action.getValue());
                return combatDescription;

            case "Stun":
                combatDescription += getStunDescription(combatDescription);
                return combatDescription;

            case "Choose":
                if (action.getValue() == null) {
                    return null;
                }

                combatDescription += "chose";
                combatDescription += "\n";
                combatDescription += action.getValue() + ", " + getPokeshintoDescription(action.getValue());
                return combatDescription;

            case "Restrict":
                if (action.getValue() == null) {
                    return null;
                }

                combatDescription += "can't use the desired action";
                combatDescription += "\n";
                combatDescription += action.getValue() + ", is restricted ";
                return combatDescription;
        }

        throw new IllegalArgumentException("The action " + action.getKey() + " is not handled in ActionDescriptor.");
    }

    private String getCapturedStatus(Combat combat) {
        if (combat.isOpponentCapturable()) {
            return combat.getOpponent().getNumberOfTurnsLeftBeforeCapture() + " meditate left before capturing "
                    + combat.getOpponent().getCurrentPokeshintoID();
        }

        return "";
    }

    private String getStunDescription(String combatDescription) {
        if (combatDescription.equals("You ")) {
            return "are stunned and can't move";
        }

        return "is stunned and can't move";
    }

    private String get2LinesSkillDescription(String owner) {
        String skillDescription = "";

        if (owner.equals("Opponent")) {
            skillDescription += " used ";
            skillDescription += opponentAction.getValue();
            skillDescription += " (" + InfoHandler.getSkillElement(opponentAction.getValue()) + ") ";

            skillDescription += "\n";

            String specialDescription = getSpecialDescription(opponentAction.getValue());

            if (specialDescription != null) {
                skillDescription += specialDescription;
                return skillDescription;
            }

            if (opponentDamageDealtToPlayer.getHasDamage()) {
                skillDescription += getDamageDescription(opponentDamageDealtToPlayer) + " to you,";
            }

            if (opponentDamageDealtToHimself.getHasDamage()) {
                skillDescription += getDamageDescription(opponentDamageDealtToHimself) + " to himself,";
            }

            if (opponentHealingDone.getHasDamage()) {
                skillDescription += getHealingDescription(opponentHealingDone);
            }

        } else {
            skillDescription += " used ";
            skillDescription += playerAction.getValue();
            skillDescription += " (" + InfoHandler.getSkillElement(playerAction.getValue()) + ") ";

            skillDescription += "\n";

            String specialDescription = getSpecialDescription(playerAction.getValue());

            if (specialDescription != null) {
                skillDescription += specialDescription;
                return skillDescription;
            }

            if (playerDamageDealtToOpponent.getHasDamage()) {
                skillDescription += getDamageDescription(playerDamageDealtToOpponent) + " to opponent,";
            }

            if (playerDamageDealtToHimself.getHasDamage()) {
                skillDescription += getDamageDescription(playerDamageDealtToHimself) + " to yourself,";
            }

            if (playerHealingDone.getHasDamage()) {
                skillDescription += getHealingDescription(playerHealingDone);
            }
        }

        skillDescription = skillDescription.substring(0, skillDescription.length()-1);

        return skillDescription;
    }

    private String getSpecialDescription(String skillID) {
        if (skillID.equals("Parry")) {
            return "reduced all physical damage dealt";

        } else if (skillID.equals("Raise Shield")) {
            return "reduced 90% physical damage and 75% spriritual damage dealt";
        }

        return null;
    }

    /**
     * Returns a description of a DamageHolder object
     */
    private String getDamageDescription(DamageHolder damage) {
        int numberOfDamages = 0;
        String description = "";

        if (damage.get("Physical") > 0) {
            description = " for ";
            description += approximateNumber(damage.get("Physical")) + " physical damage,";
            numberOfDamages++;
        }

        if (damage.get("Magical") > 0) {
            if (numberOfDamages == 0) {
                description = " for ";
            } else {
                description += " ";
            }
            description += approximateNumber(damage.get("Magical")) + " spiritual damage,";
            numberOfDamages++;
        }

        if (damage.get("Stun") > 0) {
            if (numberOfDamages == 0) {
                description = " for ";
            } else {
                description += " ";
            }
            description += approximateNumber(damage.get("Stun")) + " stun damage,";
            numberOfDamages++;
        }

        if (damage.get("Pain") > 0) {
            if (numberOfDamages == 0) {
                description = " for ";
            } else {
                description += " ";
            }
            description += approximateNumber(damage.get("Pain")) + " pain damage,";
            numberOfDamages++;
        }

        if (damage.get("Dizzy") > 0) {
            if (numberOfDamages == 0) {
                description = " for ";
            } else {
                description += " ";
            }
            description += approximateNumber(damage.get("Dizzy")) + " dizzy damage,";
            numberOfDamages++;
        }

        if (damage.get("Seal") > 0) {
            if (numberOfDamages == 0) {
                description = " for ";
            } else {
                description += " ";
            }
            description += approximateNumber(damage.get("Seal")) + " seal damage,";
        }

        if (description.length() > 0) {
            description = description.substring(0, description.length() - 1);
        }

        return description;
    }

    private String getHealingDescription(DamageHolder heal) {
        String healDescription = "and";

        if (heal.get("Physical") > 0) {
            healDescription = " healed ";
            healDescription += approximateNumber(heal.get("Physical")) + " health,";
        }

        if (heal.get("Stun") > 0) {
            healDescription += " reduced ";
            healDescription +=  approximateNumber(heal.get("Stun")) + " stun damage,";
        }

        if (heal.get("Pain") > 0) {
            healDescription += " reduced ";
            healDescription +=  approximateNumber(heal.get("Stun")) + " pain damage,";
        }

        if (heal.get("Dizzy") > 0) {
            healDescription += " reduced ";
            healDescription +=  approximateNumber(heal.get("Stun")) + " dizzy damage,";
        }

        if (heal.get("Seal") > 0) {
            healDescription += " reduced ";
            healDescription +=  approximateNumber(heal.get("Stun")) + " seal damage,";
        }

        if (healDescription.length() > 0) {
            healDescription = healDescription.substring(0, healDescription.length() - 1);
        }

        return healDescription;
    }

    private String approximateNumber(double number) {
        if (Player.approximate) {
            return Integer.toString((int) number);
        } else {
            return String.format("%.2f", number);
        }
    }

    /**
     * Setter when the CombatAI does an action
     */
    void setAction(String owner, Action<String> action) {
        if (owner.equals("Player")) {
            playerAction = action;
        } else {
            opponentAction = action;
        }
    }

    /**
     * Setters for damages and heals
     */
    void addDamageToMe(String owner, String type, double amount) {
        if (owner.equals("Player")) {
            opponentDamageDealtToPlayer.set(type, amount);
        } else {
            playerDamageDealtToOpponent.set(type, amount);
        }
    }

    void addDamageFromMe(String owner, String type, double amount) {
        if (owner.equals("Player")) {
            playerDamageDealtToHimself.set(type, amount);
        } else {
            opponentDamageDealtToHimself.set(type, amount);
        }
    }

    public void addHealing(String owner, String type, double amount) {
        if (owner.equals("Player")) {
            playerHealingDone.set(type, amount);
        } else {
            opponentHealingDone.set(type, amount);
        }
    }

}
