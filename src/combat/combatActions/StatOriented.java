package combat.combatActions;

import combat.Combat;
import combat.CombatAI;

import java.io.Serializable;

/**
 * A stat oriented can be a skill or a damage that needs to find one or more
 * attributes from a CombatAI
 *
 * @author Jérémie Beaudoin-Dion
 */
abstract class StatOriented implements Serializable{

    /**
     * Returns the value of the resistance from a particular element of the owner
     */
	double findResistance(Combat combat, String owner, String element, boolean isMe) {
        if (owner.equals("Player") && isMe || owner.equals("Opponent") && !isMe) {
            return getResistanceValueFromInt(combat.getPlayer().getResistanceFrom(element));
        } else {
            return getResistanceValueFromInt(combat.getOpponent().getResistanceFrom(element));
        }
    }

    /**
     * Transform the usual int value (0, 1, 2, -1 or -2) that represent the resistance of an element
     * into the real value that has to be multiplied by the amount dealt by the Damage.
     *
     * Values:
     *      -2 : 0.5
     *      -1 : 0.75
     *      0 : 1.0
     *      1 : 1.25
     *      2 : 1.5
     */
    private double getResistanceValueFromInt(int value) {
	    double realAmount = value/100;

        return 1 + realAmount;
    }
	
	/**
	 * Finds the owner of the desired stat then sends it to the right method
	 * 
	 * @param combat: the combat istance from where to find the stat
	 * @param owner: the Player or the opponent
	 * @param stat: the Name of the stat to find
     * @param isMe: if the stat should be searched on the owner
	 * @return the double value of the desired stat
	 */
	double findStat(Combat combat, String owner, String stat, boolean isMe) {

		if (stat == null) {
			return 0.0;
		}
		
		if (owner.equals("Player") && isMe || owner.equals("Opponent") && !isMe) {
			return findStatFromAI(combat.getPlayer(), stat);
		} else {
            return findStatFromAI(combat.getOpponent(), stat);
		}
		
	}

    /**
     * Finds the desired Stat from an AI
     *
     * @param ai: instance of the desired CombatAI
     * @param stat: the Stat to find
     * @return the double value of the stat
     */
	private double findStatFromAI(CombatAI ai, String stat) {
		switch (stat) {
			case "CurrentHp":
				return ai.getCurrentHealth();
			case "MaxHp":
				return ai.getMaxHealth();
			case "MissingHp":
				return ai.getMissingHealth();
			case "Strength":
				return ai.getStrength();
			case "Focus":
				return ai.getFocus();
			case "Armor":
				return ai.getArmor();
			case "Agility":
				return ai.getAgility();
			case "Damage Done":
				return ai.getAllDamageDone().getAllNonStatus();
			case "Poke Turn":
				return ai.getCurrentPoketurn();
		}

		throw new IllegalArgumentException("FindStat error: the Player stat: " + stat + " is not handled.");
	}

}
