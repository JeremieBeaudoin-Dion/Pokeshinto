package world;

import java.io.IOException;
import java.util.ArrayList;

import images.PhysicalObject;
import pokeshinto.Game;
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
	
	// Width and height of the game
	private int windowWidth = Game.WINDOW_WIDTH;
	private int windowHeight = Game.WINDOW_HEIGHT;
	
	/**
	 * Basic constructor for the Model component of the World
	 *
	 * @param player: the player instance of the game
	 * @throws IOException
	 */
	public ObjectHandlerInWorld(Player player) throws IOException {
		
		// MVC component
		this.player = player;
		
		// Handles images objects
		worldImageLoader = new WorldImageLoader();
		
	}
	
	/**
	 * Returns the map representation according to mapID
	 * 
	 * @param mapID: the name of the desired map
	 * @return
	 */
	public Map getMap(String mapID) {
		return worldImageLoader.getMap(mapID);
	}
	
	/**
	 * Returns all physical object to show in the game
	 * 
	 * @return
	 */
	public ArrayList<PhysicalObject> get() {
		return getMap(player.getCurrentMapID()).getObjects();
	}

}
