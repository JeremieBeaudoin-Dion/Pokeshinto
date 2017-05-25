package world;

import images.Ellipse;
import images.PhysicalObject;
import images.Point;
import pokeshinto.Player;

import java.awt.Color;
import java.util.HashMap;

/**
 * Creates shadow objects to help with collisions
 *
 * @author Jérémie Beaudoin-Dion
 */
class ShadowFactory {

    private final static Color SHADOW_COLOR = new Color(0, 0, 0, 169);

    /*
     Tree objects
     */
    private static Point treeOffset = new Point(WorldImageLoader.TILE_WIDTH/6,WorldImageLoader.TILE_WIDTH*4/5);
    private static PhysicalObject bigTreeShadow = new PhysicalObject(treeOffset, WorldImageLoader.TILE_WIDTH*2/3,
            WorldImageLoader.TILE_WIDTH/3);
    private static PhysicalObject smallTreeShadow = new PhysicalObject(treeOffset, WorldImageLoader.TILE_WIDTH*2/3,
            WorldImageLoader.TILE_WIDTH/3);

    private int[] bigTreeTileID = new int[]{220, 222, 224};
    private int[] smallTreeTileID = new int[]{221, 223, 225};

    /*
     All Objects
     */
    private HashMap<Integer, PhysicalObject> dimensionsAccordingToID;

    /**
     * Constructor initiates all shadow's dimensions
     */
    ShadowFactory() {
        initiateShadowMap();
    }

    /**
     * Helper method
     */
    private void initiateShadowMap() {
        dimensionsAccordingToID = new HashMap<>();

        for (int i=0; i<bigTreeTileID.length; i++) {
            dimensionsAccordingToID.put(bigTreeTileID[i], bigTreeShadow);
        }

        for (int i=0; i<smallTreeTileID.length; i++) {
            dimensionsAccordingToID.put(smallTreeTileID[i], smallTreeShadow);
        }
    }

    /**
     * Creates an ellipsed shadow according to ID
     */
    Ellipse getShadow(int ID, Point position) {

        if (dimensionsAccordingToID.get(ID) == null) {
            System.out.println(ID);
        }
        PhysicalObject dimensionsOffsets = dimensionsAccordingToID.get(ID);

        Point offsetPoint = new Point(position.getX() + dimensionsOffsets.getX(), position.getY() + dimensionsOffsets.getY());

        return new Ellipse(SHADOW_COLOR, offsetPoint, dimensionsOffsets.getWidth(), dimensionsOffsets.getHeight());
    }

    /**
     * Creates a shadow for the player Object
     */
    static Ellipse getPlayerShadow(Player player){
        return new Ellipse(ShadowFactory.SHADOW_COLOR, player.getCollisionPosition(), player.getCollisionDimensions().getX(),
                player.getCollisionDimensions().getY());
    }

}
