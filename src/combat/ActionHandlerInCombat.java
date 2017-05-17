package combat;

import pokeshinto.Action;
import pokeshinto.InfoHandler;
import pokeshinto.Menu;
import pokeshinto.Player;
import pokeshinto.Pokeshinto;
import pokeshinto.StackLinked;

/**
 * The ActionHandler is the main LOGIC component of the combat. It 
 * gets player input, sends it to the correct object, and holds 
 * most of the game objects.
 * 
 * @author Colocataire
 *
 */
public class ActionHandlerInCombat {
	
	private Combat combat;
	private Player player;
	private Menu currentMenu;
	private StackLinked<Menu> allMenus;
	
	private boolean combatOver;
	
	/**
	 * Constructor for the controller of the game
	 * 
	 */
	public ActionHandlerInCombat(Player player) {
		this.player = player;
	}
	
	/** 
	 * Getter for player
	 * @return: the player Object
	 */
	public Player getPlayer() {
		return player;
	}
	
	/**
	 * Getter
	 * 
	 * @return: The current menu
	 */
	public Menu getCurrentMenu() {
		return currentMenu;
	}
	
	/**
	 * Get the combat value for 
	 */
	public Combat getCombat() {
		return combat;
	}

	/**
	 * Gets an input and handles it to the correct object
	 * 
	 * @param myInput: the String converted input from JFrame
	 */
	public void doInput(String myInput) {
		
		if(myInput.equals("Left")){
			currentMenu.goLeft();
			
		} else if(myInput.equals("Right")){
			currentMenu.goRight();
			
		} else if(myInput.equals("Decide")){
			
			Action<String> decision = currentMenu.decide();
			
			// If there is an action to do
			if (decision != null){
				if (getInputIsDoable(currentMenu.getSelected())){
					handleMenuAction(decision);
				}
			}
			
		} else if(myInput.equals("Escape")){
			
			doMenuReset();
		}
		
	}
	
	/**
	 * Returns if the menu action is doable according to the Combat class
	 * 
	 * @param index
	 * @return
	 */
	public boolean getInputIsDoable(int index) {
		
		Action<Integer> action = new Action<Integer>(currentMenu.getID(), index);
		
		return combat.getPlayerActionIsDoable(action);
	}
	
	/**
	 * Handles the Menu Actions and sends them to the right method
	 * @param action
	 */
	private void handleMenuAction(Action<String> action) {
		if (action.getKey().equals("Root")){
			handleRootMenuAction(action.getValue());
		} else if (action.getKey().equals("Skill")){
			combat.setPlayerDecision(new Action<Integer>("Skill", currentMenu.getSelected()));
			doMenuReset();
		} else if (action.getKey().equals("Switch")){
			combat.setPlayerDecision(new Action<Integer>("Switch", currentMenu.getSelected()));
			doMenuReset();
		}
	}
	
	/**
	 * Handles the ROOTMENU actions.
	 * @param action
	 */
	private void handleRootMenuAction(String action){
		// Basic menu actions
		if (action.equals("Skill")){
			Skill[] equipedSkills = combat.getPlayerPokeshinto().getEquipedSkills();
			String[] buttons = new String[equipedSkills.length];
			
			for (int i=0; i<equipedSkills.length; i++) {
				buttons[i] = equipedSkills[i].getId();
			}
			
			allMenus.push(currentMenu);
			currentMenu = new Menu("Skill", buttons, null);
			
		} else if (action.equals("Switch")){
			Pokeshinto[] equipedPokeshinto = player.getAllShintos();
			String[] buttons = new String[equipedPokeshinto.length - 1];
			int j = 0;
			
			for (int i=0; i<equipedPokeshinto.length; i++) {
				if (!equipedPokeshinto[i].getId().equals(combat.getPlayerPokeshinto().getId())){
					buttons[j] = equipedPokeshinto[i].getId();
					j++;
				}
			}
			
			allMenus.push(currentMenu);
			currentMenu = new Menu("Switch", buttons, null);
			
		} else if (action.equals("Meditate")){
			combat.setPlayerDecision(new Action<Integer>(action, null));
			doMenuReset();
		}
	}

