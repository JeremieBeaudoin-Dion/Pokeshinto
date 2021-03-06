package duringMenus;

import combat.InfoHandler;
import combat.combatActions.Passive;
import combat.combatActions.Skill;
import images.*;
import images.Image;
import pokeshinto.Player;
import pokeshinto.Pokeshinto;
import world.ObjectHandlerInWorld;
import world.items.Item;

import java.awt.*;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * Returns all necessary objects for the player menu in World
 *
 * @author Jérémie Beaudoin-Dion
 */
public class ObjectHandlerInPlayerMenu {

    private ImageLoaderInMainMenu imageLoaderInMainMenu;
    private MenuImageLoader menuImageLoader;
    private ObjectHandlerInWorld objectHandlerInWorld;
    private Camera camera;

    private PhysicalObject mainMenuObject = new PhysicalObject(new Position(100, 100), 200, 400);
    private PhysicalObject secondaryMenuObject = new PhysicalObject(new Position(300, 50), 500, 500);

    private Stack<Menu> allMenus;

    // Fonts
    private static final Font FONT_TITLE = new Font("Century Schoolbook", Font.BOLD, 50);
    private static final Font FONT_BUTTON = new Font("Century Schoolbook", Font.PLAIN, 25);
    private static final Font FONT_DESCRIPTION = new Font("Century Schoolbook", Font.PLAIN, 18);

    private static final Font FONT_SMALL = new Font("Century Schoolbook", Font.PLAIN, 14);
    private static final Font FONT_SMALL_ITALIC = new Font("Century Schoolbook", Font.ITALIC, 14);
    private static final Font FONT_SMALL_BOLD = new Font("Century Schoolbook", Font.BOLD, 14);

    private static final Color COLOR_TEXT_YELLOW = new Color(190, 170, 115);

    public ObjectHandlerInPlayerMenu(ObjectHandlerInWorld objectHandlerInWorld, MenuImageLoader menuImageLoader, Camera camera) throws IOException {
        this.menuImageLoader = menuImageLoader;
        imageLoaderInMainMenu = new ImageLoaderInMainMenu();
        this.objectHandlerInWorld = objectHandlerInWorld;
        this.camera = camera;

        reset();
    }

    private Menu getMainMenu() {
        List<String> allButtons = new LinkedList<>();
        allButtons.add("Spirits");
        allButtons.add("Items");
        allButtons.add("Equipment");
        allButtons.add("Save Game");
        allButtons.add("Quit");

        return new Menu("Main", allButtons, null);
    }

    private Menu getSpiritsMenu() {
        List<String> allButtons = new LinkedList<>();

        Iterator<Pokeshinto> iter = Player.equipedPokeshintos.iterator();

        while(iter.hasNext()) {
            allButtons.add(iter.next().getId());
        }

        allButtons.add("Return");

        return new Menu("Spirits", allButtons, null);
    }

    private Menu getItemsMenu() {
        List<String> allButtons = Player.allItems.getMenuButtons();

        allButtons.add("Return");

        return new Menu("Items", allButtons, null);
    }

    /**
     * Returns all physical objects to show on screen
     */
    public List<PhysicalObject> get() {
        List<PhysicalObject> allObjects = new LinkedList<>();

        allObjects.addAll(objectHandlerInWorld.get());

        allObjects.addAll(getMainMenu(allMenus.peek()));

        if (!getCurrentMenu().getID().equals("Main")) {
            allObjects.addAll(getSecondaryMenu(getCurrentMenu()));
        }

        return allObjects;
    }

    private List<PhysicalObject> getMainMenu(Menu currentMenu) {
        List<PhysicalObject> physicalObjects = new LinkedList<>();

        Position menuWithCamera = replacePositionAccordingToCamera(mainMenuObject.getPosition());

        physicalObjects.add(new Image(menuWithCamera, mainMenuObject.getWidth(), mainMenuObject.getHeight(),
                menuImageLoader.getButtonBackground(mainMenuObject.getWidth(), mainMenuObject.getHeight())));

        physicalObjects.add(new Image(menuWithCamera, mainMenuObject.getWidth(), mainMenuObject.getHeight(),
                menuImageLoader.getMenuSelector(mainMenuObject.getWidth(), mainMenuObject.getHeight())));

        // Position, width, height
        int x = menuWithCamera.getX();
        int y = menuWithCamera.getY();
        int width = mainMenuObject.getWidth();
        int height = mainMenuObject.getHeight()/currentMenu.getNumberOfButtons();

        int selectedID = currentMenu.getCurrentSelectionInt();

        for (int i=0; i<currentMenu.getNumberOfButtons(); i++) {
            // Sets the buttons to selected
            if (i == selectedID) {
                physicalObjects.add(new Image(new Position(x, y), width, height, menuImageLoader.getMenuSelector(width, height)));
            }

            String buttonText = currentMenu.getButton(i);

            physicalObjects.add(new Text(new Position(x + width/2, y + FONT_BUTTON.getSize() * 2), buttonText, FONT_BUTTON,
                    "Center", COLOR_TEXT_YELLOW));

            y += height;
        }

        return physicalObjects;
    }

