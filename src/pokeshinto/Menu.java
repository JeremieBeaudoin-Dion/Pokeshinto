package pokeshinto;

/**
 * A menu is a game object that stores different buttons and Action
 * that are attached to these buttons
 * 
 * @author Jérémie Beaudoin-Dion
 *
 */
public class Menu {
	
	private String ID;
	private String[] buttons;
	private String allert;
	private int selected;
	
	/**
	 * Constructor
	 * 
	 * @param rootMenu: the choice of buttons at the root of the menu
	 * @param allert
	 */
	public Menu(String ID, String[] buttons, String allert) {
		this.ID = ID;
		this.buttons = buttons;
		this.allert = allert;
	}
	
	/**
	 * Returns the ID of the Menu
	 * @return
	 */
	public String getID() {
		return ID;
	}
	
	/**
	 * Returns the String allert that is currently on the menu
	 * 
	 * @return
	 */
	public String getAllert() {
		return allert;
	}
	
	/**
	 * Setter for the menu allert
	 * 
	 * @param allert
	 */
	public void setAllert(String allert){
		this.allert = allert;
	}
	
	/**
	 * Getter
	 * 
	 * @return
	 */
	public int getNumberOfButtons() {
		return buttons.length;
	}
	
	/**
	 * Getter
	 * 
	 * @param position
	 * @return: the String value of a button at a certain index
	 */
	public String getButton(int position) {
		return buttons[position];
	}
	
	/**
	 * Returns the index of the current selected button
	 * 
	 * @return
	 */
	public int getSelected() {
		return selected;
	}
	
	/**
	 * A decision has been taken by the player. The selected button
	 * will be acted upon. If there is an allert, the allert will be
	 * erased.
	 * 
	 * @return The action (or not if null) to do
	 */
	public Action<String> decide() {
		Action<String> action = null;
		
		if(allert == null){
			action = new Action<String>(ID, buttons[selected]);
		} else {
			allert = null;
		}
		
		return action;
	}
	
	/**
	 * The command to move the selector to the right.
	 */
	public void goRight() {
		selected++;
		if (selected >= buttons.length){
			selected = 0;
		}
	}
	
	/**
	 * The command to move the selector to the left.
	 */
	public void goLeft() {
		selected--;
		if (selected < 0){
			selected = buttons.length - 1;
		}
	}
	
	

}
