package combat;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import pokeshinto.Action;
import pokeshinto.Pokeshinto;

/**
 * A combat AI is used during combat to hold information and do decisions
 *
 * @author Jérémie Beaudoin-Dion
 */
public abstract class CombatAI {

    //TODO: Create new class "Giveable"

	// Basic information
	protected Pokeshinto currentPokeshinto;
	protected List<Pokeshinto> allPokeshintos;
	protected String owner;  // "Opponent" or "Player"
	
	// Combat attributes
	protected CombatAttributes combatAttributes;
	private CombatAttributes combatAttributesBuff;

	protected int stunEndurance;
	protected int stunDamage;
	protected int dizzyEndurance;
	protected int dizzyDamage;
	protected int painEndurance;
	protected int painDamage;
	
	// Combat information
	protected double physicalDamageDealt;
	protected double magicalDamageDealt;
	protected double currentHealth;
	protected double maxHealth;
	protected int pokeTurnNumber;
	
	// Skills information
	protected List<Status> allStatus;
	protected List<Trigger> allTriggers;
	protected Element skillAlignment;
	
	protected Action<String> choice;
	
	protected int capturedUntil;

    /**
     * Constructor
     *
     * @param allPokeshinto: all the pokeshintos of the AI
     * @param current: the equiped pokeshinto
     * @param combatAttributes: the attributes of the AI
     * @param maxHealth: the maximum possible health
     * @param health: the current health
     * @param owner: if it is an Enemy or a Player
     */
	public CombatAI(List<Pokeshinto> allPokeshinto, Pokeshinto current, CombatAttributes combatAttributes,
					double maxHealth, double health, String owner) {
		this.allPokeshintos = allPokeshinto;
		currentPokeshinto = current;
		this.combatAttributes = combatAttributes;
		this.currentHealth = health;
		this.maxHealth = maxHealth;
		this.owner = owner;
		
		pokeTurnNumber = 0;
		allStatus = new LinkedList<>();
		allTriggers = new LinkedList<>();

		if (currentPokeshinto != null) {
			capturedUntil = currentPokeshinto.getLevel() + 3;
		} else {
			capturedUntil = 3;
		}
		
		resetDamage();
	}
	
	/**
	 * Ask the AI to decide what to do.
	 */
	public abstract void decide(Combat combat);
	public abstract void setDecision(Action<String> action);
	
	/**
	 * Resets the damage dealt for the next turn
	 */
    void resetDamage() {
		physicalDamageDealt = 0;
		magicalDamageDealt = 0;
	}
	
	/**
	 * Does the action of the CombatAI
	 * 
	 * @param combat: the Combat class holding the logic of the combat
	 */
	void doChoice(Combat combat) {
        switch (choice.getKey()) {
            case "Skill":
                doSkill(combat, currentPokeshinto.getSkill(choice.getValue()));
                break;

            case "Switch":
                doSwitch(choice.getValue());
                break;

            case "Meditate":
                doMeditate(combat);
                break;
        }
	}

	private void doSkill(Combat combat, Skill skill) {
		if (skill.isUsable(combat, owner)) {

            if (!checkRestriction("Skill", skill.getElement())){
                // Handle damage
                handleDamage(combat, skill.getDamage());

                // Handle status
                handleStatus(combat, skill.getStatus());

                // Handle triggers
                handleTrigger(combat, skill.getTriggers());
            }

			skillAlignment = skill.getElement();
			skill.resetCooldown();

		} else {
			throw new ExceptionUnusableSkill(skill.getId());
		}
	}

	private void doSwitch(String pokeID) {

	    Iterator<Pokeshinto> iter = allPokeshintos.iterator();
	    Pokeshinto poke;

	    while(iter.hasNext()) {
            poke = iter.next();

            if (poke.getId().equals(pokeID)) {
                currentPokeshinto = poke;
                pokeTurnNumber = 0;
                return;
            }
        }

        throw new ExceptionNotHandled("The pokeshinto " + pokeID + " is not equiped by the player.");

	}

	private void doMeditate(Combat combat) {
        if (owner.equals("Player")) {
            combat.doOpponentCapture();
        }
	}

