package duringMenus;

import combat.ActionDescriptor;
import pokeshinto.*;
import world.WorldMapCreator;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


/**
 * Handles input and objects for the main menu.
 *
 * @author Jérémie Beaudoin-Dion
 */
public class ActionHandlerInMainMenu {

    private boolean startGame;

    private ObjectHandlerInMainMenu objectHandler;

    private int waitBeforeInput;
    private final int MAX_WAIT_VALUE = Game.MAX_INPUT_WAIT_TIME;

    /**
     * Constructor
     */
    public ActionHandlerInMainMenu(ObjectHandlerInMainMenu objectHandler) {
        this.objectHandler = objectHandler;
        startGame = false;

        waitBeforeInput = MAX_WAIT_VALUE;

        setObjectHandler();
    }

    private void setObjectHandler() {
        String filePath;
        PlayerInformation currentPlayer = null;
        WorldMapCreator worldMap = null;

        int playerImage = -1;

        for(int i=1; i<4; i++) {
            try {
                filePath = "Saves/Game " + i + ".ser";
                List<Object> allObjects = loadGame(filePath);

                currentPlayer = (PlayerInformation) allObjects.get(0);

                playerImage = currentPlayer.getPlayerCharNumber();

            } catch (IOException|ClassNotFoundException e) {
                playerImage = -1;

            } finally {
                objectHandler.setPlayerImage(i, playerImage);
            }
        }

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

        } else if (decision.getKey().equals("New Game")) {
            handleNewGameMenuAction(decision.getValue());

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
            WorldMapCreator worldMap = null;

            try {
                List<Object> allObjects = loadGame(filePath);

                currentPlayer = (PlayerInformation) allObjects.get(0);
                worldMap = (WorldMapCreator) allObjects.get(1);

                startGame = true;

            } catch (IOException e1) {
                System.out.println("Created new save file.");

                worldMap = new WorldMapCreator();

                objectHandler.doMenuAction("New Game");

            } catch (ClassNotFoundException e2) {
                System.out.println("Deleted old save file.");

                getRidOfFaultySavedFile(filePath);
                worldMap = new WorldMapCreator();

                objectHandler.doMenuAction("New Game");

            } finally {
                objectHandler.setGamePlayer(currentPlayer);
                objectHandler.setSavedFileName(filePath);
                objectHandler.setWorldMap(worldMap);
            }
        }
    }

    private void handleNewGameMenuAction(String action) {
        if (action.equals("Return")) {
            objectHandler.doMenuAction(action);

        } else if (action.equals("Player Image")) {
            Player.playerCharNumber++;
            Player.playerCharNumber %= Player.NUMBER_OF_PLAYER_IMAGES;

        } else if (action.equals("Start Game")) {

            objectHandler.setGamePlayer(new PlayerInformation(Player.playerCharNumber));
            startGame = true;

        } else {
            throw new IllegalArgumentException("The menu value " + action + " is not valid.");
        }
    }

    private List<Object> loadGame(String savedFile) throws IOException, ClassNotFoundException {
        ArrayList<Object> allObjectsToLoad = new ArrayList<>(2);

        FileInputStream file = new FileInputStream(savedFile);
        ObjectInputStream saved = new ObjectInputStream(file);

        allObjectsToLoad.add(saved.readObject());
        allObjectsToLoad.add(saved.readObject());

        saved.close();
        file.close();

        return allObjectsToLoad;
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
