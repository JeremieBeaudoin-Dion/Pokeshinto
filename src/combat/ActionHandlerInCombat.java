package combat;

import pokeshinto.Action;
import pokeshinto.InfoHandler;
import pokeshinto.Menu;
import pokeshinto.Player;
import pokeshinto.Pokeshinto;
import pokeshinto.StackLinked;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * The ActionHandler is the main LOGIC component of the combat. It 
 * gets player input, sends it to the correct object, and holds 
 * most of the game objects.
 * 
 * @author Jérémie Beaudoin-Dion
 *
 */
public class ActionHandlerInCombat {

    // TODO: Cleanup ActionHandlerInCombat
	
	private Combat combat;
	private Menu currentMenu;
	private StackLinked<Menu> allMenus;
	
	private boolean combatOver;
	private boolean combatAboutToBeOver;
	
	/**
	 * Getter
	 * 
	 * @return: The current menu
	 */
	public Menu getCurrentMenu() {
		return currentMenu;
	}
	
	/**
	 * Get the combat value for 
	 */
	public Combat getCombat() {
		return combat;
	}

	/**
	 * Gets an input and handles it to the correct object
	 * 
	 * @param myInput: the String converted input from JFrame
	 */
	public void doInput(String myInput) {
	    if (combatAboutToBeOver) {
            combatOver = true;
            return;
        }
		
		if(myInput.equals("Left")){
			currentMenu.goBack();
			
		} else if(myInput.equals("Right")){
			currentMenu.goForward();
			
		} else if(myInput.equals("Decide")){
			
			Action<String> decision = currentMenu.decide();
			
			// If there is an action to do
			if (decision != null){
				if (getInputIsDoable(currentMenu.getSelected())){
					handleMenuAction(decision);
				}
			}
			
		} else if(myInput.equals("Escape")){
			
			doMenuReset();
		}
		
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
	 * @param action
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
		}
	}
	
	/**
	 * Handles the ROOTMENU actions.
	 * @param action
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
	 * Pops the next menu in the Stack
	 */
	private void doMenuReset(){
		if(!allMenus.isEmpty()){
			currentMenu = allMenus.pop();
		}
	}
	
	/**
	 * Starts a combat with an opponent. Sets up the menu combat
	 *
	 * @param opponent: a combat AI with pokeShintos
	 */
	public void startCombat(CombatAI opponent) {
		combatOver = false;
        combatAboutToBeOver = false;
		allMenus = new StackLinked<>();
		combat = new Combat(Player.getCombatPlayer(), opponent);
		currentMenu = InfoHandler.getCombatMenu("Start Combat");
		newTurn();
	}
	
	/**
	 * Sends an allert to the current menu
	 * 
	 * @param allert: the String to show as an allert
	 */
	private void sendAllertToMenu(String allert){
		currentMenu.setAllert(allert);
	}

	public boolean getCombatIsAlmostDone() {
	    return combatAboutToBeOver;
    }
	
	/**
	 * Called every time there is an action
	 *
	 * @return true if the combat is OVER
	 */
	public boolean update() {
	    if (!combatAboutToBeOver) {
            if(combat.update()) {
                endTurn();
                newTurn();
            }
        }
		
		return combatOver;  //TODO: seperate this
	}
	
	/**
	 * Ends the turn and creates a new menu
	 */
	private void endTurn() {
        String outcome = combat.endTurn();
        if (outcome != null) {
            endCombat(outcome);
        }

        allMenus = new StackLinked<>();
        currentMenu = InfoHandler.getCombatMenu("New Turn");
        currentMenu.setAllert(getCombatDescription());
	}
	
	/**
	 * Starts a new turn and 
	 */
	private void newTurn() {
        combat.newTurn();
	}	
	
	/**
	 * Ends the current combat and updates the Player
	 */
	private void endCombat(String outcome) {
		// End the combat
        combatAboutToBeOver = true;

		Player.health = combat.getPlayerCurrentHp();
		Player.resetAllPokeshintos();
		
		if (outcome.equals("Captured")) {
			Player.addNewPokeshinto(combat.getOpponentPokeshinto());
		}

		if (outcome.equals("Win")) {
            Player.levelUpAllShintos(combat.getPlayerTimeSpendByPokeshinto(), combat.getOpponentTotalLevel(),
                    combat.getTurnNumber());
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

		} else if (playerDecision.getKey().equals("Switch")) {
			combatDescription += "switched to " + combat.getOpponentPokeshinto().getId();

		} else if (playerDecision.getKey().equals("Stun")) {
			combatDescription += " is stunned and can't move.";

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
	
}
