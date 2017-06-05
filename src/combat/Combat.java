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
	void setPlayerDecision(Action<Integer> playerChoice) {
		player.setDecision(playerChoice);
	}
	
	/**
	 * Updates every frame returns
     *
     * @return true if the combat calculations occured
	 */
	public boolean update() {		
		if (player.getDecision() != null) {
			opponent.decide(this);

			opponent.doChoice(this);
			player.doChoice(this);

			return true;
			
		}
		return false;
	}
	
	/**
	 * Ends the turn and deals the damage
	 */
	void endTurn() {
		opponent.doDamage(this, player.getPhysicalDamageDealt(), player.getMagicalDamageDealt());
		player.doDamage(this, opponent.getPhysicalDamageDealt(), opponent.getMagicalDamageDealt());
	}
	
	/**
	 * Resets the choice for the next turn
	 * 
	 * @return the outcome of the combat
	 * 			"Death" => if the player or opponent has 0 HP
	 * 			"Captured" => if the enemy has been captured
	 */
	String newTurn(){
		if (opponent.getHealth() <= 0 || player.getHealth() <= 0){
			return "Death";
		} if (opponent.getCaptured()) {
			return "Captured";
		}
		
		// Set the player's choice back to null
		player.setDecision(null);
		
		// Set the damage to 0
		player.resetDamage();
		opponent.resetDamage();
		
		// Implement turn number of the pokeshinto
		player.implementPoketurn();
		opponent.implementPoketurn();
		turnNumber++;
		
		return null;
	}
	
	/**
	 * Adds a status to the player
	 * @param status: the Status to give to the player
	 */
	void addPlayerStatus(Status status) {
		player.addStatus(status);
	}

	/**
	 * Adds a status to the opponent
	 * @param status: the Status to give to the opponent
	 */
	void addOpponentStatus(Status status) {
		opponent.addStatus(status);
	}
	
	/**
	 * @param action: the action to make
	 * @return if the action is doable
	 */
	boolean getPlayerActionIsDoable(Action<Integer> action) {
		 if (action.getKey().equals("Skill")){
             if (player.getCurrentPokeshinto() == null) {
                 return false;
             }
			 if (!player.getSkillIsUsable(this, action.getValue())){
				 return false;
			 }

		 } else if (action.getKey().equals("Switch")) {
		     if (player.allPokeshintos.size() <= 1) {
		         return false;
             }

         } else if (action.getKey().equals("Attack")) {
		     if (player.getCurrentPokeshinto() == null) {
		         return false;
             }

         } else if (action.getKey().equals("Items")) {
             return false;
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
	Action<Integer> getPlayerChoice() {
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
	boolean getPlayerSkillIsUsable(int index) {
		return player.getSkillIsUsable(this, index);
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
	Action<Integer> getOpponentChoice() {
		return opponent.getDecision();
	}
	int getOpponentDamageDone() {
		return (int) (opponent.getPhysicalDamageDealt() + opponent.getMagicalDamageDealt());
	}
	Pokeshinto getOpponentPokeshinto() {
		return opponent.getCurrentPokeshinto();
	}
	boolean getOpponentSkillIsUsable(int index) {
		return opponent.getSkillIsUsable(this, index);
	}

}
