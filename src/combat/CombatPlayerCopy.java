package combat;
import java.util.Random;

import pokeshinto.Pokeshinto;

/**
 * Stores information for the Combat class. Is otherwise useless off combat
 * 
 * @author Jérémie Beaudoin-Dion
 *
 */
public class CombatPlayerCopy extends CombatAI {
	
	/**
	 * Constructor
	 * 
	 * @param id : the pokeshinto's id
	 * @param combatAttributes : the attributes of the person
	 */
	public CombatPlayerCopy(Pokeshinto[] allPokeshintos, Pokeshinto currentPokeshinto,
							CombatAttributes combatAttributes, double maxHealth, double health) {
		super(allPokeshintos, currentPokeshinto, combatAttributes, maxHealth, health, "Player");
	}
	
	/**
	 * I will not decide for myself.
	 */
	public void decide() {
		// do Nothing
	}

	/**
	 * Sends the action to the player
	 */
	public void decide(Action<Integer> action) {
		choice = action;
	}

}
