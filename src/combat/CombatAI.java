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

    public static double DIZZY_PERCENTAGE = 0.2;

	// Basic information
	protected Pokeshinto currentPokeshinto;
	protected List<Pokeshinto> allPokeshintos;
	protected String owner;  // "Opponent" or "Player"
	
	// Combat attributes
	protected CombatAttributes combatAttributes;
	private CombatAttributes combatAttributesBuff;

	protected DamageHolder statusTreshold;
	protected DamageHolder currentDamagedStatuses;
	
	// Combat information
    protected DamageHolder damageDealtToOther;
    protected DamageHolder damageDealtToMe;

	protected double currentHealth;
	protected double maxHealth;

	protected Dictionary timeSpentByPokeshinto;
	
	// Skills information
	protected List<Status> allStatus;
	protected List<AttributeBuff> allBuffsToRemove;
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

        timeSpentByPokeshinto = new Dictionary();

		allStatus = new LinkedList<>();
		allTriggers = new LinkedList<>();
        allBuffsToRemove = new LinkedList<>();

		if (currentPokeshinto != null) {
			capturedUntil = currentPokeshinto.getLevel() + 3;
		} else {
			capturedUntil = 3;
		}

        statusTreshold = new DamageHolder(3, 3, 3, 3, 0, 0);
        currentDamagedStatuses = new DamageHolder();
        damageDealtToOther = new DamageHolder();
        damageDealtToMe  = new DamageHolder();
	}
	
	/**
	 * Ask the AI to decide what to do.
	 */
	public abstract void decide(Combat combat);
	public abstract void setDecision(Action<String> action);

    /**
     * Starts a new turn:
     *      Reset the damage done
     *      Implements on the turn of the pokeshinto
     *      Updates all shintos, triggers, statuses
     *      Check all triggers
     */
	void newTurn(Combat combat) {
        resetDecision();

        // Set the damage to 0
        resetDamage();

        updateAllPokeshintos();

        updateAllStatus();

        updateAllTriggers();

        addAllTemporaryBuffs(combat);

        checkTriggers(combat, "Start Turn");

        checkDizzy();
    }

    /**
     * Set the player's choice back to null if not Stunned
     */
    private void resetDecision() {
        if (currentDamagedStatuses.get("Stun") >= statusTreshold.get("Stun")) {
            setDecision(new Action<>("Stun", Double.toString(currentDamagedStatuses.get("Stun"))));
            currentDamagedStatuses.reset("Stun");
        } else {
            setDecision(null);
        }
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

    private void resetDamage() {
        damageDealtToOther.resetAll();
        damageDealtToMe.resetAll();
	}

	private void addAllTemporaryBuffs(Combat combat) {
        Iterator<Status> iter = allStatus.iterator();
        Status current;

        while(iter.hasNext()) {
            current = iter.next();

            if (current.isUsable(combat, owner)) {
                if (current instanceof StatusBuff) {
                    AttributeBuff tempbuff = ((StatusBuff) current).getBuff();

                    String id = tempbuff.getAttribute();
                    double amount = tempbuff.getAmount(combat, owner);
                    handleBuff(id, amount);
                    allBuffsToRemove.add(new AttributeBuff(true, id, amount));
                }
            }
        }
    }

    private void checkDizzy() {
        if (currentDamagedStatuses.get("Dizzy") >= statusTreshold.get("Dizzy")) {
            double strength = -(combatAttributes.getStrength() + currentPokeshinto.getCombatAttributes().getStrength())* DIZZY_PERCENTAGE;
            double armor = -(combatAttributes.getArmor() + currentPokeshinto.getCombatAttributes().getArmor())* DIZZY_PERCENTAGE;
            double focus = -(combatAttributes.getFocus() + currentPokeshinto.getCombatAttributes().getFocus())* DIZZY_PERCENTAGE;
            double agility = -(combatAttributes.getAgility() + currentPokeshinto.getCombatAttributes().getAgility())* DIZZY_PERCENTAGE;

            handleBuff("Strength", strength);
            allBuffsToRemove.add(new AttributeBuff(true, "Strength", strength));

            handleBuff("Armor", armor);
            allBuffsToRemove.add(new AttributeBuff(true, "Armor", armor));

            handleBuff("Focus", focus);
            allBuffsToRemove.add(new AttributeBuff(true, "Focus", focus));

            handleBuff("Agility", agility);
            allBuffsToRemove.add(new AttributeBuff(true, "Agility", agility));
        }
    }

    /**
     * Ends the turn
     */
    void endTurn(Combat combat) {
        checkPain();

        checkTriggers(combat, "End Turn");

        removeAllTemporaryBuffs(combat);

        implementPoketurn();
    }

    /**
     * If the combatAI is pained, unpain the AI, and deal 5% of maxHP + 5% of cuurentHP
     */
    private void checkPain() {

        if (getCurrentDamagedStatuses().get("Pain") >= getStatusTreshold().get("Pain")) {
            double flatAmount = 0.05 * maxHealth;
            Damage damage = new DamageHpFlat(true, flatAmount, 0.05, "Current Hp",
                    true, null, "True", true);

            addTrigger(new TriggeredDamage(0, 0, true, null, "End Turn", true, damage));
            addTrigger(new TriggeredDamage(0, 1, true, null, "End Turn", true, damage));
            addTrigger(new TriggeredDamage(0, 2, true, null, "End Turn", true, damage));
            addTrigger(new TriggeredDamage(0, 3, true, null, "End Turn", true, damage));
            addTrigger(new TriggeredDamage(0, 4, true, null, "End Turn", true, damage));

            getCurrentDamagedStatuses().reset("Pain");  // TODO: May last all fight
        }
    }

    private void removeAllTemporaryBuffs(Combat combat) {
        Iterator<AttributeBuff> iter = allBuffsToRemove.iterator();
        AttributeBuff tempBuff;

        while(iter.hasNext()) {
            tempBuff = iter.next();

            String id = tempBuff.getAttribute();
            double amount = -tempBuff.getAmount(combat, owner);
            handleBuff(id, amount);

            iter.remove();
        }
    }

    private void implementPoketurn() {
        // Adds to the turn number
        if (currentPokeshinto != null) {
            timeSpentByPokeshinto.push(currentPokeshinto.getId(), 1);
        }
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

            if (!checkRestriction(combat, "Skill", skill.getElement())){
                // Handle damage
                handleDamages(combat, skill.getDamage());

                // Handle status
                handleStatuses(combat, skill.getStatus());

                // Handle triggers
                handleTriggers(combat, skill.getTriggers());
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
                return;
            }
        }

        throw new IllegalArgumentException("The pokeshinto " + pokeID + " is not equiped by the player.");

	}

	private void doMeditate(Combat combat) {
        if (owner.equals("Player")) {
            combat.doOpponentCapture();
        }

        // When you meditate, you reset the Dizzy damage
        if (currentDamagedStatuses.get("Dizzy") >= statusTreshold.get("Dizzy")) {
            currentDamagedStatuses.reset("Dizzy");
        }
	}

	void doDamage(Combat combat, DamageHolder damageDealtByOther) {
        DamageHolder damageProtection = getDamageProtectionPercentage(combat);

        double physicalDamage = damageDealtByOther.get("Physical") + damageDealtToMe.get("Physical");
        double magicalDamage = damageDealtByOther.get("Magical") + damageDealtToMe.get("Magical");

        double stunDamage = damageDealtByOther.get("Stun") + damageDealtToMe.get("Stun");
        double painDamage = damageDealtByOther.get("Pain") + damageDealtToMe.get("Pain");
        double dizzyDamage = damageDealtByOther.get("Dizzy") + damageDealtToMe.get("Dizzy");
        double sealDamage = damageDealtByOther.get("Seal") + damageDealtToMe.get("Seal");
		
		// Do the damage
		currentHealth -= physicalDamage - physicalDamage * damageProtection.get("Physical");
		currentHealth -= magicalDamage - magicalDamage * damageProtection.get("Magical");

        currentDamagedStatuses.add("Stun", stunDamage - stunDamage * damageProtection.get("Stun"));
        currentDamagedStatuses.add("Pain", painDamage - painDamage * damageProtection.get("Pain"));
        currentDamagedStatuses.add("Dizzy", dizzyDamage - dizzyDamage * damageProtection.get("Dizzy"));
        currentDamagedStatuses.add("Seal", sealDamage - sealDamage * damageProtection.get("Seal"));

        checkHealthIsOverZero();
	}

	private void checkHealthIsOverZero() {
        // Update health
        if (currentHealth < 0) {
            currentHealth = 0;
        }
    }

	private void doInstantDamage(Combat combat, String type, double amount) {
        DamageHolder damageProtection = getDamageProtectionPercentage(combat);

	    if (type.equals("True")) {
            currentHealth -= amount;

        } else if (type.equals("Physical")) {
            currentHealth -= amount - amount * damageProtection.get("Physical");

        } else if (type.equals("Magical")) {
            currentHealth -= amount - amount * damageProtection.get("Magical");

        } else if (type.equals("Stun")) {
            currentDamagedStatuses.add("Stun", amount - amount * damageProtection.get("Stun"));

        } else if (type.equals("Pain")) {
            currentDamagedStatuses.add("Pain", amount - amount * damageProtection.get("Pain"));

        } else if (type.equals("Dizzy")) {
            currentDamagedStatuses.add("Dizzy", amount - amount * damageProtection.get("Dizzy"));

        } else if (type.equals("Seal")) {
            currentDamagedStatuses.add("Seal", amount - amount * damageProtection.get("Seal"));

        }

        checkHealthIsOverZero();
    }

	private DamageHolder getDamageProtectionPercentage(Combat combat) {
	    DamageHolder protection = new DamageHolder();

        if (allStatus != null) {

            Iterator<Status> iter = allStatus.iterator();
            Status currentStatus;

            while(iter.hasNext()) {
                currentStatus = iter.next();

                if (currentStatus.isUsable(combat, owner)) {
                    if (currentStatus instanceof StatusProtection) {
                        StatusProtection protect = (StatusProtection) currentStatus;

                        if (protect.getType().equals("Damage")) {
                            protection.add(protect.getStat(), protect.getAmount());
                        }
                    }
                }

            }
        }

	    return protection;
    }

    /**
     * Iterates through Statuses or Triggers and check if it's time to
     * consider them.
     */
	private boolean checkRestriction(Combat combat, String type, Object other) {
	    Iterator<Status> iter = allStatus.iterator();
	    Status current;

	    while(iter.hasNext()) {
	        current = iter.next();

	        if (current.isUsable(combat, owner)) {
                if (current instanceof StatusRestrict) {
                    if (type.equals(((StatusRestrict) current).getType())){
                        // Compare two elements
                        if (other instanceof Element) {
                            return ((Element) other).compareTo(new Element(((StatusRestrict) current).getStat())) == 0;
                        } else {
                            throw new IllegalArgumentException("The object " + other.getClass() + " is not an Element.");
                        }
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
    private void handleDamages(Combat combat, List<Damage> listOfDamage) {
        Iterator<Damage> iter = listOfDamage.iterator();

        while(iter.hasNext()) {
            giveDamage(combat, iter.next());
        }
    }

    private void handleStatuses(Combat combat, List<Status> listOfStatus) {
        Iterator<Status> iter2 = listOfStatus.iterator();

        while(iter2.hasNext()) {
            giveStatus(combat, iter2.next());
        }
    }

    private void handleTriggers(Combat combat, List<Trigger> listOfTrigger) {
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

                giveDamage(combat, trigger.getDamage());

            } else if (currentTrigger instanceof TriggeredBuff) {
                TriggeredBuff trigger = (TriggeredBuff) currentTrigger;
                giveBuff(combat, trigger.getBuff());
            }
        }

    }

    /**
     * Gives to the correct CombatAI the desired element
     */
    private void giveDamage(Combat combat, Damage damage) {
        if (damage.dealerIsMe()){
            addDamage(combat, damage);

        } else {
            // The status targets the other. Who is the other?
            if (owner.equals("Opponent")){
                combat.addPlayerDamage(damage);
            } else {
                combat.addOpponentDamage(damage);
            }
        }
    }

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
	void addDamage(Combat combat, Damage damage) {
        if (damage.affectsMe()) {
            if (!damage.isInstant()) {
                damageDealtToMe.add(damage.getDamageType(), damage.getTotalDamage(combat, owner));

            } else {
                doInstantDamage(combat, damage.getDamageType(), damage.getTotalDamage(combat, owner));
            }

        } else {
            damageDealtToOther.add(damage.getDamageType(), damage.getTotalDamage(combat, owner));
        }
    }

	void addStatus(Status status) {
		allStatus.add(status.copy());
	}

	void addTrigger(Trigger trigger) {
	    allTriggers.add(trigger.copy());
    }

    void addBuff(Combat combat, AttributeBuff buff) {
	    String id = buff.getAttribute();
	    double amount = buff.getAmount(combat, owner);

        handleBuff(id, amount);
    }

    private void handleBuff(String attribute, double amount) {
        switch (attribute) {
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
                throw new IllegalArgumentException(attribute + " is not handled in addBuff.");
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
	public DamageHolder getDamageDealtToOther() {
		return damageDealtToOther;
	}
    public DamageHolder getDamageDealtToMe() {
        return damageDealtToMe;
    }
    public DamageHolder getStatusTreshold() { return statusTreshold; }
    public DamageHolder getCurrentDamagedStatuses() { return currentDamagedStatuses; }

	public double getCurrentHealth() {
		return currentHealth;
	}
	public double getMaxHealth() {
		return maxHealth;
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

        throw new IllegalArgumentException("The skill " + id + " is not equiped by the pokeshinto");
	}

	public String getWhySkillCantBeDone(String id) {
        Iterator<Skill> iter = currentPokeshinto.getEquipedSkills().iterator();
        Skill skill;

        while (iter.hasNext()) {
            skill = iter.next();

            if (skill.getId().equals(id)) {
                return skill.getDescription();
            }
        }

        throw new IllegalArgumentException("The skill " + id + " is not equiped by the pokeshinto");
    }
    public Dictionary getTimeSpentByPokeshinto() {
	    return timeSpentByPokeshinto;
    }

    public int getAllLevels() {
	    int totalLevel = 0;

	    Iterator<Pokeshinto> iter = allPokeshintos.iterator();
	    while(iter.hasNext()) {
	        totalLevel += iter.next().getLevel();
        }

        return totalLevel;
    }

}
