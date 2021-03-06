package duringMenus;

import pokeshinto.Action;
import pokeshinto.Game;

/**
 * Handles actions when the player has lost the game.
 *
 * @author Jérémie Beaudoin-Dion
 */
public class ActionHandlerInGameOver {

    private int waitBeforeInput;
    private final int MAX_WAIT_VALUE = Game.MAX_INPUT_WAIT_TIME;

    private boolean startGame;

    private ObjectHandlerInGameOver objectHandler;

    public ActionHandlerInGameOver(ObjectHandlerInGameOver objectHandler) {
        this.objectHandler = objectHandler;

        waitBeforeInput = MAX_WAIT_VALUE;

        startGame = false;
    }

    /**
     * Handles player input
     */
    public void doInput(String myInput) {

        if (waitBeforeInput > 0) {
            return;
        }

        if(myInput.equals("Decide")){
            decide(objectHandler.getCurrentMenu());

        } else if(myInput.equals("Escape")){
            Game.isRunning = false;

        } else if (myInput.equals("Up")){
            objectHandler.getCurrentMenu().goBack();

        } else if (myInput.equals("Down")) {
            objectHandler.getCurrentMenu().goForward();
        }

        waitBeforeInput = MAX_WAIT_VALUE;

    }

    /**
     * Handles menu decisions
     */
    private void decide(Menu currentMenu) {
        Action<String> decision = currentMenu.decide();

        // If there is an action to do
        if (decision != null){
            handleMenuAction(decision);
        }
    }

    private void handleMenuAction(Action<String> decision) {
        if (decision.getValue().equals("Quit")) {
            Game.isRunning = false;

        } else if (decision.getValue().equals("Go back to last save")) {
            startGame = true;

        } else {
            throw new IllegalArgumentException("The menu value " + decision.getValue() + " is not valid.");
        }
    }

    /**
     * Called every frame
     */
    public void update() {
        if (waitBeforeInput >= 0) {
            waitBeforeInput--;
        }
    }

    public boolean isStartGame() {
        return startGame;
    }

    public void reset() {
        startGame = false;
        waitBeforeInput = MAX_WAIT_VALUE;
    }

}
