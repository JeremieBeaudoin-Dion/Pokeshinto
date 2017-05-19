package combat;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.util.ArrayList;

import images.Button;
import images.Image;
import images.PhysicalObject;
import images.Point;
import images.Text;
import pokeshinto.Game;
import pokeshinto.Menu;

/**
 * Is the model of the game. It handles the objects of the 
 * game and their position in combat.
 * 
 * @author Jérémie Beaudoin-Dion
 *
 */
public class ObjectHandlerInCombat {
	
	// The instances of the MVC
	private ActionHandlerInCombat actionHandlerInCombat;
	
	// Width and height of the game
	private int windowWidth = Game.WINDOW_WIDTH;
	private int windowHeight = Game.WINDOW_HEIGHT;
	
	// All combat Images
	private CombatImageLoader imageLoader;
	
	// The background of the combat
	private Image backgroundImage;
	
	// Fonts
	private static final Font FONT_BUTTON = new Font("Century Schoolbook", Font.PLAIN, 35);
	private static final Font FONT_ALLERT = new Font("Century Schoolbook", Font.PLAIN, 50);
	private static final Font FONT_ALLERT_SMALL = new Font("Century Schoolbook", Font.PLAIN, 30);
	private static final Font FONT_DESCRIPTION = new Font("Century Schoolbook", Font.PLAIN, 20);
	private static final Font FONT_HEADER = new Font("Century Schoolbook", Font.BOLD, 20);
	private static final Font FONT_SMALL_BOLD = new Font("Century Schoolbook", Font.BOLD, 14);
	private static final Font FONT_SMALL_NORMAL = new Font("Century Schoolbook", Font.PLAIN, 14);
	
	// Colors
	private static final Color COLOR_TEXT_YELLOW = new Color(190, 170, 115);
	
	/**
	 * Constructor
	 * 
	 * @param actionHandlerInCombat: the currenct actionHandlerInCombat
	 * @throws IOException 
	 */
	public ObjectHandlerInCombat(ActionHandlerInCombat actionHandlerInCombat) throws IOException {
		
		// Loads all images
		imageLoader = new CombatImageLoader();
		
		this.actionHandlerInCombat = actionHandlerInCombat;
		
		this.backgroundImage = new Image(new Point(0, 0), windowWidth, windowHeight, imageLoader.getCombatBackgound());
		
	}
	
	/**
	 * Returns all physical objects to draw in the game
	 * 
	 * @return
	 */
	public ArrayList<PhysicalObject> get() {
		
		ArrayList<PhysicalObject> physicalObjects = new ArrayList<PhysicalObject>();
		
		physicalObjects.add(backgroundImage);
		
		physicalObjects = getMenu(physicalObjects);
		
		physicalObjects = getAllInfo(physicalObjects);
		
		return physicalObjects;
		
	}
	
	/**
	 * Retuns all physical objects to print on the JFrame that represent the
	 * buttons of the current Menu.
	 * 
	 * @param physicalObjects
	 * @return
	 */
	private ArrayList<PhysicalObject> getMenu(ArrayList<PhysicalObject> physicalObjects) {
		Menu currentMenu = actionHandlerInCombat.getCurrentMenu();
		
		
		// When there is an allert, there are no buttons
		if (currentMenu.getAllert() != null) {
			
			physicalObjects = getAllert(physicalObjects, currentMenu);
			
		} else {
			
			physicalObjects = getMenuButtons(physicalObjects, currentMenu);
			
		}
		
		return physicalObjects;
	}
	
	/**
	 * Returns the allert of the current menu in form of Physical object 
	 * 
	 * @param physicalObjects
	 * @param currentMenu
	 * @return
	 */
	private ArrayList<PhysicalObject> getAllert(ArrayList<PhysicalObject> physicalObjects, Menu currentMenu){
		Font currentFont;
		
		// Gets the size according to the length of the text
		if (currentMenu.getAllert().length() > 20){
			currentFont = FONT_ALLERT_SMALL;
		} else {
			currentFont = FONT_ALLERT;
		}
		
		// Divide the text into lines
		String[] lines = currentMenu.getAllert().split("\n");
		int x = windowWidth/2;
		int y = 520;
		
		// Readjust the initial y
		int initialY = y - (int)((lines.length - 1) * currentFont.getSize()/1.3);
		
		for (int i=0; i<lines.length; i++) {
			physicalObjects.add(new Text(new Point(x, initialY + (int)(i * (currentFont.getSize() * 1.3))), 
									lines[i], currentFont, "Center", Color.WHITE));
		}
		
		return physicalObjects;
	}
	
