package world;

import images.Ellipse;
import images.PhysicalObject;
import images.Position;
import pokeshinto.Player;
import world.worldObjects.MovingObject;

import java.awt.Color;

/**
 * Creates shadow objects to help with collisions
 *
 * @author Jérémie Beaudoin-Dion
 */
class ShadowFactory {

    private final static Color SHADOW_COLOR = new Color(0, 0, 0, 169);

    private final static int shadowWidth = (int) (ImageLoaderInWorld.TILE_WIDTH * 0.8);
    private final static int shadowHeight = (int) (ImageLoaderInWorld.TILE_WIDTH * 0.2);
    private final static int shadowXOffset = (int) (ImageLoaderInWorld.TILE_WIDTH * 0.1);
    private final static int shadowYOffset = (int) (ImageLoaderInWorld.TILE_WIDTH * 0.9);

    /**
     * Creates an ellipsed shadow
     */
    Ellipse getShadowByID(int ID, Position position) {

        PhysicalObject shadow = getShadowFromPosition(position);

        return new Ellipse(SHADOW_COLOR, shadow.getPosition(), shadow.getWidth(), shadow.getHeight());
    }

    private PhysicalObject getShadowFromPosition(Position position) {
        return new PhysicalObject(new Position(shadowXOffset + position.getX(), shadowYOffset + position.getY()), shadowWidth, shadowHeight);
    }

    /**
     * Returns the shadow according to the object's shadow physical object
     */
    Ellipse getShadowByMovingObject(MovingObject object) {
        return new Ellipse(SHADOW_COLOR, object.getShadow().getPosition(), object.getShadow().getWidth(),
                object.getShadow().getHeight());
    }

    /**
     * Creates a shadow for the player Object
     */
    static Ellipse getPlayerShadow(){
        return new Ellipse(ShadowFactory.SHADOW_COLOR, Player.getCollisionPosition(), Player.getCollisionDimensions().getX(),
                Player.getCollisionDimensions().getY());
    }

}
