package world;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This object has all the information concerning world map. Upon get()
 * call, it will return the correct Map for creating the world map with
 * the current tileset.
 * 
 * @author Jeremie Beaudoin
 *
 */
public class WorldMapCreator {
	
	public static ArrayList<Fact<Integer>> TRUTH_TABLE;
	public final static int TOTAL_NUMBER_OF_TILES = 250;
	
	private HashMap<String, Map> allMaps;
	
	/**
	 * The constructor sets the truth table of the WorldMapCreator
	 */
	public WorldMapCreator() {
		setTruthTable();
		setMaps();
	}
	
	/**
	 * Helper method
	 */
	private void setTruthTable() {
		TRUTH_TABLE = new ArrayList<Fact<Integer>>();
		
		Fact<Integer> current;
		boolean isTrue;
		
		for(Integer i=0; i<TOTAL_NUMBER_OF_TILES; i++) {
			if (i % 20 == 10 || i % 20 == 11 || i % 20 == 12) {
				isTrue = false;
			} else {
				isTrue = true;
			}
			current = new Fact<Integer>(i, isTrue);
			TRUTH_TABLE.add(current);
		}
	}
	
	/**
	 * Helper method to create all maps
	 * 
	 * Background and Foreground must be of same Size
	 */
	private void setMaps() {
		String mapID;
		int[][] background;
		int[][] foreground;
		boolean[][] collisions;
		ArrayList<Door> doors;
		
		Map currentMap;
		
		allMaps = new HashMap<String, Map>();
		
		// First map for testing
		mapID = "Test";
		background = new int[][] {{0, 1, 2}, {20, 21, 22}, {40, 41, 42}};
		foreground = new int[][] {{-1, 200, -1}, {-1, 220, -1}, {-1, -1, -1}};
		doors = null;
		collisions = setCollisions(background, foreground);
		
		currentMap = new Map(mapID, background, foreground, collisions, doors);
		
		allMaps.put(mapID, currentMap);
		
		
		
	}
	
	/**
	 * Returns an array of boolean representing the collisions of the map
	 * 
	 * @param background
	 * @param foreground
	 * @return
	 */
	private boolean[][] setCollisions(int[][] background, int[][] foreground) {
		
		boolean[][] collisions = new boolean[background.length][background[0].length];
		
		boolean currentBackgroundCollision;
		boolean currentForegroundCollision;
		
		for (int y=0; y<background.length; y++) {
			for (int x=0; x<background.length; x++) {
				currentBackgroundCollision = TRUTH_TABLE.get(background[y][x]).isTrue();
				
				if (foreground[y][x] != -1) {
					currentForegroundCollision = TRUTH_TABLE.get(foreground[y][x]).isTrue();
				} else {
					currentForegroundCollision = true;
				}
				
				if (!currentBackgroundCollision || !currentForegroundCollision) {
					collisions[y][x] = false;
				} else {
					collisions[y][x] = true;;
				}
			}
		}
		
		return collisions;
	}
	
	/**
	 * Returns the desired Map. ATTENTION it is still incomplete
	 * 
	 * @param mapID
	 * @return
	 */
	public Map get(String mapID) {
		return allMaps.get(mapID);
	}
	
}
