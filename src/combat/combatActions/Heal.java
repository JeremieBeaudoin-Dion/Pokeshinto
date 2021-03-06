package combat.combatActions;

import combat.Combat;

/**
 * A positive effect on a Stat
 *
 * @author Jérémie Beaudoin-Dion
 */
public interface Heal {

    double getTotalHeal(Combat combat, String healOwner);

    boolean getAffectsMe();

    String getStatAffected();
}
