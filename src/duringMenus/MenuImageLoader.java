package duringMenus;

import images.PhysicalObject;
import images.Position;
import images.LoaderOfImages;
import pokeshinto.Player;
import world.ImageLoaderInWorld;
import world.worldObjects.Villager;
import world.worldObjects.Villager_Shop;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * The menus are very similar from one GameState to another. So this
 * image loader returns the necessary images to create a Menu
 *
 * @author Jérémie Beaudoin-Dion
 */
public class MenuImageLoader extends LoaderOfImages {

    private BufferedImage menuSelector;
    private PhysicalObject[] menuSelectorCorner;
    private PhysicalObject[] menuSelectorLines;

    private Position menuSelectorLastDimensions;
    private BufferedImage menuSelectorCropped;

    private BufferedImage background;
    private BufferedImage buttonBackground;

    private BufferedImage playerTile;

    private BufferedImage characterTile;

    /**
     * Constructor
     * @throws IOException : if the Image is missing from the file
     */
    public MenuImageLoader() throws IOException {

        loadMenuSelector();

        loadAllImages();

    }

    /**
     * Loads all of the images from the image file
     * Has a try/catch if the image is missing
     *
     * @throws IOException : if the Image is missing from the file
     */
    private void loadAllImages() throws IOException {
        menuSelector = toCompatibleImage(ImageIO.read(new File("Resources/Selector.png")));

        background = toCompatibleImage(ImageIO.read(new File("Resources/PaperBackground.png")));
        buttonBackground = toCompatibleImage(ImageIO.read(new File("Resources/WoodBackground.png")));

        playerTile = toCompatibleImage(ImageIO.read(new File("Resources/Players.png")));

        characterTile = toCompatibleImage(ImageIO.read(new File("Resources/Characters.png")));
    }

    /**
     * The menu selector is a complicated image to create. So it is handled by multiple variables
     */
    private void loadMenuSelector() {
        menuSelectorCorner = new PhysicalObject[4];
        menuSelectorCorner[0] = new PhysicalObject(new Position(0, 0), 15, 15);
        menuSelectorCorner[1] = new PhysicalObject(new Position(885, 0), 15, 15);
        menuSelectorCorner[2] = new PhysicalObject(new Position(885, 585), 15, 15);
        menuSelectorCorner[3] = new PhysicalObject(new Position(0, 585), 15, 15);

        menuSelectorLines = new PhysicalObject[4];
        menuSelectorLines[0] = new PhysicalObject(new Position(15, 0), -1, 15);
        menuSelectorLines[1] = new PhysicalObject(new Position(885, 15), 15, -1);
        menuSelectorLines[2] = new PhysicalObject(new Position(15, 585), -1, 15);
        menuSelectorLines[3] = new PhysicalObject(new Position(0, 15), 15, -1);

        menuSelectorLastDimensions = new Position(0, 0);
        menuSelectorCropped = menuSelector;
    }

    /**
     * Getter for the menu selector
     * Creates the image depending on the width and height
     *
     * @param width: the number of pixel representing the desired width
     * @param height: the number of pixel representing the desired height
     * @return: an image that represents the selector of the Menu
     */
    public BufferedImage getMenuSelector(int width, int height) {
        // Ensures that the selector is not changed every frame
        if (this.menuSelectorLastDimensions.getX() != width || this.menuSelectorLastDimensions.getY() != height){
            this.menuSelectorCropped = cropMenuSelector(width, height);
            this.menuSelectorLastDimensions = new Position(width, height);
        }

        return menuSelectorCropped;
    }

    /**
     * Create a new sub image from the menu selector
     *
     * @param width: the number of pixel representing the desired width
     * @param height: the number of pixel representing the desired height
     * @return: an image that represents the selector of the Menu
     */
    private BufferedImage cropMenuSelector(int width, int height) {
        BufferedImage image = new BufferedImage(width, height, menuSelector.getType());

        width--;
        height--;

        Graphics2D bbg = (Graphics2D) image.getGraphics();

        PhysicalObject[] cornerPos = new PhysicalObject[4];
        cornerPos[0] = new PhysicalObject(new Position(0, 0), -1, -1);
        cornerPos[1] = new PhysicalObject(new Position(width - menuSelectorCorner[0].getWidth(), 0), -1, -1);
        cornerPos[2] = new PhysicalObject(new Position(width - menuSelectorCorner[0].getWidth(), height - menuSelectorCorner[0].getHeight()), -1, -1);
        cornerPos[3] = new PhysicalObject(new Position(0, height - menuSelectorCorner[0].getHeight()), -1, -1);

        PhysicalObject[] linePos = new PhysicalObject[4];
        linePos[0] = new PhysicalObject(new Position(menuSelectorCorner[0].getWidth(), 0), -1, -1);
        linePos[1] = new PhysicalObject(new Position(width - menuSelectorCorner[0].getWidth(), menuSelectorCorner[0].getHeight()), -1, -1);
        linePos[2] = new PhysicalObject(new Position(menuSelectorCorner[0].getWidth(), height - menuSelectorCorner[0].getHeight()), -1, -1);
        linePos[3] = new PhysicalObject(new Position(0, menuSelectorCorner[0].getHeight()), -1, -1);

        // Draws the images on the graphic
        for (int i=0; i<menuSelectorCorner.length; i++) {
            // Draws the corners
            bbg.drawImage(menuSelector.getSubimage(menuSelectorCorner[i].getX(), menuSelectorCorner[i].getY(),
                    menuSelectorCorner[i].getWidth(), menuSelectorCorner[i].getHeight()), cornerPos[i].getX(), cornerPos[i].getY(), null);
            // Draws the line
            if (menuSelectorLines[i].getWidth() < 0) {
                bbg.drawImage(menuSelector.getSubimage(menuSelectorLines[i].getX(), menuSelectorLines[i].getY(),
                        width - 2*menuSelectorCorner[i].getWidth(), menuSelectorLines[i].getHeight()), linePos[i].getX(), linePos[i].getY(), null);
            } else {
                bbg.drawImage(menuSelector.getSubimage(menuSelectorLines[i].getX(), menuSelectorLines[i].getY(),
                        menuSelectorLines[i].getWidth(), height - 2*menuSelectorCorner[i].getHeight()), linePos[i].getX(), linePos[i].getY(), null);
            }
        }

        return image;
    }

    BufferedImage getBackground() {
        return background;
    }

    BufferedImage getBackground(int width, int height) {
        return background.getSubimage(0, 0, width, height);
    }

    BufferedImage getButtonBackground(int width, int height) {
        return buttonBackground.getSubimage(0, 0, width, height);
    }

    BufferedImage getPlayerImage(int playerTileID) {
        return playerTile.getSubimage(ImageLoaderInWorld.PLAYER_WIDTH, playerTileID * ImageLoaderInWorld.PLAYER_HEIGHT,
                ImageLoaderInWorld.PLAYER_WIDTH, ImageLoaderInWorld.PLAYER_HEIGHT);
    }

    BufferedImage getCharacterImage(Villager villager) {

        return characterTile.getSubimage(ImageLoaderInWorld.VILLAGER_WIDTH,
                villager.getCharacterId() * ImageLoaderInWorld.VILLAGER_HEIGHT,
                ImageLoaderInWorld.VILLAGER_WIDTH, ImageLoaderInWorld.VILLAGER_HEIGHT);
    }

}
