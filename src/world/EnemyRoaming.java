package world;

import combat.CombatAI;
import images.PhysicalObject;
import images.Position;
import images.CardinalDirection;

import java.io.Serializable;
import java.util.Collections;
import java.util.Random;
import java.util.Stack;

/**
 * @author Jérémie Beaudoin-Dion
 */
public class EnemyRoaming extends Enemy {

    public static final int MINIMUM_WAIT_TIME = 40;

    private int currentTime;
    private int MAX_WAIT_TIME;
    private Random generator;

    private CardinalDirection bounds;

    private Stack<String> nextDecisions;

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

        nextDecisions = new Stack<>();

        generator = new Random();
        MAX_WAIT_TIME = MINIMUM_WAIT_TIME + generator.nextInt(MINIMUM_WAIT_TIME/2);
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
        if (nextDecisions.empty()) {
            nextDecisions.push("Up");
            nextDecisions.push("Down");
            nextDecisions.push("Left");
            nextDecisions.push("Right");

            int rdn = generator.nextInt(4);

            if (rdn == 0) {
                nextDecisions.remove("Right");
            } else if (rdn == 1) {
                nextDecisions.remove("Left");
            } else if (rdn == 2) {
                nextDecisions.remove("Up");
            } else {
                nextDecisions.remove("Down");
            }

            Collections.shuffle(nextDecisions);
        }

        return nextDecisions.pop();
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
                if (cantMoveRight()) {
                    moveLeft();
                    if (nextDecisions.empty()) {
                        currentDecision = "Left";
                    } else {
                        currentDecision = nextDecisions.pop();
                    }
                }
                return;

            case "Left":
                moveLeft();
                if (cantMoveLeft()) {
                    moveRight();
                    if (nextDecisions.empty()) {
                        currentDecision = "Right";
                    } else {
                        currentDecision = nextDecisions.pop();
                    }
                }
                return;

            case "Up":
                moveUp();
                if (cantMoveUp()) {
                    moveDown();
                    if (nextDecisions.empty()) {
                        currentDecision = "Down";
                    } else {
                        currentDecision = nextDecisions.pop();
                    }
                }
                return;

            case "Down":
                moveDown();
                if (cantMoveDown()) {
                    moveUp();
                    if (nextDecisions.empty()) {
                        currentDecision = "Up";
                    } else {
                        currentDecision = nextDecisions.pop();
                    }
                }
                return;
        }

        throw new IllegalArgumentException("The direction " + decision + " doesn't exists.");
    }

    private boolean cantMoveDown() {
        return position.getY() > bounds.getSouth();
    }

    private boolean cantMoveUp() {
        return position.getY() < bounds.getNorth();
    }

    private boolean cantMoveLeft() {
        return position.getX() < bounds.getEast();
    }

    private boolean cantMoveRight() {
        return position.getX() > bounds.getWest();
    }

}
