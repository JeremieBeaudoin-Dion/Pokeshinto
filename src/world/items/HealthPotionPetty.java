package world.items;

import combat.combatActions.Heal;
import combat.combatActions.HealHpFlat;

/**
 * A kind of potion which heals of 50 and costs 15$
 *
 * @author Jérémie Beaudoin-Dion
 */
public class HealthPotionPetty extends HealthPotion {

    private static final Heal pettyHeal = new HealHpFlat(true, "Current Health", 50);

    public HealthPotionPetty() {
        super("Petty Potion", 15, pettyHeal);
    }

}
