package duringMenus;

import images.*;
import images.Image;
import world.ObjectHandlerInWorld;

import java.awt.*;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * Returns all necessary objects to create a menu in World
 *
 * @author Jérémie Beaudoin-Dion
 */
public class ObjectHandlerInPlayerMenu {

    private ImageLoaderInMainMenu imageLoaderInMainMenu;
    private MenuImageLoader menuImageLoader;
    private ObjectHandlerInWorld objectHandlerInWorld;
    private Camera camera;

    private PhysicalObject physicalMenu = new PhysicalObject(new Position(75, 75), 250, 450);

    private Stack<Menu> allMenus;

    // Fonts
    private static final Font FONT_BUTTON = new Font("Century Schoolbook", Font.PLAIN, 25);
    private static final Font FONT_TITLE = new Font("Century Schoolbook", Font.BOLD, 50);

    private static final Color COLOR_TEXT_YELLOW = new Color(190, 170, 115);

    public ObjectHandlerInPlayerMenu(ObjectHandlerInWorld objectHandlerInWorld, MenuImageLoader menuImageLoader, Camera camera) throws IOException {
        this.menuImageLoader = menuImageLoader;
        imageLoaderInMainMenu = new ImageLoaderInMainMenu();
        this.objectHandlerInWorld = objectHandlerInWorld;
        this.camera = camera;

        allMenus = new Stack<>();

        allMenus.push(getFirstMenu());
    }

    private Menu getFirstMenu() {
        List<String> allButtons = new LinkedList<>();
        allButtons.add("Pocket Shintos");
        allButtons.add("Items");
        allButtons.add("Equipment");
        allButtons.add("Save Game");
        allButtons.add("Quit");

        return new Menu("Main", allButtons, null);
    }

    /**
     * Returns all physical objects to show on screen
     */
    public List<PhysicalObject> get() {
        List<PhysicalObject> allObjects = new LinkedList<>();

        allObjects.addAll(objectHandlerInWorld.get());

        allObjects.addAll(getMenu(allMenus.peek()));

        return allObjects;
    }

    private List<PhysicalObject> getMenu(Menu currentMenu) {
        List<PhysicalObject> physicalObjects = new LinkedList<>();

        Position menuWithCamera = new Position(physicalMenu.getPosition().getX() + camera.getPosition().getX(), physicalMenu.getPosition().getY() + camera.getPosition().getY());

        physicalObjects.add(new Image(menuWithCamera, physicalMenu.getWidth(), physicalMenu.getHeight(),
                menuImageLoader.getButtonBackground(physicalMenu.getWidth(), physicalMenu.getHeight())));

        physicalObjects.add(new Image(menuWithCamera, physicalMenu.getWidth(), physicalMenu.getHeight(),
                menuImageLoader.getMenuSelector(physicalMenu.getWidth(), physicalMenu.getHeight())));

        // Position, width, height
        int x = menuWithCamera.getX();
        int y = menuWithCamera.getY();
        int width = physicalMenu.getWidth();
        int height = physicalMenu.getHeight()/currentMenu.getNumberOfButtons();

        // Button is selected
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

    /**
     * Called every frame
     */
    public void update() {

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

    boolean doMenuAction(String id) {
        if (id.equals("Return")) {
            if (allMenus.size() > 1) {
                allMenus.pop();
                return false;

            } else {
                return true;
            }

        }

        throw new IllegalArgumentException("The id " + id + " doesn't exist");
    }

}