    private Position replacePositionAccordingToCamera(Position position) {
        return new Position(position.getX() + camera.getPosition().getX(), position.getY() + camera.getPosition().getY());
    }

    private List<PhysicalObject> getSecondaryMenu(Menu currentMenu) {
        List<PhysicalObject> physicalObjects = new LinkedList<>();

        Position menuWithCamera = replacePositionAccordingToCamera(secondaryMenuObject.getPosition());

        physicalObjects.add(new Image(menuWithCamera.copy(), secondaryMenuObject.getWidth(), secondaryMenuObject.getHeight(),
                menuImageLoader.getBackground(secondaryMenuObject.getWidth(), secondaryMenuObject.getHeight())));

        physicalObjects.add(new Image(menuWithCamera.copy(), secondaryMenuObject.getWidth(), secondaryMenuObject.getHeight(),
                menuImageLoader.getMenuSelector(secondaryMenuObject.getWidth(), secondaryMenuObject.getHeight())));

        int x = menuWithCamera.getX();
        int y = menuWithCamera.getY();

        x += secondaryMenuObject.getWidth()/2;
        y += FONT_TITLE.getSize();

        physicalObjects.add(new Text(new Position(x, y), currentMenu.getID(), FONT_TITLE, "Center", Color.BLACK));

        y += FONT_TITLE.getSize()/2;

        physicalObjects.add(new Text(new Position(x, y), getMenuDescription(currentMenu.getID()), FONT_DESCRIPTION, "Center", Color.BLACK));

        x = menuWithCamera.getX() + secondaryMenuObject.getWidth()/10;
        y += FONT_DESCRIPTION.getSize() * 2;

        physicalObjects.addAll(getDescriptionObjects(currentMenu.getID(), currentMenu.getSelected(), new Position(x, y)));

        return physicalObjects;
    }

    private String getMenuDescription(String menuID) {
        switch (menuID) {
            case "Spirits":
                if (Player.equipedPokeshintos.size() > 0) {
                    return "To modify your spirits, see a priest.";
                } else {
                    return "You have no spirits. Find some in the wilderness.";
                }

            case "Items":
                return "To buy new items, see a merchant.";

            case "Equipment":
                return "To modify your equipment, see the inn keeper.";
        }

        throw new IllegalArgumentException("The menuID " + menuID + " is not handled in getMenuDescription.");
    }

    private List<PhysicalObject> getDescriptionObjects(String menuID, String selected, Position position) {
        List<PhysicalObject> allObjects = new LinkedList<>();

        if (selected.equals("Return")) {
            return allObjects;
        }

        switch (menuID) {
            case "Spirits":
                return getPokeshintoDescription(position);

            case "Items":
                return getItemDescription(selected, position);

        }

        throw new IllegalArgumentException("The menuID " + menuID + " is not handled in getDescriptionObjects.");
    }

    /**
     * Pokeshinto
     */
    private List<PhysicalObject> getPokeshintoDescription(Position position) {
        List<PhysicalObject> physicalObjects = new LinkedList<>();

        Pokeshinto pokeshinto = Player.equipedPokeshintos.get(getCurrentMenu().getCurrentSelectionInt());

        physicalObjects.addAll(getPokeshintoInformation(pokeshinto, position));

        physicalObjects.addAll(getPokeshintoSkill(pokeshinto, position));

        return physicalObjects;
    }

