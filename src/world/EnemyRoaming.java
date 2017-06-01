package world;

import combat.CombatAI;
import images.PhysicalObject;
import images.Position;
import images.CardinalDirection;

import java.util.Random;

/**
 * @author Jérémie Beaudoin-Dion
 */
public class EnemyRoaming extends Enemy {

    private int currentTime;
    private int MAX_WAIT_TIME;
    private Random generator;

    private CardinalDirection bounds;

    /**
     * Constructor for an enemy opponent
     *
     * @param position        its starting position on screen
     * @param width           the width of the physical object
     * @param height          the height of the physical object
     * @param speed           the speed with which the object moves
     * @param shadowInset     where the shadow should be according to the enemy
     * @param shadowDimension what are the dimensions of the enemy
     * @param combatAI        the Information about this AI in combat
     */
    public EnemyRoaming(Position position, int width, int height, int speed, Position shadowInset,
                        Position shadowDimension, CombatAI combatAI, CardinalDirection bounds) {
        super(position, width, height, speed, shadowInset, shadowDimension, combatAI);

        generator = new Random();
        MAX_WAIT_TIME = 40 + generator.nextInt(20);
        this.bounds = bounds;

    }

    /**
     * Constructor facilitated with complete objects
     */
    public EnemyRoaming(PhysicalObject enemy, PhysicalObject shadow, int speed, CombatAI combatAI, CardinalDirection bounds) {
        super(enemy, shadow, speed, combatAI);

        generator = new Random();
        MAX_WAIT_TIME = 40 + generator.nextInt(20);
        this.bounds = bounds;
    }

    /**
     * @return if the AI should change what it's doing
     */
    protected boolean decisionHasChanged() {
        if (currentTime == 0) {
            currentTime = MAX_WAIT_TIME;
            return true;
        }
        currentTime--;
        return false;
    }

    /**
     * @return what the AI should do next
     */
    protected String getDecision() {
        String newDecision = getRandomDirection();

        while (newDecision.equals(currentDecision)) {
            newDecision = getRandomDirection();
        }

        return newDecision;
    }

    private String getRandomDirection() {
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

    /**
     * @param decision what the AI should do next
     */
    protected void handleDecision(String decision) {
        if (decision == null) {
            return;
        }
        switch (decision) {
            case "Right":
                moveRight();
                if (positionIsOutOfBounds()) {
                    moveLeft();
                    currentDecision = "Left";
                }
                break;
            case "Left":
                moveLeft();
                if (positionIsOutOfBounds()) {
                    moveRight();
                    currentDecision = "Right";
                }
                break;
            case "Up":
                moveUp();
                if (positionIsOutOfBounds()) {
                    moveDown();
                    currentDecision = "Down";
                }
                break;
            case "DOWN":
                moveDown();
                if (positionIsOutOfBounds()) {
                    moveUp();
                    currentDecision = "Up";
                }

        }
    }

    /**
     * Helper method
     */
    private boolean positionIsOutOfBounds() {

        return position.getX() < bounds.getEast() ||
                position.getX() > bounds.getWest() ||
                position.getY() < bounds.getNorth() ||
                position.getY() > bounds.getSouth();

    }
}
