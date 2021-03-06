package combat;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import combat.combatAI.OpponentAI;
import combat.combatActions.*;
import pokeshinto.Action;
import pokeshinto.Pokeshinto;
import world.items.Consumable;
import world.items.HealthPotion;
import world.items.Item;
import world.items.ItemBag;

/**
 * A combat AI is used during combat to hold information and do decisions
 *
 * @author Jérémie Beaudoin-Dion
 */
public abstract class CombatAI implements Serializable {

    private static double DIZZY_PERCENTAGE = 0.2;

	// Basic information
    protected Pokeshinto currentPokeshinto;
    protected List<Pokeshinto> allPokeshintos;
	protected String owner;  // "Opponent" or "Player"

    // Items
    private ItemBag allItems;

	// Combat attributes
    private CombatAttributes combatAttributes;

	// Statuses
    private DamageHolder statusTreshold;
    private DamageHolder currentDamagedStatuses;
    private double damagedDealtByPain;
    private double maxDamagePainDeals;

    private boolean hasAlreadyDebuffedSeal;

    private boolean willResetPain;
    private boolean willResetStun;
    private boolean willResetDizzy;
    private boolean willResetSeal;
	
	// Combat information
    private DamageHolder damageDealtToOther;
    private DamageHolder damageDealtToMe;

    private double currentHealth;
    private double maxHealth;

    private Dictionary timeSpentByPokeshinto;
	
	// Skills information
    private List<Status> allStatus;
    private List<AttributeBuff> allBuffsToRemove;
    private List<Trigger> allTriggers;
    private Element skillAlignment;

    private List<AttributeBuff> permanentBuffsAdded;
    private List<AttributeDebuffFlat> permanentDebuffsAdded;

    protected Action<String> choice;

    private int capturedUntil;
    private int currentPoketurn;

    private ActionDescriptor combatActionDescriptor;

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
	public CombatAI(List<Pokeshinto> allPokeshinto, Pokeshinto current, ItemBag allItems, CombatAttributes combatAttributes,
                    double maxHealth, double health, String owner) {
		this.allPokeshintos = allPokeshinto;
		currentPokeshinto = current;

        verifyEquipedPokeshintoIsInAll();

        this.allItems = allItems;

		this.combatAttributes = combatAttributes;
		this.currentHealth = health;
		this.maxHealth = maxHealth;
		this.owner = owner;

        timeSpentByPokeshinto = new Dictionary();
        currentPoketurn = 0;

		allStatus = new LinkedList<>();
		allTriggers = new LinkedList<>();
        allBuffsToRemove = new LinkedList<>();
        permanentBuffsAdded = new LinkedList<>();
        permanentDebuffsAdded = new LinkedList<>();

        setNumberOfCaptured();

        statusTreshold = new DamageHolder(4, 4, 4, 4, 0, 0);
        currentDamagedStatuses = new DamageHolder();
        damageDealtToOther = new DamageHolder();
        damageDealtToMe  = new DamageHolder();
        damagedDealtByPain = 0;
        maxDamagePainDeals = 0;
	}

	void setActionDescriptor(ActionDescriptor combatActionDescriptor) {
	    this.combatActionDescriptor = combatActionDescriptor;
    }

    /**
     * Helper methods
     */
	private void verifyEquipedPokeshintoIsInAll() {
	    if (!allPokeshintos.contains(currentPokeshinto) && currentPokeshinto != null) {
            allPokeshintos.add(currentPokeshinto);
        }
    }

    private void setNumberOfCaptured() {
        if (currentPokeshinto != null) {
            capturedUntil = currentPokeshinto.getLevel() + 3;
        } else {
            capturedUntil = 3;
        }
    }
	
	/**
	 * Ask the AI to decide what to do.
	 */
	public abstract void decide(Combat combat);
	public abstract void setDecision(Action<String> action);
	public abstract void chooseFirstPokeshinto(List<Pokeshinto> opponentsPokeshinto);

