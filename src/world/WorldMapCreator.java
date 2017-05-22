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
		TRUTH_TABLE = new ArrayList<>();
		
		Fact<Integer> current;
		boolean isTrue;
		
		for(Integer i=0; i<TOTAL_NUMBER_OF_TILES; i++) {
			isTrue = !(i % 20 == 10 || i % 20 == 11 || i % 20 == 12);
			current = new Fact<>(i, isTrue);
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
		int[][] solidObjects;
		int[][] floatingObjects;
		ArrayList<Door> doors;
		
		Map currentMap;
		
		allMaps = new HashMap<>();
		
		// First map for testing
		mapID = "Test";
		background = new int[][] {{5, 6, 7}, {25, 26, 27}, {45, 46, 47}};
		solidObjects = new int[][] {{-1, -1, -1}, {-1, 220, -1}, {-1, -1, -1}};
		floatingObjects = new int[][] {{-1, 200, -1}, {-1, -1, -1}, {-1, -1, -1}};
		doors = null;
		
		currentMap = new Map(mapID, background, solidObjects, floatingObjects, doors);
		
		allMaps.put(mapID, currentMap);
		
	}
	
	/**
	 * Returns the desired Map. ATTENTION it is still incomplete
	 * 
	 * @param mapID : the name of the desired map
	 */
	public Map get(String mapID) {
		return allMaps.get(mapID);
	}
	
}
