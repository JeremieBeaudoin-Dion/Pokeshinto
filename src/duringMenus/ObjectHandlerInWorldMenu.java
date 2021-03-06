package duringMenus;

import images.*;
import images.Image;
import pokeshinto.Action;
import pokeshinto.Game;
import world.ImageLoaderInWorld;
import world.ObjectHandlerInWorld;
import world.items.Item;
import world.worldObjects.Villager;
import world.worldObjects.Villager_Inn;
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

    private static final String MENU_SHOP_ROOT_ID = "Root Shop";
    public static final String MENU_SHOP_BUY_ID = "Buy";
    // public static final String MENU_SHOP_SELL_ID = "Sell";
    public static final String REST_ACTION_ID = "Rest";
    public static final String RETURN_ACTION_ID = "Return";
    public static final String ALLERT_ACTION_ID = "Allert";

    private static final int BACKGROUND_MENU_WIDTH = 700;
    private static final int BACKGROUND_MENU_HEIGHT = 400;
    private static final Position BACKGROUND_MENU_INSET = new Position(100, 100);

    private static final int BACKGROUND_BUTTON_WIDTH = 700;
    private static final int BACKGROUND_BUTTON_HEIGHT = 100;
    private static final Position BACKGROUND_BUTTON_INSET = new Position(100, 400);

    private static final int BACKGROUND_SHOP_BUTTON_WIDTH = 200;
    private static final int BACKGROUND_SHOP_BUTTON_HEIGHT = 400;
    private static final Position BACKGROUND_SHOP_BUTTON_INSET = new Position(100, 100);

    private static final Position ALLERT_POSITION = new Position(Game.WINDOW_WIDTH/2, Game.WINDOW_HEIGHT/2);

    private static final Position CONVERSATION_INSETS = new Position(150, 150);

    private static final Font FONT_CONVERSATION_TEXT = new Font("Century Schoolbook", Font.PLAIN, 20);
    private static final Font FONT_ALLERT = new Font("Century Schoolbook", Font.PLAIN, 40);
    private static final Font FONT_BUTTON = new Font("Century Schoolbook", Font.PLAIN, 35);
    private static final Font FONT_BUTTON_SMALL = new Font("Century Schoolbook", Font.BOLD, 18);
    private static final Font FONT_DESCRIPTION = new Font("Century Schoolbook", Font.PLAIN, 16);

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

    private Menu getShopRootMenu() {
        List<String> buttons = new LinkedList<>();

        buttons.add(MENU_SHOP_BUY_ID);
        // buttons.add(MENU_SHOP_SELL_ID);
        buttons.add(RETURN_ACTION_ID);

        return new Menu(MENU_SHOP_ROOT_ID, buttons, null);
    }

    private Menu getShopBuyMenu() {
        List<String> buttons = new LinkedList<>();

        if (!(currentVillager instanceof Villager_Shop)) {
            throw new IllegalStateException("It is impossible to be in a ShopMenu state with the current Villager " + currentVillager.getClass());
        }

        for (Item item : ((Villager_Shop) currentVillager).getItems()) {
            buttons.add(item.getId());
        }

        buttons.add(RETURN_ACTION_ID);

        return new Menu(MENU_SHOP_BUY_ID, buttons, null);
    }

    private Menu getInnMenu() {
        List<String> buttons = new LinkedList<>();

        buttons.add(REST_ACTION_ID);
        buttons.add(RETURN_ACTION_ID);

        return new Menu(MENU_SHOP_ROOT_ID, buttons, null);
    }

    private Menu getDefaultMenu() {
        List<String> buttons = new LinkedList<>();

        buttons.add(RETURN_ACTION_ID);

        return new Menu(MENU_SHOP_ROOT_ID, buttons, null);
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
        if (getCurrentMenu().hasAllert()) {
            return getAllertMenu();
        }

        switch(getCurrentMenu().getID()) {
            case MENU_SHOP_ROOT_ID:
                return getConversationObjects();

            case MENU_SHOP_BUY_ID:
                return getShopObjects();
        }

        throw new IllegalArgumentException("The menuID: " + getCurrentMenu().getID() + " is not handled");
    }

    /**
     * Shows a basic allert
     */
    private List<PhysicalObject> getAllertMenu() {
        List<PhysicalObject> physicalObjects = new LinkedList<>();

        physicalObjects.addAll(getMenuBackground());

        Position allertPosition = ALLERT_POSITION.copy();
        allertPosition.addX(camera.getPosition().getX());
        allertPosition.addY(camera.getPosition().getY());

        physicalObjects.add(new Text(allertPosition, getCurrentMenu().getAllert(), FONT_ALLERT, "Center", Color.BLACK));

        return physicalObjects;
    }

    /**
     * The conversation menu
     */
    private List<PhysicalObject> getConversationObjects() {
        List<PhysicalObject> physicalObjects = new LinkedList<>();

        physicalObjects.addAll(getConversationBackground());
        physicalObjects.addAll(getConversation());
        physicalObjects.addAll(getConversationButtons());

        return physicalObjects;
    }

    private List<PhysicalObject> getConversationBackground() {
        List<PhysicalObject> physicalObjects = new LinkedList<>();

        physicalObjects.addAll(getMenuBackground());

        Position backgroundPosition = camera.getPosition().copy();
        backgroundPosition.addX(BACKGROUND_BUTTON_INSET.getX());
        backgroundPosition.addY(BACKGROUND_BUTTON_INSET.getY());

        physicalObjects.add(new Image(backgroundPosition.copy(), BACKGROUND_BUTTON_WIDTH, BACKGROUND_BUTTON_HEIGHT,
                menuImageLoader.getButtonBackground(BACKGROUND_BUTTON_WIDTH, BACKGROUND_BUTTON_HEIGHT)));

        physicalObjects.add(new Image(backgroundPosition.copy(), BACKGROUND_BUTTON_WIDTH, BACKGROUND_BUTTON_HEIGHT,
                menuImageLoader.getMenuSelector(BACKGROUND_BUTTON_WIDTH, BACKGROUND_BUTTON_HEIGHT)));

        return physicalObjects;
    }

    private List<PhysicalObject> getMenuBackground() {
        List<PhysicalObject> physicalObjects = new LinkedList<>();

        Position backgroundPosition = camera.getPosition().copy();
        backgroundPosition.addX(BACKGROUND_MENU_INSET.getX());
        backgroundPosition.addY(BACKGROUND_MENU_INSET.getY());

        physicalObjects.add(new Image(backgroundPosition.copy(), BACKGROUND_MENU_WIDTH, BACKGROUND_MENU_HEIGHT,
                menuImageLoader.getBackground(BACKGROUND_MENU_WIDTH, BACKGROUND_MENU_HEIGHT)));

        physicalObjects.add(new Image(backgroundPosition.copy(), BACKGROUND_MENU_WIDTH, BACKGROUND_MENU_HEIGHT,
                menuImageLoader.getMenuSelector(BACKGROUND_MENU_WIDTH, BACKGROUND_MENU_HEIGHT)));

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
     * The Shop menu
     */
    private List<PhysicalObject> getShopObjects() {
        List<PhysicalObject> physicalObjects = new LinkedList<>();

        physicalObjects.addAll(getShopBackground());
        physicalObjects.addAll(getShopButtons());

        return physicalObjects;
    }

    private List<PhysicalObject> getShopBackground() {
        List<PhysicalObject> physicalObjects = new LinkedList<>();

        physicalObjects.addAll(getMenuBackground());

        Position backgroundPosition = camera.getPosition().copy();
        backgroundPosition.addX(BACKGROUND_SHOP_BUTTON_INSET.getX());
        backgroundPosition.addY(BACKGROUND_SHOP_BUTTON_INSET.getY());

        physicalObjects.add(new Image(backgroundPosition.copy(), BACKGROUND_SHOP_BUTTON_WIDTH, BACKGROUND_SHOP_BUTTON_HEIGHT,
                menuImageLoader.getButtonBackground(BACKGROUND_SHOP_BUTTON_WIDTH, BACKGROUND_SHOP_BUTTON_HEIGHT)));

        physicalObjects.add(new Image(backgroundPosition.copy(), BACKGROUND_SHOP_BUTTON_WIDTH, BACKGROUND_SHOP_BUTTON_HEIGHT,
                menuImageLoader.getMenuSelector(BACKGROUND_SHOP_BUTTON_WIDTH, BACKGROUND_SHOP_BUTTON_HEIGHT)));

        return physicalObjects;
    }

    private List<PhysicalObject> getShopButtons() {
        List<PhysicalObject> physicalObjects = new LinkedList<>();

        int selectedButtonID = getCurrentMenu().getCurrentSelectionInt();

        // Position, width, height
        int x = BACKGROUND_SHOP_BUTTON_INSET.getX() + camera.getPosition().getX();
        int y = BACKGROUND_SHOP_BUTTON_INSET.getY() + camera.getPosition().getY();
        int width = BACKGROUND_SHOP_BUTTON_WIDTH;
        int height = BACKGROUND_SHOP_BUTTON_HEIGHT / getCurrentMenu().getNumberOfButtons();

        for (int i=0; i<getCurrentMenu().getNumberOfButtons(); i++) {
            if (i == selectedButtonID) {
                physicalObjects.add(new Image(new Position(x, y), width, height, menuImageLoader.getMenuSelector(width, height)));
            }

            physicalObjects.add(new Text(new Position(x + width/2, y + height/2),
                    getCurrentMenu().getButton(i), FONT_BUTTON_SMALL, "Center", COLOR_TEXT_YELLOW));

            if (!getCurrentMenu().getButton(i).equals(RETURN_ACTION_ID)) {
                physicalObjects.add(new Text(new Position(Game.WINDOW_WIDTH/2 + BACKGROUND_SHOP_BUTTON_INSET.getX(), y + height/2),
                        getItemDescription(getCurrentMenu().getButton(i)), FONT_DESCRIPTION, "Center", Color.BLACK));
            }

            y += height;
        }

        return physicalObjects;
    }

    private String getItemDescription(String itemID) {
        return "Cost : " + Integer.toString(Item.getCost(itemID)) + "; " + Item.getInformation(itemID);
    }

    /**
     * Called every frame
     */
    public void update() {

    }

    public void setVillager(Villager newVillager) {
        currentVillager = newVillager;

        if (currentVillager instanceof Villager_Shop) {
            allMenus.push(getShopRootMenu());

        } else if (currentVillager instanceof Villager_Inn) {
            allMenus.push(getInnMenu());

        } else {
            allMenus.push(getDefaultMenu());
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
        if (action.getValue().equals(RETURN_ACTION_ID)) {
            if (allMenus.size() > 0) {
                allMenus.pop();
            }

        } else if (action.getKey().equals(ALLERT_ACTION_ID)) {
            getCurrentMenu().setAllert(action.getValue());

        } else if (action.getValue().equals(MENU_SHOP_BUY_ID)) {
            allMenus.push(getShopBuyMenu());

        }
    }

    boolean isEmpty() {
        return allMenus.size() == 0;
    }
}
