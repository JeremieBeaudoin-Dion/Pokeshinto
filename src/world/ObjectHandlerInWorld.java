package world;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import images.Image;
import images.PhysicalObject;
import images.Point;
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
	private ActionHandlerInWorld actionHandlerInWorld;
	private Player player;
	private WorldImageLoader worldImageLoader;
	
	// Width and height of the game
	private int windowWidth = Game.WINDOW_WIDTH;
	private int windowHeight = Game.WINDOW_HEIGHT;
	
	/**
	 * Basic constructor for the Model component of the World
	 * 
	 * @param actionHandlerInWorld: the controller instance in world
	 * @param player: the player instance of the game
	 * @throws IOException
	 */
	public ObjectHandlerInWorld(ActionHandlerInWorld actionHandlerInWorld, Player player) throws IOException {
		
		// MVC component
		this.actionHandlerInWorld = actionHandlerInWorld;
		this.player = player;
		
		// Handles images objects
		worldImageLoader = new WorldImageLoader();
		
	}
	
	/**
	 * Returns all physical object to show in the game
	 * 
	 * @return
	 */
	public ArrayList<PhysicalObject> get() {
		
		ArrayList<PhysicalObject> game = new ArrayList<PhysicalObject>();
		BufferedImage[][] background = worldImageLoader.getBackground(0);
		int size = WorldImageLoader.TILE_WIDTH;
		
		for (int y=0; y<background.length; y++) {
			for (int x=0; x<background[y].length; x++) {
				
				game.add(new Image(new Point(x * size, y * size), size, size, background[y][x]));
			}
		}
		
		game.add(new Image(player.getPosition(), 48, 48, worldImageLoader.getPlayer()));
		
		return game;
	}

}
