package combat;
import java.util.LinkedList;
import java.util.Random;

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
			skillIndex = generator.nextInt(currentPokeshinto.getMaxSkillNum());

		} while (!getSkillIsUsable(combat, currentPokeshinto.getSkill(skillIndex).getId()));
		
		choice = new Action<>("Skill", currentPokeshinto.getSkill(skillIndex).getId());
	}

	@Override
	public void setDecision(Action<String> action) {
		choice = action;
	}

	public void chooseFirstPokeshinto() {
	    // Do nothing
    }

}
