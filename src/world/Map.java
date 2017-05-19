package world;

import java.util.ArrayList;

import images.PhysicalObject;

/**
 * A Map object is a world object which contains the images to
 * represent the current map, and its events.
 * 
 * @author Jérémie Beaudoin-Dion
 *
 */
public class Map {
	
	private String ID;
	
	private ArrayList<PhysicalObject> objects;
	private ArrayList<Door> allDoors;
	
	private int[][] background;
	private int[][] foreground;
	
	private boolean[][] collisions;
	private int xSize;
	private int ySize;
	
	/**
	 * Constructor
	 * 
	 * @param background: the ID of each tiles making the background
	 * @param foreground: the ID of each tiles making the foreground
	 * @param collisions: an array of boolean representing which terrain is passable
	 * @param allDoors: an array of all the doors of the map
	 */
	public Map(String ID, int[][] background, int[][] foreground, boolean[][] collisions, ArrayList<Door> allDoors) {
		
		this.ID = ID;
		
		this.background = background;
		this.foreground = foreground;
		
		ySize = background.length;
		xSize = background[0].length;
		
		this.collisions = collisions;
		this.allDoors = allDoors;		
		
	}
	
	/**
	 * Must be called before putting the map on frame
	 * 
	 * @param objects
	 */
	public void initiate(ArrayList<PhysicalObject> objects) {
		this.objects = objects;
	}

	
	// Getters
	public String getID() {
		return ID;
	}

	public ArrayList<PhysicalObject> getObjects() {
		return objects;
	}

	public ArrayList<Door> getAllDoors() {
		return allDoors;
	}

	public int[][] getBackground() {
		return background;
	}

	public int[][] getForeground() {
		return foreground;
	}

	public boolean[][] getCollisions() {
		return collisions;
	}

	public int getxSize() {
		return xSize;
	}

	public int getySize() {
		return ySize;
	}
	
}
