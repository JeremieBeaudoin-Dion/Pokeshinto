package duringMenus;

import images.Camera;
import images.PhysicalObject;
import pokeshinto.Action;
import world.ObjectHandlerInWorld;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * Returns all necessary objects for menus in world, except Player menu
 *
 * @author Jérémie Beaudoin-Dion
 */
public class ObjectHandlerInWorldMenu {

    private MenuImageLoader menuImageLoader;
    private ObjectHandlerInWorld objectHandlerInWorld;
    private Camera camera;

    private Stack<Menu> allMenus;

    /**
     * Constructor
     */
    public ObjectHandlerInWorldMenu(ObjectHandlerInWorld objectHandlerInWorld, MenuImageLoader menuImageLoader, Camera camera) {
        this.menuImageLoader = menuImageLoader;
        this.objectHandlerInWorld = objectHandlerInWorld;
        this.camera = camera;

        reset();
    }

    /**
     * Returns all physical objects to show on screen
     */
    public List<PhysicalObject> get() {
        List<PhysicalObject> allObjects = new LinkedList<>();

        allObjects.addAll(objectHandlerInWorld.get());

        allObjects.addAll(getMenuObject(allMenus.peek()));

        return allObjects;
    }

    private List<PhysicalObject> getMenuObject(Menu currentMenu) {
        List<PhysicalObject> allObjects = new LinkedList<>();

        return allObjects;
    }

    /**
     * Called every frame
     */
    public void update() {

    }

    void setMenu(Menu menu) {
        allMenus.push(menu);
    }

    /**
     * Resets the menus
     */
    void reset() {
        allMenus = new Stack<>();
    }

    /**
     * @return the current menu
     */
    Menu getCurrentMenu() {
        if (!allMenus.isEmpty()) {
            return allMenus.peek();
        }
        return null;
    }

    void doMenuAction(Action<String> action) {
        if (action.getValue().equals("Return")) {
            if (allMenus.size() > 0) {
                allMenus.pop();
            }

        }
    }

    boolean isEmpty() {
        return allMenus.size() == 0;
    }
}
