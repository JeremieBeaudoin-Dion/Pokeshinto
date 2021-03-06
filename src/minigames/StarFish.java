package minigames;

import images.Position;

/**
 * A starfish is a fish that moves in a sinusoïd pattern
 *
 * @author Jérémie Beaudoin-Dion
 */
public class StarFish extends Fish {

    private int speed;

    private int startingYPosition;

    StarFish(Position position, boolean isReversed) {
        super(position, isReversed);

        startingYPosition = position.getY();
        caughtTimer = 20000;
        value = 15;
        size = new Position(35, 35);

        if (isReversed) {
            speed = -2;
        } else {
            speed = 2;
        }

    }

    @Override
    void update(Position rodPosition) {
        if (isCaught()) {
            position.setY(rodPosition.getY());
            caughtTimer--;

        } else {
            getCaught(rodPosition);
            position.addX(speed);
            position.setY(getYPosition());
        }

        if (caughtTimer <= 0) {
            isCaught = false;
        }
    }

    private int getYPosition() {
        return (int) (startingYPosition + Math.sin((double) position.getX() / 50) * 100);
    }

    @Override
    protected boolean isWithinRodBoundaries(Position rodPosition) {
        return isWithinXBounds(rodPosition) && isWithinYBounds(rodPosition);
    }

    private boolean isWithinXBounds(Position rodPosition) {
        return position.getX() < rodPosition.getX() && rodPosition.getX() < position.getX() + size.getX();
    }

    private boolean isWithinYBounds(Position rodPosition) {
        return position.getY() < rodPosition.getY() && rodPosition.getY() < position.getY() + size.getY();
    }
}