	/**
	 * Returns all physical objects to print on the JFrame that represent the
	 * buttons of the Menu
	 * 
	 * @param currentMenu
	 * @return
	 */
	private ArrayList<PhysicalObject> getMenuButtons(ArrayList<PhysicalObject> physicalObjects, Menu currentMenu) {
		
		// Button is selected
		int selectedID = currentMenu.getSelected();
		boolean selected = false;
		
		// Position, width, height
		int x = 0;
		int y = 450;
		int width = windowWidth / currentMenu.getNumberOfButtons();
		int height = windowHeight - y - 45;
		
		// Color and text of button
		Color currentColor;
		Text text;
		
		for (int i=0; i<currentMenu.getNumberOfButtons(); i++) {
			// Sets the buttons to selected
			if (i == selectedID) {
				selected = true;
				physicalObjects.add(new Image(new Point(x, y), width, height, imageLoader.getMenuSelector(width, height)));
			} else {
				selected = false;
			}
			
			// Sets the color to indicate if the action is doable
			if (actionHandlerInCombat.getInputIsDoable(i)){
				currentColor = COLOR_TEXT_YELLOW;
			} else {
				currentColor = Color.GRAY;
			}
			
			text = new Text(new Point(x + width/2, 520), currentMenu.getButton(i), FONT_BUTTON, "Center", currentColor);
			physicalObjects.add(new Button(new Point(x, y), width, height, text, selected));
			x = x + width;
		}
		
		return physicalObjects;
	}
	
	/**
	 * Returns all physical objects to print on the JFrame that represent the
	 * information of the player and the opponent
	 * 
	 * @param physicalObjects
	 * @return
	 */
	private ArrayList<PhysicalObject> getAllInfo(ArrayList<PhysicalObject> physicalObjects) {
		Combat combat = actionHandlerInCombat.getCombat();
		
		// The health Bars
		int width = imageLoader.getHealthBarWidth();
		int height = imageLoader.getHealthBarHeight();
		
		physicalObjects.add(new Image(new Point(0, 0), width, height, imageLoader.getHealthBarBackground(combat.getPlayerMissingHpPercentage())));
		physicalObjects.add(new Image(new Point(windowWidth - width, 0), 
				width, height, imageLoader.getHealthBarBackground(combat.getOpponentMissingHpPercentage())));
		physicalObjects.add(new Image(new Point(0, 0), width, height, imageLoader.getHealthBarForeground()));
		physicalObjects.add(new Image(new Point(windowWidth - imageLoader.getHealthBarWidth(), 0), 0, 0, imageLoader.getHealthBarForeground()));
		
		// The name of the PokeShintos
		int y = windowHeight/10;
		int x = (int) (imageLoader.getHealthBarWidth() / 2.2);
		int x2 = (int) (windowWidth - imageLoader.getHealthBarWidth() / 1.8);
		
		physicalObjects.add(new Text(new Point(x, y), combat.getPlayerPokeshinto().getId(), FONT_HEADER, "Center", Color.BLACK));
		physicalObjects.add(new Text(new Point(x2, y), combat.getOpponentPokeshinto().getId(), FONT_HEADER, "Center", Color.BLACK));
		
		// The stats of the PokeShintos
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
				
				physicalObjects.add(new Text(new Point(x, y), playerStat, currentFont, "Center", Color.BLACK));
			}
			
			if (opponentStat != null) {
				if (opponentStat.equals("Stat") || opponentStat.equals("Element Resistance") || opponentStat.equals("Currently Affected By")){
					currentFont = FONT_SMALL_BOLD;
				} else {
					currentFont = FONT_SMALL_NORMAL;
				}	
				
				physicalObjects.add(new Text(new Point(x2, y), opponentStat, currentFont, "Center", Color.BLACK));
			}
			
			iterator++;
		}
		while(playerStat != null && opponentStat != null);
		
		
		return physicalObjects;
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

}
