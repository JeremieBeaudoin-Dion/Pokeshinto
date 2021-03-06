package combat;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import images.LoaderOfImages;

/**
 * Loads all necessary images for the image handler in combat
 * 
 * @author Jeremie Beaudoin-Dion
 *
 */
class ImageLoaderInCombat extends LoaderOfImages {

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

	static int HEALTH_BAR_BACKGROUND_INSET = 30;
	static int XP_BAR_BACKGROUND_INSET = 45;
	static int HEALTH_BAR_HALF_WIDTH = 256/2;
    static int HEALTH_BAR_HALF_HEIGHT = 21;

	
	/**
	 * Constructor
	 * @throws IOException : if the Image is missing from the file
	 */
	ImageLoaderInCombat() throws IOException {

        healthBarForegroundWidth = 256;
		healthBarBackgroundWidth = 196;
		healthBarBackgroundHeight = 32;

		xpBarForegroundWidth = 400;
		xpBarBackgroundWidth = 308;
        xpBarBackgroundHeight = 64;
		
		loadAllImages();
		
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
		combatBackground = toCompatibleImage(ImageIO.read(new File("Resources/BackgroundBattle.png")));
		healthBarBackground = toCompatibleImage(ImageIO.read(new File("Resources/HealthBarBackground.png")));
		healthBarBackground_Player = toCompatibleImage(ImageIO.read(new File("Resources/HealthBarBackground_Player.png")));
		healthBarForeground = toCompatibleImage(ImageIO.read(new File("Resources/HealthBarForeground.png")));
        xpBarForeground = toCompatibleImage(ImageIO.read(new File("Resources/XpBarForeground.png")));
        xpBarBackground = toCompatibleImage(ImageIO.read(new File("Resources/XpBarBackground.png")));

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
