package combat.combatAI;

/**
 * An opponent is diffenrent from the PlayerAI because it can be
 * beaten. Therefore, it gives back XP for pokeshinto levelup.
 *
 * @author Jérémie Beaudoin-Dion
 */
public interface OpponentAI {

    int getXp();

}
