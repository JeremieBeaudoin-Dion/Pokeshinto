package world.worldObjects;

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

    public static int CHARACTERID = 2;

    private List<Item> items;

    /**
     * Constructor
     */
    public Villager_Shop(PhysicalObject object, PhysicalObject shadow, List<Item> items, List<String> conversation) {
        super(object, shadow, conversation);

        this.items = items;

        if (conversation == null) {
            setupConversation();
        }
    }

    /**
     * Constructor with default conversation
     */
    public Villager_Shop(PhysicalObject object, PhysicalObject shadow, List<Item> items) {
        super(object, shadow, null);

        this.items = items;

        setupConversation();
    }

    /**
     * Default conversation
     */
    private void setupConversation() {
        List<String> conversation = new LinkedList<>();

        conversation.add("Can I interrest you in something ?");

        super.setConversation(conversation);
    }

    /**
     * Getter
     */
    public List<Item> getItems() {
        return items;
    }

    public int getCharacterId(){
        return CHARACTERID;
    }

}
