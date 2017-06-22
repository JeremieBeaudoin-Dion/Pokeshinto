package combatActions;

import combat.Combat;

/**
 * A stat oriented can be a skill or a damage that needs to find one or more
 * attributes from the combat AIs.
 *
 * @author Jérémie Beaudoin-Dion
 */
abstract class StatOriented {

    /**
     * Returns the value of the resistance from a particular element of the owner
     */
	double findResistance(Combat combat, String owner, String element, boolean isMe) {
        if (owner.equals("Player") && isMe || owner.equals("Opponent") && !isMe) {
            return getResistanceValueFromInt(combat.getPlayerResistanceFrom(element));
        } else {
            return getResistanceValueFromInt(combat.getOpponentResistanceFrom(element));
        }
    }

    /**
     * Transform the usual int value (0, 1, 2, -1 or -2) that represent the resistance of an element
     * into the real value that has to be multiplied by the amount dealt by the Damage.
     *
     * Values:
     *      -2 : 0.75
     *      -1 : 0.9
     *      0 : 1.0
     *      1 : 1.1
     *      2 : 1.25
     */
    private double getResistanceValueFromInt(int value) {
	    double realAmount;

	    if (value == -2) {
            realAmount = -0.25;

        } else if (value == -1) {
            realAmount = -0.1;

        } else if (value == 1) {
            realAmount = 0.1;

        } else if (value == 2) {
            realAmount = 0.25;

        } else {
	        realAmount = 0;
        }

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
			return findPlayerStat(combat, stat);
		} else {
			return findOpponentStat(combat, stat);
		}
		
	}
	
	/**
	 * Finds the desired Stat from the Player
	 * 
	 * @param combat: instance of Combat
	 * @param stat: the Stat to find
	 * @return the double value of the stat
	 */
	private double findPlayerStat(Combat combat, String stat) {
		switch (stat) {
			case "CurrentHp":
				return combat.getPlayerCurrentHp();
			case "MaxHp":
				return combat.getPlayerMaxHp();
			case "MissingHp":
				return combat.getPlayerMissingHp();
			case "Strength":
				return combat.getPlayerStrength();
			case "Focus":
				return combat.getPlayerFocus();
			case "Armor":
				return combat.getPlayerArmor();
			case "Agility":
				return combat.getPlayerAgility();
            case "Damage Recieved":
                return combat.getAllPlayerDamageRecieved().getAllNonStatus();
            case "Damage Done":
                return combat.getAllOpponentDamageRecieved().getAllNonStatus();
            case "Poke Turn":
                return combat.getPlayerPokeshintoTurn();
		} 
		
		throw new IllegalArgumentException("FindStat error: the Player stat: " + stat + " is not handled.");
	}
	
	/**
	 * Finds the desired Stat from the Opponent
	 * 
	 * @param combat: instance of Combat
	 * @param stat: the Stat to find
	 * @return the double value of the stat
	 */
	private double findOpponentStat(Combat combat, String stat) {
		switch (stat) {
			case "CurrentHp":
				return combat.getOpponentCurrentHp();
			case "MaxHp":
				return combat.getOpponentMaxHp();
			case "MissingHp":
				return combat.getOpponentMissingHp();
			case "Strength":
				return combat.getOpponentStrength();
			case "Focus":
				return combat.getOpponentFocus();
			case "Armor":
				return combat.getOpponentArmor();
			case "Agility":
				return combat.getOpponentAgility();
            case "Damage Recieved":
                return combat.getAllOpponentDamageRecieved().getAllNonStatus();
            case "Damage Done":
                return combat.getAllPlayerDamageRecieved().getAllNonStatus();
            case "Poke Turn":
                return combat.getOpponentPokeshintoTurn();
		}

		throw new IllegalArgumentException("FindStat error: the Opponent stat: " + stat + " is not handled.");
	}

}
