package pokeshinto;

import combat.CombatAttributes;
import combat.PlayerCopy;
import images.Point;

/**
 * The class that represent the player Object. Handles 
 * HP, equipment, etc.
 * 
 * @author Jérémie Beaudoin-Dion
 *
 */
public class Player{
	
	// The position of the player in the game
	private Point pixelPosition;
	private String facing;
	private int currentMapID;
	
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
	CombatAttributes combatAttributes;
	
	/**
	 * Constructor for combat player
	 * 
	 * @param pixelPosition
	 * @param facing
	 * @param health
	 * @param equipment
	 * @param currentShinto
	 * @param items
	 * @param allShintos
	 * @param combatAttributes
	 */
	public Player(double health, Pokeshinto[] allShintos, CombatAttributes combatAttributes) {
		this.health = health;
		maxHealth = health;
		this.combatAttributes = combatAttributes;
		this.equipedPokeshinto = allShintos[0];
		this.allShintos = allShintos;
		this.currentMapID = 0;
		
		pixelPosition = new Point(0, 0);
	}
	
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
	
	public Point getPosition() {
		return pixelPosition;
	}
	
	public String getFacingPosition() {
		return facing;
	}
	
	public int getCurrentMapID() {
		return currentMapID;
	}

	public void update() {
		// TODO Auto-generated method stub
		
	}
	
	public void moveRight() {
		pixelPosition.addX(4);
	}
	public void moveLeft() {
		pixelPosition.addX(-4);
	}
	public void moveUp() {
		pixelPosition.addY(-4);
	}
	public void moveDown() {
		pixelPosition.addY(4);
	}
	
	
}
