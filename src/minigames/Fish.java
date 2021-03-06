package minigames;

import images.Position;
import pokeshinto.Game;

/**
 * A fish has a cost and a pattern with which it moves
 *
 * @author Jérémie Beaudoin-Dion
 */
abstract class Fish {

    protected int value;

    protected int caughtTimer;

    protected boolean isReversed;
    protected boolean isCaught;

    protected Position position;
    protected Position size;

    Fish(Position position, boolean isReversed) {
        this.position = position;
        this.isReversed = isReversed;
        isCaught = false;
    }

    abstract void update(Position rodPosition);

    /**
     * Checks if the fish is caught
     */
    protected void getCaught(Position rodPosition) {
        if (caughtTimer <= 0){
            return;
        }

        if (isWithinRodBoundaries(rodPosition)) {
            isCaught = true;
            setTimerAccordingToPosition();
        }
    }

    protected abstract boolean isWithinRodBoundaries(Position rodPosition);

    private void setTimerAccordingToPosition() {
        caughtTimer -= (Game.WINDOW_HEIGHT - position.getY())/12;
    }

    /**
     * Setters
     */
    void setCaught(boolean value) {
        isCaught = value;
    }

    /**
     * Getters
     */
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

    Position getSize() {
        return size;
    }
}
