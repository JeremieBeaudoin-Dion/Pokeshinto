package world.worldObjects;

import images.PhysicalObject;
import images.Position;

/**
 * @author Jérémie Beaudoin-Dion
 */
public abstract class MovingObject extends PhysicalObject {

    public static final int NUMBER_OF_FRAMES_PER_STEP = 10;
    public static final int NUMBER_OF_IMAGES_PER_STEP = 3;

    private int currentTileID;
    private static int numberOfFramesWhenMoving;
    public static boolean isMoving = false;

    protected String currentMovingDecision;

    private int speed;
    private Position shadowInset;
    private Position shadowDimension;

    private String facing;

    public MovingObject(Position position, int width, int height, int speed, Position shadowInset, Position shadowDimension) {
        super(position, width, height);
        this.speed = speed;
        this.shadowInset = shadowInset;
        this.shadowDimension = shadowDimension;
        facing = "Down";
    }

    /**
     * Constructor facilitated with complete objects
     */
    public MovingObject(PhysicalObject object, int speed, PhysicalObject shadow) {
        super(object.getPosition(), object.getWidth(), object.getHeight());

        this.speed = speed;
        this.shadowInset = shadow.getPosition();
        this.shadowDimension = new Position(shadow.getWidth(), shadow.getHeight());
        facing = "Down";
    }

    protected abstract String getDecision();
    protected abstract boolean decisionHasChanged();

    /**
     * Called every frame
     */
    public void update() {

        isMoving = false;

        if (decisionHasChanged()) {
            currentMovingDecision = getDecision();
        }

        handleDecision(currentMovingDecision);

        if (!isMoving) {
            currentTileID = 1;
            numberOfFramesWhenMoving = 0;
            return;
        }

        numberOfFramesWhenMoving++;

        if (numberOfFramesWhenMoving > NUMBER_OF_FRAMES_PER_STEP) {
            numberOfFramesWhenMoving = 0;
            currentTileID++;

            if (currentTileID >= NUMBER_OF_IMAGES_PER_STEP) {
                currentTileID = 0;
            }

        }

    }

    protected void handleDecision(String decision) {
        if (decision == null) {
            return;
        }
        switch (decision) {
            case "Right":
                moveRight();
                return;

            case "Left":
                moveLeft();
                return;

            case "Up":
                moveUp();
                return;

            case "Down":
                moveDown();
                return;
        }

        throw new IllegalArgumentException("The direction " + decision + " doesn't exists.");
    }

    protected void moveRight() {
        facing = "Right";
        position.addX(getSpeed());
        isMoving = true;
    }

    protected void moveLeft() {
        facing = "Left";
        position.addX(-getSpeed());
        isMoving = true;
    }

    protected void moveUp() {
        facing = "Up";
        position.addY(-getSpeed());
        isMoving = true;
    }

    protected void moveDown() {
        facing = "Down";
        position.addY(getSpeed());
        isMoving = true;
    }

    /**
     * Getters
     */
    public int getCurrentTileID() {
        int tileID = 0;

        switch (facing) {
            case "Left":
                tileID = 3;
                break;

            case "Right":
                tileID = 6;
                break;

            case "Up":
                tileID = 9;
                break;
        }

        tileID += currentTileID;

        return tileID;
    }

    public int getSpeed() {
        return speed;
    }

    public String getFacing() {
        return facing;
    }

    public PhysicalObject getShadow() {
        return new PhysicalObject(new Position(position.getX() + shadowInset.getX(), position.getY() + shadowInset.getY()),
                shadowDimension.getX(), shadowDimension.getY());
    }

    /**
     * Setter
     */
    public void setNewPositionAccordingToShadow(Position newPosition) {
        position = new Position(newPosition.getX() - shadowInset.getX(), newPosition.getY() - shadowInset.getY());
    }

}
