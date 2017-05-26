package world;

import images.Position;
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
	
	private ObjectHandlerInWorld objectHandlerInWorld;
	
	private Map currentMap;
	
	private boolean combatHasStarted;
	
	/**
	 * Constructor for the controller of the world environment
	 */
	public ActionHandlerInWorld(Player player, ObjectHandlerInWorld model) {
		this.player = player;
		combatHasStarted = false;
		
		// Menus
		allMenus = new StackLinked<>();
		
		// Model of the game
		this.objectHandlerInWorld = model;
	}
	
	/**
	 * Gets an input and handles it to the correct object
	 * 
	 * @param myInput: the String converted input from JFrame
	 */
	public void doInput(String myInput) {
		
		if(myInput.equals("Left")){
		    player.setNewPositionAccordingToShadow(moveObject(player.getCollisionPosition(),
                    player.getCollisionDimensions(), Player.PIXEL_SPEED, myInput));
			
		} else if(myInput.equals("Right")){
            player.setNewPositionAccordingToShadow(moveObject(player.getCollisionPosition(),
                    player.getCollisionDimensions(), Player.PIXEL_SPEED, myInput));
			
		} else if(myInput.equals("Up")){
            player.setNewPositionAccordingToShadow(moveObject(player.getCollisionPosition(),
                    player.getCollisionDimensions(), Player.PIXEL_SPEED, myInput));
			
		} else if(myInput.equals("Down")){
            player.setNewPositionAccordingToShadow(moveObject(player.getCollisionPosition(),
                    player.getCollisionDimensions(), Player.PIXEL_SPEED, myInput));
			
		} else if(myInput.equals("Decide")){
			
			//combatHasStarted = true;			
			
		} else if(myInput.equals("Escape")){
			
			if(!allMenus.isEmpty()){
				currentMenu = allMenus.pop();
			}
		}
		
	}

    /**
     * Checks collision and returns where the object should move to according to speed and direction
     *
     * @param initialPosition the Position where the object is
     * @param dimensions the dimension of the object
     * @param speed the number of pixel to move the object
     * @param direction "Left", "Right", "Up" or "Down"
     * @return the new position
     */
	public Position moveObject(Position initialPosition, Position dimensions, int speed, String direction){
        Position newPosition = calculatePosition(initialPosition, speed, direction);
        Position southWest = new Position(newPosition.getX() + dimensions.getX(), newPosition.getY() + dimensions.getY());

        //TODO: add Collision with doors and enemies
        if (objectHandlerInWorld.getBackgroundCollision(newPosition, southWest)){
            return initialPosition;
        }

        if (objectHandlerInWorld.getObjectCollision(newPosition, southWest)){
        	return initialPosition;
		}

        return newPosition;
    }

    /**
     * @param initialPosition where the Object is
     * @param speed the number of pixel to move the object
     * @param direction "Left", "Right", "Up" or "Down"
     * @return the new Position position
     */
    private Position calculatePosition(Position initialPosition, int speed, String direction){

	    Position newPosition = new Position(initialPosition.getX(), initialPosition.getY());

	    switch (direction){
            case "Right":
                newPosition.addX(speed);
                break;
            case "Left":
                newPosition.addX(-speed);
                break;
            case "Up":
                newPosition.addY(-speed);
                break;
            default:
                newPosition.addY(speed);
        }

        return newPosition;
    }

	/**
	 * Returns the current Player object
	 * 
	 * @return the Player object
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