	void doDamage(Combat combat, double physicalDamage, double magicalDamage) {
		// Check for damage protection
		double magicalProtectionPercentage = 0;
		double physicalProtectionPercentage = 0;
		
		if (allStatus != null) {

			Iterator<Status> iter = allStatus.iterator();
			Status currentStatus;

			while(iter.hasNext()) {
			    currentStatus = iter.next();

                if (currentStatus instanceof StatusProtection) {
                    StatusProtection protect = (StatusProtection) currentStatus;

                    if (protect.getType().equals("Damage")) {
                        // Protection from physical damage
                        if (protect.getStat().equals("Physical")){
                            physicalProtectionPercentage += protect.getAmount();
                        }

                        // Protection from magical damage
                        if (protect.getStat().equals("Magical")){
                            magicalProtectionPercentage += protect.getAmount();
                        }
                    }
                }
            }
		}
		
		// Do the damage
		currentHealth -= physicalDamage - physicalDamage * physicalProtectionPercentage;
		currentHealth -= magicalDamage - magicalDamage * magicalProtectionPercentage;
		
		// Update health
		if (currentHealth < 0) {
			currentHealth = 0;
		}
	}

    /**
     * Iterates through Statuses or Triggers and check if it's time to
     * consider them.
     */
	private boolean checkRestriction(String type, Object other) {
	    Iterator<Status> iter = allStatus.iterator();
	    Status current;

	    while(iter.hasNext()) {
	        current = iter.next();

	        if (current instanceof StatusRestrict) {
	            if (type.equals(((StatusRestrict) current).getType())){
	                // Compare two elements
                    if (other instanceof Element) {
                        return ((Element) other).compareTo(new Element(((StatusRestrict) current).getStat())) == 0;
                    } else {
                        throw new ExceptionNotHandled("The object " + other.getClass() + " is not an Element.");
                    }
                }
            }
        }

	    return false;
    }

	void checkTriggers(Combat combat, String time) {
	    Iterator<Trigger> iter = allTriggers.iterator();
	    Trigger currentTrigger;

	    while(iter.hasNext()) {
	        currentTrigger = iter.next();

	        if (currentTrigger.isTheRightTime(time)) {
	            handleTrigger(combat, currentTrigger);
            }
        }
    }

    /**
     * Handles the desired element
     */
    private void handleDamage(Combat combat, List<Damage> listOfDamage) {
        Iterator<Damage> iter = listOfDamage.iterator();

        while(iter.hasNext()) {
            handleDamage(combat, iter.next());
        }
    }

    private void handleDamage(Combat combat, Damage damage) {
        if (damage.getDamageType().equals("Physical")){
            physicalDamageDealt += damage.getTotalDamage(combat, owner);
        } else {
            magicalDamageDealt += damage.getTotalDamage(combat, owner);
        }
    }

    private void handleStatus(Combat combat, List<Status> listOfStatus) {
        Iterator<Status> iter2 = listOfStatus.iterator();

        while(iter2.hasNext()) {
            giveStatus(combat, iter2.next());
        }
    }

    private void handleTrigger(Combat combat, List<Trigger> listOfTrigger) {
        Iterator<Trigger> iter3 = listOfTrigger.iterator();

        while(iter3.hasNext()) {
            giveTrigger(combat, iter3.next());
        }
    }

    private void handleTrigger(Combat combat, Trigger currentTrigger) {

	    if (currentTrigger.isUsable(combat, owner)){
	        currentTrigger.useTrigger();

	        if (currentTrigger instanceof TriggeredDamage) {
                TriggeredDamage trigger = (TriggeredDamage) currentTrigger;
                handleDamage(combat, trigger.getDamage());

            } else if (currentTrigger instanceof TriggeredBuff) {
                TriggeredBuff trigger = (TriggeredBuff) currentTrigger;
                giveBuff(combat, trigger.getBuff());
            }
        }

    }
	
	/**
	 * Adds on to the numbers of turns the pokeshinto has been in the hands of the AI
	 * Also updates all skills, triggers and statuses
	 */
	void implementPoketurn() {
		// Adds to the turn number
		pokeTurnNumber++;

        updateAllPokeshintos();

        updateAllStatus();

        updateAllTriggers();
		
	}

	private void updateAllPokeshintos() {
        Iterator<Pokeshinto> iter = allPokeshintos.iterator();
        Pokeshinto current;

        while(iter.hasNext()) {
            current = iter.next();

            updateAllSkills(current);
        }
    }

