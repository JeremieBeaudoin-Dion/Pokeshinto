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
	private int MAX_WAIT_TIME;

	private int currentTime;
	private String currentDecision;

	final static int SPEED = 2;
	
	/**
	 * Constructor for an enemy opponent
	 *
	 * @param combatAI the combat attributes of the object
	 */
	Enemy(Position position, int width, int height, CombatAI combatAI) {
		super(position, width, height, false);

        generator = new Random();
        MAX_WAIT_TIME = 60 + generator.nextInt(20);

		this.combatAI = combatAI;
        currentTime = MAX_WAIT_TIME;
        currentDecision = getDesiredMove();

	}
	
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
        currentTime--;
        decideWhatToDo();
        handleDecision();
        stayWithinBounds();

	}

	private void decideWhatToDo() {
	    if (currentTime == 0) {
            currentDecision = getDesiredMove();
	        currentTime = MAX_WAIT_TIME;
        }
    }

    private String getDesiredMove() {
        int rdn = generator.nextInt(4);

        if (rdn == 0) {
            return "Right";
        } else if (rdn == 1) {
            return "Left";
        } else if (rdn == 2) {
            return "Up";
        } else {
            return "Down";
        }
    }

    private void handleDecision() {
        if (currentDecision != null) {
            switch (currentDecision) {
                case "Right":
                    position.addX(SPEED);
                    break;
                case "Left":
                    position.addX(-SPEED);
                    break;
                case "Up":
                    position.addY(-SPEED);
                    break;
                case "DOWN":
                    position.addY(SPEED);
            }
        }
    }

    private void stayWithinBounds() {
	    if (position.getX() <= 0) {
	        position.setX(0);
        }
        if (position.getY() <= 0) {
            position.setY(0);
        }
    }
	
}
