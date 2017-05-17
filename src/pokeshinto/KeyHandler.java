package pokeshinto;

import java.awt.Component; 
import java.awt.event.*; 

/** 
 * The current KeyListener, to handle key events. It stores 
 * the data in an array of Booleans. The InputHandler can
 * then be called and the desired key bind getted from isKeyDown.
 * 
 * @author Jérémie Beaudoin-Dion
 */ 
public class KeyHandler implements KeyListener {
	
	boolean[] keys = new boolean [256];
	/** 
     * Assigns the newly created InputHandler to a Component 
     * @param c Component to get input from 
     */ 
    public KeyHandler(Component c){ 
            c.addKeyListener(this);
    } 
    
    /** 
     * Checks whether a specific key is down 
     * @param keyCode The key to check 
     * @return Whether the key is pressed or not 
     */ 
    public boolean isKeyDown(int keyCode) 
    { 
            if (keyCode > 0 && keyCode < 256){
            	return keys[keyCode]; 
            } 
            
            return false; 
    } 
    
    /** 
     * Called when a key is pressed while the component is focused 
     * @param e KeyEvent sent by the component 
     */ 
    public void keyPressed(KeyEvent e) 
    { 
            if (e.getKeyCode() > 0 && e.getKeyCode() < 256) 
            { 
                    keys[e.getKeyCode()] = true; 
            } 
    } 

    /** 
     * Called when a key is released while the component is focused 
     * @param e KeyEvent sent by the component 
     */ 
    public void keyReleased(KeyEvent e) 
    { 
            if (e.getKeyCode() > 0 && e.getKeyCode() < 256) 
            { 
                    keys[e.getKeyCode()] = false; 
            } 
    } 

    /** 
     * Not used 
     */ 
    public void keyTyped(KeyEvent e){} 
} 
