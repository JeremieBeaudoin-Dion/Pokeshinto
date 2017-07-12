package world;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

/**
 * A Map object is a world object which contains information to
 * represent the current map, and its events.
 * 
 * @author Jérémie Beaudoin-Dion
 *
 */
class Map implements Serializable {
	
	private String ID;

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

    void destroyEnemy(Enemy enemy) {
    	allEnemies.remove(enemy);
	}

	// Getters
	String getID() {
		return ID;
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

	int getxSize() {
		return xSize;
	}

	int getySize() {
		return ySize;
	}

}
