package mainMenu;

import combat.ActionDescriptor;
import images.*;
import images.Image;
import pokeshinto.Game;
import pokeshinto.Menu;
import pokeshinto.MenuImageLoader;
import pokeshinto.Player;

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
    private static final Font FONT_BUTTON_SMALLER = new Font("Century Schoolbook", Font.PLAIN, 25);
    private static final Font FONT_BUTTON = new Font("Century Schoolbook", Font.PLAIN, 35);
    private static final Font FONT_TITLE = new Font("Century Schoolbook", Font.BOLD, 50);

    private static final Color COLOR_TEXT_YELLOW = new Color(190, 170, 115);

    // Positions
    private static final PhysicalObject mainMenuButtons = new PhysicalObject(new Position(520, 45), 325, 520);
    private static final PhysicalObject optionMenuButtons = new PhysicalObject(new Position(50, 135), 300, 400);

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

    private Menu getOptionMenu() {
        List<String> allButtons = new LinkedList<>();
        allButtons.add("Description Level");
        allButtons.add("Approximate Damage");
        allButtons.add("Return");

        return new Menu("Options", allButtons, null);
    }

    private Menu getCreditMenu() {
        List<String> allButtons = new LinkedList<>();
        allButtons.add("Return");

        return new Menu("Credits", allButtons, null);
    }

    /**
     * Returns all physical objects to show in the games
     */
    public List<PhysicalObject> get() {

        List<PhysicalObject> allObjects = new LinkedList<>();

        allObjects.add(new Image(new Position(0,0), Game.WINDOW_WIDTH, Game.WINDOW_HEIGHT, imageLoader.getBackground()));

        if (getCurrentMenu().getID().equals("Main")) {
            allObjects.addAll(getMainMenuObjects());

        } else if (getCurrentMenu().getID().equals("Options")) {
            allObjects.addAll(getOptionMenuObjects());

        } else if (getCurrentMenu().getID().equals("Credits")) {
            allObjects.addAll(getCreditsMenuObjects());
        }

        return allObjects;
    }

    /**
     * Main menu
     */
    private List<PhysicalObject> getMainMenuObjects() {
        List<PhysicalObject> physicalObjects = new LinkedList<>();

        physicalObjects.add(new Text(new Position(250, 280), "Pocket Shinto", FONT_TITLE, "Center", Color.BLACK));
        physicalObjects.add(new Text(new Position(250, 330), "A Game By Dice Patrol", FONT_BUTTON, "Center", Color.BLACK));

        physicalObjects.add(new Image(mainMenuButtons.getPosition(), mainMenuButtons.getWidth(), mainMenuButtons.getHeight(), imageLoader.getButtonBackground(mainMenuButtons.getWidth(), mainMenuButtons.getHeight())));

        physicalObjects.add(new Image(mainMenuButtons.getPosition(), mainMenuButtons.getWidth(), mainMenuButtons.getHeight(), menuImageLoader.getMenuSelector(mainMenuButtons.getWidth(), mainMenuButtons.getHeight())));

        physicalObjects.addAll(getMainMenuButtonsObjects(allMenus.peek()));

        return physicalObjects;
    }

    private List<PhysicalObject> getMainMenuButtonsObjects(Menu currentMenu) {
        List<PhysicalObject> physicalObjects = new LinkedList<>();

        // Button is selected
        int selectedID = currentMenu.getCurrentSelectionInt();

        // Position, width, height
        int x = mainMenuButtons.getX();
        int y = mainMenuButtons.getY();
        int width = mainMenuButtons.getWidth();
        int height = mainMenuButtons.getHeight()/currentMenu.getNumberOfButtons();

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
     * Option menu
     */
    private List<PhysicalObject> getOptionMenuObjects() {
        List<PhysicalObject> physicalObjects = new LinkedList<>();

        physicalObjects.add(new Text(new Position(Game.WINDOW_WIDTH/2, 100), "Options", FONT_TITLE, "Center", Color.BLACK));

        physicalObjects.add(new Image(optionMenuButtons.getPosition(), optionMenuButtons.getWidth(), optionMenuButtons.getHeight(), imageLoader.getButtonBackground(optionMenuButtons.getWidth(), optionMenuButtons.getHeight())));

        physicalObjects.add(new Image(optionMenuButtons.getPosition(), optionMenuButtons.getWidth(), optionMenuButtons.getHeight(), menuImageLoader.getMenuSelector(optionMenuButtons.getWidth(), optionMenuButtons.getHeight())));

        physicalObjects.addAll(getOptionMenuButtonObjects((allMenus.peek())));

        return physicalObjects;
    }

    private List<PhysicalObject> getOptionMenuButtonObjects(Menu currentMenu) {
        List<PhysicalObject> physicalObjects = new LinkedList<>();

        // Button is selected
        int selectedID = currentMenu.getCurrentSelectionInt();

        // Position, width, height
        int x = optionMenuButtons.getX();
        int y = optionMenuButtons.getY();
        int width = optionMenuButtons.getWidth();
        int height = optionMenuButtons.getHeight()/currentMenu.getNumberOfButtons();

        for (int i=0; i<currentMenu.getNumberOfButtons(); i++) {
            // Sets the buttons to selected
            if (i == selectedID) {
                physicalObjects.add(new Image(new Position(x, y), width, height, menuImageLoader.getMenuSelector(width, height)));
            }

            String buttonText = currentMenu.getButton(i);

            if (buttonText.length() < 10) {
                physicalObjects.add(new Text(new Position(x + width/2, y + FONT_BUTTON.getSize() * 2), buttonText, FONT_BUTTON,
                        "Center", COLOR_TEXT_YELLOW));
            } else {
                String[] splittedButtons = buttonText.split(" ");

                physicalObjects.add(new Text(new Position(x + width/2, (int) (y + FONT_BUTTON.getSize() * 1.5)), splittedButtons[0], FONT_BUTTON,
                        "Center", COLOR_TEXT_YELLOW));
                physicalObjects.add(new Text(new Position(x + width/2, (int) (y + FONT_BUTTON.getSize() * 2.5)), splittedButtons[1], FONT_BUTTON,
                        "Center", COLOR_TEXT_YELLOW));
            }

            physicalObjects.addAll(getOptionMenuButtonDescription(buttonText, x, y, width, height, i==selectedID));

            y += height;
        }

        return physicalObjects;
    }

    private List<PhysicalObject> getOptionMenuButtonDescription(String id, int x, int y, int width, int height, boolean isSelected) {
        List<PhysicalObject> physicalObjects = new LinkedList<>();

        x = (int) (x + width * 1.2);
        y = y + height/2 - imageLoader.getSliderSize().getY()/2;

        if (!id.equals("Return")) {
            if (isSelected) {
                physicalObjects.add(new Image(new Position(x, y), 290, 68, imageLoader.getSliderBright()));
            } else {
                physicalObjects.add(new Image(new Position(x, y), 290, 68, imageLoader.getSliderDark()));
            }
        }

        x += imageLoader.getSliderSize().getX()/2;
        y += imageLoader.getSliderSize().getY()/1.5;

        switch(id) {
            case "Description Level":
                if (Player.descriptionComplexity == ActionDescriptor.DESCRIPTION_COMPLEXITY_NORMAL) {
                    physicalObjects.add(new Text(new Position(x, y), "Normal", FONT_BUTTON, "Center", COLOR_TEXT_YELLOW));

                } else if (Player.descriptionComplexity == ActionDescriptor.DESCRIPTION_COMPLEXITY_LESS) {
                    physicalObjects.add(new Text(new Position(x, y), "Less", FONT_BUTTON, "Center", COLOR_TEXT_YELLOW));

                } else {
                    physicalObjects.add(new Text(new Position(x, y), "More", FONT_BUTTON, "Center", COLOR_TEXT_YELLOW));
                }

                break;

            case "Approximate Damage":
                if (Player.approximate) {
                    physicalObjects.add(new Text(new Position(x, y), "Yes", FONT_BUTTON, "Center", COLOR_TEXT_YELLOW));

                } else {
                    physicalObjects.add(new Text(new Position(x, y), "No", FONT_BUTTON, "Center", COLOR_TEXT_YELLOW));
                }
        }

        return physicalObjects;
    }

    /**
     * Credits
     */
    private List<PhysicalObject> getCreditsMenuObjects() {
        List<PhysicalObject> physicalObjects = new LinkedList<>();

        physicalObjects.add(new Text(new Position(Game.WINDOW_WIDTH/2, 100), "Credits", FONT_TITLE, "Center", Color.BLACK));

        physicalObjects.add(new Text(new Position(Game.WINDOW_WIDTH/2, 200), "Programming", FONT_BUTTON, "Center", Color.BLACK));

        physicalObjects.add(new Text(new Position(Game.WINDOW_WIDTH/2, 230), "Jérémie Beaudoin-Dion", FONT_BUTTON_SMALLER, "Center", Color.BLACK));

        physicalObjects.add(new Text(new Position(Game.WINDOW_WIDTH/2, 300), "Game Design", FONT_BUTTON, "Center", Color.BLACK));

        physicalObjects.add(new Text(new Position(Game.WINDOW_WIDTH/2, 330), "François Baldo", FONT_BUTTON_SMALLER, "Center", Color.BLACK));

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

    void doMenuAction(String id) {
        if (id.equals("Return")) {
            if (allMenus.size() > 1) {
                allMenus.pop();
            }

        } else if (id.equals("Options")) {
            allMenus.push(getOptionMenu());

        } else if (id.equals("Credits")) {
            allMenus.push(getCreditMenu());

        } else {
            throw new IllegalArgumentException("The id " + id + " doesn't exist");
        }
    }

}
