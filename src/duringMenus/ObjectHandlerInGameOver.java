package duringMenus;

import images.Image;
import images.PhysicalObject;
import images.Position;
import images.Text;
import pokeshinto.Game;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Returns the objects to show in the frame when the player has
 * lost the game.
 *
 * @author Jérémie Beaudoin-Dion
 */
public class ObjectHandlerInGameOver {

    private Menu currentMenu;

    private MenuImageLoader imageLoader;

    private static final PhysicalObject mainButtons = new PhysicalObject(new Position(300, 300), 350, 200);

    private static final Color COLOR_TEXT_YELLOW = new Color(190, 170, 115);

    private static final Font FONT_BUTTON = new Font("Century Schoolbook", Font.PLAIN, 35);
    private static final Font FONT_TITLE = new Font("Century Schoolbook", Font.BOLD, 80);

    /**
     * Constructor
     */
    public ObjectHandlerInGameOver(MenuImageLoader imageLoader) {
        this.imageLoader = imageLoader;

        setMenu();
    }

    private void setMenu() {
        List<String> buttons = new LinkedList<>();

        buttons.add("Go back to last save");
        buttons.add("Quit");

        currentMenu = new Menu("Main", buttons, null);
    }

    /**
     * Returns all physical objects to show in the frame
     */
    public List<PhysicalObject> get() {
        List<PhysicalObject> allObjects = new LinkedList<>();

        allObjects.add(new Image(new Position(0, 0), Game.WINDOW_WIDTH, Game.WINDOW_HEIGHT, imageLoader.getBackground()));

        allObjects.add(new Text(new Position(Game.WINDOW_WIDTH/2 - FONT_TITLE.getSize()/4, 175), "Game Over", FONT_TITLE, "Center", Color.BLACK));

        allObjects.add(new Image(mainButtons.getPosition(), mainButtons.getWidth(), mainButtons.getHeight(), imageLoader.getButtonBackground(mainButtons.getWidth(), mainButtons.getHeight())));

        allObjects.add(new Image(mainButtons.getPosition(), mainButtons.getWidth(), mainButtons.getHeight(), imageLoader.getMenuSelector(mainButtons.getWidth(), mainButtons.getHeight())));

        allObjects.addAll(getMenuButtons(currentMenu));

        return allObjects;
    }

    private List<PhysicalObject> getMenuButtons(Menu currentMenu) {
        List<PhysicalObject> physicalObjects = new LinkedList<>();

        // Button is selected
        int selectedID = currentMenu.getCurrentSelectionInt();

        // Position, width, height
        int x = mainButtons.getX();
        int y = mainButtons.getY();
        int width = mainButtons.getWidth();
        int height = mainButtons.getHeight()/currentMenu.getNumberOfButtons();

        for (int i=0; i<currentMenu.getNumberOfButtons(); i++) {
            // Sets the buttons to selected
            if (i == selectedID) {
                physicalObjects.add(new Image(new Position(x, y), width, height, imageLoader.getMenuSelector(width, height)));
            }

            String buttonText = currentMenu.getButton(i);

            physicalObjects.add(new Text(new Position(x + width/2, y + FONT_BUTTON.getSize() * 2), buttonText, FONT_BUTTON,
                    "Center", COLOR_TEXT_YELLOW));

            y += height;
        }

        return physicalObjects;
    }

    /**
     * Getter
     */
    public Menu getCurrentMenu() {
        return currentMenu;
    }
}
