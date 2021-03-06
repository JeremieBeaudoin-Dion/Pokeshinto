package images;

import java.awt.*;

/**
 * A physical object that can be drawn in the shape of an ellipse with:
 *
 * // bbg.setColor()
 * // bbg.fillRoundRect();
 *
 * @author Jérémie Beaudoin-Dion
 */
public class Ellipse extends PhysicalObject {

    private Color color;

    public Ellipse(Color color, Position position, int width, int height) {
        super(position, width, height);
        this.color = color;
    }

    public Ellipse(Color color, Position position, int width, int height, boolean passable) {
        super(position, width, height, passable);
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
