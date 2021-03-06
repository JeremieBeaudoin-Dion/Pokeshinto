package world;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;

import images.*;
import images.Image;
import pokeshinto.Player;
import world.worldObjects.*;

/**
 * Loads all necessary images for the ObjectHandlerInWorld
 * 
 * @author Jeremie Beaudoin-Dion
 *
 */
public class ImageLoaderInWorld extends LoaderOfImages {
	
	public final static int TILE_WIDTH = 48;
	final static int TILE_SPACING = 3;
	final static int NUMBER_OF_TILES_IN_TILESET = 60;

    public final static int PLAYER_WIDTH = 32;
	public final static int PLAYER_HEIGHT = 48;

	public final static int VILLAGER_WIDTH = 32;
	public final static int VILLAGER_HEIGHT = 48;

	public final static int VILLAGER_SHOP_ID = 2;

    public final static int ENEMY_WIDTH = 48;
	public final static int ENEMY_HEIGHT = 48;

    static int HEALTH_BAR_BACKGROUND_INSET = 30;
    static int HEALTH_BAR_WIDTH = 196;
    static int HEALTH_BAR_HEIGHT = 32;

    static Position playerMoneyPosition = new Position(775 ,25);
	
	private BufferedImage worldTileSet;

	private BufferedImage playerTile;

    private BufferedImage characterTile;

	private BufferedImage backgroundHealthBar;
    private BufferedImage foregroundHealthBar;

	private BufferedImage enemyImage;
	private BufferedImage enemyTile;
	
	private WorldMapCreator worldMapCreator;
	private Map currentMap;
	private MapImages mapImages;
	private ShadowFactory shadowFactory;

    private static final Font FONT_MONEY = new Font("Century Schoolbook", Font.BOLD, 25);
	
	/**
	 * Basic constructor
	 * @throws IOException : if the image is missing
	 */
	ImageLoaderInWorld(WorldMapCreator worldMapCreator) throws IOException {
		
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
		playerTile = toCompatibleImage(ImageIO.read(new File("Resources/Players.png")));

		enemyTile = toCompatibleImage(ImageIO.read(new File("Resources/Enemies.png")));

        characterTile = toCompatibleImage(ImageIO.read(new File("Resources/Characters.png")));

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
		BufferedImage playerImage = playerTile.getSubimage(Player.getCurrentTileID() * PLAYER_WIDTH, Player.playerCharNumber * PLAYER_HEIGHT, PLAYER_WIDTH, PLAYER_HEIGHT);

		return new Image(Player.pixelPosition, ImageLoaderInWorld.PLAYER_WIDTH,
                ImageLoaderInWorld.PLAYER_HEIGHT, playerImage);
	}

    /**
     * @return a list of all the enemy images of the current map
     */
	List<PhysicalObject> getAllMovingObjectsImages() {

        List<PhysicalObject> listOfMovingObjectsImages = new LinkedList<>();
        Iterator<MovingObject> iter = currentMap.getAllMovingObjects().iterator();
        MovingObject nextObject;

        while (iter.hasNext()) {
            nextObject = iter.next();

            if (nextObject instanceof Enemy) {
                listOfMovingObjectsImages.add(getEnemy((Enemy) nextObject));

            } else if (nextObject instanceof Villager) {
				listOfMovingObjectsImages.add(getCharacter((Villager) nextObject));

			}

        }

        return listOfMovingObjectsImages;
    }

	private PhysicalObject getEnemy(Enemy enemy) {
	    return new Image(enemy.getPosition(), enemy.getWidth(), enemy.getHeight(), getEnemyImage(enemy));
	}

    private BufferedImage getEnemyImage(Enemy enemy) {
        int enemyYAxis = 0;

        if (enemy instanceof EnemyFollowing) {
            enemyYAxis = 1;
        }

        return enemyTile.getSubimage(enemy.getCurrentTileID() * ENEMY_WIDTH,enemyYAxis * ENEMY_HEIGHT, TILE_WIDTH, TILE_WIDTH);
    }

	private PhysicalObject getCharacter(Villager villager) {
        return new Image(villager.getPosition(), villager.getWidth(), villager.getHeight(), getCharacterImage(villager));
    }

    private BufferedImage getCharacterImage(Villager villager) {

        return characterTile.getSubimage(ImageLoaderInWorld.VILLAGER_WIDTH,
                villager.getCharacterId() * ImageLoaderInWorld.VILLAGER_HEIGHT,
                ImageLoaderInWorld.VILLAGER_WIDTH, ImageLoaderInWorld.VILLAGER_HEIGHT);
    }

    /**
     * @return all the enemy's shadows of the current map
     */
	List<PhysicalObject> getAllMovingObjectsShadows() {

        List<PhysicalObject> listOfMovingObjects = new LinkedList<>();
        Iterator<MovingObject> iter = currentMap.getAllMovingObjects().iterator();

        while (iter.hasNext()) {
            listOfMovingObjects.add(shadowFactory.getShadowByMovingObject(iter.next()));
        }

        return listOfMovingObjects;
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

	List<PhysicalObject> getPlayerMoney(Camera camera) {
		List<PhysicalObject> physicalObjects = new LinkedList<>();

        Position playerMoneyPositionWithCamera = new Position(playerMoneyPosition.getX() + camera.getPosition().getX(),
                playerMoneyPosition.getY() + camera.getPosition().getY());

        String textToShow = "Money : " + Integer.toString(Player.money);

        physicalObjects.add(new Text(playerMoneyPositionWithCamera, textToShow, FONT_MONEY, "Center", Color.BLACK));

		return physicalObjects;
	}

	private BufferedImage getPlayerHealthBarBackground(double percentage) {
        return backgroundHealthBar.getSubimage(0, 0, (int) (HEALTH_BAR_WIDTH * percentage), HEALTH_BAR_HEIGHT);
    }

}
