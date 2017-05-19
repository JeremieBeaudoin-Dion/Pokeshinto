package images;

/**
 * An object which has a position and a width and height on the frame.
 * 
 * @author Jérémie Beaudoin-Dion
 *
 */
public class PhysicalObject {
	
	protected Point position;
	private int width;
	private int height;
	private boolean passable;
	
	/**
	 * Constructor with Width and Height
	 * 
	 * @param position
	 * @param width
	 * @param height
	 */
	public PhysicalObject(Point position, int width, int height) {
		this.position = position;
		this.width = width;
		this.height = height;
		this.passable = true;
	}
	
	/**
	 * Constructor with all attributes
	 * 
	 * @param position
	 * @param width
	 * @param height
	 * @param passable: if the object can be walked through by the Player
	 */
	public PhysicalObject(Point position, int width, int height, boolean passable) {
		this.position = position;
		this.width = width;
		this.height = height;
		this.passable = passable;
	}
	
	/**
	* Constructor with two points
	*
	*@param: northEast, the highest and leftmost point
	*@param: southWest, the lowest and rightmost point
	*/
	public PhysicalObject(Point northEast, Point southWest) {
		this.position = northEast;
		width = southWest.getX() - northEast.getX();
		height = southWest.getY() - northEast.getY();
	}
	
	public int getX() {
		return position.getX();
	}
	public int getY() {
		return position.getY();
	}
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	public Point getPosition() {
		return position;
	}
	public Point getSouthPoint() {
		return new Point(position.getX() + width, position.getY() + height);
	}
	public boolean getPassable() {
		return passable;
	}

}
