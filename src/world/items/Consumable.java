package world.items;

/**
 * An Consumable is an object which can be used once
 * to do a specific effect.
 *
 * It has a value (in gold) and a specific ID.
 *
 * @author Jérémie Beaudoin-Dion
 */
public abstract class Consumable extends ItemUsableInCombat{

    private boolean isUsed;

    Consumable(String id, int value) {
        super(id, value);
    }

    public boolean getIsUsed() {
        return isUsed;
    }

    public void use() {
        isUsed = true;
    }

}
