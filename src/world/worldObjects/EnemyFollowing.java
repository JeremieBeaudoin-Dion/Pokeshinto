package world.worldObjects;

import combat.CombatAI;
import images.PhysicalObject;
import images.Position;
import pokeshinto.Player;

/**
 * An enemy that follows the player around
 *
 * @author Jérémie Beaudoin-Dion
 */
public class EnemyFollowing extends Enemy {

    private int currentTime;

    private Position initialPosition;

    private final static int MAX_WAIT_TIME = 24;
    private final static int SPEED = 2;
    private final static int VISION_RANGE = 192;

    public EnemyFollowing(PhysicalObject enemy, PhysicalObject shadow, CombatAI combatAI) {
        super(enemy, shadow, SPEED, combatAI);

        currentTime = MAX_WAIT_TIME;

        initialPosition = enemy.getPosition().copy();
    }

    @Override
    protected boolean decisionHasChanged() {
        if (currentTime == 0) {
            currentTime = MAX_WAIT_TIME;
            return true;
        }

        currentTime--;
        return false;
    }

    @Override
    protected String getDecision() {
        if (playerIsInRange()) {
            return findMostEfficientRouteTo(Player.pixelPosition);

        } else {
            if (onInitialPosition()) {
                return null;
            } else {
                return findMostEfficientRouteTo(initialPosition);
            }
        }
    }

    private boolean playerIsInRange() {
        return this.position.getX() > Player.pixelPosition.getX() - VISION_RANGE &&
                this.position.getX() < Player.pixelPosition.getX() + VISION_RANGE &&
                this.position.getY() > Player.pixelPosition.getY() - VISION_RANGE &&
                this.position.getY() < Player.pixelPosition.getY() + VISION_RANGE;
    }

    private boolean onInitialPosition() {
        return this.position.getX() == initialPosition.getX() && this.position.getY() == initialPosition.getY();
    }

    private String findMostEfficientRouteTo(Position desiredPosition) {
        int xAxisDifference = desiredPosition.getX() - this.position.getX();
        int yAxisDifference = desiredPosition.getY() - this.position.getY();

        if (Math.abs(xAxisDifference) > Math.abs(yAxisDifference)) {
            if (xAxisDifference > 0) {
                return "Right";
            } else {
                return "Left";
            }
        } else {
            if (yAxisDifference > 0) {
                return "Down";
            } else {
                return "Up";
            }
        }

    }

}
