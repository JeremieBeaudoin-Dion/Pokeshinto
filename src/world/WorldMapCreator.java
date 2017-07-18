package world;

import combat.combatAI.AISinglePokeshinto;
import images.CardinalDirection;
import images.PhysicalObject;
import images.Position;
import combat.InfoHandler;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.HashMap;

/**
 * This object has all the information concerning world map. Upon get()
 * call, it will return the correct Map for creating the world map with
 * the current tileset.
 *
 * The maps are all instanciated at the start. So, any change to these
 * maps will stay.
 * 
 * @author Jérémie Beaudoin-Dion
 *
 */
public class WorldMapCreator implements Serializable {

	private HashMap<String, Map> allMaps;
	private WorldMapArrayData mapData;

	private WorldMapObjectInformation worldMapObjectInformation;

	/**
	 * The constructor sets the truth table of the WorldMapCreator
	 */
	public WorldMapCreator() {
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
		int[][] foreground;
		int[][] solidObjects;
		int[][] floatingObjects;
		LinkedList<Door> doors;
		LinkedList<Enemy> enemies;
		
		Map currentMap;
		
		allMaps = new HashMap<>();
		
		// First map for testing
		mapID = "Sand";
		background = mapData.getBackground(mapID);
		foreground = mapData.getForeground(mapID);
		solidObjects = mapData.getSolidObject(mapID);
		floatingObjects = mapData.getFloatingObject(mapID);
		doors = new LinkedList<>();
		enemies = new LinkedList<>();

		PhysicalObject currentEnemy = worldMapObjectInformation.getBasicEnemy();
		PhysicalObject enemyShadow = worldMapObjectInformation.getBasicEnemyShadow();

        currentEnemy.setPosition(new Position(1392, 192));
		enemies.add(new EnemyRoaming(currentEnemy, enemyShadow, 2, new AISinglePokeshinto("Kaizoyu",
                InfoHandler.getBasicCombatAttributes(), 100), new CardinalDirection(96, 288, 1296, 1488)));

        currentEnemy.setPosition(new Position(1584, 144));
        enemies.add(new EnemyRoaming(currentEnemy, enemyShadow, 2, new AISinglePokeshinto("Kaizoyu",
                InfoHandler.getBasicCombatAttributes(), 100), new CardinalDirection(48, 192, 1488, 1680)));

		PhysicalObject currentDoor = worldMapObjectInformation.getBasicDoor();
		currentDoor.setPosition(new Position(2352, 384));
		doors.add(new Door(currentDoor, "Test", new Position(0, 0)));

		currentMap = new Map(mapID, background, foreground, solidObjects, floatingObjects, enemies, doors);
		
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

	public HashMap<String, Map> getAllMaps() {
		return allMaps;
	}
	
}
