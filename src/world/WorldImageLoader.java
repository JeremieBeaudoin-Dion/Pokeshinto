package world;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import images.Image;
import images.PhysicalObject;
import images.Point;

/**
 * Loads all necessary images for the ObjectHandlerInWorld
 * 
 * @author Jeremie Beaudoin-Dion
 *
 */
public class WorldImageLoader {
	
	public final static int TILE_WIDTH = 64;
	public final static int NUMBER_OF_TILES_IN_TILESET = 20;
	
	private BufferedImage worldTileSet;
	
	private BufferedImage player;
	private BufferedImage playerTile;
	
	private WorldMapCreator worldMapCreator;
	private Map currentMap;
	
	/**
	 * Basic constructor
	 * @throws IOException
	 */
	public WorldImageLoader() throws IOException {
		
		worldMapCreator = new WorldMapCreator();
		
		loadAllImages();
		
	}
	
	/**
	 * Loads all image from file
	 * @throws IOException 
	 */
	private void loadAllImages() throws IOException {
		
		worldTileSet = ImageIO.read(new File("TileSet.png"));
		playerTile = ImageIO.read(new File("charSheet_48.png"));
		player = playerTile.getSubimage(0, 0, 48, 48);
		
	}
	
	/**
	 * Returns the desired map
	 * 
	 * @param mapID
	 * @return
	 */
	public Map getMap(String mapID) {
		if (currentMap == null || !currentMap.getID().equals(mapID)) {
			
			currentMap = worldMapCreator.get(mapID);
			
			// The Images are still not instanciated
			ArrayList<PhysicalObject> objects = new ArrayList<PhysicalObject>();
			objects = transformTilesToImages(objects, currentMap.getBackground());
			objects = transformTilesToImages(objects, currentMap.getForeground());
			
			currentMap.initiate(objects);
		}
		
		return currentMap;
	}
	
	/**
	 * Takes an array of int and transforms it into the correct array of images
	 * 
	 * @param tiles; a square array of int representing the id of each image
	 * @return
	 */
	private ArrayList<PhysicalObject> transformTilesToImages(ArrayList<PhysicalObject> objects, int[][] tiles) {
		
		BufferedImage image;
		
		for (int y=0; y<tiles.length; y++) {
			for (int x=0; x<tiles[y].length; x++) {
				
				int xpos = tiles[y][x] % NUMBER_OF_TILES_IN_TILESET * TILE_WIDTH;
				int ypos = ((int) tiles[y][x] / NUMBER_OF_TILES_IN_TILESET) * TILE_WIDTH; 
				
				image = worldTileSet.getSubimage(xpos, ypos, TILE_WIDTH, TILE_WIDTH);
				
				objects.add(new Image(new Point(xpos, ypos), TILE_WIDTH, TILE_WIDTH, image));
			}
			
		}
		
		return objects;
	}
	
	/**
	 * Returns the player image
	 * 
	 * @return
	 */
	public BufferedImage getPlayer() {
		return player;
	}

}
