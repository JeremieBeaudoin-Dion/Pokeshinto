package world;

/**
 * This object has all the information concerning world map. Upon get()
 * call, it will return the correct array for creating the world map with
 * the current tileset.
 * 
 * @author Jeremie Beaudoin
 *
 */
public class WorldMapCreator {
	
	public Map get(int mapID) {
		return null;
	}
	
	/**
	 * Return an array representing the background of the map
	 * 
	 * @param menuId: the current map to load
	 * @return
	 */
	public int[][] getBackground(int mapId) {
		
		int[][] test = new int[][] {{0, 1, 2}, {20, 21, 22}, {40, 41, 42}};
		
		return test;
	}
	
	/**
	 * Return an array representing the foreground of the map
	 * 
	 * @param menuId
	 * @return
	 */
	public int[][] getForeground(int mapId) {
		return null;
	}
	
}
