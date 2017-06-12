package combat;

import pokeshinto.Action;
import pokeshinto.KeyException;
import pokeshinto.Pokeshinto;

/**
 * A combat is an object that handles a combat between player and an opponent.
 * There will be a copy of the usefull information of the player, so any changes
 * made here will not affect the player, Except the Health which will be updated
 * at the end of the combat.
 * 
 * @author Jérémie Beaudoin-Dion
 *
 */
public class Combat {
	
	// The combattants
	private CombatAI player;
	private CombatAI opponent;
	
	// Game Stat
	private int turnNumber;
	
	/**
	 * Constructor for the player class
	 * 
	 * @param player: the player of the game
	 * @param opponent: an AI that will fight against the player
	 */
	public Combat(PlayerCopy player, CombatAI opponent) {
		this.opponent = opponent;
		this.player = player;
		turnNumber = 0;
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
		if (player.getDecision() != null) {
			opponent.decide(this);

			opponent.checkTriggers(this, "Start Attack");
            player.checkTriggers(this, "Start Attack");

			opponent.doChoice(this);
			player.doChoice(this);

            opponent.checkTriggers(this, "End Attack");
            player.checkTriggers(this, "End Attack");

			return true;
			
		}
		return false;
	}
	
	/**
	 * Ends the turn and deals the damage
     *
     * @return the outcome of the combat
     * 			"Death" => if the player or opponent has 0 HP
     * 			"Captured" => if the enemy has been captured
	 */
	String endTurn() {
		opponent.doDamage(this, player.getPhysicalDamageDealt(), player.getMagicalDamageDealt());
		player.doDamage(this, opponent.getPhysicalDamageDealt(), opponent.getMagicalDamageDealt());

		player.endTurn(this);
		opponent.endTurn(this);

        if (opponent.getHealth() <= 0 || player.getHealth() <= 0){
            return "Death";
        } if (opponent.getCaptured()) {
            return "Captured";
        }

        return null;
	}
	
	/**
	 * Resets the choice for the next turn
	 */
	void newTurn(){
		
		// Set the player's choice back to null
		player.newTurn(this);
		opponent.newTurn(this);

		turnNumber++;
	}

    /**
     * Adds an object to player or opponent
     */
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
	
	/**
	 * @param action: the action to make
	 * @return if the action is doable
	 */
	boolean getPlayerActionIsDoable(Action<String> action) {
	    if (action.getKey().equals("Root")) {
            if (action.getValue().equals("Skill")){
                if (player.getCurrentPokeshinto() == null) {
                    return false;
                }

            } else if (action.getValue().equals("Switch")) {
                if (player.allPokeshintos.size() <= 1) {
                    return false;
                }

            } else if (action.getValue().equals("Attack")) {
                if (player.getCurrentPokeshinto() == null) {
                    return false;
                }

            } else if (action.getValue().equals("Items")) {
                return false;

            }
        }

        else if (action.getKey().equals("Skill")) {
            return player.getSkillIsUsable(this, action.getValue());
        }

        return true;
    }

	int getTurnNumber() {
	    return turnNumber;
    }

    /**
     * Implements the number of tries to capture opponent
     */
    void doOpponentCapture() {
        if (player.getCurrentPokeshinto() == null) {
            opponent.doCapture();
            return;
        }

        if (!player.getCurrentPokeshinto().getId().equals(opponent.getCurrentPokeshinto().getId())) {
            opponent.doCapture();
        }
    }
	
