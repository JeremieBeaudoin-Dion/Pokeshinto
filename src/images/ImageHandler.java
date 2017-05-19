package images;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JFrame;

import pokeshinto.Game;

/**
 * This class creates a JFrame instance.
 * It draws the graphics on the frame and handles 
 * doubble buffering.
 * 
 * Because this class extends JFrame, it will handle direct input
 * and sent it to the Action class
 * 
 * @author Jérémie Beaudoin-Dion
 *
 */
public class ImageHandler extends JFrame {

	/**
	 * Declare the serialVersion
	 */
	private static final long serialVersionUID = 1L;
	
	// Enables double buffer
	private BufferedImage backBuffer; 
	private Insets insets;	
	
	// List of small characters
	private static final char[] CHARACTERS_SMALL = new char[]{'f', 'i', 'j', 'l', 't'};
	
	/**
	 * Constructor; creates the window and such
	 */
	public ImageHandler (){
		
		// Sets the parameter for the window
		setTitle("PokeShinto"); 
		setSize(Game.WINDOW_WIDTH, Game.WINDOW_HEIGHT); 
		setResizable(false); 
		setDefaultCloseOperation(EXIT_ON_CLOSE); 
		setVisible(true); 

		// Makes sure the graphics dont get drawn on the border of the window
		insets = getInsets(); 
		setSize(insets.left + Game.WINDOW_WIDTH + insets.right, 
				insets.top + Game.WINDOW_HEIGHT + insets.bottom); 

		// Enables double buffer
		backBuffer = new BufferedImage(Game.WINDOW_WIDTH, Game.WINDOW_HEIGHT, BufferedImage.TYPE_INT_RGB); 
	}
	
	/** 
	 * Called every frame, this method handles graphics 
	 * 
	 * @param physicalObjects: the PhysicalObjects to show on the frame
	 */ 
	public void update(ArrayList<PhysicalObject> physicalObjects) {
		
		// Sets the double buffer
		Graphics2D g = (Graphics2D) getGraphics();
		Graphics2D bbg = (Graphics2D) backBuffer.getGraphics();
		
		// Sets the quality of the image to hight
		bbg.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
		bbg.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		bbg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		bbg.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
		// Sets default background
		bbg.setColor(Color.WHITE); 
		bbg.fillRect(0, 0, Game.WINDOW_WIDTH, Game.WINDOW_HEIGHT);
		
		// Sends update to the correct method
		drawFrame(bbg, physicalObjects);
		
		// Draws the whole thing in one shot -- Double buffer
		g.drawImage(backBuffer, insets.left, insets.top, this); 
		
		// Clears the cache
		bbg.dispose();
		g.dispose();
	}
	
	/**
	 * Distributes the physical objects to the correct private method
	 * 
	 * @param bbg: the doubble buffer
	 * @param physicalObjects: the physical object to show
	 */
	private void drawFrame(Graphics bbg, ArrayList<PhysicalObject> physicalObjects) {
		
		Iterator<PhysicalObject> iterator = physicalObjects.iterator();
		
		while (iterator.hasNext()) {
			PhysicalObject current = iterator.next();
			if (current instanceof Text) {
				printText(bbg, (Text) current);
				
			} else if (current instanceof Button) {
				printButton(bbg, (Button) current);
				
			} else if (current instanceof Image) {
				printImage(bbg, (Image) current);
			}
		}
	
	}
	
	/**
	 * Prints a Button on a buffered image
	 *
     * @param bbg: the doubble buffer
	 * @param button: the button to show
	 */
	private void printButton(Graphics bbg, Button button) {
		if (button.getImage() != null) {
			printImage(bbg, button.getImage());
		}
		if (button.getText() != null) {
			printText(bbg, button.getText());
		}
	}
	
	/**
	 * Prints an image on a buffered image
	 *
     * @param bbg: the doubble buffer
	 * @param image: the image to show
	 */
	private void printImage(Graphics bbg, Image image) {
		bbg.drawImage(image.getImage(), image.getX(), image.getY(), null);
	}
	
	/**
	 * Prints a Text object on a buffered image
	 *
     * @param bbg: the doubble buffer
	 * @param text: the text to show
	 */
	private void printText(Graphics bbg, Text text) {
		bbg.setColor(text.getColor());
		bbg.setFont(text.getFont());
		int x = text.getX();
		
		// Aligns the text
		if (text.getAlignment().equals("Center")) {
			x -= centerMessageByFont(text.getMessage(), text.getFont());
		} else if (text.getAlignment().equals("Right")) {
			//TODO
		}
		
		bbg.drawString(text.getMessage(), x, text.getY());
		
	}
	
	/**
	 * This method centers a message according to its font size
	 * 
	 * @param message: the string representation of the message
	 * @param font: its font
	 * @return: the Pixel amount the message should be diverted by
	 */
	private int centerMessageByFont(String message, Font font) {
		return font.getSize() / 2 * message.length() / 2;
	}
	
	/**
	 * TO DELETE
     * @param bbg: the doubble buffer
	 */
	private void example(Graphics bbg) {
		// drawOval(int x, int y, int width, int length)
		// drawPolygon(int x[ ], int y[ ], int n)
		// bbg.drawArc(200, 80, 75, 95, 0, 90);
		// drawArc(int x, int y, int width, int length, int startAngle, int arcAngle)
		// bbg.drawString("Bonjour", 50, 50);
		
		// Draw the background
		/*
		 * bbg.setColor(Color.GRAY);
		ArrayList<int[][]> myback = new ArrayList<int[][]>();
		myback = Background.draw();
		int n = Array.getLength(myback.get(0)[0]); // number of points in polygon
		
		for (int i=0; i<myback.size(); i++){
			bbg.fillPolygon(myback.get(i)[0], myback.get(i)[1], n);
		}
		 */
		
		/*
		 * BufferedImage img = null;
		try {
			img = ImageIO.read(new File("buttonIMG.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		bbg.drawImage(img, 10, 10, 100, 100, null);
		 */
		
		// Draw the player
		bbg.setColor(Color.BLACK); 
		// bbg.fillRoundRect(robotx, roboty, 20, 20, 20, 20); 
		//bbg.fillRect(position[0], position[1], 20, 20); 
	}
	
	/**
	 * Returns the current position of the mouse relative to the 
	 * screen position.
	 * 
	 * @return the current mouse position with a Java.Point
	 */
	public Point getMousePosition() {
		
		PointerInfo currentMouse = MouseInfo.getPointerInfo();
		Point myWindow = getLocationOnScreen();
		Point b = currentMouse.getLocation();
		
		int x = (int) (b.getX() - myWindow.getX());
		int y = (int) (b.getY() - myWindow.getY());
		
		return new Point(x, y);
	}
	
}
