package world;

import combat.combatAI.AISinglePokeshinto;
import images.CardinalDirection;
import images.PhysicalObject;
import images.Position;
import combat.InfoHandler;
import world.worldObjects.Door;
import world.worldObjects.Enemy;
import world.worldObjects.EnemyRoaming;

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
		Map currentMap;
		String mapID;
		
		allMaps = new HashMap<>();

        mapID = WorldMapArrayData.FIRST_SAND_MAP_ID;
		currentMap = getFirstSandMap(mapID);
		
		allMaps.put(mapID, currentMap);

        mapID = WorldMapArrayData.FIRST_CITY_MAP_ID;
        currentMap = getFirstCityMap(mapID);

        allMaps.put(mapID, currentMap);

	}

	private Map getFirstSandMap(String mapID) {
        int[][] background = mapData.getBackground(mapID);
        int[][] foreground = mapData.getForeground(mapID);
        int[][] solidObjects = mapData.getSolidObject(mapID);
        int[][] floatingObjects = mapData.getFloatingObject(mapID);
        LinkedList<Door> doors = new LinkedList<>();
        LinkedList<Enemy> enemies = new LinkedList<>();

        PhysicalObject currentEnemy = worldMapObjectInformation.getBasicEnemy();
        PhysicalObject enemyShadow = worldMapObjectInformation.getBasicEnemyShadow();

        currentEnemy.setPosition(new Position(1392, 192));
        enemies.add(new EnemyRoaming(currentEnemy, enemyShadow, 2, new AISinglePokeshinto("Kaizoyu",
                InfoHandler.getBasicCombatAttributes(), 100), new CardinalDirection(96, 288, 1296, 1488)));

        currentEnemy.setPosition(new Position(1584, 144));
        enemies.add(new EnemyRoaming(currentEnemy, enemyShadow, 2, new AISinglePokeshinto("Kaizoyu",
                InfoHandler.getBasicCombatAttributes(), 100), new CardinalDirection(48, 192, 1488, 1680)));

        currentEnemy.setPosition(new Position(1536, 384));
        enemies.add(new EnemyRoaming(currentEnemy, enemyShadow, 2, new AISinglePokeshinto("Kaizoyu",
                InfoHandler.getBasicCombatAttributes(), 100), new CardinalDirection(288, 480, 1440, 1632)));

        currentEnemy.setPosition(new Position(1680, 336));
        enemies.add(new EnemyRoaming(currentEnemy, enemyShadow, 2, new AISinglePokeshinto("Kaizoyu",
                InfoHandler.getBasicCombatAttributes(), 100), new CardinalDirection(240, 384, 1584, 1776)));

        currentEnemy.setPosition(new Position(1728, 528));
        enemies.add(new EnemyRoaming(currentEnemy, enemyShadow, 2, new AISinglePokeshinto("Kaizoyu",
                InfoHandler.getBasicCombatAttributes(), 100), new CardinalDirection(432, 624, 1632, 1824)));

        currentEnemy.setPosition(new Position(1872, 384));
        enemies.add(new EnemyRoaming(currentEnemy, enemyShadow, 2, new AISinglePokeshinto("Kaizoyu",
                InfoHandler.getBasicCombatAttributes(), 100), new CardinalDirection(336, 432, 1824, 1920)));

        currentEnemy.setPosition(new Position(1872, 240));
        enemies.add(new EnemyRoaming(currentEnemy, enemyShadow, 2, new AISinglePokeshinto("Kaizoyu",
                InfoHandler.getBasicCombatAttributes(), 100), new CardinalDirection(144, 336, 1776, 1968)));

        currentEnemy.setPosition(new Position(2016, 336));
        enemies.add(new EnemyRoaming(currentEnemy, enemyShadow, 2, new AISinglePokeshinto("Kaizoyu",
                InfoHandler.getBasicCombatAttributes(), 100), new CardinalDirection(288, 384, 1968, 2064)));

        currentEnemy.setPosition(new Position(2064, 192));
        enemies.add(new EnemyRoaming(currentEnemy, enemyShadow, 2, new AISinglePokeshinto("Kaizoyu",
                InfoHandler.getBasicCombatAttributes(), 100), new CardinalDirection(144, 240, 2160, 2256)));

        currentEnemy.setPosition(new Position(2112, 528));
        enemies.add(new EnemyRoaming(currentEnemy, enemyShadow, 2, new AISinglePokeshinto("Kaizoyu",
                InfoHandler.getBasicCombatAttributes(), 100), new CardinalDirection(480, 528, 2016, 2256)));

        PhysicalObject currentDoor = worldMapObjectInformation.getBasicDoor();
        currentDoor.setPosition(new Position(2352, 384));
        doors.add(new Door(currentDoor, WorldMapArrayData.FIRST_CITY_MAP_ID, new Position(48, 576)));

        return new Map(mapID, background, foreground, solidObjects, floatingObjects, enemies, doors);
    }

    private Map getFirstCityMap(String mapID) {
        int[][] background = mapData.getBackground(mapID);
        int[][] solidObjects = mapData.getSolidObject(mapID);
        int[][] foreground = mapData.getForeground(mapID);
        int[][] floatingObjects = mapData.getFloatingObject(mapID);
        LinkedList<Door> doors = new LinkedList<>();
        LinkedList<Enemy> enemies = new LinkedList<>();

        PhysicalObject currentDoor = worldMapObjectInformation.getBasicDoor();

        currentDoor.setPosition(new Position(0, 576));
        doors.add(new Door(currentDoor, WorldMapArrayData.FIRST_SAND_MAP_ID, new Position(2304, 384)));

        return new Map(mapID, background, foreground, solidObjects, floatingObjects, enemies, doors);
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
