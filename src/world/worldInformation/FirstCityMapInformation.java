package world.worldInformation;

import world.items.HealthPotionPetty;
import world.items.HealthPotionPoor;
import world.items.Item;

import java.util.LinkedList;
import java.util.List;

/**
 * Contains information about the first city map and its villagers.
 *
 * @author Jérémie Beaudoin-Dion
 */
public final class FirstCityMapInformation {

    public static List<Item> getShopItems() {
        List<Item> allItems = new LinkedList<>();

        allItems.add(new HealthPotionPetty());
        allItems.add(new HealthPotionPoor());

        return allItems;
    }

    public static List<String> getShopConversation() {
        List<String> conversation = new LinkedList<>();

        conversation.add("So you're the new one in town, eh!");
        conversation.add("Care to buy a potion? I don't get many customers.");

        return conversation;
    }

}
