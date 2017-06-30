package mainMenu;

import pokeshinto.Action;
import pokeshinto.Game;
import pokeshinto.Menu;


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
            // do nothing for now

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
            }
        }

        return false;
    }

    private void handleMenuAction(Action<String> decision) {
        if (decision.getKey().equals("Main")) {
            if (decision.getValue().equals("Quit")) {
                Game.isRunning = false;

            } else if (decision.getValue().equals("New Game")) {
                startGame = true;
            }
        }
    }

    /**
     * Called every frame
     */
    public void update() {

    }

    /**
     * Getter
     * @return if the game is ready to start
     */
    public boolean isStartGame() {
        return startGame;
    }
}
