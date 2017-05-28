package world;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;

import images.Image;
import images.PhysicalObject;
import images.Position;
import pokeshinto.Player;

/**
 * Loads all necessary images for the ObjectHandlerInWorld
 * 
 * @author Jeremie Beaudoin-Dion
 *
 */
public class WorldImageLoader {
	
	public final static int TILE_WIDTH = 64;
	public final static int NUMBER_OF_TILES_IN_TILESET = 20;

	public final static int PLAYER_WIDTH = 48;
    public final static int PLAYER_HEIGHT = 48;
	
	private BufferedImage worldTileSet;
	
	private BufferedImage playerImage;
	private BufferedImage playerTile;

	private BufferedImage enemyImage;
	private BufferedImage enemyTile;
	
	private WorldMapCreator worldMapCreator;
	private Map currentMap;
	private ShadowFactory shadowFactory;
	
	/**
	 * Basic constructor
	 * @throws IOException : if the image is missing
	 */
	public WorldImageLoader() throws IOException {
		
		worldMapCreator = new WorldMapCreator();
		shadowFactory = new ShadowFactory();
		
		loadAllImages();
		
	}
	
	/**
	 * Loads all image from file
	 * @throws IOException : if the image is missing
	 */
	private void loadAllImages() throws IOException {
		
		worldTileSet = ImageIO.read(new File("TileSet.png"));
		playerTile = ImageIO.read(new File("charSheet_48.png"));
        playerImage = playerTile.getSubimage(0, 0, PLAYER_WIDTH, PLAYER_HEIGHT);

		enemyTile = ImageIO.read(new File("Ghost.png"));
        enemyImage = enemyTile.getSubimage(0,0,TILE_WIDTH, TILE_WIDTH);
		
	}
	
	/**
	 * Returns the desired map
	 *
	 * If the map is new, the tiles are transformed into images, because they will not move.
     * The more complex images (such as Enemy, Doors and MovableObjects) will not be instanciated.
     * They will be handled by the ObjectHandler.
	 *
	 * @param mapID the name of the desired map
	 * @return the desired Map with all images instanciated
	 */
	public Map getMap(String mapID) {
		if (currentMap == null || !currentMap.getID().equals(mapID)) {
			
			currentMap = worldMapCreator.get(mapID);
			
			// The Images are still not instanciated
			currentMap.setBackgroundObjects(transformTilesToImages(currentMap.getBackground()));
            currentMap.setSolidObjects(transformTilesToImages(currentMap.getSolid()));
            currentMap.setFloatingObjects(transformTilesToImages(currentMap.getFloating()));
            currentMap.setShadows(transformTilesToShadows(currentMap.getSolid()));
		}
		
		return currentMap;
	}
	
	/**
	 * Takes an array of int and transforms it into the correct array of images
	 * 
	 * @param tiles: a square array of int representing the id of each image
	 * @return all the physical objects to show in order of appearance
	 */
	private LinkedList<PhysicalObject> transformTilesToImages(int[][] tiles) {
		
		BufferedImage image;
		LinkedList<PhysicalObject> objects = new LinkedList<>();
		
		for (int y=0; y<tiles.length; y++) {
			for (int x=0; x<tiles[y].length; x++) {

				if (tiles[y][x] != -1) {
					int xpos = tiles[y][x] % NUMBER_OF_TILES_IN_TILESET * TILE_WIDTH;
					int ypos = (tiles[y][x] / NUMBER_OF_TILES_IN_TILESET) * TILE_WIDTH;

					image = worldTileSet.getSubimage(xpos, ypos, TILE_WIDTH, TILE_WIDTH);

					objects.add(new Image(new Position(x * TILE_WIDTH, y * TILE_WIDTH), TILE_WIDTH, TILE_WIDTH, image));
				}

			}
			
		}
		
		return objects;
	}

    /**
     * Creates shadows according to ID and position on the Map
     *
     * @param tiles the ID of each solid object that needs shadows
     * @return all the PhysicalObjects
     */
	private List<PhysicalObject> transformTilesToShadows(int[][] tiles) {

        List<PhysicalObject> objects = new LinkedList<>();

        for (int y=0; y<tiles.length; y++) {
            for (int x=0; x<tiles[y].length; x++) {

                if (tiles[y][x] != -1) {
                    objects.add(shadowFactory.getShadow(tiles[y][x], new Position(x*TILE_WIDTH, y*TILE_WIDTH)));
                }

            }

        }

        return objects;

    }

	/**
	 * @return the current mapObject
	 */
	public Map getMap() {
		return currentMap;
	}

	/**
	 * @return the player's image representation
	 */
	public PhysicalObject getPlayerObject(Player player) {
		return new Image(player.getPosition(), WorldImageLoader.PLAYER_WIDTH,
                WorldImageLoader.PLAYER_HEIGHT, playerImage);
	}

    /**
     * @return the enemy image
     */
	public PhysicalObject getEnemyObject(Enemy enemy) {
	    return new Image(enemy.getPosition(), enemy.getWidth(), enemy.getHeight(), enemyImage);
	}

}
