package images;

import java.io.Serializable;

/**
 * An object which has a position and a width and height on the frame.
 * 
 * @author Jérémie Beaudoin-Dion
 *
 */
public class PhysicalObject implements Comparable<PhysicalObject>, Serializable {
	
	protected Position position;
	private int width;
	private int height;
	private boolean passable;
	
	/**
	 * Constructor with Width and Height
	 * 
	 * @param position where the Physical Object is in the map
	 * @param width its width
	 * @param height its height
	 */
	public PhysicalObject(Position position, int width, int height) {
		this.position = position;
		this.width = width;
		this.height = height;
		this.passable = true;
	}
	
	/**
	 * Constructor with all attributes
	 *
	 * @param passable: if the object can be walked through by the Player
	 */
	public PhysicalObject(Position position, int width, int height, boolean passable) {
		this.position = position;
		this.width = width;
		this.height = height;
		this.passable = passable;
	}
	
	/**
	* Constructor with two points
	*
	* @param northEast, the highest and leftmost point
	* @param southWest, the lowest and rightmost point
	*/
	public PhysicalObject(Position northEast, Position southWest) {
		this.position = northEast;
		width = southWest.getX() - northEast.getX();
		height = southWest.getY() - northEast.getY();
	}

    /**
     * @param other the other object to be compared with
     * @return 0 if they are equal, over zero if this one is greater, and under zero if this one is smaller
     */
	public int compareTo(PhysicalObject other) {
        return position.getY() - other.getY();
	}

	public void setPosition(Position newPosition){
		this.position = new Position(newPosition.getX(), newPosition.getY());
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
	public Position getPosition() {
		return position;
	}
	public Position getSouthPoint() {
		return new Position(position.getX() + width, position.getY() + height);
	}
	public boolean getPassable() {
		return passable;
	}

}
