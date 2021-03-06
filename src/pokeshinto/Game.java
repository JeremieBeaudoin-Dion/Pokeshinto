package pokeshinto;

import java.io.IOException;
import java.util.Stack;
import java.util.Timer;

import combat.ActionHandlerInCombat;
import combat.CombatAI;
import combat.ObjectHandlerInCombat;
import combat.combatActions.Heal;
import combat.combatActions.HealHpFlat;
import duringMenus.*;
import images.Camera;
import images.ImageHandler;
import minigames.MinigameHandler;
import world.ActionHandlerInWorld;
import world.ObjectHandlerInWorld;
import world.items.HealthPotion;

import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * The Main class for the game 
 * This class handles game flow
 * 
 * @author Jérémie Beaudoin-Dion
 */ 
public class Game extends java.util.TimerTask {

    public final static int GAMESTATE_GAMEOVER = -1;
    public final static int GAMESTATE_WORLD = 0;
    public final static int GAMESTATE_COMBAT = 1;
    public final static int GAMESTATE_MINIGAME = 2;
    public final static int GAMESTATE_MAINMENU = 3;
    public final static int GAMESTATE_PLAYERMENU = 4;
    public final static int GAMESTATE_WORLDMENU = 5;
	
	// Variables for the game
	public static boolean isRunning = true;
	private static Timer timer;
    private static final long FPS = 60;

    public static final int MAX_INPUT_WAIT_TIME = 7;
	
	private Stack<Integer> gameState;

	// MVC instances
	private ActionHandlerInWorld actionHandlerInWorld;
	private ObjectHandlerInWorld objectHandlerInWorld;

	private ActionHandlerInCombat actionHandlerInCombat;
	private ObjectHandlerInCombat objectHandlerInCombat;

    private ActionHandlerInMainMenu actionHandlerInMainMenu;
    private ObjectHandlerInMainMenu objectHandlerInMainMenu;

    private ActionHandlerInPlayerMenu actionHandlerInPlayerMenu;
    private ObjectHandlerInPlayerMenu objectHandlerInPlayerMenu;

    private ActionHandlerInWorldMenu actionHandlerInWorldMenu;
    private ObjectHandlerInWorldMenu objectHandlerInWorldMenu;

    private ActionHandlerInGameOver actionHandlerInGameOver;
    private ObjectHandlerInGameOver objectHandlerInGameOver;

	private MinigameHandler minigameHandler;

	private MenuImageLoader menuImageLoader;

	private ImageHandler imageHandler;
    private InputHandler inputHandler;

	private Camera camera;

	private MusicHandler musicHandler;
	
	public final static int WINDOW_WIDTH = 900;
	public final static int WINDOW_HEIGHT = 600;
	
	/**
	 * Main method to run the game
	 */
	public static void main(String[] args){

	    // TODO: Remake the loop according to a delta value: see http://www.java-gaming.org/index.php?topic=24220.0
		
		Game game = null;
		try {
			game = new Game();

		} catch (IOException | UnsupportedAudioFileException e) {
			e.printStackTrace();
			System.exit(0);
		}

        timer = new Timer();
        timer.schedule(game, 0, 1000 / Game.FPS);

	}
	
	/** 
	 * This method will set up everything needed for the game to run 
	 * @throws IOException if an image is missing from the directory
	 */
    private Game() throws IOException, UnsupportedAudioFileException {
		gameState = new Stack<>();
		
		// Main components of the game
        menuImageLoader = new MenuImageLoader();

		objectHandlerInMainMenu = new ObjectHandlerInMainMenu(menuImageLoader);
		actionHandlerInMainMenu = new ActionHandlerInMainMenu(objectHandlerInMainMenu);

        camera = new Camera();

        musicHandler = new MusicHandler();

        imageHandler = new ImageHandler(camera);
        inputHandler = new InputHandler(imageHandler);
		
		// Current GameState
        gameState.push(GAMESTATE_MAINMENU);

        musicHandler.play();

        /*
         * For testing purpose only
         */
		//startCombat(combat.InfoHandler.getGenericAI());
        //startMinigame("Fishing");
	}

