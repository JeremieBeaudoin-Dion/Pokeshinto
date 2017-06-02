package world;

import combat.CombatAI;
import images.Position;
import pokeshinto.InfoHandler;
import pokeshinto.Player;


/**
 * The ActionHandler handles player input.
 * 
 * @author Jeremie Beaudoin-Dion
 *
 */
public class ActionHandlerInWorld {
	
	private ObjectHandlerInWorld objectHandlerInWorld;

	private CombatAI currentCombatAI;
	
	/**
	 * Constructor for the controller of the world environment
	 */
	public ActionHandlerInWorld(ObjectHandlerInWorld model) {
        currentCombatAI = null;
		
		// Model of the game
		this.objectHandlerInWorld = model;
	}
	
	/**
	 * Gets an input and handles it to the correct object
	 * 
	 * @param myInput: the String converted input from JFrame
	 */
	public void doInput(String myInput) {

        if(myInput.equals("Decide")){
			
		} else if(myInput.equals("Escape")){
			
			if(objectHandlerInWorld.getCurrentMenu() == null) {
                objectHandlerInWorld.addNewMenu(InfoHandler.getWorldMenu("Root"));
            } else {
                objectHandlerInWorld.quitCurrentMenu();
            }

		} else {
		    // It is "Up", "Down", "Left" or "Right"
            if (objectHandlerInWorld.getCurrentMenu() == null) {
                Player.setNewPositionAccordingToShadow(moveObject(Player.getCollisionPosition(),
                        Player.getCollisionDimensions(), Player.PIXEL_SPEED, myInput));
            } else {
                if (myInput.equals("Up")){
                    objectHandlerInWorld.getCurrentMenu().goBack();
                } else if (myInput.equals("Down")) {
                    objectHandlerInWorld.getCurrentMenu().goForward();
                }
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
	private Position moveObject(Position initialPosition, Position dimensions, int speed, String direction){
        Position newPosition = calculatePosition(initialPosition, speed, direction);
        Position southWest = new Position(newPosition.getX() + dimensions.getX(), newPosition.getY() + dimensions.getY());

        //TODO: add Collision with doors and enemies
        if (objectHandlerInWorld.getBackgroundCollision(newPosition, southWest)){
            return initialPosition;
        }

        if (objectHandlerInWorld.getObjectCollision(newPosition, southWest)){
        	return initialPosition;
		}

		Enemy currentEnemy = objectHandlerInWorld.getEnemyCollision(newPosition, southWest);
        if (currentEnemy != null) {
            currentCombatAI = currentEnemy.getAI();
            objectHandlerInWorld.destroyEnemyObject(currentEnemy);
        }

        Door currentDoor = objectHandlerInWorld.getDoorCollision(newPosition, southWest);
        if (currentDoor != null) {
            Player.setNewMapID(currentDoor.getNextMapID());
            newPosition = currentDoor.getNextPosition();
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
	 * Returns if the combat has started in the world
	 */
	public boolean getCombatHasStarted() {
		return currentCombatAI != null;
	}

    /**
     * If combat has started, a CombatAI has been stored
     */
	public CombatAI getCurrentCombatAI() {

	    if (!getCombatHasStarted()) {
	        throw new ExceptionCombatNotStarted();
        }

	    return currentCombatAI;
    }

    public void resetCombatAI() {
	    currentCombatAI = null;
    }
	
}
