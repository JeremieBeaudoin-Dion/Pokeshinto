package images;

import pokeshinto.Copyable;

import java.io.Serializable;

/**
* A Position is an object which has two variables representing
* the X axis and the Y axis. 
*
*@author Jérémie Beaudoin-Dion
*/
public class Position implements Copyable<Position>, Serializable {

	private int x;
	private int y;
	
	/**
	* The constructor
	*
	* @param x: the X axis integer
	* @param y: the Y axis integer
	*/
	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Position copy() {
		return new Position(x, y);
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public void addX(int amount) {
		x += amount;
	}
	
	public void addY(int amount) {
		y += amount;
	}

	public void setX(int amount) { x = amount; }

	public void setY(int amount) { y = amount; }
}
