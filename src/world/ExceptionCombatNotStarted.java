package world;

/**
 * An error thrown when a combatAI is called when a combat hasn't started
 *
 * @author Jérémie Beaudoin-Dion
 */
class ExceptionCombatNotStarted extends RuntimeException {

    ExceptionCombatNotStarted() {
        super();
    }

}
