package pokeshinto;
import combat.Action;
import combat.Combat;
import combat.CombatAI;
import combat.Menu;
import combat.Skill;

/**
 * The ActionHandler is the main LOGIC component of the game. It 
 * gets player input, sends it to the correct object, and holds 
 * most of the game objects.
 * 
 * @author Colocataire
 *
 */
public class ActionHandlerInCombat {
	
	private Combat combat;
	private Player player;
	private CombatAI opponent;
	private Menu currentMenu;
	private StackLinked<Menu> allMenus;
	
	private boolean combatOver;
	
	private int waitBeforeInput;
	private final int MAX_WAIT_VALUE = 10;
	
	/**
	 * Constructor for the controller of the game
	 * 
	 */
	public ActionHandlerInCombat(Player player) {
		this.player = player;
		this.opponent = null;
		
		waitBeforeInput = MAX_WAIT_VALUE;
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
		
		// Don't do input if there isn't enough time to handle the decision
		if(waitBeforeInput != 0) {
			return;
		}
		
		if(myInput.equals("Left")){
			currentMenu.goLeft();
			
		} else if(myInput.equals("Right")){
			currentMenu.goRight();
			
		} else if(myInput.equals("Decide")){
			
			Action<String> decision = currentMenu.decide();
			
			// If there is an action to do
			if (decision != null){
				handleMenuAction(decision);
			}
			
		} else if(myInput.equals("Escape")){
			
			doMenuGoBack();
		}
		
		// Makes sure there isn't too much input per second
		waitBeforeInput = MAX_WAIT_VALUE;
		
	}
	
	/**
	 * Handles the Menu Actions and sends them to the right method
	 * @param action
	 */
	private void handleMenuAction(Action<String> action) {
		if (action.getKey().equals("Root")){
			handleRootMenuAction(action.getValue());
		} else if (action.getKey().equals("Attack")){
			combat.setPlayerDecision(new Action<Integer>("Skill", currentMenu.getSelected()));
			doMenuGoBack();
		} else if (action.getKey().equals("Switch")){
			combat.setPlayerDecision(new Action<Integer>("Switch", currentMenu.getSelected()));
			doMenuGoBack();
		}
	}
	
	/**
	 * Handles the ROOTMENU actions.
	 * @param action
	 */
	private void handleRootMenuAction(String action){
		// Basic menu actions
		if (action.equals("Attack")){
			Skill[] equipedSkills = combat.getPlayerPokeshinto().getEquipedSkills();
			String[] buttons = new String[equipedSkills.length];
			
			for (int i=0; i<equipedSkills.length; i++) {
				buttons[i] = equipedSkills[i].getId();
			}
			
			allMenus.push(currentMenu);
			currentMenu = new Menu("Attack", buttons, null);
			
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
			doMenuGoBack();
		}
	}

	/**
	 * Pops the next menu in the Stack
	 */
	private void doMenuGoBack(){
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
		this.opponent = opponent;
		combat = new Combat(player, opponent);
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
	 * Called every frame
	 * 
	 * @param gameState: the String current state of the game
	 * @return if the combat is OVER
	 */
	public boolean update(String gameState) {
		// Update waiting time for input
		waitBeforeInput -= 1;
		
		if(combat.update()) {
			endTurn();
			newTurn();
		}
		
		// Minimum value of 0
		if(waitBeforeInput < 0){
			waitBeforeInput = 0;
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
			combatDescription += " for " + combat.getOpponentDamageDone() + " damage.";
		} else if (playerDecision.getKey().equals("Switch")) {
			combatDescription += "switched to " + combat.getOpponentPokeshinto().getId();
		}
		
		combatDescription += "\n";
		combatDescription += "You ";
		
		if (playerDecision.getKey().equals("Meditate")){
			combatDescription += "meditated";
		} else if (playerDecision.getKey().equals("Skill")) {
			combatDescription += "used " + combat.getPlayerPokeshinto().getEquipedSkills()[playerDecision.getValue()].getId();
			combatDescription += " for " + combat.getPlayerDamageDone() + " damage.";
		} else if (playerDecision.getKey().equals("Switch")) {
			combatDescription += "switched to " + combat.getPlayerPokeshinto().getId();
		}
		
		return combatDescription;
	}
	
}
