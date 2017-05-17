package pokeshinto;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import combat.Combat;
import combat.Dictionary;
import combat.Menu;

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
	
	// The game model
	private ActionHandlerInWorld actionHandlerInWorld;
	private ActionHandlerInCombat actionHandlerInCombat;
	private ImageLoader imageLoader;
	
	// Width and height of the game
	private int windowWidth = 900;
	private int windowHeight = 600;
	
	// Enables double buffer
	private BufferedImage backBuffer; 
	private Insets insets;
	
	// Enables player input
	private InputHandler playerInput; 
	private MouseHandler emouse;
	
	// Creates the fonts used by ImageHandler
	private static final Font FONT_BUTTON = new Font("Century Schoolbook", Font.PLAIN, 35);
	private static final Font FONT_ALLERT = new Font("Century Schoolbook", Font.PLAIN, 50);
	private static final Font FONT_ALLERT_SMALL = new Font("Century Schoolbook", Font.PLAIN, 30);
	private static final Font FONT_DESCRIPTION = new Font("Century Schoolbook", Font.PLAIN, 20);
	private static final Font FONT_HEADER = new Font("Century Schoolbook", Font.BOLD, 20);
	private static final Font FONT_SMALL_BOLD = new Font("Century Schoolbook", Font.BOLD, 14);
	private static final Font FONT_SMALL_NORMAL = new Font("Century Schoolbook", Font.PLAIN, 14);
	
	// List of small characters
	private static final char[] CHARACTERS_SMALL = new char[]{'f', 'i', 'j', 'l', 't'};
	
	// Creates the color used by ImageHandler
	private static final Color COLOR_TEXT_SELECTED = new Color(190, 170, 115);
	
	/**
	 * Constructor; creates the window and such
	 */
	public ImageHandler (ActionHandlerInWorld actionHandlerInWorld, ActionHandlerInCombat actionHandlerInCombat){
		// Loads all images
		imageLoader = new ImageLoader();
		
		// Gets the reference to the actionHandler objects
		this.actionHandlerInWorld = actionHandlerInWorld;
		this.actionHandlerInCombat = actionHandlerInCombat;
		
		// Sets the parameter for the window
		setTitle("PokeShinto"); 
		setSize(windowWidth, windowHeight); 
		setResizable(false); 
		setDefaultCloseOperation(EXIT_ON_CLOSE); 
		setVisible(true); 

		// Makes sure the graphics dont get drawn on the border of the window
		insets = getInsets(); 
		setSize(insets.left + windowWidth + insets.right, 
				insets.top + windowHeight + insets.bottom); 

		// Enables double buffer
		backBuffer = new BufferedImage(windowWidth, windowHeight, BufferedImage.TYPE_INT_RGB); 
		
		// Enables mouse events
		playerInput = new InputHandler(this);
		emouse = new MouseHandler(this);
	}
	
	/** 
	 * Called every frame, this method handles graphics 
	 * 
	 * @param gameState
	 */ 
	public void update(String gameState) {
		
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
		bbg.fillRect(0, 0, windowWidth, windowHeight);
		
		// Sends update to the correct method
		if (gameState.equals("Combat")){
			printCombat(bbg);
		}
		
		// Draws the whole thing in one shot -- Double buffer
		g.drawImage(backBuffer, insets.left, insets.top, this); 
		
		// Clears the cache
		bbg.dispose();
		g.dispose();
	}
	
	/**
	 * Call every frame the game is in the Combat gameState
	 * 
	 * @param bbg
	 */
	private void printCombat(Graphics bbg) {
		
		/*
		 * The combat menu to show
		 */
		printCombatMenu(bbg);
		
		/*
		 * The information on the PokeShintos
		 */
		printCombatInformation(bbg);
		
	
		
	
	}
	
	/**
	 * First of the COMBAT methods. Prints the menu from which to choose 
	 * @param bbg
	 */
	private void printCombatMenu(Graphics bbg) {
		// Gets the current menu
		Menu combatMenu = actionHandlerInCombat.getCurrentMenu();
		
		// Prints the background
		bbg.drawImage(imageLoader.getCombatBackgound(), 0, 0, null);
		
		// Prints the choice menu
		int yButton = 520;
		
		if (combatMenu.getAllert() != null) {
			Font currentFont;
			
			if (combatMenu.getAllert().length() > 20){
				currentFont = FONT_ALLERT_SMALL;
			} else {
				currentFont = FONT_ALLERT;
			}
			bbg.setFont(currentFont);
			bbg.setColor(Color.WHITE);
			// prints the allert
			int xButton = windowWidth/2;
			drawStringWithParagraph(bbg, currentFont, combatMenu.getAllert(), xButton, yButton);
			
		} else {
			// There are buttons
			int numButtons = combatMenu.getNumberOfButtons();
			int[] xButtons = new int[numButtons]; //windowWidth/2;
			bbg.setFont(FONT_BUTTON);
			
			for (int i=0; i<numButtons; i++) {
				bbg.setColor(Color.WHITE);
				
				if (i == combatMenu.getSelected()){
					bbg.setColor(COLOR_TEXT_SELECTED);
				}
				
				xButtons[i] = windowWidth/(numButtons + 1) * (i+1) - centerMessageByFont(combatMenu.getButton(i), FONT_BUTTON);
				bbg.drawString(combatMenu.getButton(i), xButtons[i], yButton);
			}
			
			// Prints the description
			bbg.setFont(FONT_DESCRIPTION);
			bbg.setColor(Color.WHITE);
		}
	}
	
	/**
	 * The draw String method doesn't handle \n characters. I must handle them myself.
	 * This method also centers by X and Y, according to the relative position they must be in
	 * 
	 * @param bbg: the Graphics on which to draw the String
	 * @param message: The String message to show
	 * @param x: The desired X position
	 * @param y: The desired Y position
	 */
	private void drawStringWithParagraph(Graphics bbg, Font currentFont, String message, int x, int y){
		
		String[] lines = message.split("\n");
		
		int initialY = y - (int)((lines.length - 1) * currentFont.getSize()/1.3);
		
		for (int i=0; i<lines.length; i++) {
			bbg.drawString(lines[i], x - centerMessageByFont(lines[i], currentFont), 
								     initialY + (int)(i * (currentFont.getSize() * 1.3)));
		}
		
	}
	
	/**
	 * Second COMBAT method. Prints the information of the pokeshintos
	 */
	private void printCombatInformation(Graphics bbg) {
		Combat combat = actionHandlerInCombat.getCombat();
		
		// The health bars
		bbg.drawImage(imageLoader.getHealthBarBackground(combat.getPlayerMissingHpPercentage()), 0, 0, null);
		bbg.drawImage(imageLoader.getHealthBarBackground(combat.getOpponentMissingHpPercentage()), windowWidth - imageLoader.getHealthBarWidth(), 0, null);
		bbg.drawImage(imageLoader.getHealthBarForeground(), 0, 0, null);
		bbg.drawImage(imageLoader.getHealthBarForeground(), windowWidth - imageLoader.getHealthBarWidth(), 0, null);
		
		// The name of the Shintomons
		bbg.setFont(FONT_HEADER);
		bbg.setColor(Color.BLACK);
		
		int x = (int) (imageLoader.getHealthBarWidth() / 2.2);
		int x2 = (int) (windowWidth - imageLoader.getHealthBarWidth() / 1.8);
		int y = windowHeight/10;
		
		bbg.drawString(combat.getPlayerPokeshinto().getId(), x - centerMessageByFont(combat.getPlayerPokeshinto().getId(), FONT_HEADER), y);
		bbg.drawString(combat.getOpponentPokeshinto().getId(), x2 - centerMessageByFont(combat.getOpponentPokeshinto().getId(), FONT_HEADER), y);
		
		// Stats		
		int iterator = 0;
		String playerStat;
		String opponentStat;
		Font currentFont = FONT_SMALL_BOLD;
		
		do {
			playerStat = combatPlayerInformationGetter(combat, iterator);
			opponentStat = combatOpponentInformationGetter(combat, iterator);
			y = y + currentFont.getSize() * 2;
			
			if (playerStat != null){
				if (playerStat.equals("Stat") || playerStat.equals("Element Resistance") || playerStat.equals("Currently Affected By")){
					currentFont = FONT_SMALL_BOLD;
				} else {
					currentFont = FONT_SMALL_NORMAL;
				}
				bbg.setFont(currentFont);
				bbg.drawString(playerStat, x - centerMessageByFont(playerStat, currentFont), y);
			}
			
			if (opponentStat != null) {
				if (opponentStat.equals("Stat") || opponentStat.equals("Element Resistance") || opponentStat.equals("Currently Affected By")){
					currentFont = FONT_SMALL_BOLD;
				} else {
					currentFont = FONT_SMALL_NORMAL;
				}	
				bbg.setFont(currentFont);
				bbg.drawString(opponentStat, x2 - centerMessageByFont(opponentStat, currentFont), y);
			}
			
			iterator++;
		}
		while(playerStat != null && opponentStat != null);
	}
	
	/**
	 * Gets the correct information about the player according to an iterator
	 * 
	 * @param combat
	 * @param iterator
	 * @return
	 */
	private String combatPlayerInformationGetter(Combat combat, int iterator) {
		
		switch (iterator){
		case 0:
			return "Stat";
		case 1:
			return "Strength " + (int) combat.getPlayerStrength();
		case 2:
			return "Focus " + (int) combat.getPlayerFocus();
		case 3:
			return "Armor " + (int) combat.getPlayerArmor();
		case 4:
			return "Agility " + (int) combat.getPlayerArmor();
		case 5:
			return "Element Resistance";
		default:
			iterator -= 5;
			Dictionary<Integer> resistance = combat.getAllPlayerElementResistance();
			try {
				return resistance.getKeyByIndex(iterator) + " " + resistance.getValueByIndex(iterator);
			} catch (ArrayIndexOutOfBoundsException e){
				return null;
			}
		}
	}
	
	/**
	 * Gets the correct information about the opponent according to an iterator
	 * 
	 * @param combat
	 * @param iterator
	 * @return
	 */
	private String combatOpponentInformationGetter(Combat combat, int iterator) {
		
		switch (iterator){
		case 0:
			return "Stat";
		case 1:
			return (int) combat.getOpponentStrength() + " Strength";
		case 2:
			return (int) combat.getOpponentFocus() + " Focus";
		case 3:
			return (int) combat.getOpponentArmor() + " Armor";
		case 4:
			return (int) combat.getOpponentAgility() + " Agility";
		case 5:
			return "Element Resistance";
		default:
			iterator -= 5;
			Dictionary<Integer> resistance = combat.getAllOpponentElementResistance();
			try {
				return resistance.getValueByIndex(iterator) + " " + resistance.getKeyByIndex(iterator);
			} catch (ArrayIndexOutOfBoundsException e){
				return null;
			} catch (NullPointerException e) {
				return null;
			}
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
		return font.getSize() / 2 * message.length() / 2;
	}
	
	/**
	 * TO DELETE
	 * @param bbg
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
	 * This method handles input from JFrame, then send the action
	 * to the correct class
	 */
	public void input(String gameState){
		// Interprets the in game value of the action
		String action = null;
		
		if (playerInput.isKeyDown(KeyEvent.VK_A) || playerInput.isKeyDown(KeyEvent.VK_LEFT)){
			action = "Left";
		} 
		
		if (playerInput.isKeyDown(KeyEvent.VK_D) || playerInput.isKeyDown(KeyEvent.VK_RIGHT)){
			action = "Right";
		} 
		
		if (playerInput.isKeyDown(KeyEvent.VK_W) || playerInput.isKeyDown(KeyEvent.VK_UP)){
			action = "Up";
		}
		
		if (playerInput.isKeyDown(KeyEvent.VK_S) || playerInput.isKeyDown(KeyEvent.VK_DOWN)){
			action = "Down";
		}
		
		if (playerInput.isKeyDown(KeyEvent.VK_SPACE) || playerInput.isKeyDown(KeyEvent.VK_ENTER)){
			action = "Decide";
		}
		
		if (playerInput.isKeyDown(KeyEvent.VK_ESCAPE) || playerInput.isKeyDown(KeyEvent.VK_BACK_SPACE)){
			action = "Escape";
		}
		
		if (emouse.isMouseDown()){			
			PointerInfo currentMouse = MouseInfo.getPointerInfo();
			Point myWindow = getLocationOnScreen();
			Point b = currentMouse.getLocation();
			
			int x = (int) (b.getX() - myWindow.getX());
			int y = (int) (b.getY() - myWindow.getY());
			
			System.out.println(x);
			System.out.println(y);
			
		}
		
		// If there is an action to do...
		if (action != null) {			
			// Sends the input to the correct handler
			if (gameState.equals("Combat")){
				actionHandlerInCombat.doInput(action);
			} else {
				actionHandlerInWorld.doInput(action);
			}
		}
		
	}
	
}
