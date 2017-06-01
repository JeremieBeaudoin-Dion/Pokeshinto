package pokeshinto;

import combat.CombatAttributes;
import combat.PlayerCopy;
import images.Position;

/**
 * The class that represent the player Object. Handles 
 * HP, equipment, etc.
 * 
 * @author Jérémie Beaudoin-Dion
 *
 */
public class Player{
	
	// The position of the player in the game
	private Position pixelPosition;
	private String facing;
	private String currentMapID;
	public static final int PIXEL_SPEED = 3;
	public static final int WIDTH = 48;
	
	// The stats of the player
	private double maxHealth;
	private double health;
	
	// Everything that is equiped by the player
	private int[] equipment;
	private Pokeshinto equipedPokeshinto;
	
	// All the items of the player TODO: change the type
	private int[] items;
	private Pokeshinto[] allShintos;
	
	// Useful combat attributes for player
	private CombatAttributes combatAttributes;

	/**
	 * Constructor
	 *
	 * @param health: the maxHealth of the player
	 * @param allShintos: all current pokeshinto
	 * @param combatAttributes: the attributes of the player in combat
	 */
	public Player(double health, Pokeshinto[] allShintos, CombatAttributes combatAttributes) {
		this.health = health;
		maxHealth = health;
		this.combatAttributes = combatAttributes;
		this.equipedPokeshinto = allShintos[0];
		this.allShintos = allShintos;
		this.currentMapID = "Test";
		
		pixelPosition = new Position(0, 0);
	}

    /**
     * If the player object moves, sets the new pixelPosition according to the new position of the shadow
     */
	public void setNewPositionAccordingToShadow(Position position){
	    pixelPosition = new Position(position.getX() - WIDTH/6, position.getY() - WIDTH*5/6);
    }

    public void setNewMapID(String mapID) {
		currentMapID = mapID;
	}

	public void setPixelPosition(Position position) {
	    pixelPosition = new Position(position.getX(), position.getY());
    }

    /**
     * @return a copy of the player object with only the pertinent information for combat
     */
	public PlayerCopy getCombatPlayer() {
		return new PlayerCopy(allShintos, equipedPokeshinto, combatAttributes, maxHealth, health);
	}

	public double getMaxHealth() {
		return maxHealth;
	}
	
	public double getHealth() {
		return health;
	}
	
	public void setHealth(double remainingHp) {
		health = remainingHp;
	}

	public Pokeshinto getCurrentShinto() {
		return equipedPokeshinto;
	}

	public Pokeshinto[] getAllShintos() {
		return allShintos;
	}

	public CombatAttributes getCombatAttributes() {
		return combatAttributes;
	}
	
	public Position getPosition() {
		return pixelPosition;
	}

	public Position getCollisionPosition() {
	    return new Position(pixelPosition.getX() + WIDTH/6, pixelPosition.getY() + WIDTH*5/6);
    }

    public Position getCollisionDimensions() {
	    return new Position(WIDTH*2/3, WIDTH/3);
    }
	
	public String getFacingPosition() {
		return facing;
	}
	
	public String getCurrentMapID() {
		return currentMapID;
	}

}
