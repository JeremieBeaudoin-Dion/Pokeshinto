package images;

/**
* A Point is an object which has two variables representing
* the X axis and the Y axis. 
*
*@author Jérémie Beaudoin-Dion
*/
public class Point {

	private int x;
	private int y;
	
	/**
	* The constructor
	*
	*@param x: the X axis integer
	*@param y: the Y axis integer
	*/
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	* Getter for the x axis integer
	*/
	public int getX() {
		return x;
	}
	
	/**
	* Getter for the y axis integer
	*/
	public int getY() {
		return y;
	}
	
	public void addX(int amount) {
		x += amount;
	}
	
	public void addY(int amount) {
		y += amount;
	}

}
