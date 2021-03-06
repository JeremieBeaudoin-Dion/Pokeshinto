package world;

import java.io.IOException;
import java.util.*;

import images.Camera;
import images.PhysicalObject;
import images.Position;
import pokeshinto.Player;
import world.worldObjects.Door;
import world.worldObjects.Enemy;
import world.worldObjects.MovingObject;

/**
 * This object handler represent the Model of the game when the game
 * is in a World state. 
 * 
 * @author Jeremie Beaudoin
 */
public class ObjectHandlerInWorld {
	
	// The instances of the MVC
	private ImageLoaderInWorld imageLoaderInWorld;
    private Camera camera;
	private CollisionHandler collisionHandler;

	/**
	 * Basic constructor for the Model component of the World
	 *
	 * @throws IOException if an image is missing
	 */
	public ObjectHandlerInWorld(WorldMapCreator worldMapCreator, Camera camera) throws IOException {
		
		// Handles images objects
		imageLoaderInWorld = new ImageLoaderInWorld(worldMapCreator);

		// Handles collisions
        collisionHandler = new CollisionHandler();

        this.camera = camera;
	}

    /**
     * Called every frame
     */
	public void update() {
        getMap().update();
    }
	
	/**
	 * @return all physical object to show in the game
	 */
	public List<PhysicalObject> get() {

        List<PhysicalObject> allObjects = new LinkedList<>();

        allObjects.addAll(handleAndGetMapObjects());

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

        addAllIfNotNull(allObjects, imageLoaderInWorld.getMapImages().getBackgroundObjects());
        addAllIfNotNull(allObjects, imageLoaderInWorld.getMapImages().getForegroundObjects());
        allObjects.add(ShadowFactory.getPlayerShadow());
        addAllIfNotNull(allObjects, imageLoaderInWorld.getAllMovingObjectsShadows());
        addAllIfNotNull(allObjects, imageLoaderInWorld.getMapImages().getShadowObjects());
        addAllIfNotNull(allObjects, getAllMovingObjectsInOrder());

        addAllIfNotNull(allObjects, imageLoaderInWorld.getMapImages().getFloatingObjects());

        allObjects.addAll(imageLoaderInWorld.getPlayerHealthBar(camera));
        allObjects.addAll(imageLoaderInWorld.getPlayerMoney(camera));

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
	private List<PhysicalObject> getAllMovingObjectsInOrder(){
	    List<PhysicalObject> allMovingObjects = new LinkedList<>();

	    allMovingObjects.add(imageLoaderInWorld.getPlayerObject());
	    allMovingObjects.addAll(imageLoaderInWorld.getMapImages().getSolidObjects());
        allMovingObjects.addAll(imageLoaderInWorld.getAllMovingObjectsImages());

	    Collections.sort(allMovingObjects);

	    return allMovingObjects;
    }

    public void destroyEnemyObject(Enemy enemy) {
	    getMap().destroyEnemy(enemy);
    }

    /**
     * @param mapID: the name of the desired map
     * @return the map representation according to mapID
     */
    private Map getMap(String mapID) {
        return imageLoaderInWorld.getMap(mapID);
    }

    /**
     * @return the current map Object
     */
    public Map getMap() {
        return imageLoaderInWorld.getMap();
    }

    /**
     * @return true if the positions are out of the current map's size
     */
    boolean getIsOutOfBounds(Position northEast, Position southWest) {
        return collisionHandler.getIsOutOfBounds(northEast, southWest);
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
    boolean getSolidObjectCollision(Position northEast, Position southWest) {
        return collisionHandler.getSolidObjectCollision(new PhysicalObject(northEast, southWest),
                imageLoaderInWorld.getMapImages().getShadowObjects()) != null;
    }

    /**
     * @return the object colliding with the points
     */
    MovingObject getMovingObjectCollision(Position northEast, Position southWest) {

        return collisionHandler.getCollisionWithEnemy(new PhysicalObject(northEast, southWest),
                getMap().getAllMovingObjects());
    }

    /**
     * @return the Door colliding with the points
     */
    Door getDoorCollision (Position northEast, Position southWest) {
        return collisionHandler.getCollisionWithDoor(new PhysicalObject(northEast, southWest),
                getMap().getAllDoors());
    }

    String getPlayerFacingBackground(Position northEast, Position southWest) {
        if (collisionHandler.getWaterCollision(northEast, southWest, getMap().getBackground())){
            return "Water";
        }

        return null;
    }
}
