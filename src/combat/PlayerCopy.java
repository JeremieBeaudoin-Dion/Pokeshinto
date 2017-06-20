package combat;

import pokeshinto.Action;
import pokeshinto.Pokeshinto;

import java.util.List;

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
	 * @param allPokeshintos: all the pokeshintos
	 * @param currentPokeshinto: the current pokeshinto of the player
	 * @param combatAttributes: the Attributes of the player in combat
	 * @param maxHealth: the maximum possible health
	 * @param health: the current health
	 */
	public PlayerCopy(List<Pokeshinto> allPokeshintos, Pokeshinto currentPokeshinto,
					  CombatAttributes combatAttributes, double maxHealth, double health) {
		super(allPokeshintos, currentPokeshinto, combatAttributes, maxHealth, health, "Player");
	}
	
	/**
	 * I will not decide for myself.
	 */
	public void decide(Combat combat) {
		// Do Nothing
	}

	/**
	 * Sends the action to the player
	 */
	public void setDecision(Action<String> action) {
		choice = action;
	}

	public void chooseFirstPokeshinto() {
		// Do nothing
	}

}
