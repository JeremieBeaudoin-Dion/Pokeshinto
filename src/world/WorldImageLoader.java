package world;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;

import images.Camera;
import images.Image;
import images.PhysicalObject;
import images.Position;
import images.LoaderOfImages;
import pokeshinto.Player;

/**
 * Loads all necessary images for the ObjectHandlerInWorld
 * 
 * @author Jeremie Beaudoin-Dion
 *
 */
public class WorldImageLoader extends LoaderOfImages {
	
	public final static int TILE_WIDTH = 48;
	final static int TILE_SPACING = 3;
	final static int NUMBER_OF_TILES_IN_TILESET = 60;

	final static int PLAYER_WIDTH = 48;
    final static int PLAYER_HEIGHT = 48;

    static int HEALTH_BAR_BACKGROUND_INSET = 30;
    static int HEALTH_BAR_WIDTH = 196;
    static int HEALTH_BAR_HEIGHT = 32;
	
	private BufferedImage worldTileSet;
	
	private BufferedImage playerImage;
	private BufferedImage playerTile;

	private BufferedImage backgroundHealthBar;
    private BufferedImage foregroundHealthBar;

	private BufferedImage enemyImage;
	private BufferedImage enemyTile;
	
	private WorldMapCreator worldMapCreator;
	private Map currentMap;
	private MapImages mapImages;
	private ShadowFactory shadowFactory;
	
	/**
	 * Basic constructor
	 * @throws IOException : if the image is missing
	 */
	WorldImageLoader(WorldMapCreator worldMapCreator) throws IOException {
		
		this.worldMapCreator = worldMapCreator;
		mapImages = new MapImages();
		shadowFactory = new ShadowFactory();
		
		loadAllImages();
		
	}
	
	/**
	 * Loads all image from file
	 * @throws IOException : if the image is missing
	 */
	private void loadAllImages() throws IOException {
		
		worldTileSet = toCompatibleImage(ImageIO.read(new File("Resources/TileSet.png")));
		playerTile = toCompatibleImage(ImageIO.read(new File("Resources/charSheet_48.png")));
        playerImage = playerTile.getSubimage(0, 0, PLAYER_WIDTH, PLAYER_HEIGHT);

		enemyTile = toCompatibleImage(ImageIO.read(new File("Resources/Ghost.png")));
        enemyImage = enemyTile.getSubimage(0,0,TILE_WIDTH, TILE_WIDTH);

        backgroundHealthBar = toCompatibleImage(ImageIO.read(new File("Resources/HealthBarBackground_Player.png")));
        foregroundHealthBar = toCompatibleImage(ImageIO.read(new File("Resources/HealthBarForeground.png")));
		
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
	Map getMap(String mapID) {
		if (currentMap == null || !currentMap.getID().equals(mapID)) {
			
			currentMap = worldMapCreator.get(mapID);
			
			// The Images are still not instanciated
			mapImages.setBackgroundObjects(transformTilesToImages(currentMap.getBackground()));
            mapImages.setForegroundObjects(transformTilesToImages(currentMap.getForeground()));
			mapImages.setSolidObjects(transformTilesToImages(currentMap.getSolid()));
			mapImages.setFloatingObjects(transformTilesToImages(currentMap.getFloating()));
			mapImages.setShadows(transformTilesToShadows(currentMap.getSolid()));

            Player.setNewMapDimensions(new Position(currentMap.getxSize(), currentMap.getySize()));
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
				    int tileXNumber = tiles[y][x] % NUMBER_OF_TILES_IN_TILESET;
                    int xpos = getTilePositionOnTileSet(tileXNumber);

					int tileYNumber = tiles[y][x] / NUMBER_OF_TILES_IN_TILESET;
					int ypos = getTilePositionOnTileSet(tileYNumber);

					image = worldTileSet.getSubimage(xpos, ypos, TILE_WIDTH, TILE_WIDTH);

					objects.add(new Image(new Position(x * TILE_WIDTH, y * TILE_WIDTH), TILE_WIDTH, TILE_WIDTH, image));
				}

			}
			
		}
		
