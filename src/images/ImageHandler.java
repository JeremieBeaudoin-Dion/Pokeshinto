package images;

import java.util.List;

import java.awt.*;
import java.awt.image.BufferedImage;
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
	
	// Enables double buffer
	private BufferedImage backBuffer; 
	private Insets insets;	
	
	// List of small characters
	private static final char[] CHARACTERS_SMALL = new char[]{'f', 'i', 'j', 'l', 't'};

	private Camera camera;

	private boolean considerCamera;
	
	/**
	 * Constructor; creates the window and such
	 */
	public ImageHandler (Camera camera){

	    this.camera = camera;
		
		// Sets the parameter for the window
		setTitle("PokeShinto"); 
		setPreferredSize(new Dimension(Game.WINDOW_WIDTH, Game.WINDOW_HEIGHT));
		setResizable(false); 
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		pack();

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
	public void update(List<PhysicalObject> physicalObjects, int gameState) {

        considerCamera = (gameState == Game.GAMESTATE_WORLD || gameState == Game.GAMESTATE_PLAYERMENU  || gameState == Game.GAMESTATE_WORLDMENU);
		
		// Sets the double buffer
		Graphics2D g = (Graphics2D) getGraphics();
		Graphics2D bbg = (Graphics2D) backBuffer.getGraphics();
		
		// Sets the quality of the image to hight
		setRenderingQuality(bbg);
		
		// Sets default background
		bbg.setColor(Color.BLACK);
		bbg.fillRect(0, 0, Game.WINDOW_WIDTH, Game.WINDOW_HEIGHT);
		
		// Draws the physical objects on the frame
		drawFrame(bbg, physicalObjects);
		
		// Draws the whole thing in one shot -- Double buffer
		g.drawImage(backBuffer, insets.left, insets.top, null);
		
		// Clears the cache
		bbg.dispose();
		g.dispose();

	}

	/**
	 * Sets the desired rendering quality of the image
	 *
	 * @param bbg the double buffered image
	 */
	private void setRenderingQuality(Graphics2D bbg) {
		bbg.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
		bbg.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		bbg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		bbg.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
	}
	
	/**
	 * Distributes the physical objects to the correct private method
	 * 
	 * @param bbg: the doubble buffer
	 * @param physicalObjects: the physical object to show
	 */
	private void drawFrame(Graphics bbg, List<PhysicalObject> physicalObjects) {
		
		Iterator<PhysicalObject> iterator = physicalObjects.iterator();
        PhysicalObject current;
		
		while (iterator.hasNext()) {
			current = iterator.next();
			if (!considerCamera || objectIsWithinFrameBoundaries(current)) {
                printPhysicalObjectOnBuffer(bbg, current);
            }

		}
	
	}

    /**
     * @return true if the object is within the frame
     */
	private boolean objectIsWithinFrameBoundaries(PhysicalObject object) {

	    if (object.getX() + object.getWidth() - camera.getPosition().getX() < 0 ||
                object.getY() + object.getHeight() - camera.getPosition().getY() < 0) {
	        return false;
        }

        if (object.getX() - camera.getPosition().getX() > Game.WINDOW_WIDTH ||
                object.getY() - camera.getPosition().getY() > Game.WINDOW_HEIGHT) {
            return false;
        }

	    return true;
    }

    /**
     * Sends the physical object to the correct method in order to be put on screen
     * @param bbg the doubble buffered image
     * @param object the object to show on the frame
     */
	private void printPhysicalObjectOnBuffer(Graphics bbg, PhysicalObject object) {
        if (object instanceof Text) {
            printText(bbg, (Text) object);

        } else if (object instanceof Button) {
            printButton(bbg, (Button) object);

        } else if (object instanceof Image) {
            printImage(bbg, (Image) object);

        } else if (object instanceof Ellipse){
            printEllipse(bbg, (Ellipse) object);
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
	    if (considerCamera) {
            bbg.drawImage(image.getImage(), image.getX() - camera.getPosition().getX(),
                    image.getY() - camera.getPosition().getY(), null);
        } else {
            bbg.drawImage(image.getImage(), image.getX(), image.getY(), null);
        }

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

        if (considerCamera) {
            bbg.drawString(text.getMessage(), x - camera.getPosition().getX(),
                    text.getY() - camera.getPosition().getY());
        } else {
            bbg.drawString(text.getMessage(), x, text.getY());
        }

		
	}

    /**
     * @param bbg: the doubble buffer
     * @param ellipse: the shape to draw on screen
     */
    private void printEllipse(Graphics bbg, Ellipse ellipse) {

        bbg.setColor(ellipse.getColor());

        if (considerCamera) {
            bbg.fillOval(ellipse.getX() - camera.getPosition().getX(),
                    ellipse.getY() - camera.getPosition().getY(), ellipse.getWidth(), ellipse.getHeight());
        } else {
            bbg.fillOval(ellipse.getX(), ellipse.getY(), ellipse.getWidth(), ellipse.getHeight());
        }

    }
	
	/**
	 * This method centers a message according to its font size
	 * 
	 * @param message: the string representation of the message
	 * @param font: its font
	 * @return: the Pixel amount the message should be diverted by
	 */
	private int centerMessageByFont(String message, Font font) {
        int numberOfSmallLetters = countHowManySmallCharactersInString(message);

        int numberOfBigLetters = message.length() - numberOfSmallLetters;

		return (font.getSize() / 4) * numberOfBigLetters + (font.getSize() / 5) * numberOfSmallLetters;
	}

	private int countHowManySmallCharactersInString(String message) {
	    int smallCharacters = 0;

        for (char c : message.toCharArray()) {

            for (int i=0; i<CHARACTERS_SMALL.length; i++) {
                if (CHARACTERS_SMALL[i] == c) {
                    smallCharacters++;
                }
            }

        }

        return smallCharacters;
    }
	
	/**
	 * Returns the current position of the mouse relative to the 
	 * screen position.
	 * 
	 * @return the current mouse position with a Position
	 */
	public Point getMousePosition() {
		
		PointerInfo currentMouse = MouseInfo.getPointerInfo();
		
		int x = (int) (currentMouse.getLocation().getX() - getLocationOnScreen().getX());
		int y = (int) (currentMouse.getLocation().getY() - getLocationOnScreen().getY());
		
		return new Point(x, y);
	}

    @Override
    public void paint (Graphics g) {}

	@Override
	public void repaint () {}
	
}
