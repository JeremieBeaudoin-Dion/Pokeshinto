package world.items;

import java.io.Serializable;

/**
 * An Item is an object which has a value (in gold) and a specific ID.
 *
 * @author Jérémie Beaudoin-Dion
 */
public class Item implements Serializable{

    private String id;
    private int value;

    Item(String id, int value) {
        this.id = id;
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public int getValue() {
        return value;
    }

}
