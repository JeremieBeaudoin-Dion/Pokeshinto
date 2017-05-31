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
