package minigames;

import images.Position;
import pokeshinto.Game;

/**
 * A fish has a cost and a pattern with which it moves
 *
 * @author Jérémie Beaudoin-Dion
 */
class Fish {

    static int SMALL_TYPE = 0;
    static int NORMAL_TYPE = 1;
    static int RARE_TYPE = 2;
    // static int RAREREST_TYPE = 3;

    private int mySize;

    private int type;

    private int value;
    private int speed;

    private int caughtTimer;

    private boolean isReversed;
    private boolean isCaught;

    private Position position;

    /**
     * Constructor
     *
     * @param type: can be COMMON, NORMAL, or RARE
     */
    Fish(int type, Position position, boolean isReversed) {
        this.type = type;
        this.position = position;
        this.isReversed = isReversed;
        isCaught = false;

        if (type == SMALL_TYPE) {
            mySize = FishImageLoader.FISH_SMALL_SIZE;
            value = 5;
            speed = 2;
            caughtTimer = 2000;

        } else if (type == NORMAL_TYPE) {
            mySize = FishImageLoader.FISH_NORMAL_SIZE;
            value = 10;
            speed = 2;
            caughtTimer = 150;

        } else if (type == RARE_TYPE) {
            mySize = FishImageLoader.FISH_RARE_SIZE;
            value = 25;
            speed = 5;
            caughtTimer = 200;

            // For rarest type
            /*else if (type == RAREREST_TYPE) {
            value = 150;
            speed = 6;*/

        } else {
            throw new IllegalArgumentException("The type " + type + " isn't handled in Fish.");
        }

        if (isReversed) {
            speed = -speed;
        }

    }

    /**
     * Called every frame
     *
     * @param rodPosition the current position of the Rod
     */
    void update(Position rodPosition) {
        if (isCaught()) {
            position.setY(rodPosition.getY());
            caughtTimer--;

        } else {
            getCaught(rodPosition);
            position.addX(speed);
        }

        if (caughtTimer <= 0) {
            isCaught = false;
        }
    }

    /**
     * Checks if the fish is caught
     */
    private void getCaught(Position rodPosition) {
        if (caughtTimer <= 0 ){
            return;
        }

        if (isWithinRodBoundaries(rodPosition)) {
            isCaught = true;
            setTimerAccordingToPosition();
        }
    }

    private boolean isWithinRodBoundaries(Position rodPosition) {
        return position.getY() < rodPosition.getY() && rodPosition.getY() < position.getY() + mySize &&
                position.getX() < rodPosition.getX() && rodPosition.getX() < position.getX() + mySize;
    }

    private void setTimerAccordingToPosition() {
        caughtTimer -= (Game.WINDOW_HEIGHT - position.getY())/12;
    }

    /**
     * Getters
     */
    int getType() {
        return type;
    }

    int getValue() {
        return value;
    }

    Position getPosition() {
        return position;
    }

    boolean isReversed() {
        return isReversed;
    }

    boolean isCaught() {
        return isCaught;
    }

    int getMySize() {
        return mySize;
    }
}
