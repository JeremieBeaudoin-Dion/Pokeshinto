package world;

import java.util.LinkedList;

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
	
	private LinkedList<PhysicalObject> backgroundObjects;
	private LinkedList<PhysicalObject> solidObjects;
    private LinkedList<PhysicalObject> floatingObjects;
    private LinkedList<PhysicalObject> shadowObjects;

	private LinkedList<Door> allDoors;

	private LinkedList<Enemy> allEnemies;
	
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
	public Map(String ID, int[][] background, int[][] solid, int[][] floating, LinkedList<Enemy> allEnemies,
			   LinkedList<Door> allDoors) {
		
		this.ID = ID;
		
		this.background = background;
		this.solid = solid;
        this.floating = floating;
		
		ySize = background.length;
		xSize = background[0].length;

		this.allDoors = allDoors;
		this.allEnemies = allEnemies;
		
	}
	
	/**
	 * Must be called before putting the map on frame
	 * 
	 * @param objects the PhysicalObjects to show on the frame
	 */
	public void setBackgroundObjects(LinkedList<PhysicalObject> objects) {
		this.backgroundObjects = objects;
	}

	public void setSolidObjects(LinkedList<PhysicalObject> objects) {
		this.solidObjects = objects;
	}

    public void setFloatingObjects(LinkedList<PhysicalObject> objects) {
        this.floatingObjects = objects;
    }

    public void setShadows(LinkedList<PhysicalObject> objects) {
		this.shadowObjects = objects;
	}

	// Getters
	public String getID() {
		return ID;
	}

	public LinkedList<PhysicalObject> getBackgroundObjects() {
		return backgroundObjects;
	}

	public LinkedList<PhysicalObject> getSolidObjects() {
		return solidObjects;
	}

    public LinkedList<PhysicalObject> getFloatingObjects() {
        return floatingObjects;
    }

    public LinkedList<PhysicalObject> getShadowObjects() {
		return shadowObjects;
	}

	public LinkedList<Door> getAllDoors() {
		return allDoors;
	}

	public LinkedList<Enemy> getAllEnemies() { return allEnemies; }

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
