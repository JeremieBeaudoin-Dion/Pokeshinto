package world;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

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
	
	private BufferedImage[][] background;
	private BufferedImage[][] foreground;
	
	private BufferedImage player;
	private BufferedImage playerTile;
	
	/**
	 * Basic constructor
	 * @throws IOException
	 */
	public WorldImageLoader() throws IOException {
		
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
	 * Returns the images representing the current background
	 * 
	 * @param mapID
	 * @return
	 */
	public BufferedImage[][] getBackground(int mapID) {
		if (mapID != currentMapID){
			createMapImage(mapID);
		}
		
		return background;
	}
	
	/**
	 * Returns the images representing the current foreground
	 * 
	 * @return
	 */
	public BufferedImage[][] getForeground() {
		return foreground;
	}
	
	/**
	 * Creates the array of images representing the new Background and foreground.
	 * 
	 * @param mapID
	 */
	private void createMapImage(int mapID) {
		currentMapID = mapID;
		
		background = transformTilesToImages(worldMapCreator.getBackground(currentMapID));
		//foreground = transformTilesToImages(worldMapCreator.getForeground(currentMapID));
		
	}
	
	/**
	 * Takes an array of int and transforms it into the correct array of images
	 * 
	 * @param tiles; a square array of int representing the id of each image
	 * @return
	 */
	private BufferedImage[][] transformTilesToImages(int[][] tiles) {
		
		BufferedImage[][] images = new BufferedImage[tiles.length][tiles[0].length];
		
		for (int y=0; y<tiles.length; y++) {
			
			for (int x=0; x<tiles[y].length; x++) {
				
				int xpos = tiles[y][x] % NUMBER_OF_TILES_IN_TILESET * TILE_WIDTH;
				int ypos = ((int) tiles[y][x] / NUMBER_OF_TILES_IN_TILESET) * TILE_WIDTH; 
				
				images[y][x] = worldTileSet.getSubimage(xpos, ypos, TILE_WIDTH, TILE_WIDTH);
				
			}
			
		}
		
		return images;
	}
	
	public BufferedImage getPlayer() {
		return player;
	}

}
