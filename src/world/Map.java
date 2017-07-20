package world;

import world.worldObjects.Door;
import world.worldObjects.Enemy;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
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
	private List<Enemy> initialEnemies;
	
	private int[][] background;
    private int[][] foreground;
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
	Map(String ID, int[][] background, int[][] foreground, int[][] solid, int[][] floating, List<Enemy> allEnemies,
               List<Door> allDoors) {
		
		this.ID = ID;
		
		this.background = background;
        this.foreground = foreground;
		this.solid = solid;
        this.floating = floating;
		
		ySize = background.length;
		xSize = background[0].length;

		this.allDoors = allDoors;

        this.allEnemies = new LinkedList<>();
		this.initialEnemies = allEnemies;
        respawnEnemies();
		
	}

	void respawnEnemies() {
	    Iterator<Enemy> iter = initialEnemies.iterator();
	    Enemy currentEnemy;

	    while(iter.hasNext()) {
            currentEnemy = iter.next();
            currentEnemy.reset();
            allEnemies.add(currentEnemy);
        }
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

    int[][] getForeground() {
        return foreground;
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