		return objects;
	}

	private int getTilePositionOnTileSet(int tileNumber) {
	    return tileNumber * TILE_WIDTH + tileNumber * TILE_SPACING;
    }

    /**
     * Creates shadows according to ID and position on the Map
     *
     * @param tiles the ID of each solid object that needs shadows
     * @return all the PhysicalObjects
     */
	private List<PhysicalObject> transformTilesToShadows(int[][] tiles) {

        List<PhysicalObject> objects = new LinkedList<>();
        PhysicalObject shadow;

        for (int y=0; y<tiles.length; y++) {
            for (int x=0; x<tiles[y].length; x++) {

                if (tiles[y][x] != -1) {
                    shadow = shadowFactory.getShadowByID(tiles[y][x], new Position(x*TILE_WIDTH, y*TILE_WIDTH));

                    if (shadow != null) {
                        objects.add(shadow);
                    }
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

	public MapImages getMapImages() {
	    return mapImages;
    }

	/**
	 * @return the player's image representation
	 */
	PhysicalObject getPlayerObject() {
		return new Image(Player.pixelPosition, WorldImageLoader.PLAYER_WIDTH,
                WorldImageLoader.PLAYER_HEIGHT, playerImage);
	}

    /**
     * @return a list of all the enemy images of the current map
     */
	List<PhysicalObject> getAllEnemyImages() {

        List<PhysicalObject> listOfEnemyImages = new LinkedList<>();
        List<Enemy> enemies = currentMap.getAllEnemies();

        Iterator<Enemy> iter = enemies.iterator();
        Enemy currentEnemy;

        while (iter.hasNext()) {
            currentEnemy = iter.next();
            listOfEnemyImages.add(getEnemyImage(currentEnemy));
        }

        return listOfEnemyImages;
    }

	private PhysicalObject getEnemyImage(Enemy enemy) {
	    return new Image(enemy.getPosition(), enemy.getWidth(), enemy.getHeight(), enemyImage);
	}

    /**
     * @return all the enemy's shadows of the current map
     */
	List<PhysicalObject> getAllEnemyShadows() {

        List<PhysicalObject> listOfEnemyImages = new LinkedList<>();
        List<Enemy> enemies = currentMap.getAllEnemies();

        Iterator<Enemy> iter = enemies.iterator();
        Enemy currentEnemy;

        while (iter.hasNext()) {
            currentEnemy = iter.next();
            listOfEnemyImages.add(getEnemyShadow(currentEnemy));
        }

        return listOfEnemyImages;

    }

	private PhysicalObject getEnemyShadow(Enemy enemy) {
	    return shadowFactory.getShadowByMovingObject(enemy);
    }

	List<PhysicalObject> getPlayerHealthBar(Camera camera) {
		List<PhysicalObject> physicalObjects = new LinkedList<>();

		if (Player.getMissingHpPercentage() <= 0) {
		    return physicalObjects;
        }

		Position healthBarBackgroundPosition = new Position(camera.getPosition().getX(), camera.getPosition().getY());
		healthBarBackgroundPosition.addX(HEALTH_BAR_BACKGROUND_INSET);

		physicalObjects.add(new Image(healthBarBackgroundPosition, HEALTH_BAR_WIDTH, HEALTH_BAR_HEIGHT,
                getPlayerHealthBarBackground(Player.getMissingHpPercentage())));

        physicalObjects.add(new Image(camera.getPosition(), HEALTH_BAR_WIDTH, HEALTH_BAR_HEIGHT,
                foregroundHealthBar));

		return physicalObjects;
	}

	private BufferedImage getPlayerHealthBarBackground(double percentage) {
        return backgroundHealthBar.getSubimage(0, 0, (int) (HEALTH_BAR_WIDTH * percentage), HEALTH_BAR_HEIGHT);
    }

}
