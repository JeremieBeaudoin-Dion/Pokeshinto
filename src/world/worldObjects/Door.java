package world.worldObjects;

import images.PhysicalObject;
import images.Position;
import javafx.geometry.Pos;

import java.io.Serializable;

/**
 * A door binds a MapID to a new MapID
 * 
 * @author Jeremie Beaudoin-Dion
 */
public class Door extends PhysicalObject implements Serializable {

	private String nextMapID;
	private Position nextPosition;
	
	/**
	 * Constructor
	 *
	 * @param nextMapID the ID of the Map where to door leads
	 * @param nextPosition where the door leads in the map
	 */
	public Door(Position position, int width, int height, String nextMapID, Position nextPosition) {
		super(position, width, height);
		
		this.nextMapID = nextMapID;
		this.nextPosition = nextPosition;
	}

    /**
     * Constructor with complete physicalObject representation of the Door
     *
     * @param physicalObject the dimensions of the Door
     * @param nextMapID the ID of the Map where to door leads
     * @param nextPosition where the door leads in the map
     */
	public Door(PhysicalObject physicalObject, String nextMapID, Position nextPosition) {
		super(physicalObject.getPosition(), physicalObject.getWidth(), physicalObject.getHeight());

		this.nextMapID = nextMapID;
		this.nextPosition = nextPosition;
	}
	
	/**
	 * @return the MapID where the doors leads
	 */
	public String getNextMapID() {
		return nextMapID;
	}
	
	/**
	 * @return the Position of the Map where the player should go
	 */
	public Position getNextPosition() {
		return nextPosition;
	}

}
