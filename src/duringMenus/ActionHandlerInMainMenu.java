package duringMenus;

import combat.ActionDescriptor;
import pokeshinto.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;


/**
 * Handles input and objects for the main menu.
 *
 * @author Jérémie Beaudoin-Dion
 */
public class ActionHandlerInMainMenu {

    private boolean startGame;

    private ObjectHandlerInMainMenu objectHandler;

    private int waitBeforeInput;
    private final int MAX_WAIT_VALUE = 10;

    private Player[] allPlayers;

    /**
     * Constructor
     */
    public ActionHandlerInMainMenu(ObjectHandlerInMainMenu objectHandler) {
        this.objectHandler = objectHandler;
        startGame = false;

        waitBeforeInput = MAX_WAIT_VALUE;
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
            objectHandler.doMenuAction("Return");

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
        if (decision.getKey().equals("Main")) {
            handleMainMenuAction(decision.getValue());

        } else if (decision.getKey().equals("Play")) {
            handlePlayMenuAction(decision.getValue());

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

        } else if (action.equals("Play")) {
            objectHandler.doMenuAction(action);

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

    private void handlePlayMenuAction(String action) {
        if (action.equals("Return")) {
            objectHandler.doMenuAction(action);

        } else {
            String filePath = "Saves/" + action + ".ser";
            PlayerInformation currentPlayer = null;

            try {
                currentPlayer = loadPlayer(filePath);

            } catch (IOException e1) {
                currentPlayer = new PlayerInformation();

            } catch (ClassNotFoundException e2) {
                getRidOfFaultySavedFile(filePath);
                currentPlayer = new PlayerInformation();

            } finally {
                objectHandler.setGamePlayer(currentPlayer);
                objectHandler.setSavedFileName(filePath);
                startGame = true;

            }
        }
    }

    private PlayerInformation loadPlayer(String savedFile) throws IOException, ClassNotFoundException {
        FileInputStream file = new FileInputStream(savedFile);
        ObjectInputStream saved = new ObjectInputStream(file);

        PlayerInformation thisPlayer = (PlayerInformation) saved.readObject();

        saved.close();
        file.close();
        return thisPlayer;
    }

    private void getRidOfFaultySavedFile(String savedFile) {
        try {
            Files.deleteIfExists(Paths.get(savedFile));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    /**
     * Called every frame
     */
    public void update() {
        waitBeforeInput--;
    }

    /**
     * Getter
     * @return if the game is ready to start
     */
    public boolean isStartGame() {
        return startGame;
    }
}
