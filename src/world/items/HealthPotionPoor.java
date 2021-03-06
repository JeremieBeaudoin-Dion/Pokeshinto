package world.items;

import combat.combatActions.Heal;
import combat.combatActions.HealHpFlat;

/**
 * A potion that heals 100 HP
 *
 * @author Jérémie Beaudoin-Dion
 */
public class HealthPotionPoor extends HealthPotion {

    private static final Heal poorHeal = new HealHpFlat(true, "Current Health", 100);

    public static final String ID = "Poor Potion";
    public static final int COST = 50;

    public HealthPotionPoor() {
        super(ID, COST, poorHeal);
    }

}
