package duringMenus;

import images.Camera;
import images.Image;
import images.PhysicalObject;
import images.Position;
import pokeshinto.Action;
import world.ObjectHandlerInWorld;
import world.worldObjects.Villager;
import world.worldObjects.Villager_Shop;

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

    private Villager currentVillager;

    private static final String MENU_ROOT_SHOP_ID = "Root Shop";
    public static final String MENU_SHOP_ID = "Shop";

    private static final int BACKGROUND_MENU_WIDTH = 500;
    private static final int BACKGROUND_MENU_HEIGHT = 400;
    private static final Position BACKGROUND_MENU_INSET = new Position(100, 100);

    private static final int BACKGROUND_BUTTON_WIDTH = 500;
    private static final int BACKGROUND_BUTTON_HEIGHT = 100;
    private static final Position BACKGROUND_BUTTON_INSET = new Position(400, 100);

    /**
     * Constructor
     */
    public ObjectHandlerInWorldMenu(ObjectHandlerInWorld objectHandlerInWorld, MenuImageLoader menuImageLoader, Camera camera) {
        this.menuImageLoader = menuImageLoader;
        this.objectHandlerInWorld = objectHandlerInWorld;
        this.camera = camera;

        reset();
    }

    private Menu getRootShopMenu() {
        List<String> buttons = new LinkedList<>();

        buttons.add("Buy");
        buttons.add("Sell");
        buttons.add("Return");

        return new Menu(MENU_ROOT_SHOP_ID, buttons, null);
    }

    /**
     * Returns all physical objects to show on screen
     */
    public List<PhysicalObject> get() {
        List<PhysicalObject> allObjects = new LinkedList<>();

        allObjects.addAll(objectHandlerInWorld.get());

        allObjects.addAll(getMenuObject());

        return allObjects;
    }

    private List<PhysicalObject> getMenuObject() {
        switch(getCurrentMenu().getID()) {
            case MENU_ROOT_SHOP_ID:
                return getConversationObject();
        }

        throw new IllegalArgumentException("The menuID: " + getCurrentMenu().getID() + " is not handled");
    }

    private List<PhysicalObject> getConversationObject() {


        return null;
    }

    private List<PhysicalObject> getConversationBackground() {
        List<PhysicalObject> physicalObjects = new LinkedList<>();

        Position backgroundPosition = camera.getPosition().copy();
        backgroundPosition.addX(BACKGROUND_MENU_INSET.getX());
        backgroundPosition.addX(BACKGROUND_MENU_INSET.getY());

        physicalObjects.add(new Image(backgroundPosition.copy(), BACKGROUND_MENU_WIDTH, BACKGROUND_MENU_HEIGHT,
                menuImageLoader.getBackground(BACKGROUND_MENU_WIDTH, BACKGROUND_MENU_HEIGHT)));

        physicalObjects.add(new Image(backgroundPosition.copy(), BACKGROUND_MENU_WIDTH, BACKGROUND_MENU_HEIGHT,
                menuImageLoader.getMenuSelector(BACKGROUND_MENU_WIDTH, BACKGROUND_MENU_HEIGHT)));

        backgroundPosition.addX(BACKGROUND_BUTTON_INSET.getX());
        backgroundPosition.addX(BACKGROUND_BUTTON_INSET.getY());

        physicalObjects.add(new Image(backgroundPosition.copy(), BACKGROUND_BUTTON_WIDTH, BACKGROUND_BUTTON_HEIGHT,
                menuImageLoader.getButtonBackground(BACKGROUND_BUTTON_WIDTH, BACKGROUND_BUTTON_HEIGHT)));

        return physicalObjects;
    }

    private List<PhysicalObject> getConversationObjects() {
        List<PhysicalObject> physicalObjects = new LinkedList<>();




        return physicalObjects;
    }

    /**
     * Called every frame
     */
    public void update() {

    }

    public void setVillager(Villager newVillager) {
        currentVillager = newVillager;

        allMenus.push(currentVillager.getMenu());

        if (currentVillager instanceof Villager_Shop) {
            allMenus.push(getRootShopMenu());
        }
    }

    /**
     * Resets the menus
     */
    void reset() {
        allMenus = new Stack<>();
        currentVillager = null;
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
