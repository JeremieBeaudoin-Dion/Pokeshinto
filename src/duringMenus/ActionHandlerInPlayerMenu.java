package duringMenus;

import pokeshinto.Action;
import pokeshinto.Game;
import pokeshinto.Player;
import world.WorldMapCreator;

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

    private String savefilePath;
    private WorldMapCreator worldMap;

    public ActionHandlerInPlayerMenu(ObjectHandlerInPlayerMenu objectHandlerInPlayerMenu, WorldMapCreator worldMap, String filePath) {
        this.objectHandler = objectHandlerInPlayerMenu;

        backToGame = false;
        waitBeforeInput = Game.MAX_INPUT_WAIT_TIME;

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
                objectHandler.doMenuAction("Return");

                if (objectHandler.menuIsEmpty()) {
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

        waitBeforeInput = Game.MAX_INPUT_WAIT_TIME;

    }

    private void handleMenuAction(Action<String> menuAction) {
        if (menuAction.getKey().equals("Main")) {
            if (menuAction.getValue().equals("Quit")) {
                Game.isRunning = false;

            } else if (menuAction.getValue().equals("Save Game")) {
                saveGame();
                backToGame = true;

            } else if (menuAction.getValue().equals("Spirits")) {
                objectHandler.doMenuAction("Spirits");

            } else if (menuAction.getValue().equals("Items")) {
                objectHandler.doMenuAction("Items");
            }

        } else if (menuAction.getKey().equals("Spirits")) {
            if (menuAction.getValue().equals("Return")) {
                objectHandler.doMenuAction("Return");
            }

        } else if (menuAction.getKey().equals("Items")) {
            if (menuAction.getValue().equals("Return")) {
                objectHandler.doMenuAction("Return");
            }
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

    /**
     * Getters
     */
    public boolean isBackToGame() {
        return backToGame;
    }

    public void reset() {
        backToGame = false;
        waitBeforeInput = Game.MAX_INPUT_WAIT_TIME;
        objectHandler.reset();
    }
}
