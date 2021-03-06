package world.items;

import combat.combatActions.Heal;

/**
 * A health potion has a healing effect.
 *
 * @author Jérémie Beaudoin-Dion
 */
public class HealthPotion extends Consumable {

    private Heal heal;

    public HealthPotion(String id, int value, Heal heal) {
        super(id, value);

        this.heal = heal;
    }

    public Heal getHeal() {
        return heal;
    }
}
