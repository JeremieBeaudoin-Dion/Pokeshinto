package combat;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import images.*;
import images.Position;
import pokeshinto.Game;
import pokeshinto.InfoHandler;
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
		
		this.backgroundImage = new Image(new Position(0, 0), windowWidth, windowHeight, imageLoader.getCombatBackgound());
		
	}
	
	/**
	 * Returns all physical objects to draw in the game
	 */
	public LinkedList<PhysicalObject> get() {

		LinkedList<PhysicalObject> physicalObjects = new LinkedList<>();
		
		physicalObjects.add(backgroundImage);

		physicalObjects.addAll(getMenu());
		
		physicalObjects.addAll(getAllInfo());
		
		return physicalObjects;
		
	}
	
	/**
	 * Retuns all physical objects to print on the JFrame that represent the
	 * buttons of the current Menu.
	 */
	private List<PhysicalObject> getMenu() {
        List<PhysicalObject> physicalObjects = new LinkedList<>();

		Menu currentMenu = actionHandlerInCombat.getCurrentMenu();
		
		
		// When there is an allert, there are no buttons
		if (currentMenu.getAllert() != null) {
			
			physicalObjects.addAll(getAllert(currentMenu));
			
		} else {

		    // When showing the menu buttons, you also show their Element
		    if (currentMenu.getID().equals("Skill")) {
                physicalObjects.addAll(getMenuSkillButtons(currentMenu));
            } else {
                physicalObjects.addAll(getMenuButtons(currentMenu));
            }

		    physicalObjects.addAll(getMenuDescription(currentMenu));
			
		}
		
		return physicalObjects;
	}
	
	/**
	 * Returns the allert of the current menu in form of Physical object
	 */
	private List<PhysicalObject> getAllert(Menu currentMenu){
        LinkedList<PhysicalObject> physicalObjects = new LinkedList<>();

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
			physicalObjects.add(new Text(new Position(x, initialY + (int)(i * (currentFont.getSize() * 1.3))),
									lines[i], currentFont, "Center", Color.WHITE));
		}
		
		return physicalObjects;
	}
	
	/**
	 * Returns all physical objects to print on the JFrame that represent the
	 * buttons of the Menu
	 */
	private List<PhysicalObject> getMenuButtons(Menu currentMenu) {
        List<PhysicalObject> physicalObjects = new LinkedList<>();

        // Button is selected
		int selectedID = currentMenu.getCurrentSelectionInt();
		boolean selected;
		
		// Position, width, height
		int x = 0;
		int y = 450;
		int width = windowWidth / currentMenu.getNumberOfButtons();
		int height = windowHeight - y - 45;
		
		for (int i=0; i<currentMenu.getNumberOfButtons(); i++) {
			// Sets the buttons to selected
			if (i == selectedID) {
				selected = true;
				physicalObjects.add(new Image(new Position(x, y), width, height, imageLoader.getMenuSelector(width, height)));
			} else {
				selected = false;
			}

            Color currentColor;
			String buttonText = currentMenu.getButton(i);

            if (actionHandlerInCombat.getInputIsDoable(buttonText)){
                currentColor = COLOR_TEXT_YELLOW;
            } else {
                currentColor = Color.GRAY;
            }

            Text text = new Text(new Position(x + width/2, y + FONT_BUTTON.getSize() * 2), buttonText, FONT_BUTTON,
                    "Center", currentColor);

			physicalObjects.add(new Button(new Position(x, y), width, height, text, selected));

			x += width;
		}
		
		return physicalObjects;
	}

	private List<PhysicalObject> getMenuSkillButtons(Menu currentMenu) {
        List<PhysicalObject> physicalObjects = new LinkedList<>();

        // Button is selected
        int selectedID = currentMenu.getCurrentSelectionInt();
        boolean selected;

        // Position, width, height
        int x = 0;
        int y = 450;
        int width = windowWidth / currentMenu.getNumberOfButtons();
        int height = windowHeight - y - 45;

        for (int i=0; i<currentMenu.getNumberOfButtons(); i++) {
            // Sets the buttons to selected
            if (i == selectedID) {
                selected = true;
                physicalObjects.add(new Image(new Position(x, y), width, height, imageLoader.getMenuSelector(width, height)));
            } else {
                selected = false;
            }

            String buttonText = currentMenu.getButton(i);
            Color currentColor;

            if (actionHandlerInCombat.getInputIsDoable(buttonText)){
                currentColor = COLOR_TEXT_YELLOW;
            } else {
                currentColor = Color.GRAY;
            }

            Text text = new Text(new Position(x + width/2, y + FONT_BUTTON.getSize() * 2), buttonText, FONT_BUTTON,
                    "Center", currentColor);

            physicalObjects.add(new Button(new Position(x, y), width, height, text, selected));

            String element = InfoHandler.getSkill(buttonText).getElement().getId();

            physicalObjects.add(new Text(new Position(x + width/2, y + FONT_BUTTON.getSize() * 2 + FONT_DESCRIPTION.getSize()),
                    element, FONT_DESCRIPTION, "Center", currentColor));

            x += width;
        }

        return physicalObjects;
    }

    /**
     * Returns the description of the currently selected button of the menu
     */
	private List<PhysicalObject> getMenuDescription(Menu currentMenu) {
        List<PhysicalObject> physicalObjects = new LinkedList<>();

	    Position textPosition = new Position(Game.WINDOW_WIDTH/2, Game.WINDOW_HEIGHT*30/31);
	    String message = actionHandlerInCombat.getCombat().getCurrentDescription(currentMenu.getID(),
                currentMenu.getSelected());

	    physicalObjects.add(new Text(textPosition, message, FONT_DESCRIPTION, "Center", COLOR_TEXT_YELLOW));

	    return physicalObjects;
    }
	
	/**
	 * Returns all physical objects to print on the JFrame that represent the
	 * information of the player and the opponent
	 */
	private List<PhysicalObject> getAllInfo() {
        List<PhysicalObject> physicalObjects = new LinkedList<>();

		Combat combat = actionHandlerInCombat.getCombat();

		physicalObjects.addAll(getHealthBars(combat));

		// The name of the PokeShintos
		int y = windowHeight/10;
		int x = (int) (imageLoader.getHealthBarForegroundWidth() / 2.2);
		int x2 = (int) (windowWidth - imageLoader.getHealthBarForegroundWidth() / 1.8);

        physicalObjects.addAll(getHeader(combat, y, x, x2));

        y += FONT_HEADER.getSize();

        // The stats of the PokeShintos
		physicalObjects.addAll(getAllStats(combat, y, x, x2));
		
		return physicalObjects;
	}

    /**
     * @return the health bars of the Player and the Opponent according to their respective HP
     */
	private List<PhysicalObject> getHealthBars(Combat combat) {
	    List<PhysicalObject> physicalObjects = new LinkedList<>();

        // The health Bars
        int width = imageLoader.getHealthBarBackgroundWidth();
        int height = imageLoader.getHealthBarHeight();

        physicalObjects.add(new Image(new Position(CombatImageLoader.HEALTH_BAR_BACKGROUND_INSET, 0), width, height,
                imageLoader.getPlayerHealthBarBackground(combat.getPlayerMissingHpPercentage())));
        physicalObjects.add(new Image(new Position(windowWidth - width - CombatImageLoader.HEALTH_BAR_BACKGROUND_INSET, 0),
                width, height, imageLoader.getHealthBarBackground(combat.getOpponentMissingHpPercentage())));
        physicalObjects.add(new Image(new Position(0, 0), width, height, imageLoader.getHealthBarForeground()));
        physicalObjects.add(new Image(new Position(windowWidth - imageLoader.getHealthBarForegroundWidth(), 0), 0, 0, imageLoader.getHealthBarForeground()));

        return physicalObjects;
    }

	/**
	 * @return the name of the Pokeshintos and their descriptions, or, if the player doesn't have a pokeshinto, its name
	 */
    private List<PhysicalObject> getHeader(Combat combat, int y, int x, int x2) {
	    List<PhysicalObject> physicalObjects = new LinkedList<>();

	    // The ID of the Pokeshintos
        physicalObjects.add(new Text(new Position(x, y), combat.getPlayerPokeshintoID(), FONT_HEADER, "Center", Color.BLACK));
        physicalObjects.add(new Text(new Position(x2, y), combat.getOpponentPokeshinto().getId(), FONT_HEADER, "Center", Color.BLACK));

        // The description of the Pokeshintos
        y += FONT_HEADER.getSize();

        physicalObjects.add(new Text(new Position(x, y), combat.getPlayerPokeshintoDescription(), FONT_SMALL_BOLD, "Center", Color.BLACK));
        physicalObjects.add(new Text(new Position(x2, y), combat.getOpponentPokeshinto().getDescription(), FONT_SMALL_BOLD, "Center", Color.BLACK));

        return physicalObjects;
    }

    private List<PhysicalObject> getAllStats(Combat combat, int y, int x, int x2) {
	    List<PhysicalObject> physicalObjects = new LinkedList<>();

        int iterator = 0;
        String playerStat;
        String opponentStat;
        Font currentFont = FONT_SMALL_BOLD;

        do {
            playerStat = combatPlayerInformationGetter(combat, iterator);
            opponentStat = combatOpponentInformationGetter(combat, iterator);
            y += currentFont.getSize() * 1.5;

            if (playerStat != null){
                if (playerStat.equals("Stat") || playerStat.equals("Element Resistance") || playerStat.equals("Status")){
                    currentFont = FONT_SMALL_BOLD;
                } else {
                    currentFont = FONT_SMALL_NORMAL;
                }

                physicalObjects.add(new Text(new Position(x, y), playerStat, currentFont, "Center", Color.BLACK));
            }

            if (opponentStat != null) {
                if (opponentStat.equals("Stat") || opponentStat.equals("Element Resistance") || opponentStat.equals("Status")){
                    currentFont = FONT_SMALL_BOLD;
                } else {
                    currentFont = FONT_SMALL_NORMAL;
                }

                physicalObjects.add(new Text(new Position(x2, y), opponentStat, currentFont, "Center", Color.BLACK));
            }

            iterator++;
        }
        while(playerStat != null || opponentStat != null);

        return physicalObjects;
    }
	
	/**
	 * Gets the correct information about the player according to an iterator
	 */
	private String combatPlayerInformationGetter(Combat combat, int iterator) {

	    if (iterator < 5) {
	        return combatPlayerStatGetter(combat, iterator);
        }

        if (iterator == 5) {
            return "Element Resistance";
        }

        iterator -= 6;
        String description = combatPlayerElementResistanceGetter(combat, iterator);

        if (description != null) {
            return description;
        }

        iterator -= combat.getAllPlayerElementResistance().getSize();

        if (iterator == 0) {
            return "Status";
        }

        iterator -= 1;

        return combatPlayerStatusGetter(combat, iterator);

	}

    private String combatPlayerStatGetter(Combat combat, int iterator) {
        switch (iterator) {
            case 0:
                return "Stat";
            case 1:
                return "Strength " + (int) combat.getPlayerStrength();
            case 2:
                return "Focus " + (int) combat.getPlayerFocus();
            case 3:
                return "Armor " + (int) combat.getPlayerArmor();
            case 4:
                return "Agility " + (int) combat.getPlayerAgility();
            default:
                return null;
        }
    }

    private String combatPlayerElementResistanceGetter(Combat combat, int iterator) {

        Dictionary<Integer> resistance = combat.getAllPlayerElementResistance();
        try {
            return resistance.getKeyByIndex(iterator) + " " + resistance.getValueByIndex(iterator);

        } catch (IndexOutOfBoundsException | NullPointerException e){
            return null;
        }
    }

    private String combatPlayerStatusGetter(Combat combat, int iterator) {
        String stat;

        switch (iterator) {
            case 0:
                stat = "Stun";
                break;

            case 1:
                stat = "Dizzy";
                break;

            case 2:
                stat = "Pain";
                break;

            case 3:
                stat = "Seal";
                break;

            default:
                return null;
        }

        return stat + " : " + (int) combat.getPlayerStatusDamaged().get(stat) + " / " +
                (int) combat.getPlayerStatusTreshold().get(stat);
    }
	
	/**
	 * Gets the correct information about the opponent according to an iterator
	 */
    private String combatOpponentInformationGetter(Combat combat, int iterator) {

        if (iterator < 5) {
            return combatOpponentStatGetter(combat, iterator);
        }

        if (iterator == 5) {
            return "Element Resistance";
        }

        iterator -= 6;
        String description = combatOpponentElementResistanceGetter(combat, iterator);

        if (description != null) {
            return description;
        }

        iterator -= combat.getAllOpponentElementResistance().getSize();

        if (iterator == 0) {
            return "Status";
        }

        iterator -= 1;

        return combatOpponentStatusGetter(combat, iterator);

    }

    private String combatOpponentStatGetter(Combat combat, int iterator) {
        switch (iterator) {
            case 0:
                return "Stat";
            case 1:
                return "Strength " + (int) combat.getOpponentStrength();
            case 2:
                return "Focus " + (int) combat.getOpponentFocus();
            case 3:
                return "Armor " + (int) combat.getOpponentArmor();
            case 4:
                return "Agility " + (int) combat.getOpponentAgility();
            default:
                return null;
        }
    }

    private String combatOpponentElementResistanceGetter(Combat combat, int iterator) {

        Dictionary<Integer> resistance = combat.getAllOpponentElementResistance();
        try {
            return resistance.getKeyByIndex(iterator) + " " + resistance.getValueByIndex(iterator);

        } catch (IndexOutOfBoundsException | NullPointerException e){
            return null;
        }
    }

    private String combatOpponentStatusGetter(Combat combat, int iterator) {
        String stat;

        switch (iterator) {
            case 0:
                stat = "Stun";
                break;

            case 1:
                stat = "Dizzy";
                break;

            case 2:
                stat = "Pain";
                break;

            case 3:
                stat = "Seal";
                break;

            default:
                return null;
        }

        return stat + " : " + (int) combat.getOpponentStatusDamaged().get(stat) + " / " +
                (int) combat.getOpponentStatusTreshold().get(stat);
    }

}
