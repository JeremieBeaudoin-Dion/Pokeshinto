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
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param name
	 */
	public Image(Point position, int width, int height, BufferedImage image) {
		super(position, width, height);
		this.image = image;
	}

	public BufferedImage getImage() {
		return image;
	}
	
}
