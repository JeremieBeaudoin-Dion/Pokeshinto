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
	
	private ArrayList<PhysicalObject> backgroundObjects;
	private ArrayList<PhysicalObject> solidObjects;
    private ArrayList<PhysicalObject> floatingObjects;
	private ArrayList<Door> allDoors;
	
	private int[][] background;
	private int[][] floating;
    private int[][] solid;

	private int xSize;
	private int ySize;
	
	/**
	 * Constructor
	 * 
	 * @param background: the ID of each tiles making the background
	 * @param solid: the ID of each tiles that are impenetrable
	 * @param floating: the ID of each tiles that are floating over the player
	 * @param allDoors: an array of all the doors of the map
	 */
	public Map(String ID, int[][] background, int[][] solid, int[][] floating, ArrayList<Door> allDoors) {
		
		this.ID = ID;
		
		this.background = background;
		this.solid = solid;
        this.floating = floating;
		
		ySize = background.length;
		xSize = background[0].length;

		this.allDoors = allDoors;		
		
	}
	
	/**
	 * Must be called before putting the map on frame
	 * 
	 * @param objects the PhysicalObjects to show on the frame
	 */
	public void setBackgroundObjects(ArrayList<PhysicalObject> objects) {
		this.backgroundObjects = objects;
	}

	public void setSolidObjects(ArrayList<PhysicalObject> objects) {
		this.solidObjects = objects;
	}

    public void setFloatingObjects(ArrayList<PhysicalObject> objects) {
        this.floatingObjects = objects;
    }
	
	// Getters
	public String getID() {
		return ID;
	}

	public ArrayList<PhysicalObject> getBackgroundObjects() {
		return backgroundObjects;
	}

	public ArrayList<PhysicalObject> getSolidObjects() {
		return solidObjects;
	}

    public ArrayList<PhysicalObject> getFloatingObjects() {
        return floatingObjects;
    }

	public ArrayList<Door> getAllDoors() {
		return allDoors;
	}

	public int[][] getBackground() {
		return background;
	}

	public int[][] getFloating() {
		return floating;
	}

	public int[][] getSolid() {
	    return solid;
    }

	public int getxSize() {
		return xSize;
	}

	public int getySize() {
		return ySize;
	}
	
}
