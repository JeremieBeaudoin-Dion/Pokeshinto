package world;

import java.io.IOException;
import java.util.*;

import images.PhysicalObject;
import images.Position;
import pokeshinto.Player;

/**
 * This object handler represent the Model of the game when the game
 * is in a World state. 
 * 
 * @author Jeremie Beaudoin
 */
public class ObjectHandlerInWorld {
	
	// The instances of the MVC
	private Player player;
	private WorldImageLoader worldImageLoader;
	private CollisionHandler collisionHandler;
	
	/**
	 * Basic constructor for the Model component of the World
	 *
	 * @param player: the player instance of the game
	 * @throws IOException if an image is missing
	 */
	public ObjectHandlerInWorld(Player player) throws IOException {
		
		// MVC component
		this.player = player;
		
		// Handles images objects
		worldImageLoader = new WorldImageLoader();

		// Handles collisions
        collisionHandler = new CollisionHandler();
	}

    /**
     * Called every frame
     */
	public void update() {
        getMap().update();
    }
	
	/**
     * Places all object in order of appearance on the screen.
     * This ensures that an object which is supposed to be "under" another will be added first.
     *
	 * @return all physical object to show in the game
	 */
	public LinkedList<PhysicalObject> get() {

	    Map currentMap = getMap(player.getCurrentMapID());

        LinkedList<PhysicalObject> allObjects = new LinkedList<>();

        allObjects.addAll(currentMap.getBackgroundObjects());
        allObjects.add(ShadowFactory.getPlayerShadow(player));
        allObjects.addAll(currentMap.getShadowObjects());
        allObjects.addAll(getAllMovingObjectsInOrder(currentMap));

        // addPlayerObjectAccordingToPosition(solid, allObjects);
        allObjects.addAll(currentMap.getFloatingObjects());

		return allObjects;
	}

    /**
     * Sorts and returns all moving objects in order
     */
	private List<PhysicalObject> getAllMovingObjectsInOrder(Map currentMap){
	    List<PhysicalObject> allMovingObjects = new LinkedList<>();

	    allMovingObjects.add(worldImageLoader.getPlayerObject(player));
	    allMovingObjects.addAll(currentMap.getSolidObjects());
        allMovingObjects.addAll(getAllEnemyImage(currentMap));

	    Collections.sort(allMovingObjects);

	    return allMovingObjects;
    }

    /**
     * Transform all enemies from a Map into a list of Images
     */
    private List<PhysicalObject> getAllEnemyImage(Map currentMap) {
	    List<PhysicalObject> listOfEnemyImages = new LinkedList<>();
        List<Enemy> enemies = currentMap.getAllEnemies();

        Iterator<Enemy> iter = enemies.iterator();
        Enemy currentEnemy;

        while (iter.hasNext()) {
            currentEnemy = iter.next();
            listOfEnemyImages.add(worldImageLoader.getEnemyObject(currentEnemy));
        }

        return listOfEnemyImages;
    }

    /**
     * @param mapID: the name of the desired map
     * @return the map representation according to mapID
     */
    private Map getMap(String mapID) {
        return worldImageLoader.getMap(mapID);
    }

    /**
     * @return the current map Object
     */
    public Map getMap() {
        return worldImageLoader.getMap();
    }

    /**
     * @return true if there is a collision with a solid tile on the background
     */
    boolean getBackgroundCollision(Position northEast, Position southWest) {
        return collisionHandler.getBackgroundCollision(northEast, southWest, getMap().getBackground());
    }

    /**
     * @return true if there is a collision with a solid object on the map
     */
    boolean getObjectCollision(Position northEast, Position southWest) {
        return collisionHandler.getShadowCollision(new PhysicalObject(northEast, southWest), getMap().getShadowObjects());
    }
}
