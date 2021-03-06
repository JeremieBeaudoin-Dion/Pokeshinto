package pokeshinto;

import combat.*;
import combat.combatActions.Passive;
import combat.combatActions.Skill;

import java.io.Serializable;
import java.util.*;

/**
 * A representation of a Pokeshinto in game. It has a level,
 * specific equiped spells and stuff.
 * 
 * @author Jérémie Beaudoin-Dion
 *
 */
public class Pokeshinto implements Serializable {

    public static int XP_PER_LEVEL = 20;
	
	// Basic Info
	private int level;
	private double allXp;
	private String id;
	private String description;

	private LevelProgression levelProgression;

	// Skills
	private List<Skill> allSkills;
	private List<Skill> equipedSkills;
	private int maxSkillNum;
	
	// Passives
	private List<Passive> allPassives;
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
	 * @param passive: the allPassives available for the shinto
	 * @param maxPassiveNum: the maximum number of equiped allPassives
	 */
	public Pokeshinto(String id, String description, List<Skill> allSkills, int maxSkillNum, int maxPassiveNum, List<Passive> passive, CombatAttributes combatAttributes, LevelProgression levelProgression) {
		this.id = id;
		this.description = description;
		this.allSkills = allSkills;
		this.maxSkillNum = maxSkillNum;
		this.allPassives = passive;
		this.maxPassiveNum = maxPassiveNum;
		this.level = 0;
        allXp = 0;
		this.combatAttributes = combatAttributes;
		this.levelProgression = levelProgression;
		
		equipAllSkillsRandomly();
		equipedPassives = new LinkedList<>(); // Remove this
		// equip all passives randomly  TODO: Make LEVELABLE


	}
	
	/**
	 * Second constructor for a defined level pokeshinto
	 * 
	 * @param level: the level of the shinto
	 * @param id: the name of the shinto
	 * @param allSkills: Skill owner skills all available for the shintomon
	 * @param maxSkillNum: The maximum number of attack skills
	 * @param passive: the allPassives available for the shinto
	 * @param maxPassiveNum: the maximum number of equiped allPassives
	 */
	public Pokeshinto(int level, String id, List<Skill> allSkills, int maxSkillNum, int maxPassiveNum, List<Passive> passive, CombatAttributes combatAttributes) {
		this.id = id;
		this.allSkills = allSkills;
		this.maxSkillNum = maxSkillNum;
		this.allPassives = passive;
		this.maxPassiveNum = maxPassiveNum;
		this.level = level;
        allXp = getXpByLevel(level);
		this.combatAttributes = combatAttributes;

        equipAllSkillsRandomly();
	}

	private double getXpByLevel(int currentlevel) {
	    switch (currentlevel) {
            case 0:
                return 0;
            case 1:
                return 100;
            case 2:
                return 250;
            case 3:
                return 450;
            case 4:
                return 700;
            case 5:
                return 1000;
            case 6:
                return 1350;
            case 7:
                return 1750;
            case 8:
                return 2200;
            default:
                return 2700;
        }
    }

    private int getLevelByXp(double xp) {
        if (xp < 100) {
            return 0;
        } else if (xp < 250) {
            return 1;
        } else if (xp < 450) {
            return 2;
        } else if (xp < 700) {
            return 3;
        } else if (xp < 1000) {
            return 4;
        } else if (xp < 1350) {
            return 5;
        } else if (xp < 1750) {
            return 6;
        } else if (xp < 2200) {
            return 7;
        } else if (xp < 2700) {
            return 8;
        } else {
            return 9;
        }
    }

    /**
     * @return how much the pokeshinto is close to leveling, in percentage
     */
    public double getLevelCompletionPercentage() {
	    return allXp / getXpByLevel(level + 1);
    }
	
	/**
	 * Equips skills
	 */
	private void equipAllSkillsRandomly() {
	    // Create a stack with all skills
        Stack<Skill> skillsShuffled = new Stack<>();

        skillsShuffled.addAll(allSkills);

        // Shuffle the stack and add it to the equiped skills
        Collections.shuffle(skillsShuffled);
		equipedSkills = new LinkedList<>();
        Skill currentSkill;

		while(equipedSkills.size() < maxSkillNum  && !skillsShuffled.isEmpty()) {
            currentSkill = skillsShuffled.pop();

            equipSkill(currentSkill);
        }
		
	}

