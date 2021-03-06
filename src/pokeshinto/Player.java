package pokeshinto;

import combat.CombatAttributes;
import combat.combatAI.PlayerCopy;
import combat.ActionDescriptor;
import images.Position;
import world.ImageLoaderInWorld;
import world.items.Item;
import world.items.ItemBag;
import world.items.ItemUsableInCombat;
import world.worldObjects.MovingObject;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * The class that represent the player Object. Handles 
 * HP, equipment, etc.
 *
 * While some might argue that a Static Class is not desired in
 * new object-oriented programming, this Player class is used throughout
 * the code and accessed by more than a dozen of other classes. Giving
 * them all a reference to the same Player object seemed unnecessary here.
 *
 * To store the information into a file, a PlayerInformation class is needed.
 * 
 * @author Jérémie Beaudoin-Dion
 *
 */
public class Player {
    
    public static int MAX_NUMBER_OF_POKESHINTO = 9;
    public static int MAX_NUMBER_OF_EQUIPED_POKESHINTO = 3;

    public static boolean approximate = true;
	public static int descriptionComplexity = ActionDescriptor.DESCRIPTION_COMPLEXITY_NORMAL;
	
	// The position of the player in the game
	public static Position pixelPosition;
	public static String currentMapID;
	public static Position currentMapSize;

	// Moving and animating player
	public static int playerCharNumber;
	public static boolean isMoving = false;
    public static String facing;
    private static int currentTileID;
    public static final int NUMBER_OF_PLAYER_IMAGES = 7;
    private static int numberOfFramesWhenMoving;

	public static final int PIXEL_SPEED = 3;
	
	// The stats of the player
	public static double maxHealth;
    public static double health;
	
	// Everything that is equiped by the player TODO: change the class
    public static int[] equipment;
	
	// All the items of the player
    public static ItemBag allItems;
    public static List<Pokeshinto> allShintos;
    public static List<Pokeshinto> equipedPokeshintos;

    public static int money;
	
	// Useful combat attributes for player
    public static CombatAttributes combatAttributes;

    public static void setPlayer(PlayerInformation playerInformation) {
        health = playerInformation.getHealth();
        maxHealth = playerInformation.getMaxHealth();
        combatAttributes = playerInformation.getCombatAttributes();

        allShintos = playerInformation.getAllShintos();
        equipedPokeshintos = playerInformation.getEquipedPokeshintos();
        allItems = playerInformation.getAllItems();

        facing = playerInformation.getFacing();
        pixelPosition = playerInformation.getPixelPosition();
        currentMapID = playerInformation.getCurrentMapID();

        playerCharNumber = playerInformation.getPlayerCharNumber();

        approximate = playerInformation.isApproximate();
        descriptionComplexity = playerInformation.getDescriptionComplexity();

        money = playerInformation.getMoney();
    }

    /**
     * At the end of combat
     */
	public static void resetAllPokeshintos() {
	    Iterator<Pokeshinto> iter = allShintos.iterator();
	    Pokeshinto currentPoke;

	    while (iter.hasNext()) {
            currentPoke = iter.next();
            currentPoke.resetAllSkills();
        }
    }

    public static void levelUpAllShintos(int xpGained) {

	    Iterator<Pokeshinto> iter = allShintos.iterator();
	    Pokeshinto current;

	    while(iter.hasNext()) {
	        current = iter.next();

			current.levelUp(xpGained);
        }

    }

	public static void addNewPokeshinto(Pokeshinto pokeshinto) {
		allShintos.add(pokeshinto);

		if (equipedPokeshintos.size() < MAX_NUMBER_OF_EQUIPED_POKESHINTO) {
            equipedPokeshintos.add(pokeshinto);
        }
	}

    /**
     * If the player object moves, sets the new pixelPosition according to the new position of the shadow
     */
	public static void setNewPositionAccordingToShadow(Position position){
	    pixelPosition = new Position(position.getX() - ImageLoaderInWorld.PLAYER_WIDTH/6,
                position.getY() - ImageLoaderInWorld.PLAYER_HEIGHT*5/6);
    }

    public static void setNewMapID(String mapID) {
		currentMapID = mapID;
	}

	public static void setPosition(Position position) {
	    pixelPosition = new Position(position.getX(), position.getY());
    }

    public static void setNewMapDimensions(Position position) {
        currentMapSize = new Position(position.getX() * ImageLoaderInWorld.TILE_WIDTH, position.getY() * ImageLoaderInWorld.TILE_WIDTH);
    }

    public static Position getCollisionPosition() {
        return new Position(pixelPosition.getX() + ImageLoaderInWorld.PLAYER_WIDTH/6,
                pixelPosition.getY() + ImageLoaderInWorld.PLAYER_HEIGHT*5/6);
    }

    public static Position getCollisionDimensions() {
        return new Position(ImageLoaderInWorld.PLAYER_WIDTH *2/3, ImageLoaderInWorld.PLAYER_HEIGHT/3);
    }

    public static int getCurrentTileID() {
	    int playerTileID = 0;

        switch (Player.facing) {
            case "Left":
                playerTileID = 3;
                break;

            case "Right":
                playerTileID = 6;
                break;

            case "Up":
                playerTileID = 9;
                break;
        }

        playerTileID += Player.currentTileID;

        return playerTileID;
    }

    /**
     * @return a copy of the player object with only the pertinent information for combat
     */
	public static PlayerCopy getCombatPlayer() {
	    Pokeshinto onlyPokeshinto = null;

	    if (equipedPokeshintos.size() == 1) {
            onlyPokeshinto = equipedPokeshintos.get(0);
        }

		return new PlayerCopy(equipedPokeshintos, onlyPokeshinto, allItems, combatAttributes.copy(), maxHealth, health);
	}

	public static List<Pokeshinto> getAllShintos() {
	    return allShintos;
    }

    public static Pokeshinto getEquipedPokeshinto(String id) {
	    Iterator<Pokeshinto> iter = equipedPokeshintos.iterator();
	    Pokeshinto currentPokeshinto;

	    while(iter.hasNext()) {
            currentPokeshinto = iter.next();

	        if (currentPokeshinto.getId().equals(id)) {
	            return currentPokeshinto;
            }

        }

        throw new IllegalArgumentException("The pokeshinto " + id + " is not equiped by Player");
    }

    public static PlayerInformation getPlayerInformation() {
	    return new PlayerInformation(approximate, descriptionComplexity, health, maxHealth, combatAttributes, allItems,
                allShintos, equipedPokeshintos, facing, pixelPosition, currentMapID, playerCharNumber, money);
    }

    public static double getMissingHpPercentage() {
        return health/maxHealth;
    }

    /**
     * Called every frame
     */
    public static void update(){
        if (facing == null) {
            return;
        }

        if (!isMoving) {
            currentTileID = 1;
            numberOfFramesWhenMoving = 0;
            return;
        }

        numberOfFramesWhenMoving++;

        if (numberOfFramesWhenMoving > MovingObject.NUMBER_OF_FRAMES_PER_STEP) {
            numberOfFramesWhenMoving = 0;
            currentTileID++;

            if (currentTileID >= MovingObject.NUMBER_OF_IMAGES_PER_STEP) {
                currentTileID = 0;
            }

        }


    }

}