	private void startGame() {

        Player.setPlayer(objectHandlerInMainMenu.getGamePlayer());

        try {
            objectHandlerInWorld = new ObjectHandlerInWorld(objectHandlerInMainMenu.getWorldMap(), camera);
            actionHandlerInWorld = new ActionHandlerInWorld(objectHandlerInWorld);

            objectHandlerInPlayerMenu = new ObjectHandlerInPlayerMenu(objectHandlerInWorld, menuImageLoader, camera);
            actionHandlerInPlayerMenu = new ActionHandlerInPlayerMenu(objectHandlerInPlayerMenu, objectHandlerInMainMenu.getWorldMap(), objectHandlerInMainMenu.getSavedFileName());

            objectHandlerInWorldMenu = new ObjectHandlerInWorldMenu(objectHandlerInWorld, menuImageLoader, camera);
            actionHandlerInWorldMenu = new ActionHandlerInWorldMenu(objectHandlerInWorldMenu, objectHandlerInMainMenu.getWorldMap(), objectHandlerInMainMenu.getSavedFileName());

            objectHandlerInGameOver = new ObjectHandlerInGameOver(menuImageLoader);
            actionHandlerInGameOver = new ActionHandlerInGameOver(objectHandlerInGameOver);

            actionHandlerInCombat = new ActionHandlerInCombat();
            objectHandlerInCombat = new ObjectHandlerInCombat(actionHandlerInCombat, menuImageLoader);

            minigameHandler = new MinigameHandler();

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
        if (gameState.peek() == GAMESTATE_COMBAT){
            if (Player.health <= 0) {
                gameState.push(GAMESTATE_GAMEOVER);

            } else {
                imageHandler.update(objectHandlerInCombat.get(), gameState.peek());
                actionHandlerInCombat.update();
            }

        } else if (gameState.peek() == GAMESTATE_WORLD) {
            Player.update();
            imageHandler.update(objectHandlerInWorld.get(), gameState.peek());
            objectHandlerInWorld.update();
            actionHandlerInWorld.update();

            camera.update();

            if (actionHandlerInWorld.getCombatHasStarted()) {
                startCombat(actionHandlerInWorld.getCurrentCombatAI());
            }

        } else if (gameState.peek() == GAMESTATE_MINIGAME) {
            minigameHandler.update();
            imageHandler.update(minigameHandler.get(), gameState.peek());

        } else if (gameState.peek() == GAMESTATE_MAINMENU) {
            actionHandlerInMainMenu.update();
            imageHandler.update(objectHandlerInMainMenu.get(), gameState.peek());

        } else if (gameState.peek() == GAMESTATE_PLAYERMENU) {
            objectHandlerInPlayerMenu.update();
            actionHandlerInPlayerMenu.update();
            imageHandler.update(objectHandlerInPlayerMenu.get(), gameState.peek());

            camera.update();

        } else if (gameState.peek() == GAMESTATE_WORLDMENU) {
            objectHandlerInWorldMenu.update();
            actionHandlerInWorldMenu.update();
            imageHandler.update(objectHandlerInWorldMenu.get(), gameState.peek());

            camera.update();

        } else if (gameState.peek() == GAMESTATE_GAMEOVER) {
            actionHandlerInGameOver.update();
            imageHandler.update(objectHandlerInGameOver.get(), gameState.peek());

        } else {
            throw new IllegalStateException("The state " + gameState.peek() + " is not handled in doAction().");
        }
    }

    /**
     * Changes the gameState and updates the camera
     */
    private void quitCurrentState() {
        gameState.pop();
        musicHandler.play();
        if (Player.health <= 0) {
            gameState.push(GAMESTATE_GAMEOVER);
        }
    }

    /**
     * Starts a combat in the ActionHandler object
     *
     * @param opponent: The CombatAI that will attack Player
     */
    private void startCombat(CombatAI opponent){
        // givePlayerAPotion();  // For testing purposes

        actionHandlerInCombat.startCombat(opponent);
        gameState.push(GAMESTATE_COMBAT);
        actionHandlerInWorld.resetCombatAI();
    }

    /**
     * Starts a minigame
     *
     * @param minigame: the name of the desired minigame
     */
    private void startMinigame(String minigame) {
        minigameHandler.startMinigame(minigame);
        gameState.push(GAMESTATE_MINIGAME);
        actionHandlerInWorld.resetMinigame();
        musicHandler.play(minigame);
    }

    /**
     * Handles String actions and sends them to the correct game controller
     */
    private void handleInput(String action) {
        // If there is an action to do, Update the game Controller
        if (action != null) {
            // Sends the input to the correct handler
            if (gameState.peek() == GAMESTATE_COMBAT){
                // Combat input
                actionHandlerInCombat.doInput(action);

                if (actionHandlerInCombat.doAction()){
                    quitCurrentState();
                }

            } else if (gameState.peek() == GAMESTATE_WORLD) {
                // World input
                actionHandlerInWorld.doInput(action);

                if (actionHandlerInWorld.getCurrentMiniGame() != null) {
                    startMinigame(actionHandlerInWorld.getCurrentMiniGame());

                } else if (actionHandlerInWorld.isGoToPlayerMenu()) {
                    gameState.push(GAMESTATE_PLAYERMENU);
                    actionHandlerInWorld.resetGoToPlayerMenu();

                } else if (actionHandlerInWorld.isGoToWorldMenu()) {
                    gameState.push(GAMESTATE_WORLDMENU);
                    objectHandlerInWorldMenu.setVillager(actionHandlerInWorld.getVillagerMenu());
                    actionHandlerInWorld.resetWorldMenu();
                }

            } else if (gameState.peek() == GAMESTATE_MINIGAME) {
                minigameHandler.doInput(action);

                if (minigameHandler.isGameFinished()) {
                    quitCurrentState();
                }

            } else if (gameState.peek() == GAMESTATE_MAINMENU) {
                actionHandlerInMainMenu.doInput(action);

                if (actionHandlerInMainMenu.isStartGame()) {
                    startGame();
                }

            } else if (gameState.peek() == GAMESTATE_PLAYERMENU) {
                actionHandlerInPlayerMenu.doInput(action);

                if (actionHandlerInPlayerMenu.isBackToGame()) {
                    actionHandlerInPlayerMenu.reset();
                    quitCurrentState();
                }

            } else if (gameState.peek() == GAMESTATE_GAMEOVER) {
                actionHandlerInGameOver.doInput(action);

                if (actionHandlerInGameOver.isStartGame()) {
                    startGame();
                    actionHandlerInGameOver.reset();
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
