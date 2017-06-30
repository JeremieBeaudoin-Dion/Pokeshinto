package combat.combatAI;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import combat.Combat;
import combat.CombatAI;
import combat.CombatAttributes;
import combat.InfoHandler;
import pokeshinto.Action;
import pokeshinto.Pokeshinto;


public class AISinglePokeshinto extends CombatAI {
	
	private Random generator;
	
	/**
	 * Constructor
	 * 
	 * @param id : the pokeshinto's id
	 * @param combatAttributes : the attributes of the person
	 */
	public AISinglePokeshinto(String id, CombatAttributes combatAttributes, double health) {
		super(new LinkedList<Pokeshinto>(), InfoHandler.getPokeshinto(id), combatAttributes, health, health, "Opponent");
		
		generator = new Random();
	}
	
	/**
	 * Ask the AI to decide what to do.
	 */
	public void decide(Combat combat) {
		int skillIndex;
		
		do {
			skillIndex = generator.nextInt(getCurrentPokeshinto().getMaxSkillNum());

		} while (!getSkillIsUsable(combat, getCurrentPokeshinto().getSkill(skillIndex).getId()));
		
		choice = new Action<>("Skill", getCurrentPokeshinto().getSkill(skillIndex).getId());
	}

	@Override
	public void setDecision(Action<String> action) {
		choice = action;
	}

	public void chooseFirstPokeshinto(List<Pokeshinto> opponentsPokeshinto) {
		// First pokeshinto is already equiped
	    allPokeshintos.add(currentPokeshinto);
    }

}
