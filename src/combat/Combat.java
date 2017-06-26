package combat;

import combatActions.*;
import pokeshinto.*;

import java.util.List;

/**
 * A combat is an object that handles a combat between player and an opponent.
 * There will be a copy of the usefull information of the player, so any changes
 * made here will not affect the player, Except the Health which will be updated
 * at the end of the combat.
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
        int difference = (int) (getPlayerAgility() - getOpponentAgility());

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
     *
     * @return the outcome of the combat
     * "Death" => if the player or opponent has 0 HP
     * "Captured" => if the enemy has been captured
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
            if (action.getValue().equals("Skill")) {
                if (player.getCurrentPokeshinto() == null) {
                    return false;
                }

            } else if (action.getValue().equals("Switch")) {
                if (player.getAllPokeshintos().size() <= 1) {
                    return false;
                }

            } else if (action.getValue().equals("Attack")) {
                if (player.getCurrentPokeshinto() == null) {
                    return false;
                }

            } else if (action.getValue().equals("Items")) {
                return false;

            }

        } else if (action.getKey().equals("Skill")) {
            reasonPlayerCantDoSkill = player.getWhySkillCantBeDone(action.getValue());
            return player.getSkillIsUsable(this, action.getValue());
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
        if (Player.allShintos.size() <= Player.MAX_NUMBER_OF_POKESHINTO) {
            opponent.doCapture();
        }
    }

    /**
     * @return the description of the turn
     */
    public List<String> getActionDescriptor() {
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

    /**
     * The getters here are very long and there are many, but they ensure that
     * the player and the opponent don't get tampered with.
     * <p>
     * They are also very usefull for the accessibility of the stats for the
     * Skill and Damage classes.
     */
    // Getters Player
    public double getPlayerMaxHp() {
        return player.getMaxHealth();
    }

    public double getPlayerCurrentHp() {
        return player.getCurrentHealth();
    }

    public double getPlayerMissingHp() {
        return player.getMaxHealth() - player.getCurrentHealth();
    }

    public double getPlayerMissingHpPercentage() {
        return player.getCurrentHealth() / player.getMaxHealth();
    }

    public double getPlayerStrength() {
        if (getPlayerPokeshinto() == null) {
            return player.getCombatAttributes().getStrength();
        }
        return player.getCombatAttributes().getStrength() + player.getCurrentPokeshinto().getCombatAttributes().getStrength();
    }

    public double getPlayerFocus() {
        if (getPlayerPokeshinto() == null) {
            return player.getCombatAttributes().getFocus();
        }
        return player.getCombatAttributes().getFocus() + player.getCurrentPokeshinto().getCombatAttributes().getFocus();
    }

    public double getPlayerArmor() {
        if (getPlayerPokeshinto() == null) {
            return player.getCombatAttributes().getArmor();
        }
        return player.getCombatAttributes().getArmor() + player.getCurrentPokeshinto().getCombatAttributes().getArmor();
    }

    public double getPlayerAgility() {
        if (getPlayerPokeshinto() == null) {
            return player.getCombatAttributes().getAgility();
        }
        return player.getCombatAttributes().getAgility() + player.getCurrentPokeshinto().getCombatAttributes().getAgility();
    }

    public Action<String> getPlayerChoice() {
        return player.getDecision();
    }

    public int getPlayerNumberOfPokeshinto() {
        return player.getNumberOfPokeshinto();
    }

    public Pokeshinto getPlayerPokeshinto() {
        return player.getCurrentPokeshinto();
    }

    public int getPlayerPokeshintoTurn() {
        return player.getCurrentPoketurn();
    }

    public boolean getPlayerSkillIsUsable(String id) {
        return player.getSkillIsUsable(this, id);
    }

    public Dictionary getPlayerTimeSpendByPokeshinto() {
        return player.getTimeSpentByPokeshinto();
    }

    public DamageHolder getAllPlayerDamageDealt() {
        DamageHolder allDamage = new DamageHolder();

        allDamage.add(player.getDamageDealtToOther());
        allDamage.add(player.getDamageDealtToMe());

        return allDamage;
    }

    public DamageHolder getAllPlayerDamageRecieved() {
        DamageHolder allDamage = new DamageHolder();

        allDamage.add(opponent.getDamageDealtToOther());
        allDamage.add(player.getDamageDealtToMe());

        return allDamage;
    }

    public int getPlayerResistanceFrom(String element) {
        return player.getResistanceFrom(element);
    }

    /**
     * Getters Opponent
     */
    public boolean isOpponentCapturable() {
        return opponent instanceof AISinglePokeshinto;
    }

    public int getNumberOfTurnsLeftBeforeCapture() {
        return opponent.getNumberOfTurnsLeftBeforeCapture();
    }

    public double getOpponentMaxHp() {
        return opponent.getMaxHealth();
    }

    public double getOpponentCurrentHp() {
        return opponent.getCurrentHealth();
    }

    public double getOpponentMissingHp() {
        return opponent.getMaxHealth() - opponent.getCurrentHealth();
    }

    public double getOpponentMissingHpPercentage() {
        return opponent.getCurrentHealth() / opponent.getMaxHealth();
    }

    public double getOpponentStrength() {
        return opponent.getCombatAttributes().getStrength() + opponent.getCurrentPokeshinto().getCombatAttributes().getStrength();
    }

    public double getOpponentFocus() {
        return opponent.getCombatAttributes().getFocus() + opponent.getCurrentPokeshinto().getCombatAttributes().getFocus();
    }

    public double getOpponentArmor() {
        return opponent.getCombatAttributes().getArmor() + opponent.getCurrentPokeshinto().getCombatAttributes().getArmor();
    }

    public double getOpponentAgility() {
        return opponent.getCombatAttributes().getAgility() + opponent.getCurrentPokeshinto().getCombatAttributes().getAgility();
    }

    public Action<String> getOpponentChoice() {
        return opponent.getDecision();
    }

    public Pokeshinto getOpponentPokeshinto() {
        return opponent.getCurrentPokeshinto();
    }

    public int getOpponentPokeshintoTurn() {
        return opponent.getCurrentPoketurn();
    }

    public boolean getOpponentSkillIsUsable(String id) {
        return opponent.getSkillIsUsable(this, id);
    }

    public DamageHolder getAllOpponentDamageDealt() {
        DamageHolder allDamage = new DamageHolder();

        allDamage.add(opponent.getDamageDealtToOther());
        allDamage.add(opponent.getDamageDealtToMe());

        return allDamage;
    }

    public DamageHolder getAllOpponentDamageRecieved() {
        DamageHolder allDamage = new DamageHolder();

        allDamage.add(player.getDamageDealtToOther());
        allDamage.add(opponent.getDamageDealtToMe());

        return allDamage;
    }

    public int getOpponentTotalLevel() {
        return opponent.getAllLevels();
    }

    public int getOpponentResistanceFrom(String element) {
        return opponent.getResistanceFrom(element);
    }

}
