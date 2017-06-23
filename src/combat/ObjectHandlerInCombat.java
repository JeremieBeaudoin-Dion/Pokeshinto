package combat;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.util.*;

import images.*;
import images.Position;
import pokeshinto.*;

/**
 * Is the model of the game. It handles the objects of the 
 * game and their position in combat.
 * 
 * @author Jérémie Beaudoin-Dion
 *
 */
public class ObjectHandlerInCombat {

    public static int MENU_ALLERT_Y_POSITION = 520;
	
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

		switch(actionHandlerInCombat.getCurrentState()) {
            case "During":
                physicalObjects.addAll(getMenu());
                physicalObjects.addAll(getAllInfo());
                break;

            case "Before":
                physicalObjects.addAll(getMenu());
                physicalObjects.addAll(getPokeshintoInfo(actionHandlerInCombat.getCombatOpponent().getAllPokeshintos(), "Opponent's Pokeshinto"));
                break;

            case "After":
                physicalObjects.addAll(getMenu());

                if (actionHandlerInCombat.getCombatOutcome().equals("Capture")) {
                    physicalObjects.addAll(getPokeshintoInfo(actionHandlerInCombat.getNewPokeshinto(), "New Pokeshinto"));
                } else {
                    physicalObjects.addAll(getLevelingPhaseObjects());
                }
                break;

        }
		
