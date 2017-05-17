package combat;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import pokeshinto.Action;
import pokeshinto.Pokeshinto;

public abstract class CombatAI {
	
	// Basic information
	protected Pokeshinto currentPokeshinto;
	protected Pokeshinto[] allPokeshintos;
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
	 * @param id : the pokeshinto's id
	 * @param combatAttributes : the attributes of the person
	 */
	public CombatAI(Pokeshinto[] allPokeshinto, Pokeshinto current, CombatAttributes combatAttributes, 
					double maxHealth, double health, String type) {
		this.allPokeshintos = allPokeshinto;
		currentPokeshinto = current;
		this.combatAttributes = combatAttributes;
		this.currentHealth = health;
		this.maxHealth = maxHealth;
		this.type = type;
		
		pokeTurnNumber = 0;
		allStatus = new LinkedList<Status>();
		capturedUntil = currentPokeshinto.getLevel() + 1;
		
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
	public void resetDamage() {
		physicalDamageDealt = 0;
		magicalDamageDealt = 0;
	}
	
	/**
	 * Does the action of the CombatAI
	 * 
	 * @param combat
	 */
	public void doChoice(Combat combat) {
		if (choice.getKey().equals("Skill")){
			doSkill(combat, currentPokeshinto.getSkill(choice.getValue()));
		} else if (choice.getKey().equals("Switch")){
			doSwitch(choice.getValue());
		}
	}
	
	/**
	 * Excute the skill of the AI
	 * 
	 * @param combat
	 * @param skill
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
	 * @param pokeNumber
	 */
	private void doSwitch(int pokeNumber) {
		int error = 0;
		
		// Switch pokeshinto
		for(int i=0; i<allPokeshintos.length; i++) {
			if (!allPokeshintos[i].getId().equals(currentPokeshinto.getId())){
				if (pokeNumber == i - error) {
					currentPokeshinto = allPokeshintos[i];
				}
			} else {
				error++;
			}
		}
		
		pokeTurnNumber = 0;
	}
	
	/**
	 * Do damage to the AI
	 * 
	 * @param damage
	 */
	public void doDamage(Combat combat, double physicalDamage, double magicalDamage) {
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
	public void implementPoketurn() {
		// Adds to the turn number
		pokeTurnNumber++;
		
		// Update all skills
		for (int i=0; i<allPokeshintos.length; i++) {
			for (int j=0; j < allPokeshintos[i].getEquipedSkills().length; j++) {
				Skill currentSkill = allPokeshintos[i].getSkill(j);
				currentSkill.update();
			}
		}
		
		// Update all status
		Iterator<Status> iter = allStatus.iterator();
		boolean doRemove = false;
		while (iter.hasNext()){
			Status current = iter.next();
			current.update();
			
			// Check to delete if necessary
			if(current.isEnded()){
				doRemove = true;
			}
			if (doRemove) {
				allStatus.remove(current);
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
	public boolean getCaptured() {
		return capturedUntil == 0;
	}
	
	/**
	 * Ask the AI what is the decision
	 */
	public Action<Integer> getDecision() {
		return choice;
	}
	
	/**
	 * Adds a new status to the CombatAI
	 * @param status
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
