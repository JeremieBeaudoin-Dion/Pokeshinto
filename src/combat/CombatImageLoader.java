package combat;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import images.PhysicalObject;
import images.Position;

/**
 * Loads all necessary images for the image handler in combat
 * 
 * @author Jeremie Beaudoin-Dion
 *
 */
class CombatImageLoader {

	// Images attributes
	private BufferedImage combatBackground;
	
	private BufferedImage healthBarBackground;
	private BufferedImage healthBarBackground_Player;
	private int healthBarForegroundWidth;
	private int healthBarBackgroundWidth;
	private int healthBarBackgroundHeight;
	private BufferedImage healthBarForeground;

	private int xpBarForegroundWidth;
	private int xpBarBackgroundWidth;
    private int xpBarBackgroundHeight;
	private BufferedImage xpBarForeground;
    private BufferedImage xpBarBackground;

	public static int HEALTH_BAR_BACKGROUND_INSET = 30;
	public static int XP_BAR_BACKGROUND_INSET = 45;
	public static int HEALTH_BAR_HALF_WIDTH = 256/2;
    public static int HEALTH_BAR_HALF_HEIGHT = 21;
	
	private BufferedImage menuSelector;
	private PhysicalObject[] menuSelectorCorner;
	private PhysicalObject[] menuSelectorLines;
	
	private Position menuSelectorLast;
	private BufferedImage menuSelectorCropped;
	
	/**
	 * Constructor
	 * @throws IOException : if the Image is missing from the file
	 */
	CombatImageLoader() throws IOException {

        healthBarForegroundWidth = 256;
		healthBarBackgroundWidth = 196;
		healthBarBackgroundHeight = 32;

		xpBarForegroundWidth = 400;
		xpBarBackgroundWidth = 308;
        xpBarBackgroundHeight = 64;
		
		loadMenuSelector();
		
		loadAllImages();
		
	}
	
	/**
	 * The menu selector is a complicated image to create. So it is handled by multiple variables
	 */
	private void loadMenuSelector() {
		menuSelectorCorner = new PhysicalObject[4];
		menuSelectorCorner[0] = new PhysicalObject(new Position(0, 0), 15, 15);
		menuSelectorCorner[1] = new PhysicalObject(new Position(885, 0), 15, 15);
		menuSelectorCorner[2] = new PhysicalObject(new Position(885, 236), 15, 15);
		menuSelectorCorner[3] = new PhysicalObject(new Position(0, 236), 15, 15);
		
		menuSelectorLines = new PhysicalObject[4];
		menuSelectorLines[0] = new PhysicalObject(new Position(15, 0), -1, 15);
		menuSelectorLines[1] = new PhysicalObject(new Position(885, 15), 15, -1);
		menuSelectorLines[2] = new PhysicalObject(new Position(15, 236), -1, 15);
		menuSelectorLines[3] = new PhysicalObject(new Position(0, 15), 15, -1);
		
		menuSelectorLast = new Position(0, 0);
		menuSelectorCropped = menuSelector;
	}
	
	/**
	 * Loads all of the images from the image file
	 * Has a try/catch if the image is missing
	 *
	 * @throws IOException : if the Image is missing from the file
	 */
	private void loadAllImages() throws IOException {
		// Loads all images
		// Combat images
		combatBackground = ImageIO.read(new File("BackgroundBattle.png"));
		healthBarBackground = ImageIO.read(new File("HealthBarBackground.png"));
		healthBarBackground_Player = ImageIO.read(new File("HealthBarBackground_Player.png"));
		healthBarForeground = ImageIO.read(new File("HealthBarForeground.png"));
		menuSelector = ImageIO.read(new File("Selector.png"));
        xpBarForeground = ImageIO.read(new File("XpBarForeground.png"));
        xpBarBackground = ImageIO.read(new File("XpBarBackground.png"));

	}
	
	/**
	 * Getter
	 * 
	 * @return the combatBackground image
	 */
	BufferedImage getCombatBackgound() {
		return combatBackground;
	}
	
	/**
	 * Getter
	 * 
	 * @param percentage: how much of the image's width you need to show in percentage
	 * @return the background of the health bar
	 */
	BufferedImage getHealthBarBackground(double percentage) {

		if (percentage <= 0) {
			return null;
		}
		
		int width = (int) (healthBarBackgroundWidth * percentage);
		
		return healthBarBackground.getSubimage(0, 0, width, healthBarBackgroundHeight);
	}

    BufferedImage getPlayerHealthBarBackground(double percentage) {

        if (percentage <= 0) {
            return null;
        }

        int width = (int) (healthBarBackgroundWidth * percentage);

        return healthBarBackground_Player.getSubimage(0, 0, width, healthBarBackgroundHeight);
    }

    BufferedImage getXpBarBackground(double percentage) {

        if (percentage <= 0) {
            return null;
        }

        int width = (int) (xpBarBackgroundWidth * percentage);

        return xpBarBackground.getSubimage(0, 0, width, xpBarBackgroundHeight);
    }

	/**
	 * Getter
	 *
	 * @return the foreground of the Health Bar
	 */
	BufferedImage getHealthBarForeground() {
		return healthBarForeground;
	}

    BufferedImage getXpBarForeground() {
        return xpBarForeground;
    }
	
	/**
	 * Getter for the menu selector
	 * Creates the image depending on the width and height
	 * 
	 * @param width: the number of pixel representing the desired width
	 * @param height: the number of pixel representing the desired height
	 * @return: an image that represents the selector of the Menu
	 */
	BufferedImage getMenuSelector(int width, int height) {
		// Ensures that the selector is not changed every frame
		if (this.menuSelectorLast.getX() != width || this.menuSelectorLast.getY() != height){
			this.menuSelectorCropped = cropMenuSelector(width, height);
			this.menuSelectorLast = new Position(width, height);
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

    /**
     * Getter
     *
     * @return: the width of the HealthBar
     */
    int getHealthBarBackgroundWidth() {
        return healthBarBackgroundWidth;
    }

    int getHealthBarForegroundWidth() {
        return healthBarForegroundWidth;
    }

    int getXpBarForegroundWidth() {
        return xpBarForegroundWidth;
    }

    int getXpBarBackgroundWidth() {
        return xpBarBackgroundWidth;
    }

    /**
     * Getter
     *
     * @return: the height of the HealthBar
     */
    int getHealthBarHeight() {
        return healthBarBackgroundHeight;
    }

    int getXpBarHeight() {
        return xpBarBackgroundHeight;
    }

}
