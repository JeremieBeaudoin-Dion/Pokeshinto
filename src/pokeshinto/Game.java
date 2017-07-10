package pokeshinto;

import java.io.IOException;
import java.util.Stack;
import java.util.Timer;

import combat.ActionHandlerInCombat;
import combat.CombatAI;
import combat.ObjectHandlerInCombat;
import combat.combatActions.Heal;
import combat.combatActions.HealHpFlat;
import duringMenus.ActionHandlerInPlayerMenu;
import duringMenus.ObjectHandlerInPlayerMenu;
import images.Camera;
import images.ImageHandler;
import duringMenus.ActionHandlerInMainMenu;
import duringMenus.ObjectHandlerInMainMenu;
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

    private final static int GAMESTATE_WORLD = 0;
    private final static int GAMESTATE_COMBAT = 1;
    private final static int GAMESTATE_MINIGAME = 2;
    private final static int GAMESTATE_MAINMENU = 3;
    private final static int GAMESTATE_PLAYERMENU = 3;
	
	// Variables for the game
	public static boolean isRunning = true;
	private static Timer timer;
    private static final long FPS = 90;
	
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

        // game.run();

        if (!Game.isRunning) {
            System.exit(0);
        }
	}
	
	/** 
	 * This method will set up everything needed for the game to run 
	 * @throws IOException if an image is missing from the directory
	 */
    private Game() throws IOException, UnsupportedAudioFileException {
		gameState = new Stack<>();
		createPlayer();
		
		// Main components of the game
        menuImageLoader = new MenuImageLoader();

		objectHandlerInWorld = new ObjectHandlerInWorld();
		actionHandlerInWorld = new ActionHandlerInWorld(objectHandlerInWorld);

		actionHandlerInCombat = new ActionHandlerInCombat();
		objectHandlerInCombat = new ObjectHandlerInCombat(actionHandlerInCombat, menuImageLoader);

		objectHandlerInMainMenu = new ObjectHandlerInMainMenu(menuImageLoader);
		actionHandlerInMainMenu = new ActionHandlerInMainMenu(objectHandlerInMainMenu);

        minigameHandler = new MinigameHandler();

        camera = new Camera();

        objectHandlerInPlayerMenu = new ObjectHandlerInPlayerMenu(objectHandlerInWorld, menuImageLoader, camera);
        actionHandlerInPlayerMenu = new ActionHandlerInPlayerMenu(objectHandlerInPlayerMenu);

        musicHandler = new MusicHandler();

        imageHandler = new ImageHandler(camera);
        inputHandler = new InputHandler(imageHandler);
		
		// Current GameState
		gameState.push(GAMESTATE_WORLD);
        gameState.push(GAMESTATE_MAINMENU);
        camera.setToZero();

        musicHandler.play();

        /*
         * For testing purpose only
         */
		//startCombat(combat.InfoHandler.getGenericAI());
        //startMinigame("Fishing");
	}

	/**
	 * Creates a Player instance for testing
	 */
	private void createPlayer() {
		/*
		* For testing purpose only
		*

		Pokeshinto[] allPokeshintos = new Pokeshinto[2];

		allPokeshintos[0] = combat.InfoHandler.getPokeshinto("Kohadai");
		allPokeshintos[1] = combat.InfoHandler.getPokeshinto("Kurokage");

		player = new Player(100.0, allPokeshintos, combat.InfoHandler.getBasicCombatAttributes());*/
		new Player();
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
        }

        verifyFrameRate(System.currentTimeMillis() - time);
	}

    /**
     * Updates the correct Handler according to the current game state
     */
	private void update() {
        if (gameState.peek() == GAMESTATE_COMBAT){
            imageHandler.update(objectHandlerInCombat.get());
            actionHandlerInCombat.update();

        } else if (gameState.peek() == GAMESTATE_WORLD) {
            imageHandler.update(objectHandlerInWorld.get());
            objectHandlerInWorld.update();
            actionHandlerInWorld.update();

            camera.update();

            if (actionHandlerInWorld.getCombatHasStarted()) {
                startCombat(actionHandlerInWorld.getCurrentCombatAI());

            } else if (actionHandlerInWorld.getCurrentMiniGame() != null) {
                startMinigame(actionHandlerInWorld.getCurrentMiniGame());

            } else if (actionHandlerInWorld.isGoToPlayerMenu()) {
                gameState.push(GAMESTATE_PLAYERMENU);
                actionHandlerInWorld.resetGoToPlayerMenu();
            }

        } else if (gameState.peek() == GAMESTATE_MINIGAME) {
            minigameHandler.update();
            imageHandler.update(minigameHandler.get());

        } else if (gameState.peek() == GAMESTATE_MAINMENU) {
            actionHandlerInMainMenu.update();
            imageHandler.update(objectHandlerInMainMenu.get());

        } else if (gameState.peek() == GAMESTATE_PLAYERMENU) {
            objectHandlerInPlayerMenu.update();
            actionHandlerInPlayerMenu.update();
            imageHandler.update(objectHandlerInPlayerMenu.get());

            camera.update();

        } else {
            throw new IllegalStateException("The state " + gameState.peek() + " is not handled in doAction().");
        }
    }

    private void givePlayerAPotion() {
	    Heal potionHeal = new HealHpFlat(true, "Current Health", 50);

	    Player.addNewItem(new HealthPotion("Petty Potion", 15, potionHeal));
    }

    /**
     * Changes the gameState and updates the camera
     */
    private void quitCurrentState() {
        gameState.pop();
        musicHandler.play();
    }

    /**
     * Starts a combat in the ActionHandler object
     *
     * @param opponent: The CombatAI that will attack Player
     */
    private void startCombat(CombatAI opponent){
        givePlayerAPotion();  // For testing purposes

        actionHandlerInCombat.startCombat(opponent);
        gameState.push(GAMESTATE_COMBAT);
        camera.setToZero();
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
        camera.setToZero();
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

            } else if (gameState.peek() == GAMESTATE_MINIGAME) {
                minigameHandler.doInput(action);

                if (minigameHandler.isGameFinished()) {
                    quitCurrentState();
                }

            } else if (gameState.peek() == GAMESTATE_MAINMENU) {
                actionHandlerInMainMenu.doInput(action);

                if (actionHandlerInMainMenu.isStartGame()) {
                    quitCurrentState();
                }

            } else if (gameState.peek() == GAMESTATE_PLAYERMENU) {
                actionHandlerInPlayerMenu.doInput(action);

                if (actionHandlerInPlayerMenu.isBackToGame()) {
                    actionHandlerInPlayerMenu.resetBackToGame();
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
		long sleepTime = (1000 / Game.FPS) - time;
		
		if (sleepTime < 0 && gameState.peek() == GAMESTATE_WORLD){
            System.out.println("Game Error: FrameRate dropped below " + Game.FPS);
		}
	}
} 