    private List<PhysicalObject> getPokeshintoInformation(Pokeshinto pokeshinto, Position position) {
        List<PhysicalObject> physicalObjects = new LinkedList<>();

        physicalObjects.add(new Text(position.copy(), "Information", FONT_SMALL_BOLD, "Left", Color.BLACK));

        position.addY(FONT_SMALL_BOLD.getSize());

        physicalObjects.add(new Text(position.copy(), pokeshinto.getDescription(), FONT_SMALL, "Left", Color.BLACK));

        position.addY(FONT_SMALL.getSize());

        physicalObjects.add(new Text(position.copy(), "Level : " + pokeshinto.getLevel(), FONT_SMALL, "Left", Color.BLACK));

        position.addY(FONT_SMALL.getSize());

        physicalObjects.add(new Text(position.copy(), "Next Level : " + pokeshinto.getNextLevelBonusDescription(), FONT_SMALL, "Left", Color.BLACK));

        position.addY(FONT_SMALL.getSize() * 2);

        return physicalObjects;
    }

    private List<PhysicalObject> getPokeshintoPassive(Pokeshinto pokeshinto, Position position) {
        List<PhysicalObject> physicalObjects = new LinkedList<>();

        Iterator<Passive> iter = pokeshinto.getEquipedPassives().iterator();

        //TODO: Complete this when passives are implemented

        return physicalObjects;
    }

    private List<PhysicalObject> getPokeshintoSkill(Pokeshinto pokeshinto, Position position) {
        List<PhysicalObject> physicalObjects = new LinkedList<>();

        physicalObjects.add(new Text(position.copy(), "Skills", FONT_SMALL_BOLD, "Left", Color.BLACK));

        position.addY(FONT_SMALL_BOLD.getSize());

        Iterator<Skill> iter = pokeshinto.getEquipedSkills().iterator();
        Skill currentSkill;
        String text;

        while(iter.hasNext()) {
            currentSkill = iter.next();

            text = currentSkill.getId();

            if (currentSkill.getCurrentBuildup() > 0) {
                text += " (Buildup: " + currentSkill.getCurrentBuildup() + ")";
            }

            if (currentSkill.getCurrentCooldown() > 0) {
                text += " (Cooldown: " + currentSkill.getCurrentCooldown() + ")";
            }

            if (currentSkill.isOnce()) {
                text += " (Once)";
            }

            physicalObjects.add(new Text(position.copy(), text, FONT_SMALL, "Left", Color.BLACK));

            position.addY(FONT_SMALL.getSize());

            text = InfoHandler.getSkillInformation(currentSkill.getId());

            physicalObjects.add(new Text(position.copy(), text, FONT_SMALL_ITALIC, "Left", Color.BLACK));

            position.addY(FONT_SMALL.getSize() * 2);

        }

        return physicalObjects;
    }

    /**
     * Items
     */
    private List<PhysicalObject> getItemDescription(String id, Position position) {
        List<PhysicalObject> physicalObjects = new LinkedList<>();

        Item currentItem = Item.getNewItemFromId(id);

        physicalObjects.add(new Text(position.copy(), "Price", FONT_SMALL_BOLD, "Left", Color.BLACK));

        position.addY(FONT_SMALL_BOLD.getSize());

        physicalObjects.add(new Text(position.copy(), Integer.toString(currentItem.getValue()), FONT_SMALL, "Left", Color.BLACK));

        position.addY(FONT_SMALL.getSize() * 2);

        physicalObjects.add(new Text(position.copy(), "Information", FONT_SMALL_BOLD, "Left", Color.BLACK));

        position.addY(FONT_SMALL_BOLD.getSize());

        physicalObjects.add(new Text(position.copy(), Item.getInformation(currentItem.getId()), FONT_SMALL, "Left", Color.BLACK));

        position.addY(FONT_SMALL.getSize() * 2);

        physicalObjects.add(new Text(position.copy(), "Quantity", FONT_SMALL_BOLD, "Left", Color.BLACK));

        position.addY(FONT_SMALL_BOLD.getSize());

        physicalObjects.add(new Text(position.copy(), Integer.toString(Player.allItems.getAmount(currentItem.getId())), FONT_SMALL, "Left", Color.BLACK));

        return physicalObjects;
    }

    /**
     * Called every frame
     */
    public void update() {

    }

    /**
     * Resets the menus
     */
    void reset() {
        allMenus = new Stack<>();
        allMenus.push(getMainMenu());
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

    void doMenuAction(String id) {
        if (id.equals("Return")) {
            if (allMenus.size() > 0) {
                allMenus.pop();
            }

        } else if (id.equals("Spirits")) {
            allMenus.push(getSpiritsMenu());

        } else if (id.equals("Items")) {
            allMenus.push(getItemsMenu());
        }
    }

    boolean menuIsEmpty() {
        return allMenus.size() == 0;
    }

}
