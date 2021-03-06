package combat.combatActions;

import combat.Combat;
import combat.Element;

/**
 * A damage has a total value, a Type and an Element
 *
 * @author Jérémie Beaudoin-Dion
 */
public interface Damage {
	
	// Getters
	Element getElement();
	String getDamageType();
    boolean dealerIsMe();
	boolean affectsMe();
	boolean isInstant();
	void setInstant(boolean value);
	
	/**
	 * Return the total value of the damage, considering defense and everything
	 * 
	 * @param combat: the current combat state of the game
	 * @param damageOwner: "Player" if player is owner of the poke who does the combat
	 * @return double damage value to reduce from target's health
	 */
	double getTotalDamage(Combat combat, String damageOwner);
}
