package pokeshinto;

import java.util.List;
import java.util.Stack;

/**
 * A menu is a game object that stores different buttons and Action
 * that are attached to these buttons
 * 
 * @author Jérémie Beaudoin-Dion
 *
 */
public class Menu {
	
	private String ID;
	private List<String> buttons;
	private Stack<String> allert;
	private int selected;

	/**
	 * Constructor
	 *
	 * @param ID
	 * @param buttons
	 * @param allert
	 */
	public Menu(String ID, List<String> buttons, String allert) {
		this.ID = ID;
		this.buttons = buttons;
		this.allert = new Stack<>();

		if (allert != null) {
			this.allert.push(allert);
		}
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
	    if (allert.isEmpty()) {
	        return null;
        }

		return allert.peek();
	}
	
	/**
	 * Adds a menu allert
	 */
	public void setAllert(String allert){
		this.allert.push(allert);
	}
	
	/**
	 * Getter
	 * 
	 * @return
	 */
	public int getNumberOfButtons() {
		return buttons.size();
	}
	
	/**
	 * Getter
	 * 
	 * @param position
	 * @return: the String value of a button at a certain index
	 */
	public String getButton(int position) {
		return buttons.get(position);
	}
	
	/**
	 * Returns the current selected button
	 */
	public String getSelected() {
		return buttons.get(selected);
	}

	public int getCurrentSelectionInt() {
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
		
		if(allert.isEmpty()){
			action = new Action<>(ID, buttons.get(selected));

		} else {
			allert.pop();
		}
		
		return action;
	}
	
	/**
	 * The command to move the selector to the right.
	 */
	public void goForward() {
	    if (!allert.isEmpty()) {
	        return;
        }

		selected++;
		if (selected >= buttons.size()){
			selected = 0;
		}
	}
	
	/**
	 * The command to move the selector to the left.
	 */
	public void goBack() {
        if (!allert.isEmpty()) {
            return;
        }

		selected--;
		if (selected < 0){
			selected = buttons.size() - 1;
		}
	}
	
	

}
