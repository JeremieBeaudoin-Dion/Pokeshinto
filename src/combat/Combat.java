package combat;

import combat.combatAI.AISinglePokeshinto;
import combat.combatAI.OpponentAI;
import combat.combatAI.PlayerCopy;
import combat.combatActions.*;
import pokeshinto.*;

import java.util.List;

/**
 * A combat is an object that handles a combat between player and an opponent.
 * There will be a copy of the usefull information of the player, so any changes
 * made here will not affect the player, Except the Health which will be updated
 * at the end of the combat.
 *
 * The combat class might seem heavy, but it is to make sure that the objects are
 * safe from unauthorised changes.
 *
 * Plus, the rules of the combat system are numerous and they are often infringed
 * in order to create interresting spells and items.
 *
 * @author Jérémie Beaudoin-Dion
 */
public class Combat {

    // The combattants
    private CombatAI player;
    private CombatAI opponent;

    // Game Stat
    private int turnNumber;
    private String reasonPlayerCantDoSkill;
    private ActionDescriptor actionDescriptor;

    /**
     * Constructor for the player class
     *
     * @param player:   the player of the game
     * @param opponent: an AI that will fight against the player
     */
    public Combat(PlayerCopy player, CombatAI opponent) {
        if (!(opponent instanceof OpponentAI)) {
            throw new IllegalArgumentException("The opponent given is not an opponent the Player can fight!");
        }

        this.opponent = opponent;
        this.player = player;
        turnNumber = 0;
        reasonPlayerCantDoSkill = null;
        actionDescriptor = new ActionDescriptor();

        setActionDescriptor();
    }

    private void setActionDescriptor() {
        this.player.setActionDescriptor(actionDescriptor);
        this.opponent.setActionDescriptor(actionDescriptor);

        try {
            actionDescriptor.setAction("Player", new Action<>("Choose", player.getCurrentPokeshinto().getId()));

        } catch (NullPointerException e) {
            actionDescriptor.setAction("Player", new Action<>("Choose", null));
        }

        actionDescriptor.setAction("Opponent", new Action<>("Choose", opponent.getCurrentPokeshinto().getId()));
    }

    /**
     * The player has decided his action, and sends it to the combat
     *
     * @param playerChoice : the action of the player
     */
    void setPlayerDecision(Action<String> playerChoice) {
        player.setDecision(playerChoice);
    }

    /**
     * Updates every frame
     *
     * @return true if the combat calculations occured
     */
    public boolean update() {
        switch (compareAgilities()) {
            case 1:
                if (opponent.getDecision() == null) {
                    opponent.decide(this);
                }
        }

        if (player.getDecision() != null) {

            if (opponent.getDecision() == null) {
                opponent.decide(this);
            }

            opponent.checkTriggers(this, "Start Attack");
            player.checkTriggers(this, "Start Attack");

            // Quick phase
            opponent.doQuickState(this);
            player.doQuickState(this);

            // When quick damages, if it does,
            String combatOutcome = getCombatOutcome();
            if (combatOutcome != null) {
                return true;
            }

            opponent.checkTriggers(this, "During Attack");
            player.checkTriggers(this, "During Attack");

            // Normal phase
            opponent.doChoice(this);
            player.doChoice(this);

            opponent.checkTriggers(this, "End Attack");
            player.checkTriggers(this, "End Attack");

            return true;

        }

        return false;
    }

