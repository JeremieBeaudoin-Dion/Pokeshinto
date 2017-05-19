package pokeshinto;

import java.awt.Point;
import java.awt.event.KeyEvent;

import images.ImageHandler;

/**
 * The InputHandler class handles input from the player and converts it to
 * actions that the game can handle
 * 
 * @author Jérémie Beaudoin-Dion
 *
 */
public class InputHandler {
	
	private ImageHandler imageHandler;
	
	// Enables player input
	private KeyHandler playerInput; 
	private MouseHandler playerMouse;
	
	private int waitBeforeInput;
	private final int MAX_WAIT_VALUE = 7;
	
	/**
	 * Constructor
	 * 
	 * @param imageHandler: The reference ImageHandler object
	 */
	public InputHandler(ImageHandler imageHandler) {
		// Handles input
		playerInput = new KeyHandler(imageHandler);
		playerMouse = new MouseHandler(imageHandler);
		
		this.imageHandler = imageHandler;
		
		waitBeforeInput = MAX_WAIT_VALUE;
	}
	
	/**
	 * Gets the player input and sends it to HandleInput if there is an action to do
	 * 
	 * @return : A string that represent the action made
	 */
	public String getInput(String gameState){
		// Ensures that there aren't to many inputs
		if (waitBeforeInput > 0) {
			return null;
		}
		
		// Interprets the ingame value of the action
		String action = null;
		
		if (playerInput.isKeyDown(KeyEvent.VK_A) || playerInput.isKeyDown(KeyEvent.VK_LEFT)){
			action = "Left";
		} 
		
		if (playerInput.isKeyDown(KeyEvent.VK_D) || playerInput.isKeyDown(KeyEvent.VK_RIGHT)){
			action = "Right";
		} 
		
		if (playerInput.isKeyDown(KeyEvent.VK_W) || playerInput.isKeyDown(KeyEvent.VK_UP)){
			action = "Up";
		}
		
		if (playerInput.isKeyDown(KeyEvent.VK_S) || playerInput.isKeyDown(KeyEvent.VK_DOWN)){
			action = "Down";
		}
		
		if (playerInput.isKeyDown(KeyEvent.VK_SPACE) || playerInput.isKeyDown(KeyEvent.VK_ENTER)){
			action = "Decide";
		}
		
		if (playerInput.isKeyDown(KeyEvent.VK_ESCAPE) || playerInput.isKeyDown(KeyEvent.VK_BACK_SPACE)){
			action = "Escape";
		}
		
		if (playerMouse.isMouseDown()){			
			Point mouse = imageHandler.getMousePosition();
			System.out.println(mouse.getX());
		}
		
		if (action != null && gameState.equals("Combat")) {
			waitBeforeInput = MAX_WAIT_VALUE;
		}
		return action;
	}
	
	/**
	 * Called every frame
	 */
	public void update() {
		// Update waiting time for input
		waitBeforeInput -= 1;
		
		// Minimum value of 0
		if(waitBeforeInput < 0){
			waitBeforeInput = 0;
		}
	}

}
