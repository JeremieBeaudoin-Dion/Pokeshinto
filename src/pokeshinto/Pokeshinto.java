package pokeshinto;

import combat.CombatAttributes;
import combat.Passive;
import combat.Skill;

/**
 * A representation of a Pokeshinto in game. It has a level,
 * specific equiped spells and stuff.
 * 
 * @author Jérémie Beaudoin-Dion
 *
 */
public class Pokeshinto {
	
	// Basic Info
	private int level;
	private String id;
	
	// Skills
	private Skill[] allSkills;
	private Skill[] equipedSkills;
	private int maxSkillNum;
	
	// Passives
	private Passive[] passives;
	private Passive[] equipedPassives;
	private int maxPassiveNum;
	
	// Combat attributes
	private CombatAttributes combatAttributes;
	
	/**
	 * A basic constructor for a level 0 Shintomon
	 * 
	 * @param id: the name of the shinto
	 * @param allSkills: Skill type skills all available for the shintomon
	 * @param maxSkillNum: The maximum number of attack skills
	 * @param passive: the passives available for the shinto
	 * @param maxPassiveNum: the maximum number of equiped passives
	 */
	public Pokeshinto(String id, Skill[] allSkills, int maxSkillNum, int maxPassiveNum, Passive[] passive, CombatAttributes combatAttributes) {
		this.id = id;
		this.allSkills = allSkills;
		this.maxSkillNum = maxSkillNum;
		this.passives = passive;
		this.maxPassiveNum = maxPassiveNum;
		this.level = 0;
		this.combatAttributes = combatAttributes;
		
		equipAllSkills();
	}
	
	/**
	 * Second constructor for a defined level pokeshinto
	 * 
	 * @param level: the level of the shinto
	 * @param id: the name of the shinto
	 * @param allSkills: Skill type skills all available for the shintomon
	 * @param maxSkillNum: The maximum number of attack skills
	 * @param passive: the passives available for the shinto
	 * @param maxPassiveNum: the maximum number of equiped passives
	 */
	public Pokeshinto(int level, String id, Skill[] allSkills, int maxSkillNum, int maxPassiveNum, Passive[] passive, CombatAttributes combatAttributes) {
		this.id = id;
		this.allSkills = allSkills;
		this.maxSkillNum = maxSkillNum;
		this.passives = passive;
		this.maxPassiveNum = maxPassiveNum;
		this.level = 0;
		this.combatAttributes = combatAttributes;
		
		equipAllSkills();
	}
	
	/**
	 * Equips skills
	 */
	private void equipAllSkills() {
		equipedSkills = new Skill[maxSkillNum];
		
		for(int i=0; i<maxSkillNum; i++){
			equipedSkills[i] = allSkills[i];
		}
		
	}
	
	// Getters
	public int getMaxPassiveNum() {
		return maxPassiveNum;
	}
	
	public int getLevel() {
		return level;
	}

	public String getId() {
		return id;
	}

	public Skill getSkill(int id) {
		return allSkills[id];
	}

	public Skill[] getEquipedSkills() {
		return equipedSkills;
	}

	public int getMaxSkillNum() {
		return maxSkillNum;
	}

	public Passive[] getPassive() {
		return passives;
	}

	public CombatAttributes getCombatAttributes() {
		return combatAttributes;
	}
}