    /**
     * Compares the two agilities.
     * If there is a 3 point difference, then there is an advantage
     *
     * @return 1 if the player wins, 0 if equal or -1 if the player loses
     */
    int compareAgilities() {
        int difference = (int) (player.getAgility() - opponent.getAgility());

        if (difference < -2) {
            return -1;
        } else if (difference > 2) {
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * Ends the turn and deals the damage
     */
    void endTurn() {
        opponent.doDamage(this, player.getDamageDealtToOther());
        player.doDamage(this, opponent.getDamageDealtToOther());

        player.endTurn(this);
        opponent.endTurn(this);
    }

    String getCombatOutcome() {
        if (opponent.getCurrentHealth() <= 0) {
            return "Win";
        }

        if (player.getCurrentHealth() <= 0) {
            return "Loss";
        }

        if (opponent.getCaptured()) {
            return "Captured";
        }

        return null;
    }

    int getCombatElementWarOutcome() {
        if (player.getCurrentPokeshinto() == null || player.getSkillAlignment() == null) {
            return 0;
        }

        return player.getSkillAlignment().compareTo(opponent.getSkillAlignment());
    }

    /**
     * Resets the choice for the next turn
     */
    void newTurn() {
        if (turnNumber > 0) {
            player.newTurn(this);
            opponent.newTurn(this);

        } else {
            player.startCombat(this);
            opponent.startCombat(this);
        }

        turnNumber++;
    }

    /**
     * Adds an object to player or opponent
     */
    void addPlayerDamage(Damage damage) {
        player.addDamage(this, damage);
    }

    void addOpponentDamage(Damage damage) {
        opponent.addDamage(this, damage);
    }

    void addPlayerStatus(Status status) {
        player.addStatus(status);
    }

    void addOpponentStatus(Status status) {
        opponent.addStatus(status);
    }

    void addPlayerTrigger(Trigger trigger) {
        player.addTrigger(trigger);
    }

    void addOpponentTrigger(Trigger trigger) {
        opponent.addTrigger(trigger);
    }

    void addPlayerBuff(AttributeBuff buff) {
        player.addBuff(this, buff);
    }

    void addOpponentBuff(AttributeBuff buff) {
        opponent.addBuff(this, buff);
    }

    void addPlayerHeal(Heal heal) {
        player.addHeal(this, heal);
    }

    void addOpponentHeal(Heal heal) {
        opponent.addHeal(this, heal);
    }

    /**
     * @param action: the action to make
     * @return if the action is doable
     */
    boolean getPlayerActionIsDoable(Action<String> action) {
        if (action.getKey().equals("Root")) {
            switch (action.getValue()) {
                case "Skill":
                    return player.getCurrentPokeshinto() != null;

                case "Switch":
                    return player.getAllPokeshintos().size() > 1;

                case "Attack":
                    return player.getCurrentPokeshinto() != null;

                case "Item":
                    return player.getAllItems().size() != 0;
            }

        } else if (action.getKey().equals("Skill")) {
            reasonPlayerCantDoSkill = player.getWhySkillCantBeDone(action.getValue());
            return player.getSkillIsUsable(this, action.getValue());

        } else if (action.getKey().equals("Item")) {
            return player.getItemIsUsable(action.getValue());
        }

        return true;
    }

    boolean getOpponentSkillIsDoable(String skillID) {
        return opponent.getSkillIsUsable(this, skillID);
    }

    /**
     * When a skill can't be done, this returns the reason why
     */
    String getWhyPlayerCantDoThat() {
        return reasonPlayerCantDoSkill;
    }

    int getTurnNumber() {
        return turnNumber;
    }

    /**
     * Implements the number of tries to capture opponent
     */
    void doOpponentCapture() {
        if (Player.allShintos.size() <= Player.MAX_NUMBER_OF_POKESHINTO && isOpponentCapturable()) {
            opponent.doCapture();
        }
    }

    /**
     * @return the description of the turn
     */
    List<String> getActionDescriptor() {
        return actionDescriptor.get(this);
    }

    /**
     * Gets the player and the AI
     */
    public CombatAI getPlayer() {
        return player;
    }

    public CombatAI getOpponent() {
        return opponent;
    }

    public int getXpForCombat() {
        return ((OpponentAI) opponent).getXp();
    }

    boolean isOpponentCapturable() {
        return opponent instanceof AISinglePokeshinto;
    }

}
