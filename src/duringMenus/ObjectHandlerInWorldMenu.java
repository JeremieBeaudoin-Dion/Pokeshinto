package duringMenus;

import images.*;
import images.Image;
import pokeshinto.Action;
import world.ImageLoaderInWorld;
import world.ObjectHandlerInWorld;
import world.worldObjects.Villager;
import world.worldObjects.Villager_Shop;

import java.awt.*;
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

    private static final int BACKGROUND_MENU_WIDTH = 700;
    private static final int BACKGROUND_MENU_HEIGHT = 400;
    private static final Position BACKGROUND_MENU_INSET = new Position(100, 100);

    private static final int BACKGROUND_BUTTON_WIDTH = 700;
    private static final int BACKGROUND_BUTTON_HEIGHT = 100;
    private static final Position BACKGROUND_BUTTON_INSET = new Position(100, 400);

    private static final Position CONVERSATION_INSETS = new Position(150, 150);

    private static final Font FONT_CONVERSATION_TEXT = new Font("Century Schoolbook", Font.PLAIN, 20);
    private static final Font FONT_BUTTON = new Font("Century Schoolbook", Font.PLAIN, 35);

    private static final Color COLOR_TEXT_YELLOW = new Color(190, 170, 115);

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
        List<PhysicalObject> physicalObjects = new LinkedList<>();

        physicalObjects.addAll(getConversationBackground());
        physicalObjects.addAll(getConversation());
        physicalObjects.addAll(getConversationButtons());

        return physicalObjects;
    }

    private List<PhysicalObject> getConversationBackground() {
        List<PhysicalObject> physicalObjects = new LinkedList<>();

        Position backgroundPosition = camera.getPosition().copy();
        backgroundPosition.addX(BACKGROUND_MENU_INSET.getX());
        backgroundPosition.addY(BACKGROUND_MENU_INSET.getY());

        physicalObjects.add(new Image(backgroundPosition.copy(), BACKGROUND_MENU_WIDTH, BACKGROUND_MENU_HEIGHT,
                menuImageLoader.getBackground(BACKGROUND_MENU_WIDTH, BACKGROUND_MENU_HEIGHT)));

        physicalObjects.add(new Image(backgroundPosition.copy(), BACKGROUND_MENU_WIDTH, BACKGROUND_MENU_HEIGHT,
                menuImageLoader.getMenuSelector(BACKGROUND_MENU_WIDTH, BACKGROUND_MENU_HEIGHT)));

        backgroundPosition = camera.getPosition().copy();
        backgroundPosition.addX(BACKGROUND_BUTTON_INSET.getX());
        backgroundPosition.addY(BACKGROUND_BUTTON_INSET.getY());

        physicalObjects.add(new Image(backgroundPosition.copy(), BACKGROUND_BUTTON_WIDTH, BACKGROUND_BUTTON_HEIGHT,
                menuImageLoader.getButtonBackground(BACKGROUND_BUTTON_WIDTH, BACKGROUND_BUTTON_HEIGHT)));

        physicalObjects.add(new Image(backgroundPosition.copy(), BACKGROUND_BUTTON_WIDTH, BACKGROUND_BUTTON_HEIGHT,
                menuImageLoader.getMenuSelector(BACKGROUND_BUTTON_WIDTH, BACKGROUND_BUTTON_HEIGHT)));

        return physicalObjects;
    }

    private List<PhysicalObject> getConversation() {
        List<PhysicalObject> physicalObjects = new LinkedList<>();

        Position conversationPosition = camera.getPosition().copy();
        conversationPosition.addX(CONVERSATION_INSETS.getX());
        conversationPosition.addY(CONVERSATION_INSETS.getY());

        physicalObjects.add(new Image(conversationPosition.copy(), ImageLoaderInWorld.VILLAGER_WIDTH, ImageLoaderInWorld.VILLAGER_HEIGHT,
                menuImageLoader.getCharacterImage(currentVillager)));

        conversationPosition.addX((int) (ImageLoaderInWorld.VILLAGER_WIDTH * 1.5));

        physicalObjects.addAll(getConversationText(conversationPosition.copy()));

        return physicalObjects;
    }

    private List<PhysicalObject> getConversationText(Position position) {
        List<PhysicalObject> physicalObjects = new LinkedList<>();

        List<String> allText = currentVillager.getConversation();

        for (String text : allText) {
            physicalObjects.add(new Text(position.copy(), text, FONT_CONVERSATION_TEXT, "Left", Color.BLACK));

            position.addY(FONT_CONVERSATION_TEXT.getSize() * 2);
        }

        return physicalObjects;
    }

    private List<PhysicalObject> getConversationButtons() {
        List<PhysicalObject> physicalObjects = new LinkedList<>();

        int selectedButtonID = getCurrentMenu().getCurrentSelectionInt();

        // Position, width, height
        int x = BACKGROUND_BUTTON_INSET.getX() + camera.getPosition().getX();
        int y = BACKGROUND_BUTTON_INSET.getY() + camera.getPosition().getY();
        int width = BACKGROUND_BUTTON_WIDTH / getCurrentMenu().getNumberOfButtons();
        int height = BACKGROUND_BUTTON_HEIGHT;

        for (int i=0; i<getCurrentMenu().getNumberOfButtons(); i++) {
            if (i == selectedButtonID) {
                physicalObjects.add(new Image(new Position(x, y), width, height, menuImageLoader.getMenuSelector(width, height)));
            }

            physicalObjects.add(new Text(new Position(x + width/2, y + FONT_BUTTON.getSize() * 2),
                    getCurrentMenu().getButton(i), FONT_BUTTON, "Center", COLOR_TEXT_YELLOW));

            x += width;
        }

        return physicalObjects;
    }

    /**
     * Called every frame
     */
    public void update() {

    }

    public void setVillager(Villager newVillager) {
        currentVillager = newVillager;

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
