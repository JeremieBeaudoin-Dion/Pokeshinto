package world;

import images.PhysicalObject;
import images.Position;

/**
 * @author Jérémie Beaudoin-Dion
 */
public class MovingObject extends PhysicalObject {

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

    public void setNewPositionAccordingToShadow(Position newPosition) {
        position = new Position(newPosition.getX() - shadowInset.getX(), newPosition.getY() - shadowInset.getY());
    }

    public PhysicalObject getShadow() {
        return new PhysicalObject(new Position(position.getX() + shadowInset.getX(), position.getY() + shadowInset.getY()),
                shadowDimension.getX(), shadowDimension.getY());
    }

    public int getSpeed() {
        return speed;
    }

    public String getFacing() {
        return facing;
    }
}