		return physicalObjects;
		
	}

    /**
     * Returns the objects representing the Pokeshinto's leveling
     */
    private List<PhysicalObject> getLevelingPhaseObjects() {
        List<PhysicalObject> allObjects = new LinkedList<>();

        int x = 40;
        int y = 40;

        int width = imageLoader.getXpBarBackgroundWidth();
        int height = imageLoader.getXpBarHeight();

        HashMap<String, Double> levelAdvancement = actionHandlerInCombat.getLevelAdvancement();
        Iterator<Map.Entry<String, Double>> iter = levelAdvancement.entrySet().iterator();

        while (iter.hasNext()) {
            Map.Entry pair = iter.next();

            String message = (String) pair.getKey();

            allObjects.add(new Text(new Position(x, y), message, FONT_HEADER, "Left", Color.BLACK));

            y += FONT_HEADER.getSize() * 1.5;

            Double value = (Double) pair.getValue();

            allObjects.add(new Image(new Position( x + CombatImageLoader.XP_BAR_BACKGROUND_INSET, y), width, height,
                    imageLoader.getXpBarBackground(value)));

            allObjects.add(new Image(new Position(x, y), width, height, imageLoader.getXpBarForeground()));

            y += height * 1.5;
        }

        return allObjects;
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
		int y = MENU_ALLERT_Y_POSITION;
		
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

            double numberToMultiplyY = 1.5;
            int positionToSplit = buttonText.lastIndexOf(' ');

            if (positionToSplit != -1) {
                String firstPart = buttonText.substring(0, positionToSplit);
                buttonText = buttonText.substring(positionToSplit, buttonText.length());

                physicalObjects.add(new Text(new Position(x + width/2, y + FONT_BUTTON.getSize()),
                        firstPart, FONT_BUTTON, "Center", currentColor));

                numberToMultiplyY = 2;
            }

            Text text = new Text(new Position(x + width/2, (int) (y + FONT_BUTTON.getSize() * numberToMultiplyY)),
                    buttonText, FONT_BUTTON,"Center", currentColor);

            physicalObjects.add(new Button(new Position(x, y), width, height, text, selected));

            String other;

            if (currentColor == COLOR_TEXT_YELLOW) {
                other = InfoHandler.getSkill(currentMenu.getButton(i)).getElement().getId();
            } else {
                other = doWhySkillIsNotDoable();
            }

            physicalObjects.add(new Text(new Position(x + width/2, y + FONT_BUTTON.getSize() * 2 + FONT_DESCRIPTION.getSize()),
                    other, FONT_DESCRIPTION, "Center", currentColor));

            x += width;
        }

        return physicalObjects;
    }

    private String doWhySkillIsNotDoable() {
        String reason = actionHandlerInCombat.getWhyCurrentInputIsNotDoable();

        if (reason == null) {
            reason = "Condition not met.";
        }

        return reason;
    }

    /**
     * Returns the description of the currently selected button of the menu
     */
	private List<PhysicalObject> getMenuDescription(Menu currentMenu) {
        List<PhysicalObject> physicalObjects = new LinkedList<>();

	    Position textPosition = new Position(Game.WINDOW_WIDTH/2, Game.WINDOW_HEIGHT*30/31);
	    String message = actionHandlerInCombat.getMenuDescription();

	    physicalObjects.add(new Text(textPosition, message, FONT_DESCRIPTION, "Center", COLOR_TEXT_YELLOW));

	    return physicalObjects;
    }
	
	/**
	 * Returns all physical objects to print on the JFrame that represent the
	 * information of the player and the opponent
	 */
	private List<PhysicalObject> getAllInfo() {
        List<PhysicalObject> physicalObjects = new LinkedList<>();

		physicalObjects.addAll(getHealthBars());

		// The name of the PokeShintos
		int y = windowHeight/10;
		int x = (int) (imageLoader.getHealthBarForegroundWidth() / 2.2);
		int x2 = (int) (windowWidth - imageLoader.getHealthBarForegroundWidth() / 1.8);

        physicalObjects.addAll(getHeader(y, x, x2));

        y += FONT_HEADER.getSize();

        // The stats of the PokeShintos
		physicalObjects.addAll(getAllStats(y, x, x2));
		
		return physicalObjects;
	}

    /**
     * @return the health bars of the Player and the Opponent according to their respective HP
     */
	private List<PhysicalObject> getHealthBars() {
	    List<PhysicalObject> physicalObjects = new LinkedList<>();

        // The health Bars
        int width = imageLoader.getHealthBarBackgroundWidth();
        int height = imageLoader.getHealthBarHeight();

        physicalObjects.add(new Image(new Position(CombatImageLoader.HEALTH_BAR_BACKGROUND_INSET, 0), width, height,
                imageLoader.getPlayerHealthBarBackground(actionHandlerInCombat.getCombatPlayer().getMissingHpPercentage())));

        physicalObjects.add(new Image(new Position(windowWidth - width - CombatImageLoader.HEALTH_BAR_BACKGROUND_INSET, 0),
                width, height, imageLoader.getHealthBarBackground(actionHandlerInCombat.getCombatOpponent().getMissingHpPercentage())));

        physicalObjects.add(new Image(new Position(0, 0), width, height, imageLoader.getHealthBarForeground()));
        physicalObjects.add(new Image(new Position(windowWidth - imageLoader.getHealthBarForegroundWidth(), 0), 0, 0, imageLoader.getHealthBarForeground()));

        String hpDescription = (int) actionHandlerInCombat.getCombatPlayer().getCurrentHealth() + " / " +
                (int) actionHandlerInCombat.getCombatPlayer().getMaxHealth();
        physicalObjects.add(new Text(new Position(CombatImageLoader.HEALTH_BAR_HALF_WIDTH,
                CombatImageLoader.HEALTH_BAR_HALF_HEIGHT), hpDescription, FONT_SMALL_BOLD, "Center", Color.BLACK));

        hpDescription = (int) actionHandlerInCombat.getCombatOpponent().getCurrentHealth() + " / " +
                (int) actionHandlerInCombat.getCombatOpponent().getMaxHealth();
        physicalObjects.add(new Text(new Position(windowWidth - CombatImageLoader.HEALTH_BAR_HALF_WIDTH,
                CombatImageLoader.HEALTH_BAR_HALF_HEIGHT), hpDescription, FONT_SMALL_BOLD, "Center", Color.BLACK));

        return physicalObjects;
    }

	/**
	 * @return the name of the Pokeshintos and their descriptions, or, if the player doesn't have a pokeshinto, its name
	 */
    private List<PhysicalObject> getHeader(int y, int x, int x2) {
	    List<PhysicalObject> physicalObjects = new LinkedList<>();

	    // The ID of the Pokeshintos
        physicalObjects.add(new Text(new Position(x, y), actionHandlerInCombat.getCombatPlayer().getCurrentPokeshintoID(), FONT_HEADER, "Center", Color.BLACK));
        physicalObjects.add(new Text(new Position(x2, y), actionHandlerInCombat.getCombatOpponent().getCurrentPokeshintoID(), FONT_HEADER, "Center", Color.BLACK));

        // The description of the Pokeshintos
        y += FONT_HEADER.getSize();

        physicalObjects.add(new Text(new Position(x, y), actionHandlerInCombat.getCombatPlayer().getCurrentPokeshintoDescription(), FONT_SMALL_BOLD, "Center", Color.BLACK));
        physicalObjects.add(new Text(new Position(x2, y), actionHandlerInCombat.getCombatOpponent().getCurrentPokeshintoDescription(), FONT_SMALL_BOLD, "Center", Color.BLACK));

        return physicalObjects;
    }

    private List<PhysicalObject> getAllStats(int y, int x, int x2) {
	    List<PhysicalObject> physicalObjects = new LinkedList<>();

        int iterator = 0;
        String playerStat;
        String opponentStat;
        Font currentFont = FONT_SMALL_BOLD;

        do {
            playerStat = combatPlayerInformationGetter(iterator);
            opponentStat = combatOpponentInformationGetter(iterator);
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
	private String combatPlayerInformationGetter(int iterator) {

	    if (iterator < 5) {
	        return combatPlayerStatGetter(iterator);
        }

        if (iterator == 5) {
            return "Element Resistance";
        }

        iterator -= 6;

        Dictionary resist = actionHandlerInCombat.getCombatPlayer().getAllResistance();
        String description = combatPlayerElementResistanceGetter(iterator, resist);

        if (description != null) {
            return description;
        }

        if (!actionHandlerInCombat.getCombatPlayer().getCurrentPokeshintoID().equals("Player")) {
            iterator -= resist.getSize();
        }

        if (iterator == 0) {
            return "Status";
        }

        iterator -= 1;

        return combatPlayerStatusGetter(iterator);

	}

    private String combatPlayerStatGetter(int iterator) {
        switch (iterator) {
            case 0:
                return "Stat";
            case 1:
                return "Strength " + (int) actionHandlerInCombat.getCombatPlayer().getStrength();
            case 2:
                return "Focus " + (int) actionHandlerInCombat.getCombatPlayer().getFocus();
            case 3:
                return "Armor " + (int) actionHandlerInCombat.getCombatPlayer().getArmor();
            case 4:
                return "Agility " + (int) actionHandlerInCombat.getCombatPlayer().getAgility();
            default:
                return null;
        }
    }

    private String combatPlayerElementResistanceGetter(int iterator, Dictionary resist) {

        try {
            return resist.getKeyByIndex(iterator) + " " + resist.getValueByIndex(iterator);

        } catch (IndexOutOfBoundsException | NullPointerException e){
            return null;
        }
    }

    private String combatPlayerStatusGetter(int iterator) {
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

        return stat + " : " + (int) actionHandlerInCombat.getCombatPlayer().getCurrentDamagedStatuses().get(stat) + " / " +
                (int) actionHandlerInCombat.getCombatPlayer().getStatusTreshold().get(stat);
    }
	
	/**
	 * Gets the correct information about the opponent according to an iterator
	 */
    private String combatOpponentInformationGetter(int iterator) {

        if (iterator < 5) {
            return combatOpponentStatGetter(iterator);
        }

        if (iterator == 5) {
            return "Element Resistance";
        }

        iterator -= 6;
        Dictionary resist = actionHandlerInCombat.getCombatOpponent().getAllResistance();
        String description = combatOpponentElementResistanceGetter(iterator, resist);

        if (description != null) {
            return description;
        }

        iterator -= resist.getSize();

        if (iterator == 0) {
            return "Status";
        }

        iterator -= 1;

        return combatOpponentStatusGetter(iterator);

    }

    private String combatOpponentStatGetter(int iterator) {
        switch (iterator) {
            case 0:
                return "Stat";
            case 1:
                return "Strength " + (int) actionHandlerInCombat.getCombatOpponent().getStrength();
            case 2:
                return "Focus " + (int) actionHandlerInCombat.getCombatOpponent().getFocus();
            case 3:
                return "Armor " + (int) actionHandlerInCombat.getCombatOpponent().getArmor();
            case 4:
                return "Agility " + (int) actionHandlerInCombat.getCombatOpponent().getAgility();
            default:
                return null;
        }
    }

    private String combatOpponentElementResistanceGetter(int iterator, Dictionary resist) {

        try {
            return resist.getKeyByIndex(iterator) + " " + resist.getValueByIndex(iterator);

        } catch (IndexOutOfBoundsException | NullPointerException e){
            return null;
        }
    }

    private String combatOpponentStatusGetter(int iterator) {
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

        return stat + " : " + (int) actionHandlerInCombat.getCombatOpponent().getCurrentDamagedStatuses().get(stat) + " / " +
                (int) actionHandlerInCombat.getCombatOpponent().getStatusTreshold().get(stat);
    }

    /**
     * Gets pertinent info for pokeshinto select
     */
    private List<PhysicalObject> getPokeshintoInfo(List<Pokeshinto> allShintos, String headerMessage) {

        List<PhysicalObject> physicalObjects = new LinkedList<>();
        // The name of the PokeShintos
        int y = windowHeight/10;
        int x = (int) (imageLoader.getHealthBarForegroundWidth() / 2.2);
        int titleX = windowWidth/2;

        physicalObjects.add(new Text(new Position(titleX, y), headerMessage, FONT_ALLERT, "Center",  Color.BLACK));

        y += FONT_ALLERT.getSize() * 1.5;

        Iterator<Pokeshinto> iter = allShintos.iterator();

        while(iter.hasNext()) {
            Pokeshinto current = iter.next();

            physicalObjects.addAll(getPokeshintoAndResistance(current, x, y));

            x += windowWidth/4;

        }

        return physicalObjects;

    }

    private List<PhysicalObject> getPokeshintoAndResistance(Pokeshinto current, int x, int y) {
        List<PhysicalObject> physicalObjects = new LinkedList<>();

        physicalObjects.add(new Text(new Position(x, y), current.getId(), FONT_HEADER, "Center",  Color.BLACK));

        y += FONT_HEADER.getSize();

        physicalObjects.add(new Text(new Position(x, y), "Statistics", FONT_SMALL_BOLD, "Center",  Color.BLACK));

        y += FONT_SMALL_BOLD.getSize();

        physicalObjects.addAll(getGenericStat(current, x, y));

        y += FONT_SMALL_NORMAL.getSize() * 5;

        physicalObjects.add(new Text(new Position(x, y), "Element Resistance", FONT_SMALL_BOLD, "Center",  Color.BLACK));

        y += FONT_SMALL_BOLD.getSize();

        Dictionary resist = current.getCombatAttributes().getElementResistance();
        physicalObjects.addAll(getResistance(resist, x, y));

        return physicalObjects;
    }

    private List<PhysicalObject> getGenericStat(Pokeshinto current, int x, int y) {
        List<PhysicalObject> physicalObjects = new LinkedList<>();

        physicalObjects.add(new Text(new Position(x, y),"Strength " + (int) current.getCombatAttributes().getStrength() ,
                FONT_SMALL_NORMAL, "Center",  Color.BLACK));

        y += FONT_SMALL_NORMAL.getSize();

        physicalObjects.add(new Text(new Position(x, y),"Focus " + (int) current.getCombatAttributes().getFocus() ,
                FONT_SMALL_NORMAL, "Center",  Color.BLACK));

        y += FONT_SMALL_NORMAL.getSize();

        physicalObjects.add(new Text(new Position(x, y),"Armour " + (int) current.getCombatAttributes().getArmor() ,
                FONT_SMALL_NORMAL, "Center",  Color.BLACK));

        y += FONT_SMALL_NORMAL.getSize();

        physicalObjects.add(new Text(new Position(x, y),"Agility " + (int) current.getCombatAttributes().getAgility() ,
                FONT_SMALL_NORMAL, "Center",  Color.BLACK));

        return physicalObjects;
    }

    private List<PhysicalObject> getResistance(Dictionary resist, int x, int y) {
        List<PhysicalObject> physicalObjects = new LinkedList<>();

        for (int i=0; i<resist.getSize(); i++) {
            physicalObjects.add(new Text(new Position(x, y), resist.getKeyByIndex(i) + " " + resist.getValueByIndex(i),
                    FONT_SMALL_NORMAL, "Center",  Color.BLACK));

            y += FONT_SMALL_NORMAL.getSize();
        }

        return physicalObjects;
    }

}
