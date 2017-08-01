package Helper;

import duringMenus.*;
import images.Camera;
import images.ImageHandler;
import images.Position;
import pokeshinto.InputHandler;
import pokeshinto.Player;
import pokeshinto.PlayerInformation;
import world.ActionHandlerInWorld;
import world.ObjectHandlerInWorld;
import world.WorldMapArrayData;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.Stack;
import java.util.Timer;

/**
 * The Main class for the game
 * This class handles game flow
 *
 * @author Jérémie Beaudoin-Dion
 */
public class VillagerTest extends java.util.TimerTask {

    PlayerInformation gamePlayer = new PlayerInformation(false, 0, 100, 100,
            null, null, null, null, "Down", new Position(200, 200),
            WorldMapArrayData.FIRST_CITY_MAP_ID, 0);


    /**
     * Code starts here
     */

    public final static int GAMESTATE_WORLD = 0;
    public final static int GAMESTATE_MAINMENU = 3;
    public final static int GAMESTATE_PLAYERMENU = 4;
    public final static int GAMESTATE_WORLDMENU = 5;

    // Variables for the game
    public static boolean isRunning = true;
    private static Timer timer;
    private static final long FPS = 60;

    private Stack<Integer> gameState;

    // MVC instances
    private ActionHandlerInWorld actionHandlerInWorld;
    private ObjectHandlerInWorld objectHandlerInWorld;

    private ObjectHandlerInMainMenu objectHandlerInMainMenu;

    private ActionHandlerInWorldMenu actionHandlerInWorldMenu;
    private ObjectHandlerInWorldMenu objectHandlerInWorldMenu;

    private MenuImageLoader menuImageLoader;

    private ImageHandler imageHandler;
    private InputHandler inputHandler;

    private Camera camera;

    /**
     * Main method to run the game
     */
    public static void main(String[] args){

        VillagerTest game = null;
        try {
            game = new VillagerTest();

        } catch (IOException | UnsupportedAudioFileException e) {
            e.printStackTrace();
            System.exit(0);
        }

        timer = new Timer();
        timer.schedule(game, 0, 1000 / FPS);

    }

    /**
     * This method will set up everything needed for the game to run
     * @throws IOException if an image is missing from the directory
     */
    private VillagerTest() throws IOException, UnsupportedAudioFileException {
        gameState = new Stack<>();

        // Main components of the game
        menuImageLoader = new MenuImageLoader();

        objectHandlerInMainMenu = new ObjectHandlerInMainMenu(menuImageLoader);

        camera = new Camera();

        imageHandler = new ImageHandler(camera);
        inputHandler = new InputHandler(imageHandler);

        // Current GameState
        gameState.push(GAMESTATE_WORLDMENU);

        startGame();
    }

    private void startGame() {

        Player.setPlayer(gamePlayer);

        try {
            objectHandlerInWorld = new ObjectHandlerInWorld(objectHandlerInMainMenu.getWorldMap(), camera);
            actionHandlerInWorld = new ActionHandlerInWorld(objectHandlerInWorld);

            objectHandlerInWorldMenu = new ObjectHandlerInWorldMenu(objectHandlerInWorld, menuImageLoader, camera);
            actionHandlerInWorldMenu = new ActionHandlerInWorldMenu(objectHandlerInWorldMenu, objectHandlerInMainMenu.getWorldMap(), objectHandlerInMainMenu.getSavedFileName());

        } catch (IOException e) {
            e.printStackTrace();
            isRunning = false;
        }

        camera.placePlayerInTheCenterOfTheScreen();

        gameState = new Stack<>();
        gameState.push(GAMESTATE_WORLD);
    }

    /**
     * This method starts the game and runs it in a loop
     * It updates all CoreHandler objects
     */
    public void run() {

        // Gets current time for fps
        long time = System.currentTimeMillis();

        update();

        // Handles player input
        handleInput(inputHandler.getInput());

        if (!isRunning) {
            timer.cancel();
            System.exit(0);
        }

        verifyFrameRate(System.currentTimeMillis() - time);
    }

    /**
     * Updates the correct Handler according to the current game state
     */
    private void update() {
        if (gameState.peek() == GAMESTATE_WORLD) {
            Player.update();
            imageHandler.update(objectHandlerInWorld.get(), gameState.peek());
            objectHandlerInWorld.update();
            actionHandlerInWorld.update();

            camera.update();

        } else if (gameState.peek() == GAMESTATE_WORLDMENU) {
            objectHandlerInWorldMenu.update();
            actionHandlerInWorldMenu.update();
            imageHandler.update(objectHandlerInWorldMenu.get(), gameState.peek());

            camera.update();

        } else {
            throw new IllegalStateException("The state " + gameState.peek() + " is not handled in doAction().");
        }
    }

    /**
     * Changes the gameState and updates the camera
     */
    private void quitCurrentState() {
        gameState.pop();
    }

    /**
     * Handles String actions and sends them to the correct game controller
     */
    private void handleInput(String action) {
        // If there is an action to do, Update the game Controller
        if (action != null) {
            if (gameState.peek() == GAMESTATE_WORLD) {
                // World input
                actionHandlerInWorld.doInput(action);

                if (actionHandlerInWorld.getCurrentMiniGame() != null) {

                } else if (actionHandlerInWorld.isGoToPlayerMenu()) {
                    gameState.push(GAMESTATE_PLAYERMENU);
                    actionHandlerInWorld.resetGoToPlayerMenu();

                } else if (actionHandlerInWorld.isGoToWorldMenu()) {
                    gameState.push(GAMESTATE_WORLDMENU);
                    objectHandlerInWorldMenu.setVillager(actionHandlerInWorld.getVillagerMenu());
                    actionHandlerInWorld.resetWorldMenu();
                }

            } else if (gameState.peek() == GAMESTATE_WORLDMENU) {
                actionHandlerInWorldMenu.doInput(action);

                if (actionHandlerInWorldMenu.isBackToGame()) {

                    actionHandlerInWorldMenu.reset();
                    quitCurrentState();
                }

            } else {
                throw new IllegalStateException("The state " + gameState.peek() + " is not handled in handleInput().");
            }
        }
    }

    /**
     * Ensures that the fps is stable
     *
     * @param time in miliseconds
     */
    private void verifyFrameRate(long time){

        long currentFPS = 0;

        if (time != 0) {
            currentFPS = 1000 / (time);
        }

        // System.out.println("Current FPS: " + currentFPS);

        if (currentFPS < FPS) {
            System.out.println("GameError: Running under " + FPS + " fps");
        }
    }
}
