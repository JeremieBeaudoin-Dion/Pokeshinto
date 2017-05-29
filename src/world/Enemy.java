package world;

import combat.CombatAI;
import images.PhysicalObject;
import images.Position;

/**
 * A class for an enemy roaming the world
 * 
 * @author Jérémie Beaudoin-Dion
 *
 */
public abstract class Enemy extends MovingObject {

	private CombatAI combatAI;

	private String currentDecision;

    /**
     * Constructor for an enemy opponent
     *
     * @param position its starting position on screen
     * @param width the width of the physical object
     * @param height the height of the physical object
     * @param speed the speed with which the object moves
     * @param shadowInset where the shadow should be according to the enemy
     * @param shadowDimension what are the dimensions of the enemy
     * @param combatAI        the Information about this AI in combat
     */
    public Enemy(Position position, int width, int height, int speed, Position shadowInset, Position shadowDimension,
                 CombatAI combatAI) {
        super(position, width, height, speed, shadowInset, shadowDimension);

        this.combatAI = combatAI;
    }

    public Enemy(PhysicalObject enemy, PhysicalObject shadow, int speed, CombatAI combatAI) {
        super(enemy, speed, shadow);

        this.combatAI = combatAI;
    }

    protected abstract String getDecision();
    protected abstract boolean decisionHasChanged();
    protected abstract void handleDecision(String decision);
	
	/**
	 * @return the AI representation of the Enemy
	 */
	public CombatAI getAI() {
		return combatAI;
	}

	/**
	 * Called every frame
	 */
	public void update() {

	    if (decisionHasChanged()) {
	        currentDecision = getDecision();
        }

        handleDecision(currentDecision);

	}

	protected void moveRight() {
        position.addX(getSpeed());
    }

    protected void moveLeft() {
        position.addX(-getSpeed());
    }

    protected void moveUp() {
	    position.addY(-getSpeed());
    }

    protected void moveDown() {
        position.addY(getSpeed());
    }
	
}
