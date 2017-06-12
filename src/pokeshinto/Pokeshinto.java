package pokeshinto;

import combat.CombatAttributes;
import combat.ExceptionNotHandled;
import combat.Passive;
import combat.Skill;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

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
	private String description;
	
	// Skills
	private List<Skill> allSkills;
	private List<Skill> equipedSkills;
	private int maxSkillNum;
	
	// Passives
	private List<Passive> passives;
	private List<Passive> equipedPassives;
	private int maxPassiveNum;
	
	// Combat attributes
	private CombatAttributes combatAttributes;
	
	/**
	 * A basic constructor for a level 0 Shintomon
	 * 
	 * @param id: the name of the shinto
     * @param description: the english name of the shinto
	 * @param allSkills: Skill owner skills all available for the shintomon
	 * @param maxSkillNum: The maximum number of attack skills
	 * @param passive: the passives available for the shinto
	 * @param maxPassiveNum: the maximum number of equiped passives
	 */
	public Pokeshinto(String id, String description, List<Skill> allSkills, int maxSkillNum, int maxPassiveNum, List<Passive> passive, CombatAttributes combatAttributes) {
		this.id = id;
		this.description = description;
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
	 * @param allSkills: Skill owner skills all available for the shintomon
	 * @param maxSkillNum: The maximum number of attack skills
	 * @param passive: the passives available for the shinto
	 * @param maxPassiveNum: the maximum number of equiped passives
	 */
	public Pokeshinto(int level, String id, List<Skill> allSkills, int maxSkillNum, int maxPassiveNum, List<Passive> passive, CombatAttributes combatAttributes) {
		this.id = id;
		this.allSkills = allSkills;
		this.maxSkillNum = maxSkillNum;
		this.passives = passive;
		this.maxPassiveNum = maxPassiveNum;
		this.level = level;
		this.combatAttributes = combatAttributes;
		
		equipAllSkills();
	}
	
	/**
	 * Equips skills
	 */
	private void equipAllSkills() {
		equipedSkills = new LinkedList<>();;
		
		for(int i=0; i<maxSkillNum; i++){
			equipedSkills.add(allSkills.get(i));
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

	public String getDescription() {
	    return description;
    }

	public Skill getSkill(String id) {

        Iterator<Skill> iter = allSkills.iterator();
        Skill skill;

        while(iter.hasNext()) {
            skill = iter.next();

            if (skill.getId().equals(id)) {
                return skill;
            }
        }

        throw new ExceptionNotHandled("The skill " + id + " could not be found in the pokeshinto's equiped skills.");
	}

	public Skill getSkill(int number) {
	    return equipedSkills.get(number);
    }

	public List<Skill> getEquipedSkills() {
		return equipedSkills;
	}

	public int getMaxSkillNum() {
		return maxSkillNum;
	}

	public List<Passive> getPassive() {
		return passives;
	}

	public CombatAttributes getCombatAttributes() {
		return combatAttributes;
	}
}
