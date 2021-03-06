package world.items;

/**
 * A type of item that has an effect even in combat
 *
 * @author Jérémie Beaudoin-Dion
 */
public abstract class ItemUsableInCombat extends Item{

    ItemUsableInCombat(String id, int value) {
        super(id, value);
    }
}
