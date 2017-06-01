package pokeshinto;

import combat.CombatAttributes;
import combat.PlayerCopy;
import images.Position;
import world.WorldImageLoader;

/**
 * The class that represent the player Object. Handles 
 * HP, equipment, etc.
 * 
 * @author Jérémie Beaudoin-Dion
 *
 */
public class Player{
	
	// The position of the player in the game
	public static Position pixelPosition;
	public static String facing;
	public static String currentMapID;
	public static Position currentMapSize;

	public static final int PIXEL_SPEED = 3;
	public static final int WIDTH = 48;
	
	// The stats of the player
	public static double maxHealth;
    public static double health;
	
	// Everything that is equiped by the player
    public static int[] equipment;
    public static Pokeshinto equipedPokeshinto;
	
	// All the items of the player TODO: change the type
    public static int[] items;
    public static Pokeshinto[] allShintos;
	
	// Useful combat attributes for player
    public static CombatAttributes combatAttributes;

	public Player() {
		health = 500;
		maxHealth = 500;
		combatAttributes = InfoHandler.getBasicCombatAttributes();
		equipedPokeshinto = null;
		allShintos = new Pokeshinto[]{};

        pixelPosition = new Position(256, 192);
        currentMapID = "FirstTemple";
	}

    /**
     * If the player object moves, sets the new pixelPosition according to the new position of the shadow
     */
	public static void setNewPositionAccordingToShadow(Position position){
	    pixelPosition = new Position(position.getX() - WIDTH/6, position.getY() - WIDTH*5/6);
    }

    public static void setNewMapID(String mapID) {
		currentMapID = mapID;
	}

	public static void setPosition(Position position) {
	    pixelPosition = new Position(position.getX(), position.getY());
    }

    public static void setNewMapDimensions(Position position) {
        currentMapSize = new Position(position.getX() * WorldImageLoader.TILE_WIDTH, position.getY() * WorldImageLoader.TILE_WIDTH);
    }

    public static Position getCollisionPosition() {
        return new Position(pixelPosition.getX() + WIDTH/6, pixelPosition.getY() + WIDTH*5/6);
    }

    public static Position getCollisionDimensions() {
        return new Position(WIDTH*2/3, WIDTH/3);
    }

    /**
     * @return a copy of the player object with only the pertinent information for combat
     */
	public static PlayerCopy getCombatPlayer() {
		return new PlayerCopy(allShintos, equipedPokeshinto, combatAttributes, maxHealth, health);
	}

}
