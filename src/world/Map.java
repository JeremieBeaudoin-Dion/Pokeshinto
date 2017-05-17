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

	private int size;
	
	private int[] backgroundData;
	private int[] foregroundData;
	
	private ArrayList<PhysicalObject> objects;
	
	private boolean[][] collisions;
	
	private boolean passable;
	
	
	
	
	
	
}
