package world;

import combat.CombatAI;
import duringMenus.Menu;
import images.Position;
import minigames.MinigameHandler;
import pokeshinto.Game;
import pokeshinto.Player;
import world.worldObjects.Door;
import world.worldObjects.Enemy;
import world.worldObjects.MovingObject;
import world.worldObjects.Villager;

/**
 * The ActionHandler handles player input.
 * 
 * @author Jeremie Beaudoin-Dion
 *
 */
public class ActionHandlerInWorld {
	
	private ObjectHandlerInWorld objectHandlerInWorld;

	private CombatAI currentCombatAI;

	private String currentMinigame;

	private boolean goToPlayerMenu;
	private Villager nextMenuVillager;

    private int waitBeforeInput;
    private final int MAX_WAIT_VALUE = Game.MAX_INPUT_WAIT_TIME;
	
	/**
	 * Constructor for the controller of the world environment
	 */
	public ActionHandlerInWorld(ObjectHandlerInWorld model) {
        currentCombatAI = null;
        currentMinigame = null;

        goToPlayerMenu = false;

        waitBeforeInput = MAX_WAIT_VALUE;
		
		// Model of the game
		this.objectHandlerInWorld = model;
	}

    /**
     * Called every frame
     *
     * Checks for enemy collision while not moving
     */
	public void update(){
	    Position northEast = new Position(Player.getCollisionPosition().getX(), Player.getCollisionPosition().getY());
	    Position southWest = new Position(northEast.getX() + Player.getCollisionDimensions().getX(),
                northEast.getY() + Player.getCollisionDimensions().getY());

        handleMovingObjectCollision(northEast, southWest);

        Player.isMoving = false;
        waitBeforeInput--;
    }
	
	/**
	 * Gets an input and handles it to the correct object
	 * 
	 * @param myInput: the String converted input from JFrame
	 */
	public void doInput(String myInput) {

        if(myInput.equals("Decide")){
            handlePlayerNearWater(Player.getCollisionPosition(),
                    Player.getCollisionDimensions(), Player.PIXEL_SPEED * 2, Player.facing);
			
		} else if(myInput.equals("Escape")){

            if (waitBeforeInput > 0) {
                return;
            }

            goToPlayerMenu = true;
            waitBeforeInput = MAX_WAIT_VALUE;

		} else {
            Player.setNewPositionAccordingToShadow(moveObject(Player.getCollisionPosition(),
                    Player.getCollisionDimensions(), Player.PIXEL_SPEED, myInput));
            Player.facing = myInput;
            Player.isMoving = true;

        }
		
	}

    /**
     * When a decision has been taken, if the player is near water, start the
     * fishing minigame.
     */
	private void handlePlayerNearWater(Position initialPosition, Position dimensions, int speed, String direction) {
        Position[] allPositions = getPlayerMovesWithoutCollision(initialPosition, dimensions, speed, direction);

        Position newPosition = allPositions[0];
        Position southWest = allPositions[1];

        String nextToPlayer = objectHandlerInWorld.getPlayerFacingBackground(newPosition, southWest);

        if (nextToPlayer == null) {
            return;
        }

        if (nextToPlayer.equals("Water")) {
            currentMinigame = MinigameHandler.fishingMiniGame;
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
	    Position[] allPositions = getPlayerMovesWithoutCollision(initialPosition, dimensions, speed, direction);

	    Position newPosition = allPositions[0];
	    Position southWest = allPositions[1];

        if (objectHandlerInWorld.getIsOutOfBounds(newPosition, southWest)) {
            return initialPosition;

        } else if (objectHandlerInWorld.getBackgroundCollision(newPosition, southWest)){
            return initialPosition;

        } else if (objectHandlerInWorld.getSolidObjectCollision(newPosition, southWest)){
        	return initialPosition;

		} else if (handleMovingObjectCollision(newPosition, southWest)){
            return initialPosition;

        }

        handleDoorCollision(newPosition, southWest);

        return newPosition;
    }

    private Position[] getPlayerMovesWithoutCollision(Position initialPosition, Position dimensions, int speed, String direction){
        Position newPosition = calculatePosition(initialPosition, speed, direction);
        Position southWest = new Position(newPosition.getX() + dimensions.getX(), newPosition.getY() + dimensions.getY());

        return new Position[]{newPosition, southWest};
    }

    private boolean handleMovingObjectCollision(Position northEast, Position southWest) {
        MovingObject currentObject = objectHandlerInWorld.getMovingObjectCollision(northEast, southWest);

        if (currentObject == null) {
            return false;

        } else if (currentObject instanceof Enemy) {
            Enemy currentEnemy = (Enemy) currentObject;

            currentCombatAI = currentEnemy.getAI();
            objectHandlerInWorld.destroyEnemyObject(currentEnemy);

            return true;

        } else if (currentObject instanceof Villager) {
            nextMenuVillager = (Villager) currentObject;

            return true;
        }

        return false;
    }

    private void handleDoorCollision(Position northEast, Position southWest) {
        Door currentDoor = objectHandlerInWorld.getDoorCollision(northEast, southWest);
        if (currentDoor != null) {
            objectHandlerInWorld.getMap().respawnEnemies();

            Player.setNewMapID(currentDoor.getNextMapID());

            northEast.setX(currentDoor.getNextPosition().getX());
            northEast.setY(currentDoor.getNextPosition().getY());
        }
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

	public String getCurrentMiniGame() {
	    return currentMinigame;
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

    /**
     * Set the combatAI to null
     */
    public void resetCombatAI() {
	    currentCombatAI = null;
    }

    public void resetMinigame() {
        currentMinigame = null;
    }

    public boolean isGoToPlayerMenu() {
        return goToPlayerMenu;
    }

    public void resetGoToPlayerMenu() {
        goToPlayerMenu = false;
    }

    public boolean isGoToWorldMenu() {
        return nextMenuVillager != null;
    }

    public Villager getVillagerMenu() {
        return nextMenuVillager;
    }

    public void resetWorldMenu() {
        nextMenuVillager = null;
    }
	
}
