package world;

import java.io.IOException;
import java.util.*;

import images.Image;
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
     * @return true if there is a collision with a solid tile on the background
     */
	boolean getBackgroundCollision(Position northEast, Position southWest) {
	    return collisionHandler.getBackgroundCollision(northEast, southWest, getMap().getBackground());
    }

    boolean getObjectCollision(Position northEast, Position southWest) {
	    return collisionHandler.getShadowCollision(new PhysicalObject(northEast, southWest), getMap().getShadowObjects());
    }
	
	/**
     * Places all object in order of appearance on the screen.
     * This ensures that an object which is supposed to be "under" another will be added first.
     *
	 * @return all physical object to show in the game
	 */
	public LinkedList<PhysicalObject> get() {

	    Map currentMap = getMap(player.getCurrentMapID());

        LinkedList<PhysicalObject> background = currentMap.getBackgroundObjects();

        LinkedList<PhysicalObject> floating = currentMap.getFloatingObjects();
        LinkedList<PhysicalObject> shadow = currentMap.getShadowObjects();
        //LinkedList<PhysicalObject> enemies = currentMap.getAllEnemies();

        LinkedList<PhysicalObject> allObjects = new LinkedList<>();

        allObjects.addAll(background);
        allObjects.add(ShadowFactory.getPlayerShadow(player));
        allObjects.addAll(shadow);
        allObjects.addAll(getAllMovingObjectsInOrder(currentMap));

        // addPlayerObjectAccordingToPosition(solid, allObjects);
        allObjects.addAll(floating);

		return allObjects;
	}

    /**
     * Sorts and returns all moving objects in order
     */
	private List<PhysicalObject> getAllMovingObjectsInOrder(Map currentMap){
	    List<PhysicalObject> allMovingObjects = new LinkedList<>();

        LinkedList<PhysicalObject> solid = currentMap.getSolidObjects();
        LinkedList<Enemy> enemies = currentMap.getAllEnemies();

        Iterator<Enemy> iter = enemies.iterator();
        Enemy currentEnemy;

        while (iter.hasNext()) {
            currentEnemy = iter.next();


        }

	    allMovingObjects.add(getPlayerObject());
	    allMovingObjects.addAll(solid);


	    Collections.sort(allMovingObjects);

	    return allMovingObjects;
    }

    private PhysicalObject getPlayerObject() {
	    return new Image(player.getPosition(), WorldImageLoader.PLAYER_WIDTH,
                WorldImageLoader.PLAYER_HEIGHT, worldImageLoader.getPlayer());
    }

    /**
     * Helper method to put the player object in the right position so it is shown in the correct order
     *
     * @param solid the solid objects of the map
     * @param allObjects the array in which to add the solid objects and the player
     */
	private void addPlayerObjectAccordingToPosition(List<PhysicalObject> solid, List<PhysicalObject> allObjects) {
	    PhysicalObject playerObject = new Image(player.getPosition(), WorldImageLoader.PLAYER_WIDTH,
                WorldImageLoader.PLAYER_HEIGHT, worldImageLoader.getPlayer());

        Iterator<PhysicalObject> iter = solid.iterator();
        PhysicalObject currentObject;
        boolean done = false;

        while(iter.hasNext()){
            currentObject = iter.next();

            // Adds the player object if he is lower than the current object
            if(!done && currentObject.getY() >= player.getPosition().getY()){
                allObjects.add(playerObject);
                done = true;
            }

            allObjects.add(currentObject);

        }
        if (!done) {
            allObjects.add(playerObject);
        }

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

}
