package pokeshinto;

import combat.CombatAttributes;
import combat.Dictionary;
import combat.InfoHandler;
import combat.PlayerCopy;
import images.Position;
import world.WorldImageLoader;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * The class that represent the player Object. Handles 
 * HP, equipment, etc.
 * 
 * @author Jérémie Beaudoin-Dion
 *
 */
public class Player{
    
    public static int MAX_NUMBER_OF_POKESHINTO = 9;
    public static int MAX_NUMBER_OF_EQUIPED_POKESHINTO = 3;
	
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
	
	// Everything that is equiped by the player TODO: change the class
    public static int[] equipment;

	
	// All the items of the player TODO: change the class
    public static int[] items;
    public static List<Pokeshinto> allShintos;
    public static List<Pokeshinto> equipedPokeshintos;
	
	// Useful combat attributes for player
    public static CombatAttributes combatAttributes;

	/**
	 * Constructor
	 */
	public Player() {
		health = 500;
		maxHealth = 500;
		combatAttributes = InfoHandler.getBasicCombatAttributes();
		allShintos = new LinkedList<>();
        equipedPokeshintos = new LinkedList<>();

        pixelPosition = new Position(256, 192);
        currentMapID = "FirstTemple";
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

    public static void levelUpAllShintos(Dictionary timeSpend, int levelOfEnemy, int lengthOfCombat) {

	    Iterator<Pokeshinto> iter = allShintos.iterator();
	    Pokeshinto current;

	    while(iter.hasNext()) {
	        current = iter.next();

	        if (timeSpend.contains(current.getId())) {
	            Integer firstValue = timeSpend.getValueByKey(current.getId());
	            Integer length = lengthOfCombat;

	            double percentage = firstValue.doubleValue() / length.doubleValue();
                current.levelUp(percentage, levelOfEnemy);
            }
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
	    Pokeshinto onlyPokeshinto = null;

	    if (equipedPokeshintos.size() == 1) {
            onlyPokeshinto = equipedPokeshintos.get(0);
        }

		return new PlayerCopy(equipedPokeshintos, onlyPokeshinto, combatAttributes.copy(), maxHealth, health);
	}

	public static List<Pokeshinto> getAllShintos() {
	    return allShintos;
    }

}
