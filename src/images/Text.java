package images;

import java.awt.Color;
import java.awt.Font;

/**
 * A text is a String which can be show on the frame
 *
 * @author Jérémie Beaudoin-Dion
 */
public class Text extends PhysicalObject {
	
	private String message;
	private Font font;
	private String alignment;
	private Color color;
	
	/**
	 * Constructor
	 * 
	 * @param position
	 * @param message
	 * @param font
	 * @param alignment: "Right", "Left", "Center"
	 * @param color
	 */
	public Text(Point position, String message, Font font, String alignment, Color color){
		super(position, font.getSize(), font.getSize() * message.length());
		this.message = message;
		this.font = font;
		this.alignment = alignment;
		this.color = color;
	}

	public String getMessage() {
		return message;
	}

	public Font getFont() {
		return font;
	}

	public String getAlignment() {
		return alignment;
	}
	
	public Color getColor() {
		return color;
	}

}

