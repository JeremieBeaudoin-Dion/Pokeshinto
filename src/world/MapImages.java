package world;

import images.PhysicalObject;

import java.util.List;

/**
 * An object which contains all the images of the current map
 *
 * @author Jérémie Beaudoin-Dion
 */
public class MapImages {

    private List<PhysicalObject> backgroundObjects;
    private List<PhysicalObject> foregroundObjects;
    private List<PhysicalObject> solidObjects;
    private List<PhysicalObject> floatingObjects;
    private List<PhysicalObject> shadowObjects;

    /**
     * Must be called before putting the map on frame
     *
     * @param objects the PhysicalObjects to show on the frame
     */
    void setBackgroundObjects(List<PhysicalObject> objects) {
        this.backgroundObjects = objects;
    }

    void setForegroundObjects(List<PhysicalObject> objects) {
        this.foregroundObjects = objects;
    }

    void setSolidObjects(List<PhysicalObject> objects) {
        this.solidObjects = objects;
    }

    void setFloatingObjects(List<PhysicalObject> objects) {
        this.floatingObjects = objects;
    }

    void setShadows(List<PhysicalObject> objects) {
        this.shadowObjects = objects;
    }

    /**
     * Getters
     */
    List<PhysicalObject> getBackgroundObjects() {
        return backgroundObjects;
    }

    List<PhysicalObject> getForegroundObjects() {
        return foregroundObjects;
    }

    List<PhysicalObject> getSolidObjects() {
        return solidObjects;
    }

    List<PhysicalObject> getFloatingObjects() {
        return floatingObjects;
    }

    List<PhysicalObject> getShadowObjects() {
        return shadowObjects;
    }
}
