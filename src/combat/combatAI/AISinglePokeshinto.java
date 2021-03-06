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
import world.items.Item;
import world.items.ItemBag;

public class AISinglePokeshinto extends CombatAI implements OpponentAI{
	
	private Random generator;

	private int xpAmount;
	
	/**
	 * Constructor without items
	 */
	public AISinglePokeshinto(String id, CombatAttributes combatAttributes, double health) {
		super(new LinkedList<Pokeshinto>(), InfoHandler.getPokeshinto(id), new ItemBag(), combatAttributes, health, health, "Opponent");

		//TODO: implement pokeshintolevel
        xpAmount = 10;

		generator = new Random();
	}

    /**
     * Constructor with items
     */
    public AISinglePokeshinto(String id, ItemBag allItems, CombatAttributes combatAttributes, double health) {
        super(new LinkedList<Pokeshinto>(), InfoHandler.getPokeshinto(id), allItems, combatAttributes, health, health, "Opponent");

        //TODO: implement pokeshintolevel
        xpAmount = 10;

        generator = new Random();
    }
	
	/**
	 * Ask the AI to decide what to do.
	 */
    @Override
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

    @Override
	public void chooseFirstPokeshinto(List<Pokeshinto> opponentsPokeshinto) {
		// First pokeshinto is already equiped
	    allPokeshintos.add(currentPokeshinto);
    }

    @Override
    public int getXp() {
        return xpAmount;
    }
}
