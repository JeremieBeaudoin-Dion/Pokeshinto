package world.worldObjects;

import duringMenus.Menu;
import duringMenus.ObjectHandlerInWorldMenu;
import images.PhysicalObject;
import world.items.Item;

import java.util.LinkedList;
import java.util.List;

/**
 * A type of villager which returns a menu of all items that you can
 * buy from them.
 *
 * @author Jérémie Beaudoin-Dion
 */
public class Villager_Shop extends VillagerIdle {

    /**
     * Constructor
     */
    public Villager_Shop(PhysicalObject object, PhysicalObject shadow, List<Item> items, List<String> conversation) {
        super(object, shadow, null, conversation);

        setupItemMenu(items);

        if (conversation == null) {
            setupConversation();
        }
    }

    /**
     * Constructor with default conversation
     */
    public Villager_Shop(PhysicalObject object, PhysicalObject shadow, List<Item> items) {
        super(object, shadow, null, null);

        setupItemMenu(items);
        setupConversation();
    }

    private void setupItemMenu(List<Item> items) {
        List<String> buttons = new LinkedList<>();

        for (Item item : items) {
            buttons.add(item.getId());
        }

        buttons.add("Return");

        super.setMenu(new Menu(ObjectHandlerInWorldMenu.MENU_SHOP_ID, buttons, null));
    }

    /**
     * Default conversation
     */
    private void setupConversation() {
        List<String> conversation = new LinkedList<>();

        conversation.add("Can I interrest you in something ?");

        super.setConversation(conversation);
    }

}
