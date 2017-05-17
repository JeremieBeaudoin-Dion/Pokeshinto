package combat;

public abstract class StatOriented {
	
	/**
	 * Finds the owner of the desired stat then sends it to the right method
	 * 
	 * @param combat: the combat istance from where to find the stat
	 * @param owner: the Player or the opponent
	 * @param stat: the Name of the stat to find
	 * @return the double value of the desired stat
	 */
	protected double findStat(Combat combat, String owner, String stat, boolean isMe) {
		
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
		if (stat.equals("CurrentHp")){
			return combat.getPlayerCurrentHp();
		} else if (stat.equals("MaxHp")){
			return combat.getPlayerMaxHp();
		} else if (stat.equals("MissingHp")){
			return combat.getPlayerMissingHp();
		} else if (stat.equals("Strength")){
			return combat.getPlayerStrength();
		} else if (stat.equals("Focus")){
			return combat.getPlayerFocus();
		} else if (stat.equals("Armor")){
			return combat.getPlayerArmor();
		} else if (stat.equals("Agility")){
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
		if (stat.equals("CurrentHp")){
			return combat.getOpponentCurrentHp();
		} else if (stat.equals("MaxHp")){
			return combat.getOpponentMaxHp();
		} else if (stat.equals("MissingHp")){
			return combat.getOpponentMissingHp();
		} else if (stat.equals("Strength")){
			return combat.getOpponentStrength();
		} else if (stat.equals("Focus")){
			return combat.getOpponentFocus();
		} else if (stat.equals("Armor")){
			return combat.getOpponentArmor();
		} else if (stat.equals("Agility")){
			return combat.getOpponentAgility();
		} 
		
		System.out.println("FindStat error: the Opponent stat: " + stat + " is not handled.");
		return 0.0;
	}

}
