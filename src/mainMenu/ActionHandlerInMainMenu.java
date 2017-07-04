package mainMenu;

import combat.ActionDescriptor;
import pokeshinto.Action;
import pokeshinto.Game;
import pokeshinto.Menu;
import pokeshinto.Player;


/**
 * Handles input and objects for the main menu.
 *
 * @author Jérémie Beaudoin-Dion
 */
public class ActionHandlerInMainMenu {

    private boolean startGame;

    private ObjectHandlerInMainMenu objectHandler;

    /**
     * Constructor
     */
    public ActionHandlerInMainMenu(ObjectHandlerInMainMenu objectHandler) {
        this.objectHandler = objectHandler;
        startGame = false;

    }

    /**
     * Handles player input
     */
    public void doInput(String myInput) {

        if(myInput.equals("Decide")){
            decide(objectHandler.getCurrentMenu());

        } else if(myInput.equals("Escape")){
            objectHandler.doMenuAction("Return");

        } else if (myInput.equals("Up")){
            objectHandler.getCurrentMenu().goBack();

        } else if (myInput.equals("Down")) {
            objectHandler.getCurrentMenu().goForward();
        }

    }

    /**
     * Handles menu decisions
     */
    private void decide(Menu currentMenu) {
        Action<String> decision = currentMenu.decide();

        // If there is an action to do
        if (decision != null){
            if (getInputIsDoable(decision)){
                handleMenuAction(decision);
            }
        }
    }

    private boolean getInputIsDoable(Action<String> decision) {
        if (decision.getKey().equals("Main")) {
            if (decision.getValue().equals("New Game")) {
                return true;

            } else if (decision.getValue().equals("Quit")) {
                return true;

            } else if (decision.getValue().equals("Options")) {
                return true;

            }  else if (decision.getValue().equals("Credits")) {
                return true;
            }

        } else {
            return true;
        }

        return false;
    }

    private void handleMenuAction(Action<String> decision) {
        if (decision.getKey().equals("Main")) {
            handleMainMenuAction(decision.getValue());

        } else if (decision.getKey().equals("Options")) {
            handleOptionMenuAction(decision.getValue());

        } else if (decision.getKey().equals("Credits")) {
            objectHandler.doMenuAction(decision.getValue());

        } else {
            throw new IllegalArgumentException("The menu key " + decision.getKey() + " is not valid.");
        }
    }

    private void handleMainMenuAction(String action) {
        if (action.equals("Quit")) {
            Game.isRunning = false;

        } else if (action.equals("New Game")) {
            startGame = true;

        } else if (action.equals("Options")) {
            objectHandler.doMenuAction(action);

        } else if (action.equals("Credits")) {
            objectHandler.doMenuAction(action);
        }
    }

    private void handleOptionMenuAction(String action) {
        if (action.equals("Return")) {
            objectHandler.doMenuAction(action);

        } else if (action.equals("Description Level")) {
            if (Player.descriptionComplexity == ActionDescriptor.DESCRIPTION_COMPLEXITY_NORMAL) {
                Player.descriptionComplexity = ActionDescriptor.DESCRIPTION_COMPLEXITY_LESS;

            } else {
                Player.descriptionComplexity = ActionDescriptor.DESCRIPTION_COMPLEXITY_NORMAL;
            }

        } else if (action.equals("Approximate Damage")) {
            if (Player.approximate) {
                Player.approximate = false;

            } else {
                Player.approximate = true;
            }

        } else {
            throw new IllegalArgumentException("The menu value " + action + " is not valid.");
        }
    }

    /**
     * Called every frame
     */
    public void update() {
        // do nothing
    }

    /**
     * Getter
     * @return if the game is ready to start
     */
    public boolean isStartGame() {
        return startGame;
    }
}
