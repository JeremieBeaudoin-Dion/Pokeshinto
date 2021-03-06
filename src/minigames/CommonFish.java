package minigames;

import images.Position;

/**
 * @author Jérémie Beaudoin-Dion
 */
public class CommonFish extends Fish {

    static int SMALL_TYPE = 0;
    static int NORMAL_TYPE = 1;
    static int RARE_TYPE = 2;

    private int type;

    private int speed;

    /**
     * Constructor
     *
     * @param type: can be COMMON, NORMAL, or RARE
     */
    CommonFish(int type, Position position, boolean isReversed) {
        super(position, isReversed);
        this.type = type;

        if (type == SMALL_TYPE) {
            size = new Position(FishImageLoader.FISH_SMALL_SIZE, FishImageLoader.FISH_SMALL_SIZE);
            value = 5;
            speed = 2;
            caughtTimer = 500;

        } else if (type == NORMAL_TYPE) {
            size = new Position(FishImageLoader.FISH_NORMAL_SIZE, FishImageLoader.FISH_NORMAL_SIZE);
            value = 10;
            speed = 2;
            caughtTimer = 150;

        } else if (type == RARE_TYPE) {
            size = new Position(FishImageLoader.FISH_RARE_SIZE, FishImageLoader.FISH_RARE_SIZE);
            value = 30;
            speed = 5;
            caughtTimer = 200;

        } else {
            throw new IllegalArgumentException("The type " + type + " is not a valid CommonFish.");
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

    protected boolean isWithinRodBoundaries(Position rodPosition) {
        return isWithinXBounds(rodPosition) && isWithinYBounds(rodPosition);
    }

    private boolean isWithinXBounds(Position rodPosition) {
        return position.getX() < rodPosition.getX() && rodPosition.getX() < position.getX() + size.getX();
    }

    private boolean isWithinYBounds(Position rodPosition) {
        return position.getY() < rodPosition.getY() && rodPosition.getY() < position.getY() + size.getY();
    }

    /**
     * Getter
     */
    int getType() {
        return type;
    }

}