    /**
     * Equips the pokeshinto
     */
    protected void equip(String pokeshintoID) {
        doSwitch(pokeshintoID);
    }

    /**
     * Starts a new turn:
     *      Reset the damage done
     *      Implements on the turn of the pokeshinto
     *      Updates all shintos, triggers, statuses
     *      Check all triggers
     */
	void newTurn(Combat combat) {
        updateAllPokeshintos();

        resetDecision();

        updateAllItems();

        // Set the damage to 0
        resetDamage();

        updateAllStatus();

        updateAllTriggers();

        addAllTemporaryBuffs(combat);

        checkTriggers(combat, "Start Turn");

        checkDizzy();
    }

    void startCombat(Combat combat) {
        addAllTemporaryBuffs(combat);

        checkTriggers(combat, "Start Turn");
    }

    /**
     * Set the player's choice back to null if not Stunned
     */
    private void resetDecision() {
        if (currentDamagedStatuses.get("Stun") >= statusTreshold.get("Stun")) {
            setDecision(new Action<>("Stun", Double.toString(currentDamagedStatuses.get("Stun"))));
            willResetStun = true;
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

    private void updateAllItems() {
        allItems.removeAllConsumedConsumable();
    }

    /**
     * Updates all skills except the one that you just did
     */
    private void updateAllSkills(Pokeshinto pokeshinto) {
        Iterator<Skill> iter = pokeshinto.getEquipedSkills().iterator();
        Skill current;

        while(iter.hasNext()) {
            current = iter.next();

            if (!current.getId().equals(getDecision().getValue())) {
                current.update();
            }
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
                    allBuffsToRemove.add(new AttributeBuffFlat(true, id, amount));
                }
            }
        }
    }

    private void checkDizzy() {
        if (currentPokeshinto == null) {
            return;
        }

        if (currentDamagedStatuses.get("Dizzy") >= statusTreshold.get("Dizzy")) {
            double strength = -(combatAttributes.getStrength() + currentPokeshinto.getCombatAttributes().getStrength())* DIZZY_PERCENTAGE;
            double armor = -(combatAttributes.getArmor() + currentPokeshinto.getCombatAttributes().getArmor())* DIZZY_PERCENTAGE;
            double focus = -(combatAttributes.getFocus() + currentPokeshinto.getCombatAttributes().getFocus())* DIZZY_PERCENTAGE;
            double agility = -(combatAttributes.getAgility() + currentPokeshinto.getCombatAttributes().getAgility())* DIZZY_PERCENTAGE;

            handleBuff("Strength", strength);
            allBuffsToRemove.add(new AttributeDebuffFlat(true, "Strength", strength));

            handleBuff("Armor", armor);
            allBuffsToRemove.add(new AttributeDebuffFlat(true, "Armor", armor));

            handleBuff("Focus", focus);
            allBuffsToRemove.add(new AttributeDebuffFlat(true, "Focus", focus));

            handleBuff("Agility", agility);
            allBuffsToRemove.add(new AttributeDebuffFlat(true, "Agility", agility));
        }
    }

    /**
     * Ends the turn
     */
    void endTurn(Combat combat) {
        checkPain();

        checkSeal();

        checkTriggers(combat, "End Turn");

        removeAllTemporaryBuffs(combat);

        resetAllStatus();

        implementPoketurn();

        doElementWar(combat);
    }

    /**
     * If the combatAI is pained, it recieves damage until the treshold is reached for
     * the maximum damage pain can deal.
     */
    private void checkPain() {

        if (getCurrentDamagedStatuses().get("Pain") >= getStatusTreshold().get("Pain")) {
            if (maxDamagePainDeals == 0) {
                maxDamagePainDeals = 0.2 * maxHealth;
                damagedDealtByPain = 0;
            }

            double flatAmount = 0.02 * maxHealth;
            double changedAmount = 0.04 * (maxHealth - currentHealth);

            damagedDealtByPain += flatAmount + changedAmount;

            currentHealth -= flatAmount + changedAmount;

            if (damagedDealtByPain >= maxDamagePainDeals) {
                willResetPain = true;
                maxDamagePainDeals = 0;
            }
        }
    }

