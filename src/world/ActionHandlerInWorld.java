package world;

import pokeshinto.InfoHandler;
import pokeshinto.Menu;
import pokeshinto.Player;
import pokeshinto.StackLinked;

/**
 * The ActionHandler is the main LOGIC component of the world. It 
 * gets player input, sends it to the correct object, and holds 
 * most of the game objects.
 * 
 * @author Jeremie Beaudoin-Dion
 *
 */
public class ActionHandlerInWorld {
	
	private Player player;
	private Menu currentMenu;
	private StackLinked<Menu> allMenus;
	
	private boolean combatHasStarted;
	
	/**
	 * Constructor for the controller of the world environment
	 */
	public ActionHandlerInWorld(Player player) {
		this.player = player;
		combatHasStarted = false;
		allMenus = new StackLinked<Menu>();
		currentMenu = InfoHandler.getMenu("Start Combat");
		allMenus.push(currentMenu);
	}
	
	/**
	 * Gets an input and handles it to the correct object
	 * 
	 * @param myInput: the String converted input from JFrame
	 */
	public void doInput(String myInput) {
		
		if(myInput.equals("Left")){
			player.moveLeft();
			// currentMenu.goLeft();
			
		} else if(myInput.equals("Right")){
			player.moveRight();
			// currentMenu.goRight();
			
		} else if(myInput.equals("Up")){
			player.moveUp();
			// currentMenu.goRight();
			
		} else if(myInput.equals("Down")){
			player.moveDown();
			// currentMenu.goRight();
			
		} else if(myInput.equals("Decide")){
			
			//combatHasStarted = true;			
			
		} else if(myInput.equals("Escape")){
			
			if(!allMenus.isEmpty()){
				currentMenu = allMenus.pop();
			}
		}
		
	}
	
	/**
	 * Returns the current position of the Player object
	 * 
	 * @return a point with x and y as int
	 */
	public Player getPlayer() {
		return player;
	}
	
	/**
	 * Returns if the combat has started in the world
	 * 
	 * @return true if the combat has started
	 */
	public boolean getCombatHasStarted() {
		return combatHasStarted;
	}
	
	/**
	 * Called every frame
	 * 
	 * @param gameState: the String current state of the game
	 * @return: true if a combat occured
	 */
	public void update(String gameState) {
		
		player.update();
	
	}
	
}
