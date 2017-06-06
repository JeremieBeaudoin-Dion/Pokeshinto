package pokeshinto;

import combat.*;

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
		
		Pokeshinto pokeshinto;
        List<Skill> skills = new LinkedList<>();
        List<Passive> passives = new LinkedList<>();
		CombatAttributes combatAttributes;
		Dictionary<Integer> elementResistance = new Dictionary<>();
		
		if (id.equals("Kohadai")){
			skills.add(getSkill("Push Back"));
            skills.add(getSkill("Slice"));
            skills.add(getSkill("Parry"));
            skills.add(getSkill("Breathe In"));
            skills.add(getSkill("Quick Blade"));

			elementResistance.push("Slash", -2);
            elementResistance.push("Darkness", 2);
            elementResistance.push("Art", -1);
            elementResistance.push("Nature", 1);

			combatAttributes = new CombatAttributes(16, 8, 10, 13, elementResistance);
			pokeshinto = new Pokeshinto(id, skills, 4, 1, passives, combatAttributes);

		} else if (id.equals("Kurokage")){
            skills.add(getSkill("Bite"));
            skills.add(getSkill("Ember Blast"));

            elementResistance.push("Wind", 2);
            elementResistance.push("Light", 2);
            elementResistance.push("Darkness", -2);
            elementResistance.push("Pierce", 1);

			combatAttributes = new CombatAttributes(12, 12, 12, 8, elementResistance);
			pokeshinto = new Pokeshinto(id, skills, 2, 1, passives, combatAttributes);

		} else {
		    throw new ExceptionNotHandled("Pokeshinto " + id + " doesn't exists.");
        }
		
		return pokeshinto;
	}
	
	/**
	 * Returns a skill by its name
	 */
	public static Skill getSkill(String id){
		
		Skill skill = null;
		
		Condition condition = null;
		List<Status> status = new LinkedList<>();
        List<Damage> damage = new LinkedList<>();
        List<Trigger> trigger = new LinkedList<>();
		
		if(id.equals("Quick Blade")){
		    damage.add(new Damage(true, 16.0, "Strength", 0, "Opponent", new Element("Slash")));
			skill = new Skill(id, 0, new Element("Slash"), 0, 0, false, condition, damage, status, trigger, true);
		
		} else if (id.equals("Slice")){
            damage.add(new Damage(true, 20.0, "Strength", 0, "Opponent", new Element("Slash")));
			skill = new Skill(id, 0, new Element("Slash"), 0, 0, false, condition, damage, status, trigger, false);
		
		} else if (id.equals("Parry")){
		    status.add(new StatusProtection(1, "Physical Damage", true, 0, 0, null));
			skill = new Skill(id, 0, new Element("War"), 3, 0, false, condition, damage, status, trigger, false);
		
		} else if (id.equals("Push Back")){
            damage.add(new Damage(true, 10.0, "Strength", 0, "Opponent", new Element("Art")));

			Damage triggerDamage = new Damage(true, 20, "Strength", 0, "Opponent", new Element("Art"));
			Condition triggerCondition = new Condition(">=", "Damage Recieved", true, "CurrentHp", 0.2, true);
			trigger.add(new TriggeredDamage(0, 0, true, triggerCondition, "End Turn", true, triggerDamage));

			skill = new Skill(id, 0, new Element("Art"), 2, 0, false, condition, damage, status, trigger, false);
			
		} else if (id.equals("Breathe In")){
		    AttributeBuff buff = new AttributeBuff(true, "Strength", 0, "Focus", 20, true);
            trigger.add(new TriggeredBuff(0, 0, true, null, "Start Turn", true, buff));

            buff = new AttributeBuff(true, "Strength", 0, "Focus", 5, true);
            trigger.add(new TriggeredBuff(0, 1, true, null, "Start Turn", true, buff));

            skill = new Skill(id, 0, new Element("Art"), 3, 0, false, condition, damage, status, trigger, false);

		} else if (id.equals("Disarm")){
		} else if (id.equals("Decapitate")){
		
		} else if (id.equals("Bite")){
            damage = new LinkedList<>();
            damage.add(new Damage(true, 18.0, "Strength", 4, "Opponent", new Element("Nature")));

			skill = new Skill(id, 0, new Element("Nature"), 0, 0, false, condition, damage, status, trigger, false);
		
		} else if (id.equals("Ember Blast")){
            damage = new LinkedList<>();
            damage.add(new Damage(true, 20.0, "Focus", 0, "Opponent", new Element("Fire")));

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

    public static CombatAI getGenericAI(String id) {
        return new AISinglePokeshinto(id, getBasicCombatAttributes(), 100);
    }

}
