package world;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import images.Image;
import images.PhysicalObject;
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
		
	}
	
	/**
	 * @return all physical object to show in the game
	 */
	public ArrayList<PhysicalObject> get() {

	    Map currentMap = getMap(player.getCurrentMapID());

        ArrayList<PhysicalObject> background = currentMap.getBackgroundObjects();
        ArrayList<PhysicalObject> solid = currentMap.getSolidObjects();
        ArrayList<PhysicalObject> floating = currentMap.getFloatingObjects();

        ArrayList<PhysicalObject> allObjects = new ArrayList<>();

        allObjects.addAll(background);
        addPlayerObjectAccordingToPosition(solid, allObjects);
        allObjects.addAll(floating);

		return allObjects;
	}

    /**
     * Helper method to put the player object in the right position
     *
     * @param solid all of the solid objects
     */
	private void addPlayerObjectAccordingToPosition(ArrayList<PhysicalObject> solid, ArrayList<PhysicalObject> allObjects) {
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

}
