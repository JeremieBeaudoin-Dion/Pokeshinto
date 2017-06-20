package combat;

import pokeshinto.Action;
import pokeshinto.Menu;
import pokeshinto.Player;
import pokeshinto.Pokeshinto;

import java.util.*;

/**
 * Handles the combat actions
 *
 * @author Jérémie Beaudoin-Dion
 */
public class CombatHandlerDuring {

    private Combat combat;
    private Menu currentMenu;
    private Stack<Menu> allMenus;
    private boolean combatOver;

    private HashMap<String, Double> allLevels;
    private String combatOutcome;
    private Pokeshinto newPokeshinto;


    /**
     * Constructor
     */
    public CombatHandlerDuring(PlayerCopy player, CombatAI opponent) {
        combat = new Combat(player, opponent);

        combatOver = false;

        allMenus = new Stack<>();
        currentMenu = InfoHandler.getCombatMenu("Start Combat");

        newTurn();
    }

    /**
     * Called every time there is an action from the player
     * @param myInput
     */
    public void doInput(String myInput) {
        if(myInput.equals("Left")){
            currentMenu.goBack();

        } else if(myInput.equals("Right")){
            currentMenu.goForward();

        } else if(myInput.equals("Decide")){
            decide();

        } else if(myInput.equals("Escape")){
            doMenuReset();
        }

    }

    private void decide() {
        Action<String> decision = currentMenu.decide();

        // If there is an action to do
        if (decision != null){
            if (getInputIsDoable(currentMenu.getSelected())){
                handleMenuAction(decision);
            }
        }
    }

    /**
     * Called every frame
     */
    void update() {
        if(combat.update()) {
            endTurn();
            if (!combatOver) {
                newTurn();
            }
        }
    }

    /**
     * Ends the turn and creates a new menu
     */
    private void endTurn() {
        String outcome = combat.endTurn();
        if (outcome == null) {
            allMenus = new Stack<>();
            currentMenu = InfoHandler.getCombatMenu("New Turn");
            currentMenu.setAllert(getCombatDescription());

        } else {
            endCombat(outcome);
        }

    }

    /**
     * Ends the current combat and updates the Player
     */
    private void endCombat(String outcome) {
        // End the combat
        combatOver = true;
        combatOutcome = outcome;

        Player.health = combat.getPlayerCurrentHp();
        Player.resetAllPokeshintos();

        if (outcome.equals("Captured")) {
            newPokeshinto = combat.getOpponentPokeshinto();
            Player.addNewPokeshinto(newPokeshinto);
        }

        if (outcome.equals("Win")) {
            setUpAllLevels();

            Player.levelUpAllShintos(combat.getPlayerTimeSpendByPokeshinto(), combat.getOpponentTotalLevel(),
                    combat.getTurnNumber());
        }

        // Scrubbing
        combat = null;
    }

    private void setUpAllLevels() {
        List<Pokeshinto> allShintos = Player.getAllShintos();
        Iterator<Pokeshinto> iter = allShintos.iterator();
        Pokeshinto current;
        allLevels = new HashMap<>();

        while(iter.hasNext()) {
            current = iter.next();

            allLevels.put(current.getId(), current.getLevelCompletionPercentage());
        }
    }

    /**
     * Starts a new turn and
     */
    private void newTurn() {
        combat.newTurn();
    }

    /**
     * Returns if the menu action is doable according to the Combat class
     */
    public boolean getInputIsDoable(String value) {

        Action<String> action = new Action<>(currentMenu.getID(), value);

        return combat.getPlayerActionIsDoable(action);
    }

    public String getWhyCurrentInputIsNotDoable() {
        return combat.getWhyPlayerCantDoThat();
    }

    /**
     * Handles the Menu Actions and sends them to the right method
     */
    private void handleMenuAction(Action<String> action) {
        if (action.getKey().equals("Root")){
            handleRootMenuAction(action.getValue());

        } else if (action.getKey().equals("Skill")){
            combat.setPlayerDecision(new Action<String>("Skill", currentMenu.getSelected()));
            doMenuReset();

        } else if (action.getKey().equals("Switch")){
            combat.setPlayerDecision(new Action<String>("Switch", currentMenu.getSelected()));
            doMenuReset();

        } else if (action.getKey().equals("Choose Pokeshinto")) {
            combat.setPlayerDecision(new Action<String>("Choose", currentMenu.getSelected()));
            doMenuReset();
        }
    }

    /**
     * Pops the next menu in the Stack
     */
    private void doMenuReset(){
        if(!allMenus.isEmpty()){
            currentMenu = allMenus.pop();
        }
    }

    /**
     * Handles the ROOTMENU actions.
     */
    private void handleRootMenuAction(String action){
        // Basic menu actions
        if (action.equals("Skill")){
            if (combat.getPlayerPokeshinto() == null) {
                return;
            }

            List<Skill> equipedSkills = combat.getPlayerPokeshinto().getEquipedSkills();
            List<String> buttons = new LinkedList<>(); //new String[equipedSkills.length];

            Iterator<Skill> iter = equipedSkills.iterator();

            while (iter.hasNext()) {
                buttons.add(iter.next().getId());
            }

            allMenus.push(currentMenu);
            currentMenu = new Menu("Skill", buttons, null);

        } else if (action.equals("Switch")){
            if (combat.getPlayerPokeshinto() == null || Player.allShintos.size() <= 1) {
                return;
            }

            List<Pokeshinto> equipedPokeshinto = Player.allShintos;
            List<String> buttons = new LinkedList<>();  //new String[equipedPokeshinto.size() - 1];
            Iterator<Pokeshinto> iter = equipedPokeshinto.iterator();

            while(iter.hasNext()) {
                Pokeshinto current = iter.next();

                if (!current.getId().equals(combat.getPlayerPokeshinto().getId())){
                    buttons.add(current.getId());
                }
            }

            allMenus.push(currentMenu);
            currentMenu = new Menu("Switch", buttons, null);

        } else if (action.equals("Meditate")){
            combat.setPlayerDecision(new Action<String>(action, null));
            doMenuReset();
        }
    }

