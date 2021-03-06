package combat;

import combat.combatAI.AISinglePokeshinto;
import combat.combatActions.*;
import pokeshinto.Action;
import duringMenus.Menu;
import pokeshinto.LevelProgression;
import pokeshinto.Pokeshinto;

import java.util.LinkedList;
import java.util.List;

/**
 * This class contains all the basic game information.
 * 
 * @author Jérémie Beaudoin-Dion
 */
public final class InfoHandler {

	/**
	 * Returns a pokeshinto by its name
	 */
	public static Pokeshinto getPokeshinto(String id){
		
		Pokeshinto pokeshinto;
        List<Skill> skills = new LinkedList<>();
        List<Passive> passives = new LinkedList<>();
		CombatAttributes combatAttributes;
		Dictionary elementResistance = new Dictionary();
        LevelProgression levelProgression = new LevelProgression();

        if (id.equals("Keyori")){
            skills.add(getSkill("Slap"));
            skills.add(getSkill("Dumb Luck"));
            skills.add(getSkill("Crapskill"));
            skills.add(getSkill("Dickskill"));

            elementResistance.push("Dark", 50);

            combatAttributes = new CombatAttributes(10, 10, 10, 10, elementResistance);
            pokeshinto = new Pokeshinto(id, "N00b f**k", skills, 4, 1, passives, combatAttributes, levelProgression);

        } else if (id.equals("Saruken")) {

            skills.add(getSkill("Punch"));
            skills.add(getSkill("Smash"));
            skills.add(getSkill("Bite"));
            skills.add(getSkill("Head Butt"));
            skills.add(getSkill("Spinning Strike"));
            skills.add(getSkill("Kiai Shout"));
            skills.add(getSkill("Frenzy"));

            elementResistance.push("Slash", 25);
            elementResistance.push("Pierce", 25);
            elementResistance.push("Nature", -25);
            elementResistance.push("Ice", -25);

            levelProgression.add(1, "Strenght", 2);
            levelProgression.add(2, "Balance", -25);
            levelProgression.add(3, "Skill", 1);
            levelProgression.add(4, "Passive", 1);
            levelProgression.add(5, "Skill Slot", 1);
            levelProgression.add(6, "Skill", 1);
            levelProgression.add(7, "Armor", 2);
            levelProgression.add(8, "Passive", 1);
            levelProgression.add(9, "Skill", 1);

            combatAttributes = new CombatAttributes(13, 3, 6, 18, elementResistance);
            pokeshinto = new Pokeshinto(id, "Monkey Warrior", skills, 3, 1, passives, combatAttributes, levelProgression);


        } else if (id.equals("Furuiki")) {

            skills.add(getSkill("Sickening Spores"));
            skills.add(getSkill("Weakening Spores"));
            skills.add(getSkill("Grow"));
            skills.add(getSkill("Drain Blood"));
            skills.add(getSkill("Dispell"));

            elementResistance.push("Art", 25);
            elementResistance.push("Fire", 50);
            elementResistance.push("Crush", -50);
            elementResistance.push("Pierce", -50);

            levelProgression.add(1, "Passive", 1);
            levelProgression.add(2, "Ice", -25);
            levelProgression.add(3, "Skill Slot", 1);
            levelProgression.add(4, "Focus", 2);
            levelProgression.add(5, "Skill", 1);
            levelProgression.add(6, "Slash", -25);
            levelProgression.add(7, "Focus", 2);
            levelProgression.add(8, "Armor", 2);
            levelProgression.add(9, "Skill Slot", 1);

            combatAttributes = new CombatAttributes(8, 11, 18, 3, elementResistance);
            pokeshinto = new Pokeshinto(id, "Primordial Oak", skills, 2, 1, passives, combatAttributes, levelProgression);


        } else if (id.equals("Kohadai")){
            skills.add(getSkill("Quick Blade"));
            skills.add(getSkill("Slice"));
            skills.add(getSkill("Parry"));
			skills.add(getSkill("Push Back"));
            skills.add(getSkill("Breathe In"));
            skills.add(getSkill("Disarm"));
            skills.add(getSkill("Decapitate"));

			elementResistance.push("Slash", -50);
            elementResistance.push("Dark", 50);
            elementResistance.push("Art", -25);
            elementResistance.push("Nature", 25);

            levelProgression.add(1, "Agility", 2);
            levelProgression.add(2, "Strenght", 2);
            levelProgression.add(3, "Focus", 3);
            levelProgression.add(4, "Skill", 1);
            levelProgression.add(5, "War", -25);
            levelProgression.add(6, "Passive", 1);
            levelProgression.add(7, "Agility", 2);
            levelProgression.add(8, "Skill", 1);
            levelProgression.add(9, "Skill Slot", 1);

			combatAttributes = new CombatAttributes(16, 8, 10, 13, elementResistance);
			pokeshinto = new Pokeshinto(id, "Ancient Swordmaster", skills, 3, 1, passives, combatAttributes, levelProgression);

		} else if (id.equals("Kaizoyu")){
            skills.add(getSkill("Slice"));
            skills.add(getSkill("Cannon Blast"));
            skills.add(getSkill("Bone Spear"));
            skills.add(getSkill("Clobber"));
            skills.add(getSkill("Haunting Wail"));
            skills.add(getSkill("Pillage"));

            elementResistance.push("Wind", 25);
            elementResistance.push("Thunder", 25);
            elementResistance.push("Fire", 25);
            elementResistance.push("Nature", -25);
            elementResistance.push("Light", 50);
            elementResistance.push("Dark", -50);

            levelProgression.add(1, "Strenght", 1);
            levelProgression.add(2, "Agility", 1);
            levelProgression.add(3, "Armor", 1);
            levelProgression.add(4, "Skill", 1);
            levelProgression.add(5, "Strenght", 1);
            levelProgression.add(6, "Armor", 1);
            levelProgression.add(7, "Crush", -25);
            levelProgression.add(8, "Skill", 1);
            levelProgression.add(9, "Agility", 2);

            combatAttributes = new CombatAttributes(12, 7, 11, 10, elementResistance);
            pokeshinto = new Pokeshinto(id, "Ghostly Corsair", skills, 3, 1, passives, combatAttributes, levelProgression);

        } else if (id.equals("Shojeido")){
            skills.add(getSkill("Parry"));
            skills.add(getSkill("Raise Shield"));
            skills.add(getSkill("Thrust"));
            skills.add(getSkill("Halo of Courage"));
            skills.add(getSkill("Spinning Strike"));
            skills.add(getSkill("Heartseeker"));

            elementResistance.push("Dark", -25);
            elementResistance.push("Nature", -25);
            elementResistance.push("Crush", 25);
            elementResistance.push("Pierce", 25);
            elementResistance.push("Slash", -25);

            levelProgression.add(1, "Armor", 1);
            levelProgression.add(2, "Slash", -25);
            levelProgression.add(3, "Passive", 1);
            levelProgression.add(4, "Balance", -25);
            levelProgression.add(5, "Armor", 1);
            levelProgression.add(6, "Passive", 1);
            levelProgression.add(7, "Strenght", 1);
            levelProgression.add(8, "Skill", 1);
            levelProgression.add(9, "Passive", 1);

            combatAttributes = new CombatAttributes(10, 10, 18, 7, elementResistance);
            pokeshinto = new Pokeshinto(id, "Imperial Honorguard", skills, 4, 1, passives, combatAttributes, levelProgression);

        } else if (id.equals("Kumano")){
            skills.add(getSkill("Bite"));
            skills.add(getSkill("Clobber"));
            skills.add(getSkill("Helmcrusher"));
            skills.add(getSkill("Devour"));
            skills.add(getSkill("Charge In"));
            skills.add(getSkill("Rampage"));

            elementResistance.push("Nature", -50);
            elementResistance.push("Slash", 25);
            elementResistance.push("Cold", -25);
            elementResistance.push("Fire", 25);
            elementResistance.push("Light", 25);

            levelProgression.add(1, "Wind", -25);
            levelProgression.add(2, "Agility", 2);
            levelProgression.add(3, "Skill", 1);
            levelProgression.add(4, "Agility", 2);
            levelProgression.add(5, "Passive", 1);
            levelProgression.add(6, "Cold", -25);
            levelProgression.add(7, "Armor", 2);
            levelProgression.add(8, "Strenght", 2);
            levelProgression.add(9, "Skill", 1);

            combatAttributes = new CombatAttributes(17, 3, 15, 6, elementResistance);
            pokeshinto = new Pokeshinto(id, "Son of Bears", skills, 3, 1, passives, combatAttributes, levelProgression);

        } else if (id.equals("Kurokage")){
            skills.add(getSkill("Bite"));
            // skills.add(getSkill("Ember Blast"));

            elementResistance.push("Wind", 50);
            elementResistance.push("Light", 50);
            elementResistance.push("Dark", -50);
            elementResistance.push("Pierce", 25);

			combatAttributes = new CombatAttributes(12, 12, 12, 8, elementResistance);
			pokeshinto = new Pokeshinto(id, "Black Dragon", skills, 2, 1, passives, combatAttributes, levelProgression);

		} else {
		    throw new IllegalArgumentException("Pokeshinto " + id + " doesn't exists.");
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
        List<Heal> heals = new LinkedList<>();

        // Keyori
        if(id.equals("Slap")){
            damage.add(new DamageHp(true, 5.0, "Strength", 0, false, new Element("Art")));
            skill = new Skill(id, 0, new Element("Art"), 0, 0, false, condition, damage, status, trigger, heals,false);

        } else if(id.equals("Dumb Luck")){
            damage.add(new DamageHp(true, 300.0, "Focus", 0, false, new Element("Magic")));
            skill = new Skill(id, 0, new Element("Magic"), 0, 15, true, condition, damage, status, trigger, heals,false);

        } else if(id.equals("Crapskill")){
            damage.add(new DamageHp(true, 10.0, "Strength", 0, false, new Element("Nature")));
            skill = new Skill(id, 0, new Element("Nature"), 0, 1, true, condition, damage, status, trigger, heals,false);

        } else if(id.equals("Dickskill")){
            damage.add(new DamageHp(true, 10.0, "Focus", 0, false, new Element("Balance")));
            skill = new Skill(id, 0, new Element("Balance"), 0, 2, true, condition, damage, status, trigger, heals,false);

        }

        // Saruken
        else if(id.equals("Punch")){
            damage.add(new DamageHp(true, 20.0, "Strength", 0, false, new Element("Art")));
            skill = new Skill(id, 0, new Element("Art"), 0, 0, true, condition, damage, status, trigger, heals,false);

        } else if(id.equals("Smash")){
            damage.add(new DamageHp(true, 20.0, "Strength", 0, false, new Element("Crush")));
            skill = new Skill(id, 0, new Element("Crush"), 0, 0, true, condition, damage, status, trigger, heals,false);

        } else if(id.equals("Head Butt")){
            damage.add(new DamageHp(true, 26.0, "Strength", 0, false, new Element("Art")));
            damage.add(new DamageHp(true, 6.0, "Strength", 0, true, new Element("Art")));

            skill = new Skill(id, 0, new Element("Art"), 1, 0, true, condition, damage, status, trigger, heals,false);

        } else if(id.equals("Kiai Shout")){
            damage.add(new DamageStatus(false, "Stun", 0, new Element("Art"), false, "Stun", 2, true));
            damage.add(new DamageStatus(false, "Stun", 0, new Element("Art"), false, "Dizzy", 2, true));

            List<Status> triggerStatus = new LinkedList<>();
            AttributeBuff focusBuff = new AttributeBuffFlat(true, "Strenght", 0, "Focus", 10, true);
            triggerStatus.add(new StatusBuff(focusBuff, true, 0, null, 1));

            trigger.add(new TriggeredStatus(0, 1, true, null, "Start Turn", true, triggerStatus));

            skill = new Skill(id, 0, new Element("Art"), 1, 0, true, condition, damage, status, trigger, heals,false);

        } else if (id.equals("Frenzy")){
            AttributeBuff debuff = new AttributeDebuffFlat(false, "Armor", -3);
            trigger.add(new TriggeredBuff(0, 0, true, null, "End Attack", true, debuff));

            debuff = new AttributeDebuffFlat(false, "Focus", -3);
            trigger.add(new TriggeredBuff(0, 0, true, null, "End Attack", true, debuff));

            AttributeBuff buff = new AttributeBuffFlat(true, "Strength", 6);
            trigger.add(new TriggeredBuff(0, 0, true, null, "End Attack", true, buff));

            buff = new AttributeBuffFlat(false, "Agility", 6);
            trigger.add(new TriggeredBuff(0, 0, true, null, "End Attack", true, buff));

            status.add(new StatusRestrict("Meditate", "All", true, 0, null, Integer.MAX_VALUE));
            status.add(new StatusRestrict("Switch", "All", true, 0, null, Integer.MAX_VALUE));

            skill = new Skill(id, 9, new Element("Nature"), 0, 0, true, condition, damage, status, trigger, heals,false);

        }

        // Furuiki
        else if(id.equals("Sickening Spores")){
            damage.add(new DamageHp(true, 12.0, "Focus", 0, false, new Element("Nature")));

            AttributeBuff debuff = new AttributeDebuffFlat(true, "Focus", 0, "Focus", 6, false);

            List<Status> statusForTrigger = new LinkedList<>();
            statusForTrigger.add(new StatusBuff(debuff, true, 0, null, 2));
            trigger.add(new TriggeredStatus("End Turn", false, statusForTrigger));

            skill = new Skill(id, 0, new Element("Nature"), 0, 0, true, condition, damage, status, trigger, heals,false);

        } else if(id.equals("Weakening Spores")){
            damage.add(new DamageHp(true, 12.0, "Focus", 0, false, new Element("Nature")));

            AttributeBuff debuff = new AttributeDebuffFlat(true, "Strenght", 0, "Focus", 6, false);

            List<Status> statusForTrigger = new LinkedList<>();
            statusForTrigger.add(new StatusBuff(debuff, true, 0, null, 2));
            trigger.add(new TriggeredStatus("End Turn", false, statusForTrigger));

            skill = new Skill(id, 0, new Element("Nature"), 0, 0, true, condition, damage, status, trigger, heals,false);

        } else if(id.equals("Grow")){
            AttributeBuff buff = new AttributeBuffPercentage("Armor", true, "Strenght", 0.20, "Armor", true);
            trigger.add(new TriggeredBuff(0, 0, true, null, "End Turn", true, buff));

            buff = new AttributeBuffPercentage("Armor", true, "Strenght", 0.20, "Strenght", true);
            trigger.add(new TriggeredBuff(0, 0, true, null, "End Turn", true, buff));

            skill = new Skill(id, 0, new Element("Nature"), 1, 1, true, condition, damage, status, trigger, heals,false);

        } else if(id.equals("Drain Blood")){
            damage.add(new DamageHp(true, 16.0, "Focus", 0, false, new Element("Darkness")));

            Heal triggerHeal = new HealHP(true, 0.5, "HP", "Damage Done", true);  //TODO: check this
            trigger.add(new TriggeredHeal(0, 0, true, null, "End Turn", true, triggerHeal));

            skill = new Skill(id, 0, new Element("Darkness"), 1, 0, true, condition, damage, status, trigger, heals,false);

        } else if(id.equals("Dispell")){
            List<Status> statusForTrigger = new LinkedList<>();
            statusForTrigger.add(new StatusRestrict("Skill", "Magic", true, 0, null, 0));
            trigger.add(new TriggeredStatus("During Attack", false, statusForTrigger));

            skill = new Skill(id, 0, new Element("Energy"), 3, 1, true, condition, damage, status, trigger, heals,true);

        }

        // Kohadai
        else if(id.equals("Quick Blade")){
		    damage.add(new DamageHp(true, 16.0, "Strength", 0, false, new Element("Slash")));
			skill = new Skill(id, 0, new Element("Slash"), 0, 0, false, condition, damage, status, trigger, heals,true);
		
		} else if (id.equals("Slice")){
            damage.add(new DamageHp(true, 20.0, "Strength", 0, false, new Element("Slash")));
			skill = new Skill(id, 0, new Element("Slash"), 0, 0, false, condition, damage, status, trigger, heals,false);
		
		} else if (id.equals("Parry")){
		    status.add(new StatusProtection(1, "Damage", "Physical", true, 0, null, 0));

			skill = new Skill(id, 0, new Element("War"), 3, 0, false, condition, damage, status, trigger, heals,false);
		
		} else if (id.equals("Push Back")){
            damage.add(new DamageHp(true, 10.0, "Strength", 0, false, new Element("Art")));

			Damage triggerDamage = new DamageHp(true, 20, "Strength", 0, false, new Element("Art"));
			Condition triggerCondition = new ConditionStat(">=", "Damage Recieved", true, "CurrentHp", 0.2, true);
			trigger.add(new TriggeredDamage(0, 0, true, triggerCondition, "End Turn", true, triggerDamage));

			skill = new Skill(id, 0, new Element("Art"), 2, 0, false, condition, damage, status, trigger, heals,false);
			
		} else if (id.equals("Breathe In")){
		    AttributeBuff buff = new AttributeBuffFlat(true, "Strength", 0, "Focus", 20, true);
            status.add(new StatusBuff(buff, true, 1, null, 1));

            buff = new AttributeBuffFlat(true, "Strength", 0, "Focus", 5, true);
            status.add(new StatusBuff(buff,true, 2, null, 2));

            skill = new Skill(id, 0, new Element("Art"), 3, 0, false, condition, damage, status, trigger, heals,false);

		} else if (id.equals("Disarm")){
			status.add(new StatusRestrict("Skill", "War", false, 0, null, 3));

            skill = new Skill(id, 4, new Element("Art"), 0, 2, true, condition, damage, status, trigger, heals,false);

		} else if (id.equals("Decapitate")) {
            damage.add(new DamageHp(true, 16.0, "Strength", 0, false, new Element("Slash")));

            Damage triggerDamage = new DamageHp(true, Integer.MAX_VALUE, "Strength", 0, false, new Element("Slash"));
            Condition triggerCondition = new ConditionStat("<=", "CurrentHp", false, "MaxHp", 0.08, false);
            trigger.add(new TriggeredDamage(0, 0, true, triggerCondition, "End Turn", true, triggerDamage));

            skill = new Skill(id, 8, new Element("Slash"), 0, 2, true, condition, damage, status, trigger, heals,false);

        }

        // Kaizoyu
        else if (id.equals("Cannon Blast")){
            damage.add(new DamageHp(true, 33.0, "Strength", 0, false, new Element("Crush")));
            damage.add(new DamageStatus(true, "Stun", 2, new Element("Crush"), true));

            skill = new Skill(id, 0, new Element("Crush"), 2, 2, false, condition, damage, status, trigger, heals,false);

        } else if (id.equals("Clobber")){
            damage.add(new DamageHp(true, 12.0, "Strength", 0, false, new Element("Crush")));
            damage.add(new DamageStatus(true, "Stun", 1, new Element("Crush"), false));

            skill = new Skill(id, 0, new Element("Crush"), 0, 0, false, condition, damage, status, trigger, heals,false);

        } else if (id.equals("Bone Spear")){
            damage.add(new DamageHp(true, 22.0, "Strength", 0, false, new Element("Pierce")));
            condition = new ConditionStat(">=", "MissingHp", 0, true, "CurrentHp", 0, 44, true);

            skill = new Skill(id, 0, new Element("Pierce"), 0, 0, false, condition, damage, status, trigger, heals,false);

        } else if (id.equals("Haunting Wail")){
            damage.add(new DamageHp(true, 10.0, "Focus", 0, false, new Element("Dark")));

            Heal triggerHeal = new HealHpFlat(true, "Hp", 0, 1, "Damage Recieved", false);
            trigger.add(new TriggeredHeal(0, 0, true, null, "End Turn", true, triggerHeal));

            AttributeBuff triggerDebuff = new AttributeDebuffFlat(false, "Focus", 0, "Focus", 2, true);
            trigger.add(new TriggeredBuff(0, 0, true, null, "End Attack", true, triggerDebuff));

            AttributeBuff triggerBuff = new AttributeBuffFlat(true, "Focus", 0, "Focus", 2, true);
            trigger.add(new TriggeredBuff(0, 0, true, null, "End Attack", true, triggerBuff));

            skill = new Skill(id, 4, new Element("Dark"), 1, 1, false, condition, damage, status, trigger, heals,false);

        } else if (id.equals("Pillage")){
            // TODO: add item steal

            damage.add(new DamageHp(true, 10.0, "Focus", 2, false, new Element("War")));

            Heal triggerHeal = new HealHpFlat(true, "Hp", 0, 1, "Damage Recieved", false);
            trigger.add(new TriggeredHeal(0, 0, true, null, "End Turn", true, triggerHeal));

            skill = new Skill(id, 8, new Element("War"), 5, 1, false, condition, damage, status, trigger, heals,false);

        }

        // Shojeido
        else if (id.equals("Raise Shield")){
            status.add(new StatusProtection(0.9, "Damage", "Physical", true, 0, null, 0));
            status.add(new StatusProtection(0.75, "Damage", "Magical", true, 0, null, 0));

            Condition hasMeditated = new ConditionAction(true, new Action<>("Meditate", null));
            List<Status> triggerStatus = new LinkedList<>();
            triggerStatus.add(new StatusProtection(0.9, "Damage", "Physical", true, 0, hasMeditated, 1));
            triggerStatus.add(new StatusProtection(0.75, "Damage", "Magical", true, 0, hasMeditated, 1));

            AttributeBuff flatBuffArmor = new AttributeBuffFlat(true, "Armor", 4, null, 1, true);
            triggerStatus.add(new StatusBuff(flatBuffArmor, true, 0, null, 4));

            trigger.add(new TriggeredStatus(0, 2, true, hasMeditated, "Start Attack", true, triggerStatus));

            skill = new Skill(id, 0, new Element("War"), 4, 0, false, condition, damage, status, trigger, heals,false);

        } else if (id.equals("Thrust")){
            damage.add(new DamageHp(true, 20.0, "Strength", 0, false, new Element("Pierce")));

            skill = new Skill(id, 0, new Element("Pierce"), 0, 0, false, condition, damage, status, trigger, heals,false);

        } else if (id.equals("Halo of Courage")){
            damage.add(new DamageStatus(true, "Dizzy", 3, new Element("Light"), false));

            trigger.add(new TriggeredDispel("End Attack", "All", "All"));

            skill = new Skill(id, 0, new Element("Light"), 4, 1, false, condition, damage, status, trigger, heals,false);

        } else if (id.equals("Spinning Strike")){
            damage.add(new DamageStatus(true, "Dizzy", 1, new Element("War"), true));
            damage.add(new DamageStatus(true, "Dizzy", 1, new Element("War"), false));

            damage.add(new DamageHp(true, 20.0, "Strength", 0, false, new Element("War")));

            skill = new Skill(id, 0, new Element("War"), 1, 0, false, condition, damage, status, trigger, heals,false);

        } else if (id.equals("Heartseeker")){
            damage.add(new DamageHp(true, 15.0, "Strength", 0, false, new Element("Pierce")));
            damage.add(new DamageHpFlat(true, 0, 0.01, "MissingHp", false, null, "Physical"));
            damage.add(new DamageStatus(true, "Pain", 1, new Element("Pierce"), false, "MissingHp", 0.25, false));

            skill = new Skill(id, 8, new Element("Pierce"), 3, 3, false, condition, damage, status, trigger, heals,false);

        }

        // Kumano
        else if (id.equals("Bite")){
            damage.add(new DamageHp(true, 18.0, "Strength", 4, false, new Element("Nature")));

            skill = new Skill(id, 0, new Element("Nature"), 0, 0, false, condition, damage, status, trigger, heals,false);

        } else if (id.equals("Helmcrusher")){
            damage.add(new DamageHp(true, 18.0, "Strength", 4, false, new Element("Crush")));
            damage.add(new DamageStatus(true, "Stun", 2, new Element("Crush"), false));

            AttributeBuff debuff = new AttributeDebuffFlat(false, "Armor", -2);
            trigger.add(new TriggeredBuff(0, 0, true, null, "End Attack", false, debuff));

            skill = new Skill(id, 0, new Element("Crush"), 3, 3, false, condition, damage, status, trigger, heals,false);

        } else if (id.equals("Devour")){
            // If your opponent is at 50%+ of max HP, deal 50% more damage.
            Damage triggerDamage = new DamageHpFlat(true, 0, 0.5, "Damage Done", false, new Element("Nature"), "Physical");
            Condition triggerCondition = new ConditionStat(">=", "CurrentHp", false, "MaxHp", 0.5, false);
            trigger.add(new TriggeredDamage(0, 0, true, triggerCondition, "End Attack", true, triggerDamage));

            // If they're at less than 50%, heal yourself for 50% of that amount.
            Heal triggerHeal = new HealHpFlat(true, "Hp", 0, 0.5, "Damage Done", true);
            triggerCondition = new ConditionStat("<", "CurrentHp", false, "MaxHp", 0.5, false);
            trigger.add(new TriggeredHeal(0, 0, true, triggerCondition, "End Attack", true, triggerHeal));

            skill = new Skill(id, 0, new Element("Nature"), 0, 3, true, condition, damage, status, trigger, heals,false);

        } else if (id.equals("Charge In")){
            damage.add(new DamageHp(true, 25.0, "Strength", 4, false, new Element("War")));

            condition = new ConditionStat("<", null, 1, true, "Poke Turn", 1, 0, true);

            skill = new Skill(id, 3, new Element("War"), 0, 0, false, condition, damage, status, trigger, heals,false);

        } else if (id.equals("Rampage")){
            damage.add(new DamageStatus(true, "Seal", 6, new Element("Nature"), true));

            AttributeBuff debuff = new AttributeDebuffFlat(false, "Armor", -6);
            trigger.add(new TriggeredBuff(0, 0, true, null, "End Attack", true, debuff));

            debuff = new AttributeDebuffFlat(false, "Focus", -6);
            trigger.add(new TriggeredBuff(0, 0, true, null, "End Attack", true, debuff));

            AttributeBuff buff = new AttributeBuffFlat(true, "Strength", 6);
            trigger.add(new TriggeredBuff(0, 0, true, null, "End Attack", true, buff));

            buff = new AttributeBuffFlat(false, "Agility", 6);
            trigger.add(new TriggeredBuff(0, 0, true, null, "End Attack", true, buff));

            skill = new Skill(id, 9, new Element("Nature"), 0, 0, true, condition, damage, status, trigger, heals,false);

        }

        return skill;
	}
	
	/**
	 * Returns a quick description of a skill
	 */
	public static String getSkillInformation(String id) {
        String description = "";

        switch (id) {
            case "Slap":
                description = "Weakly slap your opponent.";
                break;

            case "Dumb Luck":
                description = "Sometimes, you get lucky!";
                break;

            case "Punch":
                description = "Deal 20 physical damage.";
                break;

            case "Smash":
                description = "Deal 20 physical damage.";
                break;

            case "Head Butt":
                description = "Deal 26 physical damage to your opponent and 6 to yourself.";
                break;

            case "Kiai Shout":
                description = "Double the amount of Stun and Dizzy points on your opponent. You gain 10 (Focus-based) Strenght next turn.";
                break;

            case "Frenzy":
                description = "For the whole fight, lose 3 Armour and 3 Focus, gain 6 Strenght and Agility and restrict Switching or Meditating.";
                break;

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

            case "Thrust":
                description = "Deal 20 pierce damage.";
                break;

            case "Halo of Courage":
                description = "Dispel all normal and special states and stat reductions. Dizzy-3 your opponent.";
                break;

            case "Spinning Strike":
                description = "Deal 20 physical damage. Dizzy-1 your opponent and yourself.";
                break;

            case "Heartseeker":
                description = "Deal 15 damage. Deals 1% more damage for each % of max HP you're missing. Pain-1 your opponent for each 25% of max HP missing, rounded up.";
                break;

            case "Cannon Blast":
                description = "Deal 33 physical damage and stun 2 yourself.";
                break;

            case "Bone Spear":
                description = "Deal 22 physical damage if you're missing 44 HP or more.";
                break;

            case "Clobber":
                description = "Deal 12 physical damage and stun 1 your opponent.";
                break;

            case "Haunting Wail":
                description = "Steal 10 health and 2 focus from your opponent.";
                break;

            case "Pillage":
                description = "Steal 12 health with spread 2. If they use an item this turn, use it instead.";
                break;

            case "Bite":
                description = "Deal 18 physical damage with 4 spread.";
                break;

            case "Healmcrusher":
                description = "Deal 18 damage. Reduce you're opponent's Armour by 2 for the whole fight. Stun-2 them.";
                break;

            case "Devour":
                description = "Deal 20*S damage with +4 Spread. If your opponent is at 50%+ of max HP, deal 50% more damage. If they're at less than 50%, heal yourself for 50% of that amount.";
                break;

            case "Charge In":
                description = "Can only be used the turn after being summoned. Deal 25 physical damage.";
                break;

            case "Rampage":
                description = "Seal-6 yourself. Lose 6 Armour and 6 Focus and gain 6 Strenght and 6 Agility for the whole fight.";
                break;

        }
		
		return description;
	}

	public static String getSkillElement(String id) {
        switch (id) {
            case "Slap":
                return "ART";

            case "Dumb Luck":
                return "MAGIC";

            case "Quick Blade":
                return "SLASH";

            case "Slice":
                return "SLASH";

            case "Parry":
                return "WAR";

            case "Push Back":
                return "WAR";

            case "Breathe In":
                return "ART";

            case "Disarm":
                return "ART";

            case "Decapitate":
                return "SLASH";

            case "Raise Shield":
                return "WAR";

            case "Thrust":
                return "PIERCE";

            case "Halo of Courage":
                return "LIGHT";

            case "Spinning Strike":
                return "WAR";

            case "Heartseeker":
                return "PIERCE";

            case "Cannon Blast":
                return "CRUSH";

            case "Bone Spear":
                return "PIERCE";

            case "Clobber":
                return "CRUSH";

            case "Haunting Wail":
                return "DARK";

            case "Pillage":
                return "WAR";

            case "Bite":
                return "NATURE";

            case "Healmcrusher":
                return "CRUSH";

            case "Devour":
                return "NATURE";

            case "Charge In":
                return "WAR";

            case "Rampage":
                return "NATURE";

            case "Punch":
                return "ART";

            case "Smash":
                return "Crush";

            case "Head Butt":
                return "Art";

            case "Kiai Shout":
                return "Art";

            case "Frenzy":
                return "Nature";
        }

        throw new IllegalArgumentException("The skill, " + id + " doesn't exist.");
    }

	public static String getPokeshintoDescription(String id) {
	    switch (id) {
            case "Keyori":
                return "N00b f**k";

            case "Kohadai":
                return "Ancient Swordmaster";

            case "Kaizoyu":
                return "Ghostly Corsair";

            case "Shojeido":
                return "Imperial Honorguard";

            case "Kumano":
                return "Son of Bears";

            case "Kurokage":
                return "Black Dragon";

            case "Saruken":
                return "Monkey Warrior";
        }

        throw new IllegalArgumentException("The pokeshinto " + id + " doesn't have a description.");
    }
	
	/**
	 * Return a standard set of combat attributes
	 */
	public static CombatAttributes getBasicCombatAttributes() {
		return new CombatAttributes(0, 0, 0, 0, new Dictionary());
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
			rootMenu.add("Item");
			rootMenu.add("Meditate");
            rootMenu.add("Inspect");

			menu = new Menu("Root", rootMenu, "Let the fight begin!");

		} else if (id.equals("New Turn")){
			rootMenu.add("Skill");
			rootMenu.add("Switch");
			rootMenu.add("Item");
			rootMenu.add("Meditate");
            rootMenu.add("Inspect");

			menu = new Menu("Root", rootMenu, null);
		}
		
		return menu;
		
	}
	
	public static CombatAI getGenericAI() {
		return new AISinglePokeshinto("Kohadai", getBasicCombatAttributes(), 100);
	}

    public static CombatAI getGenericAI(String id) {
        return new AISinglePokeshinto(id, getBasicCombatAttributes(), 100);
    }

}
