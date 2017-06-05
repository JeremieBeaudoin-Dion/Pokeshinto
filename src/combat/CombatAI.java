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
	
	// Basic information
	protected Pokeshinto currentPokeshinto;
	protected List<Pokeshinto> allPokeshintos;
	protected String type;  // "Opponent" or "Player"
	
	// Combat attributes
	protected CombatAttributes combatAttributes;
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
	protected Element skillAlignment;
	
	protected Action<Integer> choice;
	
	protected int capturedUntil;

    /**
     * Constructor
     *
     * @param allPokeshinto: all the pokeshintos of the AI
     * @param current: the equiped pokeshinto
     * @param combatAttributes: the attributes of the AI
     * @param maxHealth: the maximum possible health
     * @param health: the current health
     * @param type: if it is an Enemy or a Player
     */
	public CombatAI(List<Pokeshinto> allPokeshinto, Pokeshinto current, CombatAttributes combatAttributes,
					double maxHealth, double health, String type) {
		this.allPokeshintos = allPokeshinto;
		currentPokeshinto = current;
		this.combatAttributes = combatAttributes;
		this.currentHealth = health;
		this.maxHealth = maxHealth;
		this.type = type;
		
		pokeTurnNumber = 0;
		allStatus = new LinkedList<>();

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
	public abstract void setDecision(Action<Integer> action);
	
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
	
	/**
	 * Excute the skill of the AI
	 * 
	 * @param combat: the Combat class holding the logic of the combat
	 * @param skill: the skill to use
	 */
	private void doSkill(Combat combat, Skill skill) {
		if (skill.isUsable(combat, type)) {
			Damage[] damage = skill.getDamage();
			Status[] status = skill.getStatus();
			
			// Handle damage
			if(damage != null) {
				for (int i=0; i<damage.length; i++) {
					if (damage[i].getDamageType().equals("Physical")){
						physicalDamageDealt = damage[i].getTotalDamage(combat, type);
					} else {
						magicalDamageDealt = damage[i].getTotalDamage(combat, type);
					}
				}
			}
			
			// Handle status
			if (status != null) {
				System.out.println("has status");
				for (int i=0; i<status.length; i++) {
					if (status[i].isOnMe()){
						allStatus.add(status[i]);
					} else {
						// The status targets the other. Who is the other?
						if (type.equals("Opponent")){
							combat.addPlayerStatus(status[i]);
						} else {
							combat.addOpponentStatus(status[i]);
						}
					}
				}
			}
			skillAlignment = skill.getElement();
			skill.resetCooldown();
		} else {
			throw new ExceptionUnusableSkill(skill.getId());
		}
	}
	
	/**
	 * Switch the current equiped pokeshinto
	 * 
	 * @param pokeNumber: the Number of the pokeshinto to switch
	 */
	private void doSwitch(int pokeNumber) {
		int error = 0;
		
		// Switch pokeshinto
        currentPokeshinto = allPokeshintos.get(pokeNumber);

        /*
		for(int i=0; i<allPokeshintos.length; i++) {
			if (!allPokeshintos[i].getId().equals(currentPokeshinto.getId())){
				if (pokeNumber == i - error) {
					currentPokeshinto = allPokeshintos[i];
				}
			} else {
				error++;
			}
		}*/
		
		pokeTurnNumber = 0;
	}

    /**
     * Meditates
     */
	private void doMeditate(Combat combat) {
        if (type.equals("Player")) {
            combat.doOpponentCapture();
        }
	}

    /**
     * Does damage to the AI
     *
     * @param combat: the Combat class holing the logic of the combat
     * @param physicalDamage: how much physical damage to deal
     * @param magicalDamage: how much magical damage to deal
     */
	void doDamage(Combat combat, double physicalDamage, double magicalDamage) {
		// Check for damage protection
		double magicalProtectionPercentage = 0;
		double physicalProtectionPercentage = 0;
		
		if (allStatus != null) {
			
			for (int i=0; i<allStatus.size(); i++){
				if (allStatus.get(i) instanceof StatusProtection) {
					StatusProtection protect = (StatusProtection) allStatus.get(i);
					
					// Protection from physical damage
					if (protect.getStat().equals("Physical Damage")){
						physicalProtectionPercentage += protect.getAmount();
					}
					
					// Protection from magical damage
					if (protect.getStat().equals("Magical Damage")){
						magicalProtectionPercentage += protect.getAmount();
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
	 * Adds on to the numbers of turns the pokeshinto has been in the hands of the AI
	 * Also implements the turn number of the skills of the Pokeshinto
	 */
	void implementPoketurn() {
		// Adds to the turn number
		pokeTurnNumber++;

		Iterator<Pokeshinto> iter = allPokeshintos.iterator();
		Pokeshinto current;

		// Update all skills
		while(iter.hasNext()) {
		    current = iter.next();

            for (int j=0; j < current.getEquipedSkills().length; j++) {
                Skill currentSkill = current.getSkill(j);
                currentSkill.update();
            }
        }

		
		// Update all status
		Iterator<Status> iter2 = allStatus.iterator();
		boolean doRemove = false;
		while (iter2.hasNext()){
			Status currentStatus = iter2.next();
            currentStatus.update();
			
			// Check to delete if necessary
			if(currentStatus.isEnded()){
				doRemove = true;
			}
			if (doRemove) {
				allStatus.remove(currentStatus);
				doRemove = false;
			}
			
		}
		
	}
	
	/**
	 * Try to capture 
	 */
	public void doCapture() {
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
	Action<Integer> getDecision() {
		return choice;
	}
	
	/**
	 * Adds a new status to the CombatAI
	 * @param status: the new status to Add
	 */
	public void addStatus(Status status) {
		allStatus.add(status);
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
	public List<Status> getCurrentStatus() {
		return allStatus;
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
	public boolean getSkillIsUsable(Combat combat, int index) {
		return currentPokeshinto.getSkill(index).isUsable(combat, type);
	}

}
