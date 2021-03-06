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

    // TODO: Add every item in package ITEMS
    public static Item getNewItemFromId(String id) {
        switch(id) {
            case HealthPotionPetty.ID:
                return new HealthPotionPetty();

            case HealthPotionPoor.ID:
                return new HealthPotionPoor();
        }

        throw new IllegalArgumentException("The item " + id + " doesn't exists");
    }

    public static String getInformation(String itemID) {
        if (itemID.equals(HealthPotionPetty.ID)) {
            return "A potion that restores 50 HP.";

        } else if (itemID.equals(HealthPotionPoor.ID)) {
            return "A potion that restores 100 HP.";
        }

        throw new IllegalArgumentException("The item " + itemID + " is not handled in getInformation");
    }

    public static int getCost(String itemID) {
        if (itemID.equals(HealthPotionPetty.ID)) {
            return HealthPotionPetty.COST;

        } else if (itemID.equals(HealthPotionPoor.ID)) {
            return HealthPotionPoor.COST;
        }

        throw new IllegalArgumentException("The item " + itemID + " is not handled in getInformation");
    }

}