    private void updateAllSkills(Pokeshinto pokeshinto) {
        Iterator<Skill> iter = pokeshinto.getEquipedSkills().iterator();
        Skill current;

        while(iter.hasNext()) {
            current = iter.next();

            current.update();
        }
    }

	private void updateAllStatus() {
        Iterator<Status> iter = allStatus.iterator();

        while (iter.hasNext()){
            Status currentStatus = iter.next();
            currentStatus.update();

            // Check to delete if necessary
            if(currentStatus.isEnded()){
                iter.remove();
            }

        }
    }

    private void updateAllTriggers() {
        Iterator<Trigger> iter = allTriggers.iterator();

        while (iter.hasNext()){
            Trigger currentTrigger = iter.next();
            currentTrigger.update();

            if(currentTrigger.isEnded()){
                iter.remove();
            }

        }
    }

    /**
     * Gives to the correct CombatAI the desired element
     */
    private void giveStatus(Combat combat, Status status) {
        if (status.isOnMe()){
            allStatus.add(status);
        } else {
            // The status targets the other. Who is the other?
            if (owner.equals("Opponent")){
                combat.addPlayerStatus(status);
            } else {
                combat.addOpponentStatus(status);
            }
        }
    }

    private void giveTrigger(Combat combat, Trigger trigger) {
        if (trigger.isOnMe()) {
            allTriggers.add(trigger);
        } else {
            // The status targets the other. Who is the other?
            if (owner.equals("Opponent")){
                combat.addPlayerTrigger(trigger);
            } else {
                combat.addOpponentTrigger(trigger);
            }
        }
    }

    private void giveBuff(Combat combat, AttributeBuff buff) {
        if (buff.isOnMe()) {
            addBuff(combat, buff);
        } else {
            // The status targets the other. Who is the other?
            if (owner.equals("Opponent")){
                combat.addPlayerBuff(buff);
            } else {
                combat.addOpponentBuff(buff);
            }
        }
    }
	
	/**
	 * Adds a new element to the CombatAI
	 */
	void addStatus(Status status) {
		allStatus.add(status);
	}

	void addTrigger(Trigger trigger) {
	    allTriggers.add(trigger);
    }

    void addBuff(Combat combat, AttributeBuff buff) {
	    String id = buff.getAttribute();
	    double amount = buff.getAmount(combat, owner);

        switch (id) {
            case "Strength":
                combatAttributes.addStrength(amount);
                break;

            case "Focus":
                combatAttributes.addFocus(amount);
                break;

            case "Agility":
                combatAttributes.addAgility(amount);
                break;

            case "Armor":
                combatAttributes.addArmor(amount);
                break;

            default:
                throw new ExceptionNotHandled(id + "is not handled in addBuff");
        }

    }

    /**
     * Try to capture
     */
    void doCapture() {
        capturedUntil--;
    }

    /**
     * Return true if the pokemon has been captured
     */
    boolean getCaptured() {
        return capturedUntil <= 0;
    }

    /**
     * Ask the AI what is the decision
     */
    Action<String> getDecision() {
        return choice;
    }

	/**
	 * Getters
	 */
	public double getHealth() {
		return currentHealth;
	}
	public double getPhysicalDamageDealt() {
		return physicalDamageDealt;
	}
	public double getMagicalDamageDealt() {
		return magicalDamageDealt;
	}
	public double getCurrentHealth() {
		return currentHealth;
	}
	public double getMaxHealth() {
		return maxHealth;
	}
	public int getPokeTurnNumber() {
		return pokeTurnNumber;
	}
	public CombatAttributes getCombatAttributes() {
		return combatAttributes;
	}
	public Pokeshinto getCurrentPokeshinto() {
		return currentPokeshinto;
	}
	public Element getSkillAlignment() {
		return skillAlignment;
	}
	public boolean getSkillIsUsable(Combat combat, String id) {

	    Iterator<Skill> iter = currentPokeshinto.getEquipedSkills().iterator();
	    Skill skill;

	    while (iter.hasNext()) {
	        skill = iter.next();

	        if (skill.getId().equals(id)) {
	            return skill.isUsable(combat, owner);
            }
        }

        throw new ExceptionNotHandled("The skill " + id + " is not equiped by the pokeshinto");
	}

}