    /**
     * Adds a skill to the equiped skills if Pokehinto meets the level requirements.
     */
	public void equipSkill(Skill skill) {
	    // If there is no space left for new skills
        if (equipedSkills.size() >= maxSkillNum) {
            return;
        }

        // Only equip skills that are at the right level
        if (level >= skill.getLevelRequirement()) {
            equipedSkills.add(skill);
        }
    }

    private void equipAllPassivesRandomly() {
        // Create a stack with all skills
        Stack<Passive> passivesShuffle = new Stack<>();

        passivesShuffle.addAll(allPassives);

        // Shuffle the stack and add it to the equiped skills
        Collections.shuffle(passivesShuffle);
        equipedPassives = new LinkedList<>();
        Passive currentPassive;

        while(equipedPassives.size() < maxPassiveNum  && !passivesShuffle.isEmpty()) {
            currentPassive = passivesShuffle.pop();

            equipPassive(currentPassive);
        }

    }

    public void equipPassive(Passive passive) {
        // If there is no space left for new skills
        if (equipedPassives.size() >= maxPassiveNum) {
            return;
        }

        // Only equip skills that are at the right level
        if (level >= passive.getLevelRequirement()) {
            equipedPassives.add(passive);
        }
    }

    /**
     * At the end of a fight, resets all skills cooldowns and buildups
     */
	public void resetAllSkills() {
        Iterator<Skill> iter = allSkills.iterator();
        Skill currentSkill;

        while(iter.hasNext()) {
            currentSkill = iter.next();

            currentSkill.resetCooldown();
            currentSkill.resetBuildup();
        }
	}

    /**
     * When you win a fight, you levelUp
     */
    public void levelUp(int xpGained) {
        allXp += xpGained;
        int newlevel = getLevelByXp(xpGained);

        if (newlevel != level) {
        	getnewLevel(newlevel);
		}
    }

    private void getnewLevel (int newLevel) {
        Stack<Integer> allLevelsToAdd = new Stack<>();

        while(level < newLevel) {
            level++;
            allLevelsToAdd.push(level);
        }

        while(!allLevelsToAdd.isEmpty()) {
            DictionaryElement whatToAdd = levelProgression.get(allLevelsToAdd.pop());

            handleLevelUp(whatToAdd.getKey(), whatToAdd.getValue());
        }

	}

	private void handleLevelUp(String key, int howMuch) {
        if (isElementResistance(key)) {
            combatAttributes.getElementResistance().push(key, howMuch);
            return;
        }

        switch (key) {
            case "Strenght":
                combatAttributes.addStrength(howMuch);
                break;

            case "Armor":
                combatAttributes.addStrength(howMuch);
                break;

            case "Focus":
                combatAttributes.addStrength(howMuch);
                break;

            case "Agility":
                combatAttributes.addStrength(howMuch);
                break;

            case "Skill":
                break;

            case "Passive":
                break;

            case "Skill Slot":
                maxSkillNum += howMuch;
                break;

            case "Passive Slot":
                maxPassiveNum += howMuch;
                break;

            default:
                throw new IllegalArgumentException("The levelup " + key + " is not handled.");
        }
    }

    private boolean isElementResistance(String id) {
        try{
            Element element = new Element(id);
            return true;

        } catch (IllegalArgumentException e) {
            return false;
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

        throw new IllegalArgumentException("The skill " + id + " could not be found in the pokeshinto's equiped skills.");
	}

	public Skill getSkill(int number) {
	    return equipedSkills.get(number);
    }

	public List<Skill> getEquipedSkills() {
		return equipedSkills;
	}

    public List<Passive> getEquipedPassives() {
        return equipedPassives;
    }

	public int getMaxSkillNum() {
		return maxSkillNum;
	}

	public List<Passive> getPassive() {
		return allPassives;
	}

	public CombatAttributes getCombatAttributes() {
		return combatAttributes;
	}

	public String getNextLevelBonusDescription() {
        if (level == 9) {
            return "Maxed";
        }

        String description;

        if (levelProgression.get(level + 1).getKey().equals("Skill") || levelProgression.get(level + 1).getKey().equals("Passive")) {
            description = "New " + levelProgression.get(level + 1).getKey();
        } else {
            description = levelProgression.get(level + 1).getKey();

            if (levelProgression.get(level + 1).getValue() > 0) {
                description += " +" + levelProgression.get(level + 1).getValue();
            } else {
                description += " -" + levelProgression.get(level + 1).getValue();
            }
        }

        return description;
    }

}
