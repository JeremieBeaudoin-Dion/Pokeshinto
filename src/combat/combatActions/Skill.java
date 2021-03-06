package combat.combatActions;

import combat.*;

import java.util.Iterator;
import java.util.List;

/**
 * Skills are ShintoMon's abilities that can be casted during combat.
 * 
 * @author Jérémie Beaudoin-Dion
 *
 */
public class Skill extends Conditional {
	
	// Basic identification
	private String id;
	private int levelRequirement;
	private Element element;
	
	// Effect
	private List<Damage> damages;
	private List<Status> status;
	private List<Trigger> triggers;
	private List<Heal> heals;
	private boolean quick;

	/**
     * Constructor
     *
     * @param id: the name of the skill
     * @param levelRequirement: the minimum level of the shinto required to do the skill
     * @param element: the element of the skill; the player tries to win the element war each turn
     * @param initCooldown: the wait time after doing the skill
     * @param initBuildup: the wait time after the battle has begon
     * @param once: if the skill can only be casted once
     * @param damage: the damage inflicted by the skill
     * @param status: the statuses inflicted by the skill
     * @param triggers: all the triggers of the skill
     * @param heals: all the healing effects of a skill
     * @param quick: if the skill is quick
     */
    public Skill(String id, int levelRequirement, Element element, int initCooldown, int initBuildup, boolean once,
                 Condition condition, List<Damage> damage, List<Status> status, List<Trigger> triggers,
                 List<Heal> heals, boolean quick) {
        super(initCooldown, initBuildup, once, condition);

        this.id = id;
        this.levelRequirement = levelRequirement;
        this.element = element;

        this.damages = damage;
        this.status = status;
        this.triggers = triggers;
        this.heals = heals;

        this.quick = quick;
    }

	@Override
	public void resetCooldown() {
		super.resetCooldown();

        resetAllTriggers();
        resetAllStatus();
	}

	private void resetAllTriggers() {
        Iterator<Trigger> iter = triggers.iterator();
        Trigger currentTrigger;

        while (iter.hasNext()) {
            currentTrigger = iter.next();
            currentTrigger.resetCooldown();
        }
    }

    private void resetAllStatus() {
        Iterator<Status> iter = status.iterator();
        Status currentStatus;

        while (iter.hasNext()) {
            currentStatus = iter.next();
            currentStatus.resetCooldown();
        }
    }
	
	// Getters
	public String getId() {
		return id;
	}
	public int getLevelRequirement() {
		return levelRequirement;
	}
	public Element getElement() {
		return element;
	}
	public List<Damage> getDamage() {
		return damages;
	}
	public List<Status> getStatus() {
		return status;
	}
	public List<Trigger> getTriggers() {
		return triggers;
	}
	public List<Heal> getHeals() {
	    return heals;
    }
	public boolean getIsQuick() {
		return quick;
	}

	public int getCurrentCooldown() {
    	return cooldown;
	}
    public int getCurrentBuildup() {
        return buildup;
    }
    public boolean isOnce() {
        return once;
    }

}
