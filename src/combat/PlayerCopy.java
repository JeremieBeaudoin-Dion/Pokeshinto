package combat;

import pokeshinto.Action;
import pokeshinto.Pokeshinto;

/**
 * Stores information of the player for the Combat class. It acts as a CombatAI
 * but doesn't take any decision by itself. 
 * 
 * @author Jérémie Beaudoin-Dion
 *
 */
public class PlayerCopy extends CombatAI {
	
	/**
	 * Constructor
	 * 
	 * @param id : the pokeshinto's id
	 * @param combatAttributes : the attributes of the person
	 */
	public PlayerCopy(Pokeshinto[] allPokeshintos, Pokeshinto currentPokeshinto,
							CombatAttributes combatAttributes, double maxHealth, double health) {
		super(allPokeshintos, currentPokeshinto, combatAttributes, maxHealth, health, "Player");
	}
	
	/**
	 * I will not decide for myself.
	 */
	public void decide(Combat combat) {
		// do Nothing
	}

	/**
	 * Sends the action to the player
	 */
	public void setDecision(Action<Integer> action) {		
		choice = action;
	}

}
