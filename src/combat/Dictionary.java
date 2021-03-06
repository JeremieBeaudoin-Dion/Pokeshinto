package combat;
import pokeshinto.Copyable;
import pokeshinto.KeyException;

import java.io.Serializable;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * This stores Integers binded with Strings.
 * 
 * @author Jérémie Beaudoin-Dion
 */
public class Dictionary implements Copyable<Dictionary>, Serializable {

    protected List<DictionaryElement> elements;

    /**
     * Constructor
     */
    public Dictionary() {
        elements = new LinkedList<>();
    }

    /**
     * Constructor for copy
     */
    private Dictionary(List<DictionaryElement> elements) {
        this.elements = elements;
    }

	public int getSize() {
		return elements.size();
	}

	public boolean contains(String key) {
        Iterator<DictionaryElement> iter = elements.iterator();

        while(iter.hasNext()) {
            if (iter.next().isKey(key)) {
                return true;
            }
        }

        return false;
    }

    public int indexOf(String key) throws KeyException {
        Iterator<DictionaryElement> iter = elements.iterator();
        int i = 0;

        while(iter.hasNext()) {
            if (iter.next().isKey(key)) {
                return i;
            }
            i++;
        }

        // Can't find the key in the dic
        throw new KeyException("Can't find the key: " + key);
    }
	
	/**
	 * Gets the element binded by the key.
	 * 
	 * @param key: the String attached to the element
	 * @return the <T> element
	 */
	public int getValueByKey(String key) throws KeyException{
	    return elements.get(indexOf(key)).getValue();
	}
	
	/**
	 * Gets the key at a specified location. Throws an exception if the index is not valid
	 * 
	 * @param index: the index of the key
	 * @return the <String> key
	 */
    public String getKeyByIndex(int index) throws IndexOutOfBoundsException {
		return elements.get(index).getKey();
	}
	
	/**
	 * Gets the value at a specified location. Throws an exception if the index is not valid
	 * 
	 * @param index: the index of the key
	 * @return the <T> element
	 */
    public int getValueByIndex(int index) throws IndexOutOfBoundsException {
		return elements.get(index).getValue();
	}
	
	/**
	 * Adds a new element binded to a String key.
     * If the element already exists, the value is implemented instead.
	 * 
	 * @param key: String the key binded to the element
	 * @param value: owner <t> the element to be stored in the dic
	 */
    public void push(String key, int value){
	    if (elements == null) {
            elements = new LinkedList<>();
        }

        try {
	        elements.get(indexOf(key)).addToValue(value);

        } catch (KeyException e) {
	        elements.add(new DictionaryElement(key, value));

        } catch (NullPointerException n) {
	        elements.add(new DictionaryElement("", 0));
        }
		
	}

    /**
     * Sorts the dictionary according to Integers
     */
    public void sortWithIntegers() {
        Collections.sort(elements);
    }

	@Override
	public Dictionary copy() {
        List<DictionaryElement> newElements = new LinkedList<>();
        Iterator<DictionaryElement> iter = elements.iterator();

        while(iter.hasNext()) {
            newElements.add(iter.next().copy());
        }

        return new Dictionary(newElements);
    }

}