	/**
	 * Pops the next menu in the Stack
	 */
	private void doMenuReset(){
		if(!allMenus.isEmpty()){
			currentMenu = allMenus.pop();
		}
	}
	
	/**
	 * Starts a combat with an opponent. Sets up the menu combat
	 * 
	 * @param player: its pokeShintos and useful Items
	 * @param opponent: a combat AI with pokeShintos
	 */
	public void startCombat(CombatAI opponent) {
		combatOver = false;
		allMenus = new StackLinked<Menu>();
		combat = new Combat(player.getCombatPlayer(), opponent);
		currentMenu = InfoHandler.getMenu("Start Combat");
		newTurn();
	}
	
	/**
	 * Sends an allert to the current menu
	 * 
	 * @param allert: the String to show as an allert
	 */
	private void sendAllertToMenu(String allert){
		currentMenu.setAllert(allert);
	}
	
	/**
	 * Called every time there is an action
	 * 
	 * @param gameState: the String current state of the game
	 * @return true if the combat is OVER
	 */
	public boolean update(String gameState) {
		if(combat.update()) {
			endTurn();
			newTurn();
		}
		
		return combatOver;
		
	}
	
	/**
	 * Ends the turn and creates a new menu
	 */
	private void endTurn() {
		allMenus = new StackLinked<Menu>();
		currentMenu = InfoHandler.getMenu("New Turn");		
		currentMenu.setAllert(getCombatDescription());
		combat.endTurn();
	}
	
	/**
	 * Starts a new turn and 
	 */
	private void newTurn() {
		String outcome = combat.newTurn();
		if (outcome != null) {
			endCombat(outcome);
		}		
	}	
	
	/**
	 * Ends the current combat and updates the Player
	 */
	private void endCombat(String outcome) {
		// End the combat
		combatOver = true;
		
		// Set up the new player's health
		player.setHealth(combat.getPlayerCurrentHp());
		
		if (outcome.equals("Captured")) {
			// handle pokeshinto capture
		}
		
	}
	
	/**
	 * Depending on the outcome of the combat, returns a string with a description
	 * of what happenned.
	 * 
	 * @return
	 */
	private String getCombatDescription() {
		Action<Integer> opponentDecision = combat.getOpponentChoice();
		Action<Integer> playerDecision = combat.getPlayerChoice();
		String combatDescription = "Opponent ";
		
		if (opponentDecision.getKey().equals("Meditate")){
			combatDescription += "meditated";
		} else if (opponentDecision.getKey().equals("Skill")) {
			combatDescription += "used " + combat.getOpponentPokeshinto().getEquipedSkills()[opponentDecision.getValue()].getId();
			if (combat.getOpponentDamageDone() > 0) {
				combatDescription += " for " + combat.getOpponentDamageDone() + " damage.";
			}
		} else if (playerDecision.getKey().equals("Switch")) {
			combatDescription += "switched to " + combat.getOpponentPokeshinto().getId();
		}
		
		combatDescription += "\n";
		combatDescription += "You ";
		
		if (playerDecision.getKey().equals("Meditate")){
			combatDescription += "meditated";
		} else if (playerDecision.getKey().equals("Skill")) {
			combatDescription += "used " + combat.getPlayerPokeshinto().getEquipedSkills()[playerDecision.getValue()].getId();
			if (combat.getPlayerDamageDone() > 0){
				combatDescription += " for " + combat.getPlayerDamageDone() + " damage.";
			}
		} else if (playerDecision.getKey().equals("Switch")) {
			combatDescription += "switched to " + combat.getPlayerPokeshinto().getId();
		}
		
		return combatDescription;
	}
	
}
