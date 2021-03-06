package combat;

import pokeshinto.Copyable;

import java.io.Serializable;

/**
 * An element of the Dictionary. Can store most numbers.
 *
 * @author Jérémie Beaudoin-Dion
 */
public class DictionaryElement implements Copyable<DictionaryElement>, Comparable<DictionaryElement>, Serializable {

    private String key;
    private int value;

    /**
     * Constructor
     */
    public DictionaryElement(String key, int value) {
        this.key = key;
        this.value = value;
    }

    /**
     * Will not cause problems in most number cases
     */
    public void addToValue(int other) {
        value += other;
    }

    public boolean isKey(String other) {
        return key.equals(other);
    }

    public String getKey() {
        return key;
    }

    public int getValue() {
        return value;
    }


    @Override
    public DictionaryElement copy() {
        return new DictionaryElement(key, value);
    }

    @Override
    public int compareTo(DictionaryElement other) {
        return value - other.getValue();
    }
}