	/**
	 * The getters here are very long and there are many, but they ensure that
	 * the player and the opponent don't get tampered with.
	 * 
	 * They are also very usefull for the accessibility of the stats for the 
	 * Skill and Damage classes.
	 */
	// Getters Player
	double getPlayerMaxHp() {
		return player.getMaxHealth();
	}
	double getPlayerCurrentHp() {
		return player.getCurrentHealth();
	}
	double getPlayerMissingHp() {
		return player.getMaxHealth() - player.getCurrentHealth();
	}
	double getPlayerMissingHpPercentage() {
		return player.getCurrentHealth() / player.getMaxHealth();
	}
	double getPlayerStrength() {
	    if (getPlayerPokeshinto() == null) {
            return player.getCombatAttributes().getStrength();
        }
		return player.getCombatAttributes().getStrength() + player.getCurrentPokeshinto().getCombatAttributes().getStrength();
	}
	double getPlayerFocus() {
        if (getPlayerPokeshinto() == null) {
            return player.getCombatAttributes().getFocus();
        }
		return player.getCombatAttributes().getFocus() + player.getCurrentPokeshinto().getCombatAttributes().getFocus();
	}
	double getPlayerArmor() {
        if (getPlayerPokeshinto() == null) {
            return player.getCombatAttributes().getArmor();
        }
		return player.getCombatAttributes().getArmor() + player.getCurrentPokeshinto().getCombatAttributes().getArmor();
	}
	double getPlayerAgility() {
        if (getPlayerPokeshinto() == null) {
            return player.getCombatAttributes().getAgility();
        }
		return player.getCombatAttributes().getAgility() + player.getCurrentPokeshinto().getCombatAttributes().getAgility();
	}
	Dictionary<Integer> getAllPlayerElementResistance() {
	    if (getPlayerPokeshinto() == null) {
	        return player.getCombatAttributes().getElementResistance();
        }
		return player.getCurrentPokeshinto().getCombatAttributes().getElementResistance();
	}
	int getPlayerElementResistance(Element element) {
		
		int elementResistance = 0;
		
		try {
			player.getCurrentPokeshinto().getCombatAttributes().getElementResistance().getValueByKey(element.getId());
		} catch (KeyException e) {
			elementResistance = 0;
		}
		
		return elementResistance;
	}
	Action<String> getPlayerChoice() {
		return player.getDecision();
	}
	int getPlayerDamageDone() {
		return (int) (player.getPhysicalDamageDealt() + player.getMagicalDamageDealt());
	}
	Pokeshinto getPlayerPokeshinto() {
		return player.getCurrentPokeshinto();
	}
	String getPlayerPokeshintoID() {
		if (getPlayerPokeshinto() == null) {
			return "Player";
		}
		return getPlayerPokeshinto().getId();
	}
	String getPlayerPokeshintoDescription() {
		if (getPlayerPokeshinto() == null) {
			return "Adventurer";
		}
		return getPlayerPokeshinto().getDescription();
	}
	boolean getPlayerSkillIsUsable(String id) {
		return player.getSkillIsUsable(this, id);
	}

    /**
     * Getters Opponent
     */
	double getOpponentMaxHp() {
		return opponent.getMaxHealth();
	}
	double getOpponentCurrentHp() {
		return opponent.getCurrentHealth();
	}
	double getOpponentMissingHp() {
		return opponent.getMaxHealth() - opponent.getCurrentHealth();
	}
	double getOpponentMissingHpPercentage() {
		return opponent.getCurrentHealth() / opponent.getMaxHealth();
	}
	double getOpponentStrength() {
		return opponent.getCombatAttributes().getStrength() + opponent.getCurrentPokeshinto().getCombatAttributes().getStrength();
	}
	double getOpponentFocus() {
		return opponent.getCombatAttributes().getFocus() + opponent.getCurrentPokeshinto().getCombatAttributes().getFocus();
	}
	double getOpponentArmor() {
		return opponent.getCombatAttributes().getArmor() + opponent.getCurrentPokeshinto().getCombatAttributes().getArmor();
	}
	double getOpponentAgility() {
		return opponent.getCombatAttributes().getAgility() + opponent.getCurrentPokeshinto().getCombatAttributes().getAgility();
	}
	Dictionary<Integer> getAllOpponentElementResistance() {
		return opponent.getCurrentPokeshinto().getCombatAttributes().getElementResistance();
	}
	int getOpponentElementResistance(Element element) {
		
		int elementResistance = 0;
		
		try {
			opponent.getCurrentPokeshinto().getCombatAttributes().getElementResistance().getValueByKey(element.getId());
		} catch (KeyException e) {
			elementResistance = 0;
		}
		
		return elementResistance;
	}
	Action<String> getOpponentChoice() {
		return opponent.getDecision();
	}
	int getOpponentDamageDone() {
		return (int) (opponent.getPhysicalDamageDealt() + opponent.getMagicalDamageDealt());
	}
	Pokeshinto getOpponentPokeshinto() {
		return opponent.getCurrentPokeshinto();
	}
	boolean getOpponentSkillIsUsable(String id) {
		return opponent.getSkillIsUsable(this, id);
	}

}
