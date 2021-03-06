package pokeshinto;

import combat.DictionaryElement;

import java.io.Serializable;

/**
 * A level progression is a set of bonus that a Pokeshinto gets
 * when obtaining a certain level.
 *
 * This has been done differently than the Dictionary because even if
 * the keys are the same, they don't add up.
 *
 * @author Jérémie Beaudoin-Dion
 */
public class LevelProgression implements Serializable {

    DictionaryElement[] allLevelUps;

    public static int NUMBER_OF_LEVELS = 10;

    /**
     * Constructor
     */
    public LevelProgression() {
        allLevelUps = new DictionaryElement[NUMBER_OF_LEVELS];
    }

    /**
     * To instanciate the levelup
     */
    public void add(int level, String element, int amount) {
        if (level == 0 || level > 9) {
            throw new IllegalArgumentException("The level " + level + " doesn't exists.");
        }

        allLevelUps[level] = new DictionaryElement(element, amount);
    }

    /**
     * Getter
     */
    public DictionaryElement get(int level) {
        if (level == 0 || level > 9) {
            throw new IllegalArgumentException("The level " + level + " doesn't exists.");
        }

        return allLevelUps[level];
    }

}
