package duringMenus;

import combat.ActionDescriptor;
import images.*;
import images.Image;
import pokeshinto.*;
import world.ImageLoaderInWorld;
import world.WorldMapCreator;

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

    private PlayerInformation gamePlayer;
    private WorldMapCreator worldMap;
    private String savedFileName;

    private int[] playerImage;

    // Fonts
    private static final Font FONT_BUTTON_SMALLER = new Font("Century Schoolbook", Font.PLAIN, 25);
    private static final Font FONT_BUTTON = new Font("Century Schoolbook", Font.PLAIN, 35);
    private static final Font FONT_TITLE = new Font("Century Schoolbook", Font.BOLD, 50);

    private static final Color COLOR_TEXT_YELLOW = new Color(190, 170, 115);

    // Positions
    private static final PhysicalObject mainMenuButtons = new PhysicalObject(new Position(520, 45), 325, 520);
    private static final PhysicalObject optionMenuButtons = new PhysicalObject(new Position(50, 135), 300, 400);

    private static final PhysicalObject playMenuButtons = new PhysicalObject(new Position(315, 150), 300, 380);

    private static final PhysicalObject newGameMenuButtons = new PhysicalObject(new Position(250, 200), 400, 300);

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

        playerImage = new int[4];

    }

    private Menu getFirstMenu() {
        List<String> allButtons = new LinkedList<>();
        allButtons.add("Play");
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

    private Menu getPlayMenu() {
        List<String> allButtons = new LinkedList<>();
        allButtons.add("Game 1");
        allButtons.add("Game 2");
        allButtons.add("Game 3");
        allButtons.add("Return");

        return new Menu("Play", allButtons, null);
    }

    private Menu getNewGameMenu() {
        List<String> allButtons = new LinkedList<>();
        allButtons.add("Player Image");
        allButtons.add("Start Game");
        allButtons.add("Return");

        return new Menu("New Game", allButtons, null);
    }

    /**
     * Returns all physical objects to show in the games
     */
    public List<PhysicalObject> get() {

        List<PhysicalObject> allObjects = new LinkedList<>();

        allObjects.add(new Image(new Position(0,0), Game.WINDOW_WIDTH, Game.WINDOW_HEIGHT, menuImageLoader.getBackground()));

        if (getCurrentMenu().getID().equals("Main")) {
            allObjects.addAll(getMainMenuObjects());

        } else if (getCurrentMenu().getID().equals("Options")) {
            allObjects.addAll(getOptionMenuObjects());

        } else if (getCurrentMenu().getID().equals("Credits")) {
            allObjects.addAll(getCreditsMenuObjects());

        } else if (getCurrentMenu().getID().equals("Play")) {
            allObjects.addAll(getPlayMenuObjects());

        } else if (getCurrentMenu().getID().equals("New Game")) {
            allObjects.addAll(getNewGameMenuObjects());
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

        physicalObjects.add(new Image(mainMenuButtons.getPosition(), mainMenuButtons.getWidth(), mainMenuButtons.getHeight(), menuImageLoader.getButtonBackground(mainMenuButtons.getWidth(), mainMenuButtons.getHeight())));

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

        physicalObjects.add(new Image(optionMenuButtons.getPosition(), optionMenuButtons.getWidth(), optionMenuButtons.getHeight(), menuImageLoader.getButtonBackground(optionMenuButtons.getWidth(), optionMenuButtons.getHeight())));

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

        physicalObjects.add(new Text(new Position(Game.WINDOW_WIDTH/2, 200), "Programming and Music", FONT_BUTTON, "Center", Color.BLACK));

        physicalObjects.add(new Text(new Position(Game.WINDOW_WIDTH/2, 230), "Jérémie Beaudoin-Dion", FONT_BUTTON_SMALLER, "Center", Color.BLACK));

        physicalObjects.add(new Text(new Position(Game.WINDOW_WIDTH/2, 300), "Game Design", FONT_BUTTON, "Center", Color.BLACK));

        physicalObjects.add(new Text(new Position(Game.WINDOW_WIDTH/2, 330), "François Baldo", FONT_BUTTON_SMALLER, "Center", Color.BLACK));

        return physicalObjects;
    }

    /**
     * Play
     */
    private List<PhysicalObject> getPlayMenuObjects() {
        List<PhysicalObject> physicalObjects = new LinkedList<>();

        physicalObjects.add(new Text(new Position(Game.WINDOW_WIDTH/2, 100), "Chose a file", FONT_TITLE, "Center", Color.BLACK));

        physicalObjects.add(new Image(playMenuButtons.getPosition(), playMenuButtons.getWidth(), playMenuButtons.getHeight(), menuImageLoader.getButtonBackground(playMenuButtons.getWidth(), playMenuButtons.getHeight())));

        physicalObjects.add(new Image(playMenuButtons.getPosition(), playMenuButtons.getWidth(), playMenuButtons.getHeight(), menuImageLoader.getMenuSelector(playMenuButtons.getWidth(), playMenuButtons.getHeight())));

        int x = playMenuButtons.getX();
        int y = playMenuButtons.getY();
        int width = playMenuButtons.getWidth();
        int height = playMenuButtons.getHeight()/getCurrentMenu().getNumberOfButtons();

        for (int i=0; i<getCurrentMenu().getNumberOfButtons(); i++) {
            physicalObjects.add(new Text(new Position(x + width/2, y + FONT_BUTTON.getSize() * 2), getCurrentMenu().getButton(i), FONT_BUTTON,
                    "Center", COLOR_TEXT_YELLOW));

            if (getCurrentMenu().getCurrentSelectionInt() == i) {
                physicalObjects.add(new Image(new Position(x, y), width, height, menuImageLoader.getMenuSelector(width, height)));
            }

            if (i < 3 && playerImage[i+1] > -1) {
                physicalObjects.add(new Image(new Position(x + ImageLoaderInWorld.PLAYER_WIDTH, y + ImageLoaderInWorld.PLAYER_HEIGHT/2),
                        ImageLoaderInWorld.PLAYER_WIDTH, ImageLoaderInWorld.PLAYER_HEIGHT, menuImageLoader.getPlayerImage(playerImage[i+1])));
            }

            y += height;
        }

        return physicalObjects;
    }

    /**
     * Create new Game File
     */
    private List<PhysicalObject> getNewGameMenuObjects() {
        List<PhysicalObject> physicalObjects = new LinkedList<>();

        physicalObjects.add(new Text(new Position(Game.WINDOW_WIDTH/2, 100), "New Game", FONT_TITLE, "Center", Color.BLACK));

        physicalObjects.add(new Image(newGameMenuButtons.getPosition(), newGameMenuButtons.getWidth(), newGameMenuButtons.getHeight(), menuImageLoader.getButtonBackground(newGameMenuButtons.getWidth(), newGameMenuButtons.getHeight())));

        physicalObjects.add(new Image(newGameMenuButtons.getPosition(), newGameMenuButtons.getWidth(), newGameMenuButtons.getHeight(), menuImageLoader.getMenuSelector(newGameMenuButtons.getWidth(), newGameMenuButtons.getHeight())));

        int x = newGameMenuButtons.getX();
        int y = newGameMenuButtons.getY();
        int width = newGameMenuButtons.getWidth();
        int height = newGameMenuButtons.getHeight()/getCurrentMenu().getNumberOfButtons();

        for (int i=0; i<getCurrentMenu().getNumberOfButtons(); i++) {

            if (i == 0) {
                physicalObjects.add(new Image(new Position(x + newGameMenuButtons.getX() * 2, y), width, height, menuImageLoader.getPlayerImage(Player.playerCharNumber)));
            }

            physicalObjects.add(new Text(new Position(x + width/2, y + FONT_BUTTON.getSize() * 2), getCurrentMenu().getButton(i), FONT_BUTTON,
                    "Center", COLOR_TEXT_YELLOW));

            if (getCurrentMenu().getCurrentSelectionInt() == i) {
                physicalObjects.add(new Image(new Position(x, y), width, height, menuImageLoader.getMenuSelector(width, height)));
            }

            y += height;
        }

        return physicalObjects;
    }

    void doMenuAction(String id) {
        if (id.equals("Return")) {
            if (allMenus.size() > 1) {
                allMenus.pop();
            }

        } else if (id.equals("Play")) {
            allMenus.push(getPlayMenu());

        } else if (id.equals("Options")) {
            allMenus.push(getOptionMenu());

        } else if (id.equals("Credits")) {
            allMenus.push(getCreditMenu());

        } else if (id.equals("New Game")) {
            allMenus.push(getNewGameMenu());

        } else {
            throw new IllegalArgumentException("The id " + id + " doesn't exist");
        }
    }

    /**
     * Setters
     */
    void setGamePlayer(PlayerInformation player) {
        gamePlayer = player;
    }

    void setWorldMap(WorldMapCreator world) {
        worldMap = world;
    }

    void setSavedFileName(String name) {
        savedFileName = name;
    }

    void setPlayerImage(int GameID, int playerImage) {
        this.playerImage[GameID] = playerImage;
    }

    /**
     * Getters
     */
    Menu getCurrentMenu() {
        if (!allMenus.isEmpty()) {
            return allMenus.peek();
        }
        return null;
    }

    public PlayerInformation getGamePlayer() {
        return gamePlayer;
    }

    public WorldMapCreator getWorldMap() {
        return worldMap;
    }

    public String getSavedFileName() {
        return savedFileName;
    }

}
