package duringMenus;

import pokeshinto.Action;
import pokeshinto.Game;
import pokeshinto.Player;
import world.WorldMapCreator;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Handles player input during world menus
 *
 * @author Jérémie Beaudoin-Dion
 */
public class ActionHandlerInWorldMenu {

    private ObjectHandlerInWorldMenu objectHandler;

    private int waitBeforeInput;
    private final int MAX_WAIT_VALUE = 10;

    private String savefilePath;
    private WorldMapCreator worldMap;

    /**
     * Constructor
     */
    public ActionHandlerInWorldMenu(ObjectHandlerInWorldMenu objectHandlerInWorldMenu, WorldMapCreator worldMap, String filePath) {
        this.objectHandler = objectHandlerInWorldMenu;

        waitBeforeInput = MAX_WAIT_VALUE;

        savefilePath = filePath;
        this.worldMap = worldMap;
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
                objectHandler.doMenuAction(new Action<>("Return", "Return"));
                break;

            case "Up":
                objectHandler.getCurrentMenu().goBack();
                break;

            case "Down":
                objectHandler.getCurrentMenu().goForward();
                break;

            case "Left":
                objectHandler.getCurrentMenu().goBack();
                break;

            case "Right":
                objectHandler.getCurrentMenu().goForward();
                break;
        }

        waitBeforeInput = MAX_WAIT_VALUE;

    }

    private void handleMenuAction(Action<String> menuAction) {
        if (menuAction.getValue().equals("Save Game")) {
            saveGame();

        } else {
            objectHandler.doMenuAction(menuAction);
        }
    }

    private void saveGame() {
        try {
            FileOutputStream fileOut = new FileOutputStream(savefilePath);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);

            out.writeObject(Player.getPlayerInformation());
            out.writeObject(worldMap);

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

    public void reset() {
        waitBeforeInput = MAX_WAIT_VALUE;
        objectHandler.reset();
    }

    /**
     * Getters
     */
    public boolean isBackToGame() {
        return objectHandler.isEmpty();
    }

}
