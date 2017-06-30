package pokeshinto;

import java.io.IOException;
import java.util.Stack;

import combat.ActionHandlerInCombat;
import combat.CombatAI;
import combat.ObjectHandlerInCombat;
import images.Camera;
import images.ImageHandler;
import mainMenu.ActionHandlerInMainMenu;
import mainMenu.ObjectHandlerInMainMenu;
import minigames.MinigameHandler;
import world.ActionHandlerInWorld;
import world.ObjectHandlerInWorld;

/** 
 * 
 * The Main class for the game 
 * This class handles game flow
 * 
 * @author Jérémie Beaudoin-Dion
 * 
 */ 
public class Game{ 
	
	// Variables for the game
	public static boolean isRunning = true;

	private final long FPS_IN_WORLD = 60;
	private final long FPS_IN_COMBAT = 60;
	
	private Stack<String> gameState;

	private ActionHandlerInWorld actionHandlerInWorld;
	private ObjectHandlerInWorld objectHandlerInWorld;

	private ActionHandlerInCombat actionHandlerInCombat;
	private ObjectHandlerInCombat objectHandlerInCombat;

    private ActionHandlerInMainMenu actionHandlerInMainMenu;
    private ObjectHandlerInMainMenu objectHandlerInMainMenu;

	private MinigameHandler minigameHandler;

	private MenuImageLoader menuImageLoader;

	private ImageHandler imageHandler;
    private InputHandler inputHandler;

	private Camera camera;
	
	public final static int WINDOW_WIDTH = 900;
	public final static int WINDOW_HEIGHT = 600;
	
	/**
	 * Main method to run the game
	 */
	public static void main(String[] args){ 
		
		Game game = null;
		try {
			game = new Game();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		game.run();
		System.exit(0); 
	}
	
	/**
	 * Starts a combat in the ActionHandler object
	 * 
	 * @param opponent: The CombatAI that will attack Player
	 */
	private void startCombat(CombatAI opponent){
		actionHandlerInCombat.startCombat(opponent);
		gameState.push("Combat");
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
        gameState.push("Minigame");
	    camera.setToZero();
        actionHandlerInWorld.resetMinigame();
    }
	
	/** 
	 * This method will set up everything needed for the game to run 
	 * @throws IOException if an image is missing from the directory
	 */
    private Game() throws IOException {
		gameState = new Stack<>();
		createPlayer();
		
		// Main components of the game
        menuImageLoader = new MenuImageLoader();

		objectHandlerInWorld = new ObjectHandlerInWorld(menuImageLoader);
		actionHandlerInWorld = new ActionHandlerInWorld(objectHandlerInWorld);

		actionHandlerInCombat = new ActionHandlerInCombat();
		objectHandlerInCombat = new ObjectHandlerInCombat(actionHandlerInCombat, menuImageLoader);

		objectHandlerInMainMenu = new ObjectHandlerInMainMenu(menuImageLoader);
		actionHandlerInMainMenu = new ActionHandlerInMainMenu(objectHandlerInMainMenu);

        minigameHandler = new MinigameHandler();

        camera = new Camera();

        imageHandler = new ImageHandler(camera);
        inputHandler = new InputHandler(imageHandler);
		
		// Current GameState
		gameState.push("World");
        gameState.push("Main Menu");

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
     * Changes the gameState and updates the camera
     */
    private void quitCurrentState() {
        gameState.pop();
        camera.update();
    }

	/** 
	 * This method starts the game and runs it in a loop 
	 * It updates all CoreHandler objects
	 */ 
	private void run() {

		// Main loop
		while(isRunning){

			// Gets current time for fps
			long time = System.currentTimeMillis();

            update();

            inputHandler.update();

            // Handles player input
            handleInput(inputHandler.getInput(gameState.peek()));

			//  delay for each frame - time it took for one frame 
			delayGame(System.currentTimeMillis() - time);
			
		}
	}

    /**
     * Updates the correct Handler according to the current game state
     */
	private void update() {
        if (gameState.peek().equals("Combat")){
            imageHandler.update(objectHandlerInCombat.get());

        } else if (gameState.peek().equals("World")) {
            imageHandler.update(objectHandlerInWorld.get());
            objectHandlerInWorld.update();
            actionHandlerInWorld.update();

            if (actionHandlerInWorld.getCombatHasStarted()) {
                startCombat(actionHandlerInWorld.getCurrentCombatAI());

            } else if (actionHandlerInWorld.getCurrentMiniGame() != null) {
                startMinigame(actionHandlerInWorld.getCurrentMiniGame());
            }

        } else if (gameState.peek().equals("Minigame")) {
            minigameHandler.update();
            imageHandler.update(minigameHandler.get());

        } else if (gameState.peek().equals("Main Menu")) {
            actionHandlerInMainMenu.update();
            imageHandler.update(objectHandlerInMainMenu.get());

        } else {
            throw new IllegalStateException("The state " + gameState.peek() + " is not handled in update().");
        }
    }

    /**
     * Handles String actions and sends them to the correct game controller
     */
    private void handleInput(String action) {
        // If there is an action to do, Update the game Controller
        if (action != null) {
            // Sends the input to the correct handler
            if (gameState.peek().equals("Combat")){
                // Combat input
                actionHandlerInCombat.doInput(action);

                if (actionHandlerInCombat.update()){
                    quitCurrentState();
                }

            } else if (gameState.peek().equals("World")) {
                // World input
                actionHandlerInWorld.doInput(action);
                camera.update();

            } else if (gameState.peek().equals("Minigame")) {
                minigameHandler.doInput(action);

                if (minigameHandler.isGameFinished()) {
                    quitCurrentState();
                }

            } else if (gameState.peek().equals("Main Menu")) {
                actionHandlerInMainMenu.doInput(action);

                if (actionHandlerInMainMenu.isStartGame()) {
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
	private void delayGame(long time){
	    long fps = getCurrentFPS();

		long sleepTime = (1000 / fps) - time;
		
		if (sleepTime >= 0){ 
				
			try{ 
				// Stop the game for a limited time
				Thread.sleep(sleepTime);
			} 
			catch(Exception e){
				System.out.println("Time Error: Not handled");
			}
			
		} else {
			System.out.println("Game Error: FrameRate dropped below " + fps);
		}
	}

	private long getCurrentFPS() {
        if (gameState.peek().equals("Combat")) {
            return FPS_IN_COMBAT;

        } else if (gameState.peek().equals("World")) {
            return FPS_IN_WORLD;

        } else if (gameState.peek().equals("Minigame")) {
            return FPS_IN_WORLD;  // is the same: 60

        } else if (gameState.peek().equals("Main Menu")) {
            return FPS_IN_COMBAT;

        } else {
            throw new IllegalStateException("The gamestate " + gameState.peek() + " is not handled in getCurrentFPS().");
        }
    }
} 
