package pokeshinto;
import java.awt.Component;
import java.awt.event.*; 

/** 
 * The current MouseLister, to handle mouse events. It stores 
 * the value of ismousedown in a boolean. MouseHandler can
 * then be called and know if the mouse is pressed or not with 
 * isMouseDown.
 * 
 * @author Jérémie Beaudoin-Dion
 */ 
public class MouseHandler implements MouseListener {

	boolean ismousedown = false;
	/** 
     * Assigns the newly created MouseHandler to a Component 
     * @param c Component to get mouse input from 
     */ 
    public MouseHandler(Component c){ 
            c.addMouseListener(this);
    }
	
	/**
	 * Handles mouse events
	 */
	public void mousePressed(MouseEvent arg0) {
		ismousedown = true;
	}

	public void mouseReleased(MouseEvent arg0) {
		ismousedown = false;
	}
	
	/**
	 * 
	 * @return Whether the mouse is pressed or not
	 */
	public boolean isMouseDown(){
		return ismousedown;
	}
	
	// Not used
	public void mouseClicked(MouseEvent e) {}

	public void mouseEntered(MouseEvent arg0) {}

	public void mouseExited(MouseEvent arg0) {}
}
