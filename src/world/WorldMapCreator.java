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

	private HashMap<String, Map> allMaps;
	private WorldMapArrayData mapData;

	private WorldMapObjectInformation worldMapObjectInformation;

	/**
	 * The constructor sets the truth table of the WorldMapCreator
	 */
	WorldMapCreator() {
	    mapData = new WorldMapArrayData();

        worldMapObjectInformation = new WorldMapObjectInformation();

		setMaps();
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
		doors = new LinkedList<>();
		enemies = new LinkedList<>();

		PhysicalObject currentEnemy = worldMapObjectInformation.getBasicEnemy();
		PhysicalObject enemyShadow = worldMapObjectInformation.getBasicEnemyShadow();

        currentEnemy.setPosition(new Position(120, 120));
		enemies.add(new EnemyRoaming(currentEnemy, enemyShadow, 2, new AISinglePokeshinto("Kohadai",
                InfoHandler.getBasicCombatAttributes(), 100), new CardinalDirection(80, 260, 80, 270)));

		PhysicalObject currentDoor = worldMapObjectInformation.getBasicDoor();

		currentDoor.setPosition(new Position(80, 80));
		doors.add(new Door(currentDoor, "Test", new Position(0, 0)));

		currentMap = new Map(mapID, background, solidObjects, floatingObjects, enemies, doors);
		
		allMaps.put(mapID, currentMap);

		// First Temple of the Game
		mapID = "FirstTemple";
		background = mapData.getBackground(mapID);
		solidObjects = mapData.getSolidObject(mapID);
		floatingObjects = mapData.getFloatingObject(mapID);
        doors = new LinkedList<>();
		enemies = new LinkedList<>();

        currentDoor.setPosition(new Position(4 * WorldImageLoader.TILE_WIDTH, 9 * WorldImageLoader.TILE_WIDTH));
        doors.add(new Door(currentDoor, "FirstForest", new Position(8 * WorldImageLoader.TILE_WIDTH,
                9 * WorldImageLoader.TILE_WIDTH)));

        currentDoor.setPosition(new Position(5 * WorldImageLoader.TILE_WIDTH, 9 * WorldImageLoader.TILE_WIDTH));
        doors.add(new Door(currentDoor, "FirstForest", new Position(9 * WorldImageLoader.TILE_WIDTH,
                9 * WorldImageLoader.TILE_WIDTH)));

        currentMap = new Map(mapID, background, solidObjects, floatingObjects, enemies, doors);

        allMaps.put(mapID, currentMap);

		// First Forest of the Game
		mapID = "FirstForest";
		background = mapData.getBackground(mapID);
		solidObjects = mapData.getSolidObject(mapID);
		floatingObjects = mapData.getFloatingObject(mapID);
        doors = new LinkedList<>();
		enemies = new LinkedList<>();

		currentEnemy = worldMapObjectInformation.getBasicEnemy();
		enemyShadow = worldMapObjectInformation.getBasicEnemyShadow();

		currentEnemy.setPosition(new Position(768, 1344));
		enemies.add(new EnemyRoaming(currentEnemy, enemyShadow, 2, new AISinglePokeshinto("Kohadai",
				InfoHandler.getBasicCombatAttributes(), 100), new CardinalDirection(1216, 1594, 642, 1316)));

        currentEnemy.setPosition(new Position(960, 960));
        enemies.add(new EnemyRoaming(currentEnemy, enemyShadow, 2, new AISinglePokeshinto("Kohadai",
                InfoHandler.getBasicCombatAttributes(), 100), new CardinalDirection(834, 1152, 896, 1152)));

        currentEnemy.setPosition(new Position(1152, 1152));
        enemies.add(new EnemyRoaming(currentEnemy, enemyShadow, 2, new AISinglePokeshinto("Kohadai",
                InfoHandler.getBasicCombatAttributes(), 100), new CardinalDirection(1026, 1344, 1026, 1344)));

        currentEnemy.setPosition(new Position(1409, 1026));
        enemies.add(new EnemyRoaming(currentEnemy, enemyShadow, 2, new AISinglePokeshinto("Kohadai",
                InfoHandler.getBasicCombatAttributes(), 100), new CardinalDirection(898, 1152, 1216, 1595)));

        currentEnemy.setPosition(new Position(1344, 1410));
        enemies.add(new EnemyRoaming(currentEnemy, enemyShadow, 2, new AISinglePokeshinto("Kohadai",
                InfoHandler.getBasicCombatAttributes(), 100), new CardinalDirection(1282, 1595, 1154, 1595)));

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
