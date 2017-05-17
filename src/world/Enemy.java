package world;

import java.util.Random;

import combat.CombatAI;
import images.PhysicalObject;
import images.Point;

/**
 * A class for an enemy roaming the world
 * 
 * @author Jérémie Beaudoin-Dion
 *
 */
public class Enemy extends PhysicalObject {

	private CombatAI combatAI;
	private Random generator;
	private boolean[][] currentMapCollisions;
	
	/**
	 * Constructor for an enemy opponent
	 * 
	 * @param position
	 * @param width
	 * @param height
	 * @param combatAI
	 */
	public Enemy(Point position, int width, int height, CombatAI combatAI, boolean[][] currentMapCollisions) {
		super(position, width, height, false);
		
		this.combatAI = combatAI;
		this.currentMapCollisions = currentMapCollisions;
	}
	
	/**
	 * Returns the AI representation of the Enemy
	 * 
	 * @return
	 */
	public CombatAI getAI() {
		return combatAI;
	}
	
	/**
	 * Sets an array of boolean to represent the current map the enemy is in
	 * 
	 * @param currentMapCollisions
	 */
	public void setCollision(boolean[][] currentMapCollisions) {
		this.currentMapCollisions = currentMapCollisions;
	}
	
	/**
	 * Called every frame
	 */
	public void update() {
		int rdn = generator.nextInt(15);
		
		if (rdn == 0) {
			moveRight();
		} else if (rdn == 1) {
			moveLeft();
		} else if (rdn == 2) {
			moveUp();
		} else if (rdn == 3) {
			moveDown();
		}
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
		
		Point desiredPosition = new Point(position.getX(), position.getY());
		
		if (ID.equals("x")){
			desiredPosition.addX(value);
		} else {
			desiredPosition.addY(value);
		}
		
		if (checkPosition(desiredPosition)) {
			position = desiredPosition;
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
