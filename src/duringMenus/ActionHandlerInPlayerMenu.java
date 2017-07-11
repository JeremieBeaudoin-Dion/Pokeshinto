package duringMenus;

import pokeshinto.Action;
import pokeshinto.Game;
import pokeshinto.Player;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

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

    private String savefilePath;

    public ActionHandlerInPlayerMenu(ObjectHandlerInPlayerMenu objectHandlerInPlayerMenu, String filePath) {
        this.objectHandler = objectHandlerInPlayerMenu;

        backToGame = false;
        waitBeforeInput = MAX_WAIT_VALUE;

        savefilePath = filePath;
    }

    public void doInput(String myInput) {

        if (waitBeforeInput > 0) {
            return;
        }

        switch (myInput) {
            case "Decide":
                Action<String> menuAction = objectHandler.getCurrentMenu().decide();
                handleMenuAction(menuAction);
                break;

            case "Escape":
                if (objectHandler.doMenuAction("Return")) {
                    backToGame = true;
                }
                break;

            case "Up":
                objectHandler.getCurrentMenu().goBack();
                break;

            case "Down":
                objectHandler.getCurrentMenu().goForward();
                break;
        }

        waitBeforeInput = MAX_WAIT_VALUE;

    }

    private void handleMenuAction(Action<String> menuAction) {
        if (menuAction.getKey().equals("Main")) {
            if (menuAction.getValue().equals("Quit")) {
                Game.isRunning = false;

            } else if (menuAction.getValue().equals("Save Game")) {
                saveGame();
                backToGame = true;
            }
        }
    }

    private void saveGame() {
        try {
            FileOutputStream fileOut = new FileOutputStream(savefilePath);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);

            out.writeObject(Player.getPlayerInformation());

            out.close();
            fileOut.close();

        } catch(IOException i) {
            i.printStackTrace();
        }
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
