package world.items;

import java.io.Serializable;
import java.util.*;

/**
 * An item bag contains certain items and a quantity of them
 *
 * @author Jérémie Beaudoin-Dion
 */
public class ItemBag implements Serializable {

    private List<Item> allItems;

    /**
     * Constructor
     */
    public ItemBag() {
        allItems = new LinkedList<>();
    }

    public void add(Item newItem) {
        allItems.add(newItem);
    }

    public void add(String itemId) {
        allItems.add(Item.getNewItemFromId(itemId));
    }

    public void remove(Item itemToRemove) {
        allItems.remove(itemToRemove);
    }

    public void remove(String itemId) {
        allItems.remove(get(itemId));
    }

    public void removeAllConsumedConsumable() {
        for(Item item : allItems) {
            if (item instanceof Consumable) {
                if (((Consumable) item).getIsUsed()) {
                    remove(item);
                }
            }
        }
    }

    public boolean contains(Item item) {
        return allItems.contains(item);
    }

    public boolean contains(String itemId) {
        try {
            get(itemId);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public Item get(String itemId) {
        for(Item item : allItems) {
            if (item.getId().equals(itemId)) {
                return item;
            }
        }

        throw new IllegalArgumentException("The Item " + itemId + " is not equiped in the ItemBag");
    }

    public int getAmount(String itemId) {
        int amount = 0;

        for(Item item : allItems) {
            if (item.getId().equals(itemId)) {
                amount++;
            }
        }

        return amount;
    }

    public int size() {
        return allItems.size();
    }

    /**
     * Resets all usable items
     */
    public void reset() {
        throw new UnsupportedOperationException();
    }

    public List<String> getMenuButtons() {
        List<String> buttons = new LinkedList<>();

        for (Item item : allItems) {
            if (!buttons.contains(item.getId())) {
                buttons.add(item.getId());
            }
        }

        return buttons;
    }

}
