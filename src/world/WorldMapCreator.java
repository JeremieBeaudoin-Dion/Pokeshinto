package world;

import combat.AISinglePokeshinto;
import images.Position;
import pokeshinto.InfoHandler;

import java.util.LinkedList;
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
	
	public static LinkedList<Fact<Integer>> TRUTH_TABLE;
	public final static int TOTAL_NUMBER_OF_TILES = 250;
	
	private HashMap<String, Map> allMaps;
	private WorldMapArrayData mapData;
	
	/**
	 * The constructor sets the truth table of the WorldMapCreator
	 */
	public WorldMapCreator() {
	    mapData = new WorldMapArrayData();

		setTruthTable();
		setMaps();
	}
	
	/**
	 * Helper method
	 */
	private void setTruthTable() {
		TRUTH_TABLE = new LinkedList<>();
		
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
		LinkedList<Door> doors;
		LinkedList<Enemy> enemies;
		
		Map currentMap;
		
		allMaps = new HashMap<>();
		
		// First map for testing
		mapID = "Test";
		background = mapData.getBackground(mapID);
		solidObjects = mapData.getSolidObject(mapID);
		floatingObjects = mapData.getFloatingObject(mapID);
		doors = null;
		enemies = new LinkedList<>();

		enemies.add(new Enemy(new Position(80, 80), 64, 64, new AISinglePokeshinto("Kohadai", InfoHandler.getBasicCombatAttributes(), 100)));
		enemies.add(new Enemy(new Position(120, 160), 64, 64, new AISinglePokeshinto("Kohadai", InfoHandler.getBasicCombatAttributes(), 100)));
		enemies.add(new Enemy(new Position(80, 120), 64, 64, new AISinglePokeshinto("Kohadai", InfoHandler.getBasicCombatAttributes(), 100)));
		enemies.add(new Enemy(new Position(40, 400), 64, 64, new AISinglePokeshinto("Kohadai", InfoHandler.getBasicCombatAttributes(), 100)));
		enemies.add(new Enemy(new Position(400, 80), 64, 64, new AISinglePokeshinto("Kohadai", InfoHandler.getBasicCombatAttributes(), 100)));
		enemies.add(new Enemy(new Position(808, 80), 64, 64, new AISinglePokeshinto("Kohadai", InfoHandler.getBasicCombatAttributes(), 100)));
		enemies.add(new Enemy(new Position(80, 600), 64, 64, new AISinglePokeshinto("Kohadai", InfoHandler.getBasicCombatAttributes(), 100)));

		
		currentMap = new Map(mapID, background, solidObjects, floatingObjects, enemies, doors);
		
		allMaps.put(mapID, currentMap);

		// First Temple of the Game
		mapID = "FirstTemple";
		background = mapData.getBackground(mapID);
		solidObjects = mapData.getSolidObject(mapID);
		floatingObjects = mapData.getFloatingObject(mapID);
		doors = null;
		enemies = new LinkedList<>();

        currentMap = new Map(mapID, background, solidObjects, floatingObjects, enemies, doors);

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
