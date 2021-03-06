package combat;

import duringMenus.Menu;
import pokeshinto.Game;
import pokeshinto.Player;
import pokeshinto.Pokeshinto;

import java.util.HashMap;
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

    private CombatHandlerBefore beforeCombat;
    private CombatHandlerDuring duringCombat;
    private CombatHandlerAfter afterCombat;

	private String currentState;
	private boolean allIsOver;

    private int waitBeforeInput;
    private final int MAX_WAIT_VALUE = Game.MAX_INPUT_WAIT_TIME;

    public ActionHandlerInCombat() {
        waitBeforeInput = MAX_WAIT_VALUE;
    }

	/**
	 * Gets an input and handles it to the correct object
	 * 
	 * @param myInput: the String converted input from JFrame
	 */
	public void doInput(String myInput) {

	    if (waitBeforeInput > 0) {
	        return;
        }

	    if (currentState.equals("During")) {
	        duringCombat.doInput(myInput);

        } else if (currentState.equals("Before")) {
	        beforeCombat.doInput(myInput);

        } else if (currentState.equals("After")) {
            afterCombat.doInput(myInput);

        }

        waitBeforeInput = MAX_WAIT_VALUE;

    }
	
	/**
	 * Starts a combat with an opponent. Sets up the menu combat
	 *
	 * @param opponent: a combat AI with pokeShintos
	 */
	public void startCombat(CombatAI opponent) {
        allIsOver = false;

        currentState = "Before";

        beforeCombat = new CombatHandlerBefore(Player.getCombatPlayer(), opponent);

        if (beforeCombat.isStartCombat()) {
            startDuringCombat();
        }

	}

	private void startDuringCombat() {
        currentState = "During";
        duringCombat = new CombatHandlerDuring(beforeCombat.getPlayer(), beforeCombat.getOpponent());
        beforeCombat = null;
    }

    private void endDuringCombat() {
        currentState = "After";

        if (duringCombat.getCombatOutcome().equals("Win")) {
            afterCombat = new CombatHandlerAfter(duringCombat.getShintoLevelsBeforeXp());

        } else if (duringCombat.getCombatOutcome().equals("Captured")) {
            afterCombat = new CombatHandlerAfter(duringCombat.getNewPokeshinto());

        } else {
            allIsOver = true;
        }

        duringCombat = null;
    }

    private void endItAll() {
        allIsOver = true;
        afterCombat = null;
    }

    /**
     * Called every frame
     */
    public void update() {
        waitBeforeInput--;
    }

    /**
	 * Called every time there is an action
	 *
	 * @return true if the combat is OVER
	 */
	public boolean doAction() {

        if (currentState.equals("During")) {
            duringCombat.update();

            if (duringCombat.isCombatOver()) {
                endDuringCombat();
            }

        } else if (currentState.equals("Before")) {
            if (beforeCombat.isStartCombat()) {
                startDuringCombat();
            }

        } else if (currentState.equals("After")) {
            if (afterCombat.isAllOver()) {
                endItAll();
            }
        }

        return allIsOver;
	}

    /**
     * Getters
     */
	String getCurrentState() {
	    if (currentState.equals("During")) {
	        if (duringCombat.isInspectingOpponent()) {
	            return "Inspect";
            }
        }

	    return currentState;
    }

    Menu getCurrentMenu() {
        if (currentState.equals("During")) {
            return duringCombat.getCurrentMenu();

        } else if (currentState.equals("Before")) {
            return beforeCombat.getCurrentMenu();

        } else if (currentState.equals("After")) {
            return afterCombat.getCurrentMenu();
        }

        throw new IllegalArgumentException("The current state " + currentState + " does not exist.");
    }

    String getMenuDescription() {
        if (currentState.equals("Before")) {
            return beforeCombat.getMenuDescription();
        } else if (currentState.equals("After")) {
            return "";
        }

	    return duringCombat.getMenuDescription();
    }

    CombatAI getCombatPlayer() {
        if (currentState.equals("During")) {
            return duringCombat.getPlayer();

        }

        return beforeCombat.getPlayer();
    }

    CombatAI getCombatOpponent() {
        if (currentState.equals("During")) {
            return duringCombat.getOpponent();

        }

        return beforeCombat.getOpponent();
    }

    boolean getInputIsDoable(String value) {
	    if (currentState.equals("Before")) {
	        return true;
        } else if (currentState.equals("After")) {
	        return true;
        }

        return duringCombat.getInputIsDoable(value);
    }

    public boolean getOpponentSkillIsDoable(String id) {

	    if (currentState.equals("During")) {
	        return duringCombat.getOpponentSkillIsDoable(id);
        }

        return false;
    }

    String getWhyCurrentInputIsNotDoable() {
        return duringCombat.getWhyCurrentInputIsNotDoable();
    }

    HashMap<String, Double> getLevelAdvancement() {
	    return afterCombat.getLevelsToShow();
    }

    List<Pokeshinto> getNewPokeshinto() {
	    List<Pokeshinto> list = new LinkedList<>();

	    list.add(afterCombat.getCapturedPokeshinto());

	    return list;
    }

    String getCombatOutcome() {
	    return afterCombat.getOutcome();
    }

    int getElementWarOutcome() {
	    if (duringCombat == null) {
	        return 0;
        }

	    return duringCombat.getElementWarOutcome();
    }

}
