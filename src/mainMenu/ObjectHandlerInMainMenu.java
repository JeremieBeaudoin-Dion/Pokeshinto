package mainMenu;

import images.*;
import images.Image;
import pokeshinto.Game;
import pokeshinto.Menu;
import pokeshinto.MenuImageLoader;

import java.awt.*;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * This object handler represent the Model of the game when the game
 * is in a Main Menu state. When get() is called, it returns the physical
 * objects to show on the frame.
 *
 * @author Jérémie Beaudoin-Dion
 */
public class ObjectHandlerInMainMenu {

    // The Menu
    private Stack<Menu> allMenus;

    // The instances of the MVC
    private ImageLoaderInMainMenu imageLoader;

    private MenuImageLoader menuImageLoader;

    // Fonts
    private static final Font FONT_BUTTON = new Font("Century Schoolbook", Font.PLAIN, 35);
    private static final Font FONT_TITLE = new Font("Century Schoolbook", Font.BOLD, 50);

    private static final Color COLOR_TEXT_YELLOW = new Color(190, 170, 115);

    /**
     * Basic constructor for the Model component of the World
     *
     * @throws IOException if an image is missing
     */
    public ObjectHandlerInMainMenu(MenuImageLoader menuImageLoader) throws IOException {

        // Handles images objects
        imageLoader = new ImageLoaderInMainMenu();
        this.menuImageLoader = menuImageLoader;

        allMenus = new Stack<>();
        allMenus.push(getFirstMenu());
    }

    private Menu getFirstMenu() {
        List<String> allButtons = new LinkedList<>();
        allButtons.add("New Game");
        allButtons.add("Load Game");
        allButtons.add("Options");
        allButtons.add("Credits");
        allButtons.add("Quit");

        return new Menu("Main", allButtons, null);
    }

    /**
     * Returns all physical objects to show in the games
     */
    public List<PhysicalObject> get() {

        List<PhysicalObject> allObjects = new LinkedList<>();

        allObjects.add(new Image(new Position(0,0), Game.WINDOW_WIDTH, Game.WINDOW_HEIGHT, imageLoader.getBackground()));

        allObjects.add(new Text(new Position(250, 280), "Poket Shinto", FONT_TITLE, "Center", Color.BLACK));
        allObjects.add(new Text(new Position(250, 330), "A Game By Dice Patrol", FONT_BUTTON, "Center", Color.BLACK));

        allObjects.addAll(getMenu());

        return allObjects;
    }

    /**
     * Returns necessary objects to represent the current menu
     */
    private List<PhysicalObject> getMenu() {
        List<PhysicalObject> physicalObjects = new LinkedList<>();

        Menu currentMenu = allMenus.peek();


        // When there is an allert, there are no buttons
        if (currentMenu.getAllert() != null) {

            physicalObjects.addAll(getAllert(currentMenu));

        } else {

            physicalObjects.addAll(getMenuButtons(currentMenu));

        }

        return physicalObjects;
    }

    private List<PhysicalObject> getAllert(Menu currentMenu){
        throw new UnsupportedOperationException("Not supported yet");
    }

    private List<PhysicalObject> getMenuButtons(Menu currentMenu) {
        List<PhysicalObject> physicalObjects = new LinkedList<>();

        // Button is selected
        int selectedID = currentMenu.getCurrentSelectionInt();

        // Position, width, height
        int x = 520;
        int y = 44;
        int width = 328;
        int height = 104;

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
     * @return the current menu
     */
    Menu getCurrentMenu() {
        if (!allMenus.isEmpty()) {
            return allMenus.peek();
        }
        return null;
    }

}
