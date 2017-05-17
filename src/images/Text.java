package images;

import java.awt.Color;
import java.awt.Font;

public class Text extends PhysicalObject {
	
	private String message;
	private Font font;
	private String alignment;
	private Color color;
	
	/**
	 * Constructor
	 * 
	 * @param x
	 * @param y
	 * @param message
	 * @param font
	 * @param alignment: "Right", "Left", "Center"
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