    /**
     * Depending on the outcome of the combat, returns a string with a description
     * of what happenned.
     */
    private String getCombatDescription() {
        Action<String> opponentDecision = combat.getOpponentChoice();
        Action<String> playerDecision = combat.getPlayerChoice();
        String combatDescription = "Opponent ";

        if (opponentDecision.getKey().equals("Meditate")){
            combatDescription += "meditated";

        } else if (opponentDecision.getKey().equals("Skill")) {
            combatDescription += "used " + opponentDecision.getValue();
            combatDescription += getDamageDescription(combat.getAllOpponentDamageDealt());

        } else if (opponentDecision.getKey().equals("Switch")) {
            combatDescription += "switched to " + combat.getOpponentPokeshinto().getId();

        } else if (opponentDecision.getKey().equals("Stun")) {
            combatDescription += " is stunned and can't move.";

        } else if (opponentDecision.getKey().equals("Choose")) {
            combatDescription += " chose " + combat.getOpponentPokeshinto().getId();
        }

        combatDescription += "\n";
        combatDescription += "You ";

        if (playerDecision.getKey().equals("Meditate")){
            combatDescription += "meditated";

        } else if (playerDecision.getKey().equals("Skill")) {
            combatDescription += "used " + playerDecision.getValue();
            combatDescription += getDamageDescription(combat.getAllPlayerDamageDealt());

        } else if (playerDecision.getKey().equals("Switch")) {
            combatDescription += "switched to " + combat.getPlayerPokeshinto().getId();

        } else if (playerDecision.getKey().equals("Stun")) {
            combatDescription += " are stunned and can't move.";

        } else if (playerDecision.getKey().equals("Choose")) {
            combatDescription += " chose " + combat.getPlayerPokeshinto().getId();
        }

        return combatDescription;
    }

    private String getDamageDescription(DamageHolder damage) {
        int numberOfDamages = 0;
        String description = "";

        if (damage.get("Physical") > 0) {
            description = " for ";
            description += (int) damage.get("Physical") + " physical damage ";
            numberOfDamages++;
        }

        if (damage.get("Magical") > 0) {
            if (numberOfDamages == 0) {
                description = " for ";
            }
            description += (int) damage.get("Magical") + " magical damage ";
            numberOfDamages++;
        }

        if (damage.get("Stun") > 0) {
            if (numberOfDamages == 0) {
                description = " for ";
            }
            description += (int) damage.get("Stun") + " stun damage ";
            numberOfDamages++;
        }

        if (damage.get("Pain") > 0) {
            if (numberOfDamages == 0) {
                description = " for ";
            }
            description += (int) damage.get("Pain") + " pain damage ";
            numberOfDamages++;
        }

        if (damage.get("Dizzy") > 0) {
            if (numberOfDamages == 0) {
                description = " for ";
            }
            description += (int) damage.get("Dizzy") + " dizzy damage ";
            numberOfDamages++;
        }

        if (damage.get("Seal") > 0) {
            if (numberOfDamages == 0) {
                description = " for ";
            }
            description += (int) damage.get("Seal") + " seal damage ";
        }

        return description;
    }

    /**
     * @return a description of the desired combat information
     */
    String getMenuDescription() {

        if (currentMenu.getID() == "Root") {
            if (combat.getPlayer().getCurrentPokeshinto() == null) {
                switch (currentMenu.getSelected()) {
                    case "Skill":
                        return "Use one of your pokeshinto's skill against your opponent.";

                    case "Meditate":
                        return "Regain your focus and capture enemy pokeshinto if possible.";

                    case "Switch":
                        return "Switch your current pokeshinto with another.";

                    case "Items":
                        return "Use an item in combat.";
                }
            } else {
                switch (combat.compareAgilities()) {
                    case -1:
                        return "Your opponent will know your next move.";

                    case 0:
                        return "None of you will know the opponent's next move.";

                    case 1:
                        return getOpponentDecisionDescription();
                }

            }
        }

        // else
        return InfoHandler.getSkillInformation(currentMenu.getSelected());
    }

    private String getOpponentDecisionDescription() {
        String description = "Your opponent is about to use ";

        CombatAI opponent = combat.getOpponent();

        if (opponent.getDecision() == null) {
            throw new NullPointerException("The opponent hasn't decided on a skill yet.");
        }

        if (opponent.getDecision().getKey().equals("Skill")) {
            description += "the skill ";
            description += opponent.getDecision().getValue();
        } else if (opponent.getDecision().getKey().equals("Skill")){
            description = "Your opponent is stunned and can't move this turn.";

        } else {
            description += opponent.getDecision().getKey();
        }

        return description;

    }

    /**
     * Getters
     */
    Menu getCurrentMenu(){
        return currentMenu;
    }

    CombatAI getPlayer() {
        return combat.getPlayer();
    }

    CombatAI getOpponent() {
        return combat.getOpponent();
    }

    Combat getCombat() {
        return combat;
    }

    boolean isCombatOver() {
        return combatOver;
    }

    String getCombatOutcome() {
        return combatOutcome;
    }

    HashMap<String, Double> getShintoLevelsBeforeXp() {
        return allLevels;
    }

    Pokeshinto getNewPokeshinto() {
        return newPokeshinto;
    }

}
