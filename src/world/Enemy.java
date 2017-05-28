package world;

import java.util.Random;

import combat.CombatAI;
import images.PhysicalObject;
import images.Position;

/**
 * A class for an enemy roaming the world
 * 
 * @author Jérémie Beaudoin-Dion
 *
 */
public class Enemy extends PhysicalObject {

    //TODO: add Moving object
	private CombatAI combatAI;
	private Random generator;

	public final static int SPEED = 4;
	
	/**
	 * Constructor for an enemy opponent
	 *
	 * @param combatAI the combat attributes of the object
	 */
	public Enemy(Position position, int width, int height, CombatAI combatAI) {
		super(position, width, height, false);
		
		this.combatAI = combatAI;
		generator = new Random();
	}
	
	/**
	 * @return the AI representation of the Enemy
	 */
	public CombatAI getAI() {
		return combatAI;
	}

	/**
	 * Returns the direction the enemy wants to move to
	 *
	 * @return "Right", "Left", "Up" or "Down"
	 */
	public String getDesiredMove() {
		int rdn = generator.nextInt(15);

		if (rdn == 0) {
			return "Right";
		} else if (rdn == 1) {
			return "Left";
		} else if (rdn == 2) {
			return "Up";
		} else if (rdn == 3) {
			return "Down";
		}

		return null;
	}

	/**
	 * Called every frame
	 */
	public void update() {

	}
	
	
}
