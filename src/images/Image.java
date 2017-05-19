package images;

import java.awt.image.BufferedImage;

/**
 * An image is a physical object which can be put on the JFrame
 * 
 * @author Jérémie Beaudoin-Dion
 *
 */
public class Image extends PhysicalObject {

    private BufferedImage image;
	
	/**
	 * Constructor
	 * 
	 * @param position: where the image is on screen
	 * @param width: the width of the image
	 * @param height: the height of the image
     * @param image: what is the BufferedImage to show
	 */
	public Image(Point position, int width, int height, BufferedImage image) {
		super(position, width, height);
		this.image = image;
	}
	
	/**
	 * Constructor for an image on the world map
	 *
     * @param position: where the image is on screen
     * @param width: the width of the image
     * @param height: the height of the image
     * @param image: what is the BufferedImage to show
	 * @param passable
	 */
	public Image(Point position, int width, int height, BufferedImage image, boolean passable) {
		super(position, width, height, passable);
		this.image = image;
	}

	public BufferedImage getImage() {
		return image;
	}
	
}