    private void checkSeal() {
        if (!hasAlreadyDebuffedSeal && getCurrentDamagedStatuses().get("Seal") >= getStatusTreshold().get("Seal")) {
            if (currentPokeshinto == null) {
                hasAlreadyDebuffedSeal = true;
                return;
            }

            doSealDebuff(1);

            hasAlreadyDebuffedSeal = true;
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

    private void resetAllStatus() {
        if (willResetDizzy) {
            currentDamagedStatuses.reset("Dizzy");
            willResetDizzy = false;
        }

        if (willResetPain) {
            currentDamagedStatuses.reset("Pain");
            willResetPain = false;
        }

        if (willResetStun) {
            currentDamagedStatuses.reset("Stun");
            willResetStun = false;
        }

        if (willResetSeal) {
            currentDamagedStatuses.reset("Seal");
            doSealDebuff(-1);
            willResetSeal = false;
            hasAlreadyDebuffedSeal = false;
        }
    }

    /**
     * Adds or substract 1 to all skill's initial cooldown
     */
    private void doSealDebuff(int value) {
        Iterator<Skill> iter = currentPokeshinto.getEquipedSkills().iterator();
        Skill currentSkill;
        while(iter.hasNext()) {
            currentSkill = iter.next();

            currentSkill.addToInitialCooldown(value);
        }
    }

    private void implementPoketurn() {
        // Adds to the turn number
        if (currentPokeshinto != null) {
            timeSpentByPokeshinto.push(currentPokeshinto.getId(), 1);
        }

        currentPoketurn++;
    }

    private void doElementWar(Combat combat) {
        int outcome = combat.getCombatElementWarOutcome();

        if (outcome >= 1 && owner.equals("Player") || outcome <= -1 && owner.equals("Opponent")) {

            AttributeBuff permBuff = new AttributeBuffFlat(true, "Armor", 1, null, 1, true);
            addBuff(combat, permBuff);

            permBuff = new AttributeBuffFlat(true, "Focus", 1, null, 1, true);
            addBuff(combat, permBuff);

            permBuff = new AttributeBuffFlat(true, "Strength", 1, null, 1, true);
            addBuff(combat, permBuff);

            permBuff = new AttributeBuffFlat(true, "Agility", 1, null, 1, true);
            addBuff(combat, permBuff);
        }
    }

    /**
	 * Does the action of the CombatAI
	 * 
	 * @param combat: the Combat class holding the logic of the combat
	 */
    void doQuickState(Combat combat) {
        if (choice.getKey().equals("Skill")) {
            Skill currentSkill = currentPokeshinto.getSkill(choice.getValue());
            if (currentSkill.getIsQuick()) {
                doSkill(combat, currentSkill);
                // Empty choice
                combatActionDescriptor.setAction(owner, choice);
                choice = new Action<>("", "");
            }
        }
    }

	void doChoice(Combat combat) {
        if (checkRestriction(combat, choice.getKey(), choice.getValue())) {
            combatActionDescriptor.setAction(owner, new Action<>("Restrict", choice.getKey()));
            return;
        }

        switch (choice.getKey()) {
            case "Skill":
                doSkill(combat, currentPokeshinto.getSkill(choice.getValue()));
                combatActionDescriptor.setAction(owner, choice);
                return;

            case "Item":
                doItem(combat, getItem(choice.getValue()));
                combatActionDescriptor.setAction(owner, choice);
                return;

            case "Switch":
                doSwitch(choice.getValue());
                combatActionDescriptor.setAction(owner, choice);
                return;

            case "Choose":
                doSwitch(choice.getValue());
                combatActionDescriptor.setAction(owner, choice);
                return;

            case "Meditate":
                doMeditate(combat);
                combatActionDescriptor.setAction(owner, choice);
                return;

            case "Stun":
                combatActionDescriptor.setAction(owner, choice);
                return;
        }

        throw new IllegalArgumentException("The choice: " + choice.getKey() + " is not handled.");
	}

	private void doSkill(Combat combat, Skill skill) {

		if (skill.isUsable(combat, owner)) {

            handleDamages(combat, skill.getDamage());

            handleStatuses(combat, skill.getStatus());

            handleTriggers(combat, skill.getTriggers());

            handleHeals(combat, skill.getHeals());

			skillAlignment = skill.getElement();
			skill.resetCooldown();

		} else {
			throw new ExceptionUnusableSkill(skill.getId());
		}
	}

	private Item getItem(String id) {
        return allItems.get(id);
    }

	private void doItem(Combat combat, Item item) {

        if (item instanceof HealthPotion) {
            giveHeal(combat, ((HealthPotion) item).getHeal());
        }

        if (item instanceof Consumable) {
            ((Consumable) item).use();
        }
    }

	private void doSwitch(String pokeID) {
	    if (pokeID == null) {
	        return;
        }

	    Iterator<Pokeshinto> iter = allPokeshintos.iterator();
	    Pokeshinto poke;

	    while(iter.hasNext()) {
            poke = iter.next();

            if (poke.getId().equals(pokeID)) {
                currentPokeshinto = poke;
                currentPoketurn = -1;
                return;
            }
        }

        throw new IllegalArgumentException("The pokeshinto " + pokeID + " is not equiped by the CombatAI.");

	}

	private void doMeditate(Combat combat) {
        if (!(this instanceof OpponentAI)) {
            combat.doOpponentCapture();
        }

        // When you meditate, you reset the Dizzy damage
        if (currentDamagedStatuses.get("Dizzy") >= statusTreshold.get("Dizzy")) {
            willResetDizzy = true;
        }
	}

	void doDamage(Combat combat, DamageHolder damageDealtByOther) {
        DamageHolder damageProtection = getDamageProtectionPercentage(combat);

        addDamageToDescriptor(damageDealtByOther, damageProtection);

        double physicalDamage = damageDealtByOther.get("Physical") + damageDealtToMe.get("Physical");
        double magicalDamage = damageDealtByOther.get("Magical") + damageDealtToMe.get("Magical");

        double totalPhysicalDamageDealt = reduceDamageWithProtection("Physical", physicalDamage, damageProtection);
        double totalMagicalDamageDealt = reduceDamageWithProtection("Magical", magicalDamage, damageProtection);
		
		// Do the damage
		currentHealth -= totalPhysicalDamageDealt;
		currentHealth -= totalMagicalDamageDealt;

        double stunDamage = damageDealtByOther.get("Stun") + damageDealtToMe.get("Stun");
        double painDamage = damageDealtByOther.get("Pain") + damageDealtToMe.get("Pain");
        double dizzyDamage = damageDealtByOther.get("Dizzy") + damageDealtToMe.get("Dizzy");
        double sealDamage = damageDealtByOther.get("Seal") + damageDealtToMe.get("Seal");

        currentDamagedStatuses.add("Stun", reduceDamageWithProtection("Stun", stunDamage, damageProtection));
        currentDamagedStatuses.add("Pain", reduceDamageWithProtection("Pain", painDamage, damageProtection));
        currentDamagedStatuses.add("Dizzy", reduceDamageWithProtection("Dizzy", dizzyDamage, damageProtection));
        currentDamagedStatuses.add("Seal", reduceDamageWithProtection("Seal", sealDamage, damageProtection));

        checkHealthIsOverZero();
	}

	private void addDamageToDescriptor(DamageHolder damageDealtByOther, DamageHolder damageProtection) {
        combatActionDescriptor.addDamageToMe(owner, "Physical", reduceDamageWithProtection("Physical", damageDealtByOther.get("Physical"), damageProtection));
        combatActionDescriptor.addDamageToMe(owner, "Magical", reduceDamageWithProtection("Magical", damageDealtByOther.get("Magical"), damageProtection));

        combatActionDescriptor.addDamageToMe(owner, "Stun", reduceDamageWithProtection("Stun", damageDealtByOther.get("Stun"), damageProtection));
        combatActionDescriptor.addDamageToMe(owner, "Pain", reduceDamageWithProtection("Pain", damageDealtByOther.get("Pain"), damageProtection));
        combatActionDescriptor.addDamageToMe(owner, "Dizzy", reduceDamageWithProtection("Dizzy", damageDealtByOther.get("Dizzy"), damageProtection));
        combatActionDescriptor.addDamageToMe(owner, "Seal", reduceDamageWithProtection("Seal", damageDealtByOther.get("Seal"), damageProtection));

        combatActionDescriptor.addDamageFromMe(owner, "Physical", reduceDamageWithProtection("Physical", damageDealtToMe.get("Physical"), damageProtection));
        combatActionDescriptor.addDamageFromMe(owner, "Magical", reduceDamageWithProtection("Magical", damageDealtToMe.get("Magical"), damageProtection));

        combatActionDescriptor.addDamageFromMe(owner, "Stun", reduceDamageWithProtection("Stun", damageDealtToMe.get("Stun"), damageProtection));
        combatActionDescriptor.addDamageFromMe(owner, "Pain", reduceDamageWithProtection("Pain", damageDealtToMe.get("Pain"), damageProtection));
        combatActionDescriptor.addDamageFromMe(owner, "Dizzy", reduceDamageWithProtection("Dizzy", damageDealtToMe.get("Dizzy"), damageProtection));
        combatActionDescriptor.addDamageFromMe(owner, "Seal", reduceDamageWithProtection("Seal", damageDealtToMe.get("Seal"), damageProtection));
    }

    private double reduceDamageWithProtection(String damageType, double damage, DamageHolder damageProtection) {
        return damage - damage * damageProtection.get(damageType);
    }

	private void checkHealthIsOverZero() {
        // Update health
        if (currentHealth < 0) {
            currentHealth = 0;
        }
    }

	private void doInstantDamage(Combat combat, String type, double amount) {
        DamageHolder damageProtection = getDamageProtectionPercentage(combat);

        switch (type) {
            case "True":
                currentHealth -= amount;
                break;

            case "Physical":
                currentHealth -= amount - amount * damageProtection.get("Physical");
                break;

            case "Magical":
                currentHealth -= amount - amount * damageProtection.get("Magical");
                break;

            case "Stun":
                currentDamagedStatuses.add("Stun", amount - amount * damageProtection.get("Stun"));
                break;

            case "Pain":
                currentDamagedStatuses.add("Pain", amount - amount * damageProtection.get("Pain"));
                break;

            case "Dizzy":
                currentDamagedStatuses.add("Dizzy", amount - amount * damageProtection.get("Dizzy"));
                break;

            case "Seal":
                currentDamagedStatuses.add("Seal", amount - amount * damageProtection.get("Seal"));
                break;
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
	private boolean checkRestriction(Combat combat, String type, String other) {
	    Iterator<Status> iter = allStatus.iterator();
	    Status current;

	    while(iter.hasNext()) {
	        current = iter.next();

	        if (current.isUsable(combat, owner)) {
                if (current instanceof StatusRestrict) {

                    if (type.equals("Skill")) {
                        if (type.equals(((StatusRestrict) current).getType())){

                            // Compare two elements
                            try {
                                Element otherElement = new Element(((StatusRestrict) current).getStat());
                                return otherElement.compareTo(currentPokeshinto.getSkill(other).getElement()) == 0;

                            } catch (IllegalArgumentException e) {

                            }
                        }

                    } else if (type.equals("Meditate")) {
                        return type.equals(((StatusRestrict) current).getType());

                    } else if (type.equals("Switch")) {
                        if (type.equals(((StatusRestrict) current).getType())) {
                            if (((StatusRestrict) current).getStat().equals("All")) {
                                return true;

                            } else {
                                return other.equals(((StatusRestrict) current).getStat());
                            }
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

    private void handleHeals(Combat combat, List<Heal> listOfHeals) {
        Iterator<Heal> iter = listOfHeals.iterator();

        while(iter.hasNext()) {
            giveHeal(combat, iter.next());
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

    private void giveHeal(Combat combat, Heal heal) {
        if (heal.getAffectsMe()){
            addHeal(combat, heal);

        } else {
            // The status targets the other. Who is the other?
            if (owner.equals("Opponent")){
                combat.addPlayerHeal(heal);
            } else {
                combat.addOpponentHeal(heal);
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

    void addHeal(Combat combat, Heal heal) {
	    double healAmount = heal.getTotalHeal(combat, owner);

	    currentHealth += healAmount;
	    damagedDealtByPain -= healAmount;

	    if (currentHealth > maxHealth) {
	        currentHealth = maxHealth;
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

        if (buff instanceof AttributeDebuffFlat) {
            permanentDebuffsAdded.add(new AttributeDebuffFlat(id, amount));
        } else {
            permanentBuffsAdded.add(new AttributeBuffFlat(id, amount));
        }

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
                throw new IllegalArgumentException(attribute + " is not handled in handleBuff.");
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

            } else if (currentTrigger instanceof TriggeredDispel) {
                TriggeredDispel dispel = (TriggeredDispel) currentTrigger;
                handleDispel(combat, dispel);
            }
        }
    }

    private void handleDispel(Combat combat, TriggeredDispel dispel) {
        if (dispel.getKey().equals("All")) {
            allStatus.clear();
            resetAllStatusIfIsOverTreshold();
            clearAllDebuffs(combat);
            return;
        }

        throw new IllegalArgumentException("The dispel's key " + dispel.getKey() + " doesn't exists.");
    }

    private void resetAllStatusIfIsOverTreshold() {
        if (currentDamagedStatuses.get("Dizzy") >= getStatusTreshold().get("Dizzy")) {
            willResetDizzy = true;
        }

        if (currentDamagedStatuses.get("Pain") >= getStatusTreshold().get("Pain")) {
            willResetPain = true;
        }

        if (currentDamagedStatuses.get("Stun") >= getStatusTreshold().get("Stun")) {
            willResetStun = true;
        }

        if (currentDamagedStatuses.get("Seal") >= getStatusTreshold().get("Seal")) {
            willResetSeal = true;
        }
    }

    private void clearAllDebuffs(Combat combat) {
        Iterator<AttributeDebuffFlat> iter = permanentDebuffsAdded.iterator();
        AttributeDebuffFlat currentDebuff;

        while(iter.hasNext()) {
            currentDebuff = iter.next();

            handleBuff(currentDebuff.getAttribute(), -currentDebuff.getAmount(combat, owner));
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
    int getNumberOfTurnsLeftBeforeCapture() {
        return capturedUntil;
    }

    /**
     * Ask the AI what is the decision
     */
    public Action<String> getDecision() {
        return choice;
    }

    /**
     * Try and do actions, and get the reason why player can't do that
     */
    protected boolean getSkillIsUsable(Combat combat, String id) {

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

    String getWhySkillCantBeDone(String id) {
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

    boolean getItemIsUsable(String id) {

        Item currentItem = getItem(id);

        if (currentItem instanceof Consumable) {
            return !((Consumable) currentItem).getIsUsed();
        }

        return true;
    }

    public void reset() {
        currentHealth = maxHealth;
        timeSpentByPokeshinto = new Dictionary();
        currentPoketurn = 0;

        allStatus = new LinkedList<>();
        allTriggers = new LinkedList<>();
        allBuffsToRemove = new LinkedList<>();
        permanentBuffsAdded = new LinkedList<>();
        permanentDebuffsAdded = new LinkedList<>();

        setNumberOfCaptured();

        statusTreshold = new DamageHolder(4, 4, 4, 4, 0, 0);
        currentDamagedStatuses = new DamageHolder();
        damageDealtToOther = new DamageHolder();
        damageDealtToMe  = new DamageHolder();
        damagedDealtByPain = 0;
        maxDamagePainDeals = 0;
    }

	/**
	 * Getters
	 */
	public DamageHolder getAllDamageDone() {
	    DamageHolder allDamage = new DamageHolder();

	    allDamage.add(damageDealtToOther);
	    allDamage.add(damageDealtToMe);

	    return allDamage;
    }
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
	public double getMissingHealth() {
	    return maxHealth - currentHealth;
	}
    public double getMissingHpPercentage() {
        return getCurrentHealth() / getMaxHealth();
    }

    public CombatAttributes getCombatAttributes() {
		return combatAttributes;
	}

    public double getStrength() {
	    if (getCurrentPokeshinto() != null) {
            return getCombatAttributes().getStrength() + getCurrentPokeshinto().getCombatAttributes().getStrength();
        }

        return getCombatAttributes().getStrength();
    }
    public double getFocus() {
        if (getCurrentPokeshinto() != null) {
            return getCombatAttributes().getFocus() + getCurrentPokeshinto().getCombatAttributes().getFocus();
        }

        return getCombatAttributes().getFocus();
    }
    public double getArmor() {
        if (getCurrentPokeshinto() != null) {
            return getCombatAttributes().getArmor() + getCurrentPokeshinto().getCombatAttributes().getArmor();
        }

        return getCombatAttributes().getArmor();
    }
    public double getAgility() {
        if (getCurrentPokeshinto() != null) {
            return getCombatAttributes().getAgility() + getCurrentPokeshinto().getCombatAttributes().getAgility();
        }

        return getCombatAttributes().getAgility();
    }

    public Dictionary getAllResistance() {
	    if (currentPokeshinto == null) {
	        return combatAttributes.getElementResistance();
        }

        Dictionary elemResist = currentPokeshinto.getCombatAttributes().getElementResistance();
	    Dictionary ownerResist = combatAttributes.getElementResistance();
	    int i = 0;

	    while(i < ownerResist.getSize()) {
	        elemResist.push(ownerResist.getKeyByIndex(i), ownerResist.getValueByIndex(i));
	        i++;
        }

        return elemResist;
    }
    public int getResistanceFrom(String element) {
	    Dictionary allRes = getAllResistance();
	    if (allRes.contains(element)) {
	        return allRes.getValueByKey(element);
        }

        return 0;
    }
    protected Pokeshinto getCurrentPokeshinto() {
		return currentPokeshinto;
	}
    public String getCurrentPokeshintoID() {
	    if (currentPokeshinto == null) {
	        return owner;
        }

        return currentPokeshinto.getId();
    }
    public String getCurrentPokeshintoDescription() {
        if (currentPokeshinto == null) {
            switch(owner) {
                case "Player":
                    return "adventurer";
                default:
                    return "Magicien";
            }
        }

        return currentPokeshinto.getDescription();
    }

    public Element getSkillAlignment() {
		return skillAlignment;
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
    protected int getNumberOfPokeshinto() {
	    return allPokeshintos.size();
    }
    List<Pokeshinto> getAllPokeshintos() {
	    return allPokeshintos;
    }
    public int getCurrentPoketurn() {
	    return currentPoketurn;
    }

    ItemBag getAllItems() {
	    return allItems;
    }

}
