package pokeshinto;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageLoader {

	// Combat
	private BufferedImage combatBackground;
	
	private BufferedImage healthBarBackground;
	private int healthBarBackgroundWidth;
	private int healthBarBackgroundHeight;
	private BufferedImage healthBarForeground;
	
	/**
	 * Constructor
	 */
	public ImageLoader() {
		
		healthBarBackgroundWidth = 256;
		healthBarBackgroundHeight = 32;
		
		loadAllImages();
		
	}
	
	/**
	 * Loads all of the images from the image file
	 * Has a try/catch if the image is missing
	 */
	private void loadAllImages() {
		// Loads all images
		//TODO: throw?
		try {
			
			// Combat images
			combatBackground = ImageIO.read(new File("BackgroundBattle.png"));
			healthBarBackground = ImageIO.read(new File("HealthBarBackground.png"));
			healthBarForeground = ImageIO.read(new File("HealthBarForeground.png"));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Getter
	 * 
	 * @return the combatBackground image
	 */
	public BufferedImage getCombatBackgound() {
		return combatBackground;
	}
	
	/**
	 * Getter
	 * 
	 * @param percentage: how much of the image's width you need to show in percentage
	 * @return
	 */
	public BufferedImage getHealthBarBackground(double percentage) {
		if (percentage <= 0) {
			return null;
		}
		
		int width = (int) (healthBarBackgroundWidth * percentage);
		
		return healthBarBackground.getSubimage(0, 0, width, healthBarBackgroundHeight);
	}
	
	public int getHealthBarWidth() {
		return healthBarBackgroundWidth;
	}
	
	/**
	 * Getter
	 * 
	 * @return the foreground of the Health Bar
	 */
	public BufferedImage getHealthBarForeground() {
		return healthBarForeground;
	}

	
	
	
	
}
