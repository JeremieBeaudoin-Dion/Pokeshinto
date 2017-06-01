package world;

import java.io.IOException;
import java.util.*;

import images.PhysicalObject;
import images.Position;
import pokeshinto.Menu;
import pokeshinto.Player;
import pokeshinto.StackLinked;

/**
 * This object handler represent the Model of the game when the game
 * is in a World state. 
 * 
 * @author Jeremie Beaudoin
 */
public class ObjectHandlerInWorld {
	
	// The instances of the MVC
	private WorldImageLoader worldImageLoader;
	private CollisionHandler collisionHandler;

	// The Menu
    private StackLinked<Menu> allMenus;

	/**
	 * Basic constructor for the Model component of the World
	 *
	 * @throws IOException if an image is missing
	 */
	public ObjectHandlerInWorld() throws IOException {
		
		// Handles images objects
		worldImageLoader = new WorldImageLoader();

		// Handles collisions
        collisionHandler = new CollisionHandler();

        allMenus = new StackLinked<>();
	}

    /**
     * Called every frame
     */
	public void update() {
	    if (getCurrentMenu() == null) {
            getMap().update();
        }
    }
	
	/**
	 * @return all physical object to show in the game
	 */
	public LinkedList<PhysicalObject> get() {

        LinkedList<PhysicalObject> allObjects = new LinkedList<>();

        allObjects.addAll(handleAndGetMapObjects());

        if (getCurrentMenu() != null) {
            allObjects.addAll(handleAndGetMapObjects());
        }

		return allObjects;
	}

	private List<PhysicalObject> handlerAndGetMenuObjects() {
	    LinkedList<PhysicalObject> allObjects = new LinkedList<>();

	    return allObjects;
    }

    /**
     * Places all object in order of appearance on the screen.
     * This ensures that an object which is supposed to be "under" another will be added first.
     *
     * @return all physical objects that represent the map of the game
     */
	private List<PhysicalObject> handleAndGetMapObjects() {
        Map currentMap = getMap(Player.currentMapID);

        LinkedList<PhysicalObject> allObjects = new LinkedList<>();

        addAllIfNotNull(allObjects, currentMap.getBackgroundObjects());
        allObjects.add(ShadowFactory.getPlayerShadow());
        addAllIfNotNull(allObjects, worldImageLoader.getAllEnemyShadows());
        addAllIfNotNull(allObjects, currentMap.getShadowObjects());
        addAllIfNotNull(allObjects, getAllMovingObjectsInOrder(currentMap));

        addAllIfNotNull(allObjects, currentMap.getFloatingObjects());

        return allObjects;
    }

    private <E> void addAllIfNotNull(List<E> list, Collection<? extends E> c) {
        if (c != null) {
            list.addAll(c);
        }
    }

    /**
     * Sorts and returns all moving objects in order
     */
	private List<PhysicalObject> getAllMovingObjectsInOrder(Map currentMap){
	    List<PhysicalObject> allMovingObjects = new LinkedList<>();

	    allMovingObjects.add(worldImageLoader.getPlayerObject());
	    allMovingObjects.addAll(currentMap.getSolidObjects());
        allMovingObjects.addAll(worldImageLoader.getAllEnemyImages());

	    Collections.sort(allMovingObjects);

	    return allMovingObjects;
    }

    public void addNewMenu(Menu menu) {
	    allMenus.push(menu);
    }

    public void quitCurrentMenu() {
	    allMenus.pop();
    }

    /**
     * @return the current menu
     */
    public Menu getCurrentMenu() {
	    if (!allMenus.isEmpty()) {
	        return allMenus.peek();
        }
        return null;
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
        return collisionHandler.getSolidObjectCollision(new PhysicalObject(northEast, southWest),
                getMap().getShadowObjects()) != null;
    }

    Enemy getEnemyCollision(Position northEast, Position southWest) {

        return collisionHandler.getCollisionWithEnemy(new PhysicalObject(northEast, southWest),
                getMap().getAllEnemies());
    }

    Door getDoorCollision (Position northEast, Position southWest) {
        return collisionHandler.getCollisionWithDoor(new PhysicalObject(northEast, southWest),
                getMap().getAllDoors());
    }
}
