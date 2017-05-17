package combat;
import java.util.Random;

import pokeshinto.Action;
import pokeshinto.InfoHandler;
import pokeshinto.Pokeshinto;


public class AISinglePokeshinto extends CombatAI {
	
	protected Random generator;
	
	/**
	 * Constructor
	 * 
	 * @param id : the pokeshinto's id
	 * @param combatAttributes : the attributes of the person
	 */
	public AISinglePokeshinto(String id, CombatAttributes combatAttributes, double health) {
		super(new Pokeshinto[]{InfoHandler.getPokeshinto(id)}, 
				InfoHandler.getPokeshinto(id), combatAttributes, health, health, "Opponent");
		
		generator = new Random();
	}
	
	/**
	 * Ask the AI to decide what to do.
	 */
	public void decide(Combat combat) {
		int skillIndex;
		int tried = 2;
		
		do {
			// skillIndex = generator.nextInt(currentPokeshinto.getMaxSkillNum());
			skillIndex = tried;
			tried--;
		} while (!getSkillIsUsable(combat, skillIndex));
		
		choice = new Action<Integer>("Skill", skillIndex);
	}

	@Override
	public void setDecision(Action<Integer> action) {
		choice = action;
	}

}
