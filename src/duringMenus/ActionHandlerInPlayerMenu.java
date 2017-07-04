package duringMenus;

/**
 * Handles player input during player menu
 *
 * @author Jérémie Beaudoin-Dion
 */
public class ActionHandlerInPlayerMenu {

    private ObjectHandlerInPlayerMenu objectHandler;
    private boolean backToGame;

    private int waitBeforeInput;
    private final int MAX_WAIT_VALUE = 10;

    public ActionHandlerInPlayerMenu(ObjectHandlerInPlayerMenu objectHandlerInPlayerMenu) {
        this.objectHandler = objectHandlerInPlayerMenu;

        backToGame = false;
        waitBeforeInput = MAX_WAIT_VALUE;
    }

    public void doInput(String myInput) {

        if (waitBeforeInput > 0) {
            return;
        }

        if(myInput.equals("Decide")){

        } else if(myInput.equals("Escape")){
            if (objectHandler.doMenuAction("Return")) {
                backToGame = true;
            }

        } else if (myInput.equals("Up")){
            objectHandler.getCurrentMenu().goBack();

        } else if (myInput.equals("Down")) {
            objectHandler.getCurrentMenu().goForward();
        }

        waitBeforeInput = MAX_WAIT_VALUE;

    }

    /**
     * Called every frame
     */
    public void update() {
        waitBeforeInput--;
    }

    /**
     * Getters
     */
    public boolean isBackToGame() {
        return backToGame;
    }

    public void resetBackToGame() {
        backToGame = false;
        waitBeforeInput = MAX_WAIT_VALUE;
    }
}
