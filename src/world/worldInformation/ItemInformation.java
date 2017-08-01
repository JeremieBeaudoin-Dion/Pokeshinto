package world.worldInformation;

import world.items.HealthPotionPetty;
import world.items.HealthPotionPoor;

/**
 * Holds the descriptions and information about certain items
 *
 * @author Jérémie Beaudoin-Dion
 */
public class ItemInformation {

    public static String getInformation(String itemID) {
        if (itemID.equals(HealthPotionPetty.ID)) {
            return "A potion that restores 50 HP.";

        } else if (itemID.equals(HealthPotionPoor.ID)) {
            return "A potion that restores 100 HP.";
        }

        throw new IllegalArgumentException("The item " + itemID + " is not handled in getInformation");
    }

}
