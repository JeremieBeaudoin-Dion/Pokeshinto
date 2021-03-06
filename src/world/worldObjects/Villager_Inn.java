package world.worldObjects;

import images.PhysicalObject;
import world.items.Item;

import java.util.LinkedList;
import java.util.List;

/**
 * A type of villager which can heal the Player and
 * save the current game.
 *
 * @author Jérémie Beaudoin-Dion
 */
public class Villager_Inn extends VillagerIdle {

    public static int CHARACTERID = 3;

    /**
     * Constructor
     */
    public Villager_Inn(PhysicalObject object, PhysicalObject shadow, List<String> conversation) {
        super(object, shadow, conversation);

        if (conversation == null) {
            setupConversation();
        }
    }

    /**
     * Constructor with default conversation
     */
    public Villager_Inn(PhysicalObject object, PhysicalObject shadow) {
        super(object, shadow, null);

        setupConversation();
    }

    /**
     * Default conversation
     */
    private void setupConversation() {
        List<String> conversation = new LinkedList<>();

        conversation.add("Do you want to rest a bit?");
        conversation.add("This will heal you and save your game.");

        super.setConversation(conversation);
    }

    public int getCharacterId(){
        return CHARACTERID;
    }

}

