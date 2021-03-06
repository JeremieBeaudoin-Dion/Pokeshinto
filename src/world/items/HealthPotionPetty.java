package world.items;

import combat.combatActions.Heal;
import combat.combatActions.HealHpFlat;

/**
 * A potion that heals 50 HP
 *
 * @author Jérémie Beaudoin-Dion
 */
public class HealthPotionPetty extends HealthPotion {

    private static final Heal pettyHeal = new HealHpFlat(true, "Current Health", 50);

    public static final String ID = "Petty Potion";
    public static final int COST = 20;

    public HealthPotionPetty() {
        super(ID, COST, pettyHeal);
    }

}
