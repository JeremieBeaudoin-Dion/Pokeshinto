package combat.combatActions;

import combat.Combat;

/**
 * A buff to a certain attribute
 *
 * @author Jérémie Beaudoin-Dion
 */
public interface AttributeBuff {

    /**
     * Getters
     */
    boolean isOnMe();

    String getAttribute();

    double getAmount(Combat combat, String owner);
}
