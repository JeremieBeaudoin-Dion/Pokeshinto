package pokeshinto;

import combat.CombatAttributes;
import combat.PlayerCopy;
import images.Point;
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
	private Point pixelPosition;
	private String facing;
	private int currentMapID;
	private boolean[][] currentMapCollisions;
	
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
	
	public void setCurrentMapCollision(boolean[][] value) {
		currentMapCollisions = value;
	}

	public void update() {
		// TODO Auto-generated method stub
		
	}
	
	public void moveRight() {
		setNewPos(4, "x");
	}
	public void moveLeft() {
		setNewPos(-4, "x");
	}
	public void moveUp() {
		setNewPos(-4, "y");
	}
	public void moveDown() {
		setNewPos(4, "y");
	}
	
	/**
	 * Sets the new position of the player according to the value
	 * 
	 * @param value: positive or negative int
	 * @param ID: "x" or "y"
	 */
	private void setNewPos(int value, String ID) {
		
		Point desiredPosition = new Point(pixelPosition.getX(), pixelPosition.getY());
		
		if (ID.equals("x")){
			desiredPosition.addX(value);
		} else {
			desiredPosition.addY(value);
		}
		
		if (checkPosition(desiredPosition)) {
			pixelPosition = desiredPosition;
		}
	}
	
	/**
	 * Returns true if the desired position is good according to the map.
	 * 
	 * @param desiredPosition
	 * @return
	 */
	private boolean checkPosition(Point desiredPosition) {
		
		Point gridPosition = new Point((int) desiredPosition.getX() / WorldImageLoader.TILE_WIDTH, 
									   (int) desiredPosition.getY() / WorldImageLoader.TILE_WIDTH);
		
		if (gridPosition.getX() < 0 || gridPosition.getY() < 0) {
			return false;
		} else if (gridPosition.getX() >= currentMapCollisions.length || 
				gridPosition.getY() >= currentMapCollisions.length) {
			return false;
		}
		
		return currentMapCollisions[gridPosition.getY()][gridPosition.getX()];
	}
	
}
