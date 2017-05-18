package pokeshinto;

import java.io.IOException;

import combat.ActionHandlerInCombat;
import combat.CombatAI;
import combat.ObjectHandlerInCombat;
import images.ImageHandler;
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
	private boolean isRunning = true; 
	private int fps = 60;
	
	private StackLinked<String> gameState;
	private ActionHandlerInWorld actionHandlerInWorld;
	private ObjectHandlerInWorld objectHandlerInWorld;
	private ActionHandlerInCombat actionHandlerInCombat;
	private ObjectHandlerInCombat objectHandlerInCombat;
	private ImageHandler imageHandler;
	private Player player;
	private InputHandler inputHandler;
	
	public final static int WINDOW_WIDTH = 900;
	public final static int WINDOW_HEIGHT = 600;
	
	/**
	 * Main 
	 * @param args
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
	public void startCombat(CombatAI opponent){
		actionHandlerInCombat.startCombat(opponent);
		gameState.push("Combat");
	}
	
	/** 
	 * This method will set up everything needed for the game to run 
	 * @throws IOException 
	 */ 
	public Game() throws IOException {
		// The current gameState of the game. It can store a maximum of 5 states
		gameState = new StackLinked<String>();
		player = createPlayer();
		
		// Main components of the game
		objectHandlerInWorld = new ObjectHandlerInWorld(player);
		actionHandlerInWorld = new ActionHandlerInWorld(player, objectHandlerInWorld);
		actionHandlerInCombat = new ActionHandlerInCombat(player);
		objectHandlerInCombat = new ObjectHandlerInCombat(actionHandlerInCombat);
		imageHandler = new ImageHandler();
		inputHandler = new InputHandler(imageHandler);
		
		// Current GameState
		gameState.push("World");
		//startCombat(InfoHandler.getGenericAI());
	}
	
	/**
	 * Creates a Player instance for testing
	 * @return
	 */
	public Player createPlayer() {
		Pokeshinto[] allPokeshintos = new Pokeshinto[2];
		
		allPokeshintos[0] = InfoHandler.getPokeshinto("Kohadai");
		allPokeshintos[1] = InfoHandler.getPokeshinto("Kurokage");
		
		Player player = new Player(100.0, allPokeshintos, InfoHandler.getBasicCombatAttributes());
		
		player.setCurrentMapCollision(new boolean[][]{{true, true, true}, {true, true, true}, {true, true, true}});
		
		return player;
	}
	
	/**
	 * Handles String actions and sends them to the correct game controller
	 * 
	 * @param action
	 */
	private void handleInput(String action) {
		// If there is an action to do, Update the game Controller
		if (action != null) {			
			// Sends the input to the correct handler
			if (gameState.peek().equals("Combat")){
				// Combat input
				actionHandlerInCombat.doInput(action);
				
				if (actionHandlerInCombat.update(gameState.peek())){
					gameState.pop();
				}
				
			} else {
				// World input
				actionHandlerInWorld.doInput(action);
				actionHandlerInWorld.update(gameState.peek());
				
				if (actionHandlerInWorld.getCombatHasStarted()) {
					startCombat(InfoHandler.getGenericAI());
				}
			}
		}
	}

	/** 
	 * This method starts the game and runs it in a loop 
	 * It updates all CoreHandler objects
	 */ 
	private void run() {

		// Main loop -> game loop running at 60 fps
		while(isRunning){ 
			// Gets current time for fps
			long time = System.currentTimeMillis();
			
			inputHandler.update();
			
			// Handles player input
			handleInput(inputHandler.getInput(gameState.peek()));
			
			// Updates the view
			if (gameState.peek().equals("Combat")){
				imageHandler.update(objectHandlerInCombat.get());
			} else {
				imageHandler.update(objectHandlerInWorld.get());
			}
			

			//  delay for each frame - time it took for one frame 
			delayGame(System.currentTimeMillis() - time);
			
		}
	} 
	
	/**
	 * Ensures that the fps is stable
	 * 
	 * @param time in miliseconds
	 */
	private void delayGame(long time){
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
			System.out.println("Game Error: FrameRate dropped below 60");
		}
	}
} 
