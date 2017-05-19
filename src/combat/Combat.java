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
	public void setPlayerDecision(Action<Integer> playerChoice) {
		player.setDecision(playerChoice);
	}
	
	/**
	 * Updates every frame returns true if the combat calculations occured
	 */
	public boolean update() {		
		if (player.getDecision() != null) {
			opponent.decide(this);
			
			// Do stuff
			opponent.doChoice(this);
			player.doChoice(this);
			
			return true;
			
		}
		return false;
	}
	
	/**
	 * Ends the turn and deals the damage
	 */
	public void endTurn() {
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
	public String newTurn(){
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
	public void addPlayerStatus(Status status) {
		player.addStatus(status);
	}
	/**
	 * Adds a status to the opponent
	 * @param status: the Status to give to the opponent
	 */
	public void addOpponentStatus(Status status) {
		opponent.addStatus(status);
	}
	
	
	/**
	 * Returns if an action from the player is doable
	 * 
	 * @param action: the action to make
	 * @return if the action is doable
	 */
	public boolean getPlayerActionIsDoable(Action<Integer> action) {
		 if (action.getKey().equals("Skill")){
			 if (!player.getSkillIsUsable(this, action.getValue())){
				 return false;
			 }
		 }
		
		return true;
	}
	
	/**
	 * The getters here are very long and there are many, but they ensure that
	 * the player and the opponent don't get tampered with.
	 * 
	 * They are also very usefull for the accessibility of the stats for the 
	 * Skill and Damage classes.
	 *
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
		return player.getCombatAttributes().getStrength() + player.getCurrentPokeshinto().getCombatAttributes().getStrength();
	}
	public double getPlayerFocus() {
		return player.getCombatAttributes().getFocus() + player.getCurrentPokeshinto().getCombatAttributes().getFocus();
	}
	public double getPlayerArmor() {
		return player.getCombatAttributes().getArmor() + player.getCurrentPokeshinto().getCombatAttributes().getArmor();
	}
	public double getPlayerAgility() {
		return player.getCombatAttributes().getAgility() + player.getCurrentPokeshinto().getCombatAttributes().getAgility();
	}
	public Dictionary<Integer> getAllPlayerElementResistance() {
		return player.getCurrentPokeshinto().getCombatAttributes().getElementResistance();
	}
	public int getPlayerElementResistance(Element element) {
		
		int elementResistance = 0;
		
		try {
			player.getCurrentPokeshinto().getCombatAttributes().getElementResistance().getValueByKey(element.getId());
		} catch (KeyException e) {
			elementResistance = 0;
		}
		
		return elementResistance;
	}
	public Action<Integer> getPlayerChoice() {
		return player.getDecision();
	}
	public int getPlayerDamageDone() {
		return (int) (player.getPhysicalDamageDealt() + player.getMagicalDamageDealt());
	}
	public Pokeshinto getPlayerPokeshinto() {
		return player.getCurrentPokeshinto();
	}
	public boolean getPlayerSkillIsUsable(int index) {
		return player.getSkillIsUsable(this, index);
	}
	
	// Getters Opponent
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
	public Dictionary<Integer> getAllOpponentElementResistance() {
		return opponent.getCurrentPokeshinto().getCombatAttributes().getElementResistance();
	}
	public int getOpponentElementResistance(Element element) {
		
		int elementResistance = 0;
		
		try {
			opponent.getCurrentPokeshinto().getCombatAttributes().getElementResistance().getValueByKey(element.getId());
		} catch (KeyException e) {
			elementResistance = 0;
		}
		
		return elementResistance;
	}
	public Action<Integer> getOpponentChoice() {
		return opponent.getDecision();
	}
	public int getOpponentDamageDone() {
		return (int) (opponent.getPhysicalDamageDealt() + opponent.getMagicalDamageDealt());
	}
	public Pokeshinto getOpponentPokeshinto() {
		return opponent.getCurrentPokeshinto();
	}
	public boolean getOpponentSkillIsUsable(int index) {
		return opponent.getSkillIsUsable(this, index);
	}

}
