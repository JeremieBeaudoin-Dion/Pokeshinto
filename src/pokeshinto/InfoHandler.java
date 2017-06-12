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
			skills.add(getSkill("Raise Shield"));
            skills.add(getSkill("Slice"));
            skills.add(getSkill("Parry"));
            skills.add(getSkill("Breathe In"));
            skills.add(getSkill("Quick Blade"));

			elementResistance.push("Slash", -2);
            elementResistance.push("Darkness", 2);
            elementResistance.push("Art", -1);
            elementResistance.push("Nature", 1);

			combatAttributes = new CombatAttributes(16, 8, 10, 13, elementResistance);
			pokeshinto = new Pokeshinto(id, "Ancient Swordmaster", skills, 4, 1, passives, combatAttributes);

		} else if (id.equals("Kurokage")){
            skills.add(getSkill("Bite"));
            skills.add(getSkill("Ember Blast"));

            elementResistance.push("Wind", 2);
            elementResistance.push("Light", 2);
            elementResistance.push("Darkness", -2);
            elementResistance.push("Pierce", 1);

			combatAttributes = new CombatAttributes(12, 12, 12, 8, elementResistance);
			pokeshinto = new Pokeshinto(id, "Black Dragon", skills, 2, 1, passives, combatAttributes);

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
		    status.add(new StatusProtection(1, "Damage", "Physical", true, 0, null, 0));

			skill = new Skill(id, 0, new Element("War"), 3, 0, false, condition, damage, status, trigger, false);
		
		} else if (id.equals("Push Back")){
            damage.add(new Damage(true, 10.0, "Strength", 0, "Opponent", new Element("Art")));

			Damage triggerDamage = new Damage(true, 20, "Strength", 0, "Opponent", new Element("Art"));
			Condition triggerCondition = new ConditionStat(">=", "Damage Recieved", true, "CurrentHp", 0.2, true);
			trigger.add(new TriggeredDamage(0, 0, true, triggerCondition, "End Turn", true, triggerDamage));

			skill = new Skill(id, 0, new Element("Art"), 2, 0, false, condition, damage, status, trigger, false);
			
		} else if (id.equals("Breathe In")){
		    AttributeBuff buff = new AttributeBuff(true, "Strength", 0, "Focus", 20, true);
            status.add(new StatusBuff(buff, true, 1, null, 1));

            buff = new AttributeBuff(true, "Strength", 0, "Focus", 5, true);
            status.add(new StatusBuff(buff,true, 2, null, 2));

            skill = new Skill(id, 0, new Element("Art"), 3, 0, false, condition, damage, status, trigger, false);

		} else if (id.equals("Disarm")){
			status.add(new StatusRestrict("Skill", "War", false, 0, null, 3));

            skill = new Skill(id, 4, new Element("Art"), 0, 2, true, condition, damage, status, trigger, false);

		} else if (id.equals("Decapitate")){
            damage.add(new Damage(true, 16.0, "Strength", 0, "Opponent", new Element("Slash")));

            Damage triggerDamage = new Damage(true, Integer.MAX_VALUE, "Strength", 0, "Opponent", new Element("Slash"));
            Condition triggerCondition = new ConditionStat("<=", "CurrentHp", false, "MaxHp", 0.08, false);
            trigger.add(new TriggeredDamage(0, 0, true, triggerCondition, "End Turn", true, triggerDamage));

            skill = new Skill(id, 8, new Element("Slash"), 0, 2, true, condition, damage, status, trigger, false);

        } else if (id.equals("Raise Shield")){
            status.add(new StatusProtection(0.9, "Damage", "Physical", true, 0, null, 0));
            status.add(new StatusProtection(0.75, "Damage", "Magical", true, 0, null, 0));

            Condition hasMeditated = new ConditionAction(true, new Action<>("Meditate", null));
            status.add(new StatusProtection(0.9, "Damage", "Physical", true, 1, hasMeditated, 1));
            status.add(new StatusProtection(0.75, "Damage", "Magical", true, 1, hasMeditated, 1));

            AttributeBuff flatBuffArmor = new AttributeBuff(true, "Armor", 4, null, 1, true);
            status.add(new StatusBuff(flatBuffArmor,true, 1, hasMeditated, 5));

            skill = new Skill(id, 0, new Element("War"), 2, 0, false, condition, damage, status, trigger, false);

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
                description = "Quick. Deal 16 physical damage.";
                break;

            case "Slice":
                description = "Deal 20 physical damage.";
                break;

            case "Parry":
                description = "Reduce all physical damage you would receive this turn to zero.";
                break;

            case "Push Back":
                description = "Deal 10 physical damage and an other 20 if you lose at least 20% of your current health.";
                break;

            case "Breathe In":
                description = "Gain 20 Strenght next turn, then 5 Strenght the turn after.";
                break;

            case "Disarm":
                description = "Give a restriction to your opponent that prevents from doing WAR skills.";
                break;

            case "Decapitate":
                description = "Deals SLASH damage and kills instantly the enemy if its HP is under 8%.";
                break;

            case "Raise Shield":
                description = "Reduce damage you recieve this turn. Next turn, if you meditate, do it again and gain 4 Armor.";
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
        return new AISinglePokeshinto(id, getBasicCombatAttributes(), 500);
    }

}
