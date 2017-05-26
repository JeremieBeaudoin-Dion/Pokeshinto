package world;

import images.PhysicalObject;
import images.Position;

/**
 * A door binds a MapID to a new MapID
 * 
 * @author Jeremie Beaudoin-Dion
 */
public class Door extends PhysicalObject {
	
	private String from;
	private String to;
	private Position nextPosition;
	
	/**
	 * Constructor
	 * 
	 * @param from: The ID of the Map holding the door
	 * @param to: The ID of the Map where to door leads
	 * @param nextPosition: where the door leads in the map
	 */
	public Door(Position position, int width, int height, String from, String to, Position nextPosition) {
		super(position, width, height);
		
		this.from = from;
		this.to = to;
		this.nextPosition = nextPosition;
	}
	
	/**
	 * Returns the MapID where the doors belongs
	 * 
	 * @return
	 */
	public String getFromID() {
		return from;
	}
	
	/**
	 * Returns the MapID where the doors leads
	 * 
	 * @return
	 */
	public String getToID() {
		return to;
	}
	
	/**
	 * Returns the Position of the Map where the player should go
	 * 
	 * @return
	 */
	public Position getNextPosition() {
		return nextPosition;
	}

}
