package combat;
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
	private Damage[] damages;
	private Status[] status;
	private Trigger[] triggers;
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
	 */
	public Skill(String id, int levelRequirement, Element element, int initCooldown, int initBuildup, boolean once, 
			Condition condition, Damage[] damage, Status[] status, Trigger[] triggers, boolean quick) {
		super(initCooldown, initBuildup, once, condition);
		
		this.id = id;
		this.levelRequirement = levelRequirement;
		this.element = element;
		
		this.damages = damage;
		this.status = status;
		this.triggers = triggers;
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
	public Damage[] getDamage() {
		return damages;
	}
	public Status[] getStatus() {
		return status;
	}
	public Trigger[] getTriggers() {
		return triggers;
	}
	public boolean getIsQuick() {
		return quick;
	}
	

}
