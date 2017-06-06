package pokeshinto;

import combat.AISinglePokeshinto;
import combat.CombatAI;
import combat.CombatAttributes;
import combat.Condition;
import combat.Damage;
import combat.Element;
import combat.Skill;
import combat.Status;
import combat.StatusProtection;
import combat.Trigger;
import combat.Dictionary;

import java.util.LinkedList;
import java.util.List;

/**
 * This class contains all the basic FINAL game information.
 * 
 * @author Jérémie Beaudoin-Dion
 *
 */
public final class InfoHandler {

	/**
	 * Returns a pokeshinto by its name
	 * 
	 * @param id
	 * @return
	 */
	public static Pokeshinto getPokeshinto(String id){
		
		Pokeshinto pokeshinto = null;
		Skill[] skills = null;
		CombatAttributes combatAttributes = null;
		Dictionary<Integer> elementResistance = null;
		
		if (id.equals("Kohadai")){
			skills = new Skill[]{getSkill("Push Back"), getSkill("Slice"), getSkill("Parry"), getSkill("Breathe In")};
			elementResistance = new Dictionary<Integer>(new String[]{"Slash", "Darkness", "Art", "Nature"}, new Integer[]{-2, 2, -1, 1});
			combatAttributes = new CombatAttributes(16, 8, 10, 13, elementResistance);
			pokeshinto = new Pokeshinto(id, skills, 4, 1, null, combatAttributes);
		} else if (id.equals("Kurokage")){
			skills = new Skill[]{getSkill("Bite"), getSkill("Ember Blast")};
			elementResistance = new Dictionary<Integer>(new String[]{"Wind", "Light", "Darkness", "Pierce"}, new Integer[]{2, 2, -2, 1});
			combatAttributes = new CombatAttributes(12, 12, 12, 8, elementResistance);
			pokeshinto = new Pokeshinto(id, skills, 2, 1, null, combatAttributes);
		}
		
		return pokeshinto;
	}
	
	/**
	 * Returns a skill by its name
	 */
	public static Skill getSkill(String id){
		
		Skill skill = null;
		
		Condition condition = null;
		Status[] status = null;
		Damage[] damage = null;
		Trigger[] trigger = null;
		
		if(id.equals("Quick Blade")){
			damage = new Damage[]{ new Damage(true, 16.0, "Strength", 0, "Opponent", new Element("Slash"))};
			skill = new Skill(id, 0, new Element("Slash"), 0, 0, false, condition, damage, status, trigger, true);
		
		} else if (id.equals("Slice")){
			damage = new Damage[]{ new Damage(true, 20.0, "Strength", 0, "Opponent", new Element("Slash"))};
			skill = new Skill(id, 0, new Element("Slash"), 0, 0, false, condition, damage, status, trigger, false);
		
		} else if (id.equals("Parry")){
			status = new Status[]{new StatusProtection(1, "Physical Damage", true, 0, 0, null)};
			skill = new Skill(id, 0, new Element("War"), 3, 0, false, condition, damage, status, trigger, false);
		
		} else if (id.equals("Push Back")){
			damage = new Damage[]{ new Damage(true, 10.0, "Strength", 0, "Opponent", new Element("Art"))};
			// trigger = new trigger();
			skill = new Skill(id, 0, new Element("Art"), 2, 0, false, condition, damage, status, trigger, false);
			
		} else if (id.equals("Breathe In")){
		} else if (id.equals("Disarm")){
		} else if (id.equals("Decapitate")){
		
		} else if (id.equals("Bite")){
			damage = new Damage[]{ new Damage(true, 18.0, "Strength", 4, "Opponent", new Element("Nature"))};
			skill = new Skill(id, 0, new Element("Nature"), 0, 0, false, condition, damage, status, trigger, false);
		
		} else if (id.equals("Ember Blast")){
			damage = new Damage[]{ new Damage(true, 20.0, "Focus", 0, "Opponent", new Element("Fire"))};
			// TODO: add trigger
			skill = new Skill(id, 0, new Element("Fire"), 1, 0, false, condition, damage, status, trigger, false);
		}
		
		return skill;
	}

    /**
     * Returns a quick description of a Menu action in combat
     */
    public static String getCombatInformation(String id) {
        String description = null;

        switch (id) {
            case "Skill":
                description = "Use one of your pokeshinto's skill against your opponent.";
                break;

            case "Meditate":
                description = "Regain your focus and capture enemy pokeshinto if possible.";
                break;

            case "Switch":
                description = "Switch your current pokeshinto with another.";
                break;

            case "Items":
                description = "Use an item in combat.";
                break;
        }

        if (description == null) {
            description = getSkillInformation(id);
        }

        return description;
    }
	
	/**
	 * Returns a quick description of a skill in a String
	 */
	private static String getSkillInformation(String id) {
        String description = "";

        switch (id) {
            case "Quick Blade":
                description = "A swift SLASH attack of 16 damage";
                break;

            case "Slice":
                description = "A basic SLASH attack of 20 damage";
                break;

            case "Parry":
                description = "A WAR skill that reduces all physical damage recieved this turn";
                break;

            case "Push Back":
                description = "An ART skill that deals 10 damage. If you lose at least 20% of your current health, " +
                        "deal another 20 damage and Stun-2 your opponent.";
                break;

            case "Breathe In":
                description = "An ART skill that gives 20 Strenght next turn, then 5 Strenght the turn after.";
                break;



        }
		
		return description;
	}
	
	/**
	 * Return a standard set of combat attributes
	 */
	public static CombatAttributes getBasicCombatAttributes() {
		return new CombatAttributes(10, 10, 10, 10, null);
	}
	
	/**
	 * Returns a menu according to its function
	 */
	public static Menu getCombatMenu(String id) {
		
		Menu menu = null;
		List<String> rootMenu = new LinkedList<>();
		
		if (id.equals("Start Combat")){
			rootMenu.add("Skill");
			rootMenu.add("Switch");
			rootMenu.add("Items");
			rootMenu.add("Meditate");
			// String[] rootMenu = new String[]{"Skill", "Switch", "Items", "Meditate"};
			menu = new Menu("Root", rootMenu, "Let the fight begin!");
		} else if (id.equals("New Turn")){
			rootMenu.add("Skill");
			rootMenu.add("Switch");
			rootMenu.add("Items");
			rootMenu.add("Meditate");
			// String[] rootMenu = new String[]{"Skill", "Switch", "Items", "Meditate"};
			menu = new Menu("Root", rootMenu, null);
		}
		
		return menu;
		
	}

	public static Menu getWorldMenu(String id) {
	    Menu menu = null;

	    /*
	    if (id.equals("Root")) {
	        String[] rootMenu = new String[]{"Player", "Pokeshinto", "Equipment", "Save Game", "Quit"};
	        menu = new Menu("Root", rootMenu, null);
        }*/

	    return menu;
    }
	
	public static CombatAI getGenericAI() {
		return new AISinglePokeshinto("Kohadai", getBasicCombatAttributes(), 100);
	}

}
