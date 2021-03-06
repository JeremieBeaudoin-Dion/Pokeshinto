package duringMenus;

import pokeshinto.Action;
import pokeshinto.Game;
import pokeshinto.Player;
import world.WorldMapCreator;
import world.items.Item;

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
    private final int MAX_WAIT_VALUE = Game.MAX_INPUT_WAIT_TIME;

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
                if (menuAction != null) {
                    handleMenuAction(menuAction);
                }
                break;

            case "Escape":
                objectHandler.doMenuAction(new Action<>(ObjectHandlerInWorldMenu.RETURN_ACTION_ID, ObjectHandlerInWorldMenu.RETURN_ACTION_ID));
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
        // Return has priority over other actions
        if (menuAction.getValue().equals(ObjectHandlerInWorldMenu.RETURN_ACTION_ID)) {
            objectHandler.doMenuAction(menuAction);
            return;
        }

        // Any other action
        if (menuAction.getKey().equals(ObjectHandlerInWorldMenu.MENU_SHOP_BUY_ID)) {
            handleBuyMenu(menuAction.getValue());

        } else if (menuAction.getValue().equals(ObjectHandlerInWorldMenu.REST_ACTION_ID)) {
            handleRestAction();

        } else {
            objectHandler.doMenuAction(menuAction);
        }

        // to save game
        /*
        if (menuAction.getValue().equals("Save Game")) {
            saveGame();

        } else
         */
    }

    private void handleBuyMenu(String itemID) {
        Item itemToBuy = Item.getNewItemFromId(itemID);

        if (Player.money >= itemToBuy.getValue()) {
            Player.allItems.add(itemToBuy);
            Player.money -= itemToBuy.getValue();
            objectHandler.doMenuAction(new Action<>(ObjectHandlerInWorldMenu.ALLERT_ACTION_ID, "Bought " + itemID));
        }
    }

    private void handleRestAction() {
        Player.health = Player.maxHealth;
        if (saveGame()) {
            objectHandler.doMenuAction(new Action<>(ObjectHandlerInWorldMenu.ALLERT_ACTION_ID, "Succesfully saved the game"));
        } else {
            objectHandler.doMenuAction(new Action<>(ObjectHandlerInWorldMenu.ALLERT_ACTION_ID, "Oops, something went wrong..."));
        }

    }

    private boolean saveGame() {
        try {
            FileOutputStream fileOut = new FileOutputStream(savefilePath);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);

            out.writeObject(Player.getPlayerInformation());
            out.writeObject(worldMap);

            out.close();
            fileOut.close();

            return true;

        } catch(IOException i) {
            i.printStackTrace();
            return false;
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
