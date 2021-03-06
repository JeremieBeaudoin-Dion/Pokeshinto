package minigames;

import images.Position;

/**
 * A type of fish which you can't catch
 *
 * @author Jérémie Beaudoin-Dion
 */
public class Shark extends Fish {

    private final int xSize = 147;
    private final int ySize = 27;

    private final int yCatchSize = 31;

    private final int yInset = 31;

    private int speed = -1;

    /**
     * Constructor
     */
    Shark(Position position) {
        super(position, false);

        value = -5;
        size = new Position(xSize, ySize);
        caughtTimer = 30000;
    }

    @Override
    void update(Position rodPosition) {
        position.addX(speed);
        getCaught(rodPosition);
    }

    protected boolean isWithinRodBoundaries(Position rodPosition) {
        return isWithinXBounds(rodPosition) && isWithinYBounds(rodPosition);
    }

    private boolean isWithinXBounds(Position rodPosition) {
        return position.getX() < rodPosition.getX() && rodPosition.getX() < position.getX() + size.getX();
    }

    private boolean isWithinYBounds(Position rodPosition) {
        return position.getY() + yInset < rodPosition.getY() && rodPosition.getY() < position.getY() + yInset + yCatchSize;
    }


}
