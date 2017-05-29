package world;

import combat.AISinglePokeshinto;
import images.CardinalDirection;
import images.PhysicalObject;
import images.Position;
import pokeshinto.InfoHandler;

import java.util.LinkedList;
import java.util.HashMap;

/**
 * This object has all the information concerning world map. Upon get()
 * call, it will return the correct Map for creating the world map with
 * the current tileset.
 * 
 * @author Jérémie Beaudoin-Dion
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

		PhysicalObject enemy = new PhysicalObject(new Position(120, 120),
                WorldImageLoader.TILE_WIDTH, WorldImageLoader.TILE_WIDTH);
		PhysicalObject enemyShadow = new PhysicalObject(new Position(enemy.getWidth()/6, enemy.getHeight()*2/3),
                enemy.getWidth()*5/6, enemy.getHeight()/3);

		enemies.add(new EnemyRoaming(enemy, enemyShadow, 2, new AISinglePokeshinto("Kohadai",
                InfoHandler.getBasicCombatAttributes(), 100), new CardinalDirection(80, 260, 80, 270)));
		
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
