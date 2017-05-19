package images;

/**
 * A physical object which can be selected
 * 
 * @author Jérémie Beaudoin-Dion
 *
 */
public class Button extends PhysicalObject {
	
	private Image image;
	private boolean selected;
	private Text text;
	
	/**
	 * Constructor for an image-button
	 * 
	 * @param image
	 * @param selected
	 */
	public Button(Image image, boolean selected) {
		super(image.getPosition(), image.getWidth(), image.getHeight());
		this.image = image;
		this.selected = selected;
	}
	
	/**
	 * Constructor for an image-button in the world
	 * 
	 * @param image
	 * @param selected
	 * @param passable
	 */
	public Button(Image image, boolean selected, boolean passable) {
		super(image.getPosition(), image.getWidth(), image.getHeight(), passable);
		this.image = image;
		this.selected = selected;
	}
	
	/**
	 * Constructor for a button with text
	 * 
	 * @param position
	 * @param width
	 * @param height
	 * @param text
	 * @param selected
	 */
	public Button(Point position, int width, int height, Text text, boolean selected){
		super(position, width, height);
		this.text = text;
		this.selected = selected;
	}

	public Image getImage() {
		return image;
	}

	public boolean isSelected() {
		return selected;
	}

	public Text getText() {
		return text;
	}
	

}
