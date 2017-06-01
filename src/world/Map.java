package world;

import java.util.Iterator;
import java.util.List;

import images.PhysicalObject;

/**
 * A Map object is a world object which contains the images to
 * represent the current map, and its events.
 *
 * When created, the map doesn't have any Images. The tiles are instanciated in the WorldImageLoader and they are
 * stored in Lists.
 *
 * The more complex images (such as Enemy, Doors and MovableObjects) will not be instanciated.
 * They will be handled by the ObjectHandler.
 * 
 * @author Jérémie Beaudoin-Dion
 *
 */
public class Map {
	
	private String ID;

	private boolean instanciated;
	
	private List<PhysicalObject> backgroundObjects;
	private List<PhysicalObject> solidObjects;
    private List<PhysicalObject> floatingObjects;
    private List<PhysicalObject> shadowObjects;

	private List<Door> allDoors;

	private List<Enemy> allEnemies;
	
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
	Map(String ID, int[][] background, int[][] solid, int[][] floating, List<Enemy> allEnemies,
               List<Door> allDoors) {
		
		this.ID = ID;
		
		this.background = background;
		this.solid = solid;
        this.floating = floating;
		
		ySize = background.length;
		xSize = background[0].length;

		this.allDoors = allDoors;
		this.allEnemies = allEnemies;

        instanciated = false;
		
	}

    /**
     * Called every frame
     */
    void update() {

        // Updates all enemies
        Iterator<Enemy> iter = allEnemies.iterator();
        while(iter.hasNext()) {
            iter.next().update();
        }

    }
	
	/**
	 * Must be called before putting the map on frame
	 * 
	 * @param objects the PhysicalObjects to show on the frame
	 */
	void setBackgroundObjects(List<PhysicalObject> objects) {
		this.backgroundObjects = objects;
        instanciated = true;
	}

	void setSolidObjects(List<PhysicalObject> objects) {
		this.solidObjects = objects;
	}

    void setFloatingObjects(List<PhysicalObject> objects) {
        this.floatingObjects = objects;
    }

    void setShadows(List<PhysicalObject> objects) {
		this.shadowObjects = objects;
	}

	// Getters
	String getID() {
		return ID;
	}

	List<PhysicalObject> getBackgroundObjects() {

	    if (!instanciated) {
	        throw new ExceptionMapNotInstanciated("Tried to acces background objects before instanciating Map");
        }

		return backgroundObjects;
	}

	List<PhysicalObject> getSolidObjects() {
		return solidObjects;
	}

    List<PhysicalObject> getFloatingObjects() {
        return floatingObjects;
    }

    List<PhysicalObject> getShadowObjects() {
		return shadowObjects;
	}

	List<Door> getAllDoors() {
		return allDoors;
	}

	List<Enemy> getAllEnemies() {
	    return allEnemies;
	}

	int[][] getBackground() {
		return background;
	}

	int[][] getFloating() {
		return floating;
	}

	int[][] getSolid() {
	    return solid;
    }

	public int getxSize() {
		return xSize;
	}

	public int getySize() {
		return ySize;
	}
	
}
