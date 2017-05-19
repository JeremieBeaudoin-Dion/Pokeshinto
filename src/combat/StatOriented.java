package combat;

/**
 * A stat oriented can be a skill or a damage that needs to find one or more
 * attributes from the combat AIs.
 *
 * @author Jérémie Beaudoin-Dion
 */
abstract class StatOriented {
	
	/**
	 * Finds the owner of the desired stat then sends it to the right method
	 * 
	 * @param combat: the combat istance from where to find the stat
	 * @param owner: the Player or the opponent
	 * @param stat: the Name of the stat to find
	 * @return the double value of the desired stat
	 */
	double findStat(Combat combat, String owner, String stat, boolean isMe) {
		
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
		} 
		
		System.out.println("FindStat error: the Player stat: " + stat + " is not handled.");
		return 0.0;
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
		} 
		
		System.out.println("FindStat error: the Opponent stat: " + stat + " is not handled.");
		return 0.0;
	}

}
